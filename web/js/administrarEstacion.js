$(document).ready(function () {
    $("#agregarEstacion").on("click", function () {
       
        agregarEstacion($("#cmb_lineas").val(), $("#nombre_estacion").val(), $("#id_pk_estacion").val(),$("#abrev_estacion").val());
    });
    $("#cmb_lineas").on("change", function (evento) {
        cargaTablaEstaciones($("#cmb_lineas").val());
    });
    $(".cargando").hide();
    $("#tablaEstaciones").hide();
    $("#msj").hide();
    $("#data").hide();
    
});

function cargaTablaEstaciones(idLinea) {
    if (idLinea !== "") {
        $.ajax({
            url: 'ajax/cargaTablaEstaciones.jsp',
            type: "POST",
            data: {idLinea: idLinea},
            beforeSend: function () {
                $("#tablaEstaciones").show();
                $(".cargando").fadeIn("slow");
            },
            complete: function () {
                $(".cargando").fadeOut("slow");
            },
            success: function (data) {
                $('#tablaEstaciones').html(data);
            }});
    }

}

function agregarEstacion(idLinea, idEstacion, progEst,abrev) {
    if ((idLinea !== '') && (idEstacion !== "") && (progEst !== "")) {
          
        $.ajax({
            url: 'AdministrarEstacion',
            type: "POST",
            data: {accion: 'Agregar', select_linea: idLinea, nombre_estacion: idEstacion, id_pk_estacion: progEst,abrev_estacion:abrev},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                //$("#msj").html("El otro");
               // $("#msj").fadeOut("slow");
              
                $('#data').html(data);
                cargaTablaEstaciones($("#cmb_lineas").val());
            }
        });
    } else {
        alert("Datos invalidos");
    }
}
function eliminar() {
    eliminoEstacion($("#id_linea_el").val(), $("#id_pk_estacion_el").val());
    cancelarEstacion();
}
function eliminarEstacion(id, id2) {
    ajaxEstacion(id, id2, "ajax/eliminarEstacion.jsp");
}
function eliminoEstacion(idLinea, idPkEstacion) {
    if ((idLinea !== "") && (idPkEstacion !== "")) {
        $.ajax({
            url: 'AdministrarEstacion',
            type: "POST",
            data: {accion: 'Eliminar', id_linea: idLinea, id_pk_estacion: idPkEstacion},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").fadeOut("slow");
                $('#data').html(data);
                cargaTablaEstaciones($("#cmb_lineas").val());
            }
        });
    } else {
        alert("Datos invalidos");
    }

}
function editarEstacion(id, id2) {
    ajaxEstacion(id, id2, "ajax/editarEstacion.jsp");
}

function editar() {
    var id_linea = $("#id_linea_ed").val();
    var nombre_estacion = $("#nombre_estacion_ed").val();
    var id_pk_estacion = $("#id_pk_estacion_ed").val();
    var abrev_estacion = $("#abrev_estacion_ed").val();
    if ((id_linea !== "") && (nombre_estacion !== "") && (id_pk_estacion !== "")) {
        $.ajax({
            url: 'AdministrarEstacion',
            type: "POST",
            data: {accion: 'Editar', id_linea: id_linea, nombre_estacion: nombre_estacion, id_pk_estacion: id_pk_estacion, abrev_estacion:abrev_estacion},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").fadeOut("slow");
                cancelarEstacion();
                cargaTablaEstaciones(id_linea);
                $('#data').html(data);
            }
        });
        cargaTablaEstaciones($("#cmb_lineas").val());
    } else {
        alert("Datos invalidos");
    }
}

function cancelarEstacion() {
    $("#bgVentanaModal").fadeOut();
    $('#datos').html("");
}

function ajaxEstacion(id, id2, url) {

    var msjEspera = "...:: Consultando Estacion::..";

    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
            type: "POST",
            data: {idLinea: id, idPkEstacion: id2},
            beforeSend: function () {
                $("#msjajax").html(msjEspera);
                $("#msjajax").slideUp(500);
            },
            complete: function () {
                $("#msjajax").slideDown(500);
            },
            success: function (data) {
                $('#datos').html(data);
            }
        });
    }
}

function cambiarNombre(id){

        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}
