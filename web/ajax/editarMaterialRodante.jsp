<%@page import="modelo.GestorLista"%>
<%--<%@include file="../css/estilo.css" %>--%>
<%@include file="../jslt.jsp" %>
<c:set var="id" value="${param.id}"/>
<c:choose>
    <c:when test="${!empty id}">
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <c:set var="mr" value="${gl.buscarMaterialRodante(id)}"/>
        <div class="contenedorFormulario" id="formMatRod" >
            <legend><h2>Editar Material Rodante ${mr.nombreMaterialRodante}</h2></legend>
            <form>               
                <input type="hidden" id="id_material_Rodante_ed" value="${id}" >
                <div class="columna1">
                    <label class="tituloFormulario">Nombre del Material Rodante</label>
                    <input class="campoFormulario" type="text" id="nombre_material_rodante_ed" value="${mr.nombreMaterialRodante}">
                    <label class="tituloFormulario">Tipo</label>
                    <input class="campoFormulario" type="text" id="tipo_ed" value="${mr.tipo}" >
                    <label class="tituloFormulario">Sub-Tipo</label>
                    <select class="campoFormulario" id="sub_tipo_ed">
                        <option value="Tren de Viajeros">Tren de Viajeros</option>
                        <option value="Tren de Mercarcias">Tren de Mercancias</option>
                        <option value="Locomotora">Locomotora</option>
                    </select>
                    <label class="tituloFormulario"> Número de Vagones/Coches</label>
                    <input class="campoFormulario" type="text" id="numero_vagones_ed" value="${mr.numeroVagones}" >
                    <label class="tituloFormulario">Capacidad de Pasajeros</label>
                    <input class="campoFormulario" type="text" id="capacidad_pasajeros_ed" value="${mr.capacidadPasajeros}" >
                    <label class="tituloFormulario"> Número de Ejes</label>
                    <input class="campoFormulario" type="text" id="numero_ejes_ed" value="${mr.numeroEjes}" >

                </div>
                <div class="columna2">
                    
                    <label class="tituloFormulario">Aceleración Máxima(m/s^2)</label>
                    <input class="campoFormulario" type="text" id="aceleracion_maxima_ed" value="${mr.aceleracionMax}" >
                    <label class="tituloFormulario">Desaceleración Máxima(m/s^2)</label>
                    <input class="campoFormulario" type="text" id="desaceleracion_maxima_ed" value="${mr.desaceleracionMax}" >
                    <label class="tituloFormulario">Desaceleración de Emergencia</label>
                    <input class="campoFormulario" type="text" id="desaceleracion_emergencia_ed" value="${mr.desaceleracionEmergencia}"  required>
                    <label class="tituloFormulario"> Peso (t)</label>
                    <input class="campoFormulario" type="text" id="masa_ed" value="${mr.masa}" >
                    <label class="tituloFormulario">Velocidad de Diseño(km/h)</label>
                    <input class="campoFormulario" type="text" id="velocidad_diseño_ed" value="${mr.velocidadDisenio}" >
                    <label class="tituloFormulario">Velocidad de operacion (km/h)</label>
                    <input class="campoFormulario" type="text" id="velocidad_operacion_ed" value="${mr.velocidadOperativa}" >
                    <label class="tituloFormulario">Carga Máxima (t)</label>
                    <input class="campoFormulario" type="number" id="carga_maxima_ed" value="${mr.cargaMaxima}" >
                
              </div>
                    <div class="columna2">
                            <label class="tituloFormulario">Número de Unidades Remolque</label>
                            <input class="campoFormulario" type="number" id="unidades_remolque_ed" value="${mr.numeroRemolque}"required>
                            <label class="tituloFormulario">Número de Unidades Motriz</label>
                            <input class="campoFormulario" type="number" id="unidades_motriz_ed" value="${mr.numeroMotriz}" required>
                            <label class="tituloFormulario">Longitud Total (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_total_ed" value="${mr.longitudTotal}" required>
                            <label class="tituloFormulario">Longitud Unidad Remolque (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_remolque_ed" value="${mr.longitudRemolque}" required>                 
                            <label class="tituloFormulario">Longitud Unidad Motriz (metros)</label>
                            <input class="campoFormulario" type="text" id="longitud_motriz_ed" value="${mr.longitudMotriz}" required>                 
                            <label class="tituloFormulario">Alto por Ancho Unidades Remolque (metros)</label>
                            <input class="campoFormulario" type="text" id="alto_x_ancho_remolque_ed" value="${mr.altoXAnchoRemolque}" required>                 
                            <label class="tituloFormulario">Alto por Ancho Unidades Motriz (metros)</label>
                            <input class="campoFormulario" type="text" id="alto_x_ancho_motriz_ed" value="${mr.altoXAnchoMotriz}" required>                 
                            
                        </div>
                        <div class="columna2">
                            <label class="tituloFormulario">Masa de Unidades Remolque (t)</label>
                            <input class="campoFormulario" type="text" id="masa_remolque_ed" value="${mr.masaRemolque}" required>
                            <label class="tituloFormulario">Masa de Unidades Motriz</label>
                            <input class="campoFormulario" type="text" id="masa_motriz_ed" value="${mr.masaMotriz}" required>
                            <label class="tituloFormulario">Descripción del Tipo de Frenado</label>
                            <input class="campoFormulario" type="text" id="frenado_descripcion_ed" value="${mr.frenadoDescripcion}" required>
                            <label class="tituloFormulario">Voltaje (Voltios)</label>
                            <input class="campoFormulario" type="text" id="voltaje_ed" value="${mr.voltaje}" required>                 
                            <label class="tituloFormulario">Voltaje de Batería (Voltios)</label>
                            <input class="campoFormulario" type="text" id="voltaje_bateria_ed" value="${mr.voltajeBateria}" required>                 
                            <label class="tituloFormulario">Capacidad de Unidades Remolque </label>
                            <input class="campoFormulario" type="text" id="capacidad_remolque_ed" value="${mr.capacidadRemolque}" required>                 
                            <label class="tituloFormulario">Capacidad de Unidades Motriz</label>
                            <input class="campoFormulario" type="text" id="capacidad_motriz_ed" value="${mr.capacidadMotriz}" required>                 
                            <label class="tituloFormulario">Rango de Presión de Trabajo (Kpa)</label>
                            <input class="campoFormulario" type="text" id="presion_trabajo_ed" value="${mr.presionTrabajo}" required>                 
                      
                                <div id="contenedorImg">
                    
                    <img src="img/icon/cancelar.png" alt="Cancelar" title="Cancelar"onclick="cancelarMaterialRodante()">
                    <img src="img/icon/edit-validated-icon.png" alt="Editar" title="Editar"onclick="editarMR()">
                </div>
                        </div>
                        </form>
        </div>
                    
                </c:when>
                <c:otherwise>
                    Material Rodante no encontrado
                </c:otherwise>

            </c:choose>