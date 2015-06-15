<%-- 
    Document   : datosDocTren
    Created on : 26/03/2015, 09:11:05 AM
    Author     : Kelvins Insua
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../jslt.jsp" %>
<c:set var="idLinea" value="${param.idLinea}"></c:set>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <jsp:useBean class="modelo.GestorLista" id="gl"/>
<!DOCTYPE html>
<div class="contenedorFormulario">
            <legend><h2>Datos para el Documento de Tren</h2></legend>
            <form action="AdministrarRestriccion" method="post">
                <input type="hidden" value="${idLinea}" name="idLinea">
                <input type="hidden" value="Generar DocTren" name="accion">
                <label class="tituloFormulario">Documento de Tren</label>
                <input class="campoFormulario" name="nombre" type="text" id="nro_doc_tren" maxlength="50" required>
                <label class="tituloFormulario">Nota:</label>
                <input class="campoFormulario" type="text" id="nota" name="nota" maxlength="110">
                <label class="tituloFormulario">Nota:</label>
                <input class="campoFormulario" type="text" id="nota" name="nota2" maxlength="300">
                <label class="tituloFormulario">Instrucciones</label>
                <input class="campoFormulario" type="text" id="instrucciones" name="instrucciones" maxlength="730">
                <label class="tituloFormulario">Comunicaciones</label>
                <input class="campoFormulario" type="text" id="comunicaciones" name="comunicaciones" maxlength="730">
                <label class="tituloFormulario">Precauciones</label>
                <input class="campoFormulario" type="text" id="precauciones" name="precauciones" maxlength="730">
                <label class="tituloFormulario">Vigencia desde:</label>
                <input class="campoFormulario" type="datetime"  name="vigencia" id="vigencia" placeholder="DD-MM-AAAA" required="">
                <select id="cmb_materiales" name="materialRodante" class="campoFormulario">
                            <option value="" > Seleccione...</option>

                            <c:set var="materiales" value="${gl.listaMaterialRodante()}"/>
                            <c:forEach var="material" items="${materiales}">                       
                                <option value="${material.idMaterialRodante}"> ${material.idMaterialRodante}-${material.nombreMaterialRodante}</option>                        
                            </c:forEach>
                        </select>
                            <div id="contenedorImg">
                                <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar" onclick="cancelarRestriccion()">
                                <input class="botonContinuar" type="submit" value="" id="btn_doc_tren" title="Generar Documento de Tren" >
                    <!--<input class="botonContinuar" type="button" value="Cancelar" onclick="cancelarRestriccion()">-->
                    
                            </div>
            </form>
        </div>
