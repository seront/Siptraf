$(document).ready(function () {
    $(".cargando").hide();
    $(".cargando2").hide();

    $("#cmb_lineas").on("change", function (evento) {
        cargaEstaciones($("#cmb_lineas").val());
        $("#estacionesIntermediasMT").html("");
        $(".marcoRestricciones").hide();
    });
    $("#velocidad").on("change", function (evento) {
       // cargaEstaciones($("#cmb_lineas").val());
        $(".marcoRestricciones").hide();
    });
     



    $("#resultadoMarchaTipo").css('display', 'none');
    $(".marcoRestricciones").hide();
    cargarTablaMarchaTipo();
});


            function continuar() {
                if (($("#cmb_lineas").val() !== "") && ($("#velocidad").val() > 0) && ($("#cmb_est_final").val() !== "") && ($("#cmb_est_inicio").val() !== "")) {
                    cargaRestricciones($("#cmb_lineas").val(), $("#velocidad").val());
                } else {
                    alert("Uno De Los Datos Ingresados Es Invalido");
                }
            };
function cargarTablaMarchaTipo() {
    $.ajax({
        url: 'ajax/cargarTablaMarchaTipo.jsp',
        type: "POST",
        data: {},
        beforeSend: function () {
            $('#tablaMarchaTipo').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            $('#car2').fadeOut("slow");
        },
        success: function (data) {
            $('#tablaMarchaTipo').html(data);
        }
    });

}
function cargaEstaciones(idLinea) {
    $.ajax({
        url: 'ajax/cargaEstaciones.jsp',
        type: "POST",
        data: {idLinea: idLinea},
        beforeSend: function () {
            $("#car1").fadeIn("slow");
        },
        complete: function () {
            $("#car1").fadeOut("slow");
        },
        success: function (data) {
            $('#estaciones').html(data);
            $("#cmb_est_final").on("change", function (evento) {
        $(".marcoRestricciones").hide();
       // alert($("#cmb_est_inicio").val() +$("#cmb_est_final").val()+ $("#cmb_lineas").val());
        cargarEstacionesIntermedias($("#cmb_est_inicio").val(),$("#cmb_est_final").val(),$("#cmb_lineas").val());
    });
     $("#cmb_est_inicio").on("change", function (evento) {
        $(".marcoRestricciones").hide();
         cargarEstacionesIntermedias($("#cmb_est_inicio").val(),$("#cmb_est_final").val(),$("#cmb_lineas").val());
    });
        }});
}

function cargarEstacionesIntermedias(progEstInicio,progEstFinal,idLinea){
     //alert("entra");
     if(progEstInicio!==""&&progEstFinal!==""&&idLinea!==""){
 $.ajax({
        url: 'AdministrarEstacion',
        type: "POST",
        data: {accion:"Estaciones Intermedias",idLinea:idLinea,progEstacionInicial:progEstInicio,progEstacionFinal:progEstFinal},
        beforeSend: function () {
           // $('#tablaLineas').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            //$('#car2').fadeOut("slow");
        },
        success: function (data) {
            //variablesParaGraficos(idMarchaTipo,sentido);
           $("#estacionesIntermediasMT").html("");
           //alert(data);
            $("#estacionesIntermediasMT").html(data);
            //location.href='visualizarMarchaTipo.jsp?idMarchaTipo='+idMarchaTipo+'';
            
        }
    });
     }
    
}


function cargaRestricciones(linea, velocidad) {
    var progEstacionInicial = $("#cmb_est_inicio").val();
    var progEstacionFinal = $("#cmb_est_final").val();
    if (progEstacionInicial !== progEstacionFinal) {
        $.ajax({
            url: 'ajax/cargaRestricciones.jsp',
            type: "POST",
            data: {idLinea: linea, progEstacionInicial: progEstacionInicial, progEstacionFinal: progEstacionFinal, velMax: velocidad},
            beforeSend: function () {
                $("#rmt").show();
                $("#car2").fadeIn("slow");
            },
            complete: function () {
                $("#car2").fadeOut("slow");
            },
            success: function (data) {
                $("#contMarTip").css('float', 'left');
                $("#contMarTip").css('margin', '0px 20px 0px 20px');
                $("#contRestricciones").css('display', 'inline-block');
                $("#contRestricciones").html(data);
            }
        });
    } else {
        alert("Las estaciones de inicio y fin no pueden ser iguales");
    }
}

function simular() {
    var nombre = $("#nombre_marcha_tipo").val();
    var idLinea = $("#cmb_lineas").val();
    var vel = $("#velocidad").val();
    var materialRodante = $("#cmb_materiales").val();
    var estInicial = $("#cmb_est_inicio").val();
    var estFinal = $("#cmb_est_final").val();
    var restricciones = "";
    var estacionesConParada = "";
    var atp= $("#atp").is(':checked');
    var cargaMarcha= $("#carga_marcha").val();
    
    var sentido;
    if(estInicial<estFinal){
        sentido=true;
        
    }else{
        
        sentido=false;
    }
    
    $(".incluir").each(function (i, ele) {
        var chk = $(ele).is(':checked');
        var valor = $(ele).val();
        if (chk === true && (valor !== '')) {
            restricciones += valor + " ";
        }
    });
    
    $(".incluirEstacion").each(function (i, ele) {
        var chk = $(ele).is(':checked');
        var valor = $(ele).val();
        if (chk === true && (valor !== '')) {
            estacionesConParada += valor + " ";
        }
    });
    if (estFinal !== estInicial) {
        if ((idLinea !== "") && (cargaMarcha !== "") && (vel !== "") && (materialRodante !== "") && (estFinal !== "") && (estInicial !== "")) {
            //alert(atp);
            $.ajax({
                url: 'MarchaTipo',
                type: "POST",
                data: {accion: "Simular", nombre_marcha_tipo: nombre, idLinea: idLinea, vel: vel, materialRodante: materialRodante, progInicial: estInicial, progFinal: estFinal, restricciones: restricciones, atp:atp, carga_marcha:cargaMarcha,estacionesConParada:estacionesConParada},
                beforeSend: function () {
                    $("#resultadoMarchaTipo").css('display', 'block');
                    $('#msjMT').html("");
                    $("#car3").fadeIn("slow");
                },
                complete: function () {
                    // alert(data);
                },
                success: function (data) {
                    $("#car3").fadeOut("slow");
                    $('#msjMT').html(data);
                   // alert(sentido);
                    graficarMarchaTipo(sentido);
                    graficarTiempoVelocidad();
                }
            });
        } else {
            alert("Uno de los valores no es correcto");
        }
    } else {
        alert("Las estaciones de inicio y fin no pueden ser iguales");
    }
}
function eliminoMarchaTipo(id) {
    ajaxMarchaTipo(id, "ajax/eliminarMarchaTipo.jsp");
   // alert(id);
}
function ajaxMarchaTipo(id, url) {

    var msjEspera = "...:: Consultando Marcha Tipo::..";

    if (id !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: url,
            type: "POST",
            
            data: {idMarchaTipo: id},
            
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
function cancelarMarchaTipo() {
    $("#bgVentanaModal").fadeOut();
    $('#datos').html("");
}
function eliminarMarchaTipo(){
    var idMarchaTipo=$("#id_marcha_tipo").val();
    var ocultar=$("#ocultar").val()
    if(idMarchaTipo!==""){
         $.ajax({
                url: 'MarchaTipo',
                type: "POST",
                data: {accion: "Eliminar", idMarchaTipo: idMarchaTipo},
                beforeSend: function () {
                    $("#resultadoMarchaTipo").css('display', 'block');
                    $('#msjMT').html("");
                    $("#car3").fadeIn("slow");
                },
                    
                complete: function () {
                    $("#car1").fadeOut("slow");
                },
                success: function (data) {
                    $("#car3").fadeOut("slow");
                    $('#msjMT').html(data);
                   $("#eliminarMT").hide();
                   $("#agregarMT").hide();
                   $("#preguntaMT").hide();
                    cargarTablaMarchaTipo();
                    cancelarMarchaTipo();
                     $("#marchaSeleccionada").html("");
                     $("#graficoMarchaTipo").html("");
                     $("#graficoMarchaTipoTiempoVelocidad").html("");
                     
                    
                   
                   
                   
                }
            });
        
    }else{
        
       alert("No se Pudo Eliminar La Marcha Tipo");
    }
    
    
    
}

function agregarMarchaTipo(){
    alert("Marcha Tipo Agregada Satisfactoriamente");
    $("#eliminarMT").hide();
    $("#agregarMT").hide();
    $("#preguntaMT").hide();
    
}

function visualizarMarchaTipo(idMarchaTipo,sentido){
    var idMarchaTipo=idMarchaTipo;
    var sentido=sentido;
 $.ajax({
        url: 'ajax/cargaDatosMarchaTipo.jsp',
        type: "POST",
        data: {idMarchaTipo:idMarchaTipo},
        beforeSend: function () {
           // $('#tablaLineas').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            //$('#car2').fadeOut("slow");
        },
        success: function (data) {
            variablesParaGraficos(idMarchaTipo,sentido);
           
            $("#marchaSeleccionada").html(data);
            //location.href='visualizarMarchaTipo.jsp?idMarchaTipo='+idMarchaTipo+'';
            
        }
    });
    
}

function variablesParaGraficos(idMarchaTipo,sentido) {
    var idMarchaTipo=idMarchaTipo;
    var sentido=sentido;
  
    $.ajax({
        url: 'MarchaTipo',
        type: "POST",
        data: {accion:"Generar Variables",idMarchaTipo:idMarchaTipo},
        beforeSend: function () {
           // $('#tablaLineas').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            //$('#car2').fadeOut("slow");
        },
        success: function (data) {
            $("#varGraficos").html(data);
             graficarMarchaTipoAD(sentido);
            graficarTiempoVelocidad();
//            $("#graficoMarchaTipo").c
         
            //variablesParaGraficos(idMarchaTipo);
            //location.href='visualizarMarchaTipo.jsp?idMarchaTipo='+idMarchaTipo+'';
            
        }
    });
}

function graficarMarchaTipo(sentido) {
   
    
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficoMarchaTipo',
            type: 'area'
        },
        title: {
            text: 'Gráfico Marcha Tipo'
        },
        subtitle: {
            text: 'Gerencia de Gestión de Tráfico (I.F.E)'
        },
        xAxis: {
            reversed: !sentido,
            title: {
                text: 'Progresiva'
            }
        },
        yAxis: {
            title: {
                text: 'Velocidad'
            },
            labels: {
                formatter: function () {
                    return this.value;
                }
            }
        },
        tooltip: {
            shared: true,
            valueSuffix: ' Km/h'
        },
        plotOptions: {
            area: {
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    enabled: false
                }
            }
        },
        series: [{
                name: 'Velocidad',
                data: arr2,
            }]
    });
}

function graficarMarchaTipoAD(sentido) {
  
    
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficoMarchaTipo',
            type: 'area'
        },
        title: {
            text: 'Gráfico Marcha Tipo'
        },
        subtitle: {
            text: 'Gerencia de Gestión de Tráfico (I.F.E)'
        },
        xAxis: {
            reversed: !sentido1,
            title: {
                text: 'Progresiva'
            }
        },
        yAxis: {
            title: {
                text: 'Velocidad'
            },
            labels: {
                formatter: function () {
                    return this.value;
                }
            }
        },
        tooltip: {
            shared: true,
            valueSuffix: ' Km/h'
        },
        plotOptions: {
            area: {
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    enabled: false
                }
            }
        },
        series: [{
                name: 'Velocidad',
                data: arr2,
            }]
    });
}

function graficarTiempoVelocidad() {

    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficoMarchaTipoTiempoVelocidad',
            type: 'line'
        },
        title: {
            text: 'Gráfico Marcha Tipo'
        },
        subtitle: {
            text: 'Gerencia de Gestión de Tráfico (I.F.E)'
        },
        xAxis: {
            type: 'datetime',
            title: {
                text: 'Tiempo (min)'
            }
        },
        yAxis: {
            title: {
                text: 'Velocidad'
            },
            labels: {
                formatter: function () {
                    return this.value;
                }
            }
        },
        tooltip: {
            enabled: true,
            formatter: function () {
                var fecha = new Date(this.x);
                var horas = fecha.getUTCHours();
                var minutos = fecha.getUTCMinutes();
                var segundos = fecha.getUTCSeconds();
                return '(' + horas + ':' + minutos + ':' + segundos + ') ' + ': ' + this.y;
            }
        },
        plotOptions: {
            area: {
                stacking: 'normal',
                lineColor: '#666666',
                lineWidth: 1,
                marker: {
                    lineWidth: 1,
                    lineColor: '#666666'
                }
            }
        },
        series: [{
                name: 'Tiempo vs Velocidad',
                data: tiempos,
                color: 'blue'
            } ]
    });
}

function editarTiempoAdicional(idMT,idPkEstacion){
    var msjEspera = "...:: Consultando Marcha Tipo::..";

    if (idMT !== '') {
        $("#bgVentanaModal").fadeIn();
        $.ajax({
            url: "ajax/editarMarchaTipo.jsp",
            type: "POST",
            
            data: {idMarchaTipo: idMT,idPkEstacion:idPkEstacion},
            
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
function editarMarchaTipo(idMarchaTipo,idPkEstacion,sentido){
    var minutoAdicional=$("#num_tiempo_adicional_ed").val();
    
    $.ajax({
        url: 'MarchaTipo',
        type: "POST",
        data: {accion:"Editar",idMarchaTipo:idMarchaTipo,idPkEstacion:idPkEstacion,minutoAdicional:minutoAdicional},
        beforeSend: function () {
           // $('#tablaLineas').html("<img id='car2' class='cargando2' src='img/cargando2.gif'/>");
        },
        complete: function () {
            //$('#car2').fadeOut("slow");
        },
        success: function (data) {
          $('#msjMT').html(data);
            cancelarMarchaTipo();
            visualizarMarchaTipo(idMarchaTipo,sentido);
            
            
        }
    });
}

function editarTiempoMinimo(idMarchaTipo){
    var msjEspera = "...:: Consultando Marcha Tipo::..";
    $("#bgVentanaModal").fadeIn();
    $.ajax({
        
        url: 'ajax/editarTiempoMinimoMT.jsp',
        type: "POST",
        data: {idMarchaTipo:idMarchaTipo},
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

function editarTiempoMinimoMT(idMarchaTipo){
    var minutoAdicional=$("#num_tiempo_minimo_ed").val();
    
    $.ajax({
        url: 'MarchaTipo',
        type: "POST",
        data: {accion:"Editar Tiempo Minimo",idMarchaTipo:idMarchaTipo,minutoAdicional:minutoAdicional},
        beforeSend: function () {
           $("#resultadoMarchaTipo").css('display', 'block');
                    $('#msjMT').html("");
                    $("#car3").fadeIn("slow");
        },
        complete: function () {
            $('#car3').fadeOut("slow");
        },
        success: function (data) {
          $('#msjMT').html(data);
            cancelarMarchaTipo();
            cargarTablaMarchaTipo();
            
            
        }
    });
}
