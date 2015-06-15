//var chart;


//
//function graficoCurvasEsfuesfuerzo(){
//
////                var velocidades=$("#velocidades").val();
////                var traccion=$("#traccion").val();
////                var frenado=$("#frenado").val();
////                var velArray=[];
////                var tracArray=[];
////                var frenArray=[];
////                velArray=velocidades.split(",");
////                tracArray=traccion.split(",");
////                frenArray=frenado.split(",");
////                alert(velArray);
////                var vel=[];
////                var trac=[];
////                var fren=[];
////                for(i=0; i<velArray.length; i++){
////                    vel[i]=parseFloat(velArray[i]);
////                }
////                for(i=0; i<tracArray.length; i++){
////                    trac[i]=parseFloat(tracArray[i]);
////                }
////                for(i=0; i<frenArray.length; i++){
////                    fren[i]=parseFloat(frenArray[i]);
////                }
////                alert(vel);
////               
//                                               alert("SI");
//                var chart = new Highcharts.Chart({
//			chart: {
//				renderTo: 'graficoEsfuerzos', 	// Le doy el nombre a la gráfica
//				defaultSeriesType: 'line'	// Pongo que tipo de gráfica es
//			},
//			title: {
//				text: 'Grafica de Traccion y Frenado'	// Titulo (Opcional)
//			},
//			subtitle: {
//				text: 'S.I.P.T.R.A.F'		// Subtitulo (Opcional)
//			},
//			// Pongo los datos en el eje de las 'X'
//			xAxis: {
//				//categories: vel
//                                
//,
//				// Pongo el título para el eje de las 'X'
//				title: {
//					text: 'Velocidad (Km/h)'
//				}
//			},
//			yAxis: {
//                                
//				// Pongo el título para el eje de las 'Y'
//				title: {
//					text: 'Esfuerzo KgF'
//				}
//                                
//			},
//			// Doy formato al la "cajita" que sale al pasar el ratón por encima de la gráfica
//			tooltip: {
//				enabled: true,
//				formatter: function() {
//					return '<b>'+ this.series.name +'</b><br/>'+
//						this.x +': '+ this.y +' '+this.series.name;
//				}
//			},
//			// Doy opciones a la gráfica
//			plotOptions: {
//				line: {
//					dataLabels: {
//						enabled: true
//					},
//					enableMouseTracking: true
//				}
//			},
//			// Doy los datos de la gráfica para dibujarlas
//			series: [{
//		                name: 'Curva de Traccion',
//		                data: trac
//		            },
//		            {
//		                name: 'Curva de Frenado',
//		                data: fren
//		            },
//		            ],
//                            });	
//                
//                
//           
//		
//		
//	};	

//
//function graficarMarchaTipo(progInicial, progFinal){
//    alert("SI");
//var progI=progInicial;
//var progF=progFinal;
//var sentido;
//if(progI>progF){
//                    sentido= true;
//                    
//                }else{
//                    sentido= false;
//                }
//                    alert("SI");
//
//var chart = new Highcharts.Chart({
//        chart: {
//            renderTo: 'graficoMarchaTipo',
//            type: 'area'
//        },
//        title: {
//            text: 'Gráfico Marcha Tipo'
//        },
//        subtitle: {
//            text: 'Gerencia de Gestión de Tráfico (I.F.E)'
//        },
//        xAxis: {
//            //type:'linear',
////            categories: arr1,
////            tickmarkPlacement: 'on',
//                
//            reversed:sentido,
//                
//            
//            title: {
//                text: 'Progresiva'
//                
//            }
//        },
//        yAxis: {
//            title: {
//                text: 'Velocidad'
//            },
//            labels: {
//                formatter: function () {
//                    return this.value;
//                }
//            }
//        },
//        tooltip: {
//            shared: true,
//            valueSuffix: ' Km/h'
//        },
//        plotOptions: {
//            area: {
//                stacking: 'normal',
//                lineColor: '#666666',
//                lineWidth: 1,
//                marker: {
//                    enabled: false
////                    lineWidth: 1,
////                    lineColor: '#666666'
//                }
//            }
//        },
//        series: [{
//            name: 'Velocidad',
//            data: arr2,
//            //data: [0,1,2],
//                    
//        }]
//    });
//}
//
//function graficarTiempoVelocidad(){
//
//                alert("SI");
//
//var chart = new Highcharts.Chart({
//        chart: {
//            renderTo: 'graficoMarchaTipoTiempoVelocidad',
//            type: 'line'
//        },
//        title: {
//            text: 'Gráfico Marcha Tipo'
//        },
//        subtitle: {
//            text: 'Gerencia de Gestión de Tráfico (I.F.E)'
//        },
//        xAxis: {
//            type:'datetime',
////            categories: arr1,
////            tickmarkPlacement: 'on',
//            title: {
//                text: 'Tiempo (min)'                
//            }
//        },
//        yAxis: {
//            title: {
//                text: 'Velocidad'
//            },
//            labels: {
//                formatter: function () {
//                    return this.value;
//                }
//            }
//        },
//        tooltip: {
//                    enabled: true,
//                            formatter: function () {
//                            var fecha = new Date(this.x);
//                                    var horas = fecha.getUTCHours();
//                                    var minutos = fecha.getUTCMinutes();
//                                    var segundos= fecha.getUTCSeconds();
//                                    return '(' + horas + ':' + minutos +':' +segundos+') ' + ': ' + this.y;
//                            }
//                    },
//        plotOptions: {
//            area: {
//                stacking: 'normal',
//                lineColor: '#666666',
//                lineWidth: 1,
//                marker: {
//                    lineWidth: 1,
//                    lineColor: '#666666'
//                }
//            }
//        },
//        series: [{
//                    name: 'Tiempo vs Velocidad',
//                            data: tiempos,
//                            color: 'blue'
//                    },]
//    });
//}