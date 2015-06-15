$(document).ready(function () {
    //alert("comenzando");
    $("#btn_agr_curv").on("click", function () {
        agregarCurva($("#cmb_materiales").val(), $("#txt_vel_mat_rod").val(), $("#txt_esf_tra").val(), $("#txt_esf_fre").val());
    });
    $("#cmb_materiales").on("change", function (evento) {
//        alert("comenzando");
        cargaTablaCurvas($("#cmb_materiales").val());
        cargaGrafico($("#cmb_materiales").val());
    });
    $(".cargando").hide();
    $("#tablaCurvas").hide();
    $("#msj").hide();
});
function cargaGrafico(idMatRod) {
    
    if (idMatRod !== "") {
//alert(idMatRod);
        $.ajax({
            url: 'AdministrarCurvas',
            type: "POST",
            data: {accion: 'Grafico', idMatRod: idMatRod},
            beforeSend: function () {
                $("#graficoEsfuerzos").show();
                $("#graficoEsfuerzos").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/> ");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
//                alert(data);
                $("#graficoEsfuerzos").html("");
                
                $("#graficoEsfuerzosVar").html(data);
                graficoCurvasEsfuesfuerzo();
            }
        });
    }
}
function cargaTablaCurvas(idMatRod) {
    if (idMatRod !== "") {
        $.ajax({
            url: 'ajax/cargaTablaCurvas.jsp',
            type: "POST",
            data: {idMatRod: idMatRod},
            beforeSend: function () {
                $("#tablaCurvas").show();
                $("#tablaCurvas").html("<img id='car2' class='cargando2' src='img/cargando2.gif'/> ");
            },
            complete: function () {

            },
            success: function (data) {
                $("#car2").fadeOut("600");
                $('#tablaCurvas').html(data);
                graficoCurvasEsfuesfuerzo();
            }});
    }
}

function agregarCurva(idMatRod, vel, esfTrac, esfFre) {
    if ((idMatRod !== '') && (vel !== "") && (esfTrac !== "")) {
      //  alert("agregando");
        $.ajax({
            url: 'AdministrarCurvas',
            type: "POST",
            data: {accion: 'Agregar', idMatRod: idMatRod, vel: vel, esfTrac: esfTrac, esfFre: esfFre},
            beforeSend: function () {
                $("#msj").show();
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/> ");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargaTablaCurvas($("#cmb_materiales").val());
                cargaGrafico($("#cmb_materiales").val());
            }
        });
    } else {
        alert("Datos invalidos");
    }
}
function eliminarC() {
    eliminoCurva($("#id_mat_rod_el").val(), $("#id_vel_el").val());
    cancelarCurva();
}
function eliminarCurva(id, id2) {

    ajaxCurvas(id, id2, "ajax/eliminarCurvas.jsp");
}
function eliminoCurva(idMatRod, vel) {
    if ((idMatRod !== "") && (vel !== "")) {
        $.ajax({
            url: 'AdministrarCurvas',
            type: "POST",
            data: {accion: 'Eliminar', idMatRod: idMatRod, vel: vel},
            beforeSend: function () {
                $("#msj").show();
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/> ");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
                cargaTablaCurvas($("#cmb_materiales").val());
                cargaGrafico($("#cmb_materiales").val());
            }
        });
    } else {
        alert("Datos invalidos");
    }
}
function editarCurva(id, id2) {

    ajaxCurvas(id, id2, "ajax/editarCurvas.jsp");
}

function editarC() {
    var idMatRod = $("#id_mat_rod_ed").val();
    var idVel = $("#id_vel_ed").val();
    var esfTrac = $("#txt_esf_tra_ed").val();
    var esfFren = $("#txt_esf_fre_ed").val();
    if ((idMatRod !== "") && (idVel !== "") && (esfTrac !== "")) {
        $.ajax({
            url: 'AdministrarCurvas',
            type: "POST",
            data: {accion: 'Editar', idMatRod: idMatRod, idVel: idVel, esfTrac: esfTrac, esfFren: esfFren},
            beforeSend: function () {
                $("#msj").show();
                $("#msj").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/> ");
            },
            complete: function () {
                $("#car1").fadeOut("slow");
            },
            success: function (data) {
                $("#msj").html("");
                $("#msj").html(data);
//                alert(esfFren);
                cargaTablaCurvas($("#cmb_materiales").val());
                cargaGrafico($("#cmb_materiales").val());
                cancelarCurva();

            }
        });
    } else {
        alert("Datos invalidos");
    }
}

function cancelarCurva() {
    $("#bgVentanaModal").fadeOut();
    $('#datos').html("");
}

function ajaxCurvas(id, id2, url) {

    var msjEspera = "...:: Consultando::..";

    if (id !== '') {
        $("#datos").html("<img id='car3' class='cargando2' src='img/cargando2.gif'/> ");
        $("#bgVentanaModal").fadeIn();

        $.ajax({
            url: url,
            type: "POST",
            data: {idMatRod: id, vel: id2},
            beforeSend: function () {
//                $("#msjajax").html(msjEspera);
//                $("#msjajax").slideUp(500);
            },
            complete: function () {
//                $("#msjajax").slideDown(500);
//                $("#car3").fadeOut("slow");
            },
            success: function (data) {
                $("#car3").fadeOut("slow");
                $('#datos').html(data);
//                $('#datos').show();
            }
        });
    }
}


function graficoCurvasEsfuesfuerzo() {

//                var velocidades=$("#velocidades").val();
//                var traccion=$("#traccion").val();
//                var frenado=$("#frenado").val();
//                var velArray=[];
//                var tracArray=[];
//                var frenArray=[];
//                velArray=velocidades.split(",");
//                tracArray=traccion.split(",");
//                frenArray=frenado.split(",");
//                alert(velArray);
//                var vel=[];
//                var trac=[];
//                var fren=[];
//                for(i=0; i<velArray.length; i++){
//                    vel[i]=parseFloat(velArray[i]);
//                }
//                for(i=0; i<tracArray.length; i++){
//                    trac[i]=parseFloat(tracArray[i]);
//                }
//                for(i=0; i<frenArray.length; i++){
//                    fren[i]=parseFloat(frenArray[i]);
//                }
//                alert(vel);
//               
   // alert("SI");
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficoEsfuerzos', // Le doy el nombre a la gráfica
            defaultSeriesType: 'line'	// Pongo que tipo de gráfica es
        },
        title: {
            text: 'Grafica de Traccion y Frenado'	// Titulo (Opcional)
        },
        subtitle: {
            text: 'S.I.P.T.R.A.F'		// Subtitulo (Opcional)
        },
        // Pongo los datos en el eje de las 'X'
        xAxis: {
//            categories: vel,

            
                    // Pongo el título para el eje de las 'X'
                    title: {
                        text: 'Velocidad (Km/h)'
                    }
        },
        yAxis: {
            // Pongo el título para el eje de las 'Y'
            title: {
                text: 'Esfuerzo KgF'
            }

        },
        // Doy formato al la "cajita" que sale al pasar el ratón por encima de la gráfica
        tooltip: {
            enabled: true,
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                        this.x + ': ' + this.y + ' ' + this.series.name;
            }
        },
        // Doy opciones a la gráfica
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            }
        },
        // Doy los datos de la gráfica para dibujarlas
        series: [{
                name: 'Curva de Traccion',
                data: trac
            },
            {
                name: 'Curva de Frenado',
                data: fren
            }
        ]
    });





}
function cambiarNombre(id){

        document.getElementById(id).value='Editar';
   
    
    
    
}

function valorInicial(id){
    var id=id;
         document.getElementById(id).value=id;   
}

