

$(document).ready(function () {
    // Grafico
var chart;
// id de la linea, no deberia cambiar
var id_linea;
// Array con las progresivas de las estaciones en cada orden
var prog_estaciones_asc = [];
var prog_estaciones_desc = [];
// Array con los tiempos predeterminados en cada sentido
var tiempos_tramos_asc = [];
var tiempos_tramos_desc = [];
// tiempos de parada predeterminados
var tiempo_paradas = [];
// Variable del sentido, depende de los botones que se seleccionen
var sentido;
// Objeto marcha tipo para referencia, [idMarchaTipo,nombreMarchaTipo]
//var mT_pred_asc = [];
var mT_pred_asc;
var mT_pred_desc = [];
// Color predeterminado de las circulaciones en cada sentido
var color_asc_pre;
var color_desc_pre;

    $("#cmb_lineas").on("change", function () {
        cargarMarchasAsc();
        cargaCamposTiemParaAsc();
        cargarMarchasDesc();
        cargaCamposTiemParaDesc();
    });
//    $("#tipo_numeracion").on("change", function () {
//        cargarNumeracion();
//    });
    $("#btn_continuar").on("click", function () {
        cargarAreaTrabajo();
    });
    
    

function dibujarGrafico() {
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficaLineal', // Le doy el nombre a la gráfica
            defaultSeriesType: 'line'	// Pongo que tipo de gráfica es
        },
        title: {
            text: 'Grafico de movimiento de trenes'	// Titulo (Opcional)
        },
        credits: {
            text: 'Nestor Mendoza y Kelvins Insua',
            href: ''
        },
        subtitle: {
            text: 'Primeras Pruebas' // Subtitulo (Opcional)
        },
        // Pongo los datos en el eje de las 'X'
        xAxis: {
            type: 'datetime'
        },
        yAxis: {
            // Pongo el título para el eje de las 'Y'
            title: {
                text: 'Progresivas'
            }
        },
        // Doy formato al la "cajita" que sale al pasar el ratón por encima de la gráfica
        tooltip: {
            enabled: true,
            formatter: function () {
                var fecha = new Date(this.x);
                var horas = fecha.getUTCHours();
                var minutos = fecha.getUTCMinutes();
                return '(' + horas + ':' + minutos + ') ' + ': ' + this.y;
            }
        },
        legend: {
            enabled: 0
        },
        // Doy opciones a la gráfica
        plotOptions: {
            series: {
                marker: {
                    enabled: false
                }
            },
            line: {
                dataLabels: {
                    enabled: true,
                    formatter: function () {
                        var fecha = new Date(this.x);
                        var idSerie = this.series.options.id;
                        var minutos = fecha.getUTCMinutes();
                        var horaYMin = fecha.getUTCHours() + ":" + minutos;
                        var circulacionSerie = this.series;
                        var data = circulacionSerie.data;
                        var fechaPriPto = new Date(data[0].x);
                        var salida;
                        var horaYMinPriPto = fechaPriPto.getUTCHours() + ":" + fechaPriPto.getUTCMinutes();
                        if (horaYMinPriPto === horaYMin) {
                            salida = idSerie + " :" + minutos;
                        } else {
                            salida = " :" + minutos;
                        }
                        return salida;
                    },
//                                verticalAlign: alEtiq
                    verticalAlign: 'top'
                },
                enableMouseTracking: true,
                allowPointSelect: true
            }
        }
    });
    $("#rango").on("change", function () {
        zoom($("#rango").val());
    });
//});
}
;
function zoom(rango) {
    var valor = rango + '%';
    $('#graficaLineal').css('width', valor);
    $('#graficaLineal').highcharts().reflow();
}

function editar() {
    var valorPto = chart.getSelectedPoints();
    var circulacionSerie = chart.get(valorPto[0].series.options.id);
    var data = circulacionSerie.data;
    var valores = "";
    for (var i = 0; i < data.length; i++) {
        var fecha = new Date(data[i].x);
        var horas = fecha.getUTCHours();
        var minutos = fecha.getUTCMinutes();
        valores += horas + ":" + minutos + ", " + data[i].y + "\n";
    }
    //var vert = circulacionSerie.options.dataLabels.align;
    alert("Circulacion: " + circulacionSerie.options.id + "\n" + valores);
}
function insertarViaje(sent) {
    var idMT;
    var nomMT;
    if (sent === true) {
//        idMT = mT_pred_asc[0];
//        nomMT = mT_pred_asc[1];
        idMT = mT_pred_asc.id;
        nomMT = mT_pred_asc.nombre;
    } else {
        idMT = mT_pred_desc[0];
        nomMT = mT_pred_desc[1];
    }
    console.log("INSERTAR VIAJE");
    console.log("idMT=" + idMT);
    console.log("nomMT=" + nomMT);
    $("#bgVentanaModal").fadeIn();
    $.ajax({
        url: 'ajax/GMT/agregarCirculacion.jsp',
        type: "POST",
        data: {id_linea: id_linea, idMT: idMT, nomMT: nomMT, tiempo_paradas: tiempo_paradas},
        beforeSend: function () {
            $("#msjajax").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
            $('#msjajax').show();
        },
        complete: function () {
        },
        success: function (data) {
            $("#msjajax").html("");
            $("#msjajax").html(data);
            $("#btn_agr_cir").on("click", function () {
                agregarCirculacion();
            });
            $("#btn_can").on("click", function () {
                cancelar();
            });
            sentido = sent;
        }
    });
}

function cancelar() {
    $('#msjajax').html("");
    $('#datos').html("");
    $("#bgVentanaModal").fadeOut();
}

function eliminarCirculacion() {
    var punto = chart.getSelectedPoints()[0];
    var circulacionSerie = chart.get(punto.series.options.id);
    for (var i = 0; i < chart.series.length; i++) {
        if (circulacionSerie === chart.series[i]) {
            chart.series[i].remove();
        }
    }
}
function unirCirculacion() {
    var colorRuta = $("#colorRuta").val();
    var punto1 = chart.getSelectedPoints()[0];
    var punto2 = chart.getSelectedPoints()[1];
    var circulacionSerie1 = chart.get(punto1.series.options.id);
    var circulacionSerie2 = chart.get(punto2.series.options.id);
    if (circulacionSerie1 === circulacionSerie2) {
        alert("Opción invalida");
    } else {
        if ((circulacionSerie1.data[0].x > circulacionSerie2.data[0].x)) {
            alert("La primera circulacion seleccionada ocurre despues de la segunda");
        } else if (circulacionSerie1.data[circulacionSerie1.data.length - 1].y !== circulacionSerie2.data[0].y) {
            alert("Las estaciones que se intentan seleccionar son incorrectas");
        } else {
            var salida = [];
            var j = 0;
            for (var i = 0; i < circulacionSerie1.data.length; i++) {
                var fecha = new Date(circulacionSerie1.data[i].x);
                var fechaUTC = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), fecha.getUTCHours(), fecha.getUTCMinutes(), fecha.getUTCSeconds());
                var par = [];
                par[0] = fechaUTC;
                par[1] = circulacionSerie1.data[i].y;
                salida[i] = par;
                j++;
            }
            for (i = 0; i < circulacionSerie2.data.length; i++) {
                var fecha = new Date(circulacionSerie2.data[i].x);
                var fechaUTC = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), fecha.getUTCHours(), fecha.getUTCMinutes(), fecha.getUTCSeconds());
                var par = [];
                par[0] = fechaUTC;
                par[1] = circulacionSerie2.data[i].y;
                salida[i + j] = par;
            }
            for (i = 0, max = chart.series.length; i < max; i++) {
                if (circulacionSerie1 === chart.series[i]) {
                    chart.series[i].remove();
                    break;
                }
            }
            for (i = 0, max = chart.series.length; i < max; i++) {
                if (circulacionSerie2 === chart.series[i]) {
                    chart.series[i].remove();
                    break;
                }
            }
            var dat = {
                name: 'Ruta 1',
                id: 'Ruta 1',
                data: salida,
                color: colorRuta
            };
            chart.addSeries(dat);
        }
    }
}

function agregarCirculacion() {
//    alert(prog_estaciones_asc);
//    alert(tiempos_tramos_asc);
    alert(mT_pred_asc.nomPtoRef);
    alert(mT_pred_asc.tiemposParadas);
    var patron = /(0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){1}/;
    var horaInicio = $("#txt_hora_ini").val();
    if (patron.test(horaInicio) === true) {
        var hm = horaInicio.split(":");
        var paradas = [];
        var color;
//        paradas = tiempo_paradas;
        var cambiarParadas = false;
        console.log("VALORES ");
        $(".estacionesAg").each(function (i, ele) {
            var valor = $(ele).val();
            console.log(valor);
            if (valor !== "" && valor !== "0") {
                paradas[paradas.length] = parseInt(valor);
            } else {
                cambiarParadas = true;
                return false;
            }
        });
        console.log("Minutos de PARADA: " + paradas);
        if (paradas === tiempo_paradas || cambiarParadas === true) {
            paradas = tiempo_paradas;
        }
        console.log("Minutos de PARADA: " + paradas);
        var tramos = [];
        var estaciones = [];
        if (sentido === true) {
//            tramos = tiempos_tramos_asc;
//            estaciones = prog_estaciones_asc;
//            color=color_asc_pre;
//            console.log("Agregar Circulacion ASCENDENTE");
//            console.log("Tiempo tramos " + mT_pred_asc.tiempoEntrePtoRef[2] + " asc");
//            console.log("Estaciones " + mT_pred_asc.progPtoRef[1] + " asc");
//            console.log("Color " +mT_pred_asc.color + " asc");
            tramos = mT_pred_asc.tiempoEntrePtoRef;
            estaciones = mT_pred_asc.progPtoRef;
            color = mT_pred_asc.color;
            console.log("Agregar Circulacion ASCENDENTE");
            console.log("Tiempo tramos " + tramos + " asc");
            console.log("Estaciones " + estaciones + " asc");
            console.log("Color " + color + " asc");
        } else {
            tramos = tiempos_tramos_desc;
            estaciones = prog_estaciones_desc;
            color = color_desc_pre;
            console.log("Agregar Circulacion DESCENDENTE");
            console.log("Tiempo tramos " + tramos + " desc");
            console.log("Estaciones " + estaciones + " desc");
            console.log("Color " + color + " desc");
        }


//        alert(paradas);
//        var paradas = [1, 1];

        var salida = [];
        //Ingreso la hora de inicio
        var fechaUTC = Date.UTC(2015, 1, 18, hm[0], hm[1], 0);
        var par = [];
        par[0] = fechaUTC;
        par[1] = estaciones[0];
        salida[0] = par;
        var j = 1;
        for (var i = 1; i < estaciones.length; i++) {
            console.log("Entrando al for " + i);
            var parAnt = [];
            parAnt = salida[salida.length - 1];
            var fechaAnt = new Date(parAnt[0]);
            var horaAnt = fechaAnt.getUTCHours();
            var minAnt = fechaAnt.getUTCMinutes();
            var min;
            var hora;
            console.log("(minAnt + tramos[i - 1]) " + (minAnt + tramos[i - 1]));
            if ((minAnt + tramos[i - 1]) >= 60) {
                console.log("Mayor que 60 ");
                min = minAnt + tramos[i - 1] - 60;
                console.log("min " + min);
                hora = horaAnt + 1;
                console.log("hora " + hora);
            } else {
                min = minAnt + tramos[i - 1];
                console.log("tramos[i - 1] " + tramos[i - 1]);
                console.log("minAnt " + minAnt);
                console.log("min " + min);

                hora = horaAnt;
                console.log("hora " + hora);
            }
            if (hora < 24) {
                if (i !== (estaciones.length - 1)) {
                    console.log("TRAMO");
                    console.log("Sumar tramo agregar Hora: " + hora + " minutos: " + min);
                    var fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                    var par = [];
                    par[0] = fechaUTC;
                    par[1] = estaciones[i];
                    salida[j++] = par;
                    console.log("PARADA");
                    console.log("(minAnt + paradas[i - 1]) " + (minAnt + paradas[i - 1]));
                    if ((min + paradas[i - 1]) >= 60) {
                        console.log("Mayor que 60");
                        console.log("paradas[i - 1] " + paradas[i - 1]);
                        console.log("min " + min);
                        min = min + paradas[i - 1] - 60;
                        hora = hora + 1;
                        console.log("hora " + hora);
//                        alert();
                        if (hora >= 24) {
                            alert("No se puede agregar la circulación");
                            return;
                        }
                    } else {
                        console.log("paradas[i - 1] " + paradas[i - 1]);
                        console.log("min " + min);
                        min = (min + paradas[i - 1]);
                    }
                    console.log("Sumar parada agregar Hora: " + hora + " minutos: " + min);
                    fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                    par = [];
                    par[0] = fechaUTC;
                    par[1] = estaciones[i];
                    salida[j++] = par;
                } else {
                    console.log("Sumar Final agregar Hora: " + hora + " minutos: " + min);
                    var fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                    var par = [];
                    par[0] = fechaUTC;
                    par[1] = estaciones[i];
                    salida[j++] = par;
                }
            } else {
                alert("No se puede agregar la circulación");
                return;
            }
        }
        //Agregando la serie
        var serie;
        serie = {
            name: '111002',
            id: '111002',
            //sentido: 'false',
            data: salida,
            color: color
        };
        chart.addSeries(serie);
        cancelar();
    } else {
        alert("Alguno de los valores introducidos es incorrecto");
        return;
    }

}

function cargarMarchasAsc() {
    var id_linea = $("#cmb_lineas").val();
    if (id_linea !== "") {
        $.ajax({
            url: "ajax/GMT/cargaMarchasAsc.jsp",
            type: "POST",
            data: {id_linea: id_linea},
            beforeSend: function () {
                $("#img_cargando").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#img_cargando").fadeIn("slow");
            },
            complete: function () {
                $("#img_cargando").fadeOut("slow");
            },
            success: function (data) {
                $("#select_marchas_tipo_asc").html(data);
            }
        });
    }
}
function cargarMarchasDesc() {
    var id_linea = $("#cmb_lineas").val();
    if (id_linea !== "") {
        $.ajax({
            url: "ajax/GMT/cargaMarchasDesc.jsp",
            type: "POST",
            data: {id_linea: id_linea},
            beforeSend: function () {
                $("#img_cargando").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#img_cargando").fadeIn("slow");
            },
            complete: function () {
                $("#img_cargando").fadeOut("slow");
            },
            success: function (data) {
                $("#select_marchas_tipo_desc").html(data);
            }
        });
    }
}
function cargaCamposTiemParaAsc() {
    var id_linea = $("#cmb_lineas").val();
    if (id_linea !== "") {
        $.ajax({
            url: "ajax/GMT/cargaTiempoParadasAsc.jsp",
            type: "POST",
            data: {id_linea: id_linea},
            beforeSend: function () {
                $("#img_cargando").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#img_cargando").fadeIn("slow");
            },
            complete: function () {
                $("#img_cargando").fadeOut("slow");
            },
            success: function (data) {
                $("#tiem_parada_pred_asc").html(data);
            }
        });
    }
}
function cargaCamposTiemParaDesc() {
    var id_linea = $("#cmb_lineas").val();
    if (id_linea !== "") {
        $.ajax({
            url: "ajax/GMT/cargaTiempoParadasDesc.jsp",
            type: "POST",
            data: {id_linea: id_linea},
            beforeSend: function () {
                $("#img_cargando").html("<p><image class='cargando' src='img/ajax-loader.gif'/></p>");
                $("#img_cargando").fadeIn("slow");
            },
            complete: function () {
                $("#img_cargando").fadeOut("slow");
            },
            success: function (data) {
                $("#tiem_parada_pred_desc").html(data);
            }
        });
    }
}

function cargarNumeracion() {
    var tipoNumeracion = $("#tipo_numeracion").val();
    $.ajax({
        url: "ajax/GMT/cargaNumeracion.jsp",
        type: "POST",
        data: {tipoNumeracion: tipoNumeracion},
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (data) {
            $("#grp_select_num").html(data);
        }
    });
}

function cargarAreaTrabajo() {
    console.log("Cargar area de trabajo");
    var idMarAsc = $("#cmb_mar_tip_asc").val();
    var idMarDesc = $("#cmb_mar_tip_desc").val();
    var idLinea = $("#cmb_lineas").val();
    var estAsc = [];
    var estDesc = [];
    var nomMTpredAsc = $("#cmb_mar_tip_asc option:selected").text();
    var nomMTpredDesc = $("#cmb_mar_tip_desc option:selected").text();
    var colorAscPre = $("#color_asc_pre").val();
    var colorDescPre = $("#color_desc_pre").val();
    console.log("Nombre MT ASC: " + nomMTpredAsc);
    console.log("Nombre MT DESC: " + nomMTpredDesc);

    $(".estacionesAsc").each(function (i, ele) {
        var valor = $(ele).val();
        console.log("Tiempos en estaciones ASCENDENTE " + i + " valor: " + valor);
        estAsc[estAsc.length] = parseInt(valor);
    });

    $(".estacionesDesc").each(function (i, ele) {
        var valor = $(ele).val();
        console.log("Tiempos en estaciones DESCENDENTE " + i + " valor: " + valor);
        estDesc[estDesc.length] = parseInt(valor);
    });
    if ((idMarAsc !== "") && (idMarDesc !== "")) {
        $.ajax({
            url: "ajax/GMT/GMT.jsp",
            type: "POST",
            data: {},
            beforeSend: function () {

            },
            complete: function () {

            },
            success: function (data) {
                $("#area_trabajo").html(data);
                dibujarGrafico();
                
                console.log("prog_estaciones_asc: "+prog_estaciones_asc);
                console.log("tiempos_tramos_asc: "+tiempos_tramos_asc);
//                 id_MT_asc_pre = idMarAsc;
//                console.log("id_MT_asc_pre: " + id_MT_asc_pre);
//                id_MT_desc_pre = idMarDesc;
//                console.log("id_MT_desc_pre: " + id_MT_desc_pre);
                id_linea = idLinea;
                console.log("Metodo cargar datos trabajo: ");
                cargarDatosTrabajo(id_linea, idMarAsc, idMarDesc);
//                cargarDatosTrabajo(id_linea, id_MT_asc_pre, id_MT_desc_pre);
                console.log("prog_estaciones_asc: "+prog_estaciones_asc);
                console.log("tiempos_tramos_asc: "+tiempos_tramos_asc);
               
//                console.log("id_linea: " + id_linea);
                tiempo_paradas = estAsc;
//                tiempo_paradas = estDesc;

//                console.log("tiempo_paradas: " + tiempo_paradas);
                mT_pred_asc = [idMarAsc, nomMTpredAsc];
//                console.log("mT_pred_asc: " + mT_pred_asc);
                mT_pred_desc = [idMarDesc, nomMTpredDesc];
//                console.log("mT_pred_desc: " + mT_pred_desc);
                color_asc_pre = colorAscPre;
//                console.log("color_asc_pre: " + color_asc_pre);
                color_desc_pre = colorDescPre;
//                console.log("color_desc_pre: " + color_desc_pre);
                
//                cargarDatosTrabajo(id_linea, mT_pred_asc[0], mT_pred_desc[0]);

                console.log("Intentando crear e objeto marcha tipo: ");
                mT_pred_asc = {id: idMarAsc,
                    nombre: nomMTpredAsc,
                    nomPtoRef: prog_estaciones_asc,
                    progPtoRef: prog_estaciones_asc,
                    tiempoEntrePtoRef: tiempos_tramos_asc,
                    sentido: true,
                    progParadas: prog_estaciones_asc,
                    tiemposParadas: tiempo_paradas,
                    color: color_asc_pre};
                console.log(" ya lo creé, " + mT_pred_asc);
//                console.log("id: " + mT_pred_asc.id);
//                console.log("nombre: " + mT_pred_asc.nombre);
//                console.log("nomPtoRef: " + mT_pred_asc.nomPtoRef);
//                console.log("progPtoRef: " + mT_pred_asc.progPtoRef);
//                console.log("tiempoEntrePtoRef: " + mT_pred_asc.tiempoEntrePtoRef);
//                console.log("sentido: " + mT_pred_asc.sentido);
//                console.log("progParadas: " + mT_pred_asc.progParadas);
//                console.log("tiemposParadas: " + mT_pred_asc.tiemposParadas);
//                console.log("color: " + mT_pred_asc.color);
            }
        });

    } else {
        alert("Datos incorrectos");
    }
}

//Asigna los valores a las variables javascript globales con las que trabajar
function cargarDatosTrabajo(id_linea, id_MT_asc_pre, id_MT_desc_pre) {
    $.ajax({
        url: 'GMT',
        type: "POST",
        data: {accion: 'datosIniciales', id_linea: id_linea, id_MT_asc_pre: id_MT_asc_pre, id_MT_desc_pre: id_MT_desc_pre},
        beforeSend: function () {

        },
        complete: function () {

        },
        success: function (data) {
            $("#variables").html(data);
        }
    });
}



});

