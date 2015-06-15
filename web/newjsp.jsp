<%-- 
    Document   : index
    Created on : 03/01/2015, 07:39:14 PM
    Author     : seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/jquery-1.9.1.js"></script>
        <script src="js/marchaTipo.js"></script>
        <script type="text/javascript" src="js/highcharts.js"></script>
        <script type="text/javascript" src="js/exporting.js"></script>
        <script type="text/javascript" src="js/graficos.js"></script>
        <link href="css/estilo.css" type="text/css" rel="stylesheet"/>
        <%@include file="jslt.jsp" %>
        <title>SIPTRAF</title>
    </head>
    <body>
        <header>
            <%--<%@include file="encabezado.jsp" %>--%>
            <p class="titulo">Realizar Marcha tipo</p>
        </header>
        <div class="contenedorFormulario">

            <legend> <h2>Datos para la marcha tipo</h2></legend>
            <!--<form method="get" action="SERVLETAQUI">-->
            <!--                <form class="contenedorFormulario">-->
            <form >
                <label class="tituloFormulario">Velocidad de la marcha tipo</label> 
                <input class="campoFormulario" type="number" id="velocidad" min="0">
                <label class="tituloFormulario">Linea </label> 
                <select id="cmb_lineas" class="campoFormulario" >
                    <option value=""> Seleccione...</option>
                    <jsp:useBean class="modelo.GestorLista" id="gl"/>
                    <c:set var="lineas" value="${gl.listaLinea()}"/>
                    <c:forEach var="linea" items="${lineas}">                        
                        <option value="${linea.idLinea}"> ${linea.idLinea}-${linea.nombreLinea}</option>                        
                    </c:forEach>
                </select>
                <label class="tituloFormulario">Material rodante </label> 
                <select id="cmb_materiales" class="campoFormulario">
                    <option value=""> Seleccione...</option>

                    <c:set var="materiales" value="${gl.listaMaterialRodante()}"/>
                    <c:forEach var="material" items="${materiales}">                       
                        <option value="${material.idMaterialRodante}"> ${material.idMaterialRodante}-${material.nombreMaterialRodante}</option>                        
                    </c:forEach>
                </select> 
                <div id="estaciones">
                    <p class="cargando"><image class="cargando" src="img/ajax-loader.gif"/></p>
                    <label class="tituloFormulario">Estacion inicial </label> 
                    <select id="cmb_est_inicio" class="campoFormulario">
                        <option value=""> Seleccione...</option>
                    </select>
                    <label class="tituloFormulario">Estacion final </label> 
                    <select id="cmb_est_final" class="campoFormulario">
                        <option value=""> Seleccione...</option>   
                    </select>
                </div>
                <div class="contenedorBoton">
                    <input type="button" id="continuar" class="botonContinuar" value="Continuar"/> 
                </div>
            </form>

        </div>
        <div id="marcoRestricciones">            
            
        </div>
                    <div id="resultadoMarchaTipo" class="contenedorFormulario">   
                        <div id="msj">                           
                            <p><image class="cargando" src="img/ajax-loader.gif"/></p>
                        </div>
                    </div>
                     <div id="graficoMarchaTipo">
                        
                    </div>
        <footer>
            <%@include file="footer.jsp"%>
        </footer>
    </body>
</html>