$(document).ready(function () {
    $("#cmb_lineas").on("change load", function () {
        cargarTablaCV();
    });
    $("#btn_agr_cir_via").on("click", function () {
        agregarCirVia();
    });
    $("#msj").hide();
    $("#data").hide();
    $("#marcoCV").hide();
});

function agregarCirVia() {
    var id_linea = $("#cmb_lineas").val();
    var prog_ini_cir = $("#prog_ini_cir").val();
    var prog_fin_cir = $("#prog_fin_cir").val();
    if ((id_linea !== "") && (prog_ini_cir !== "") && (prog_fin_cir !== "")) {
        $.ajax({
            url: 'AdministrarCircuitoVia',
            type: "POST",
            data: {accion: "Agregar", id_linea: id_linea, prog_ini_cir: prog_ini_cir, prog_fin_cir: prog_fin_cir},
            beforeSend: function () {
                $("#msj").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#msj").fadeIn("slow");
            },
            complete: function () {

            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargarTablaCV();
                cancelarCircuitoVia();
            }
        });
    } else {
        alert("Datos incompletos");
    }
}
function eliminarCirVia() {
    var id_linea = $("#hdd_id_linea").val();
    var prog_ini_cir = $("#hdd_prog_ini_cir").val();
    if ((id_linea !== "") && (prog_ini_cir !== 0)) {
        $.ajax({
            url: 'AdministrarCircuitoVia',
            type: "POST",
            data: {accion: "Eliminar", id_linea: id_linea, prog_ini_cir: prog_ini_cir},
            beforeSend: function () {
                $("#msj").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#msj").fadeIn("slow");
            },
            complete: function () {

            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargarTablaCV();
                cancelarCircuitoVia();
            }
        });
    } else {
        alert("Error al eliminar");
    }

}
function editarCirVia() {
    var id_linea = $("#hdd_id_linea_ed").val();
    var prog_ini_cir = $("#hdd_prog_ini_cir_ed").val();
    var prog_fin_cir = $("#txt_prog_fin_cir_ed").val();
    $.ajax({
        url: 'AdministrarCircuitoVia',
        type: "POST",
        data: {accion: "Editar", id_linea: id_linea, prog_ini_cir: prog_ini_cir, prog_fin_cir: prog_fin_cir},
        beforeSend: function () {
            $("#msj").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
            $("#msj").fadeIn("slow");
        },
        complete: function () {

        },
        success: function (data) {
            $("#msj").html("");
            $("#msj").html(data);
            cargarTablaCV();
            cancelarCircuitoVia();
        }
    });
}

function ajaxCircuitoVia(id, id2, url) {
    var msjEspera = "...:: Consultando Circuito de Via::..";
    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
            type: "POST",
            data: {idLinea: id, idPkInicialCircuito: id2},
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
function eliminarCircuitoVia(id, id2) {
    ajaxCircuitoVia(id, id2, "ajax/eliminarCircuitoVia.jsp");
}

function editarCircuitoVia(id, id2) {
    ajaxCircuitoVia(id, id2, "ajax/editarCircuitoVia.jsp");
}

function cancelarCircuitoVia() {
    $("#bgVentanaModal").fadeOut()();
    $('#datos').html("");
}

function cargarTablaCV() {
    var idLinea = $("#cmb_lineas").val();
    $("#marcoCV").load("ajax/cargaTablaCircuitosVia.jsp", {idLinea: idLinea}, function () {
        $("#marcoCV").show();
    });
}

function cambiarNombre(id){

        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}

