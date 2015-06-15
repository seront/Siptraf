/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function cerrarSesion() {
    $.ajax({
        url: 'SessionAdmin',
        type: "POST",
        data: {accion: 'Cerrar'},
        beforeSend: function () {

//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
        },
        complete: function () {
//                $("#msjajax").slideDown(500);

        },
        success: function (data) {
            alert(data);
            window.location="index.jsp";
            
            
        }

    });

}

function cambiarContraseña(){
    $('#bgVentanaModal').fadeIn();
    $.ajax({
        url: 'ajax/cambiarContraseña.jsp',
        type: "POST",
        data: {},
        beforeSend: function () {

//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
        },
        complete: function () {
//                $("#msjajax").slideDown(500);

        },
        success: function (data) {
            $('#datos').html(data);
            
        }

    });}
    
function nuevaContraseña(){
    var viejaContraseña=$('#pass_usu_viejo').val();
    var nuevaContraseña=$('#pass_usu_nuevo').val();
    var nuevaContraseña2=$('#pass_usu_nuevo2').val();
    if(nuevaContraseña===nuevaContraseña2){
    $.ajax({
        url: 'AdministrarUsuario',
        type: "POST",
        data: {accion:"Cambiar Contraseña",viejaContraseña:viejaContraseña,nuevaContraseña:nuevaContraseña,nuevaContraseña2:nuevaContraseña2},
        beforeSend: function () {

//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
        },
        complete: function () {
//                $("#msjajax").slideDown(500);

        },
        success: function (data) {
            alert(data);
                cancelarUsuario();
        }

    });
}else{
    alert("Las Contraseñas No Coinciden");
}
    
}

  function cancelarUsuario() {
    $("#bgVentanaModal").fadeOut();
    $('#datos').html("");

}