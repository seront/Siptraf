<%-- 
    Document   : editarUsuario
    Created on : 16/04/2015, 01:59:31 PM
    Author     : Kelvins Insua
--%>
<%@page import="modelo.GestorLista"%>
<%@include file="../jslt.jsp" %>
<script src="js/administrarUsuarios.js" type="text/javascript"></script>
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="u" value="${gl.buscarUsuario(id)}"/>       
        <div class="contenedorFormulario">
            <legend><h2>Editar Usuario ${id}</h2></legend>
            <form>
                <label class="tituloFormulario">Nombre</label>
                <input type="text" id="txt_nom_usu_ed" class="campoFormulario"  placeholder="Nombre" value="${u.nombre}" required>
                    <label class="tituloFormulario">Apellido</label>
                    <input type="text" id="txt_ape_usu"_ed class="campoFormulario"  placeholder="Apellido " value="${u.apellido}" required>
                    <label class="tituloFormulario">Contraseña</label>
                    <input type="password" id="pass_usu_ed" class="campoFormulario"  placeholder="**********" required>
                    <label class="tituloFormulario">Confirmar Contraseña</label>
                    <input type="password" id="pas_usu2"_ed class="campoFormulario"  placeholder="**********" required>
                    <label class="tituloFormulario">Nivel</label>
                    <select id="nivel_usu_ed" class="campoFormulario">
                        <option value="">Seleccione Nivel</option>
                        <option value="1">Administrador</option>
                        <option value="2">Programador</option>
                        <option value="3">Consultor</option>
                    </select>
                
                    <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarUsuario()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarUsuario()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Línea no Encontrada
    </c:otherwise>

</c:choose>