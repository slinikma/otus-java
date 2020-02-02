// Глобальные переменные зло, но с JS-ом у меня сложно всё (:
let stompClient = null;
let isConnected = false;

function connect() {
     stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
     stompClient.connect({}, frame => {
        isConnected = true;
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/response', response => {
          printToTable(JSON.parse(response.body));
        });

        stompClient.send("/app/admin/user/list");
    });
};

function connect1() {
     console.log("Called connect1()");
     stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
     stompClient.connect({}, frame => {
        isConnected = true;
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/response', response => {
            connect();
        // TODO: редирект??
//          createNewUser();
//stompClient.send("/app/admin/user/list");
        });

        createNewUser();
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
        connect();
    }

     // TODO: дисконнект должен быть по таймауту со стороны сервера?
     // TODO: если его нужно явно дёргать на стороне клиента, то как это лучше делать?
//     disconnect();
};


function createNewUser() {

    if (isConnected) {
        stompClient.send("/app/admin/user/create",
            {},
            JSON.stringify({'login': $("#holder-input-login").val(), 'password': $("#holder-input-password").val()}));
    } else {
        connect1();
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

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#create-user").click(function() { createNewUser(); });
});
