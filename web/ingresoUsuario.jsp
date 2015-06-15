<%-- 
    Document   : ingresoUsuario
    Created on : 16/04/2015, 11:10:43 AM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="estilo.jsp" %>
<%@include file="jslt.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <script src="js/jquery-1.9.1.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/administrarUsuarios.js" type="text/javascript"></script>
        <script src="js/sesionCerrar.js" type="text/javascript"></script>
        <jsp:useBean class="modelo.GestorLista" id="gl"/>
        <title>S.I.P.T.R.A.F</title>
    </head>
    <body >
        
            
        <div id="bgVentanaModal">           
            <div id="datos">                
            </div>            
        </div>
        <header>
            <p class="titulo">Administrar Usuarios</p>
        </header>
        <c:choose>
            <c:when test="${!empty sessionScope.usuario}">
                <div id="header">
                    <ul class="nav">
                        <li><a href="">${sessionScope.usuario}</a>
                            <ul>
                                <li onclick="cerrarSesion()"><a >Cerrar Sesión</a></li>
                                <li onclick="cambiarContraseña()"><a >Cambiar Contraseña</a></li>
                            </ul>
                        </li>

                    </ul>
                </div>

            </c:when>
            <c:otherwise>
                <script src="js/sesion.js" type="text/javascript" rel=""></script>
            </c:otherwise>
        </c:choose>
        <main>
            <div style="height: 50px">
                <input type="button" id="btnMenu" class="btnIrAlMenu" value="Ir al Menú" onclick="location.href = 'index.jsp'">
            </div>
            <c:if test="${sessionScope.nivel=='1'}">
            <div class="contenedorFormulario">
                <legend><h2>Datos del Usuario</h2></legend>
                <form>
                    <label class="tituloFormulario">Nombre de Usuario</label>
                    <input type="text" id="id_usuario" class="campoFormulario"  placeholder="Nombre del Usuario" required>
                    <label class="tituloFormulario">Nombre</label>
                    <input type="text" id="txt_nom_usu" class="campoFormulario"  placeholder="Nombre" required>
                    <label class="tituloFormulario">Apellido</label>
                    <input type="text" id="txt_ape_usu" class="campoFormulario"  placeholder="Apellido " required>
                    <label class="tituloFormulario">Contraseña</label>
                    <input type="password" id="pass_usu" class="campoFormulario"  placeholder="**********" required>
                    <label class="tituloFormulario">Confirmar Contraseña</label>
                    <input type="password" id="pass_usu2" class="campoFormulario"  placeholder="**********" required>
                    <label class="tituloFormulario">Nivel</label>
                    <select id="nivel_usu" class="campoFormulario">
                        <option value="">Seleccione Nivel</option>
                        <option value="1">Administrador</option>
                        <option value="2">Programador</option>
                        <option value="3">Consultor</option>
                    </select>
                    <div id="contenedorImg">
                        <!--<input class="botonContinuar" type="button" id="btn_agr_usu" name="accion" value="Agregar Usuario"/>-->
                        <img src="img/icon/add-user-icon.png" alt="Agregar" title="Agregar Usuario" onclick=" agregarUsuario();">
                    </div>
                </form>
            </div>

            <div class="contenedorFormulario" id="msjUsuario">

            </div>

            <div class="contenedorFormulario" id="tabla_usuarios" style="width: 100%;">            
                
<!--            <table class="tablas">
                <tr>
                    <td>Nombre de Usuario</td>
                    <td>Nombre</td>
                    <td>Apellido</td>
                    <td>Nivel</td>
                </tr>                
                    <%--<c:forEach var="u" items="${gl.listaUsuario()}">--%>
                        <tr>
                            <td> <input type="button" onmouseover="cambiarNombre($ {u.idUsuario})" onmouseout="valorInicial($ {u.idUsuario})" id="$ {u.idUsuario}" onclick="editarUsuario1('$ {u.idUsuario}')" value="$ {u.idUsuario}" ></td>
                            <td> <img src="img/icon/edit-20x20.png" alt="Editar" title="Editar" onclick="editarUsuario1('$ {u.idUsuario}')"  >$ { u.idUsuario}</td>
                            <td>$ {u.nombre}</td>
                            <td>$ {u.apellido}</td>
                            <td>$ {u.nivel}</td>

                            
                            <td><input type="button" onclick="eliminarUsuario1('$ { u.idUsuario}')" value="X"></td>
                        </tr>
                    <%--</c:forEach>--%>
               
            </table>-->
        </div>

        </main>
                
        </c:if>
        <footer>
            <%@include file="footer.jsp" %>
        </footer>
        
    </body>

</html>
