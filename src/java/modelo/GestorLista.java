package modelo;

import java.util.ArrayList;
import modelo.controlBD.CircuitoViaJpaController;
import modelo.entity.CircuitoVia;
import modelo.entity.CurvaEsfuerzo;
import modelo.entity.Estacion;
import modelo.entity.Linea;
import modelo.entity.MaterialRodante;
import modelo.entity.Restriccion;
import modelo.entity.Segmento;
import modelo.entity.SegmentoPK;
import java.util.List;
import modelo.controlBD.CurvaEsfuerzoJpaController;
import modelo.controlBD.EstacionJpaController;
import modelo.controlBD.IdentificadorTrenJpaController;
import modelo.controlBD.LineaJpaController;
import modelo.controlBD.MarchaTipoJpaController;
import modelo.controlBD.MaterialRodanteJpaController;
import modelo.controlBD.RestriccionJpaController;
import modelo.controlBD.RestriccionMarchaTipoJpaController;
import modelo.controlBD.SegmentoJpaController;
import modelo.controlBD.TiempoEstacionMarchaTipoJpaController;
import modelo.controlBD.UsuarioJpaController;
import modelo.entity.CurvaEsfuerzoPK;
import modelo.entity.IdentificadorTren;
import modelo.entity.MarchaTipo;
import modelo.entity.RestriccionMarchaTipo;
import modelo.entity.TiempoEstacionMarchaTipo;
import modelo.entity.Usuario;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kelvins Insua
 */
public class GestorLista {
    
    
public List<Segmento> listaSegmento(int id_linea){
        SegmentoJpaController sjc=new SegmentoJpaController(Conex.getEmf());
//        return sjc.buscarIdLineaDescendente(id_linea);
        return sjc.buscarIdLineaDescendente(id_linea);
    }
public List<CurvaEsfuerzo> listaEsfuerzos(int id_mr){
       CurvaEsfuerzoJpaController crjc =new CurvaEsfuerzoJpaController(Conex.getEmf());
        return crjc.curvaDelMaterialRodante(id_mr);
    }
public CurvaEsfuerzo buscarCurva(int idMatRod, double vel){
        CurvaEsfuerzoJpaController crjc =new CurvaEsfuerzoJpaController(Conex.getEmf());
        CurvaEsfuerzoPK cpk= new CurvaEsfuerzoPK(idMatRod, vel);
        return crjc.findCurvaEsfuerzo(cpk);
    }

public Segmento buscarSegmento(SegmentoPK spk){
        SegmentoJpaController sjc=new SegmentoJpaController(Conex.getEmf());
        return sjc.findSegmento(spk);        
    }
public List<MaterialRodante> listaMaterialRodante(){
        MaterialRodanteJpaController mrjc=new MaterialRodanteJpaController(Conex.getEmf());
        return mrjc.findMaterialRodanteEntities();
        
    }
public MaterialRodante buscarMaterialRodante(int id_mr){
    MaterialRodanteJpaController mrjc=new MaterialRodanteJpaController(Conex.getEmf());
    return mrjc.findMaterialRodante(id_mr);
}
public Linea buscarLinea(int id_linea){
    LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
    return ljc.findLinea(id_linea);
}
public List<Linea> listaLinea(){
        LineaJpaController ljc=new LineaJpaController(Conex.getEmf());
        return ljc.findLineaEntities();
        
    }
public List<Estacion> buscarEstacion(int id_linea){
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        return ejc.buscarEstacion(id_linea);
    }
public List<Restriccion> buscarRestriccion(int id_linea){
        RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
        return rjc.buscarRestriccion(id_linea);
    }
public List<Restriccion> buscarRestriccionVel(int id_linea, double vel){
        RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
        return rjc.buscarIdLineaVelocidad(id_linea, vel);
    }

public List<Restriccion> buscarRestriccionEntreEstacionesAscendente(int id_linea,double progEstacionInicial,double progEstacionFinal, double vel){
        RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
        return rjc.restriccionEntreEstacionesAscendente(id_linea, progEstacionInicial, progEstacionFinal, vel);
    }
public List<Restriccion> buscarRestriccionEntreEstacionesDescendente(int id_linea,double progEstacionInicial,double progEstacionFinal, double vel){
        RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
        return rjc.restriccionEntreEstacionesDescendente(id_linea, progEstacionInicial, progEstacionFinal, vel);
    }
public List<CircuitoVia> buscarCircuitoVia(int id_linea){
        CircuitoViaJpaController cvjc=new CircuitoViaJpaController(Conex.getEmf());
        return cvjc.buscarCircuitoVia(id_linea);
    }
public Segmento buscarSegmentoPorPK(int idLinea, double idPkInicial){
SegmentoJpaController sjc=new SegmentoJpaController(Conex.getEmf());
return sjc.buscarSegmentoPK(idLinea, idPkInicial);
}
public Restriccion buscarRestriccionPorPK(int idLinea, int idRestriccion){
RestriccionJpaController rjc=new RestriccionJpaController(Conex.getEmf());
return rjc.buscarRestriccionPK(idLinea, idRestriccion);
}
public Estacion buscarEstacionPorPK(int idLinea, double idPkEstacion){
EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
return ejc.buscarEstacionPK(idLinea, idPkEstacion);
}

public CircuitoVia buscarCircuitoViaPorPK(int idLinea, double idPkInicialCircuito){
CircuitoViaJpaController cvjc=new CircuitoViaJpaController(Conex.getEmf());
return cvjc.buscarCircuitoViaPK(idLinea, idPkInicialCircuito);
}

public List<Double> buscarVelocidadesMR(int id_mr){
    CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
    List<CurvaEsfuerzo> curvas =cejc.curvaDelMaterialRodante(id_mr);
    List<Double> vel = new ArrayList<>();
    for (int i = 0; i < curvas.size(); i++) {
       vel.add(curvas.get(i).getCurvaEsfuerzoPK().getIdVelocidadCurvaEsfuerzo());
        
    }
        
    
    
    return vel;

}

public List<Double> buscarTraccionMR(int id_mr){
    CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
    List<CurvaEsfuerzo> curvas =cejc.curvaDelMaterialRodante(id_mr);
    List<Double> trac = new ArrayList<>();
    for (int i = 0; i < curvas.size(); i++) {
       trac.add(curvas.get(i).getEsfuerzoTraccion());
        
    }
        
    
    
    return trac;

}
 
public List<Double> buscarFrenadoMR(int id_mr){
    CurvaEsfuerzoJpaController cejc=new CurvaEsfuerzoJpaController(Conex.getEmf());
    List<CurvaEsfuerzo> curvas =cejc.curvaDelMaterialRodante(id_mr);
    List<Double> fren = new ArrayList<>();
    for (int i = 0; i < curvas.size(); i++) {
       fren.add(curvas.get(i).getEsfuerzoFrenado());
        
    }
        
    
    
    return fren;

}

public List<MarchaTipo> listaMarchaTipo(){
    MarchaTipoJpaController mtjc=new MarchaTipoJpaController(Conex.getEmf());
    List<MarchaTipo> marchasTipo=mtjc.findMarchaTipoEntities();
    return marchasTipo;
    
}

public MarchaTipo buscarMarchaTipo(int idMarchaTipo){
 MarchaTipoJpaController mtjc=new MarchaTipoJpaController(Conex.getEmf());
 MarchaTipo mt=mtjc.bucarMarchaTipo(idMarchaTipo);
 
 return mt;
}

public List<TiempoEstacionMarchaTipo> buscarTiemposMarchaTipo(int idMarchaTipo,boolean sentido){
    
    TiempoEstacionMarchaTipoJpaController temtjc=new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
    List<TiempoEstacionMarchaTipo> tiempos;
    if(sentido==true){
    tiempos=temtjc.buscarPorIdMarchaTipoAsc(idMarchaTipo);
    }else{
    tiempos=temtjc.buscarPorIdMarchaTipoDesc(idMarchaTipo);}
    
    return tiempos;
}

public List<RestriccionMarchaTipo> buscarRestriccionesMarchaTipo(int idMarchaTipo){
    RestriccionMarchaTipoJpaController temtjc=new RestriccionMarchaTipoJpaController(Conex.getEmf());
    List<RestriccionMarchaTipo> restricciones=temtjc.buscarPorIdMarchaTipo(idMarchaTipo);
    
    return restricciones;
}

public TiempoEstacionMarchaTipo buscarTiempoEstacionMTPK(int idMarchaTipo, double idPkEstacion){
    TiempoEstacionMarchaTipoJpaController temtjc=new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
    TiempoEstacionMarchaTipo temt=temtjc.buscarPorPKTiempoEstacion(idMarchaTipo, idPkEstacion);
    
    return temt;

}

public List<MarchaTipo> listarMarchaTipoAsc(int idLinea) {
        MarchaTipoJpaController mtjc = new MarchaTipoJpaController(Conex.getEmf());
        List<MarchaTipo> mt = mtjc.listarMarchaTipoAsc(idLinea);
        return mt;
    }
    public List<MarchaTipo> listarMarchaTipoDesc(int idLinea) {
        MarchaTipoJpaController mtjc = new MarchaTipoJpaController(Conex.getEmf());
        List<MarchaTipo> mt = mtjc.listarMarchaTipoDesc(idLinea);
        return mt;
    }
    
    // Aqui estan los 4 (Cuatro metodos) que listan los prefijos de la numeracion
    
    public List<IdentificadorTren> listarServicio() {
        IdentificadorTrenJpaController itjc = new IdentificadorTrenJpaController(Conex.getEmf());
        List<IdentificadorTren> it = itjc.listarServicio();
        return it;
    }
    public List<IdentificadorTren> listarCRT() {
        IdentificadorTrenJpaController itjc = new IdentificadorTrenJpaController(Conex.getEmf());
        List<IdentificadorTren> it = itjc.listarCRT();
        return it;
    }
    public List<IdentificadorTren> listarCategoriaIdentificacion() {
        IdentificadorTrenJpaController itjc = new IdentificadorTrenJpaController(Conex.getEmf());
        List<IdentificadorTren> it = itjc.listarCategoriaIdentificacion();
        return it;
    }
    public List<IdentificadorTren> listarEmpresaPropietaria() {
        IdentificadorTrenJpaController itjc = new IdentificadorTrenJpaController(Conex.getEmf());
        List<IdentificadorTren> it = itjc.listarEmpresaPropietaria();
        return it;
    }
    
    public List<Estacion> seleccionarParadaEstacionAsc(double pkInicio, double pkFinal,int idLinea){
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        List<Estacion> estaciones=ejc.buscarEstacionesMTAscConParada(pkInicio, pkFinal, idLinea);
        return estaciones;
        
    
    }
    public List<Estacion> seleccionarParadaEstacionDesc(double pkInicio, double pkFinal,int idLinea){
        EstacionJpaController ejc=new EstacionJpaController(Conex.getEmf());
        List<Estacion> estaciones=ejc.buscarEstacionesMTDescConParada(pkInicio, pkFinal, idLinea);
        return estaciones;
        
    
    }
    
    public List<Usuario> listaUsuario(){
        UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());
        List<Usuario> usuarios=ujc.findUsuarioEntities();
        return usuarios;
    }
    
    public Usuario buscarUsuario(String idUsuario){
    Usuario usuario;
    UsuarioJpaController ujc=new UsuarioJpaController(Conex.getEmf());
    usuario=ujc.findUsuario(idUsuario);
    
    return usuario;
    }
public List<TiempoEstacionMarchaTipo> buscarParadasMT(int idMarchaTipo) {
        TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
      return temtjc.buscarParadaIdMarchaTipo(idMarchaTipo);
    }
//    public List<TiempoEstacionMarchaTipo> buscarParadasSalida(int idMarchaTipo){
    public TiempoEstacionMarchaTipo buscarParadasSalida(int idMarchaTipo){
        TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
        return temtjc.buscarEstacionInicialIdMarchaTipo(idMarchaTipo);
    }
    public TiempoEstacionMarchaTipo buscarParadasLlegada(int idMarchaTipo){
        TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
        return temtjc.buscarEstacionFinalIdMarchaTipo(idMarchaTipo);
    }
    public List<TiempoEstacionMarchaTipo> buscarParadasIntermediasMT(int idMarchaTipo, double estInicio, double estFinal) {
        TiempoEstacionMarchaTipoJpaController temtjc = new TiempoEstacionMarchaTipoJpaController(Conex.getEmf());
        if(estFinal > estInicio){
        return temtjc.buscarParadasIntermediasIdMarchaTipoAsc(idMarchaTipo,estInicio,estFinal);
        }else{
        return temtjc.buscarParadasIntermediasIdMarchaTipoDesc(idMarchaTipo,estInicio,estFinal);
        }
}
}
