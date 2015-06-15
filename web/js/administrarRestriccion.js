$(document).ready(function () {
    $("#cmb_lineas").on("change", function (evento) {
        cargaRestricciones($("#cmb_lineas").val());
        $("#contRestricciones").show();
    });
    $(".marcoRestricciones").hide();
    $("#contRestricciones").hide();
    $("#msj").hide();
    $("#data").hide();
    $("#masjajax").hide();
    $("#agregar").on("click", function () {
        agregarRestriccion();
    });
});

function agregarRestriccion() {
    var cmb_lineas = $("#cmb_lineas").val();
    var prog_inicio = $("#prog_inicio").val();
    var prog_final = $("#prog_final").val();
    var vel_max_ascendente = $("#vel_max_ascendente").val();
    var vel_max_descendente = $("#vel_max_descendente").val();
    var fecha_registro = $("#fecha_registro").val();
    var observacion = $("#observacion").val();
    if ((cmb_lineas !== "") && (prog_inicio !== "") && (prog_final !== "") && (vel_max_ascendente !== "") && (vel_max_descendente !== "")) {
        $.ajax({
            url: 'AdministrarRestriccion',
            type: "POST",
            data: {accion: "Agregar", cmb_lineas: cmb_lineas, prog_inicio: prog_inicio, prog_final: prog_final, vel_max_ascendente: vel_max_ascendente, vel_max_descendente: vel_max_descendente, fecha_registro:fecha_registro,observacion:observacion},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
                $("#msj").show();
            },
            complete: function () {
                //$("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargaRestricciones($("#cmb_lineas").val());
            }
        });
    } else {
        alert("Uno de los datos es invalido");
    }
}

function editarRes() {
    var id_restriccion = $("#id_restriccion_ed").val();
    var id_linea = $("#id_linea_ed").val();
    var vel_max_ascendente = $("#vel_max_ascendente_ed").val();
    var vel_max_descendente = $("#vel_max_descendente_ed").val();
    var prog_inicio = $("#prog_inicio_ed").val();
    var prog_final = $("#prog_final_ed").val();
    var fecha_registro=$("#fecha_registro_ed").val();
    var observacion=$("#observacion_ed").val();
    if ((id_restriccion !== "") && (id_linea !== "") &&  (prog_inicio !== "") && (prog_final !== "") && (vel_max_ascendente !== "") && (vel_max_descendente !== "")) {
        $.ajax({
            url: 'AdministrarRestriccion',
            type: "POST",
            data: {accion: "Editar",  id_restriccion: id_restriccion, id_linea: id_linea, prog_inicio: prog_inicio, prog_final: prog_final, vel_max_ascendente: vel_max_ascendente, vel_max_descendente: vel_max_descendente,fecha_registro:fecha_registro,observacion:observacion},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
                $("#msj").show();
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargaRestricciones($("#cmb_lineas").val());
                cancelarRestriccion();
            }
        });
    } else {
        alert("Uno de los datos es invalido");
    }
}
function eliminarRes() {
    var id_restriccion = $("#id_restriccion_el").val();
    var id_linea = $("#id_linea_el").val();
    if ((id_restriccion !== "") && (id_linea !== "")) {
        $.ajax({
            url: 'AdministrarRestriccion',
            type: "POST",
            data: {accion: "Eliminar", id_restriccion: id_restriccion, id_linea: id_linea},
            beforeSend: function () {
                $("#msj").html(".:Esperando:.");
                $("#msj").show();
            },
            complete: function () {
                $("#msj").html(".:Listo:.");
            },
            success: function (data) {
                $("#msj").fadeOut("slow");
                $('#data').html(data);
                $('#data').show();
                cargaRestricciones($("#cmb_lineas").val());
                cancelarRestriccion();
            }
        });
    }
    else {
        alert("No se pudo eliminar");
    }
}
function cargaRestricciones(linea) {
//    $("#marcoRestricciones").load("ajax/cargaRestricciones2.jsp", {idLinea: linea});
    $(".marcoRestricciones").show();
    $("#contRestricciones").load("ajax/cargaRestricciones2.jsp", {idLinea: linea});

}
function ajaxRestriccion(id, id2, url) {
    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
            type: "POST",
            data: {idLinea: id, idRestriccion: id2},
            beforeSend: function () {
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
                $("#msjajax").fadeIn("slow");
            },
            complete: function () {
//                $("#msjajax").slideDown(500);
                $("#msjajax").fadeOut("slow");
            },
            success: function (data) {
                $('#datos').html(data);

            }
        });


    }
}

function datosDocTren(idLinea){
    $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: "ajax/datosDocTren.jsp",
            type: "POST",
            data: {idLinea:idLinea},
            beforeSend: function () {
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
                $("#msjajax").fadeIn("slow");
            },
            complete: function () {
//                $("#msjajax").slideDown(500);
                $("#msjajax").fadeOut("slow");
            },
            success: function (data) {
                $('#datos').html(data);

            }
        });


    
}
function eliminarRestriccion(id, id2) {
    ajaxRestriccion(id, id2, "ajax/eliminarRestriccion.jsp");
}

function editarRestriccion(id, id2) {
    ajaxRestriccion(id, id2, "ajax/editarRestriccion.jsp");

}
function cancelarRestriccion() {
    $('#datos').html("");
    $("#bgVentanaModal").fadeOut();
}

function cambiarNombre(id){

        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}

function generarDocTren(idLinea){
    var nota=$("#nota").val();
    var nombre=$("#nro_doc_tren").val();
    var instrucciones=$("#instrucciones").val();
    var comunicaciones=$("#comunicaciones").val();
    var precauciones=$("#precauciones").val();
    var vigencia=$("#vigencia").val();
    var materialRodante=$("#cmb_materiales").val();
   // alert($("#file").val());
    var idLinea=idLinea;
    //alert(idLinea);
     if (idLinea !== ''&&nombre!==''&&vigencia!=='') {
      //  $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: 'AdministrarRestriccion',
            type: "POST",
            data: {accion:'Generar DocTren',idLinea: idLinea,nota:nota,precauciones:precauciones,comunicaciones:comunicaciones,instrucciones:instrucciones,nombre:nombre,vigencia:vigencia,materialRodante:materialRodante},
            beforeSend: function () {
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
              //  $("#msjajax").fadeIn("slow");
            },
            complete: function () {
//                $("#msjajax").slideDown(500);
                //$("#msjajax").fadeOut("slow");
            },
            success: function (data) {
                $("#bgVentanaModal").fadeOut();
                //alert(data);
                window.open(data,'_blank');
              //   $('#datos').html("");
               
            }
        });


    }else{
        alert("Rellene los campos Nro de Documento de Tren y Vigencia");
    }
    
}