// Глобальные переменные зло, но с JS-ом у меня сложно всё (:
let stompClient = null;
let isConnected = false;


function connect(onConnectCallback) {
     console.log("Called connect()");
     stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
     stompClient.connect({}, frame => {
        isConnected = true;
        console.log(`Connected: ${frame}`);
        onConnectCallback();
    });
};

const disconnect = () => {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    isConnected = false;
    console.log("Disconnected");
};

function getAllUsers() {

    if (isConnected) {
        stompClient.send("/app/admin/user/list");
    } else {
        connect(() => {
          stompClient.subscribe('/user/topic/response/user/list', response => {
            printToTable(JSON.parse(response.body));
          });
          stompClient.subscribe('/topic/response/user/list', response => {
            printToTable(JSON.parse(response.body));
          });
          stompClient.subscribe('/topic/response/new/user', response => {
              console.log("Hey, new user was created! " + response);
              appendToTable(JSON.parse(response.body));
          });
          stompClient.subscribe('/user/topic/response/errors', response => {
            alert(response.body);
          });

          stompClient.send("/app/admin/user/list");
        });
    }
};


function createNewUser() {

    if (isConnected) {
        stompClient.send("/app/admin/user/create",
            {},
            JSON.stringify({'login': $("#holder-input-login").val(), 'password': $("#holder-input-password").val()}));
    } else {
        connect(() => {
          stompClient.subscribe('/topic/response/new/user', response => {
              console.log("Hey, new user was created! " + response);
              window.location.pathname = '/admin_show_users.html'
          });
          stompClient.subscribe('/user/topic/response/errors', response => {
            alert(response.body);
          });

           stompClient.send("/app/admin/user/create",
               {},
               JSON.stringify({'login': $("#holder-input-login").val(), 'password': $("#holder-input-password").val()}));
        });
    }
}

function stopMessageSystem() {
  if (isConnected) {
    stompClient.send("/app/messagesystem/stop",
        {},
        {});
  } else {
    connect(() => {
        stompClient.subscribe('/user/topic/response/messagesystem', response => {
            alert(response.body);
        });
        stompClient.subscribe('/user/topic/response/errors', response => {
          alert(response.body);
        });

        stompClient.send("/app/messagesystem/stop",
                {},
                {});
    });
  }
}

function printToTable(users) {
    console.log("in printToTable");
    console.log(users);

    $("#tdata").empty();
    $.each(users, function(index, value){
      $("#tdata").append("<tr>" + "<td>" + value.login + "</td>" + "<td>" + value.password + "</td>" + "</tr>")
    });
};

function appendToTable(user) {
    console.log("appending to table ... ");
    console.log(user);

    $("#tdata").append("<tr>" + "<td>" + user.login + "</td>" + "<td>" + user.password + "</td>" + "</tr>")
};

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#create-user").click(function() { createNewUser(); });
    $("#stop-ms").click(function() { stopMessageSystem(); });
});
