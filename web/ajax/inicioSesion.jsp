<%-- 
    Document   : inicioSesion
    Created on : 06/04/2015, 01:33:28 PM
    Author     : Kelvins Insua
--%>

<%@include file="../jslt.jsp" %>

<!--<div class="contenedorFormulario">
            <legend>
                <h2>
                    INICIAR SESIÓN
                </h2>
            </legend>
            <form>
                
                <label class="tituloFormulario" id="label_us">Usuario:</label>
                <input class="campoFormulario" type="text" id="usuario_sesion" >
                <label class="tituloFormulario" id="label_cl">Clave:</label>
                <input class="campoFormulario" type="password" id="clave_sesion">

                <div class="contenedorBoton">
                    <input class="botonContinuar" type="button" value="Iniciar Sesión" onclick="iniciarSesion()">
                </div>


            </form>
        </div>-->
<form method="post" action="SessionAdmin" class="login" id="formSesion">
    <div id="inicio_sesion">
    <p>
    <div style="font-size: 14pt;">Iniciar Sesión</div >

    </p>
    <p>
      <label id="session_label" for="login">Usuario:</label>
      <input  type="text" id="login" name="idUsuario" placeholder="Usuario">
      <input  type="hidden"  name="accion" value="Iniciar">
    </p>

    <p>
      <label id="session_label" for="password">Contraseña:</label>
      <input  type="password" id="password" name="clave" placeholder="*********" >
    </p>

    <p class="login-submit">
        <button  type="button"  class="login-button" onclick="iniciarSesion()" ></button>
    </p>
    </div>
  </form>


