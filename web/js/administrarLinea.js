$(document).ready(function () {
    $("#btn_agr_lin").on('click', function (evento) {
        evento.preventDefault();

        agregarLinea();
    });
   
cargarTablaLineas();
    $("#msj").hide();

});

function cargarTablaLineas() {
    $.ajax({
        url: 'ajax/cargaTablaLineas.jsp',
        type: "POST",
        data: {},
        beforeSend: function () {
            $('#tablaLineas').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            $('#car2').fadeOut("slow");
        },
        success: function (data) {
            $('#tablaLineas').html(data);
//            $("#btn_agr_lin").on('hover', function (evento) {
//        evento.preventDefault();
//
//        agregarLinea();
//    });
            
        }
    });

}

function agregarLinea() {
    var nombre_linea = $("#txt_nom_lin").val();
    var pk_inicial = $("#num_prog_ini").val();
    var pk_final = $("#num_prog_fin").val();
    var trocha = $("#num_tro").val();
    var tipoVia = $("#tipo_via").val();
    var velocidad_linea = $("#velocidad_linea").val();
    if ((nombre_linea !== "") && (velocidad_linea !== "") && (pk_inicial !== "") && (pk_final !== "") && (trocha !== "")) {
        $.ajax({
            url: 'AdministrarLinea',
            type: "POST",
            data: {accion: "Agregar", nombre_linea: nombre_linea, pk_inicial: pk_inicial, pk_final: pk_final, trocha: trocha,tipo_via:tipoVia, velocidad_linea:velocidad_linea},
            beforeSend: function () {
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
                $("#msj").fadeIn("slow");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                
                $("#msj").html(data);
                cargarTablaLineas();
            }
        });
    } else {
        alert("Faltan datos para agregar la línea");
    }
}
function eliminarL(id) {
    if (id !== '') {
        $.ajax({
            url: 'AdministrarLinea',
            type: "POST",
            data: {accion: 'Eliminar', nombre_linea: id},
            beforeSend: function () {
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
                $("#msj").show();
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargarTablaLineas();
                cancelarLinea();
            }
        });
    }
}
function editarL() {
    var id_linea = $("#hdd_id_linea_ed").val();
    var nombre_linea = $("#txt_nom_lin_ed").val();
    var pk_inicial = $("#num_prog_ini_ed").val();
    var pk_final = $("#num_prog_fin_ed").val();
    var trocha = $("#num_tro_ed").val();
    var tipo_via = $("#tipo_via_ed").val();
     var velocidad_linea = $("#velocidad_linea_ed").val();
    if ((id_linea !== "") && (velocidad_linea !== "") && (nombre_linea !== "") && (pk_inicial !== "") && (pk_final !== "") && (trocha !== "")) {
        $.ajax({
            url: 'AdministrarLinea',
            type: "POST",
            data: {accion: 'Editar', id_linea: id_linea, nombre_linea: nombre_linea, pk_inicial: pk_inicial, pk_final: pk_final, trocha: trocha,tipo_via:tipo_via,velocidad_linea:velocidad_linea},
            beforeSend: function () {
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
                $("#msj").fadeIn("slow");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargarTablaLineas();
                cancelarLinea();

            }
        });
    } else {
        alert("Faltan parametros para editar la linea");
    }

}
function ajaxLinea(id, url) {
    
    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
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
function eliminarLinea(id) {
    ajaxLinea(id, "ajax/eliminarLinea.jsp");
}

function editarLinea(id) {
    ajaxLinea(id, "ajax/editarLinea.jsp");

}
function cancelarLinea() {
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

