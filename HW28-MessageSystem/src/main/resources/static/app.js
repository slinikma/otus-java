let stompClient = null;

const setConnected = connected => {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected)
        $("#chatLine").show();
    else
        $("#chatLine").hide();
    $("#message").html("");
};

function connect() {
     stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
     stompClient.connect({}, frame => {
        setConnected(true);
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/response', response => {
          showAllUsers(JSON.parse(response.body));
        });

        stompClient.send("/app/admin/user/list");
    });
};

const connect1 = () => {
     console.log("Called connect1()");
     stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
     stompClient.connect({}, frame => {
        setConnected(true);
        console.log(`Connected: ${frame}`);
        stompClient.subscribe('/topic/response', response => {
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
    setConnected(false);
    console.log("Disconnected");
};

const showUsers = () => {
    connect();

    stompClient.send(
        "/admin/user/list",
        {});
//        JSON.stringify({'messageStr': $("#message").val()}));

     disconnect();
};

function getAllUsers() {

    // TODO: возможно, стоит проверять есть ли уже соединение
    connect();

     // TODO: дисконнект должен быть по таймауту со стороны сервера?
     // TODO: если его нужно явно дёргать на стороне клиента, то как это лучше делать?
//     disconnect();
};

const createNewUser = () => stompClient.send(
    "/app/admin/user/save",
    {},
    JSON.stringify({'login': $("#holder-input-login").val(), 'password': $("#holder-input-password").val()}));

function showAllUsers(users) {
    console.log("in showAllUsers");
    console.log(users);

    $.each(users, function(index, value){
      $("#tdata").append("<tr>" + "<td>" + value.login + "</td>" + "<td>" + value.password + "</td>" + "</tr>")
    });
};

//const showGreeting = messageStr =>
//    $("#chatLine").append(`<tr><td>${messageStr}</td></tr>`);

$(() => {
// TODO: ну и срань ваш JS, доделать ...
    $("form").on('submit', event => event.preventDefault());
//    $('#form').submit(false);
//    $("#connect").click(connect);
//    $("#disconnect").click(disconnect);


    $("#create-user").click(connect1());
});
