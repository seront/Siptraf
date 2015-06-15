<%@include file="../../jslt.jsp"%>

<c:set var="tipoNumeracion" value="${param.tipoNumeracion}"></c:set>
    
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:choose>
    <c:when test="${tipoNumeracion==true}">
        <!--Numeracion ordinaria, 4 digitos identificadores-->
        <label class="tituloFormulario">Tipo de Servicio</label>
        
        <select class="campoFormulario prefijoNumeracion" id="cmb_tipo_ser" ng-model="gac.cmb_tipo_ser" ng-change="gac.hola()" title="Servicio del tren">
            <option value="">Seleccione el Tipo de Servicio</option>          
            <c:forEach var="servicio" items="${gl.listarServicio()}">
                <option value="${servicio.valor}">${servicio.valor} - ${servicio.descripcion}</option>
            </c:forEach>
        </select>
        <label class="tituloFormulario">Categoria de Identificación</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_cat_id" title="Categoria de identificacion">
            <option value="">Seleccione la Categoria de Identificación</option>          
            <c:forEach var="catId" items="${gl.listarCategoriaIdentificacion()}">
                <option value="${catId.valor}">${catId.valor} - ${catId.descripcion}</option>
            </c:forEach> 
        </select>
        <label class="tituloFormulario">CRT</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_crt" title="Centro de regulación de tráfico">
            <option value="">Seleccione el CRT</option>          
            <c:forEach var="crt" items="${gl.listarCRT()}">
                <option value="${crt.valor}">${crt.valor} - ${crt.descripcion}</option>
            </c:forEach>
        </select>
        <label class="tituloFormulario">Empresa Propietaria</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_emp_pro" title="Empresa Propietaria">
            <option value="">Seleccione la Empresa Propietaria</option>          
            <c:forEach var="ep" items="${gl.listarEmpresaPropietaria()}">
                <option value="${ep.valor}">${ep.valor} - ${ep.descripcion}</option>
            </c:forEach>
        </select>
    </c:when>
    <c:otherwise>
        <label class="tituloFormulario">Categoria de Identificación</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_cat_id" title="Categoria de identificacion">
            <option value="">Seleccione la Categoria de Identificación</option>          
            <c:forEach var="catId" items="${gl.listarCategoriaIdentificacion()}">
                <option value="${catId.valor}">${catId.valor} - ${catId.descripcion}</option>
            </c:forEach> 
        </select>
        <label class="tituloFormulario">CRT</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_crt" title="Centro de regulación de tráfico">
            <option value="">Seleccione el CRT</option>          
            <c:forEach var="crt" items="${gl.listarCRT()}">
                <option value="${crt.valor}">${crt.valor} - ${crt.descripcion}</option>
            </c:forEach>
        </select>
        <label class="tituloFormulario">Empresa Propietaria</label>        
        <select class="campoFormulario prefijoNumeracion" id="cmb_emp_pro" title="Empresa Propietaria">
            <option value="">Seleccione la empresa propietaria</option>          
            <c:forEach var="ep" items="${gl.listarEmpresaPropietaria()}">
                <option value="${ep.valor}">${ep.valor} - ${ep.descripcion}</option>
            </c:forEach>
        </select>
    </c:otherwise>
</c:choose>
