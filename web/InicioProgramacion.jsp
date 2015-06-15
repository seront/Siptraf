<!DOCTYPE html>
<html ng-app="gmtApp">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@page contentType="text/html" pageEncoding="UTF-8"%>
        <%@include file="jslt.jsp" %>
        <%@include file="estilo.jsp" %>
        <script type="text/javascript" src="js/jquery-1.9.1.js"></script>        
        <script type="text/javascript" src="js/GMT/highcharts.js"></script>        
        <script type="text/javascript" src="js/GMT/exporting-src.js"></script>
        <script type="text/javascript" src="js/customEvents.js"></script>
        <script type="text/javascript" src="js/GMT/angular.js"></script>    
        <script type="text/javascript" src="js/GMT/angular-sanitize.js"></script>    
        <script type="text/javascript" src="js/GMT/PruebasAngular.js"></script>    
        <script type="text/javascript" src="js/confirmarSalida.js"></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <link href="css/estiloImpresion.css" type="text/css" rel="stylesheet" media="print"/>
        <title>S.I.P.T.R.A.F</title>
    </head>
    <body ng-controller="gmtAppCtrl as gac">
        <!--<div class="bgVentanaModal" ng-hide="gac.esconder_ventanaModal">-->
        <div id="bgVentanaModal" ng-hide="gac.esconder_ventanaModal">
            <div id="msModal" ng-hide="gac.esconder_ventanaModal">
                <span>Espere por favor...</span>
                <br>                
                <img id="car2" class="cargando2" src="img/382.png" alt="img/cargando2.gif"/>
            </div>            
        </div>
<!--        <div id="bgVentanaModal" class="dialogoModal">
            <div id="msjajax"></div>
            <div id="datos">                
            </div>

        </div>-->
        <header ng-hide="gac.esconder_header">
            <p class="titulo">Iniciar Programación Horaria</p>            
        </header>
        <main>
            <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
                        <li><a href="">${sessionScope.usuario}</a>
                            <ul>
                                <li onclick="cerrarSesion()"><a href="">Cerrar Sesión</a></li>
                                <li onclick="cambiarContraseña()"><a >Cambiar Contraseña</a></li>
                            </ul>
                        </li>

                    </ul>
                </div>

            </c:when>
            <c:otherwise>
                <!--<script src="js/sesion.js" type="text/javascript" rel=""></script>-->
                <script src="js/sesion.js" type="text/javascript"></script>
            </c:otherwise>
        </c:choose>
                
                <div id="irAlMenu" style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel<='2'}">
                
            
            <div class="encabezado">
                <div class="h1">                    
                    Gerencia de Gestión de Trafico
                </div>                
                <div class="h2">
                    Área de Servicios de Circulación Ferroviaria
                </div>                
                <div>
                    Horario de trenes(Solo sabados)
                </div>                
                <div>
                    Programacion Horaria {{gac.nombre_programacion}}
                </div>
            </div>

                <section class="contenedorFormulario" ng-hide="gac.esconderInicio" style="width: 450px;">
                <h2>
                   Nueva Programación horaria
                </h2>
                <div style="width: 100%;">
                    <input type="button" class="botonContinuar" value="Nueva Programación" ng-click="gac.iniciarProgramacion()" style="width: 100%;">
                </div>
                <h2 style="margin-top: 15px;">
                    Programaciones Existentes
                </h2>
                <div style="margin-top: 15px; width: 100%;">
                    <select ng-options="programacion.nombreProgramacionHoraria for programacion in gac.todasProgramaciones" ng-model="gac.programacionActual" style="width: 100%;">

                    </select>
                    </div>
                    <div class="contenedorBoton">                    
                        <input type="button" class="botonContinuar" value="Abrir" ng-click="gac.abrirProgramacion()" style="float:left;">
                        <input type="button" class="botonContinuar" value="Abrir como plantilla" ng-click="gac.abrirPlantilla()" style="float:left; margin-left: 10px;">
                        <input type="button" class="botonContinuar" value="Eliminar" ng-click="gac.eliminarProgramacion()" style="float:left; margin-left: 10px;">
                        <input type="button" class="botonContinuar" value="Rutas" ng-click="gac.abrirRutas()" style="float:left; margin-left: 10px;">
                    </div>
                
            </section>

            <section id="inicioProgramacion" ng-hide="gac.esconderNueva">
                <div class="contenedorFormulario" id="formGMT">
                    <h2>Datos para la nueva programación horaria</h2>
                    <form>
                        <div>
                            <div style="display: block; margin-left: 15px;">
                            <label class="tituloFormulario" >
                                Nombre de la programación
                            </label>
                            <input type="text" class="campoFormulario" title="Puede cambiarse luego" ng-model="gac.nombre_programacion">
                            </div>
                            <label class="tituloFormulario" style="margin-left: 15px;">
                                Prefijo 
                            </label>
                            <select class="campoFormulario" ng-options="(num.valor+' - '+num.nombre) for num in gac.grupoNumeraciones" ng-model="gac.prefijo_numeracion" style="width: 250px;">
                            </select>
                            <label class="tituloFormulario">Linea</label>
                            <select class="campoFormulario" id="cmb_lineas" ng-model="gac.cmb_lineas" ng-change="gac.cargarMarchas()" style="width: 250px;">
                                <option value="">Seleccione la Linea</option>
                                <jsp:useBean class="modelo.GestorLista" id="gl"/>
                                <c:forEach var="b" items="${gl.listaLinea()}">
                                    <option value="${b.idLinea}">${b.nombreLinea}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <section class="columna1">                      
                            
                            <div id="select_marchas_tipo_asc">
                                <label class="tituloFormulario">
                                    Marcha tipo ascendente predeterminada
                                </label>
                                <select class="campoFormulario" id="cmb_mar_tip_asc" ng-options="marcha.nombreMarchaTipo for marcha in gac.todas_mar_tip_asc" ng-model="gac.mar_tip_asc" title="Marcha tipo que se utilizará de forma predeterminada">

                                </select>
                                <label class="tituloFormulario">
                                    Color de las circulaciones Ascendentes
                                </label>
                                <input class="campoFormulario color" type="color" id="color_asc_pre" ng-model="gac.color_asc_pre" title="Color predeterminado de las circulaciones en este sentido">
                            </div>                            

                            <div id="tiem_parada_pred_asc" ng-repeat="estParada in gac.parada_pred_asc" ng-init="index = $index;">
                                <label class="tituloFormulario">Estación {{estParada.nombreEstacion}} (min) </label>
                                <input type="number" class="campoFormulario estacionesAsc" ng-model="gac.tiempo_parada_pred_asc[index]" title="Minutos de parada estandar en esta estacion" min="0" step="1"/>                       
                            </div>
                        </section>
                        <section class="columna2">
                            <div id="select_marchas_tipo_desc">
                                <label class="tituloFormulario">
                                    Marcha tipo descendente predeterminada
                                </label>
                                <select class="campoFormulario" id="cmb_mar_tip_desc" ng-options="marcha.nombreMarchaTipo for marcha in gac.todas_mar_tip_desc" ng-model="gac.mar_tip_desc"title="Marcha tipo que se utilizará de forma predeterminada">

                                </select>

                                <label class="tituloFormulario">
                                    Color de las circulaciones Descendentes
                                </label>
                                <input class="campoFormulario color" type="color" id="color_desc_pre" ng-model="gac.color_desc_pre" title="Color predeterminado de las circulaciones en este sentido">
                            </div>

                            <div id="tiem_parada_pred_desc" ng-repeat="estParada in gac.parada_pred_desc" ng-init="index = $index;">
                                <label class="tituloFormulario">
                                    Estación {{estParada.nombreEstacion}} (min)
                                </label>                               
                                <input type="number" class="campoFormulario estacionesDesc" id="{{estParada.estacionPK.idPkEstacion}}" ng-model="gac.tiempo_parada_pred_desc[index]" title="Minutos de parada estandar en esta estacion" min="0" step="1"/>                       
                            </div>
                        </section>                        

                        <div class="contenedorBoton">
                            <input type="button" class="botonContinuar" id="btn_continuar" value="Continuar" ng-click="gac.continuar()">
                        </div>
                    </form>
                </div>
            </section>

            <section id="area_trabajo" style="margin-top: 40px" ng-hide="gac.esconder_area_trabajo">                

                <section id="grp_botones_desc" ng-hide="gac.esconder_btn_desc">
                    <input type="button" value="Agregar Circulación Descendente"  ng-click="gac.insertarViaje(false)"/>
                    <!--<img class="img_gmt_2" alt="Agregar Circulación Descendente" src="img/33333.png"  ng-click="gac.insertarViaje(false)"/>-->
                    <input type="range" min="100" max="2000" step="50" id="rango" ng-model="gac.rango" ng-change="gac.zoom()"/>
                    <input type="button" value="Agregar Grupo Descedente" ng-click="gac.insertarGrupo(false)"/>                                              
                    <input type="button" value="Agregar numeración" ng-click="gac.insertarPrefijo()"/>                                              
                    <input type="button" value="Eliminar numeración" ng-click="gac.eliminarPrefijo()"/>                                              
                    <input type="button" value="Eliminar Circulacion" ng-click="gac.eliminarCirculacion()"/>                                              
                    <input type="button" value="Horas de llegada x estacion" ng-click="gac.preMostrarHorasEnEstacion()"/>                    
                </section>
                 
                <section ng-hide="gac.esconder_botones_ruta">
                    <input type="range" min="100" max="2000" step="50" id="rango" ng-model="gac.rango" ng-change="gac.zoom()"/>
                    <input type="button" value="Rutas" ng-click="gac.adminRutas()"/>                    
                    <input type="button" value="Agregar a ruta" ng-click="gac.preAgregarARuta()"/>                    
                    <input type="button" value="Desvíncular de ruta" ng-click="gac.desvincularDeRuta()"/>                    
                </section>
                <section id="grafico">
                    <div style="position: relative">
                        <section id="opciones" ng-style="gac.sec_opc_left" ng-hide="gac.esconder_opciones">
                            <div class="contenedorFormulario" id="cont_agregar" ng-hide="gac.esconder_agregar">                                                  
                                <h2>{{gac.opcion_nombre}}</h2>                                                    
                                <form>                        
                                    <div class="gmt_camp_tit">
                                        <label class="tituloFormulario">                            
                                            Marcha tipo
                                            <span ng-hide="!gac.editando_circulacion">
                                                Número {{gac.numCirActual}}
                                            </span>
                                        </label>
                                    </div>
                                    <select class="campoFormulario" id="cmb_mar_tip_ag" ng-options="marcha.nombreMarchaTipo for marcha in gac.todas_mar_tip" ng-disabled="gac.editando_circulacion" ng-change="gac.cambiarMarchaActual()" ng-model="gac.mar_tip_agr" title="Marcha tipo que se agregará">

                                    </select>
                                    <label class="tituloFormulario">
                                        Prefijo Numeración
                                    </label>

                                    <select class="campoFormulario" ng-options="(num.valor+' - '+num.nombre) for num in gac.grupoNumeraciones" ng-model="gac.prefijo_numeracion">

                                    </select>
                                    <div class="gmt_camp_tit">
                                        <div class="medio_gmt_tit">
                                            <label class="tituloFormulario gmt_opciones_titulos">Hora de inicio</label>        
                                        </div>
                                        <div class="medio_gmt_camp">
                                            <input type="time" class="campoFormulario gmt_opciones_campos texto" id="txt_hora_ini" pattern="0[0-9]|1[0-9]|2[0-3]:[0-5][0-9]{1}" ng-model="gac.horaInicio" ng-readonly="gac.editando_circulacion" title="HH:MM">
                                        </div>
                                    </div>

                                    <div class="gmt_camp_tit" ng-hide="!gac.insertando_grupo">
                                        <div class="medio_gmt_tit">
                                            <label class="tituloFormulario" ng-hide="!gac.insertando_grupo">Hora final</label>        
                                        </div>
                                        <div class="medio_gmt_camp">
                                            <input type="time" class="campoFormulario gmt_opciones_campos texto" id="txt_hora_ini" pattern="0[0-9]|1[0-9]|2[0-3]:[0-5][0-9]{1}" ng-model="gac.horaFinal" ng-hide="!gac.insertando_grupo" title="HH:MM">
                                        </div>
                                    </div>
                                    <div class="gmt_camp_tit" ng-hide="!gac.insertando_grupo">
                                        <div class="medio_gmt_tit">
                                            <label class="tituloFormulario" ng-hide="!gac.insertando_grupo">Frecuencia (min)</label> 
                                        </div>
                                        <div class="medio_gmt_camp">
                                            <input type="number" class="campoFormulario gmt_opciones_campos texto" id="txt_hora_ini" min="0" step="1" ng-model="gac.frecuencia_grupo" ng-hide="!gac.insertando_grupo" title="HH:MM">
                                        </div>
                                    </div>
                                    <div class="gmt_camp_tit">
                                        <div class="medio_gmt_tit">    
                                            <label class="tituloFormulario">
                                                Estacion de Salida
                                            </label>
                                        </div>
                                        <div class="medio_gmt_camp">
                                            <select class="campoFormulario gmt_opciones_campos" id="cmb_est_salida" ng-options="est.nombreEstacion for est in gac.cmb_est_salida" ng-model="gac.est_salida" ng-disabled="gac.editando_circulacion" ng-change="gac.agregarCamposTiempoParada()">

                                            </select>
                                        </div>
                                    </div>
                                    <div class="gmt_camp_tit">
                                        <div class="medio_gmt_tit">    
                                            <label class="tituloFormulario">
                                                Estacion de Llegada
                                            </label>
                                        </div>
                                        <div class="medio_gmt_camp">
                                            <select class="campoFormulario gmt_opciones_campos" ng-options="est.nombreEstacion for est in gac.cmb_est_llegada" ng-model="gac.est_llegada" ng-disabled="gac.editando_circulacion" ng-change="gac.agregarCamposTiempoParada()">

                                            </select>
                                        </div>
                                    </div>
                                    <div class="gmt_camp_tit">
                                        <label class="tituloFormulario">
                                            Paradas (min)
                                        </label>
                                    </div>
                                    <div id="estaciones_intermedias" ng-repeat="estParada in gac.estaciones_intermedias" ng-init="index = $index" >
                                        <div class="gmt_camp_tit">
                                            <div class="medio_gmt_tit">  
                                                <!--<label class="tituloFormulario">Estación {{estParada.nombreEstacion}} (min)</label>-->
                                                <label class="tituloFormulario "> {{estParada.nombreEstacion}}</label>
                                            </div>
                                            <div class="medio_gmt_camp">
                                                <input type="number" class="campoFormulario gmt_opciones_campos texto" ng-model="gac.tiempo_estaciones_intermedias[index].valor" title="Minutos de parada en esta estacion" min="0" step="1" ng-disabled="gac.editando_circulacion"/>
                                            </div>
                                        </div>     
                                    </div>
                                    <div class="gmt_camp_tit">
                                        <div class="medio_gmt_tit">  
                                            <label class="tituloFormulario">
                                                Color de la circulación
                                            </label>
                                        </div>
                                        <div class="medio_gmt_camp">                                        
                                            <input type="color" class="campoFormulario color gmt_opciones_campos" ng-model="gac.colorActual" ng-disabled="gac.editando_circulacion"/>
                                        </div>
                                    </div>
                                    <div class="contenedorBoton">                                        
                                        <input class="botonContinuar" type="button" id="btn_agr_cir" ng-value="gac.val_btn_ag" ng-click="gac.agregarCirculacion()" ng-hide="gac.editando_circulacion" ng-disabled="gac.habilitar_btn_agr_cir">

                                        <input class="botonContinuar" type="button" value="Editar" ng-click="gac.habilitarEdicion()" ng-hide="!gac.editando_circulacion" ng-disabled="gac.habilitar_btn_agr_cir">
                                        <input class="botonContinuar" type="button" value="Eliminar" ng-click="gac.eliminarCirculacion()" ng-hide="!gac.editando_circulacion" ng-disabled="gac.habilitar_btn_agr_cir">
                                        <input type="button" class="botonContinuar" id="btn_can" value="Cancelar" ng-click="gac.cancelar()">
                                    </div>
                                </form>
                            </div>
                            <div class="contenedorFormulario" id="numeraciones" ng-hide="gac.esconder_numeraciones">
                                <div ng-hide="gac.esconder_agregar_numeracion">
                                    <h2>
                                        Agregar Numeración
                                    </h2>
                                    <label class="tituloFormulario">
                                        Tipo de Numeración
                                    </label>
                                    <div class="grp_rbt">
                                        <label class="tituloFormulario">
                                            Ordinaria
                                        </label>
                                        <input class="campoFormulario" type="radio" name="tipo_numeracion" ng-model="gac.tipo_numeracion" value="false" ng-change="gac.construyePrefijo()" />               
                                        <label class="tituloFormulario">
                                            Cercanias
                                        </label>  
                                        <input class="campoFormulario" type="radio" name="tipo_numeracion" ng-model="gac.tipo_numeracion" value="true" ng-change="gac.construyePrefijo()"/>               
                                    </div>
                                    <div id="grp_select_num">
                                        <label class="tituloFormulario" ng-hide="gac.escSer">
                                            Tipo de Servicio
                                        </label>
                                        <select class="campoFormulario prefijoNumeracion" ng-options="(servicio.valor+' - '+servicio.descripcion) for servicio in gac.lista_tipo_servicio" ng-model="gac.prefijo[0]" ng-change="gac.construyePrefijo()" ng-hide="gac.escSer" title="Servicio del tren">
                                        </select>
                                        <label class="tituloFormulario">Categoria de Identificación</label>        
                                        <select class="campoFormulario prefijoNumeracion" ng-options="(catId.valor+' - '+catId.descripcion) for catId in gac.lista_cat_ident" ng-model="gac.prefijo[1]" ng-change="gac.construyePrefijo()" title="Categoria de identificacion">

                                        </select>
                                        <label class="tituloFormulario">
                                            CRT
                                        </label>        
                                        <select class="campoFormulario prefijoNumeracion" ng-options="(crt.valor+' - '+crt.descripcion) for crt in gac.lista_crt" ng-model="gac.prefijo[2]" ng-change="gac.construyePrefijo()" title="Centro de regulación de tráfico">
                                        </select>
                                        <label class="tituloFormulario">
                                            Empresa Propietaria
                                        </label>        
                                        <select class="campoFormulario prefijoNumeracion" ng-options="(ep.valor+' - '+ep.descripcion) for ep in gac.lista_emp_prop"  ng-model="gac.prefijo[3]" ng-change="gac.construyePrefijo()" title="Empresa Propietaria">
                                        </select>
                                        <label class="tituloFormulario">
                                            Complemento del prefijo
                                        </label>
                                        <input type="text" maxlength="5" class="campoFormulario" ng-model="gac.prefijo[5]">                                    
                                        <label class="tituloFormulario">
                                            Nombre de la numeración actual
                                        </label>                                    
                                        <input type="text" ng-model="gac.prefijo[6]" class="campoFormulario"/>                                
                                        Prefijo de la numeracion: <span ng-bind-html="gac.prefijo[5]"></span>
                                    </div>
                                </div>
                                <div ng-hide="gac.esconder_eliminar_numeracion">
                                    <label class="tituloFormulario">
                                        Prefijo Numeración
                                    </label>
                                    <select class="campoFormulario" ng-options="(num.valor+' - '+num.nombre) for num in gac.grupoNumeraciones" ng-model="gac.prefijo_numeracion">
                                    </select>
                                </div>
                                <div class="contenedorBoton">
                                    <input type="button" class="botonContinuar" value="Agregar Numeración" ng-click="gac.agregarNumeracion()" ng-hide="gac.esconder_agregar_numeracion">
                                    <input type="button" class="botonContinuar" value="Eliminar Numeración" ng-click="gac.eliminarNumeracion()" ng-hide="gac.esconder_eliminar_numeracion">
                                    <input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">
                                </div>
                            </div>
                            <div class="contenedorFormulario" ng-hide="gac.esconder_cambiarNombrePH">                                
                                <h2>
                                    Programación Horaria
                                </h2>
                                <label class="tituloFormulario">
                                    Nuevo nombre
                                </label>
                                <input type="text" class="campoFormulario" ng-model="gac.nomPH">                                
                                <div class="contenedorBoton">
                                    <input type="button" class="botonContinuar" value="Cambiar nombre" ng-click="gac.cambiarNombrePH()">
                                    <input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">
                                </div>
                            </div>
                            <div class="contenedorFormulario" ng-hide="gac.esconder_ruta">

                                <div class="contenedorBoton" ng-hide="gac.esconder_opcionesRuta">
                                    <input type="button" class="botonContinuar" value="Crear ruta" ng-click="gac.preCrearRuta()" style="float:left;">
                                    <input type="button" class="botonContinuar" value="Agregar a existente" ng-click="gac.agregarAExistente();" style="float: right;">
                                    <!--<input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">-->

                                </div>
                                <div ng-hide="gac.esconder_crearRuta">                                
                                    <h2>
                                        Crear ruta
                                    </h2>
                                    <label class="tituloFormulario">
                                        Nombre de ruta
                                    </label>
                                    <input type="text" class="campoFormulario" ng-model="gac.nomRuta">                                
                                    <input type="color" class="campoFormulario" ng-model="gac.colorRuta">                                
                                    <!--<input type="button" ng-click="gac.crearRuta()">-->                                
                                </div>
                                <div ng-hide="gac.esconder_seleccionarRuta" >
                                    <select ng-options="ruta.nombre for ruta in gac.rutas" ng-model="gac.rutaSeleccionada" style="min-width: 250px;">

                                    </select>
                                </div>

                                <div class="contenedorBoton" ng-hide="gac.esconder_botonGuardarRuta">
                                    <input type="button" class="botonContinuar" value="Guardar" ng-click="gac.crearRuta()">
                                    <input type="button" class="botonContinuar" value="Eliminar" ng-click="gac.eliminarRuta()">
                                    <input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">
                                </div>
                                <div class="contenedorBoton" ng-hide="gac.esconder_botonAgregarARuta">
                                    <input type="button" class="botonContinuar" value="Agregar a Ruta" ng-click="gac.agregarARuta()">
                                    <input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">
                                </div>
                                
                            </div>
                            <div class="contenedorFormulario" ng-hide="gac.esconder_afluencia">                                                                
                                    <h2>
                                        Afluencia
                                    </h2>
                                    <label class="tituloFormulario">
                                        Estación
                                    </label>                                    
                                    <!--<select class="campoFormulario" ng-options="estacion.nombreEstacion for estacion in gac.estacionesLinea" ng-model="gac.afluencia_estacion" ng-click="gac.cargarHorasAfluencia()">-->
                                    <!--<select class="campoFormulario" ng-options="estacion.nombreEstacion for estacion in gac.estacionesLinea" ng-model="gac.afluencia_estacion" ng-change="gac.cargarHorasAfluencia()">-->
                                    <select class="campoFormulario" ng-options="estacion.nombreEstacion for estacion in gac.estacionesLinea" ng-model="gac.afluencia_estacion">

                                    </select>
                                <label class="tituloFormulario">
                                        Sentido
                                    </label>
                                    <div class="grp_rbt">
                                        <label class="tituloFormulario">
                                            Ascendente
                                        </label>
                                        <input class="campoFormulario" type="radio" name="sentido_afluencia" ng-model="gac.afluencia_sentido" value="true" ng-change="gac.cargarHorasAfluencia()" />
                                        <label class="tituloFormulario">
                                            Descendente
                                        </label>  
                                        <input class="campoFormulario" type="radio" name="sentido_afluencia" ng-model="gac.afluencia_sentido" value="false" ng-change="gac.cargarHorasAfluencia()"/>               
                                    </div>
                                <input type="date" class="campoFormulario" ng-model="gac.afluencia_fecha" ng-change="gac.cargarHorasAfluencia()"/>
<!--                                <input type="time" class="campoFormulario" ng-model="gac.afluencia_horaInicio"/>
                                <input type="time" class="campoFormulario" ng-model="gac.afluencia_horaFin"/>-->
                                <table>
                                    <tr ng-repeat="hora in gac.horasEstacion track by $index" ng-init="index=$index;">
                                        <td>{{hora}}</td>
                                    </tr>
                                </table>
                                <div class="contenedorBoton">                                    
                                    <input type="button" class="botonContinuar" value="Cancelar" ng-click="gac.cancelar()">
                                </div>
                            </div>
                        </section>
                        <div class="cont_grafico">                            
                            <div id="graficaLineal">

                            </div>
                        </div>
                    </div>
                </section>

                <section id="grp_botones_asc" ng-hide="gac.esconder_btn_asc">                    
                    <input type="button" value="Agregar Circulación Ascedente" ng-click="gac.insertarViaje(true)"/>
                    <!--<img class="img_gmt_2" alt="Agregar Circulación Ascedente" title="Agregar Circulación Ascedente"  src="img/4444.png"ng-click="gac.insertarViaje(true)"/>-->
                    <input type="button" value="Cambiar nombre de la PH" ng-click="gac.preCambiarNombrePH()"/>
                    <!--<input type="range" min="100" max="1000" step="20" id="rango" ng-model="gac.rango" ng-change="gac.zoom()"/>-->
                    <input type="range" min="100" max="2100" step="50" id="rango" ng-model="gac.rango" ng-change="gac.zoom()"/>
                    <input type="button" value="Agregar Grupo Ascedente" ng-click="gac.insertarGrupo(true)"/>                       
                    <!--<img alt="Agregar Grupo Ascedente" class="img_gmt_4" title="Agregar Grupo Ascedente"  src="img/11111.png"ng-click="gac.insertarGrupo(true)"/>-->                       
                    <input type="button" value="Imprimir grafico" ng-click="gac.imprimirGrafico()">
                    <input type="button" value="Guardar progreso" ng-click="gac.guardarProgramacion()">
                </section>

            </section>


          <section  class="imprimible" ng-hide=" gac.esconderTH">
                <br>
                <br>
                <div class="datagrid">
                    <table class="tablas tablaCirculaciones" id="tablaCirculaciones">
                        <tr>
                            <td>
                                Nº
                            </td>                        
                            <td>
                                Via Sal
                            </td>                        
                            <td ng-repeat="estacion in gac.estacionesLinea" ng-init="index = $index;" colspan="{{((index > 0 && index < gac.estacionesLinea.length - 1)? 2 :1)}}">
                                <!--{{estacion.nombreEstacion}}-->
                                {{estacion.abrevEstacion}}
                            </td>
                            <td>
                                Via Lleg
                            </td>
                            <td>
                                Observaciones
                            </td>
                        </tr>
                        <tr ng-repeat=" fila in gac.tablaHoraria.filasAscendentes">
                            <td>
                                {{fila.numeroCirculacion}}
                            </td>
                            <!--modificado el 29/05/2015 este td -->
                            <td>
                                {{fila.viaSalida}}
                            </td>
                            <td ng-repeat="hora in fila.horaEnEstacion track by $index">
                                {{hora}}
                            </td>
                            <!--modificado el 29/05/2015 estos 2 td -->
                            <td>
                                {{fila.viaLlegada}}
                            </td>
                            <td>
                                {{fila.observacion}}
                            </td>
                        </tr>                    
                    </table>
                </div>
                <div class="saltoDePagina">

                </div>
                <div class="datagrid">
                    <table class="tablas tablaCirculaciones">
                        <tr>
                            <td>
                                Nº
                            </td>
                            <td>
                                Via Sal
                            </td>
                            <td ng-repeat="estacion in gac.estacionesLinea| orderBy:'-estacionPK.idPkEstacion'" ng-init="index = $index;" colspan="{{((index > 0 && index < gac.estacionesLinea.length - 1)? 2 :1)}}">
                                <!--{{estacion.nombreEstacion}}-->
                                {{estacion.abrevEstacion}}
                            </td>
                            <td>
                                Via Lleg
                            </td>
                            <td>
                                Observaciones
                            </td>
                        </tr>
                        <tr ng-repeat=" fila in gac.tablaHoraria.filasDescendentes">
                            <td>
                                {{fila.numeroCirculacion}}
                            </td>
                            <!--modificado el 29/05/2015 este td -->
                            <td>
                               {{fila.viaSalida}}
                            </td>
                            <td ng-repeat="hora in fila.horaEnEstacion track by $index">
                                {{hora}}
                            </td>
                            <!--modificado el 29/05/2015 estos 2 td -->
                            <td>
                                {{fila.viaLlegada}}
                            </td>
                            <td>
                                {{fila.observacion}}
                            </td>
                        </tr>                    
                    </table>
                </div>
            </section>
<!--            <section  class="imprimible" ng-hide=" gac.esconderHR">
                <br>
                <br>
                <div class="datagrid">
                    <div ng-repeat=" arrayRutas in gac.arrayDeFilasRutas track by $index" ng-init="index = $index;">
                    <table class="tablas tablaCirculaciones" id="tablaCirculaciones">
                        <tr>
                            <td class="nombreRuta" colspan="{{ ((gac.estacionesLinea.length-2)*2)+5}}">
                                {{arrayRutas[0].nombre}}
                            </td>
                            
                        </tr>
                        <tr>
                                                  
                            <td>
                                Nº
                            </td>                        
                            
                            <td ng-repeat="estacion in gac.estacionesLinea" ng-init="index = $index;" colspan="{{((index > 0 && index < gac.estacionesLinea.length - 1)? 2 :1)}}">
                                {{estacion.nombreEstacion}}
                                {{estacion.abrevEstacion}}
                            </td>
                            <td>
                                Nº
                            </td>                        
                            <td>
                                TM
                            </td>                        
                        </tr>
                        
                        <tr ng-repeat=" fila in arrayRutas track by $index" ng-init="index = $index;">
                        <tr >
                            
                            <td>
                            <div ng-hide="!fila.sentido">
                                    {{fila.numeroCirculacion}}
                                </div>
                            </td>
                            
                            <td ng-repeat="hora in fila.horaEnEstacion track by $index" >
                                {{hora}}
                            </td>
                            <td ng-repeat="hora in fila.horaEnEstacion track by $index| orderBy:'-hora'" ng-hide="fila.sentido">
                                {{hora}}
                            </td>
                            
                            <td>
                                <div ng-hide="fila.sentido">
                                    {{fila.numeroCirculacion}}
                                </div>
                                
                            </td>
                            <td>
                                {{fila.tiempoMarcha}}
                            </td>
                            
                        </tr> 
                        <tr ng-repeat=" fila in gac.tablaHoraria.filasDescendentes">
                            <td>
                                {{fila.numeroCirculacion}}
                            </td>
                            <td ng-repeat="hora in fila.horaEnEstacion track by $index">
                                {{hora}}
                            </td>
                            <td>
                                {{fila.numeroCirculacion}}
                            </td>
                            
                        </tr>  
                    </table>
                        <div class="saltoDePagina">

                </div>
                </div>
            </section>-->
                                </c:if>
        </main>
        <footer ng-hide="gac.esconder_footer">
            <%@include file="footer.jsp" %>
        </footer>

    </body>
</html>
