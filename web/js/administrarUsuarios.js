/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $("#btn_agr_usu").on('click', function (evento) {
        
        agregarUsuario();
    });
   
cargarTablaUsuario();
$("#msjUsuario").hide();

});

function agregarUsuario(){
    var idUsuario=$("#id_usuario").val();
    var nombreUsuario=$("#txt_nom_usu").val();
    var  apellidoUsuario=$("#txt_ape_usu").val();
    var contraseñaUsuario=$("#pass_usu").val();
    var confirmarContraseñaUsuario=$("#pass_usu2").val();
    var nivelUsuario=$("#nivel_usu").val();
    
    
    if(idUsuario!==""&&nombreUsuario!==""&&apellidoUsuario!==""&&contraseñaUsuario!==""&&nivelUsuario!==""){
        
        
        $.ajax({
        url: 'AdministrarUsuario',
        type: "POST",
        data: {accion:"Agregar",idUsuario:idUsuario,nombreUsuario:nombreUsuario,apellidoUsuario:apellidoUsuario,contraseñaUsuario:contraseñaUsuario,nivelUsuario:nivelUsuario,confirmarContraseña:confirmarContraseñaUsuario},
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (data) {
            $("#msjUsuario").show();
            $('#msjUsuario').html(data);
                cargarTablaUsuario();
            
        }
    });

    }else{
        alert("Datos Invalidos");
    }
    
}
function editarUsuario(){
    var nombreUsuario=$("#txt_nom_usu_ed").val();
    var  apellidoUsuario=$("#txt_ape_usu_ed").val();
    var contraseñaUsuario=$("#pass_usu_ed").val();
    var confirmarContraseñaUsuario=$("#pass_usu2_ed").val();
    var nivelUsuario=$("#nivel_usu_ed").val();
    
    if(nombreUsuario!==""&&apellidoUsuario!==""&&contraseñaUsuario!==""&&nivelUsuario!==""){
        $.ajax({
        url: 'AdministrarUsuario',
        type: "POST",
        data: {accion:"Editar",nombreUsuario:nombreUsuario,apellidoUsuario:apellidoUsuario,contraseñaUsuario:contraseñaUsuario,nivelUsuario:nivelUsuario},
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (data) {
            $("#msjUsuario").show();
            $('#msjUsuario').html(data);
                cancelarUsuario();
                cargarTablaUsuario();
        }
    });

    }else{
        alert("Datos Invalidos");
    }
    
}
function eliminarUsuario(){
    
    var idUsuario=$("#id_usu_el").val();
    
    if(idUsuario!==""){
        $.ajax({
        url: 'AdministrarUsuario',
        type: "POST",
        data: {accion:"Eliminar",idUsuario:idUsuario},
        beforeSend: function () {
        },
        complete: function () {
        },
        success: function (data) {
            $("#msjUsuario").show();
            $('#msjUsuario').html(data);
                cancelarUsuario();
                cargarTablaUsuario();
        }
    });

    }else{
        alert("Datos Invalidos");
    }
    
}

function cargarTablaUsuario(){
    $.ajax({
        url: 'ajax/cargaTablaUsuarios.jsp',
        type: "POST",
        data: {},
        beforeSend: function () {
          //  $('#tabla_usuarios').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            //$('#car2').fadeOut("slow");
        },
        success: function (data) {
            $('#tabla_usuarios').html(data);
            
        }
    });
}
    function editarUsuario1(id) {
     
    
    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: 'ajax/editarUsuario.jsp',
            type: "POST",
            data: {id: id},
            beforeSend: function () {
                $('#datos').html("<img id='car3' class='cargando2' src='img/cargando2.gif'/>");
            },
            complete: function () {
                $("#car3").fadeOut("slow");
            },
            success: function (data) {
                $('#datos').html(data);
                
            }
        });
    }
}
    function eliminarUsuario1(id) {
    
    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: 'ajax/eliminarUsuario.jsp',
            type: "POST",
            data: {id: id},
            beforeSend: function () {
                $('#datos').html("<img id='car3' class='cargando2' src='img/cargando2.gif'/>");
            },
            complete: function () {
                $("#car3").fadeOut("slow");
            },
            success: function (data) {
                $('#datos').html(data);
            }
        });
    }
    
  
}

  function cancelarUsuario() {
    $("#bgVentanaModal").fadeOut();
    $('#datos').html("");

}
    

function cambiarNombre(id){
        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}