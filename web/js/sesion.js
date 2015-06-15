$(document).ready(function ajaxSesion() {



    var msjEspera = "...:: INICIO DE SESIÓN  ::..";
    //var session=$("#sesionActiva").val();
    // alert(session);


    $("#bgVentanaModal").fadeIn();
    $.ajax({
        url: 'ajax/inicioSesion.jsp',
        type: "POST",
        data: {},
        beforeSend: function () {

            $("#msjajax").html(msjEspera);
            $("#msjajax").slideUp(500);
        },
        complete: function () {
            $("#msjajax").slideDown(500);

        },
        success: function (data) {
            $('#datos').html(data);
            $("#password").on("keypress", function (event) {
                iniciarSesion2(event);
            });
            $("#login").on("keyup", function (event) {
                iniciarSesion2(event);
            });
        }
    });


});


function iniciarSesion() {
    var usuario = $("#login").val();
    var clave = $("#password").val();
    // alert(usuario + '  ' + clave);
    $.ajax({
        url: 'SessionAdmin',
        type: "POST",
        data: {accion: 'Iniciar', idUsuario: usuario, clave: clave},
        beforeSend: function () {
            $("#inicio_sesion").fadeOut();
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
        },
        complete: function () {
//                $("#msjajax").slideDown(500);

        },
        success: function (data) {

            alert(data);
            $('#session').html(data);
            $("#inicio_sesion").fadeIn();
            $("#bgVentanaModal").fadeOut();
            window.location = "index.jsp";
        }
    });

}
function iniciarSesion2(event) {
    var usuario = $("#login").val();
    var clave = $("#password").val();
//    alert(event.which);
    if (event.which === 13) {
        $.ajax({
            url: 'SessionAdmin',
            type: "POST",
            data: {accion: 'Iniciar', idUsuario: usuario, clave: clave},
            beforeSend: function () {
                $("#inicio_sesion").fadeOut();
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
            },
            complete: function () {
//                $("#msjajax").slideDown(500);

            },
            success: function (data) {

                alert(data);
                $('#session').html(data);
                $("#inicio_sesion").fadeIn();
                $("#bgVentanaModal").fadeOut();
                window.location = "index.jsp";
            }
        });
    }
}
