<%-- 
    Document   : cargaTablaUsuarios
    Created on : 16/04/2015, 11:50:57 AM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../jslt.jsp" %>
<jsp:useBean class="modelo.GestorLista" id="gl"/>
<c:if test="${gl.listaUsuario()!='null'}">
    <div class="contenedor_tabla" style="width: 100%">
            <table class="tablas">
                <tr>
                    <td>Nombre de Usuario</td>
                    <td>Nombre</td>
                    <td>Apellido</td>
                    <td>Nivel</td>
                </tr>                
                    <c:forEach var="u" items="${gl.listaUsuario()}">
                        <tr>
                            <td> <img src="img/icon/edit-20x20.png" alt="Editar" title="Editar" onclick="editarUsuario1('${u.idUsuario}')"  >${u.idUsuario}</td>
                            <td>${u.nombre}</td>
                            <td>${u.apellido}</td>
                            <td>${u.nivel}</td>

                            
                            
                            <td><img src="img/icon/delete-20x20.png" onclick="eliminarUsuario1('${u.idUsuario}')" alt="Eliminar" title="Eliminar"></td>
                        </tr>
                    </c:forEach>
               
            </table>
        </div>
 </c:if>
