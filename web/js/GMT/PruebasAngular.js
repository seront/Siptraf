//Inyeccion de dependencias angular
angular.module('gmtApp', ['ngSanitize'])
        .controller("gmtAppCtrl", ['$http', '$sce', '$rootScope', controladorPrincipal]);
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
    //Carga los combos con los que se arman los prefijos de los numeros de circulacion
    scope.cargaCombosPrefijo = function () {
        cargaCombosPrefijo(scope, $http);
    };
    //Carga los grupos de prefijos ya existentes en la base de datos
    scope.cargaGrupoPrefijos = function () {
        scope.grupoNumeraciones = [];
        cargaGruposPrefijos(scope, $http);
    };
    //se ejecuta al cargar la pagina
    scope.cargaGrupoPrefijos();
    //Actualiza el prefijo que se va ensamblando a medida que se cambian los componentes del prefijo
    scope.construyePrefijo = function () {
        cargarNumeracion(scope);
    };
    //Carga todas las marchas tipo asociadas a una linea y muestra los campos 
    //para establecer tiempos de parada por defecto en cada una de las estaciones
    // de la linea
    scope.cargarMarchas = function () {
        cargarMarchasAsc(scope, $http);
        cargarMarchasDesc(scope, $http);
        cargaCamposTiemParada(scope, $http);
    };
    //Se carga el área de trabajo principal una vez definido los parametros 
    //para una nueva programacion horaria
    scope.continuar = function () {
        cargaAreaTrabajo(scope, $http);
    };
    //Angular, se utiliza para que se pueda añadir correctamente codigo html a 
    //la pagina, si no se utiliza envia error y el codigo html no se agrega
    scope.renderHtml = function (htmlCode) {
        return $sce.trustAsHtml(htmlCode);
    };
    // Zoom de el gráfico
    scope.zoom = function () {
        var valor = scope.rango + '%';
        scope.safeApply($('#graficaLineal').css('width', valor));
        scope.chart.reflow();
    };
    //Al dar doble click sobre una circulación, esta funcion permite cambiar 
    //los parametros de la misma para poder editarla
    scope.habilitarEdicion = function () {
        scope.editando_circulacion = false;
        scope.val_btn_ag = "Aceptar";
    };
    //Agrega una circulacion nueva o borra una existente y la redibuja con 
    //los datos ingresados
    scope.agregarCirculacion = function () {
        scope.cancelar();
        if (scope.val_btn_ag === "Aceptar") {
            scope.editando_circulacion = true;
        }
        agregarCirculacion(scope, $http);
        scope.editando_circulacion = false;

    };
    //Esconde el panel lateral y cancela cualquier opcion
    scope.cancelar = function () {
        scope.sec_opc_left = {left: "-500px"};
        scope.chart.hideLoading();
        scope.esconder_cambiarNombrePH = true;
    };
    //Agrega los campos de texto necesarios para ingresar los tiempos de 
    //parada en minutos en funcion de cuales estaciones son parada 
    //para la marcha tipo y las estaciones de salida y llegada seleccionadas
    scope.agregarCamposTiempoParada = function () {
        agregarCamposTiempoParada(scope, $http);
    };
    //Configura y Despliega el panel lateral para ingresar los datos 
    //necesarios para dibujar una nueva circulación
    scope.insertarViaje = function (sentido) {
        scope.cancelar();
        scope.chart.showLoading();
        scope.chart.reflow();
        scope.esconder_numeraciones = true;
        scope.opcion_nombre = "Agregar Circulación";
        scope.val_btn_ag = "Agregar";
        scope.editando_circulacion = false;
        scope.insertando_grupo = false;
        scope.esconder_agregar = false;
        scope.habilitar_btn_agr_cir = false;
        scope.colorActual = "#000000";
        insertarViaje(sentido, scope, $http);
        scope.sec_opc_left = {left: "0px"};
    };
    //Configura y Despliega el panel lateral para ingresar los datos 
    //necesarios para dibujar un nuevo grupo de circulaciones que compartiran
    // el mismo comportamiento 
    scope.insertarGrupo = function (sentido) {
        scope.cancelar();
        scope.chart.showLoading();
        $('#graficaLineal').highcharts().reflow();
        scope.esconder_numeraciones = true;
        scope.opcion_nombre = "Agregar Grupo";
        scope.val_btn_ag = "Agregar";
        scope.editando_circulacion = false;
        scope.insertando_grupo = true;
        scope.esconder_agregar = false;
        scope.habilitar_btn_agr_cir = false;
        scope.colorActual = "#000000";
        insertarViaje(sentido, scope, $http);
        scope.sec_opc_left = {left: "0px"};
    };
    //Se activa al dar doble-click sobre alguno de los puntos de una circulación
    //los puntos iniciales y finales son menos sensibles a este comportamiento
    //a fecha de (21-05-2015) aun no he estudiado esto. seront
    scope.detallarCirculacion = function (circulacion) {//        
        scope.opcion_nombre = "Detalle de Circulación";
        scope.insertando_grupo = false;
        detallarCirculacion(scope, $http, circulacion);
    };
    //Dibuja el gráfico vacio con las configuraciones establecidas
    scope.dibujarGrafico = function () {
        scope.safeApply(dibujarGrafico(scope));
    };
    //Se ejecuta al cambiar la marcha tipo seleccionada, adapta la vista en 
    //función de esto
    scope.cambiarMarchaActual = function () {
        cambiarMarchaActual(scope);
        llenarCmbEstLlegada(scope, $http);
        llenarCmbEstSalida(scope, $http);
    };
    //Se ejecuta el presionar el boton eliminar desde el panel lateral cuando
    // se detalla una circulación o desde el boton externo, aplica tanto para 
    // eliminar tanto una circulación como para un grupo
    scope.eliminarCirculacion = function () {
        eliminarCirculacion(scope, $http);
        scope.cancelar();
    };
    //Agrega una nueva numeracion (o prefijo) a la base de datos
    scope.agregarNumeracion = function () {
        agregarNumeracion(scope, $http);
        scope.cancelar();
    };
    //Elimina la numeracion (o prefijo) seleccionada de la base de datos, solo si no es 
    //usada por ninguna preCirculacion
    scope.eliminarNumeracion = function () {
        eliminarNumeracion(scope, $http);
        scope.cancelar();
    };
    //Despliega el panel lateral para que se pueda definir un prefijo para 
    //agregarlo a la base de datos 
    scope.insertarPrefijo = function () {
        scope.cargaCombosPrefijo();
        insertarPrefijo(scope);
    };
    //Despliega el panel lateral para que se pueda elegir una prefijo
    // (o numeracion) para eliminarlo de la base de datos 
    scope.eliminarPrefijo = function () {
        eliminarPrefijo(scope);
    };
    //Aun en trabajo (21-05-2015)
    scope.imprimirGrafico = function () {
        imprimirGrafico(scope);
    };
    //Guarda todas las preCirculaciones de la circulaciones realizadas en la
    // programacion actual en la que se este trabajando
    scope.guardarProgramacion = function () {
        guardarProgramacion(scope, $http);
    };
    //Muestra el formulario que permite la creacion de una programación horaria nueva
    scope.iniciarProgramacion = function () {
        scope.esconderInicio = true;
        scope.esconderNueva = false;
    };
    //Busca todas las programaciones horarias guardadas en la BD y las carga en el select
    scope.cargarTodasProgramaciones = function () {
        cargarTodasProgramaciones(scope, $http);
    };
    //se ejecuta al cargar la pag
    scope.cargarTodasProgramaciones();
    //Carga todas las preCirculaciones de la programación seleccionada y dibuja las circulaciones
    scope.abrirProgramacion = function () {
        var accion = "abrirProgramacionHoraria";
        scope.esconder_ventanaModal = false;
        abrirProgramacionHoraria(scope, $http, accion);
        scope.esconderInicio = true;
//        cargarRutas(scope,$http);
        scope.esconder_ruta = true;
        scope.esconder_botones_ruta = true;
    };
    scope.abrirRutas = function () {

        scope.esconderHR = false;
        var accion = "abrirRutas";
        scope.rutasActivas = true;
        cargarRutas(scope, $http);
        scope.esconder_ventanaModal = false;
        abrirProgramacionHoraria(scope, $http, accion);
        scope.esconderInicio = true;
        scope.esconder_ruta = true;
        scope.esconder_botones_ruta = false;
        scope.esconder_btn_desc = true;
        scope.esconder_btn_asc = true;
    };
    //Copia la programacion horaria seleccionda y sus respectivas 
    //preCirculaciones asignandoles un nuevo id de programacion luego
    // Carga todas las preCirculaciones de la programación seleccionada y dibuja las circulaciones
    scope.abrirPlantilla = function () {
        scope.esconder_ventanaModal = false;
        var accion = "abrirProgramacionPlantilla";
        abrirProgramacionHoraria(scope, $http, accion);
        scope.esconderInicio = true;
        scope.esconder_botones_ruta = true;
    };
    //Elimina la programación horaria seleccionada y todos sus elementos de la BD
    scope.eliminarProgramacion = function () {
        eliminarProgramacionHoraria(scope, $http);
    };
    //Despliega el panel lateral con los campos necesarios para crear una ruta
    scope.adminRutas = function () {
//        scope.cancelar();
        scope.chart.showLoading();

        scope.esconder_opcionesRuta = true;
        scope.esconder_ruta = false;
        scope.esconder_botonGuardarRuta = false;
        scope.esconder_botonAgregarARuta = true;
        scope.esconder_seleccionarRuta = false;
        scope.esconder_crearRuta = false;
        scope.sec_opc_left = {left: "0px"};
    };
    scope.eliminarRuta = function () {
        eliminarRuta(scope, $http);
    };
    scope.agregarCircARuta = function () {
        scope.ruta.circulaciones.push(scope.circulacion);
    };
    scope.rutas = [];
    scope.ruta = {};
    scope.ruta.circulaciones = [];

    scope.preAgregarARuta = function () {
        preAgregarARuta(scope);
    };
    scope.agregarARuta = function () {
        agregarARuta(scope, $http);
    };
    scope.desvincularDeRuta = function () {
        desvincularDeRuta(scope, $http);
    };
    scope.crearRuta = function () {
        crearRuta(scope, $http);
    };
    scope.preCrearRuta = function () {
        preCrearRuta(scope);
    };
    //Despliega el panel lateral con los campos necesarios para cambiar el 
    //nombre de la programacion horaria en la que se esta trabajando, este 
    //nombre es el que saldra en el GMT y en la tabla horaria
    scope.preCambiarNombrePH = function () {
        scope.cancelar();
        scope.chart.showLoading();
        scope.esconder_numeraciones = true;
        scope.editando_circulacion = false;
        scope.esconder_agregar = true;
        scope.esconder_cambiarNombrePH = false;
        scope.sec_opc_left = {left: "0px"};
    };
    //Ejecuta el cambio de nombre de la programacion horaria en la BD, luego en 
    //la variable local 
    scope.cambiarNombrePH = function () {
        cambiarNombrePH(scope, $http);
    };

    scope.agregarAExistente = function () {
        agregarAExistente(scope);
    };

    //Creado el 29-05-2015
    //Modificado el 10-06-2015
    scope.preMostrarHorasEnEstacion = function () {
        scope.cancelar();
        scope.chart.showLoading();
        scope.esconder_afluencia = false;
        scope.afluencia_estacion = scope.estacionesLinea[0];
//        scope.afluencia_sentido=true;
        scope.afluencia_sentido = "true";
        scope.afluencia_fecha = new Date();
        cargarHorasAfluencia(scope, $http);

        scope.esconder_ruta = true;
        scope.editando_circulacion = false;
        scope.esconder_agregar = true;
        scope.esconder_cambiarNombrePH = true;
        scope.sec_opc_left = {left: "0px"};
    };
    //Creado el 29-05-2015
    //Modificado el 10-06-2015
    scope.cargarHorasAfluencia = function () {
        cargarHorasAfluencia(scope, $http);
    };

    /*** Valores predeterminados de la mayoria de las variables dentro del scope ***/

    //Ubicación por defecto del panel desplegable (oculto)
    scope.sec_opc_left = {left: "-500px"};
    //Array en el que se guardan los valores con los que se van armando el prefijo que se agregara
    scope.prefijo = [];
    //Nombre de la opcion activa del panel desplegable
    scope.opcion_nombre = "Agregar circulación";
    /*Valor del boton principal del panel desplegable cuando la opcion seleccionada 
     es agregar circulacion o agregar grupo de circulaciones, cuando se va a 
     editar una circulacion esta variable toma el valor de "Aceptar"*/
    scope.val_btn_ag = "Agregar";
    //Valor minimo del input utilizado para el zoom del grafico
    scope.rango = 100;
    //al parecer esta variable no es usada (21-05-2015)
//    scope.tablaCirculaciones = ""; 
    /*Establece por defecto que el prefijo a utilizar tendra la configuracion 
     * de "Cercanias" segun lo establece la NGC*/
    scope.tipo_numeracion = "false";
    /*Esconde el select "Tipo de Servicio" dependiendo de el tipo de numeracion a utilizar*/
    scope.escSer = (scope.tipo_numeracion === "true" ? true : false);
    /*Esconde el formulario utilizado para definir una nueva programacion horaria*/
    scope.esconderNueva = true;
    /* Esconde el área de trabajo principal, en el que se encuentra el grafico, 
     * los botones y el panel despleglable con las opciones*/
    scope.esconder_area_trabajo = true;
    //Esconde el formulario usado para cambiarNombrePH del panel lateral despleglable
    scope.esconder_cambiarNombrePH = true;
    //Esconde la seccion de la pag que contiene la tabla horia resultado de la programacion actual
    scope.esconderTH = true;
    //Esconde el formulario usado para agregar una nueva numeracion o prefijo
    scope.esconder_numeraciones = true;
    //Indica si actualmente se esta editando una circulación
    scope.editando_circulacion = false;
    //Esconde el formulario usado para agregar una o un grupo de circulaciones
    scope.esconder_agregar = true;
    //Esconde el fondo negro que cubre la pantalla por completo, usado cuando se hace una transaccion larga
    scope.esconder_ventanaModal = true;
    //esconde los botones guardar ruta y cancelar
    scope.esconder_boronGuardarRuta = false;
    //esconde los botones para agregar a una ruta
    scope.esconder_botonAgregarARuta = true;
    //Array que contienen los minutos en cada estacion de la linea
    scope.tiempo_parada_pred_asc = [];
    scope.tiempo_parada_pred_desc = [];
    /*Tiempo de parada en las estaciones intermedias de una circulacion, se 
     * usa para llenar esos campos de texto */
    scope.tiempo_estaciones_intermedias = [];
    /*Estacion de salida y llegada cuando se agrega o edita una circulacion o un grupo*/
    scope.est_salida = "";
    scope.est_llegada = "";
    //esconde el formulario de crear ruta
    scope.esconder_ruta = true;
    scope.esconder_botones_ruta = true;
    scope.rutasActivas = false;
    scope.esconderHR = true;
    //scope.esconder_seleccionarRuta=true;
    /*Colores que tendran por defecto las circulaciones de una programacion horaria */
    scope.color_asc_pre = "#000000";
    scope.color_desc_pre = "#000000";
    scope.colorRuta = "#000000";

    /*Color que tendra la circulación con la que actualmente se esta trabajando,
     *  tanto para agregar como para editar*/
    scope.colorActual = "#000000";
    /*Estructura basica e incompleta de un objeto preCirculacion*/
    scope.preCirculacion = {
        marchaTipo: {},
        idPreCirculacion: ""
    };
    /*Objeto tabla horaria*/
    scope.tablaHoraria = {};
    scope.tablaHoraria.filasAscendentes = [];
    scope.tablaHoraria.filasDescendentes = [];
    /* Prefijo de numeracion que se establece x defecto*/
    scope.prefijo_numeracion = scope.grupoNumeraciones[0];
    /*Estado que tendra la preCirculacion con a que se trabaja
     * 0 = sin cambios (Esta representado igual en la pag que en la BD)
     * 1 = agregado nuevo pero sin guardar en la base de datos
     * */
    scope.estado = 0;

    scope.nomRuta = "Ruta 1";
    scope.programacionActual = {};
}

function abrirProgramacionHoraria(scope, $http, accion) {
    scope.idProgramacionHoraria = scope.programacionActual.idProgramacionHoraria;
    scope.nombre_programacion = scope.programacionActual.nombreProgramacionHoraria;
    var id_programacion_horaria = scope.programacionActual.idProgramacionHoraria;
//    var config = {params: {accion: accion, id_programacion_horaria: id_programacion_horaria}, responseType: "JSON"};
    var config = {params: {
            accion: accion,
            id_programacion_horaria: id_programacion_horaria
        },
        responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (data) {
        /** Orden de la respuesta recibida:
         * data[0] = Objeto linea
         * data[1] = Array de objetos "Estacion"
         * data[2] = Objeto MarchaTipo Ascendente
         * data[3] = Array de objetos TiempoParadaMarchaTipo Ascendente
         * data[4] = Objeto MarchaTipo Descendente
         * data[5] = Array de objetos TiempoParadaMarchaTipo Descendente
         * data[6] = Milisegundos de tiempoMinimo Ascendente
         * data[7] = Milisegundos de tiempoMinimo Descendente             * 
         * data[8] = Array de precirculaciones
         * data[9] = Array de todas las marchas ascendentes
         * data[10] = Array de todas las marchas DEscendentes
         *OPCIONAL
         *  data[11] = Nuevo id de la programacion
         */
        scope.linea = data[0];
        scope.linea.prefijo = scope.prefijo;
        scope.estacionesLinea = data[1];
        scope.mT_pred_asc = data[2];
        scope.mT_pred_asc.tiempoMinimo = data[6];
        scope.mT_pred_asc.sentido = true;
        scope.mT_pred_asc.color = scope.programacionActual.colorPredAsc;
        //ESTABLECE LAS RUTAS EXISTENTES DE ESTA PH
        console.log(data[11]);
        scope.rutas = data[11];

        //Establecer el tiempo de parada predeterminado en 1
        var array = [];
        for (var i = 1; i < scope.estacionesLinea.length - 1; i++) {
            array.push(1);
        }
        scope.mT_pred_asc.tiempo_parada_pred = array;
        scope.tiempos_pred_asc = data[3];
        scope.mT_pred_desc = data[4];
        scope.mT_pred_desc.tiempoMinimo = data[7];
        scope.mT_pred_desc.sentido = false;
        scope.mT_pred_desc.color = scope.programacionActual.colorPredDesc;
        scope.mT_pred_desc.tiempo_parada_pred = array;
        scope.tiempos_pred_desc = data[5];
        scope.esconderNueva = true;
        scope.esconder_area_trabajo = false;
        scope.todas_mar_tip_asc = data[9];
        scope.todas_mar_tip_desc = data[10];
        scope.prefijo_numeracion = scope.grupoNumeraciones[0];

        scope.dibujarGrafico(scope, accion);
        scope.insertando_grupo = true;
        //Insertando las precirculciones guardadas en la base de datos
        console.log("Precirculaciones: " + data[8].length);
        for (i = 0; i < data[8].length; i++) {
            var preCirculacion = data[8][i][0];

            //SI VOY A ABRIR RUTAS LE ESTABLESCO EL COLOR DE LA RUTA A CADA CIRCULACION
            if (accion === "abrirRutas") {

//                  scope.econder_botones_ruta=false;
                var idRutaDePreCir = preCirculacion.preCirculacionPK.idRuta;
                for (var m = 0; m < scope.rutas.length; m++) {
                    if (scope.rutas[m].rutaPK.idRuta === idRutaDePreCir)
                        preCirculacion.color = scope.rutas[m].color;
                }
                scope.esconderTH = true;

            } else {
                scope.esconder_botones_ruta = true;
            }
            var seguir = true;
            for (var j = 0; j < scope.todas_mar_tip_asc.length; j++) {
                if (preCirculacion.marchaTipo === scope.todas_mar_tip_asc[j].idMarchaTipo) {
                    preCirculacion.marchaTipo = scope.todas_mar_tip_asc[j];
                    seguir = false;
                    break;
                }
            }
            if (seguir === true) {
                for (j = 0; j < scope.todas_mar_tip_desc.length; j++) {
                    if (preCirculacion.marchaTipo === scope.todas_mar_tip_desc[j].idMarchaTipo) {
                        preCirculacion.marchaTipo = scope.todas_mar_tip_desc[j];
                        break;
                    }
                }
            }

            var teps = data[8][i][1];
            if (preCirculacion.marchaTipo.sentido === false) {
                teps.reverse();
            }

            for (j = 0; j < teps.length; j++) {
                teps[j].estacion = teps[j].estacionTramoParadaPK.idEstacion;
                teps[j].tramo = teps[j].tiempo;
            }
            preCirculacion.teps = teps;
            preCirculacion.idPreCirculacion = preCirculacion.preCirculacionPK.idPreCirculacion;
            preCirculacion.horaInicio = preCirculacion.horaInicial;
            preCirculacion.est_salida = data[8][i][2][0];
            preCirculacion.est_llegada = data[8][i][2][1];

            for (j = 0; j < scope.grupoNumeraciones.length; j++) {
                if (preCirculacion.prefijoNumeracion === scope.grupoNumeraciones[j].idPrefijo) {
                    preCirculacion.prefijo_numeracion = scope.grupoNumeraciones[j];
                    break;
                }
            }
            preCirculacion.estado = 0;
            calculosCirc(preCirculacion, new Date(preCirculacion.horaInicio), scope, $http);
        }
        scope.safeApply(asignarNumCirculacion(scope));
        if (data.length === 13) {
            scope.idProgramacionHoraria = data[12];
            scope.programacionActual.idProgramacionHoraria = data[12];
        }
        scope.esconder_ventanaModal = true;



    });
}

function eliminarProgramacionHoraria(scope, $http) {
    var id = scope.programacionActual.idProgramacionHoraria;
    var nombre = scope.programacionActual.nombreProgramacionHoraria;
    if (confirm("¿Esta seguro que desea eliminar la programacion " + id + "-" + nombre + " ?")) {
        scope.esconder_ventanaModal = false;
        var config = {params: {
                accion: "eliminarProgramacionHoraria",
                id_programacion_horaria: id
            },
//            responseType: "JSON"};
            responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.todasProgramaciones = respuesta;
            scope.programacionActual = scope.todasProgramaciones[0];
            scope.esconder_ventanaModal = true;
        });
    }
}

function cambiarNombrePH(scope, $http) {
    if (confirm("¿Esta seguro que desea alterar el nombre de la programación?")) {
        var nombre = scope.nomPH;
        var id_programacion_horaria = scope.programacionActual.idProgramacionHoraria;
        var config = {params: {
                accion: "cambiarNombrePH",
                id_programacion_horaria: id_programacion_horaria,
                nombre: nombre
            },
//            responseType: "JSON"};
            responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.programacionActual = respuesta;
            scope.esconder_cambiarNombrePH = true;
            scope.nombre_programacion = nombre;
            scope.chart.redraw();
        });
    }
}


function cargarTodasProgramaciones(scope, $http) {
//    var config = {params: {accion: "cargaTodasProgramaciones"}, responseType: "JSON"};
    var config = {params: {accion: "cargaTodasProgramaciones"}, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.todasProgramaciones = respuesta;
        scope.programacionActual = scope.todasProgramaciones[0];
    });
}

function cargaGruposPrefijos(scope, $http) {
//    var config = {params: {accion: "cargaGruposPrefijos"}, responseType: "JSON"};
    var config = {params: {accion: "cargaGruposPrefijos"}, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.grupoNumeraciones = respuesta;
        scope.prefijo_numeracion = scope.grupoNumeraciones[0];
    });
}

function cargaCombosPrefijo(scope, $http) {
    if (scope.lista_tipo_servicio === undefined
            && scope.lista_cat_ident === undefined
            && scope.lista_cat_ident === undefined
            && scope.lista_emp_prop === undefined) {
        console.log("Indefinidos");
//        var config = {params: {accion: "listarServicio"}, responseType: "JSON"};
        var config = {params: {accion: "listarServicio"}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.lista_tipo_servicio = respuesta;
            scope.prefijo[0] = scope.lista_tipo_servicio[0];
        });
//        config = {params: {accion: "listarCategoriaIdentificacion"}, responseType: "JSON"};
        config = {params: {accion: "listarCategoriaIdentificacion"}, responseType: "json"};
        llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config)
                .success(function (respuesta) {
                    scope.lista_cat_ident = respuesta;
                    scope.prefijo[1] = scope.lista_cat_ident[0];
                });

//        config = {params: {accion: "listarCRT"}, responseType: "JSON"};
        config = {params: {accion: "listarCRT"}, responseType: "json"};
        llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config)
                .success(function (respuesta) {
                    scope.lista_crt = respuesta;
                    scope.prefijo[2] = scope.lista_crt[0];
                });

//        config = {params: {accion: "listarEmpresaPropietaria"}, responseType: "JSON"};
        config = {params: {accion: "listarEmpresaPropietaria"}, responseType: "json"};
        llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config)
                .success(function (respuesta) {
                    scope.lista_emp_prop = respuesta;
                    scope.prefijo[3] = scope.lista_emp_prop[0];
                });
    }

}

function cargarMarchasAsc(scope, $http) {
    if ((scope.cmb_lineas !== "" && scope.cmb_lineas !== undefined)) {
        var config = {params: {accion: "cargaMarchas", id_linea: parseInt(scope.cmb_lineas), sentido: true}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.todas_mar_tip_asc = respuesta;
            scope.mar_tip_asc = scope.todas_mar_tip_asc[0];
        });
    } else if (scope.linea !== undefined) {
        var config = {params: {accion: "cargaMarchas", id_linea: scope.linea.idLinea, sentido: true}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.todas_mar_tip_asc = respuesta;
            scope.mar_tip_asc = scope.todas_mar_tip_asc[0];
        });
    } else {
        scope.todas_mar_tip_asc[0] = {idMarchaTipo: " ", nombreMarchaTipo: "Seleccione..."};
    }
}

function cargarMarchasDesc(scope, $http) {
    if (scope.cmb_lineas !== "" && scope.cmb_lineas !== undefined) {
        var config = {params: {accion: "cargaMarchas", id_linea: parseInt(scope.cmb_lineas), sentido: false}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.todas_mar_tip_desc = respuesta;
            scope.mar_tip_desc = scope.todas_mar_tip_desc[0];
        });
    } else if (scope.linea !== undefined) {
        var config = {params: {accion: "cargaMarchas", id_linea: scope.linea.idLinea, sentido: false}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.todas_mar_tip_desc = respuesta;
            scope.mar_tip_desc = scope.todas_mar_tip_desc[0];
        });
    } else {
        scope.todas_mar_tip_desc[0] = {idMarchaTipo: " ", nombreMarchaTipo: "Seleccione..."};
    }
}

function cargaCamposTiemParada(scope, $http) {
    if (scope.cmb_lineas !== "" && scope.cmb_lineas !== undefined) {
        var config = {params: {accion: "cargaTiempoParadas", id_linea: parseInt(scope.cmb_lineas)}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.parada_pred_asc = respuesta;
            for (var i = 0; i < scope.parada_pred_asc.length; i++) {
                scope.tiempo_parada_pred_asc[i] = "";
            }
            scope.parada_pred_desc = scope.parada_pred_asc.reverse();
            for (var i = 0; i < scope.parada_pred_desc.length; i++) {
                scope.tiempo_parada_pred_desc[i] = "";
            }
        });
    } else {
        scope.parada_pred_asc = scope.renderHtml("");
        scope.parada_pred_desc = scope.renderHtml("");
    }
}

function cargarNumeracion(scope) {
    if (scope.tipo_numeracion === "true") {
        scope.escSer = true;
        scope.prefijo[0] = "";
    } else {
        scope.escSer = false;
        scope.prefijo[0] = scope.lista_tipo_servicio[0];
    }
    scope.prefijo[4] = "";
    if (scope.esconder_numeraciones === true) {
        for (var i = 0; i < 4; i++) {
            if (scope.prefijo[i].valor !== undefined) {
                scope.prefijo[4] += scope.prefijo[i].valor;
            }
        }
    } else {
        for (var i = 0; i < 4; i++) {
            console.log(scope.prefijo[i].valor);
            if (scope.prefijo[i].valor !== undefined) {
                scope.prefijo[4] += scope.prefijo[i].valor;
            }
        }
        scope.prefijo[5] = "";
        scope.prefijo[5] += scope.prefijo[4];
        return true;
    }
}

function agregarNumeracion(scope, $http) {
    if (scope.prefijo[6] !== "" && scope.prefijo[6] !== undefined && scope.prefijo[5].length === 5) {
        var nombre = scope.prefijo[6];
        var valor = scope.prefijo[5];
        var config = {params: {accion: "agregarNumeracion", nombre: nombre, valor: valor}, responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.grupoNumeraciones[scope.grupoNumeraciones.length] = respuesta;
            scope.prefijo_numeracion = scope.grupoNumeraciones[0];
            for (var i = 0; i < scope.grupoNumeraciones.length; i++) {
                console.log(scope.grupoNumeraciones[i].nombre + " " + scope.grupoNumeraciones[i].valor);
            }
        });
    } else {
        alert("Valor invalido, no se puede agregar.");
    }
}
function eliminarNumeracion(scope, $http) {
    console.log(scope.prefijo_numeracion);
    var config = {params: {accion: "eliminarNumeracion", id: scope.prefijo_numeracion.idPrefijo}, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.grupoNumeraciones = respuesta;
        scope.prefijo_numeracion = scope.grupoNumeraciones[0];
        for (var i = 0; i < scope.grupoNumeraciones.length; i++) {
            console.log(scope.grupoNumeraciones[i].nombre + " " + scope.grupoNumeraciones[i].valor);
        }
    });
}

function insertarPrefijo(scope) {
    scope.esconder_agregar = true;
    scope.esconder_numeraciones = false;
    scope.esconder_agregar_numeracion = false;
    scope.esconder_eliminar_numeracion = true;
    scope.sec_opc_left = {left: "0px"};
}
function eliminarPrefijo(scope) {
    scope.esconder_agregar = true;
    scope.esconder_numeraciones = false;
    scope.esconder_agregar_numeracion = true;
    scope.esconder_eliminar_numeracion = false;
    scope.sec_opc_left = {left: "0px"};
}

function cargaAreaTrabajo(scope, $http) {
    var idMarAsc = scope.mar_tip_asc;
    var idMarDesc = scope.mar_tip_desc;
    var seguir = false;
    for (var i = 0; i < scope.tiempo_parada_pred_asc.length; i++) {
        if (scope.tiempo_parada_pred_asc[i] !== "" && scope.tiempo_parada_pred_asc[i] !== undefined &&
                scope.tiempo_parada_pred_asc[i] !== null &&
                scope.tiempo_parada_pred_asc[i] !== "null") {
            seguir = true;
        } else {
            seguir = false;
        }
    }
    for (i = 0; i < scope.tiempo_parada_pred_desc.length; i++) {
        if (scope.tiempo_parada_pred_desc[i] !== "" && scope.tiempo_parada_pred_desc[i] !== undefined &&
                scope.tiempo_parada_pred_desc[i] !== null &&
                scope.tiempo_parada_pred_desc[i] !== "null") {
            seguir = true;
        } else {
            seguir = false;
        }
    }

    if ((idMarAsc !== "") && (idMarAsc !== undefined) && (idMarDesc !== "") && (idMarDesc !== undefined) && (seguir === true)) {
        var config = {params: {accion: 'datosIniciales',
                nombre: scope.nombre_programacion,
                id_linea: parseInt(scope.cmb_lineas),
                id_MT_asc_pre: idMarAsc.idMarchaTipo,
                color_asc: scope.color_asc_pre,
                id_MT_desc_pre: idMarDesc.idMarchaTipo,
                color_desc: scope.color_desc_pre
            },
            responseType: "json"};
        var llamada = $http.post('GMT', {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (data) {
            /** Orden de la respuesta recibida:
             * data[0]=Objeto linea
             * data[1]=Array de objetos "Estacion"
             * data[2]=Objeto MarchaTipo Ascendente
             * data[3]=Array de objetos TiempoParadaMarchaTipo Ascendente
             * data[4]=Objeto MarchaTipo Descendente
             * data[5]=Array de objetos TiempoParadaMarchaTipo Descendente
             * data[6]=Milisegundos de tiempoMinimo Ascendente
             * data[7]=Milisegundos de tiempoMinimo Descendente             * 
             * data[8]=Id de la programacion horaria 
             */
            scope.linea = data[0];
            scope.linea.prefijo = scope.prefijo;
            scope.estacionesLinea = data[1];
            scope.mT_pred_asc = data[2];
            scope.mT_pred_asc.tiempoMinimo = data[6];
            scope.mT_pred_asc.sentido = true;
            scope.mT_pred_asc.color = scope.color_asc_pre;
            scope.mT_pred_asc.tiempo_parada_pred = scope.tiempo_parada_pred_asc;
            scope.tiempos_pred_asc = data[3];
            scope.mT_pred_desc = data[4];
            scope.mT_pred_desc.tiempoMinimo = data[7];
            scope.mT_pred_desc.sentido = false;
            scope.mT_pred_desc.color = scope.color_desc_pre;
            scope.mT_pred_desc.tiempo_parada_pred = scope.tiempo_parada_pred_desc;
            scope.tiempos_pred_desc = data[5];
            scope.esconderNueva = true;
            scope.esconder_area_trabajo = false;
            scope.prefijo_numeracion = scope.grupoNumeraciones[0];
            scope.idProgramacionHoraria = data[8];
            scope.programacionActual.idProgramacionHoraria = data[8];
        });
        scope.dibujarGrafico(scope);
        scope.chart.reflow();
    } else {
        alert("Datos incorrectos");
        scope.esconderNueva = false;
        scope.esconder_area_trabajo = true;
    }
}

function insertarViaje(sent, scope, $http) {
    if (sent === true) {
        scope.todas_mar_tip = scope.todas_mar_tip_asc;
        scope.sentido = true;
        scope.marchaTipoActual = scope.mT_pred_asc;
        for (var i = 0; i < scope.todas_mar_tip.length; i++) {
            if (scope.todas_mar_tip[i].idMarchaTipo === scope.marchaTipoActual.idMarchaTipo) {
                scope.mar_tip_agr = scope.todas_mar_tip[i];
                break;
            }
        }
    } else {
        scope.todas_mar_tip = scope.todas_mar_tip_desc;
        scope.sentido = false;
        scope.marchaTipoActual = scope.mT_pred_desc;
        for (var i = 0; i < scope.todas_mar_tip.length; i++) {
            if (scope.todas_mar_tip[i].idMarchaTipo === scope.marchaTipoActual.idMarchaTipo) {
                scope.mar_tip_agr = scope.todas_mar_tip[i];
                break;
            }
        }
    }
    llenarCmbEstSalida(scope, $http);
}

function llenarCmbEstSalida(scope, $http, preCirculacion) {
    var config = {params: {accion: "cargaEstSalida", id_marcha_tipo: parseInt(scope.marchaTipoActual.idMarchaTipo)}, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.cmb_est_salida = respuesta;
        if (scope.editando_circulacion === false) {
            scope.est_salida = scope.cmb_est_salida[0];
        } else {
            for (var i = 0; i < scope.cmb_est_salida.length; i++) {
                if (preCirculacion.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion === scope.cmb_est_salida[i].tiempoEstacionMarchaTipoPK.idPkEstacion) {
                    scope.est_salida = scope.cmb_est_salida[i];
                    break;
                }
            }
        }
        llenarCmbEstLlegada(scope, $http, preCirculacion);
    });
}
function llenarCmbEstLlegada(scope, $http, preCirculacion) {
    var config = {params: {accion: "cargaEstLlegada", id_marcha_tipo: parseInt(scope.marchaTipoActual.idMarchaTipo)}, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.cmb_est_llegada = respuesta;
        if (scope.editando_circulacion === false) {
            scope.est_llegada = scope.cmb_est_llegada[0];
        } else {
            for (var i = 0; i < scope.cmb_est_llegada.length; i++) {
                if (preCirculacion.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion === scope.cmb_est_llegada[i].tiempoEstacionMarchaTipoPK.idPkEstacion) {
                    scope.est_llegada = scope.cmb_est_llegada[i];
                }
            }
        }
//        console.log("llenarCmbEstLlegda listo");
        agregarCamposTiempoParada(scope, $http, preCirculacion);
    });
}

function agregarCamposTiempoParada(scope, $http, preCirculacion) {
//    console.log("agregarCamposTiempoParada");
    var est_salida = parseFloat(scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion);
    var est_llegada = parseFloat(scope.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion);

    var config = {params: {accion: "cargaParadas", id_marcha_tipo: parseInt(scope.marchaTipoActual.idMarchaTipo), estacion_inicio: est_salida, estacion_final: est_llegada}, responseType: ""};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        scope.estaciones_intermedias = respuesta;
//        console.log(respuesta[0].tiempoEstacionMarchaTipoPK);
        if (scope.editando_circulacion === true) {
            console.log("Editando circulacion, teps");

            for (var i = 0; i < respuesta.length; i++) {
                scope.tiempo_estaciones_intermedias[i] = {};
                scope.tiempo_estaciones_intermedias[i].valor = preCirculacion.teps[i + 1].parada;
                scope.tiempo_estaciones_intermedias[i].estacion = preCirculacion.teps[i + 1].estacion;
            }
        } else {
            for (var i = 0; i < respuesta.length; i++) {
                scope.tiempo_estaciones_intermedias[i] = {};
                scope.tiempo_estaciones_intermedias[i].estacion = respuesta[i].tiempoEstacionMarchaTipoPK.idPkEstacion;
                if (est_salida < est_llegada) {
                    scope.tiempo_estaciones_intermedias[i].valor = scope.mT_pred_asc.tiempo_parada_pred[i];
                } else {
                    scope.tiempo_estaciones_intermedias[i].valor = scope.mT_pred_desc.tiempo_parada_pred[i];
                }

            }
        }
//        console.log("scope.tiempo_estaciones_intermedias");
//        console.log(scope.tiempo_estaciones_intermedias);
        scope.habilitar_btn_agr_cir = !true;
    });
//    }
}

function cambiarMarchaActual(scope) {
    for (var i = 0; i < scope.todas_mar_tip.length; i++) {
        if (scope.mar_tip_agr === scope.todas_mar_tip[i]) {
            scope.marchaTipoActual = scope.todas_mar_tip[i];
            break;
        }
    }
}

function agregarCirculacion(scope, $http) {
    var patron = /((0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){1})/;
    var est_salida = parseFloat(scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion);
    var est_llegada = parseFloat(scope.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion);
    var sentido = scope.sentido;
    // A este limite se le agregan las 4 horas de diferencia de Venezuela con respecto al meridiano 0 
    var fechaLimite = new Date((24 + 4) * 60 * 60 * 1000);
    if (est_salida === undefined || est_llegada === undefined || est_salida === est_llegada ||
            (sentido === true && est_salida > est_llegada) ||
            sentido === false && est_salida < est_llegada) {
        alert("Combinacion no válida");
        scope.habilitar_btn_agr_cir = !false;
    } else if (scope.insertando_grupo === true) {
        if (patron.test(scope.horaInicio) === true && patron.test(scope.horaFinal) === true
                && scope.horaInicio < 86400000) {
            var hi = scope.horaInicio;
            scope.safeApply(escogerMarcha(scope, hi, $http));
        } else {
            alert("Alguno de los valores introducidos es incorrecto");
            scope.estaciones_intermedias = "";
            scope.insertando_grupo = false;
            return;
        }
    } else if (patron.test(scope.horaInicio) === true
            && scope.horaInicio < fechaLimite) {
        var hi = scope.horaInicio;
        escogerMarcha(scope, hi, $http);
    } else {
        alert("Alguno de los valores introducidos es incorrecto");
        scope.estaciones_intermedias = "";
        return;
    }
}

function escogerMarcha(scope, horaInicio, $http) {
    if (scope.marchaTipoActual !== scope.mT_pred_asc && scope.marchaTipoActual !== scope.mT_pred_desc) {
        console.log("La marchatipo Diferente");
        marchaTipoDiferente(scope, horaInicio, $http);
    } else {
        marchaTipoIgual(scope, horaInicio, $http);
    }
}

function marchaTipoIgual(scope, horaInicio, $http) {
    //Trabajando con grupos de circulaciones
    if (scope.insertando_grupo === true) {
        if (scope.marchaTipoActual.sentido === true) {
            if (horaInicio < scope.horaFinal) {
                while (horaInicio < scope.horaFinal && comprobarHoraInicio(scope.sentido, scope)) {
                    generarPreCirculacion(scope, scope.tiempos_pred_asc);
                    calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
                    horaInicio = new Date(horaInicio.getTime() + (parseInt(scope.frecuencia_grupo) * 60000));
                    scope.horaInicio = horaInicio;
                    scope.preCirculacion = {
                        marchaTipo: {},
                        idPreCirculacion: ""
                    };
                }
                scope.horaInicio = "";
                scope.horaFinal = "";
            } else {
                scope.horaInicio = "";
                scope.horaFinal = "";
                alert("Horas invalidas");
            }
        } else {
            if (horaInicio < scope.horaFinal) {
                while (horaInicio < scope.horaFinal && comprobarHoraInicio(scope.sentido, scope)) {
//                    console.log("scope.Precirculacion");
//                    console.log(scope.preCirculacion);
//                    console.log("Id_Precirculacion " + scope.preCirculacion.idPreCirculacion);
                    generarPreCirculacion(scope, scope.tiempos_pred_desc);
                    calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
                    horaInicio = new Date(horaInicio.getTime() + (parseInt(scope.frecuencia_grupo) * 60000));
                    scope.horaInicio = horaInicio;
                    scope.preCirculacion = {
                        marchaTipo: {},
                        idPreCirculacion: ""
                    };
                }
                scope.horaInicio = "";
                scope.horaFinal = "";
            } else {
                scope.horaInicio = "";
                scope.horaFinal = "";
                alert("Horas invalidas");
            }
        }
        scope.insertando_grupo = false;
        asignarNumCirculacion(scope);
    } else {
        // Una sola circulación
        if (comprobarHoraInicio(scope.sentido, scope) === true) {
//            console.log("scope.Precirculacion");
//            console.log(scope.preCirculacion);
//            console.log("Id_Precirculacion " + scope.preCirculacion.idPreCirculacion);
            if (scope.marchaTipoActual.sentido === true) {
                generarPreCirculacion(scope, scope.tiempos_pred_asc);
                calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
            } else {
                generarPreCirculacion(scope, scope.tiempos_pred_desc);
                calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
            }
            scope.preCirculacion = {
                marchaTipo: {},
                idPreCirculacion: ""
            };
        }
    }
    asignarNumCirculacion(scope);
//    scope.safeApply(asignarNumCirculacion(scope));
}

function marchaTipoDiferente(scope, horaInicio, $http) {
    var config = {params: {accion: "cambiarMT",
            idMT: scope.marchaTipoActual.idMarchaTipo,
            idLinea: scope.linea.idLinea,
            cmb_est_salida: scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion,
            cmb_est_llegada: scope.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion
        }, responseType: "json"};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (data) {
        if (scope.insertando_grupo === true) {
            if (horaInicio < scope.horaFinal) {
                while (horaInicio < scope.horaFinal && comprobarHoraInicio(scope.sentido, scope)) {
//                    console.log("scope.Precirculacion");
//                    console.log(scope.preCirculacion);
//                    console.log("Id_Precirculacion " + scope.preCirculacion.idPreCirculacion);
                    generarPreCirculacion(scope, data[0]);
                    calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
                    horaInicio = new Date(horaInicio.getTime() + (parseInt(scope.frecuencia_grupo) * 60000));
                    scope.horaInicio = horaInicio;
                    scope.preCirculacion = {
                        marchaTipo: {},
                        idPreCirculacion: ""
                    };
                }
                scope.horaInicio = "";
                scope.horaFinal = "";
            } else {
                scope.horaInicio = "";
                scope.horaFinal = "";
                alert("Horas invalidas");
            }
            scope.insertando_grupo = false;
        } else {
            if (comprobarHoraInicio(scope.sentido, scope) === true) {
//                console.log("scope.Precirculacion");
//                console.log(scope.preCirculacion);
//                console.log("Id_Precirculacion " + scope.preCirculacion.idPreCirculacion);
                generarPreCirculacion(scope, data[0]);
                calculosCirc(scope.preCirculacion, horaInicio, scope, $http);
                scope.preCirculacion = {
                    marchaTipo: {},
                    idPreCirculacion: ""
                };
            }
        }
        scope.safeApply(asignarNumCirculacion(scope));

    });
}
function generarPreCirculacion(scope, tiempos_dif) {
//    console.log("Id_Precirculacion " + scope.preCirculacion.idPreCirculacion);
    scope.preCirculacion.marchaTipo = scope.marchaTipoActual;
//    console.log(scope.colorActual);
    //Tramos, estaciones, parada
    scope.preCirculacion.teps = [];
    if (scope.marchaTipoActual.sentido === true) {
//        console.log("Agregar Circulacion ASCENDENTE");
        for (var y = 0; y < scope.estacionesLinea.length; y++) {
            if (scope.estacionesLinea[y].estacionPK.idPkEstacion >= parseFloat(scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion) &&
                    scope.estacionesLinea[y].estacionPK.idPkEstacion <= parseFloat(scope.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion)) {
                var tep = {};
                tep.estacion = scope.estacionesLinea[y].estacionPK.idPkEstacion;
                scope.preCirculacion.teps.push(tep);
            }
        }

        for (y = 0; y < scope.preCirculacion.teps.length; y++) {
            for (var h = 0; h < tiempos_dif.length; h++) {
                if (tiempos_dif[h].idPkEstacion === scope.preCirculacion.teps[y].estacion) {
                    var hora = new Date(tiempos_dif[h].tiempoAsimilado);
                    scope.preCirculacion.teps[y].tramo = hora.getUTCMinutes();
                }
            }
        }

        var j = 0;
        for (i = 0; i < scope.preCirculacion.teps.length; i++) {
//            console.log("i= " + i + ", j= " + j);
            if (j < scope.tiempo_estaciones_intermedias.length) {
                if (i === 0) {
                    scope.preCirculacion.teps[i].parada = 0;
                } else if (scope.preCirculacion.teps[i].estacion === scope.tiempo_estaciones_intermedias[j].estacion) {
                    scope.preCirculacion.teps[i].parada = scope.tiempo_estaciones_intermedias[j].valor;
                    j++;
                } else {
                    //agregado 29/05/2015 para solventar el null pointer
                    scope.preCirculacion.teps[i].parada = 0;
                }
            } else {
                scope.preCirculacion.teps[i].parada = 0;
            }
        }
    } else {
//        console.log("Agregar Circulacion DESCENDENTE");
        scope.estacionesLinea.reverse();
        for (var y = 0; y < scope.estacionesLinea.length; y++) {
            if (parseFloat(scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion) >= scope.estacionesLinea[y].estacionPK.idPkEstacion &&
                    parseFloat(scope.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion) <= scope.estacionesLinea[y].estacionPK.idPkEstacion) {
                var tep = {};
                tep.estacion = scope.estacionesLinea[y].estacionPK.idPkEstacion;
                scope.preCirculacion.teps.push(tep);
            }
        }
        scope.estacionesLinea.reverse();

        for (y = 0; y < scope.preCirculacion.teps.length; y++) {
            for (var h = 0; h < tiempos_dif.length; h++) {
                if (tiempos_dif[h].idPkEstacion === scope.preCirculacion.teps[y].estacion) {
                    var hora = new Date(tiempos_dif[h].tiempoAsimilado);
                    scope.preCirculacion.teps[y].tramo = hora.getUTCMinutes();
                }
            }
        }

        var j = 0;
        var i = 0;

        for (i = 0; i < scope.preCirculacion.teps.length; i++) {
            if (j < scope.tiempo_estaciones_intermedias.length) {
                if (i === 0) {
                    scope.preCirculacion.teps[i].parada = 0;
                } else if (scope.preCirculacion.teps[i].estacion === scope.tiempo_estaciones_intermedias[j].estacion) {
                    scope.preCirculacion.teps[i].parada = scope.tiempo_estaciones_intermedias[j].valor;
                    j++;
                } else {
                    //agregado 29/05/2015 para solventar el null pointer
                    scope.preCirculacion.teps[i].parada = 0;
                }
            } else {
                scope.preCirculacion.teps[i].parada = 0;
            }
        }
    }
    //Asignando estado
    if (scope.estado === 0 && scope.editando_circulacion === true) {
        scope.estado = 2;
    } else if (scope.estado === 1 && scope.editando_circulacion === true) {
        scope.estado = 1;
    } else {
        scope.estado = 1;
    }

    if (scope.colorActual !== scope.preCirculacion.color) {
        scope.preCirculacion.color = scope.colorActual;
    }
    scope.preCirculacion.est_salida = scope.est_salida;
    scope.preCirculacion.est_llegada = scope.est_llegada;
    scope.preCirculacion.horaInicio = scope.horaInicio.getTime();
//    console.log("Prefijo actual q se asignara");
//    console.log(scope.prefijo_numeracion);
    scope.preCirculacion.prefijo_numeracion = scope.prefijo_numeracion;
    scope.preCirculacion.estado = scope.estado;
}

function calculosCirc(preCirculacion, hm, scope, $http) {
//    console.log(preCirculacion);
    var salida = [];
    var j = 1;
    var desf = 0;
    var desf2 = 0;
//    var desfX = -10;
    var desfX = 0;
    for (var i = 0; i < preCirculacion.teps.length; i++) {
        if (preCirculacion.marchaTipo.sentido === true) {
            desf = 20;
            desf2 = -30;

        } else {
            desf = -30;
            desf2 = 10;
        }
        if (i === 0) {
            var fechaUTC = Date.UTC(1970, 0, 1, hm.getHours(), hm.getMinutes(), 0);
            var par = {};
            par.x = fechaUTC;
            par.y = preCirculacion.teps[i].estacion;
            par.dataLabels = {
                y: desf,
                x: desfX,
                z: -1
            };
            salida[0] = par;
        } else {
            var parAnt = {};
            parAnt = salida[salida.length - 1];
            var fechaAnt = new Date(parAnt.x);
            var horaAnt = fechaAnt.getUTCHours();
            var minAnt = fechaAnt.getUTCMinutes();
            var min;
            var hora;
            if ((minAnt + preCirculacion.teps[i].tramo) >= 60) {
                var a = ((minAnt + preCirculacion.teps[i].tramo) / 60);
                hora = horaAnt + parseInt(a.toFixed());
                min = parseInt(((a - a.toFixed()) * 60).toFixed());
            } else {
                min = minAnt + preCirculacion.teps[i].tramo;
                hora = horaAnt;
            }
            if (hora < 24) {
                if (i !== (preCirculacion.teps.length - 1)) {
                    var fechaUTC = Date.UTC(1970, 0, 1, hora, min, 0);
                    var par = {};
                    par.x = fechaUTC;
                    par.y = preCirculacion.teps[i].estacion;
                    par.dataLabels = {
                        y: desf2,
                        x: desfX,
                        z: -1
                    };
                    salida[j++] = par;
                    parAnt = salida[salida.length - 1];
                    fechaAnt = new Date(parAnt.x);
                    horaAnt = fechaAnt.getUTCHours();
                    minAnt = fechaAnt.getUTCMinutes();
                    if ((min + preCirculacion.teps[i].parada) >= 60) {
                        var a = ((minAnt + preCirculacion.teps[i].parada) / 60);
                        hora = horaAnt + parseInt(a.toFixed());
                        min = parseInt(((a - a.toFixed()) * 60).toFixed());
                        if (hora >= 24) {
                            alert("No se puede agregar la circulación (1)");
                            return;
                        }
                    } else {
                        min = (min + preCirculacion.teps[i].parada);
                    }
                    fechaUTC = Date.UTC(1970, 0, 1, hora, min, 0);
                    par = {};
                    par.x = fechaUTC;
                    par.y = preCirculacion.teps[i].estacion;
                    par.dataLabels = {
                        y: desf2,
                        x: desfX,
                        z: -1
                    };
                    salida[j++] = par;
                } else {
                    var fechaUTC = Date.UTC(1970, 0, 1, hora, min, 0);
                    var par = {};
                    par.x = fechaUTC;
                    par.y = preCirculacion.teps[i].estacion;
                    par.dataLabels = {
                        y: desf2,
                        x: desfX,
                        z: -1
                    };
                    salida[j++] = par;
                }
            } else {
                alert("No se puede agregar la circulación (2)");
                return;
            }
        }
    }
    //Agregando la serie
//    console.log("Salida");
//    console.log(salida);
    var serie;
    serie = {
        data: salida,
        color: preCirculacion.color,
        preCirculacion: preCirculacion
    };
    //modificado el 29/05/2015 1 linea
    if (scope.editando_circulacion && comprobarHoraInicio2(scope, serie)) {
        scope.chart.series[scope.indiceSerieActual].remove();
        if (serie.preCirculacion.idPreCirculacion !== "") {
            var editar = parsePreCirculacion(serie.preCirculacion);
            var config = {params: {accion: "editarPreCirculacion",
                    id_programacion_horaria: parseInt(scope.idProgramacionHoraria),
                    id_pre_circulacion: parseInt(editar.idPreCirculacion),
                    pre_circulacion: editar},
                responseType: "json"};
            var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
            llamada.success(function (respuesta) {
//                console.log(respuesta.respuesta);
                console.log(respuesta);
                scope.chart.addSeries(serie);

            });
        } else {
            scope.chart.addSeries(serie);
        }

    } else if (scope.insertando_grupo === true) {
        // modificado el 01/06/2015 4 lineas
        if (!comprobarHoraInicio2(scope, serie)) {
            console.log("No se puede agregar grupo");
            return;
        } else {
            scope.safeApply(scope.chart.addSeries(serie, false));
        }

        // modificado el 29/05/2015 1 linea
    } else if (comprobarHoraInicio2(scope, serie)) {
//        scope.safeApply(scope.chart.addSeries(serie));
        scope.safeApply(scope.chart.addSeries(serie, false));
    }
    scope.horaInicio = "";
    scope.estaciones_intermedias = "";
}

/*Elimina una o un grupo de circulaciones seleccionadas*/
function eliminarCirculacion(scope, $http) {
    console.log("Eliminando");
    var mensaje;
    if (scope.chart.getSelectedPoints().length > 1) {
        mensaje = "¿Desea eliminar " + scope.chart.getSelectedPoints().length + " circulaciones?";
    } else if (scope.chart.getSelectedPoints().length === 1) {
        mensaje = "¿Desea eliminar la circulación " + scope.chart.getSelectedPoints()[0].series.name + "?";
    } else if (scope.chart.getSelectedPoints().length === 0) {
        //En caso de que no este ninguna circulacion seleccionada
        return;
    }

    if (confirm(mensaje)) {
        scope.esconder_ventanaModal = false;
        var ides = [];
        var idesRuta = [];
        //Este bucle selecciona que no hayan id de preCirculaciones repetidos
        for (var i = 0; i < scope.chart.getSelectedPoints().length; i++) {
            if ((ides.length === 0)) {
                ides.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion.idPreCirculacion);
                idesRuta.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion.preCirculacionPK.idRuta);
            } else
            if (scope.chart.getSelectedPoints()[i].series.options.preCirculacion.idPreCirculacion !== "") {
                for (var h = 0; h < ides.length; h++) {
                    if (ides[h] === scope.chart.getSelectedPoints()[i].series.options.preCirculacion.idPreCirculacion) {
                        break;
                    } else if (h === (ides.length - 1)) {
                        ides.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion.idPreCirculacion);
                        idesRuta.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion.preCirculacionPK.idRuta);
                        break;
                    }
                }

            }
        }
        console.log("Eliminar " + ides);
        if (ides.length > 0) {
            var config = {
                params: {accion: "eliminarPreCirculacion",
                    id_programacion_horaria: parseInt(scope.idProgramacionHoraria),
                    id_pre_circulacion: ides, id_rutas: idesRuta
                },
                responseType: "json"};
            //Se eliminan de la BD
            var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
            llamada.success(function (respuesta) {
                //Luego de ser eliminados de la BD se eliminan del grafico
                console.log(respuesta);
                var max = ides.length;
                for (i = 0; i < max; i++) {
                    scope.chart.getSelectedPoints()[0].series.remove(false);
                }
                //Se reasignan los numeros de circulación
                asignarNumCirculacion(scope);
                scope.esconder_ventanaModal = true;
            });
        } else {
            /*Si se elimina una circulación o un grupo que no se 
             *ha guardado en la BD
             **/
            var max = scope.chart.getSelectedPoints().length;
            for (i = 0; i < max; i++) {
                scope.chart.getSelectedPoints()[0].series.remove(false);
            }
            asignarNumCirculacion(scope);
            scope.esconder_ventanaModal = true;
        }
    } else {
        return;
    }
}


function dibujarGrafico(scope, accion) {
    //Opciones generales del grafico
    Highcharts.setOptions({
        lang: {
            loading: "Cargando..."
        }
    });
    //El grafico en si
    scope.safeApply(function () {

        scope.chart = new Highcharts.Chart({
            chart: {
                renderTo: 'graficaLineal', // Contenedor en el que se dibujara
                defaultSeriesType: 'line'	// Pongo que tipo de gráfica es            
            },
            loading: {
                labelStyle: {
                    color: 'white'
                },
                style: {
                    backgroundColor: 'gray'
                }
            },
            title: {
                text: 'Grafico de movimiento de trenes'	// Titulo (Opcional)
            },
            credits: {
                text: 'Nestor Mendoza y Kelvins Insua',
                href: ''
            },
            subtitle: {
                text: scope.nombre_programacion // Subtitulo (Opcional)
            },
            // Opciones del eje de las 'X'
            xAxis: {
                type: 'datetime'
            },
            yAxis: {
                //Opciones del eje de las 'Y'
                title: {
                    text: 'Progresivas'
                }
            },
            //Formato del tooltip
            tooltip: {
                enabled: true,
                formatter: function () {
                    var estacion = "";
                    for (var i = 0; i < scope.estacionesLinea.length; i++) {
                        if (this.y === scope.estacionesLinea[i].estacionPK.idPkEstacion) {
                            estacion = scope.estacionesLinea[i].abrevEstacion;
                            break;
                        }
                    }
                    var dateFormat = '%H:%M';
                    return '(' + Highcharts.dateFormat(dateFormat, this.x) + ') ' + ': ' + estacion;
                }
            },
            legend: {
                enabled: 0
            },
            // Doy opciones a la gráfica
            plotOptions: {
                series: {
                    //Señalador de cada punto
                    marker: {
                        enabled: true
                    },
                    events: {
                        dblclick: function () {
                        }
                    },
                    point: {
                        //Habilitado por el plug-in customEvents.js
                        events: {
                            dblclick: function () {
                                if (scope.rutasActivas === true) {

                                } else {
                                    console.log(accion);
                                    console.log("doble click");
                                    scope.safeApply(this.select());
                                    scope.safeApply(scope.detallarCirculacion(this.series));
                                }
                            }
                        }
                    }
                },
                line: {
                    dataLabels: {
                        enabled: true,
                        formatter: function () {
                            var dateFormat = ':%M';
                            var dateFormat2 = '%H:%M';
                            var nombreSerie = this.series.name;
                            var salida;
                            //Si es el primer punto de la serie que le agregue el numero de circulación
                            if (Highcharts.dateFormat(dateFormat2, this.series.data[0].x) === Highcharts.dateFormat(dateFormat2, this.x)) {
                                salida = nombreSerie + " " + Highcharts.dateFormat(dateFormat, this.x);
                            } else {
                                salida = Highcharts.dateFormat(dateFormat, this.x);
                            }
                            return salida;
                        },
                        verticalAlign: 'top',
                        padding: 0,
                        allowOverlap: true
                    },
                    enableMouseTracking: true,
                    allowPointSelect: true
                }
            }
        });
    });
    scope.chart.reflow();
}

//Funcion de seguridad, para que ningún tren salga muy cerca del otro
function comprobarHoraInicio(sentido, scope) {
    for (var i = 0; i < scope.chart.series.length; i++) {
        if ((scope.chart.series[i].options.preCirculacion.marchaTipo.sentido === sentido)) {
            for (var j = 0; j < scope.chart.series[i].data.length; j++) {
                if (scope.chart.series[i].data[j].y === scope.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion) {
                    //El tiempo minimo se genera junto con la marcha tipo
                    if (((scope.chart.series[i].data[j].x + scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000)) > (scope.horaInicio - (4 * 3600 * 1000))) &&
                            (scope.chart.series[i].data[j].x - (scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000))) < (scope.horaInicio - (4 * 3600 * 1000)) &&
                            (scope.chart.series[i].name !== scope.numCirActual)) {
                        window.alert("La circulacion " + scope.chart.series[i].name + "\n esta muy cerca, no se puede agregar");
                        return false;
                    }
                }
            }
        }
    }
    return true;
}
/*Ordena las circulaciones comparando las horas en las que salen los trenes, 
 * si son iguales compara las horas en las que llegan*/
function ordenarCirculaciones(scope) {
    scope.chart.series.sort(function (a, b) {
        if (a.xData.length !== 0 && b.xData.length !== 0) {
//            var saleA = a.data[0].x;
//            var saleB = b.data[0].x;
            var saleA = a.xData[0];
            var saleB = b.xData[0];
            if ((saleA - saleB) !== 0) {
                return (saleA - saleB);
            } else {
                var llegA = a.xData[a.xData.length - 1];
                var llegB = b.xData[a.xData.length - 1];
                return llegA - llegB;
            }
        }
    });
}

function detallarCirculacion(scope, $http, circulacionSerie) {
    for (var i = 0; i < scope.chart.series.length; i++) {
        if (scope.chart.series[i].name === circulacionSerie.name) {
            scope.indiceSerieActual = i;
            break;
        }
    }
    scope.editando_circulacion = true;
    if (circulacionSerie.options.preCirculacion.marchaTipo.sentido === true) {
        scope.todas_mar_tip = scope.todas_mar_tip_asc;
        scope.sentido = true;
        scope.marchaTipoActual = scope.mT_pred_asc;
        for (var i = 0; i < scope.todas_mar_tip.length; i++) {
            if (scope.todas_mar_tip[i].idMarchaTipo === circulacionSerie.options.preCirculacion.marchaTipo.idMarchaTipo) {
                scope.mar_tip_agr = scope.todas_mar_tip[i];
                break;
            }
        }
    } else {
        scope.todas_mar_tip = scope.todas_mar_tip_desc;
        scope.sentido = false;
        scope.marchaTipoActual = scope.mT_pred_desc;
        for (var i = 0; i < scope.todas_mar_tip.length; i++) {
            if (scope.todas_mar_tip[i].idMarchaTipo === circulacionSerie.options.preCirculacion.marchaTipo.idMarchaTipo) {
                scope.mar_tip_agr = scope.todas_mar_tip[i];
                break;
            }
        }
    }
    var fecha = new Date(circulacionSerie.options.preCirculacion.horaInicio);
    scope.horaInicio = fecha;
    scope.colorActual = circulacionSerie.options.preCirculacion.color;
    scope.numCirActual = circulacionSerie.name;
    llenarCmbEstSalida(scope, $http, circulacionSerie.options.preCirculacion);
    llenarCmbEstLlegada(scope, $http, circulacionSerie.options.preCirculacion);
    scope.esconder_agregar = false;
    if (circulacionSerie.options.preCirculacion.idPreCirculacion !== "") {
        scope.preCirculacion.idPreCirculacion = circulacionSerie.options.preCirculacion.idPreCirculacion;
    }
    scope.sec_opc_left = {left: "0px"};
}

function asignarNumCirculacion(scope) {
    ordenarCirculaciones(scope);
    var final = "";

    for (var k = 0; k < scope.grupoNumeraciones.length; k++) {
        var contadorAscendente = 0;
        var contadorDescendente = 1;
        var letra = 0;
        if (scope.grupoNumeraciones[k].valor.length === 3) {
            contadorAscendente = parseInt(scope.grupoNumeraciones[k].valor + "000");
            contadorDescendente = parseInt(scope.grupoNumeraciones[k].valor + "001");
            letra = 0;
        } else if (scope.grupoNumeraciones[k].valor.length === 4) {
            contadorAscendente = parseInt(scope.grupoNumeraciones[k].valor[1] + scope.grupoNumeraciones[k].valor[2] + scope.grupoNumeraciones[k].valor[3] + "00");
            contadorDescendente = parseInt(scope.grupoNumeraciones[k].valor[1] + scope.grupoNumeraciones[k].valor[2] + scope.grupoNumeraciones[k].valor[3] + "01");
            letra = scope.grupoNumeraciones[k].valor[0];

        } else if (scope.grupoNumeraciones[k].valor.length === 5) {
            contadorAscendente = parseInt(scope.grupoNumeraciones[k].valor[1] + scope.grupoNumeraciones[k].valor[2] + scope.grupoNumeraciones[k].valor[3] + scope.grupoNumeraciones[k].valor[4] + "0");
            contadorDescendente = parseInt(scope.grupoNumeraciones[k].valor[1] + scope.grupoNumeraciones[k].valor[2] + scope.grupoNumeraciones[k].valor[3] + scope.grupoNumeraciones[k].valor[4] + "1");
            letra = scope.grupoNumeraciones[k].valor[0];
        } else {
            contadorAscendente = parseInt(scope.grupoNumeraciones[k].valor + "0");
            contadorDescendente = parseInt(scope.grupoNumeraciones[k].valor + "1");
            letra = 0;
        }

        for (var i = 0; i < scope.chart.series.length; i++) {
            if (scope.chart.series[i].options.preCirculacion.prefijo_numeracion.idPrefijo === scope.grupoNumeraciones[k].idPrefijo) {
                if (scope.chart.series[i].options.preCirculacion.marchaTipo.sentido === true) {
                    contadorAscendente += 2;
                    final = letra + contadorAscendente;
                } else {
                    final = letra + contadorDescendente;
                    contadorDescendente += 2;
                }
                scope.chart.series[i].name = final;
            }
        }
    }
    scope.safeApply(scope.chart.redraw());
    if (scope.chart.series.length > 0) {
        scope.esconderTH = false;
    } else {
        scope.esconderTH = true;
    }
    generarFilasTH(scope);
}

function generarFilasTH(scope) {
    var dateFormat = '%H:%M';
    scope.tablaHoraria.filasAscendentes = [];
    scope.tablaHoraria.filasDescendentes = [];

    scope.arrayDeFilasRutas = [];
    if (scope.rutasActivas === true) {
        for (var r = 0; r < scope.rutas.length; r++) {
            scope.arrayDeRutas = [];
            var h = 0;
            var ruta = scope.rutas[r].rutaPK.idRuta;
            console.log(ruta);
            var tm = 0;
            for (var s = 0; s < scope.chart.series.length; s++) {
                var arrayRuta = {};

                if (scope.chart.series[s].options.preCirculacion.preCirculacionPK.idRuta === ruta) {
                    tm = 0;
                    for (var t = 0; t < scope.chart.series[s].options.preCirculacion.teps.length; t++) {
                        tm += scope.chart.series[s].options.preCirculacion.teps[t].tramo
                                + scope.chart.series[s].options.preCirculacion.teps[t].parada;
                    }
//                        console.log(tm);
                    arrayRuta.tiempoMarcha = tm;
                    if (scope.chart.series[s].options.preCirculacion.marchaTipo.sentido === true) {

                        arrayRuta.numeroCirculacion = scope.chart.series[s].name;
                        arrayRuta.horaEnEstacion = [];


                        for (var j = 0; j < scope.estacionesLinea.length; j++) {
                            var estLeida = false;
                            for (var k = 0; k < scope.chart.series[s].data.length; k++) {
                                if (scope.estacionesLinea[j].estacionPK.idPkEstacion === scope.chart.series[s].data[k].y) {
                                    arrayRuta.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, scope.chart.series[s].data[k].x));
                                    estLeida = true;
                                }
                                else {
                                    h++;
                                }
                            }
                            if (h === scope.chart.series[s].data.length) {
                                arrayRuta.horaEnEstacion.push("");
                                arrayRuta.horaEnEstacion.push("");
                            } else if (estLeida === false) {
                                arrayRuta.horaEnEstacion.push("");
                                arrayRuta.horaEnEstacion.push("");
                            }
                        }
                        arrayRuta.sentido = true;
                        arrayRuta.nombre = scope.rutas[r].nombre;

//                    console.log(arrayRuta);
                        scope.arrayDeRutas.push(arrayRuta);
                    } else {
                        //Sentido DESCENDENTE
                        arrayRuta.numeroCirculacion = scope.chart.series[s].name;
                        arrayRuta.horaEnEstacion = [];
                        scope.estacionesLinea.reverse();
                        for (var j = 0; j < scope.estacionesLinea.length; j++) {
                            var estLeida = false;
                            for (var k = 0; k < scope.chart.series[s].data.length; k++) {
                                if (scope.estacionesLinea[j].estacionPK.idPkEstacion === scope.chart.series[s].data[k].y) {
                                    arrayRuta.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, scope.chart.series[s].data[k].x));
                                    estLeida = true;
                                } else {
                                    h++;
                                }
                            }
                            if (h === scope.chart.series[s].data.length) {
                                arrayRuta.horaEnEstacion.push("");
                                arrayRuta.horaEnEstacion.push("");
                            } else if (estLeida === false) {
                                arrayRuta.horaEnEstacion.push("");
                                arrayRuta.horaEnEstacion.push("");
                            }
                        }
                        scope.estacionesLinea.reverse();
                        arrayRuta.sentido = false;
                        arrayRuta.nombre = scope.rutas[r].nombre;
//            console.log(arrayRuta);            
                        arrayRuta.horaEnEstacion.reverse();
                        scope.arrayDeRutas.push(arrayRuta);
                    }
                }
            }
//              scope.arrayDeFilasRutas.push(arrayRuta);
            scope.arrayDeFilasRutas.push(scope.arrayDeRutas);
        }
//    console.log(scope.arrayDeFilasRutas);
    }
    for (var i = 0; i < scope.chart.series.length; i++) {
        var fila = {};
        h = 0;
        //Sentido ASCENDENTE
        if (scope.chart.series[i].options.preCirculacion.marchaTipo.sentido === true) {
            fila.numeroCirculacion = scope.chart.series[i].name;
            fila.horaEnEstacion = [];
            //agregado el 01/06/2015 3 lineas
            fila.viaSalida = "";
            fila.viaLlegada = "";
            fila.observacion = "";
            //**
            for (var j = 0; j < scope.estacionesLinea.length; j++) {
                var estLeida = false;
                for (var k = 0; k < scope.chart.series[i].data.length; k++) {
                    if (scope.estacionesLinea[j].estacionPK.idPkEstacion === scope.chart.series[i].data[k].y) {
                        fila.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, scope.chart.series[i].data[k].x));
                        estLeida = true;
                    }
                    else {
                        h++;
                    }
                }
                //modificado el 01/06/2015 @Seront
//                if (h === scope.chart.series[i].data.length) {
//                    fila.horaEnEstacion.push("");
//                    fila.horaEnEstacion.push("");
//                } else

                if (estLeida === false) {
                    fila.horaEnEstacion.push("");
                    fila.horaEnEstacion.push("");
                }
            }
            scope.tablaHoraria.filasAscendentes.push(fila);
        } else {
            //Sentido DESCENDENTE
            fila.numeroCirculacion = scope.chart.series[i].name;
            fila.horaEnEstacion = [];
            //agregado el 01/06/2015 3 lineas
            fila.viaSalida = "";
            fila.viaLlegada = "";
            fila.observacion = "";
            //**
            scope.estacionesLinea.reverse();
            for (var j = 0; j < scope.estacionesLinea.length; j++) {
                var estLeida = false;
                for (var k = 0; k < scope.chart.series[i].data.length; k++) {
                    if (scope.estacionesLinea[j].estacionPK.idPkEstacion === scope.chart.series[i].data[k].y) {
                        fila.horaEnEstacion.push(Highcharts.dateFormat(dateFormat, scope.chart.series[i].data[k].x));
                        estLeida = true;
                    } else {
                        h++;
                    }
                }
                //modificado el 01/06/2015 @Seront
//                if (h === scope.chart.series[i].data.length) {
//                    fila.horaEnEstacion.push("");
//                    fila.horaEnEstacion.push("");
//                } else 
                if (estLeida === false) {
                    fila.horaEnEstacion.push("");
                    fila.horaEnEstacion.push("");
                }
            }
            scope.estacionesLinea.reverse();
            scope.tablaHoraria.filasDescendentes.push(fila);
        }
    }
}


function imprimirGrafico(scope) {
//    console.log("Extremos");
//    console.log(scope.chart.xAxis[0].getExtremes());
    var fecha = new Date(scope.chart.xAxis[0].getExtremes().dataMin);
    var horaMenor = fecha.getUTCHours();
//    console.log(fecha);
    fecha = new Date(scope.chart.xAxis[0].getExtremes().dataMax);
    var horaMayor = fecha.getUTCHours();
//    console.log(fecha);
//    console.log(horaMenor+" , "+horaMayor+"; "+(horaMayor-horaMenor));
//    console.log($("#graficaLineal").css("height"));
    var altoContenedor = $("#graficaLineal").css("height");
    var anchoContenedor = (1960 * (horaMayor - horaMenor)) + "px";
    scope.safeApply($("#graficaLineal").css("width", anchoContenedor));
    scope.safeApply($("#graficaLineal").css("height", "1050px"));
    scope.chart.reflow();
    var largoHoja = 297 * (horaMayor - horaMenor);
    alert("El tamaño recomendado de la hoja de impresión es de 210mm x " + largoHoja + "mm");
    var contGraf = document.getElementById("graficaLineal");
    var vent = window.open("GMT.html",
            "S.I.P.T.R.A.F", "toolbar=yes, scrollbars=yes, menubar=yes, directories=yes");
    vent.document.write(contGraf.innerHTML);
    vent.document.close();
    scope.safeApply($("#graficaLineal").css("height", altoContenedor));
    scope.chart.reflow();
}

function guardarProgramacion(scope, $http) {
    scope.esconder_ventanaModal = false;
    var agregar = [];
    var posicionesAgregar = [];
    for (var i = 0; i < scope.chart.series.length; i++) {
        switch (scope.chart.series[i].options.preCirculacion.estado) {
            case 0:
                break;
            case 1:
                posicionesAgregar.push(i);
                agregar.push(parsePreCirculacion(scope.chart.series[i].options.preCirculacion));
                break;
        }
    }
    if (agregar.length > 0) {
        scope.safeApply(function () {
//            console.log(scope.programacionActual.idProgramacionHoraria);
//            console.log(parseInt(scope.programacionActual.idProgramacionHoraria));
            $.ajax({
                url: 'GMT',
                type: "POST",
                dataType: 'json',
                data: {accion: "agregarPrecirculaciones",
//                    id_programacion_horaria: parseInt(scope.idProgramacionHoraria),
                    id_programacion_horaria: parseInt(scope.programacionActual.idProgramacionHoraria),
                    preCirclaciones: JSON.stringify(agregar)},
                success: function (respuesta) {
                    var ides = respuesta;
                    if (ides.length === posicionesAgregar.length) {
                        for (var i = 0; i < posicionesAgregar.length; i++) {
                            scope.chart.series[parseInt(posicionesAgregar[i])].options.preCirculacion.estado = 0;
                            scope.chart.series[parseInt(posicionesAgregar[i])].options.preCirculacion.idPreCirculacion = ides[i];
                        }
                    }
                    scope.safeApply(function () {
                        scope.esconder_ventanaModal = true;
                    });
                },
                fail: function (respuesta) {
                    console.log("Error al guardar");
                    console.log(respuesta);
                    scope.esconder_ventanaModal = true;
                }
            });
        });
    } else {
        scope.esconder_ventanaModal = true;
    }
}

function parsePreCirculacion(preCirculacion) {
    var listo = {};
    listo.marchaTipo = parseInt(preCirculacion.marchaTipo.idMarchaTipo);
    listo.teps = preCirculacion.teps;
    listo.color = preCirculacion.color;
    listo.estacionSalida = preCirculacion.est_salida.tiempoEstacionMarchaTipoPK.idPkEstacion;
    listo.estacionLlegada = preCirculacion.est_llegada.tiempoEstacionMarchaTipoPK.idPkEstacion;
    listo.horaInicio = preCirculacion.horaInicio;
    listo.prefijoNumeracion = preCirculacion.prefijo_numeracion.idPrefijo;
    listo.idPreCirculacion = preCirculacion.idPreCirculacion;
    return listo;
}

function preAgregarARuta(scope) {
    console.log(scope.chart.getSelectedPoints());
    if (scope.chart.getSelectedPoints().length !== 0) {
        //Agarrar solo circulaciones que no se repitan
        if (scope.rutas.length === 0) {
            scope.esconder_crearRuta = false;
            scope.esconder_opcionesRuta = true;
            scope.esconder_seleccionarRuta = true;
        } else {
            scope.esconder_opcionesRuta = false;
            scope.esconder_crearRuta = true;
            scope.esconder_botonGuardarRuta = true;
            scope.esconder_seleccionarRuta = true;
            scope.esconder_botonAgregarARuta = true;
        }
        scope.cancelar();
        scope.chart.showLoading();
        scope.esconder_ruta = false;
        scope.editando_circulacion = false;
        scope.esconder_agregar = true;
        scope.esconder_cambiarNombrePH = true;
        scope.sec_opc_left = {left: "0px"};
    } else {
        alert("Seleccione Al Menos Una Circulación");
    }

}

function crearRuta(scope, $http) {

    var nombre = "" + scope.nomRuta;
    var ruta = {};
//        scope.ruta.nombre=nombre;
//        scope.ruta.circulaciones=circulaciones;
//        scope.ruta.color=scope.colorRuta;
//        scope.rutas.push(scope.ruta);
    ruta.nombre = nombre;

    ruta.color = scope.colorRuta;
//        scope.rutas.push(ruta);
    scope.esconder_crearRuta = true;

    var idPH = scope.programacionActual.idProgramacionHoraria;
    console.log(scope.programacionActual.idProgramacionHoraria);
    var config = {params: {accion: "agregarRuta", nombre: ruta.nombre, color: ruta.color, idProgramacion: idPH}, responseType: ""};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        console.log(respuesta);
        scope.rutas = respuesta;
        scope.rutaSeleccionada = scope.rutas[0];
    });
    agregarAExistente(scope);
}

function cargarRutas(scope, $http) {


    var idPH = scope.programacionActual.idProgramacionHoraria;
    console.log(scope.programacionActual.idProgramacionHoraria);
    var config = {params: {accion: "cargarRutas", idProgramacion: idPH}, responseType: ""};
    var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        console.log(respuesta);
        scope.rutas = respuesta;
    });
}

function agregarAExistente(scope) {
    scope.esconder_opcionesRuta = true;
    scope.esconder_botonGuardarRuta = true;
    scope.esconder_seleccionarRuta = false;
    scope.esconder_botonAgregarARuta = false;
}

function preCrearRuta(scope) {
    scope.esconder_opcionesRuta = true;
    scope.esconder_seleccionarRuta = true;
    scope.esconder_seleccionarRuta = true;
    scope.esconder_botonAgregarARuta = true;
    scope.esconder_crearRuta = false;
    scope.esconder_botonGuardarRuta = false;
//        agregarAExistente(scope);
}

function agregarARuta(scope, $http) {
//        var ruta=scope.rutaSeleccionada;
//        var circulaciones=[];
    var preCirculaciones = [];
    var indices = [];
    if (scope.chart.getSelectedPoints().length > 0) {
        for (var i = 0; i < scope.chart.getSelectedPoints().length; i++) {
//            circulaciones.push(scope.chart.getSelectedPoints()[i].series);
            preCirculaciones.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion);
        }
        console.log(scope.chart.getSelectedPoints()[0].series);
        for (i = 0; i < scope.chart.series.length; i++) {
            for (var j = 0; j < scope.chart.getSelectedPoints().length; j++) {
                if (scope.chart.series[i] === scope.chart.getSelectedPoints()[j].series) {
                    indices.push(i);
                }
            }
        }
        scope.rutaSeleccionada.preCirculaciones = preCirculaciones;
        console.log(scope.rutaSeleccionada.preCirculaciones[0].idPreCirculacion);


        var idPrecirculaciones = [];
        var idPH = scope.programacionActual.idProgramacionHoraria;
        for (i = 0; i < scope.rutaSeleccionada.preCirculaciones.length; i++) {
//            idPrecirculaciones.push(scope.rutaSeleccionada.circulaciones[i].options.preCirculacion.idPreCirculacion);
            idPrecirculaciones.push(scope.rutaSeleccionada.preCirculaciones[i].idPreCirculacion);
        }
        console.log(idPrecirculaciones);

//        var config = {params: {accion: "agregarARuta", idProgramacion:idPH,circulaciones:idPrecirculaciones,ruta:scope.rutaSeleccionada.rutaPK.idRuta}, responseType: ""};
        var config = {params: {accion: "agregarARuta",
                idProgramacion: idPH,
                circulaciones: idPrecirculaciones,
                ruta: scope.rutaSeleccionada.rutaPK.idRuta},
            responseType: "json"};
        var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
        llamada.success(function (respuesta) {
            scope.editando_circulacion = true;
            for (i = 0; i < preCirculaciones.length; i++) {
                scope.indiceSerieActual = indices[i];
                preCirculaciones[i].color = scope.rutaSeleccionada.color;

//            preCirculaciones[i].color=scope.rutaSeleccionada.color;
//            scope.chart.getSelectedPoints()[i].series.color=scope.rutaSeleccionada.color;
                scope.safeApply(function () {

//                scope.chart.getSelectedPoints()[i].series.update({
//                    color:scope.rutaSeleccionada.color
//                });
                    scope.chart.series[indices[i]].update({
                        name: scope.chart.series[indices[i]].name,
                        color: scope.rutaSeleccionada.color

                    });
                });
                scope.chart.series[indices[i]].options.preCirculacion.preCirculacionPK.idRuta = scope.rutaSeleccionada.rutaPK.idRuta;
                console.log(scope.chart.series[indices[i]].options.preCirculacion.preCirculacionPK.idRuta);
//            scope.chart.getSelectedPoints()[i].series.update();
//            calculosCirc(preCirculaciones[i], new Date(preCirculaciones[i].horaInicio), scope, $http);
            }
//        scope.chart.redraw();
            scope.editando_circulacion = false;
        });
    } else {
        alert("Selecciones Al Menos Una Circulación");
    }
    scope.esconder_ruta = true;
    scope.chart.hideLoading();
}
function desvincularDeRuta(scope, $http) {
//        var ruta=scope.rutaSeleccionada;
//        var circulaciones=[];
    if (scope.chart.getSelectedPoints().length > 0) {
        if (confirm('¿Desea Desvíncular Estas Circulaciones De Su Ruta?')) {
            var preCirculaciones = [];
            var indices = [];
            for (var i = 0; i < scope.chart.getSelectedPoints().length; i++) {
//            circulaciones.push(scope.chart.getSelectedPoints()[i].series);
                preCirculaciones.push(scope.chart.getSelectedPoints()[i].series.options.preCirculacion);
            }
            console.log(scope.chart.getSelectedPoints()[0].series);
            for (i = 0; i < scope.chart.series.length; i++) {
                for (var j = 0; j < scope.chart.getSelectedPoints().length; j++) {
                    if (scope.chart.series[i] === scope.chart.getSelectedPoints()[j].series) {
                        indices.push(i);
                    }
                }
            }
//        scope.rutaSeleccionada.preCirculaciones=preCirculaciones;
//        console.log(scope.rutaSeleccionada.preCirculaciones[0].idPreCirculacion);


            var idPrecirculaciones = [];
            var idPH = scope.programacionActual.idProgramacionHoraria;
            for (i = 0; i < preCirculaciones.length; i++) {
//            idPrecirculaciones.push(scope.rutaSeleccionada.circulaciones[i].options.preCirculacion.idPreCirculacion);
                idPrecirculaciones.push(preCirculaciones[i].idPreCirculacion);
            }
            console.log(idPrecirculaciones);

//        var config = {params: {accion: "agregarARuta", idProgramacion:idPH,circulaciones:idPrecirculaciones,ruta:scope.rutaSeleccionada.rutaPK.idRuta}, responseType: ""};
            var config = {params: {accion: "desvincularDeRuta",
                    idProgramacion: idPH,
                    circulaciones: idPrecirculaciones},
                responseType: "json"};
            var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
            llamada.success(function (respuesta) {
                scope.editando_circulacion = true;
                for (i = 0; i < preCirculaciones.length; i++) {
                    scope.indiceSerieActual = indices[i];
                    preCirculaciones[i].color = scope.rutas[0].color;
//            preCirculaciones[i].color=scope.rutaSeleccionada.color;
//            scope.chart.getSelectedPoints()[i].series.color=scope.rutaSeleccionada.color;
                    scope.safeApply(function () {
//                scope.chart.getSelectedPoints()[i].series.update({
//                    color:scope.rutaSeleccionada.color
//                });
                        scope.chart.series[indices[i]].update({
                            color: scope.rutas[0].color
                        });
                    });
//            scope.chart.getSelectedPoints()[i].series.update();
//            calculosCirc(preCirculaciones[i], new Date(preCirculaciones[i].horaInicio), scope, $http);
                }
//        scope.chart.redraw();
                scope.editando_circulacion = false;
            });

            scope.esconder_ruta = true;
            scope.chart.hideLoading();
        }
    } else {
        alert("Seleccione Al Menos Una Circulación");
    }
}

function eliminarRuta(scope, $http) {
    if (confirm('¿Desea Eliminar La Ruta?')) {

        var ruta = scope.rutaSeleccionada.rutaPK.idRuta;
        var idPH = scope.programacionActual.idProgramacionHoraria;
        if (ruta !== 0) {

            var preCirculaciones = [];
            var indices = [];
            console.log(scope.chart.series[0].options.preCirculacion);
            for (var i = 0; i < scope.chart.series.length; i++) {
                console.log(scope.chart.series[i].options.preCirculacion.preCirculacionPK.idRuta);
                if (scope.chart.series[i].options.preCirculacion.preCirculacionPK.idRuta === ruta) {

                    preCirculaciones.push(scope.chart.series[i].options.preCirculacion);

                    indices.push(i);
                }
            }
//        for (i = 0; i < scope.chart.series.length; i++) {
//        for (var j = 0; j < preCirculaciones.length; j++) {
//            if(scope.chart.series[i].options.preCirculacion===preCirculaciones[j]){
//                indices.push(i);
//            }
//        }
//    }
            var config = {params: {accion: "eliminarRuta",
                    idProgramacion: idPH,
                    idRuta: ruta},
                responseType: "json"};
            var llamada = $http.post("GMT", {id_linea: parseInt(scope.cmb_lineas)}, config);
            llamada.success(function (respuesta) {
                scope.rutas = respuesta;
                for (i = 0; i < preCirculaciones.length; i++) {

//            preCirculaciones[i].color=scope.rutas[0].color;
//            preCirculaciones[i].color=scope.rutaSeleccionada.color;
//            scope.chart.getSelectedPoints()[i].series.color=scope.rutaSeleccionada.color;
                    scope.safeApply(function () {
//                scope.chart.getSelectedPoints()[i].series.update({
//                    color:scope.rutaSeleccionada.color
//                });
                        scope.chart.series[indices[i]].update({
                            color: scope.rutas[0].color
                        });
                    });
//            scope.chart.getSelectedPoints()[i].series.update();
//            calculosCirc(preCirculaciones[i], new Date(preCirculaciones[i].horaInicio), scope, $http);
                }
            });
        } else {
            alert("La Ruta 0 No Puede Ser Eliminada");
        }
        scope.esconder_ruta = true;
        scope.chart.hideLoading();

    }
}

function comprobarHoraInicio2(scope, serie) {
    for (var i = 0; i < scope.chart.series.length; i++) {
//        console.log(serie.data[0].x <= scope.chart.series[i].xData[0]);
//        console.log(scope.chart.series[i].xData[0] <= serie.data[serie.data.length - 1].x);
//        console.log("||");
//        console.log(serie.data[0].x <= scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1]);
//        console.log(scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1] <= serie.data[serie.data.length - 1].x);
//        
        //modificado 01/06/2015
        //modificado 10/06/2015 agregar parentesis en el primer if
        if (serie.preCirculacion.marchaTipo.sentido === scope.chart.series[i].options.preCirculacion.marchaTipo.sentido
                && ((serie.data[0].x <= scope.chart.series[i].xData[0]
                        && scope.chart.series[i].xData[0] <= serie.data[serie.data.length - 1].x)
                        || (serie.data[0].x <= scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1]
                                && scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1] <= serie.data[serie.data.length - 1].x)
                        || (serie.data[0].x <= scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1]
                                && (serie.data[0].x <= scope.chart.series[i].xData[scope.chart.series[i].xData.length - 1])))) {
//                    console.log("Si entra");
            for (var j = 0; j < scope.chart.series[i].yData.length; j++) {
                for (var k = 0; k < serie.data.length; k++) {
                    //coincide la estacion

                    if (serie.data[k].y === scope.chart.series[i].yData[j]) {
//                        console.log("Estaciones iguales");
                        if (((scope.chart.series[i].xData[j] + scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000)) > (serie.data[k].x))
                                && (scope.chart.series[i].xData[j] - (scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000))) < (serie.data[k].x)) {

//                                console.log(new Date(scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo- (4 * 3600 * 1000)));
//                                console.log(new Date(scope.chart.series[i].xData[j]));
//                                console.log(new Date(scope.chart.series[i].xData[j] + (scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000))));
//                            console.log(new Date(serie.data[k].x));
//                            console.log(new Date(scope.chart.series[i].xData[j] - (scope.chart.series[i].options.preCirculacion.marchaTipo.tiempoMinimo - (4 * 3600 * 1000))));
                            console.log("Se sale del calculo y no agrega");
                            window.alert("La circulacion " + scope.chart.series[i].name + "\n esta muy cerca, no se puede agregar");
                            return false;
                        }
                    }
                }
            }
        }

    }
    return true;
}

//Agregado el 28/05/2015
// Modificado el 10-06-2015
//function cargarHorasAfluencia(scope, $http) {
//    var patron = /((0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){1})/;
//
//    var salida = [];
//    var dateFormat = "%H:%M";
//    var fecha = new Date(scope.afluencia_fecha);
//    var fechaMinima = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), 0, 0, 0);
//    var fechaMaxima = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), 23, 59, 0);
//
//    var config = {params: {accion: "cargarAfluenciaEstacion",
//            idLinea: scope.linea.idLinea,
////            idPkEstacion: scope.afluencia_estacion.estacionPK.idPkEstacion,
//            fechaMinima: fechaMinima,
//            fechaMaxima: fechaMaxima
//        },
//        responseType: "json"};
//    var llamada = $http.post("AdministrarAfluencia", {id_linea: parseInt(scope.cmb_lineas)}, config);
//    llamada.success(function (respuesta) {
//        console.log(respuesta);
//        console.log("------nuevo arreglo---------");
//        ordenarCirculaciones(scope);
//        // Array que tiene todas las horas de todas las estaciones
//        var horasEnEstaciones = [];
//        // Objeto q tiene todas las horas en la que los trenes pasan x la estacion señalada
//        var horasEnEstacion = {
//            idPkEstacion: "",
//            horas: []
//        };
//        // Objeto q tiene las horas de inicio y fin del intervalo con el cual se 
//        // calculara cuanta gente ha entrado a la estacion
//        var horaIFD = {
//            inicio: "",
//            fin: "",
//            diferencia: "",
//            diferenciaMinutos: ""
//        };
//        for (var l = 0; l < scope.estacionesLinea.length; l++) {
//            horasEnEstacion.idPkEstacion = scope.estacionesLinea[l].estacionPK.idPkEstacion;
////            console.log(horasEnEstacion.idPkEstacion + " l " + l);
//            horasEnEstaciones.push(horasEnEstacion);
//            horasEnEstacion = {
//                idPkEstacion: "",
//                horas: []
//            };
//        }
////        console.log(horasEnEstaciones);
//        console.log(scope.afluencia_sentido);
//if (scope.afluencia_sentido === "false") {
//                    horasEnEstaciones.reverse();
//                }
//        for (var i = 0; i < scope.chart.series.length; i++) {
//            //Mismo sentido
//            if (scope.chart.series[i].options.preCirculacion.marchaTipo.sentido.toString() === scope.afluencia_sentido) {
//
//                for (var k = 0; k < horasEnEstaciones.length; k++) {
////                    console.log("Hora en estaciones " + horasEnEstaciones[k].idPkEstacion);
//
//                    for (var j = 0; j < scope.chart.series[i].data.length; j++) {
//                        horaIFD = {
//                            inicio: "",
////                            fin: "",
//                            diferencia: "",
//                            diferenciaMinutos: "",
//                            salida: false
//                        };
//                        //Misma estacion
//                        if (horasEnEstaciones[k].idPkEstacion === scope.chart.series[i].data[j].y) {
//
//                            if (j > 0 && j < scope.chart.series[i].data.length - 1) {
//                                if (j % 2 === 0 && scope.chart.series[i].data[j].x !== scope.chart.series[i].data[j - 1].x) {
//
//                                    horaIFD.inicio = scope.chart.series[i].data[j].x;
//                                    if (horasEnEstaciones[k].horas.length !== 0) {
//                                        horaIFD.diferencia = scope.chart.series[i].data[j].x - horasEnEstaciones[k].horas[horasEnEstaciones[k].horas.length - 1].inicio;
//                                        horaIFD.diferenciaMinutos = (new Date(horaIFD.diferencia)).getUTCMinutes();
//                                    } else {
//                                        horaIFD.diferencia = (new Date(scope.chart.series[i].data[j].x).getUTCMinutes()) * 60 * 1000;
//                                        horaIFD.diferenciaMinutos = (new Date(scope.chart.series[i].data[j].x).getUTCMinutes());
//                                    }
//                                    horasEnEstaciones[k].horas.push(horaIFD);
//                                }
//
//                            } else {
//                                if (j === 0) {
//                                    horaIFD.salida = true;
//                                }
//                                horaIFD.inicio = scope.chart.series[i].data[j].x;
//                                if (horasEnEstaciones[k].horas.length !== 0) {
//                                    horaIFD.diferencia = scope.chart.series[i].data[j].x - horasEnEstaciones[k].horas[horasEnEstaciones[k].horas.length - 1].inicio;
//                                    horaIFD.diferenciaMinutos = (new Date(horaIFD.diferencia)).getUTCMinutes();
//                                } else {
////                                    horaIFD.diferencia = 0;
//                                    horaIFD.diferencia = (new Date(scope.chart.series[i].data[j].x).getUTCMinutes()) * 60 * 1000;
//                                    horaIFD.diferenciaMinutos = (new Date(scope.chart.series[i].data[j].x).getUTCMinutes());
//                                }
//                                horasEnEstaciones[k].horas.push(horaIFD);
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    if(scope.afluencia_sentido==="false"){
//       respuesta.reverse();
//    }
//        var afluenciaEstacion = respuesta[0];
//        console.log(respuesta[0]);
//        
//        var horasCirc = horasEnEstaciones[0].horas;
//        for (i = 0; i < afluenciaEstacion.length; i++) {
//            for (k = 0; k < horasCirc.length; k++) {
//                if ((new Date(afluenciaEstacion[i].afluenciaPK.idFechaInicial)).getUTCHours() <= (new Date(horasCirc[k].inicio)).getUTCHours()
//                        && (new Date(horasCirc[k].inicio)).getUTCHours() <= (new Date(afluenciaEstacion[i].fechaFinal)).getUTCHours()) {
//                    console.log("si diferenciaMinutos "+horasCirc[k].diferenciaMinutos);
//                    console.log((afluenciaEstacion[i].afluencia / (60 * 60 * 1000)) * horasCirc[k].diferencia);
//                    var capacidad=920;
//                    capacidad-=(afluenciaEstacion[i].afluencia / (60 * 60 * 1000)) * horasCirc[k].diferencia;
//                    console.log("capacidad Restante: "+capacidad);
//                } else {
//                    console.log("No");
//                }
//            }
//        }
//    });
//}    
function cargarHorasAfluencia(scope, $http) {
    var patron = /((0[0-9]|1[0-9]|2[0-3])(:[0-5][0-9]){1})/;

    var salida = [];
    var dateFormat = "%H:%M";
    var fecha = new Date(scope.afluencia_fecha);
    var fechaMinima = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), 0, 0, 0);
    var fechaMaxima = Date.UTC(fecha.getUTCFullYear(), fecha.getUTCMonth(), fecha.getUTCDate(), 23, 59, 0);

    var config = {params: {accion: "cargarAfluenciaEstacion",
            idLinea: scope.linea.idLinea,
//            idPkEstacion: scope.afluencia_estacion.estacionPK.idPkEstacion,
            fechaMinima: fechaMinima,
            fechaMaxima: fechaMaxima
        },
        responseType: "json"};
    var llamada = $http.post("AdministrarAfluencia", {id_linea: parseInt(scope.cmb_lineas)}, config);
    llamada.success(function (respuesta) {
        console.log(respuesta);
        console.log("------nuevo arreglo---------");
        ordenarCirculaciones(scope);
        var indexes = [];
        for (var i = 0; i < scope.chart.series.length; i++) {
            //Mismo sentido
            if (scope.chart.series[i].options.preCirculacion.marchaTipo.sentido.toString() === scope.afluencia_sentido) {
                indexes.push(i);
            }
        }
        console.log("indexes "+indexes);
//        if(scope.afluencia_sentido==="false"){
//            respuesta.reverse();
//        }
        for (i = 0; i < indexes.length; i++) {
//            console.log("indice "+i);
            if (i === 0) {
                for (var j = 0; j < scope.chart.series[i].data.length; j++) {
                    for (var k = 0; k < respuesta.length; k++) {
//                        console.log(respuesta[k][0].afluenciaPK.idPkEstacion+" "+scope.chart.series[i].data[j].y);
//                        console.log();
                        if(respuesta[k][0].afluenciaPK.idPkEstacion===scope.chart.series[i].data[j].y){
                            console.log("Entra "+respuesta[k].length);
                            for (var l = 0; l < respuesta[k].length; l++) {
                                
                                var soloHoraInicio=Date.UTC(1970,0,1,(new Date(respuesta[k][l].afluenciaPK.idFechaInicial)).getUTCHours(),(new Date(respuesta[k][l].afluenciaPK.idFechaInicial)).getUTCMinutes(),0);
                                var soloHoraFinal=Date.UTC(1970,0,1,(new Date(respuesta[k][l].fechaFinal)).getUTCHours(),(new Date(respuesta[k][l].fechaFinal)).getUTCMinutes(),0);
//                                console.log(soloHoraInicio);
//                                console.log(scope.chart.series[i].data[j].x);
//                                console.log(soloHoraFinal);
                                if(soloHoraInicio <= scope.chart.series[i].data[j].x
                                        &&scope.chart.series[i].data[j].x<=soloHoraFinal){
                                    var personas=(respuesta[k][l].afluencia)/60*(new Date(scope.chart.series[i].data[j].x)).getUTCMinutes();
                                    console.log("Estacion "+respuesta[k][l].afluenciaPK.idPkEstacion+" Personas "+personas);
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                for (var j = 0; j < scope.chart.series[i].data.length; j++) {
                    //Si no es el ultimo punto de la circulacion
                    if (j < scope.chart.series[indexes[i]].data.length - 1) {

//                        scope.chart.series[indexes[i]].data[j].x
                        for (var k = 0; k < scope.chart.series[indexes[i - 1]].data.length; k++) {
                            if (k === 0) {
                                
                            } else if (k === scope.chart.series[indexes[i - 1]].data.length - 1) {
                                
                            } else if (scope.chart.series[indexes[i]].data[k].y === scope.chart.series[indexes[i - 1]].data[k].y) {

                            }
                        }
                    }
                }

            }
        }
    });
}    