<%@include file="../jslt.jsp" %>
<script src="js/administrarLinea.js" type="text/javascript"></script>
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="lin" value="${gl.buscarLinea(id)}"/>       
        <div class="contenedorFormulario">
            <legend><h2>Eliminar Línea ${lin.nombreLinea}</h2></legend>
            <form>
                <input type="hidden" name="hdd_nom_lin" value="${lin.nombreLinea}" >
                <p>¿Está seguro que quieres eliminar La Línea ${lin.nombreLinea}?,
                    pulse Eliminar para continuar, pulse Cancelar para salir.<br/> 
                    Al eliminar se eliminaran todos los datos relacionados a esta línea.</p>
                
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarLinea()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarL('${lin.nombreLinea}')">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Linea no Encontrada
    </c:otherwise>
</c:choose>