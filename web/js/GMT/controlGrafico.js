// Grafico
var chart;
// Objeto linea, no debe cambiar
var linea = {};
var prefijo;
//var estaciones = {};
var estacionesLinea = {};
// Variable del sentido, depende de los botones que se seleccionen
var sentido;
// Objetos marcha tipo predeterminada
var mT_pred_asc = {};
var tiempos_pred_asc = {};
var tiempo_parada_pred_asc = [];
var colorAscPre;

var mT_pred_desc = {};
var tiempos_pred_desc = {};
var tiempo_parada_pred_desc = [];
var colorDescPre;

var marchaTipoDif = {};
var tiempos_dif = {};
$(document).ready(function () {
    cargarMarchasAsc();
    cargaCamposTiemParaAsc();
    cargarMarchasDesc();
    cargaCamposTiemParaDesc();

    $("#cmb_lineas").on("change", function () {
        cargarMarchasAsc();
        cargaCamposTiemParaAsc();
        cargarMarchasDesc();
        cargaCamposTiemParaDesc();
    });

    $("#btn_continuar").on("click", function () {
        cargarAreaTrabajo();
    });
    cargarNumeracion();
    $("input[name='tipo_numeracion']").on("change", function () {
        cargarNumeracion();
    });
});

function dibujarGrafico() {
    chart = new Highcharts.Chart({
        chart: {
            renderTo: 'graficaLineal', // Le doy el nombre a la gráfica
            defaultSeriesType: 'line'	// Pongo que tipo de gráfica es
//            ,zoomType: 'x' intentando sar un plugin q no sirve para highcharts sino para highstock
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
//                    enabled: false
                    enabled: true
                },
                events: {
                    dblclick: function () {
//                        alert('dbclick on serie');
//                        editar();
                    }
                },
                point: {
                    events: {
                        dblclick: function () {
//                            alert('dbclick on pto: '+this.series.name);
                            editar(this.series);
                        }
                    }
                }
            },
            line: {
                dataLabels: {
                    enabled: true,
                    formatter: function () {
                        var fecha = new Date(this.x);
//                        var idSerie = this.series.options.id;

                        var nombreSerie = this.series.name;
                        var minutos = fecha.getUTCMinutes();
                        var horaYMin = fecha.getUTCHours() + ":" + minutos;
                        var circulacionSerie = this.series;
                        var data = circulacionSerie.data;
                        var fechaPriPto = new Date(data[0].x);
                        var salida;
                        var horaYMinPriPto = fechaPriPto.getUTCHours() + ":" + fechaPriPto.getUTCMinutes();
                        if (horaYMinPriPto === horaYMin) {
//                            salida = idSerie + " :" + minutos;
                            salida = nombreSerie + " :" + minutos;
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
}
;
function zoom(rango) {
    var valor = rango + '%';
    $('#graficaLineal').css('width', valor);
    $('#graficaLineal').highcharts().reflow();
}

function editar(circulacion) {
    var circulacionSerie = circulacion;
    var data = circulacionSerie.data;
    var valores = "";
    for (var i = 0; i < data.length; i++) {
        var fecha = new Date(data[i].x);
        var horas = fecha.getUTCHours();
        var minutos = fecha.getUTCMinutes();
        valores += horas + ":" + minutos + ", " + data[i].y + "\n";
    }
    alert("Circulacion: " + circulacionSerie.name + "\n" + valores);
}
function insertarViaje(sent) {
    var idMT;
    var nomMT;
    var marchaTipo;
    if (sent === true) {
        idMT = mT_pred_asc.idMarchaTipo;
        nomMT = mT_pred_asc.nombreMarchaTipo;
        marchaTipo = mT_pred_asc;
    } else {
        idMT = mT_pred_desc.idMarchaTipo;
        nomMT = mT_pred_desc.nombreMarchaTipo;
        marchaTipo = mT_pred_desc;
    }
    console.log("INSERTAR VIAJE");
    console.log("idMT=" + idMT);
    console.log("nomMT=" + nomMT);
    $("#bgVentanaModal").fadeIn();
    $.ajax({
        url: 'ajax/GMT/agregarCirculacion.jsp',
        type: "POST",
        data: {id_linea: linea.idLinea, idMT: idMT, nomMT: nomMT, sentido:sent},
        fail: function () {
            alert("Error en la funcion AJAX 'insertarViaje'");
        },
        beforeSend: function () {
            $("#msjajax").html("<img id='car1' class='cargando2' src='img/cargando2.gif'/>");
            $('#msjajax').show();
        },
        success: function (data) {
            $("#msjajax").html("");
            $("#msjajax").html(data);
            agregarCamposTiempoParada($("#cmb_mar_tip_asc_ag").val(), $("#cmb_est_salida").val(), $("#cmb_est_llegada").val());
            $("#car1").hide();
            $("#btn_agr_cir").on("click", function () {
                agregarCirculacion(marchaTipo, idMT);
            });

            $(".cmb_est").on("change", function () {
                if (($("#cmb_est_salida").val() === $("#cmb_est_llegada").val())
                        ||(($("#hdd_sentido_ag").val()==="true")&&(parseFloat($("#cmb_est_salida").val()) > parseFloat($("#cmb_est_llegada").val()))) 
                        ||(($("#hdd_sentido_ag").val()==="false")&&(parseFloat($("#cmb_est_salida").val()) < parseFloat($("#cmb_est_llegada").val())))) {
                    alert("Combinacion no válida");
                    $("#btn_agr_cir").attr("disabled", true);
                } else {
                    agregarCamposTiempoParada($("#cmb_mar_tip_asc_ag").val(), $("#cmb_est_salida").val(), $("#cmb_est_llegada").val());
                    $("#btn_agr_cir").removeAttr("disabled");
                }
            });

            $("#btn_cam_MT").on("click", function () {
                cambiarMarchaTipo();
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

//function escogerMarcha(marchaTipoPredeterminada, idMT, horaInicio) {
function escogerMarcha(marchaTipoPredeterminada, horaInicio) {
//    var preCirculacion = {};
    /*preCirculacion={
     * marchaTipo={},
     * tramos=[], 
     * paradas=[], 
     * estaciones=[]
     * };*/
    console.log("marchaTipo: " + marchaTipoPredeterminada.nombreMarchaTipo + " ID: " + marchaTipoPredeterminada.idMarchaTipo);
    var otraMT = $("#cmb_mar_tip_asc_ag").val();
    if (parseInt(otraMT) !== marchaTipoPredeterminada.idMarchaTipo) {
        console.log("La marchatipo Diferente");
        marchaTipoDiferente(otraMT, horaInicio, $("#cmb_est_salida").val(), $("#cmb_est_llegada").val());
        console.log("Ya hizo la conexion y devolvio lo q se necesitaba");
    } else {
        marchaTipoIgual(marchaTipoPredeterminada, horaInicio);
    }
}

function marchaTipoIgual(marchaTipo, horaInicio) {
    var paradas = [];
    var tramos = [];
    var estaciones = [];
    var cambiarParadas = false;
    console.log(" marchatipo que se definio x defecto ");
    if (marchaTipo.sentido === true) {
        console.log("Agregar Circulacion ASCENDENTE");
        for (var y = 1; y < tiempos_pred_asc.length; y++) {
            if (tiempos_pred_asc[y].idPkEstacion > parseFloat($("#cmb_est_salida").val()) && tiempos_pred_asc[y].idPkEstacion <= parseFloat($("#cmb_est_llegada").val())) {
                var hora = new Date(tiempos_pred_asc[y].tiempoAsimilado);
                tramos[tramos.length] = hora.getUTCMinutes();
            }
        }
        for (y = 0; y < estacionesLinea.length; y++) {
//            if(parseDouble($("#cmb_est_salida").val())>=estacionesLinea[y].estacionPK.idPkEstacion &&parseDouble($("#cmb_est_llegada").val())<=estacionesLinea[y].estacionPK.idPkEstacion){
            if (estacionesLinea[y].estacionPK.idPkEstacion >= parseFloat($("#cmb_est_salida").val()) && estacionesLinea[y].estacionPK.idPkEstacion <= parseFloat($("#cmb_est_llegada").val())) {
                estaciones[estaciones.length] = estacionesLinea[y].estacionPK.idPkEstacion;
                console.log(estacionesLinea[y].estacionPK.idPkEstacion);
            }
        }
    } else {
        console.log("Agregar Circulacion DESCENDENTE");
        tiempos_pred_desc.reverse();
        for (var y = 1; y < tiempos_pred_desc.length; y++) {
//            if (tiempos_pred_desc[y].idPkEstacion > parseFloat($("#cmb_est_salida").val()) && tiempos_pred_desc[y].idPkEstacion <= parseFloat($("#cmb_est_llegada").val())) {
            if (parseFloat($("#cmb_est_salida").val()) > tiempos_pred_desc[y].idPkEstacion && parseFloat($("#cmb_est_llegada").val())<= tiempos_pred_desc[y].idPkEstacion) {
            var hora = new Date(tiempos_pred_desc[y].tiempoAsimilado);
            tramos[tramos.length] = hora.getUTCMinutes();
            }
        }
        tiempos_pred_desc.reverse();
        estacionesLinea.reverse();
        for (y = 0; y < estacionesLinea.length; y++) {
            if (parseFloat($("#cmb_est_salida").val()) >= estacionesLinea[y].estacionPK.idPkEstacion && parseFloat($("#cmb_est_llegada").val()) <= estacionesLinea[y].estacionPK.idPkEstacion) {
            estaciones[estaciones.length] = estacionesLinea[y].estacionPK.idPkEstacion;
            console.log(estacionesLinea[y].estacionPK.idPkEstacion);
        }
    }
        estacionesLinea.reverse();
    }
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
    if (paradas === marchaTipo.tiempo_parada_pred || cambiarParadas === true) {
        paradas = marchaTipo.tiempo_parada_pred;
        console.log(marchaTipo.tiempo_parada_pred);
    }
    console.log("Minutos de PARADA: " + paradas);

    console.log("Tiempo tramos " + tramos);
    console.log("Estaciones " + estaciones);
    console.log("Color " + marchaTipo.color);

    preCirculacion = {
        marchaTipo: marchaTipo,
        tramos: tramos,
        paradas: paradas,
        estaciones: estaciones
    };
    calculosCirc(preCirculacion, horaInicio);
    asignarNumCirculacion();
    cancelar();
}
function marchaTipoDiferente(otraMT, horaInicio, salida, llegada) {
    var paradas = [];
    var tramos = [];
    var estaciones = [];
    var cambiarParadas = false;
    $.ajax({
        url: 'GMT',
        type: "POST",
        dataType: 'JSON',
        data: {accion: "cambiarMT", idMT: otraMT, idLinea: linea.idLinea, cmb_est_salida: salida, cmb_est_llegada: llegada},
        fail: function () {
            alert("Error en la traccion AJAX 'escogerMarcha'");
        },
        success: function (data) {
            console.log(data);
            marchaTipoDif = data[0];
            if (sentido === true) {
                marchaTipoDif.tiempo_parada_pred = tiempo_parada_pred_asc;
            } else {
                marchaTipoDif.tiempo_parada_pred = tiempo_parada_pred_desc;
            }
            marchaTipoDif.sentido = sentido;
            marchaTipoDif.color = $("#color_ag_dif").val();
            tiempos_dif = data[1];
            console.log(marchaTipoDif);
            console.log(tiempos_dif);
            marchaTipo = marchaTipoDif;

            if (marchaTipo.sentido === true) {
                console.log("Agregar Circulacion ASCENDENTE");
                for (var y = 1; y < tiempos_dif.length; y++) {
                    var hora = new Date(tiempos_dif[y].tiempoAsimilado);
                    tramos[tramos.length] = hora.getUTCMinutes();
                }
                for (y = 0; y < estacionesLinea.length; y++) {
                    if (estacionesLinea[y].estacionPK.idPkEstacion >= tiempos_dif[0].idPkEstacion && estacionesLinea[y].estacionPK.idPkEstacion <= tiempos_dif[tiempos_dif.length - 1].idPkEstacion) {
                        estaciones[estaciones.length] = estacionesLinea[y].estacionPK.idPkEstacion;
                        console.log(estacionesLinea[y].estacionPK.idPkEstacion);
                    }
                }
            } else {
                console.log("Agregar Circulacion DESCENDENTE");
                tiempos_dif.reverse();
                for (var y = 1; y < tiempos_dif.length; y++) {
                    var hora = new Date(tiempos_dif[y].tiempoAsimilado);
                    tramos[tramos.length] = hora.getUTCMinutes();
                }
                tiempos_dif.reverse();
                estacionesLinea.reverse();
                for (y = 0; y < estacionesLinea.length; y++) {
                    estaciones[estaciones.length] = estacionesLinea[y].estacionPK.idPkEstacion;
                    console.log(estacionesLinea[y].estacionPK.idPkEstacion);
                }
                estacionesLinea.reverse();
            }

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
            if (paradas === marchaTipo.tiempo_parada_pred || cambiarParadas === true) {
                paradas = marchaTipo.tiempo_parada_pred;
                console.log(marchaTipo.tiempo_parada_pred);
            }
            console.log("Minutos de PARADA: " + paradas);

            console.log("Tiempo tramos " + tramos);
            console.log("Estaciones " + estaciones);
            console.log("Color " + marchaTipo.color);

            preCirculacion = {
                marchaTipo: marchaTipo,
                tramos: tramos,
                paradas: paradas,
                estaciones: estaciones
            };
            calculosCirc(preCirculacion, horaInicio);
            asignarNumCirculacion();
            cancelar();
        }
    });
}
function calculosCirc(preCirculacion, hm) {

    console.log("Tiempo tramos " + preCirculacion.tramos);
    console.log("Estaciones " + preCirculacion.estaciones);
    console.log("Color " + preCirculacion.marchaTipo.color);
    var salida = [];
    //Ingreso la hora de inicio
    var fechaUTC = Date.UTC(2015, 1, 18, hm[0], hm[1], 0);
    var par = [];
    par[0] = fechaUTC;
    par[1] = preCirculacion.estaciones[0];
    salida[0] = par;
    var j = 1;
    for (var i = 1; i < preCirculacion.estaciones.length; i++) {
        console.log("Entrando al for " + i);
        var parAnt = [];
        parAnt = salida[salida.length - 1];
        var fechaAnt = new Date(parAnt[0]);
        var horaAnt = fechaAnt.getUTCHours();
        var minAnt = fechaAnt.getUTCMinutes();
        var min;
        var hora;
        console.log("(minAnt + tramos[i - 1]) " + (minAnt + preCirculacion.tramos[i - 1]));
        if ((minAnt + preCirculacion.tramos[i - 1]) >= 60) {
            console.log("Mayor que 60 ");
            min = minAnt + preCirculacion.tramos[i - 1] - 60;
            console.log("min " + min);
            hora = horaAnt + 1;
            console.log("hora " + hora);
        } else {
            min = minAnt + preCirculacion.tramos[i - 1];
            console.log("tramos[i - 1] " + preCirculacion.tramos[i - 1]);
            console.log("minAnt " + minAnt);
            console.log("min " + min);

            hora = horaAnt;
            console.log("hora " + hora);
        }
        if (hora < 24) {
            if (i !== (preCirculacion.estaciones.length - 1)) {
                console.log("TRAMO");
                console.log("Sumar tramo agregar Hora: " + hora + " minutos: " + min);
                var fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                var par = [];
                par[0] = fechaUTC;
                par[1] = preCirculacion.estaciones[i];
                salida[j++] = par;
                console.log("PARADA");
                console.log("(minAnt + paradas[i - 1]) " + (minAnt + preCirculacion.paradas[i - 1]));
                if ((min + preCirculacion.paradas[i - 1]) >= 60) {
                    console.log("Mayor que 60");
                    console.log("paradas[i - 1] " + preCirculacion.paradas[i - 1]);
                    console.log("min " + min);
                    min = min + preCirculacion.paradas[i - 1] - 60;
                    hora = hora + 1;
                    console.log("hora " + hora);

                    if (hora >= 24) {
                        alert("No se puede agregar la circulación");
                        return;
                    }
                } else {
                    console.log("paradas[i - 1] " + preCirculacion.paradas[i - 1]);
                    console.log("min " + min);
                    min = (min + preCirculacion.paradas[i - 1]);
                }
                console.log("Sumar parada agregar Hora: " + hora + " minutos: " + min);
                fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                par = [];
                par[0] = fechaUTC;
                par[1] = preCirculacion.estaciones[i];
                salida[j++] = par;
            } else {
                console.log("Sumar Final agregar Hora: " + hora + " minutos: " + min);
                var fechaUTC = Date.UTC(2015, 1, 18, hora, min, 0);
                var par = [];
                par[0] = fechaUTC;
                par[1] = preCirculacion.estaciones[i];
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
        data: salida,
        sentido: preCirculacion.marchaTipo.sentido,
        color: preCirculacion.marchaTipo.color
    };
    chart.addSeries(serie);
}

function agregarCirculacion(marchaTipoPredeterminada, idMT) {
    var patron = /((0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){1})/;
    var horaInicio = $("#txt_hora_ini").val();
    if (patron.test(horaInicio) === true) {
        escogerMarcha(marchaTipoPredeterminada, horaInicio.split(":"));
    } else {
        alert("Alguno de los valores introducidos es incorrecto");
        return;
    }
}

function usarMTDiferente(idMT, nombreOtraMT) {
    if (idMT !== "") {
        $.ajax({
            url: 'GMT',
            type: "POST",
            dataType: 'JSON',
            data: {accion: "cambiarMT", idMT: idMT, idLinea: linea.idLinea},
            fail: function () {
                alert("Error en la traccion AJAX 'usarMTDiferente'");
            },
            success: function (data) {
                console.log(data);
                marchaTipoDif = data[0];
                if (sentido === true) {
                    marchaTipoDif.tiempo_parada_pred = tiempo_parada_pred_asc;
                } else {
                    marchaTipoDif.tiempo_parada_pred = tiempo_parada_pred_desc;
                }
                marchaTipoDif.sentido = sentido;
                tiempos_dif = data[1];
                console.log(marchaTipoDif);
                console.log(tiempos_dif);
                return marchaTipoDif;
            }
        });
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
                $("#cmb_mar_tip_asc").append(data);
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
                $("#cmb_mar_tip_desc").append(data);
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
    var tipoNumeracion = $("input:radio[name='tipo_numeracion']:checked").val();
    console.log("Radio buttons tipo numeracion " + tipoNumeracion);
    $.ajax({
        url: "ajax/GMT/cargaNumeracion.jsp",
        type: "POST",
        data: {tipoNumeracion: tipoNumeracion},
        fail: function () {
            alert("Error en la traccion AJAX 'cargarNumeracion'");
        },
        success: function (data) {
            $("#grp_select_num").html(data);
            $(".prefijoNumeracion").on("change", function () {
                prefijo = "";
//                linea.prefijo = "";
                $(".prefijoNumeracion").each(function (i, ele) {
                    var valor = $(ele).val();
                    prefijo += valor;
                });
                $("#pref_nume").html("Prefijo de la numeracion: " + prefijo);
            });
        }
    });
}

function cargarAreaTrabajo() {
    console.log("Cargar area de trabajo");
    var idMarAsc = $("#cmb_mar_tip_asc").val();
    var idMarDesc = $("#cmb_mar_tip_desc").val();
    var idLinea = $("#cmb_lineas").val();
    var nomMTpredAsc = $("#cmb_mar_tip_asc option:selected").text();
    var nomMTpredDesc = $("#cmb_mar_tip_desc option:selected").text();
    colorAscPre = $("#color_asc_pre").val();
    colorDescPre = $("#color_desc_pre").val();
    console.log("Nombre MT ASC: " + nomMTpredAsc);
    console.log("Nombre MT DESC: " + nomMTpredDesc);

    $(".estacionesAsc").each(function (i, ele) {
        var valor = $(ele).val();
        tiempo_parada_pred_asc[tiempo_parada_pred_asc.length] = parseInt(valor);
    });

    $(".estacionesDesc").each(function (i, ele) {
        var valor = $(ele).val();
        tiempo_parada_pred_desc[tiempo_parada_pred_desc.length] = parseInt(valor);
    });

    if ((idMarAsc !== "") && (idMarDesc !== "")) {
        $.ajax({
            url: "ajax/GMT/GMT.jsp",
            type: "POST",
            success: function (data) {
                $("#area_trabajo").html(data);
                dibujarGrafico();
                console.log("Metodo cargar datos trabajo: ");
                cargarDatosTrabajo(idLinea, idMarAsc, idMarDesc);
            },
            fail: function () {
                alert("Error en la transaccion AJAX 'cargarAreaTrabajo'");
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
        dataType: 'JSON',
        data: {accion: 'datosIniciales', id_linea: id_linea, id_MT_asc_pre: id_MT_asc_pre, id_MT_desc_pre: id_MT_desc_pre},
        success: function (data) {
            console.log("*************Aqui va json*************");
            /** Orden de la respuesta recibida:
             * data[0]=Objeto linea
             * data[1]=Array de objetos "Estacion"
             * data[2]=Objeto MarchaTipo Ascendente
             * data[3]=Array de objetos TiempoParadaMarchaTipo Ascendente
             * data[4]=Objeto MarchaTipo Descendente
             * data[5]=Array de objetos TiempoParadaMarchaTipo Descendente
             */
            linea = data[0];
            linea.prefijo = prefijo;
            estacionesLinea = data[1];
            mT_pred_asc = data[2];
            mT_pred_asc.sentido = true;
            mT_pred_asc.color = colorAscPre;
            mT_pred_asc.tiempo_parada_pred = tiempo_parada_pred_asc;
            tiempos_pred_asc = data[3];
            mT_pred_desc = data[4];
            mT_pred_desc.sentido = false;
            mT_pred_desc.color = colorDescPre;
            mT_pred_desc.tiempo_parada_pred = tiempo_parada_pred_desc;
            tiempos_pred_desc = data[5];
        },
        fail: function () {
            alert("Error en la transaccion AJAX 'cargarDatosTrabajo'");
        }
    });
}
function cambiarMarchaTipo() {
    var r = confirm("¿Cambiar marcha tipo?");
    var url;
    console.log(sentido);
    if (sentido === true) {
        url = "ajax/GMT/cargaMarchasAsc.jsp";
    } else {
        url = "ajax/GMT/cargaMarchasDesc.jsp";
    }
    if (r === true) {
        console.log("Cambiando la marcha");
        $.ajax({
            url: url,
            type: "POST",
            data: {id_linea: linea.idLinea},
            success: function (data) {
                $("#cmb_mar_tip_asc_ag").html(data);
                $("#cmb_mar_tip_asc_ag").removeAttr("disabled");
                $("#cmb_mar_tip_asc_ag").on("change", function () {
                    alert("Cargando nuevas estaciones de salida y llegada");
                    cargarEstParadaAsc($("#cmb_mar_tip_asc_ag").val());
                    cargarEstParadaDesc($("#cmb_mar_tip_asc_ag").val());
                });
                $("#color_chooser").html("<input type='color' class='campoFormulario' id='color_ag_dif' title='Color de la nueva circulación'>");
            },
            fail: function () {
                alert("Error en la transacción 'cambiarMarchaTipo'");
            }
        });
    } else {
        console.log("Continuar con la misma marcha");
    }
}

function cargarEstParadaAsc(idMT) {
    $.ajax({
        url: "ajax/GMT/cargaEstSalida.jsp",
        type: "POST",
        data: {id_marcha_tipo: idMT},
        beforeSend: function () {
            $("#car1").show();
        },
        complete: function () {
            $("#car1").hide();
        },
        success: function (data) {
            $("#cmb_est_salida").html(data);
        }
    });
}
function cargarEstParadaDesc(idMT) {
    $.ajax({
        url: "ajax/GMT/cargaEstLlegada.jsp",
        type: "POST",
        data: {id_marcha_tipo: idMT},
        success: function (data) {
            $("#cmb_est_llegada").html(data);
        }
    });
}

function ordenarCirculaciones() {
    console.log("Ordenando circulaciones...");
    chart.series.sort(function (a, b) {
        for (var i = 0, max = a.data.length; i < max; i++) {
            for (var j = 0, max = b.data.length; j < max; j++) {
                if (a.data[i].y === b.data[j].y) {
                    return a.data[i].x - b.data[j].x;
                }
            }
        }
    });
    console.log("Listo...");
}

function asignarNumCirculacion() {
    ordenarCirculaciones();
    var contadorAscendente = 0;
    var contadorDescendente = 1;
    var final = "";

    for (var i = 0; i < chart.series.length; i++) {
        if (chart.series[i].options.sentido === true) {
            contadorAscendente += 2;
            if (contadorAscendente < 10) {
                final = "00" + contadorAscendente;
            } else if (contadorAscendente >= 10 && contadorAscendente < 100) {
                final = "0" + contadorAscendente;
            } else {
                final = contadorAscendente;
            }
        } else {
            if (contadorDescendente < 10) {
                final = "00" + contadorDescendente;
            } else if (contadorDescendente >= 10 && contadorDescendente < 100) {
                final = "0" + contadorDescendente;
            } else {
                final = contadorDescendente;
            }
            contadorDescendente += 2;
        }
        chart.series[i].name = linea.prefijo + final;
        chart.series[i].redraw();
    }
}
function generaTabla() {
    $("#tablaCirculaciones").html(chart.getTable());
}

(function (Highcharts) {
    Highcharts.Chart.prototype.getTable =
            Highcharts.Chart.prototype.getTable =
            function ordenarFilas() {
                var dateFormat = '%H:%M';
                var filasAsc = [];
                var filasDesc = [];
                console.log("Tamaño del array de las series: " + chart.series.length);
                for (var i = 0; i < chart.series.length; i++) {
                    var fila = {};
                    console.log("Valor de i del primer for: " + i);
                    //Sentido ASCENDENTE
                    if (chart.series[i].options.sentido === true) {
                        fila.numeroCirculacion = chart.series[i].name;
                        console.log(fila.numeroCirculacion);
                        fila.horaEnEstacion = [];
                        fila.progEstacion = [];
                        for (var j = 0; j < estacionesLinea.length; j++) {
                            for (var k = 0; k < chart.series[i].data.length; k++) {
                                if (estacionesLinea[j].estacionPK.idPkEstacion === chart.series[i].data[k].y) {
                                    console.log(chart.series[i].data[k].x);
                                    fila.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, chart.series[i].data[k].x));
                                    fila.progEstacion.push(chart.series[i].data[k].y);
                                }
                            }
                        }
                        filasAsc.push(fila);
                        console.log("Agregado a Filas: " + filasAsc.length);
                    } else {
                        //Sentido DESCENDENTE
                        fila.numeroCirculacion = chart.series[i].name;
                        console.log(fila.numeroCirculacion);
                        fila.horaEnEstacion = [];
                        fila.progEstacion = [];
                        estacionesLinea.reverse();
                        for (var j = 0; j < estacionesLinea.length; j++) {
                            for (var k = 0; k < chart.series[i].data.length; k++) {
                                if (estacionesLinea[j].estacionPK.idPkEstacion === chart.series[i].data[k].y) {
                                    console.log(chart.series[i].data[k].x);
                                    fila.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, chart.series[i].data[k].x));
                                    fila.progEstacion.push(chart.series[i].data[k].y);
                                }
                            }
                        }
                        estacionesLinea.reverse();
                        filasDesc.push(fila);
                    }
                }

                var salida = "</br><table class='tablas'>";
                salida += "<tr>";
                salida += "<td>";
                salida += "Nº Circulación";
                salida += "</td>";
                for (i = 0; i < estacionesLinea.length; i++) {
                    if (i === 0 || i === estacionesLinea.length - 1) {
                        salida += "<td>";
                    } else {
                        salida += "<td colspan='2'>";
                    }
                    salida += estacionesLinea[i].nombreEstacion;
                    salida += "</td>";
                }
                salida += "</tr>";
                if (filasAsc.length > 0) {
                    console.log("Cant de filas ASC a imprimir: " + filasAsc.length);
                    for (i = 0; i < filasAsc.length; i++) {
                        console.log("Imprimiendo fila " + i);
                        salida += "<tr>";
                        salida += "<td>";
                        salida += filasAsc[i].numeroCirculacion;
                        salida += "</td>";
                        var entra = true;
                        for (j = 0; j < filasAsc[i].horaEnEstacion.length; j++) {

                            for (var n = 0; n < estacionesLinea.length; n++) {
                                console.log("FILA progEstacion " + filasAsc[i].progEstacion[j] + " idPkEstacion " + estacionesLinea[n].estacionPK.idPkEstacion);
                                if (filasAsc[i].progEstacion[j] === estacionesLinea[n].estacionPK.idPkEstacion) {
                                    console.log("Entro en el primer if");
                                    console.log("n " + n);
                                    if (n > 0 && entra === true) {
                                        console.log("if");
                                        for (var m = 0; m < n + 1; m++) {
                                            salida += "<td></td>";
                                        }
                                        salida += "<td>";
                                        salida += filasAsc[i].horaEnEstacion[j];
                                        salida += "</td>";
                                        entra = false;
                                    } else {
                                        console.log("else");
                                        salida += "<td>";
                                        salida += filasAsc[i].horaEnEstacion[j];
                                        salida += "</td>";
                                        entra = false;
                                        break;
                                    }
                                }
                            }

                        }
                        salida += "</tr>";
                    }
                }
                salida += "</table>";

                salida += "</br><table class='tablas'>";
                salida += "<tr>";
                salida += "<td>";
                salida += "Nº Circulación";
                salida += "</td>";
                estacionesLinea.reverse();
                for (i = 0; i < estacionesLinea.length; i++) {
                    if (i === 0 || i === estacionesLinea.length - 1) {
                        salida += "<td>";
                    } else {
                        salida += "<td colspan='2'>";
                    }
                    salida += estacionesLinea[i].nombreEstacion;
                    salida += "</td>";
                }
//                estacionesLinea.reverse();
                if (filasDesc.length > 0) {
                    console.log("Cant de filas DESC a imprimir: " + filasDesc.length);
                    salida += "</tr>";
                    
                    for (i = 0; i < filasDesc.length; i++) {
                        entra = true;
                        salida += "<tr>";
                         salida += "<td>";
                        salida += filasDesc[i].numeroCirculacion;
                        salida += "</td>";
                        for (j = 0; j < filasDesc[i].horaEnEstacion.length; j++) {                           
                            for (var n = 0; n < estacionesLinea.length; n++) {
                                console.log("FILA progEstacion " + filasDesc[i].progEstacion[j] + " idPkEstacion " + estacionesLinea[n].estacionPK.idPkEstacion);
                                if (filasDesc[i].progEstacion[j] === estacionesLinea[n].estacionPK.idPkEstacion) {
                                    console.log("Entro en el primer if");
                                    console.log("n " + n);
                                    if (n > 0 && entra === true) {
                                        console.log("if");
                                        for (var m = 0; m < n + 1; m++) {
                                            salida += "<td></td>";
                                        }
                                        salida += "<td>";
                                        salida += filasDesc[i].horaEnEstacion[j];
                                        salida += "</td>";
                                        entra = false;
                                    } else {
                                        console.log("else");
                                        salida += "<td>";
                                        salida += filasDesc[i].horaEnEstacion[j];
                                        salida += "</td>";
                                        entra = false;
                                        break;
                                    }
                                }
                            }
                        }
                        salida += "</tr>";
                    }
                    estacionesLinea.reverse();
                }
                salida += "</table>";
                return salida;
            };
}(Highcharts));

function agregarCamposTiempoParada(idMT, estSalida, estLlegada) {
    //alert("Cargando contenedor estaciones");
    var est1 = parseInt(estSalida);
    var est2 = parseInt(estLlegada);
    var data = {id_marcha_tipo: parseInt(idMT)};
    data.sentido = sentido;
    data.estacion_inicio = est1;
    data.estacion_final = est2;
    console.log("agregarCamposTiempoParada data");
    console.log(data);
    $.ajax({
        url: "ajax/GMT/cargaParadas.jsp",
        type: "POST",
        data: data,
        beforeSend: function (xhr) {
            $("#car1").show();
        },
        complete: function (jqXHR, textStatus) {
            $("#car1").hide();
        },
        success: function (data) {
            $("#estaciones_intermedias").html(data);
        },
        fail: function () {
            alert("Error en la transaccion AJAX 'agregarCamposTiempoParada'");
        }
    });
}