<%-- 
    Document   : GMT
    Created on : 02/03/2015, 09:31:24 AM
    Author     : seront
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="grp_botones_desc">

    <input type="button" value="Agregar Circulacion Descendente" onclick="insertarViaje(false)"/>
    <input type="button" value="Eliminar Circulacion" onclick="eliminarCirculacionDesc()"/>

</section>
<section id="grafico">
    <div style="width: 100%; height: 600px; margin: 0 auto;overflow: auto">
        <div id="graficaLineal" style="height: 100%; margin: 0 auto">

        </div>
    </div>
</section>

<section id="grp_botones_asc">

    <input type="button" value="Agregar Circulacion Ascedente" onclick="insertarViaje(true)"/>
    <input type="button" value="Eliminar Circulacion" onclick="eliminarCirculacion()"/>

    <input type="range" min="100" max="1000" step="20" id="rango"/>
    <input type="button" value="Generar Tabla" onclick="generaTabla()"/>
</section>


