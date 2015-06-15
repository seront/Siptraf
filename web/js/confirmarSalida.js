/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var bPreguntar = true;



window.onbeforeunload = preguntarAntesDeSalir;



function preguntarAntesDeSalir() {

    var respuesta;



    if (bPreguntar) {

        respuesta = confirm('¿Seguro que quieres salir?');



        if (respuesta) {

            window.onunload = function () {

                return true;

            };

        } else {

            return false;

        }

    }

}




