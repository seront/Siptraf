$(document).ready(function () {
//    $("#cargando").hide();
    $(".cargando").hide();

    $("#cmb_lineas").on("change", function (evento) {
        cargaEstaciones($("#cmb_lineas").val());
    });

    $("#continuar").click(
            function (evento) {
                if (($("#cmb_lineas").val() !== "") && ($("#velocidad").val() > 0)&&($("#cmb_est_final").val() !== "")&&($("#cmb_est_inicio").val() !== "")) {
                    cargaRestricciones($("#cmb_lineas").val(), $("#velocidad").val());
                } else {
                    alert("Uno De Los Datos Ingresados Es Invalido");
                }
            });

    $("#resultadoMarchaTipo").css('display', 'none');

    $("#estaciones.cargando").css("display", "none");

});



function cargaEstaciones(idLinea) {
    $.ajax({
        url: 'ajax/cargaEstaciones.jsp',
        type: "POST",
        data: {idLinea: idLinea},
        beforeSend: function () {
            $(".cargando").fadeIn("slow");
            $(".cargando").css("display", "block");
        },
        complete: function () {
            $(".cargando").fadeOut("slow");
            $(".cargando").css("display", "none");
        },
        success: function (data) {
            $('#estaciones').html(data);
        }});
}


function cargaRestricciones(linea, velocidad) {
    var progEstacionInicial = $("#cmb_est_inicio").val();
    var progEstacionFinal = $("#cmb_est_final").val();
    $("#marcoRestricciones").load("ajax/cargaRestricciones.jsp", {idLinea: linea, progEstacionInicial: progEstacionInicial, progEstacionFinal: progEstacionFinal, velMax: velocidad});
}

function simular() {
    alert("Simular");
    $("#resultadoMarchaTipo").css('display', 'block');
    var idLinea = $("#cmb_lineas").val();
    var vel = $("#velocidad").val();
    var materialRodante = $("#cmb_materiales").val();
    var estInicial = $("#cmb_est_inicio").val();
    var estFinal = $("#cmb_est_final").val();
//    var restricciones = [];
    var restricciones="";
    $(".incluir").each(function (i, ele) {
        var chk = $(ele).is(':checked');
        var valor = $(ele).val();
        if (chk === true && (valor !== '')) {
//            restricciones[a++] = i;
            restricciones+= valor+" ";
        }
    }
    );
    alert(restricciones);
    $.ajax({
        url: 'MarchaTipo',
        type: "POST",
        data: {idLinea: idLinea, vel: vel, materialRodante: materialRodante, progInicial: estInicial, progFinal: estFinal, restricciones: restricciones},
        
        beforeSend: function () {
            $("#cargando").show();
        },
        complete: function () {
            $("#cargando").hide();
        },
        success: function (data) {
            
            $('#resultadoMarchaTipo').html(data);
            graficarMarchaTipo();

        }
    });
}

//function graficarMarchaTipo(vel,prog){
//    var velocidades=[];
//        velocidades=vel;
//        var progresivas=[];
//        progresivas=prog;
//}