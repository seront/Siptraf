//Inyeccion de dependencias angular
//angular.module('afluApp', ['ngSanitize'])
angular.module('afluApp', [])
        .controller("afluAppCtrl", ['$http', '$rootScope', controladorPrincipal]);
//esta función es mi controlador    
function controladorPrincipal($http, $sce, $rootScope) {

    var scope = this;
    //Angular, Función para aplicar metodos desde fuera de angular y reconozca los cambios
    scope.safeApply = function (fn) {
        var phase = $rootScope.$$phase;
        if (phase === '$apply' || phase === '$digest') {
            if (fn && (typeof (fn) === 'function')) {
                fn();
            }
        } else {
            $rootScope.$apply(fn);
        }
    };

    scope.armarFechaInicial = function () {
        if (scope.fechaInicial !== undefined) {
            scope.fechaFinal = new Date(scope.fechaInicial.getTime() + (7 * 24 * 60 * 60 * 1000));
            cargarTablaAfluencia(scope, $http);
        }
    };
    scope.armarRango = function () {
        if (scope.horaInicial !== null && scope.horaInicial !== "" && scope.horaInicial !== undefined) {
            scope.rango = "Desde " + scope.horaInicial + ":00 a " + (scope.horaInicial + 1) + ":00";
        } else {
            scope.rango = "";
        }
    };
    scope.cargarTablaAfluencia = function () {
        cargarTablaAfluencia(scope, $http);
    };

    scope.cargarEstaciones = function () {
        cargarEstaciones(scope, $http);
    };
    scope.agregarAfluencia = function () {
        agregarAfluencia(scope, $http);
    };
    scope.fechaInicial = new Date();
    scope.fechaFinal = new Date(scope.fechaInicial.getTime() + (7 * 24 * 60 * 60 * 1000));
    scope.consolidado = false;
    encabezadoTablaAfluencia(scope);
    scope.filasTablaAfluencia = [];

    scope.preEditarAfluencia = function (fecha, afluencia) {
        scope.cantidad = afluencia;
        scope.fechaInicial = fecha;
        scope.horaInicial = fecha.getHours();
    };
}
;
function cargarEstaciones(scope, $http) {
    if (scope.cmb_lineas !== "") {
        var config = {params: {accion: "cargarEstaciones", id_linea: parseInt(scope.cmb_lineas)}, responseType: "json"};
        var llamada = $http.post("AdministrarAfluencia", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.estaciones = respuesta;
            scope.estacionActual = scope.estaciones[0];
            cargarTablaAfluencia(scope, $http);
        });
    } else {
        scope.estaciones = "";
    }
}
;

function agregarAfluencia(scope, $http) {
    if (scope.cantidad >= 0) {
        var fechaHoraInicial = new Date(scope.fechaInicial);
        scope.fechaHoraInicial = Date.UTC(scope.fechaInicial.getFullYear(), scope.fechaInicial.getMonth(), scope.fechaInicial.getUTCDate(), scope.horaInicial, 0, 0);
        scope.fechaHoraFinal = Date.UTC(scope.fechaInicial.getUTCFullYear(), scope.fechaInicial.getUTCMonth(), fechaHoraInicial.getUTCDate(), scope.horaInicial, 59, 0);
        console.log("Agregando afluencia");
        console.log("fechaHoraInicial");
        console.log(scope.fechaHoraInicial);
        console.log(new Date(scope.fechaHoraInicial));
        console.log("fechaHoraFinal");
        console.log(scope.fechaHoraFinal);
        console.log(new Date(scope.fechaHoraFinal));
        var afluenciaPK = {
            idLinea: parseInt(scope.cmb_lineas),
            idFechaInicial: scope.fechaHoraInicial,
            idPkEstacion: scope.estacionActual.estacionPK.idPkEstacion
        };
        var afluencia = {
            afluenciaPK: afluenciaPK,
            afluencia: scope.cantidad,
            consolidado: scope.consolidado,
            fechaFinal: scope.fechaHoraFinal
        };

        var config = {params: {accion: "crearAfluencia", afluencia: afluencia}, responseType: "json"};
        var llamada = $http.post("AdministrarAfluencia", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            cargarTablaAfluencia(scope, $http);
            
        });
        scope.horaInicial+=1;
        scope.armarRango();
    }
}

function encabezadoTablaAfluencia(scope) {
    scope.celdasEncabezado = [];
    var dateFormat = '%H:%M';

    var hora = 0;
    var dia = hora * 60 * 60 * 1000;
    var celda = {
        desde: "00:00\n00:59",
        fecha: dia
    };
    scope.celdasEncabezado.push(celda);
    for (var i = 0; i < 23; i++) {
        celda = {};
        hora += 1;
        dia = hora * 60 * 60 * 1000;
        celda.desde = Highcharts.dateFormat(dateFormat, dia) + "\n";
        celda.fecha = dia;
        dia += 59 * 60 * 1000;
        celda.fecha = dia;
        celda.desde += Highcharts.dateFormat(dateFormat, dia);
        scope.celdasEncabezado.push(celda);
    }
}

function asignarInicioSemana(dia, diaSemana) {
    switch (diaSemana) {
        case 0:
//            dia += 1;
            dia -= 6;
            break;
        case 1:
//            dia = 1;
            break;
        case 2:
            dia -= 1;
            break;
        case 3:
            dia -= 2;
            break;
        case 4:
            dia -= 3;
            break;
        case 5:
            dia -= 4;
            break;
        case 6:
            dia -= 5;
            break;
    }
    return dia;
}

function cargarTablaAfluencia(scope, $http) {
    
    var hr1 = new Date(scope.fechaInicial.getFullYear(), scope.fechaInicial.getMonth(), asignarInicioSemana(scope.fechaInicial.getUTCDate(), scope.fechaInicial.getUTCDay()), 0, 0, 0);
    var fechaMinima = Date.UTC(scope.fechaInicial.getFullYear(), scope.fechaInicial.getMonth(), asignarInicioSemana(scope.fechaInicial.getUTCDate(), scope.fechaInicial.getUTCDay()), 0, 0, 0);
    var fechaMaxima = Date.UTC(scope.fechaInicial.getFullYear(), scope.fechaInicial.getMonth(), hr1.getUTCDate() + 6, 23, 59, 0);
    var idLinea = parseInt(scope.cmb_lineas);
    var idPkEstacion = scope.estacionActual.estacionPK.idPkEstacion;
    var config = {params: {
            accion: "cargarTablaAfluencia",
            idLinea: idLinea,
            idPkEstacion: idPkEstacion,
            fechaMinima: fechaMinima,
            fechaMaxima: fechaMaxima
        },
        responseType: "json"};
    var llamada = $http.post("AdministrarAfluencia", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        console.log(respuesta);
        {
            scope.afluencias = respuesta;
            var dateFormat = '%e/%m/%y';
            scope.filasTablaAfluencia = [];
            var fila = {
                dia: "",
                fecha: "",
                afluencias: []
            };
            //Dias
            for (var i = 0; i < respuesta.length; i++) {
                fila.fecha = Highcharts.dateFormat(dateFormat, respuesta[i][0].afluenciaPK.idFechaInicial);
                var dia = new Date(respuesta[i][0].afluenciaPK.idFechaInicial);

                switch (dia.getUTCDay()) {
                    case 0:
                        fila.dia = "Domingo";
                        break;
                    case 1:
                        fila.dia = "Lunes";
                        break;
                    case 2:
                        fila.dia = "Martes";
                        break;
                    case 3:
                        fila.dia = "Miércoles";
                        break;
                    case 4:
                        fila.dia = "Jueves";
                        break;
                    case 5:
                        fila.dia = "Viernes";
                        break;
                    case 6:
                        fila.dia = "Sabado";
                        break;
                }

                var k = 0;
                var celda = {
                    afluencia: "-",
                    fecha: "-"
                };
                for (var j = 0; j < scope.celdasEncabezado.length; j++) {
                    if (k < respuesta[i].length) {
                        var idFechaInicial = new Date(respuesta[i][k].afluenciaPK.idFechaInicial);
                        var fechaCelda = new Date(scope.celdasEncabezado[j].fecha + (4 * 60 * 60 * 1000));

                        if (fechaCelda.getHours() === idFechaInicial.getUTCHours()) {
                            celda.afluencia = respuesta[i][k].afluencia;
                            celda.fecha = idFechaInicial;
                            fila.afluencias.push(celda);
                            k++;
                        } else {
                            fila.afluencias.push(celda);
                        }
                    } else {
                        fila.afluencias.push(celda);
                    }
                    celda = {
                        afluencia: "-",
                        fecha: "-"
                    };
                }
                console.log("k "+k);
                scope.filasTablaAfluencia.push(fila);
                fila = {
                    dia: "",
                    fecha: "",
                    afluencias: []
                };
            }
        }
    });
}
