<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>

<%@include file="../jslt.jsp" %>
<c:set var="idMatRod" value="${param.idMatRod}"/>
<c:set var="vel" value="${param.vel}"/>

<c:choose>
    <c:when test="${!empty idMatRod}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>         
        <c:set var="curva" value="${gl.buscarCurva(idMatRod, vel)}"/>
        <div class="contenedorFormulario">
            <legend><h2>Editar curvas de esfuerzo de ${idMatRod}</h2></legend>
            <form>                
                <input type="hidden" id="id_mat_rod_ed" value="${idMatRod}" >
                <input type="hidden" id="id_vel_ed" value="${vel}" >
                <label class="tituloFormulario"> Esfuerzo de tracción</label>
                <input class="campoFormulario" type="text" id="txt_esf_tra_ed" value="${curva.esfuerzoTraccion}">                
                <label class="tituloFormulario"> Esfuerzo de frenado</label>
                <input class="campoFormulario" type="text" id="txt_esf_fre_ed" value="${curva.esfuerzoFrenado}">
                                
                <div id="contenedorImg">
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarCurva()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarC()">
                    
                </div>
            </form>
        </div>            
    </c:when>
    <c:otherwise>
        No Encontrado
    </c:otherwise>
    
</c:choose>
