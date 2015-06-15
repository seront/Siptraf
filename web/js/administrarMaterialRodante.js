
$(document).ready(function () {
   
    $("#agregar").on("click", function () {
        agregarMaterialRodante();
    });
    
    $("#accion_el").on("click", function () {
        eliminarMR();
    });
    
    cargaTablaMatRod();   
    $('#msj').hide();
    
    //$('#marcoMR').hide();
    

});


function agregarMaterialRodante() {
    var nombre = $("#nombre").val();
    var tipo = $("#tipo").val();
    var sub_tipo = $("#sub_tipo").val();
    var numero_vagones = $("#numero_vagones").val();
    var numero_ejes = $("#numero_ejes").val();
    var capacidad_pasajeros = $("#capacidad_pasajeros").val();
    var carga_maxima = $("#carga_maxima").val();
    var velocidad_diseño = $("#velocidad_diseño").val();
    var velocidad_operacion = $("#velocidad_operacion").val();
    var masa = $("#masa").val();
    var aceleracion_maxima = $("#aceleracion_maxima").val();
    var desaceleracion_maxima = $("#desaceleracion_maxima").val();
    var desaceleracion_emergencia = $("#desaceleracion_emergencia").val();
    var unidades_remolque = $("#unidades_remolque").val();
    var unidades_motriz = $("#unidades_motriz").val();
    var longitud = $("#longitud_total").val();
    var longitud_remolque = $("#longitud_remolque").val();
    var longitud_motriz = $("#longitud_motriz").val();
    var alto_x_ancho_remolque = $("#alto_x_ancho_remolque").val();
    var alto_x_ancho_motriz = $("#alto_x_ancho_motriz").val();
    var masa_motriz = $("#masa_motriz").val();
    var capacidad_motriz = $("#capacidad_motriz").val();
    var masa_remolque = $("#masa_remolque").val();
    var capacidad_remolque = $("#capacidad_remolque").val();
    var frenado_descripcion = $("#frenado_descripcion").val();
    var voltaje=$("#voltaje").val();
    var presion_trabajo=$("#presion_trabajo").val();
    var voltaje_bateria=$("#voltaje_bateria").val();
    
    
    if ((nombre !== "")  && (tipo !== "") && (sub_tipo !== "") && (numero_vagones !== "") &&(numero_ejes !== "") && (capacidad_pasajeros !== "") && (velocidad_diseño !== "")
            && (velocidad_operacion !== "") && (carga_maxima !== "") && (masa !== "") && (aceleracion_maxima !== "") && (desaceleracion_maxima !== "")) {
        $.ajax({
            url: 'AdministrarMaterialRodante',
            type: "POST",
            data: {accion: "Agregar", nombre: nombre, tipo: tipo, sub_tipo: sub_tipo, numero_vagones: numero_vagones,
                capacidad_pasajeros: capacidad_pasajeros, velocidad_diseño: velocidad_diseño, velocidad_operacion: velocidad_operacion, masa: masa,
                aceleracion_maxima: aceleracion_maxima, desaceleracion_maxima: desaceleracion_maxima,numero_ejes:numero_ejes,carga_maxima:carga_maxima,
            desaceleracion_emergencia:desaceleracion_emergencia,unidades_remolque:unidades_remolque,unidades_motriz:unidades_motriz,longitud_total:longitud,
        longitud_remolque:longitud_remolque,longitud_motriz:longitud_motriz,alto_x_ancho_remolque:alto_x_ancho_remolque,alto_x_ancho_motriz:alto_x_ancho_motriz,
    masa_motriz:masa_motriz,masa_remolque:masa_remolque,capacidad_motriz:capacidad_motriz,capacidad_remolque:capacidad_remolque,frenado_descripcion:frenado_descripcion,
voltaje:voltaje,voltaje_bateria:voltaje_bateria,presion_trabajo:presion_trabajo},
            beforeSend: function () {
                $('#msj').show();
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $('#msj').html(data);
                cargaTablaMatRod();
            }
        });
    } else {
        alert("Falta uno o más parametros");
    }
}

function editarMR() {
    var nombre = $("#nombre_material_rodante_ed").val();
    var tipo = $("#tipo_ed").val();
    var sub_tipo = $("#sub_tipo_ed").val();
    var numero_vagones = $("#numero_vagones_ed").val();
    var capacidad_pasajeros = $("#capacidad_pasajeros_ed").val();
    var velocidad_diseño = $("#velocidad_diseño_ed").val();
    var velocidad_operacion = $("#velocidad_operacion_ed").val();
    var masa = $("#masa_ed").val();
    var aceleracion_maxima = $("#aceleracion_maxima_ed").val();
    var desaceleracion_maxima = $("#desaceleracion_maxima_ed").val();
    var id_material_rodante = $("#id_material_Rodante_ed").val();
    var numero_ejes = $("#numero_ejes_ed").val();
    var carga_maxima = $("#carga_maxima_ed").val();
    var desaceleracion_emergencia = $("#desaceleracion_emergencia_ed").val();
    var unidades_remolque = $("#unidades_remolque_ed").val();
    var unidades_motriz = $("#unidades_motriz_ed").val();
    var longitud = $("#longitud_total_ed").val();
    var longitud_remolque = $("#longitud_remolque_ed").val();
    var longitud_motriz = $("#longitud_motriz_ed").val();
    var alto_x_ancho_remolque = $("#alto_x_ancho_remolque_ed").val();
    var alto_x_ancho_motriz = $("#alto_x_ancho_motriz_ed").val();
    var masa_motriz = $("#masa_motriz_ed").val();
    var capacidad_motriz = $("#capacidad_motriz_ed").val();
    var masa_remolque = $("#masa_remolque_ed").val();
    var capacidad_remolque = $("#capacidad_remolque_ed").val();
    var frenado_descripcion = $("#frenado_descripcion_ed").val();
    var voltaje=$("#voltaje_ed").val();
    var presion_trabajo=$("#presion_trabajo_ed").val();
    var voltaje_bateria=$("#voltaje_bateria_ed").val();
    if ((nombre !== "") && (tipo !== "") && (sub_tipo !== "") && (numero_vagones !== "") && (numero_ejes !== "") && (capacidad_pasajeros !== "")
             && (velocidad_diseño !== "")
            && (velocidad_operacion !== "") && (masa !== "") && (aceleracion_maxima !== "") && (carga_maxima !== "") && (desaceleracion_maxima !== "")) {
        $.ajax({
            url: 'AdministrarMaterialRodante',
            type: "POST",
            data: {accion: "Editar", nombre: nombre, tipo: tipo, sub_tipo: sub_tipo, numero_vagones: numero_vagones,
                capacidad_pasajeros: capacidad_pasajeros, velocidad_diseño: velocidad_diseño, velocidad_operacion: velocidad_operacion, masa: masa,
                aceleracion_maxima: aceleracion_maxima, desaceleracion_maxima: desaceleracion_maxima,
                id_material_rodante: id_material_rodante,numero_ejes:numero_ejes,carga_maxima:carga_maxima,
            desaceleracion_emergencia:desaceleracion_emergencia,unidades_remolque:unidades_remolque,unidades_motriz:unidades_motriz,longitud_total:longitud,
        longitud_remolque:longitud_remolque,longitud_motriz:longitud_motriz,alto_x_ancho_remolque:alto_x_ancho_remolque,alto_x_ancho_motriz:alto_x_ancho_motriz,
    masa_motriz:masa_motriz,masa_remolque:masa_remolque,capacidad_motriz:capacidad_motriz,capacidad_remolque:capacidad_remolque,frenado_descripcion:frenado_descripcion,
voltaje:voltaje,voltaje_bateria:voltaje_bateria,presion_trabajo:presion_trabajo},
            beforeSend: function () {
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
                $("#msj").show();
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html(data);
                cargaTablaMatRod();
                cancelarMaterialRodante();
            }
        });
    } else {
        alert("Faltan un parametros");
    }
}

function eliminarMR() {
    var id_material_rodante = $("#id_material_rodante_el").val();
    if (id_material_rodante !== "") {
        $.ajax({
            url: 'AdministrarMaterialRodante',
            type: "POST",
            data: {accion: "Eliminar", id_material_rodante: id_material_rodante},
            beforeSend: function () {
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
                $("#msj").show();
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html(data);
                cargaTablaMatRod();
                cancelarMaterialRodante();
            }
        });
    } else {
        alert("Ha ocurrido un error");
    }
}

function cargaTablaMatRod() {
    $("#marcoMR").load("ajax/cargaTablaMatRod.jsp");
}

function eliminarMaterialRodante(id) {
    ajaxMaterialRodante(id, "ajax/eliminarMaterialRodante.jsp");
}

function editarMaterialRodante(id) {
    ajaxMaterialRodante(id, "ajax/editarMaterialRodante.jsp");
}

function ajaxMaterialRodante(id, url) {
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

function cancelarMaterialRodante() {
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