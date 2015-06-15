<%@page import="modelo.GestorLista"%>
<%@include file="../../jslt.jsp" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="idLinea" value="${param.id_linea}">    
</c:set>
<c:set var="idMT" value="${param.idMT}">    
</c:set>
<c:set var="nomMT" value="${param.nomMT}">    
</c:set>
<c:set var="sentido" value="${param.sentido}">    
</c:set>

<div class="contenedorFormulario">
    <h2>Agregar circulación</h2>
    <form>
        <input type="hidden" value="${sentido}" id="hdd_sentido_ag">
        <label class="tituloFormulario">
            <input type="button" value="Marcha tipo" title="Cambiar marcha tipo" id="btn_cam_MT">
        </label>
        <select class="campoFormulario" id="cmb_mar_tip_asc_ag" title="Marcha tipo que se agregará" disabled>
            <option value="${idMT}">${nomMT}</option>
        </select>         

        <label class="tituloFormulario">Hora de inicio</label>        
        <input type="time" class="campoFormulario" id="txt_hora_ini" title="HH:MM">

        <!--<div id="contenedorEstaciones" onload="agregarCamposTiempoParada($('#cmb_mar_tip_asc_ag').val(), $('#cmb_est_salida').val(), $('#cmb_est_llegada').val())">-->
        <div id="contenedorEstaciones">
            <label class="tituloFormulario">
            Estacion de Salida
        </label>
        
        <div id="est_salida">
            <jsp:useBean class="modelo.GestorLista" id="gl"/>
            <select class="campoFormulario cmb_est" id="cmb_est_salida">
                <%--<c:forEach var="estSalida" items="${gl.buscarParadasSalida(idMT)}">--%>
                <c:set var="estSalida" value="${gl.buscarParadasSalida(idMT)}">                    
                </c:set>
                <option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
                    ${estSalida.nombreEstacion}
                </option>
                <%--</c:forEach>--%>
                <c:forEach var="estSalida" items="${gl.buscarParadasMT(idMT)}">
                    <option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
                        ${estSalida.nombreEstacion}
                    </option>
                </c:forEach>                    
            </select>
        </div>
        <label class="tituloFormulario">
            Estacion de Llegada
        </label>
        <div id="est_llegada">           
            <select class="campoFormulario cmb_est" id="cmb_est_llegada">                
                <c:set var="estSalida" value="${gl.buscarParadasLlegada(idMT)}">                    
                </c:set>
                <option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
                    ${estSalida.nombreEstacion}
                </option>                
                <c:forEach var="estSalida" items="${gl.buscarParadasMT(idMT)}">
                    <option value="${estSalida.tiempoEstacionMarchaTipoPK.idPkEstacion}">
                        ${estSalida.nombreEstacion}
                    </option>
                </c:forEach>

            </select>
        </div>
        </div>
        
                <div class="cargando">
                    <img class="cargando" id="car1" src="img/ajax-loader.gif"/>
                </div>
                
        <div id="estaciones_intermedias">

        </div>


        <div id="color_chooser">

        </div>

        <div class="contenedorBoton">
            <input class="botonContinuar" type="button" id="btn_agr_cir" value="Agregar">
        </div>

        <div class="contenedorBoton">
            <input type="button" class="botonContinuar" id="btn_can" value="Cancelar">
        </div>
    </form>
</div>


