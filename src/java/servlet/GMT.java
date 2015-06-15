package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Conex;
import modelo.EstacionTiempo;
import modelo.GestorLista;
import modelo.PreCirculacionDeserializer;
import modelo.StringArrayPrecirculacionesDeserializer;
import modelo.controlBD.EstacionTramoParadaJpaController;
import modelo.controlBD.PreCirculacionJpaController;
import modelo.controlBD.PrefijoNumeracionJpaController;
import modelo.controlBD.ProgramacionHorariaJpaController;
import modelo.controlBD.RutaJpaController;
import modelo.controlBD.TiempoEstacionMarchaTipoJpaController;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Estacion;
import modelo.entity.EstacionTramoParada;
import modelo.entity.EstacionTramoParadaPK;
import modelo.entity.IdentificadorTren;
import modelo.entity.Linea;
import modelo.entity.PreCirculacion;
import modelo.entity.PreCirculacionPK;
import modelo.entity.PrefijoNumeracion;
import modelo.entity.ProgramacionHoraria;
import modelo.entity.Ruta;
import modelo.entity.RutaPK;
import modelo.entity.TiempoEstacionMarchaTipo;

/**
 *
 * @author seront
 */
@WebServlet(name = "GMT", urlPatterns = {"/GMT"})
public class GMT extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        response.setContentType("text/html;charset=UTF-8");
//        System.out.println("LLego la solicitud");
        switch (request.getParameter("accion")) {
            case "datosIniciales":
                asignarDatosIniciales(request, response);
                break;
            case "cambiarMT":
                cambiarMarchaTipo(request, response);
                break;
            case "cargaMarchas":
                cargaMarchas(request, response);
                break;
            case "cargaEstSalida":
                cargaEstSalida(request, response);
                break;
            case "cargaEstLlegada":
                cargaEstLlegada(request, response);
                break;
            case "cargaParadas":
                cargaParadas(request, response);
                break;
            case "cargaTiempoParadas":
                cargaTiempoParadas(request, response);
                break;
            case "listarServicio":
                listarServicio(request, response);
                break;
            case "listarCategoriaIdentificacion":
                listarCategoriaIdentificacion(request, response);
                break;
            case "listarCRT":
                listarCRT(request, response);
                break;
            case "listarEmpresaPropietaria":
                listarEmpresaPropietaria(request, response);
                break;
            case "agregarNumeracion":
                agregarNumeracion(request, response);
                break;
            case "eliminarNumeracion":
                eliminarNumeracion(request, response);
                break;
            case "cargaGruposPrefijos":
                cargaGruposPrefijos(request, response);
                break;
            case "agregarPrecirculaciones":
                agregarPrecirculaciones(request, response);
                break;
            case "eliminarPreCirculacion":
                eliminarPreCirculacion(request, response);
                break;
            case "editarPreCirculacion":
                editarPreCirculacion(request, response);
                break;
            case "cargaTodasProgramaciones":
                cargaTodasProgramaciones(request, response);
                break;
            case "abrirProgramacionHoraria":
                abrirProgramacionHoraria(request, response);
                break;
            case "abrirRutas":
                abrirProgramacionHoraria(request, response);
                break;
            case "abrirProgramacionPlantilla":
                abrirProgramacionPlantilla(request, response);
                break;
            case "eliminarProgramacionHoraria":
                eliminarProgramacionHoraria(request, response);
                break;
            case "cambiarNombrePH":
                cambiarNombrePH(request, response);
                break;
            case "agregarRuta":
                agregarRuta(request, response);
                break;
            case "agregarARuta":
                agregarARuta(request, response);
                break;
            case "desvincularDeRuta":
                desvincularDeRuta(request, response);
                break;
            case "cargarRutas":
                cargarRutas(request, response);
                break;
            case "eliminarRuta":
                eliminarRuta(request, response);
                break;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void asignarDatosIniciales(HttpServletRequest request, HttpServletResponse response) {
        int idLinea = Integer.parseInt(request.getParameter("id_linea"));
        int idMTAsc = Integer.parseInt(request.getParameter("id_MT_asc_pre"));
        int idMTDesc = Integer.parseInt(request.getParameter("id_MT_desc_pre"));
        String nombrePH = request.getParameter("nombre");
        String colorAs = request.getParameter("color_asc");
        String colorDesc = request.getParameter("color_desc");

        int[] idMarchas = new int[]{idMTAsc, idMTDesc};
        GestorLista gl = new GestorLista();
        Gson gson = new Gson();
        List listaSalida = new ArrayList();

        Linea linea = gl.buscarLinea(idLinea);
        linea.setCircuitoViaList(null);
        linea.setRestriccionList(null);
        linea.setSegmentoList(null);
        linea.setEstacionList(null);
        //Agregado el 04/06/2015
        linea.setAfluenciaList(null);
        listaSalida.add(linea);

        List<Estacion> estacionesAsc = gl.buscarEstacion(idLinea);
        for (Estacion estacionesAsc1 : estacionesAsc) {
            estacionesAsc1.setLinea(null);
        }
        listaSalida.add(estacionesAsc);

        for (int i = 0; i < idMarchas.length; i++) {
            modelo.entity.MarchaTipo mt = gl.buscarMarchaTipo(idMarchas[i]);
//            System.out.println(mt.getNombreMarchaTipo() + " sentido: " + mt.isSentido());
            mt.setRestriccionMarchaTipoList(null);
            mt.setGraficoMarchaTipoList(null);
            mt.setTiempoEstacionMarchaTipoList(null);
            listaSalida.add(mt);
            TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
            List<TiempoEstacionMarchaTipo> tiemposEstacionesAsc = temtjc.buscarPorIdMarchaTipo(idMarchas[i]);
            List<EstacionTiempo> estTiem = new ArrayList<>();
            for (TiempoEstacionMarchaTipo temt : tiemposEstacionesAsc) {
                EstacionTiempo et = new EstacionTiempo(temt.getTiempoEstacionMarchaTipoPK().getIdPkEstacion(), temt.getTiempoAsimilado().getTime());
                estTiem.add(et);
            }
            listaSalida.add(estTiem);
        }
        listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(2)).getTiempoMinimo().getTime());
        listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(4)).getTiempoMinimo().getTime());

        ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
//        List<Integer> idsPH = phjc.listarId();
        int idPH = 0;
        try{
        List<Integer> idsPH = phjc.listarId();
        for (int i = 0; i < idsPH.size(); i++) {
            if (idsPH.get(i) != i) {
                idPH = i;
                break;
            }
            if (i == idsPH.size() - 1) {
                idPH = idsPH.size();
                break;
            }
        }
        }catch(NullPointerException e){
        e.printStackTrace();
        idPH = 0;
        }
        listaSalida.add(idPH);
        String json = gson.toJson(listaSalida);

        try (PrintWriter salida = response.getWriter()) {
            salida.println(json);
            ProgramacionHoraria ph = new ProgramacionHoraria(idPH, idLinea, nombrePH, idMTAsc, colorAs, idMTDesc, colorDesc);
            
            phjc.create(ph);
            
            int idRuta=0;
            RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
            Ruta r=new Ruta();
            r.setColor("#000000");
            r.setNombre("Ruta 0");
            RutaPK rpk=new RutaPK(idRuta, idPH);
            r.setRutaPK(rpk);
            r.setProgramacionHoraria(ph);
            rjc.create(r);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cambiarMarchaTipo(HttpServletRequest request, HttpServletResponse response) {
        int idMT = Integer.parseInt(request.getParameter("idMT"));
        double estSalida = Double.parseDouble(request.getParameter("cmb_est_salida"));
        double estLlegada = Double.parseDouble(request.getParameter("cmb_est_llegada"));

        Gson gson = new Gson();
        List listaSalida = new ArrayList();
        GestorLista gl = new GestorLista();
        TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
        List<TiempoEstacionMarchaTipo> tiemposEstacionesAsc = temtjc.buscarPorIdMarchaTipoSalidaLlegada(idMT, gl.buscarMarchaTipo(idMT).isSentido(), estSalida, estLlegada);
        List<EstacionTiempo> estTiem = new ArrayList<>();
        for (TiempoEstacionMarchaTipo temt : tiemposEstacionesAsc) {
            EstacionTiempo et = new EstacionTiempo(temt.getTiempoEstacionMarchaTipoPK().getIdPkEstacion(), temt.getTiempoAsimilado().getTime());
            estTiem.add(et);
        }
        listaSalida.add(estTiem);

        String json = gson.toJson(listaSalida);

        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("Otra Marcha tipo");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargaMarchas(HttpServletRequest request, HttpServletResponse response) {
        GestorLista gl = new GestorLista();
        int idLinea = Integer.parseInt(request.getParameter("id_linea"));
        Gson gson = new Gson();
        List listaSalida = new ArrayList();
        List<modelo.entity.MarchaTipo> marchas;
        boolean sentido = Boolean.parseBoolean(request.getParameter("sentido"));
        if (sentido == true) {
            marchas = gl.listarMarchaTipoAsc(idLinea);
        } else {
            marchas = gl.listarMarchaTipoDesc(idLinea);
        }

        for (modelo.entity.MarchaTipo marcha : marchas) {
            marcha.setGraficoMarchaTipoList(null);
            marcha.setRestriccionMarchaTipoList(null);
            marcha.setTiempoEstacionMarchaTipoList(null);
            listaSalida.add(marcha);
        }
        String json = gson.toJson(listaSalida);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("Carga Marchas");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargaEstSalida(HttpServletRequest request, HttpServletResponse response) {
        int idMT = Integer.parseInt(request.getParameter("id_marcha_tipo"));
        System.out.println("Cargando estacion de salida");
        GestorLista gl = new GestorLista();
        List<TiempoEstacionMarchaTipo> estaciones = new ArrayList();
        TiempoEstacionMarchaTipo estacionParada = gl.buscarParadasSalida(idMT);
        estacionParada.setMarchaTipo(null);
        System.out.println("idMarchaTipo "+idMT);
        estaciones.add(estacionParada);
        for (TiempoEstacionMarchaTipo estacionParad : gl.buscarParadasMT(idMT)) {
//        for (TiempoEstacionMarchaTipo estacionParad : gl.buscarParadasInMT(idMT)) {
            estacionParad.setMarchaTipo(null);
            
            estaciones.add(estacionParad);
        }
        Gson gson = new Gson();

        String json = gson.toJson(estaciones);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("cargaEstSalida");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargaEstLlegada(HttpServletRequest request, HttpServletResponse response) {
        int idMT = Integer.parseInt(request.getParameter("id_marcha_tipo"));
        
        GestorLista gl = new GestorLista();
        List<TiempoEstacionMarchaTipo> estaciones = new ArrayList();
        TiempoEstacionMarchaTipo estacionParada = gl.buscarParadasLlegada(idMT);
        estacionParada.setMarchaTipo(null);
        
        estaciones.add(estacionParada);
        for (TiempoEstacionMarchaTipo estacionParad : gl.buscarParadasMT(idMT)) {
            estacionParad.setMarchaTipo(null);
       
            estaciones.add(estacionParad);
        }
        Gson gson = new Gson();
        String json = gson.toJson(estaciones);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("cargaEstLlegada");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargaParadas(HttpServletRequest request, HttpServletResponse response) {
        int idMarchaTipo = Integer.parseInt(request.getParameter("id_marcha_tipo"));
        double estInicio = Double.parseDouble(request.getParameter("estacion_inicio"));
        double estFinal = Double.parseDouble(request.getParameter("estacion_final"));
        GestorLista gl = new GestorLista();
        List<TiempoEstacionMarchaTipo> estaciones = gl.buscarParadasIntermediasMT(idMarchaTipo, estInicio, estFinal);
        List<TiempoEstacionMarchaTipo> listaSalida = new ArrayList();
        for (TiempoEstacionMarchaTipo estacionParad : estaciones) {
            estacionParad.setMarchaTipo(null);
            listaSalida.add(estacionParad);
        }
        Gson gson = new Gson();
        String json = gson.toJson(listaSalida);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("cargaParadas");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargaTiempoParadas(HttpServletRequest request, HttpServletResponse response) {
        int idLinea = Integer.parseInt(request.getParameter("id_linea"));
        GestorLista gl = new GestorLista();
        List<Estacion> estaciones = gl.buscarEstacion(idLinea);
        List listaSalida = new ArrayList();
        for (int i = 1; i < estaciones.size() - 1; i++) {
            Estacion estacion = estaciones.get(i);
            estacion.setLinea(null);
            listaSalida.add(estacion);
        }
        Gson gson = new Gson();
        String json = gson.toJson(listaSalida);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
//            System.out.println("Estaciones predeterminadas");
//            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarServicio(HttpServletRequest request, HttpServletResponse response) {
        GestorLista gl = new GestorLista();
        List<IdentificadorTren> lt = gl.listarServicio();
        Gson gson = new Gson();
        String json = gson.toJson(lt);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarCategoriaIdentificacion(HttpServletRequest request, HttpServletResponse response) {
        GestorLista gl = new GestorLista();
        List<IdentificadorTren> lt = gl.listarCategoriaIdentificacion();
        Gson gson = new Gson();
        String json = gson.toJson(lt);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarCRT(HttpServletRequest request, HttpServletResponse response) {
        GestorLista gl = new GestorLista();
        List<IdentificadorTren> lt = gl.listarCRT();
        Gson gson = new Gson();
        String json = gson.toJson(lt);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listarEmpresaPropietaria(HttpServletRequest request, HttpServletResponse response) {
        GestorLista gl = new GestorLista();
        List<IdentificadorTren> lt = gl.listarEmpresaPropietaria();
        Gson gson = new Gson();
        String json = gson.toJson(lt);
        try (PrintWriter salida = response.getWriter()) {
            salida.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void agregarNumeracion(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            //Metodo que agrega un objeto tipo prefijo de numeracion y responde ese mismo objeto prefijo en formato json
            String nombre = request.getParameter("nombre");
            String valor = request.getParameter("valor");
            int id = 0;
            PrefijoNumeracionJpaController pnjc = new PrefijoNumeracionJpaController(Conex.getEmf());
            List<Integer> idPrefijos = pnjc.listarId();
            for (int i = 0; i < idPrefijos.size(); i++) {
                if (idPrefijos.get(i) != i) {
                    id = i;
                    break;
                }
                if (i == idPrefijos.size() - 1) {
                    id = idPrefijos.size();
                    break;
                }
            }
            PrefijoNumeracion pn = new PrefijoNumeracion();
            pn.setIdPrefijo(id);
            pn.setNombre(nombre);
            pn.setValor(valor);
            pnjc.create(pn);
            Gson gson = new Gson();
            String json = gson.toJson(pn);
            salida = response.getWriter();
            salida.write(json);
        } catch (PreexistingEntityException ex) {
            System.out.println("Ya existe el prefijo");
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error enviando la respuesta");
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void eliminarNumeracion(HttpServletRequest request, HttpServletResponse response) {
        //Metodo que elimina un objeto tipo prefijo, recibe el id y responde un array de todos los prefijos en formato json
        int id = Integer.parseInt(request.getParameter("id"));
        PrintWriter salida = null;
        try {
            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            PrefijoNumeracionJpaController pnjc = new PrefijoNumeracionJpaController(Conex.getEmf());
//            if (pcjc.buscarPorPrefijo(id).size() != 0) {
            if (pcjc.buscarPorPrefijo(id).isEmpty()) {
                pnjc.destroy(id);
            } else {
                System.out.println("No se borro xq esta en uso");
            }
            Gson gson = new Gson();
            String json = gson.toJson(pnjc.findPrefijoNumeracionEntities());
            salida = response.getWriter();
            salida.write(json);
        } catch (NonexistentEntityException ex) {
            System.out.println("No existe el prefijo");
            ex.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error enviando la respuesta");
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void cargaGruposPrefijos(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            PrefijoNumeracionJpaController pnjc = new PrefijoNumeracionJpaController(Conex.getEmf());
            Gson gson = new Gson();
            String json = gson.toJson(pnjc.findPrefijoNumeracionEntities());
            salida = response.getWriter();
            salida.write(json);
        } catch (IOException e) {
            System.out.println("Error enviando la respuesta");
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void agregarPrecirculaciones(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Parametros en la solicitud");
        System.out.println(request.getParameter("id_programacion_horaria"));
        System.out.println(request.getParameter("preCirclaciones"));
        System.out.println(request.getParameter("preCirclaciones").replace("\"", ""));

        System.out.println(Arrays.toString(request.getParameterValues("preCirclaciones")));

        PrintWriter salida = null;
        try {
            int idPH = Integer.parseInt(request.getParameter("id_programacion_horaria"));
            System.out.println("idPH: "+idPH);
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            ProgramacionHoraria ph = phjc.findProgramacionHoraria(idPH);
            RutaJpaController rjc =new RutaJpaController(Conex.getEmf());
            int idRuta=0;
            RutaPK rpk=new RutaPK(idRuta, idPH);
            Ruta r = rjc.findRuta(rpk);
            GsonBuilder gb = new GsonBuilder();
            gb.registerTypeAdapter(PreCirculacion.class, new PreCirculacionDeserializer());
            gb.registerTypeAdapter(String[].class, new StringArrayPrecirculacionesDeserializer());
            Gson gson = gb.create();
            System.out.println("preCirculaciones toJson");
            System.out.println(gson.toJson(request.getParameter("preCirclaciones")));
            System.out.println("preCirculaciones toJsonTree");
            System.out.println(gson.toJsonTree(request.getParameter("preCirclaciones")));

            String preCirculaciones[] = gson.fromJson(request.getParameter("preCirclaciones"), String[].class);
//            String preCirculaciones[] = gson.fromJson(gson.toJson(request.getParameter("preCirclaciones")),String[].class);
            //            String preCirculaciones[] = request.getParameterValues("preCirclaciones");
//            String preCirculaciones[] = gson.toJson(request.getParameter("preCirclaciones");
//            System.out.println("*******PreCirculaciones recibidas ***** cant " + preCirculaciones.length);
            List<Integer> idLibres = new ArrayList<>();
            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            int idPreCirculacionesMAX = pcjc.buscarIdPreCirclacionMax(idPH);
            System.out.println("idPreCirculacionesMAX " + idPreCirculacionesMAX);
            for (int i = 0; i < preCirculaciones.length; i++) {
                idLibres.add(++idPreCirculacionesMAX);
            }

            System.out.println("idLibres " + idLibres);
            System.out.println("Guardando las precirculaciones");
            EstacionTramoParadaJpaController tepjc = new EstacionTramoParadaJpaController(Conex.getEmf());
            for (int i = 0; i < preCirculaciones.length; i++) {
//                System.out.println("Guardando las precirculacion de id: "+idLibres.get(i)+ " PK: "+idPH+","+idLibres.get(i));
                PreCirculacionPK pk = new PreCirculacionPK(idLibres.get(i), idRuta,idPH);
                
                PreCirculacion pc = gson.fromJson(preCirculaciones[i], PreCirculacion.class);
                pc.setPreCirculacionPK(pk);
                System.out.println(pc);
                
                System.out.println(r.getRutaPK().getIdRuta());
                for (int j = 0; j < pc.getEstacionTramoParadaList().size(); j++) {
                    pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdProgramacionHoraria(idPH);
                    pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdRuta(idRuta);
                    pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdPreCirculacion(idLibres.get(i));
                }
                List<EstacionTramoParada> teps = new ArrayList<>();
                for (EstacionTramoParada tep : pc.getEstacionTramoParadaList()) {
                    teps.add(tep);
                }
                pc.setRuta(r);
                pc.getEstacionTramoParadaList().clear();
                System.out.println(pc.getRuta().getRutaPK().getIdRuta());
                pcjc.create(pc);

                for (EstacionTramoParada tep : teps) {
//                    System.out.println("TEP PK: "+tep.getEstacionTramoParadaPK().getIdProgramacionHoraria()+", "+tep.getEstacionTramoParadaPK().getIdPreCirculacion()+", "+tep.getEstacionTramoParadaPK().getIdEstacion());
                    tep.setPreCirculacion(pc);
                    tepjc.create(tep);
                }
            }
            salida = response.getWriter();
            salida.write(gson.toJson(idLibres));
        } catch (PreexistingEntityException ex) {
            System.out.println("Ya existe esta precirculacion ");
        } catch (IOException ex) {
            System.out.println("Error en el writer");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void eliminarPreCirculacion(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            int idPH = Integer.parseInt(request.getParameter("id_programacion_horaria"));
//            int idRuta=0;
//            int idPreCirculacion = Integer.parseInt(request.getParameter("id_pre_circulacion"));
            String[] ides = request.getParameterValues("id_pre_circulacion");
            String[] idesRuta = request.getParameterValues("id_rutas");
            for (int i = 0; i < ides.length; i++) {
                int idPreCirculacion = Integer.parseInt(ides[i]);
                int idRuta = Integer.parseInt(idesRuta[i]);
                System.out.println("Eliminando PreCirculacion= "+idPH+", "+idPreCirculacion);
                EstacionTramoParadaJpaController ejc = new EstacionTramoParadaJpaController(Conex.getEmf());
                for (EstacionTramoParada tep : ejc.buscarTEPS(idPreCirculacion, idPreCirculacion)) {
                    EstacionTramoParadaPK ePK = new EstacionTramoParadaPK(tep.getEstacionTramoParadaPK().getIdEstacion(),idPreCirculacion,idRuta, idPH );
                    
                    
                    ejc.destroy(ePK);
                }
                PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
                PreCirculacionPK pcPK = pcjc.buscarPorPH(idPH, idPreCirculacion).getPreCirculacionPK();
                pcjc.destroy(pcPK);

            }
//            EstacionTramoParadaJpaController ejc = new EstacionTramoParadaJpaController(Conex.getEmf());
//            for (EstacionTramoParada tep : ejc.buscarTEPS(idPreCirculacion, idPreCirculacion)) {
//                EstacionTramoParadaPK ePK = new EstacionTramoParadaPK(idPH, idPreCirculacion, tep.getEstacionTramoParadaPK().getIdEstacion());
//                ejc.destroy(ePK);
//            }
//            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
//            PreCirculacionPK pcPK = new PreCirculacionPK(idPH, idPreCirculacion);
//            pcjc.destroy(pcPK);
            
//            salida.write("{'respuesta':'eliminado satisfactoriamente'}");
            salida = response.getWriter();
            salida.write("eliminado satisfactoriamente");
        } catch (IllegalOrphanException ex) {
            System.out.println("IllegalOrphanException eliminarPreCirculacion");
            ex.printStackTrace();
        } catch (NonexistentEntityException ex) {
            System.out.println("NonexistentEntityException eliminarPreCirculacion");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("EXCEPCION eliminarPreCirculacion en el escritor de salida");
        }catch(Exception e){
            e.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void editarPreCirculacion(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            int idPH = Integer.parseInt(request.getParameter("id_programacion_horaria"));
            int idPreCirculacion = Integer.parseInt(request.getParameter("id_pre_circulacion"));
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            EstacionTramoParadaJpaController tepjc = new EstacionTramoParadaJpaController(Conex.getEmf());
            ProgramacionHoraria ph = phjc.findProgramacionHoraria(idPH);
            RutaJpaController rjc =new RutaJpaController(Conex.getEmf());
            int idRuta=0;
            RutaPK rpk=new RutaPK(idRuta, idPH);
            Ruta r = rjc.findRuta(rpk);
            GsonBuilder gb = new GsonBuilder();
            gb.registerTypeAdapter(PreCirculacion.class, new PreCirculacionDeserializer());
            Gson gson = gb.create();

            PreCirculacionPK pk = new PreCirculacionPK(idPreCirculacion,idRuta, idPH );
            PreCirculacion pc = gson.fromJson(request.getParameter("pre_circulacion"), PreCirculacion.class);
            pc.setPreCirculacionPK(pk);
            System.out.println(pc);
            pc.setRuta(r);
            for (int j = 0; j < pc.getEstacionTramoParadaList().size(); j++) {
                pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdProgramacionHoraria(idPH);
                pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdRuta(idRuta);
                pc.getEstacionTramoParadaList().get(j).getEstacionTramoParadaPK().setIdPreCirculacion(idPreCirculacion);
                
                
            }
            List<EstacionTramoParada> teps = new ArrayList<>();
            for (EstacionTramoParada tep : pc.getEstacionTramoParadaList()) {
                teps.add(tep);
            }
            pc.getEstacionTramoParadaList().clear();
            System.out.println("Editando la precirculacion");
            pcjc.edit(pc);
            System.out.println("Editando los TEPS");
            for (EstacionTramoParada tep : teps) {
                tep.setPreCirculacion(pc);
                tepjc.edit(tep);
            }
            salida = response.getWriter();
            salida.write("editado satisfactoriamente");
        } catch (NonexistentEntityException ex) {
            System.out.println("Excepcion editando la pre circulacion");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Excepcion editando la pre circulacion");
            ex.printStackTrace();
        } finally {
            salida.close();
        }
    }

    private void cargaTodasProgramaciones(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            Gson gson = new Gson();
            List<ProgramacionHoraria> phs = phjc.findProgramacionHorariaEntities();
            for (ProgramacionHoraria ph : phs) {
                ph.setRutaList(null);
            }
            salida = response.getWriter();
            salida.write(gson.toJson(phs));
        } catch (IOException ex) {
            System.out.println("Error en cargaTodasProgramaciones");
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            salida.close();
        }
    }

    private void abrirProgramacionHoraria(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("abrirProgramacionHoraria");
        PrintWriter salida = null;
        try {
            List arraySalida = new ArrayList();
            int idProgramacionHoraria = Integer.parseInt(request.getParameter("id_programacion_horaria"));
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            ProgramacionHoraria ph = phjc.findProgramacionHoraria(idProgramacionHoraria);
            int idMTAsc = ph.getMarchaTipoPredAsc();
            int idMTDesc = ph.getMarchaTipoPredDesc();
            int idLinea = ph.getIdLinea();
            System.out.println("ph.getIdLinea()= " + ph.getIdLinea());
            int[] idMarchas = new int[]{idMTAsc, idMTDesc};
            GestorLista gl = new GestorLista();
            Gson gson = new Gson();
            List listaSalida = new ArrayList();

            Linea linea = gl.buscarLinea(idLinea);
            linea.setCircuitoViaList(null);
            linea.setRestriccionList(null);
            linea.setSegmentoList(null);
            linea.setEstacionList(null);
            //Agregado el 04/06/2015
        linea.setAfluenciaList(null);
        
            listaSalida.add(linea);

            List<Estacion> estacionesAsc = gl.buscarEstacion(idLinea);
            for (Estacion estacionesAsc1 : estacionesAsc) {
                estacionesAsc1.setLinea(null);
            }
            listaSalida.add(estacionesAsc);

            for (int i = 0; i < idMarchas.length; i++) {
                modelo.entity.MarchaTipo mt = gl.buscarMarchaTipo(idMarchas[i]);
                mt.setRestriccionMarchaTipoList(null);
                mt.setGraficoMarchaTipoList(null);
                mt.setTiempoEstacionMarchaTipoList(null);
                listaSalida.add(mt);
                TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
                List<TiempoEstacionMarchaTipo> tiemposEstacionesAsc = temtjc.buscarPorIdMarchaTipo(idMarchas[i]);
                List<EstacionTiempo> estTiem = new ArrayList<>();
                for (TiempoEstacionMarchaTipo temt : tiemposEstacionesAsc) {
                    EstacionTiempo et = new EstacionTiempo(temt.getTiempoEstacionMarchaTipoPK().getIdPkEstacion(), temt.getTiempoAsimilado().getTime());
                    estTiem.add(et);
                }
                listaSalida.add(estTiem);
            }
            listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(2)).getTiempoMinimo().getTime());
            listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(4)).getTiempoMinimo().getTime());

            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            List<PreCirculacion> preCirculaciones = pcjc.listarPreCirculaciones(idProgramacionHoraria);
            EstacionTramoParadaJpaController etpjc = new EstacionTramoParadaJpaController(Conex.getEmf());

            for (PreCirculacion preCirculacion : preCirculaciones) {
                System.out.println("PreCirculacion " + preCirculacion.getPreCirculacionPK().getIdPreCirculacion());
                preCirculacion.setEstacionTramoParadaList(null);
                preCirculacion.setRuta(null);
                List trio = new ArrayList();
                trio.add(preCirculacion);
                List<EstacionTramoParada> teps = etpjc.buscarTEPS(idProgramacionHoraria, preCirculacion.getPreCirculacionPK().getIdPreCirculacion());

                for (EstacionTramoParada tep : teps) {
                    System.out.println("tep: " + tep.getEstacionTramoParadaPK().getIdEstacion() + " t= " + tep.getTiempo());
                    tep.setPreCirculacion(null);
                }
                trio.add(teps);
                List<TiempoEstacionMarchaTipo> estaciones = new ArrayList();
                TiempoEstacionMarchaTipo estacionParada = gl.buscarParadasSalida(preCirculacion.getMarchaTipo());
                estacionParada.setMarchaTipo(null);
                estaciones.add(estacionParada);
                estacionParada = gl.buscarParadasLlegada(preCirculacion.getMarchaTipo());
                estacionParada.setMarchaTipo(null);
                estaciones.add(estacionParada);
                trio.add(estaciones);
                arraySalida.add(trio);
            }

            listaSalida.add(arraySalida);

            List<modelo.entity.MarchaTipo> marchas;
            List<modelo.entity.MarchaTipo> arrMarchas = new ArrayList();
            marchas = gl.listarMarchaTipoAsc(idLinea);
//            System.out.println("Cantidad de marchas asc "+marchas.size());
            for (modelo.entity.MarchaTipo marcha : marchas) {
                marcha.setGraficoMarchaTipoList(null);
                marcha.setRestriccionMarchaTipoList(null);
                marcha.setTiempoEstacionMarchaTipoList(null);
                arrMarchas.add(marcha);
            }
//            System.out.println("Cantidad de marchas asc arrMarchas "+arrMarchas.size());
//            System.out.println("asc arrMarchas "+arrMarchas.toString());
            listaSalida.add(arrMarchas);
            arrMarchas = new ArrayList<>();

            marchas = gl.listarMarchaTipoDesc(idLinea);
//            System.out.println("Cantidad de marchas desc "+marchas.size());
            for (modelo.entity.MarchaTipo marcha : marchas) {
                marcha.setGraficoMarchaTipoList(null);
                marcha.setRestriccionMarchaTipoList(null);
                marcha.setTiempoEstacionMarchaTipoList(null);
                arrMarchas.add(marcha);
            }
//            System.out.println("Cantidad de marchas Desc arrMarchas "+arrMarchas.size());
//            System.out.println("Desc arrMarchas "+arrMarchas.toString());
            listaSalida.add(arrMarchas);

            
            //EDITADO KELVINS PARA ENVIAR LAS RUTAS
            RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       List<Ruta> rutas=rjc.buscarRutaPorPH(idProgramacionHoraria);
       List rutas2=new ArrayList();
        System.out.println(rutas);
        for (Ruta ruta : rutas) {
            ruta.setPreCirculacionList(null);
            ruta.setProgramacionHoraria(null);
            rutas2.add(ruta);
        }
        
        //AKI AGREGO LAS RUTAS
       listaSalida.add(rutas2);
            
            salida = response.getWriter();
//            System.out.println("" + listaSalida.toString());
//            System.out.println("" + gson.toJson(listaSalida));
            
            String json = gson.toJson(listaSalida);
            salida.write(json);
//            System.out.println("PreCirculaciones de salida " + arraySalida.size());
//            System.out.println(gson.toJson(arraySalida));
//            System.out.println(json);
        } catch (IOException ex) {
            System.out.println("Error con la salida en abrirProgramacionHoraria");
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            salida.close();
        }
    }

    private void abrirProgramacionPlantilla(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("abrirProgramacionPlantilla");
        int idProgramacionHoraria = Integer.parseInt(request.getParameter("id_programacion_horaria"));
        int idPHAnterior = idProgramacionHoraria;
        PrintWriter salida;
        try {
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            ProgramacionHoraria ph = phjc.findProgramacionHoraria(idProgramacionHoraria);

            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            List<PreCirculacion> preCirculaciones = pcjc.listarPreCirculaciones(idPHAnterior);
            EstacionTramoParadaJpaController etpjc = new EstacionTramoParadaJpaController(Conex.getEmf());

            List<Integer> idsPH = phjc.listarId();
            // Encontrando el Id nuevo
            idProgramacionHoraria = 0;
            for (int i = 0; i < idsPH.size(); i++) {
                if (idsPH.get(i) != i) {
                    idProgramacionHoraria = i;
                    break;
                }
                if (i == idsPH.size() - 1) {
                    idProgramacionHoraria = idsPH.size();
                    break;
                }
            }
            System.out.println("Nuevo idProgramacionHoraria " + idProgramacionHoraria);
            ph.setIdProgramacionHoraria(idProgramacionHoraria);
            ph.setRutaList(null);
            phjc.create(ph);
            
            int idRuta=0;
            RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
            Ruta r=new Ruta();
            r.setColor("#000000");
            r.setNombre("Ruta 0");
            RutaPK rpk=new RutaPK(idRuta, idProgramacionHoraria);
            r.setRutaPK(rpk);
            r.setProgramacionHoraria(ph);
            rjc.create(r);
            List<EstacionTramoParada> teps;
            for (PreCirculacion preCirculacion : preCirculaciones) {
                teps = new ArrayList<>();
//                System.out.println("Copiando preCirculacion Nº "+preCirculaciones.indexOf(preCirculacion));
                preCirculacion.getPreCirculacionPK().setIdProgramacionHoraria(idProgramacionHoraria);
                List<EstacionTramoParada> teps2 = etpjc.buscarTEPS(idPHAnterior, preCirculacion.getPreCirculacionPK().getIdPreCirculacion());
                System.out.println("PreCirculacion " + preCirculacion.getPreCirculacionPK().getIdPreCirculacion());
                for (EstacionTramoParada tep : teps2) {
//                    System.out.println("Copiando tep Nº "+teps2.indexOf(tep));
                    System.out.println("tep: " + tep.getEstacionTramoParadaPK().getIdEstacion() + " t= " + tep.getTiempo());
                    tep.getEstacionTramoParadaPK().setIdProgramacionHoraria(idProgramacionHoraria);
                    teps.add(tep);
                }
               
                preCirculacion.setEstacionTramoParadaList(null);
                preCirculacion.setRuta(r);
                pcjc.create(preCirculacion);
                for (EstacionTramoParada tep : teps) {
//                    System.out.println("Pegando tep Nº "+teps.indexOf(tep));
                    tep.setPreCirculacion(preCirculacion);
                    etpjc.create(tep);
                }
//                preCirculacion.setEstacionTramoParadaList(teps);                
            }

            //Aqui comienzan las cosas que van a salir
            List arraySalida = new ArrayList();

            int idMTAsc = ph.getMarchaTipoPredAsc();
            int idMTDesc = ph.getMarchaTipoPredDesc();
            int idLinea = ph.getIdLinea();
            System.out.println("ph.getIdLinea()= " + ph.getIdLinea());
            int[] idMarchas = new int[]{idMTAsc, idMTDesc};
            GestorLista gl = new GestorLista();
            Gson gson = new Gson();
            List listaSalida = new ArrayList();

            Linea linea = gl.buscarLinea(idLinea);
            linea.setCircuitoViaList(null);
            linea.setRestriccionList(null);
            linea.setSegmentoList(null);
            linea.setEstacionList(null);
            //Agregado el 04/06/2015
            linea.setAfluenciaList(null);
            //data[0]
            listaSalida.add(linea);

            List<Estacion> estacionesAsc = gl.buscarEstacion(idLinea);
            for (Estacion estacionesAsc1 : estacionesAsc) {
                estacionesAsc1.setLinea(null);
            }
            //data[1]
            listaSalida.add(estacionesAsc);

            for (int i = 0; i < idMarchas.length; i++) {
                modelo.entity.MarchaTipo mt = gl.buscarMarchaTipo(idMarchas[i]);
                mt.setRestriccionMarchaTipoList(null);
                mt.setGraficoMarchaTipoList(null);
                mt.setTiempoEstacionMarchaTipoList(null);
                //data[2] y data[4]
                listaSalida.add(mt);
                TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
                List<TiempoEstacionMarchaTipo> tiemposEstacionesAsc = temtjc.buscarPorIdMarchaTipo(idMarchas[i]);
                List<EstacionTiempo> estTiem = new ArrayList<>();
                for (TiempoEstacionMarchaTipo temt : tiemposEstacionesAsc) {
                    EstacionTiempo et = new EstacionTiempo(temt.getTiempoEstacionMarchaTipoPK().getIdPkEstacion(), temt.getTiempoAsimilado().getTime());
                    estTiem.add(et);
                }
                //data[3] y data[5]
                listaSalida.add(estTiem);
            }
            //data[6]
            listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(2)).getTiempoMinimo().getTime());
            //data[7]
            listaSalida.add(((modelo.entity.MarchaTipo) listaSalida.get(4)).getTiempoMinimo().getTime());

            for (PreCirculacion preCirculacion : preCirculaciones) {
                preCirculacion.setEstacionTramoParadaList(null);
                preCirculacion.setRuta(null);
                List trio = new ArrayList();
                trio.add(preCirculacion);
//                List<EstacionTramoParada> teps = etpjc.buscarTEPS(idProgramacionHoraria, preCirculacion.getPreCirculacionPK().getIdPreCirculacion());
                teps = etpjc.buscarTEPS(idProgramacionHoraria, preCirculacion.getPreCirculacionPK().getIdPreCirculacion());
                for (EstacionTramoParada tep : teps) {
                    tep.setPreCirculacion(null);
                }
                trio.add(teps);
                List<TiempoEstacionMarchaTipo> estaciones = new ArrayList();
                TiempoEstacionMarchaTipo estacionParada = gl.buscarParadasSalida(preCirculacion.getMarchaTipo());
                estacionParada.setMarchaTipo(null);
                estaciones.add(estacionParada);
                estacionParada = gl.buscarParadasLlegada(preCirculacion.getMarchaTipo());
                estacionParada.setMarchaTipo(null);
                estaciones.add(estacionParada);
                trio.add(estaciones);
                arraySalida.add(trio);
            }
            //data[8]
            listaSalida.add(arraySalida);

            List<modelo.entity.MarchaTipo> marchas;
            List<modelo.entity.MarchaTipo> arrMarchas = new ArrayList();
            marchas = gl.listarMarchaTipoAsc(idLinea);
//            System.out.println("Cantidad de marchas asc "+marchas.size());
            for (modelo.entity.MarchaTipo marcha : marchas) {
                marcha.setGraficoMarchaTipoList(null);
                marcha.setRestriccionMarchaTipoList(null);
                marcha.setTiempoEstacionMarchaTipoList(null);
                arrMarchas.add(marcha);
            }
            //data[9]
            listaSalida.add(arrMarchas);
            arrMarchas = new ArrayList<>();

            marchas = gl.listarMarchaTipoDesc(idLinea);
            for (modelo.entity.MarchaTipo marcha : marchas) {
                marcha.setGraficoMarchaTipoList(null);
                marcha.setRestriccionMarchaTipoList(null);
                marcha.setTiempoEstacionMarchaTipoList(null);
                arrMarchas.add(marcha);
            }
            //data[10]
            listaSalida.add(arrMarchas);
            //data[11]
            
            //EDITADO KELVINS PARA ENVIAR LAS RUTAS
//            RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       List<Ruta> rutas=rjc.buscarRutaPorPH(idProgramacionHoraria);
       List rutas2=new ArrayList();
        System.out.println(rutas);
        for (Ruta ruta : rutas) {
            ruta.setPreCirculacionList(null);
            ruta.setProgramacionHoraria(null);
            rutas2.add(ruta);
        }
        
        //AKI AGREGO LAS RUTAS
       listaSalida.add(rutas2);
       //data 12
            listaSalida.add(idProgramacionHoraria);
            salida = response.getWriter();
            System.out.println("" + listaSalida.toString());
            System.out.println("" + gson.toJson(listaSalida));
            String json = gson.toJson(listaSalida);
            salida.write(json);

        } catch (PreexistingEntityException e) {
            System.out.println("PreexistingEntityException en abrirProgramacionPlantilla");
            e.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException");
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void eliminarProgramacionHoraria(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter salida = null;
        try {
            int idProgramacionHoraria = Integer.parseInt(request.getParameter("id_programacion_horaria"));
            EstacionTramoParadaJpaController ejc = new EstacionTramoParadaJpaController(Conex.getEmf());
            List<EstacionTramoParada> teps = ejc.buscarTEPSPH(idProgramacionHoraria);
            for (EstacionTramoParada tep : teps) {
                ejc.destroy(tep.getEstacionTramoParadaPK());
            }
            PreCirculacionJpaController pcjc = new PreCirculacionJpaController(Conex.getEmf());
            List<PreCirculacion> preCirculaciones = pcjc.listarPreCirculaciones(idProgramacionHoraria);
            for (PreCirculacion preCirculacion : preCirculaciones) {
                pcjc.destroy(preCirculacion.getPreCirculacionPK());
            }
            ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
            phjc.destroy(idProgramacionHoraria);
            List<ProgramacionHoraria> phs = phjc.findProgramacionHorariaEntities();
            for (ProgramacionHoraria ph : phs) {
                ph.setRutaList(null);
            }
            Gson gson = new Gson();
            String json = gson.toJson(phs);
            salida = response.getWriter();
            salida.write(json);
        } catch (NonexistentEntityException ex) {
            System.out.println("NonexistentEntityException");
            ex.printStackTrace();
        } catch (IllegalOrphanException ex) {
            System.out.println("IllegalOrphanException");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("IOException");
            ex.printStackTrace();
        }
    }

    private void cambiarNombrePH(HttpServletRequest request, HttpServletResponse response) {
        int idProgramacionHoraria = Integer.parseInt(request.getParameter("id_programacion_horaria"));
        String nombre = request.getParameter("nombre");
        ProgramacionHorariaJpaController phjc = new ProgramacionHorariaJpaController(Conex.getEmf());
        ProgramacionHoraria ph = phjc.findProgramacionHoraria(idProgramacionHoraria);
        ph.setNombreProgramacionHoraria(nombre);
        ph.setRutaList(null);
        try {
            phjc.edit(ph);
            Gson gson = new Gson();
            String json = gson.toJson(ph);
            PrintWriter salida = response.getWriter();
            salida.write(json);
        } catch (NonexistentEntityException ex) {
            System.out.println("cambiarNombrePH NonexistentEntityException");
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("cambiarNombrePH Exception");
            Logger.getLogger(GMT.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private void agregarRuta(HttpServletRequest request, HttpServletResponse response) throws IOException {
       PrintWriter salida=response.getWriter();
        try{
        String nombre=request.getParameter("nombre");
       String color=request.getParameter("color");
       int idProgramacion=Integer.parseInt(request.getParameter("idProgramacion"));
        System.out.println("nombre: "+nombre);
        System.out.println("color: "+color);
        System.out.println("idPH: "+idProgramacion);
        
        RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
        Ruta r=new Ruta();
        
        r.setColor(color);
        r.setNombre(nombre);
        
        int idRuta = 0;
        try{
        List<Integer> idsR = rjc.listarId();
        for (int i = 0; i < idsR.size(); i++) {
            if (idsR.get(i) != i) {
                idRuta = i;
                break;
            }
            if (i == idsR.size() - 1) {
                idRuta = idsR.size();
                break;
            }
        }
        }catch(NullPointerException e){
        e.printStackTrace();
        idRuta = 0;
        }
        
        RutaPK rpk=new RutaPK();
        rpk.setIdProgramacionHoraria(idProgramacion);
        rpk.setIdRuta(rjc.getRutaCount());
        r.setRutaPK(rpk);
        ProgramacionHorariaJpaController phjc=new ProgramacionHorariaJpaController(Conex.getEmf());
        ProgramacionHoraria ph= phjc.findProgramacionHoraria(idProgramacion);
        
        r.setProgramacionHoraria(ph);
        
        rjc.create(r);
        
        List<Ruta> rutas=rjc.buscarRutaPorPH(idProgramacion);
       List rutas2=new ArrayList();
        System.out.println(rutas);
        for (Ruta ruta : rutas) {
            ruta.setPreCirculacionList(null);
            ruta.setProgramacionHoraria(null);
            rutas2.add(ruta);
        }
        
        
       Gson gson=new Gson();
        System.out.println(gson.toJson(rutas2));
       String json=gson.toJson(rutas2);
        System.out.println(json);
       salida.write(json);
        }catch(Exception e){
        e.printStackTrace();
        }
    }

    private void agregarARuta(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, Exception {
       
        try{
        int idRuta=Integer.parseInt(request.getParameter("ruta"));
       String[] idCirculaciones=request.getParameterValues("circulaciones");
       int idProgramacion=Integer.parseInt(request.getParameter("idProgramacion"));
       PreCirculacionJpaController pcjc= new PreCirculacionJpaController(Conex.getEmf());
       PreCirculacion pc;
       PreCirculacion pc2;
       RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       RutaPK rpk=new RutaPK(idRuta, idProgramacion);
       Ruta r=rjc.findRuta(rpk);
           EstacionTramoParadaJpaController tepjc=new EstacionTramoParadaJpaController(Conex.getEmf()); 
            
       List<PreCirculacion> rr=pcjc.listarPreCirculaciones(idProgramacion);
          
            
                System.out.println(rr.size());
                
        for (int i = 0; i < idCirculaciones.length; i++) {
            
            pc=pcjc.buscarPorPH(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            pc2=pcjc.buscarPorPH(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            List<EstacionTramoParada> tep=tepjc.buscarTEPS(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            List<EstacionTramoParada> tep2=tepjc.buscarTEPS(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            for (EstacionTramoParada tep1 : tep) {
                tepjc.destroy(tep1.getEstacionTramoParadaPK());
            }
            System.out.println(pc.getPreCirculacionPK());
                       pcjc.destroy(pc.getPreCirculacionPK());
           PreCirculacionPK pcpk=new PreCirculacionPK(Integer.parseInt(idCirculaciones[i]), idRuta, idProgramacion);
           pc2.setPreCirculacionPK(pcpk);
           pc2.setRuta(r);
           pc2.setEstacionTramoParadaList(null);
           
           pcjc.create(pc2);
            for (EstacionTramoParada tep1 : tep2) {
                EstacionTramoParadaPK tepPK=new EstacionTramoParadaPK(tep1.getEstacionTramoParadaPK().getIdEstacion(), tep1.getEstacionTramoParadaPK().getIdPreCirculacion()
                        , idRuta, idProgramacion);
                tep1.setEstacionTramoParadaPK(tepPK);
                tep1.setPreCirculacion(pc2);
                tepjc.create(tep1);
            }
           
        }
        rr=pcjc.listarPreCirculaciones(idProgramacion);
          
            
                System.out.println(rr.size());
            
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    private void desvincularDeRuta(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, Exception {
       
        try{
       int idRuta=0;
       String[] idCirculaciones=request.getParameterValues("circulaciones");
       int idProgramacion=Integer.parseInt(request.getParameter("idProgramacion"));
       PreCirculacionJpaController pcjc= new PreCirculacionJpaController(Conex.getEmf());
       PreCirculacion pc;
       PreCirculacion pc2;
       RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       RutaPK rpk=new RutaPK(idRuta, idProgramacion);
       Ruta r=rjc.findRuta(rpk);
           EstacionTramoParadaJpaController tepjc=new EstacionTramoParadaJpaController(Conex.getEmf()); 
            
       List<PreCirculacion> rr=pcjc.listarPreCirculaciones(idProgramacion);
          
            
                System.out.println(rr.size());
                
        for (int i = 0; i < idCirculaciones.length; i++) {
            
            pc=pcjc.buscarPorPH(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            pc2=pcjc.buscarPorPH(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            List<EstacionTramoParada> tep=tepjc.buscarTEPS(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            List<EstacionTramoParada> tep2=tepjc.buscarTEPS(idProgramacion, Integer.parseInt(idCirculaciones[i]));
            for (EstacionTramoParada tep1 : tep) {
                tepjc.destroy(tep1.getEstacionTramoParadaPK());
            }
            System.out.println(pc.getPreCirculacionPK());
                       pcjc.destroy(pc.getPreCirculacionPK());
           PreCirculacionPK pcpk=new PreCirculacionPK(Integer.parseInt(idCirculaciones[i]), idRuta, idProgramacion);
           pc2.setPreCirculacionPK(pcpk);
           pc2.setRuta(r);
           pc2.setEstacionTramoParadaList(null);
           
           pcjc.create(pc2);
            for (EstacionTramoParada tep1 : tep2) {
                EstacionTramoParadaPK tepPK=new EstacionTramoParadaPK(tep1.getEstacionTramoParadaPK().getIdEstacion(), tep1.getEstacionTramoParadaPK().getIdPreCirculacion()
                        , idRuta, idProgramacion);
                tep1.setEstacionTramoParadaPK(tepPK);
                tep1.setPreCirculacion(pc2);
                tepjc.create(tep1);
            }
           
        }
        rr=pcjc.listarPreCirculaciones(idProgramacion);
          
            
                System.out.println(rr.size());
            
        }catch(Exception e){
        e.printStackTrace();
        }
    }

    private void cargarRutas(HttpServletRequest request, HttpServletResponse response) throws IOException {
       PrintWriter salida=response.getWriter();
        int idPH=Integer.parseInt(request.getParameter("idProgramacion"));
       
       RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       List<Ruta> rutas=rjc.buscarRutaPorPH(idPH);
       List rutas2=new ArrayList();
        System.out.println(rutas);
        for (Ruta ruta : rutas) {
            ruta.setPreCirculacionList(null);
            ruta.setProgramacionHoraria(null);
            rutas2.add(ruta);
        }
        
        
       Gson gson=new Gson();
        System.out.println(gson.toJson(rutas2));
       String json=gson.toJson(rutas2);
        System.out.println(json);
       salida.write(json);
    }

    private void eliminarRuta(HttpServletRequest request, HttpServletResponse response) throws IOException {
       PrintWriter salida=response.getWriter();
        try{
        int idRuta=Integer.parseInt(request.getParameter("idRuta"));
        if(idRuta!=0){
//       String[] idCirculaciones=request.getParameterValues("circulaciones");
       int idProgramacion=Integer.parseInt(request.getParameter("idProgramacion"));
       PreCirculacionJpaController pcjc= new PreCirculacionJpaController(Conex.getEmf());
//       PreCirculacion pc;
//       PreCirculacion pc2;
       RutaJpaController rjc=new RutaJpaController(Conex.getEmf());
       RutaPK rpk=new RutaPK(idRuta, idProgramacion);
       RutaPK rpk0=new RutaPK(0, idProgramacion);
       Ruta r=rjc.findRuta(rpk);
       Ruta ruta0=rjc.findRuta(rpk0);
           EstacionTramoParadaJpaController tepjc=new EstacionTramoParadaJpaController(Conex.getEmf()); 
            
       List<PreCirculacion> precirculaciones=pcjc.buscarPorRuta(idRuta, idProgramacion);
       List<PreCirculacion> precirculaciones2=pcjc.buscarPorRuta(idRuta, idProgramacion);
       List<EstacionTramoParada> tep1;
       List<EstacionTramoParada> tep2;
             for (int i = 0; i < precirculaciones.size(); i++) {
                 tep1=tepjc.buscarTEPS(idProgramacion, precirculaciones.get(i).getPreCirculacionPK().getIdPreCirculacion());
                 tep2=tepjc.buscarTEPS(idProgramacion, precirculaciones.get(i).getPreCirculacionPK().getIdPreCirculacion());
                 System.out.println("tep1: "+tep1.size());
                 for (EstacionTramoParada tep : tep1) {
                     tepjc.destroy(tep.getEstacionTramoParadaPK());
                     System.out.println("-------");
                 }
                 pcjc.destroy(precirculaciones.get(i).getPreCirculacionPK());
                PreCirculacionPK pcpk=new PreCirculacionPK(precirculaciones2.get(i).getPreCirculacionPK().getIdPreCirculacion(), 0, idProgramacion);
                 precirculaciones2.get(i).setPreCirculacionPK(pcpk);
                 precirculaciones2.get(i).setRuta(ruta0);
                 precirculaciones2.get(i).setEstacionTramoParadaList(null);
                 
                 pcjc.create(precirculaciones2.get(i));
                 System.out.println("tep2: "+tep2.size());
                 for (EstacionTramoParada tep21 : tep2) {
                     EstacionTramoParadaPK tepPK=new EstacionTramoParadaPK(tep21.getEstacionTramoParadaPK().getIdEstacion(), tep21.getEstacionTramoParadaPK().getIdPreCirculacion()
                        , 0, idProgramacion);
                tep21.setEstacionTramoParadaPK(tepPK);
                tep21.setPreCirculacion(precirculaciones2.get(i));
                tepjc.create(tep21);
                     System.out.println("******");
                 }
             }
             rjc.destroy(r.getRutaPK());
       List<Ruta> rutas=rjc.buscarRutaPorPH(idProgramacion);
       List rutas2=new ArrayList();
        System.out.println(rutas);
            
        for (Ruta ruta : rutas) {
            ruta.setPreCirculacionList(null);
            ruta.setProgramacionHoraria(null);
            rutas2.add(ruta);
        }
        
        
       Gson gson=new Gson();
        System.out.println(gson.toJson(rutas2));
       String json=gson.toJson(rutas2);
        System.out.println(json);
       salida.write(json);
        
        }
            
        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
