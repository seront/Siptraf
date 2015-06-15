<%@page import="modelo.GestorLista"%>
<%@include file="../jslt.jsp" %>
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="lin" value="${gl.buscarLinea(id)}"/>       
        <div class="contenedorFormulario">
            <legend><h2>Editar Línea ${lin.nombreLinea}</h2></legend>
            <form>
                <input type="hidden" id="hdd_id_linea_ed" value="${lin.idLinea}" >
                <label class="tituloFormulario">Nombre de la Línea</label>
                <input class="campoFormulario" type="text" id="txt_nom_lin_ed" value="${lin.nombreLinea}" >
                <label class="tituloFormulario">Punto Kilométrico Inicial</label>
                <input class="campoFormulario" type="number" id="num_prog_ini_ed" value="${lin.pkInicial}" >
                <label class="tituloFormulario"> Punto Kilométrico Final</label>
                <input class="campoFormulario" type="number" id="num_prog_fin_ed" value="${lin.pkFinal}" >
                <label class="tituloFormulario">Trocha</label>
                <input class="campoFormulario" type="number" id="num_tro_ed" value="${lin.trocha}">
                <label class="tituloFormulario">Velocidad Máxima</label>
                <input class="campoFormulario" type="number" id="velocidad_linea_ed" value="${lin.velocidadLinea}">
                 <select class="campoFormulario" id="tipo_via_ed" required>
                            <option value="true">Doble</option>
                            <option value="false">Única</option>
                        </select>
                
                 <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarLinea()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarL()">
                </div>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        Línea no Encontrada
    </c:otherwise>

</c:choose>