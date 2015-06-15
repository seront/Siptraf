<%-- 
    Document   : cambiarContraseña
    Created on : 20/04/2015, 12:41:47 PM
    Author     : Kelvins Insua
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form class="contenedorFormulario">
    <label class="tituloFormulario">Antigua Contraseña</label>
    <input type="password" id="pass_usu_viejo" class="campoFormulario"  placeholder="**********" required>
    <label class="tituloFormulario">Nueva Contraseña</label>
    <input type="password" id="pass_usu_nuevo" class="campoFormulario"  placeholder="**********" required>
    <label class="tituloFormulario">Confirmar Contraseña</label>
    <input type="password" id="pass_usu_nuevo2" class="campoFormulario"  placeholder="**********" required>
    <div id='contenedorImg'>
    <!--<input type="button" class="botonContinuar" value="Cambiar" onclick="nuevaContraseña()">-->
    <img src="img/icon/cancelar.png"  alt="Cancelar" title="Cancelar" onclick="cancelarUsuario()">
    <img src="img/icon/modify-key-icon.png" alt="cambiar" title="Cambiar Contraseña" onclick="nuevaContraseña()">
    <!--<input type="button" class="botonContinuar" value="Cancelar" onclick="cancelarUsuario()">-->
    
    </div>
</form>