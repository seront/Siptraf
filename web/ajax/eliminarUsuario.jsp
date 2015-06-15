<%-- 
    Document   : eliminarUsuario
    Created on : 16/04/2015, 02:16:38 PM
    Author     : Kelvins Insua
--%>

<%@include file="../jslt.jsp" %>
<script src="js/administrarUsuarios.js" type="text/javascript"></script>
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="u" value="${gl.buscarUsuario(id)}"/>       
        <div class="contenedorFormulario">
            <legend><h2>Eliminar Usuario ${id}</h2></legend>
            <form>
                <input type="hidden" id="id_usu_el" value="${id}" >
                <p>¿Está seguro que quieres eliminar El Usuario ${id}?,
                    pulse Eliminar para continuar, pulse Cancelar para salir.</p>
                    
                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarUsuario()">
                    <img src="img/icon/delete-icon.png" alt="Eliminar" title="Eliminar"onclick="eliminarUsuario()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Linea no Encontrada
    </c:otherwise>
</c:choose>