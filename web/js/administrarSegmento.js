$(document).ready(function () {
    $("#cmb_lineas").on("change", function (evento) {
        cargaTablaSegmentos();
    });
    $("#msj").hide();
    $("#data").hide();
    $("#tablaSegmentos").hide();

});
function cargaTablaSegmentos() {
    var idLinea = $("#cmb_lineas").val();
    if (idLinea !== "") {
        $.ajax({
            url: 'ajax/cargaTablaSegmentos.jsp',
            type: "POST",
            data: {idLinea: idLinea},
            beforeSend: function () {
                $("#tablaSegmentos").show();
                $(".cargando").fadeIn("slow");
            },
            complete: function () {
                $(".cargando").fadeOut("slow");
            },
            success: function (data) {
                $('#tablaSegmentos').html(data);
            }});
    }
}

function agregar() {
    var cmb_linea = $("#cmb_lineas").val();
    var tipo_segmento = $("#tipo_segmento").val();
    var pk_inicio = $("#pk_inicio").val();
    var pk_final = $("#pk_final").val();
    var gradiente = $("#gradiente").val();
//    var tunel = $("#tunel").val();
    var velocidad_max_ascendente = $("#velocidad_max_ascendente").val();
    var velocidad_max_descendente = $("#velocidad_max_descendente").val();

    if (tipo_segmento === true) {
        var radio = $("#radio").val();
    }else{
        radio= 0;
    }
    if ((cmb_linea !== "") && (tipo_segmento !== "") && (pk_inicio !== "")
            && (pk_final !== "") && (gradiente !== "") 
            && (velocidad_max_ascendente !== "")  && (velocidad_max_ascendente >0) 
            && (velocidad_max_descendente !== "")&& (velocidad_max_descendente >0)) {
        $.ajax({
            url: 'AdministrarSegmento',
            type: "POST",
            data: {accion: "Agregar", cmb_linea: cmb_linea, tipo_segmento: tipo_segmento, pk_inicio: pk_inicio, pk_final: pk_final, radio: radio, gradiente: gradiente, velocidad_max_ascendente: velocidad_max_ascendente, velocidad_max_descendente: velocidad_max_descendente},
            beforeSend: function () {
                
                $("#msj").html(".:Esperando:.");
                $("#msj").fadeIn("slow");
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
              //  alert(pk_final);
            },
            success: function (data) {
                //alert(radio);
                $("#msj").html(data);
                $('#tablaSegmentos').load('ajax/cargaTablaSegmentos.jsp', {idLinea: cmb_linea});
            }
        });
    }else{
        alert("Uno de los valores ingresados es incorrecto");
    }
}

function ajaxSegmento(id, id2, url) {

    var msjEspera = "...:: Consultando Segmento::..";

    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
            type: "POST",
            data: {idLinea: id, idPkInicial: id2},
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
function eliminarSegmento(id, id2) {
    ajaxSegmento(id, id2, "ajax/eliminarSegmento.jsp");
}

function eliminarS(progresiva, linea) {
    if ((progresiva !== '') && (linea !== '')) {
        $.ajax({
            url: 'AdministrarSegmento',
            type: "POST",
            data: {accion: 'Eliminar', progresiva: progresiva, linea: linea},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
                $("#msj").show();                
            },
            complete: function () {
                $("#bgVentanaModal").fadeOut();
            },
            success: function (data) {
                $("#msj").hide();
                $('#data').html(data);
                $('#data').show();
                cargaTablaSegmentos($("#cmb_lineas").val());
            }
        });
    }
}

function editarSegmento(id, id2) {
    ajaxSegmento(id, id2, "ajax/editarSegmento.jsp");
}
function editarS() {

    var id_linea_ed = $("#id_linea_ed").val();
    var tipo_segmento_ed = $("#tipo_segmento_ed").val();
    var pk_inicio_ed = $("#pk_inicio_ed").val();
    var pk_final_ed = $("#pk_final_ed").val();
    var gradiente_ed = $("#gradiente_ed").val();
   // var tunel_ed = $("#tunel_ed").val();
    var velocidad_max_ascendente_ed = $("#velocidad_max_ascendente_ed").val();
    var velocidad_max_descendente_ed = $("#velocidad_max_descendente_ed").val();

    if (tipo_segmento_ed === true) {
        var radio_ed = $("#radio_ed").val();
    }else{
        var radio_ed = 0;
    }
    if ((id_linea_ed !== "") && (tipo_segmento_ed !== "") && (pk_inicio_ed !== "")
            && (pk_final_ed !== "") && (gradiente_ed !== "") && 
             (velocidad_max_ascendente_ed !== "") && (velocidad_max_descendente_ed !== "")
             && (velocidad_max_descendente_ed >0)&& (velocidad_max_ascendente_ed >0)) {
        $.ajax({
            url: 'AdministrarSegmento',
            type: "POST",
//            data: {accion: 'Editar', id_linea: ("#id_linea").val(), id_nombre_estacion: $("#id_nombre_estacion").val(), pk_estacion:$("#pk_estacion").val()},
            data: {accion: 'Editar', id_linea: id_linea_ed, tipo_segmento: tipo_segmento_ed, pk_inicio: pk_inicio_ed, pk_final: pk_final_ed, radio: radio_ed, gradiente: gradiente_ed,  velocidad_max_ascendente: velocidad_max_ascendente_ed, velocidad_max_descendente: velocidad_max_descendente_ed},
            beforeSend: function () {
                $("#msj").show();
                $("#msj").html(".:Esperando:.");
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").fadeOut("slow");
                $('#data').html(data);
                $('#data').show();
                cargaTablaSegmentos($("#cmb_lineas").val());
                cancelarSegmento();

            }
        });
    } else {
        alert("Uno de los parametros es invalido");
    }
}
function cancelarSegmento() {
    $("#bgVentanaModal").fadeOut()();
    $('#datos').html("");

}
function cambiarNombre(id){

        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}

