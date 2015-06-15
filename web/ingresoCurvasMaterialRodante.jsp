<%-- 
Document   : index
Created on : 11/11/2014, 09:30:34 AM
Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="jslt.jsp" %>
        <title>S.I.P.T.R.A.F</title>
        <%@include file="estilo.jsp" %>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <script type="text/javascript" src="js/jquery-1.9.1.js"></script>
        <script type="text/javascript" src="js/administrarCurvaEsfuerzos.js"></script>
        <script type="text/javascript" src="js/graficos.js"></script>
        <script type="text/javascript" src="js/highcharts.js"></script>
        <script type="text/javascript" src="js/exporting.js"></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <c:set var="velocidades" value="${gl.buscarVelocidadesMR(idMatRod)}"></c:set>
        <c:set var="traccion" value="${gl.buscarTraccionMR(idMatRod)}"></c:set>
        <c:set var="frenado" value="${gl.buscarFrenadoMR(idMatRod)}"></c:set>
        </head>
        <body >
            <div id="bgVentanaModal">
                <div id="datos">                
                </div>
            </div>
            <header>
                <p class="titulo">Curvas de Tracción y Frenado</p>
            </header>
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
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
            </c:otherwise>
        </c:choose>
        <main>
            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel=='1'}">
                <div class="contenedorFormulario">            
                    <legend><h2>Tracción y frenado del Material Rodante</h2></legend>
                    <p style="font-size: 20px;">Importante: para ingresar decimales utilice puntos. Ejemplo: 10.5</p>
                    <form>
                        <label class="tituloFormulario">Material Rodante</label>
                        <select id="cmb_materiales" class="campoFormulario">
                            <option value=""> Seleccione...</option>
                            <c:set var="materiales" value="${gl.listaMaterialRodante()}"/>
                            <c:forEach var="material" items="${materiales}">                       
                                <option value="${material.idMaterialRodante}"> ${material.idMaterialRodante}-${material.nombreMaterialRodante}</option>                        
                            </c:forEach>
                        </select> 
                        <label class="tituloFormulario">Velocidad (km/h)</label>
                        <input type="text" id="txt_vel_mat_rod" class="campoFormulario" placeholder="Velocidad (km/h)" required min="1">
                        <label class="tituloFormulario">Esfuerzo Tractivo (kgf)</label>
                        <input type="text" id="txt_esf_tra" class="campoFormulario" placeholder="Esfuerzo Tractivo (kgf)" required>
                        <label class="tituloFormulario">Esfuerzo de Frenado (kgf)</label>
                        <input type="text" id="txt_esf_fre" class="campoFormulario" placeholder="Esfuerzo de Frenado (kgf)" required>
                        <div id="contenedorImg">
                            <!--<input type="button" class="botonContinuar" id="btn_agr_curv" value="Agregar">-->
                            <img src="img/icon/add-icon.png" alt="Agregar" title="Agregar" onclick='agregarCurva($("#cmb_materiales").val(), $("#txt_vel_mat_rod").val(), $("#txt_esf_tra").val(), $("#txt_esf_fre").val());'>
                        </div>
                    </form>
                </div>
                <div class="contenedorFormulario" id="msj">                        
                </div>
                <div class="contenedorFormulario" id="tablaCurvas">
                </div>
                <div id="graficoEsfuerzosVar">
                </div>
                <div id="graficoEsfuerzos" style=" margin-top:15px; ">
                </div>
            </main>
        </c:if>
        <footer>
            <%@include file="footer.jsp"%>
        </footer>
    </body>
</html>
