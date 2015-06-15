/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controlBD;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.MarchaTipo;
import modelo.entity.TiempoEstacionMarchaTipo;
import modelo.entity.TiempoEstacionMarchaTipoPK;

/**
 *
 * @author Kelvins Insua
 */
public class TiempoEstacionMarchaTipoJpaController implements Serializable {

    public TiempoEstacionMarchaTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TiempoEstacionMarchaTipo tiempoEstacionMarchaTipo) throws PreexistingEntityException, Exception {
        if (tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK() == null) {
            tiempoEstacionMarchaTipo.setTiempoEstacionMarchaTipoPK(new TiempoEstacionMarchaTipoPK());
        }
        tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK().setIdMarchaTipo(tiempoEstacionMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MarchaTipo marchaTipo = tiempoEstacionMarchaTipo.getMarchaTipo();
            if (marchaTipo != null) {
                marchaTipo = em.getReference(marchaTipo.getClass(), marchaTipo.getIdMarchaTipo());
                tiempoEstacionMarchaTipo.setMarchaTipo(marchaTipo);
            }
            em.persist(tiempoEstacionMarchaTipo);
            if (marchaTipo != null) {
                marchaTipo.getTiempoEstacionMarchaTipoList().add(tiempoEstacionMarchaTipo);
                marchaTipo = em.merge(marchaTipo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTiempoEstacionMarchaTipo(tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK()) != null) {
                throw new PreexistingEntityException("TiempoEstacionMarchaTipo " + tiempoEstacionMarchaTipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TiempoEstacionMarchaTipo tiempoEstacionMarchaTipo) throws NonexistentEntityException, Exception {
        tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK().setIdMarchaTipo(tiempoEstacionMarchaTipo.getMarchaTipo().getIdMarchaTipo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            TiempoEstacionMarchaTipo persistentTiempoEstacionMarchaTipo = em.find(TiempoEstacionMarchaTipo.class, tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK());
//            MarchaTipo marchaTipoOld = persistentTiempoEstacionMarchaTipo.getMarchaTipo();
//            MarchaTipo marchaTipoNew = tiempoEstacionMarchaTipo.getMarchaTipo();
//            if (marchaTipoNew != null) {
//                marchaTipoNew = em.getReference(marchaTipoNew.getClass(), marchaTipoNew.getIdMarchaTipo());
//                tiempoEstacionMarchaTipo.setMarchaTipo(marchaTipoNew);
//            }
            tiempoEstacionMarchaTipo = em.merge(tiempoEstacionMarchaTipo);
//            if (marchaTipoOld != null && !marchaTipoOld.equals(marchaTipoNew)) {
//                marchaTipoOld.getTiempoEstacionMarchaTipoList().remove(tiempoEstacionMarchaTipo);
//                marchaTipoOld = em.merge(marchaTipoOld);
//            }
//            if (marchaTipoNew != null && !marchaTipoNew.equals(marchaTipoOld)) {
//                marchaTipoNew.getTiempoEstacionMarchaTipoList().add(tiempoEstacionMarchaTipo);
//                marchaTipoNew = em.merge(marchaTipoNew);
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                TiempoEstacionMarchaTipoPK id = tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK();
                if (findTiempoEstacionMarchaTipo(id) == null) {
                    throw new NonexistentEntityException("The tiempoEstacionMarchaTipo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(TiempoEstacionMarchaTipoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TiempoEstacionMarchaTipo tiempoEstacionMarchaTipo;
            try {
                tiempoEstacionMarchaTipo = em.getReference(TiempoEstacionMarchaTipo.class, id);
                tiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tiempoEstacionMarchaTipo with id " + id + " no longer exists.", enfe);
            }
//            MarchaTipo marchaTipo = tiempoEstacionMarchaTipo.getMarchaTipo();
//            if (marchaTipo != null) {
//                marchaTipo.getTiempoEstacionMarchaTipoList().remove(tiempoEstacionMarchaTipo);
//                marchaTipo = em.merge(marchaTipo);
//            }
            em.remove(tiempoEstacionMarchaTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TiempoEstacionMarchaTipo> findTiempoEstacionMarchaTipoEntities() {
        return findTiempoEstacionMarchaTipoEntities(true, -1, -1);
    }

    public List<TiempoEstacionMarchaTipo> findTiempoEstacionMarchaTipoEntities(int maxResults, int firstResult) {
        return findTiempoEstacionMarchaTipoEntities(false, maxResults, firstResult);
    }

    private List<TiempoEstacionMarchaTipo> findTiempoEstacionMarchaTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiempoEstacionMarchaTipo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TiempoEstacionMarchaTipo findTiempoEstacionMarchaTipo(TiempoEstacionMarchaTipoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TiempoEstacionMarchaTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTiempoEstacionMarchaTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TiempoEstacionMarchaTipo> rt = cq.from(TiempoEstacionMarchaTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<TiempoEstacionMarchaTipo> buscarPorIdMarchaTipoAsc(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByIdMarchaTipoAsc",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
        public List<TiempoEstacionMarchaTipo> buscarPorIdMarchaTipoDesc(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByIdMarchaTipoDesc",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }

    public TiempoEstacionMarchaTipo buscarPorPKTiempoEstacion(int idMarchaTipo,double idPkEstacion){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByPKTiempoEstacion",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            cq.setParameter("idPkEstacion", idPkEstacion);
             return cq.getSingleResult();
           } finally {
            em.close();
        } 
    }
    
    public List<TiempoEstacionMarchaTipo> buscarPorIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByIdMarchaTipo",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
     public TiempoEstacionMarchaTipo buscarEstacionInicialIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByProgEstacionInicial",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getSingleResult();
           } finally {
            em.close();
        } 
    }
    
    // Creado 25/03/2015
    public TiempoEstacionMarchaTipo buscarEstacionFinalIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByProgEstacionFinal",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getSingleResult();
           } finally {
            em.close();
        } 
    }
    
    public List<Double> buscarProgParadasDeMT(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Double> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findProgParada",Double.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            // Modificado el 8-06-2015
            cq.setParameter("parada", true);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    public List<TiempoEstacionMarchaTipo> buscarPorIdMarchaTipoSalidaLlegada(int idMarchaTipo, boolean sentido,double salida, double llegada){
      EntityManager em=getEntityManager();
        try {
            TypedQuery<TiempoEstacionMarchaTipo> cq;
            if(sentido==true){
                cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByIdMarchaTipoSalidaLlegadaAsc",TiempoEstacionMarchaTipo.class);
            }else{
                cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByIdMarchaTipoSalidaLlegadaDesc",TiempoEstacionMarchaTipo.class);
            }
            
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            cq.setParameter("salida", salida);
            cq.setParameter("llegada", llegada);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    public List<TiempoEstacionMarchaTipo> buscarParadaIdMarchaTipo(int idMarchaTipo){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findByParada",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            //Modificado el 8-06-2015
            cq.setParameter("parada", true);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    public List<TiempoEstacionMarchaTipo> buscarParadasIntermediasIdMarchaTipoAsc(int idMarchaTipo, double estInicio, double estFinal){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findParadasIntermediasAsc",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            cq.setParameter("estInicio", estInicio);
            cq.setParameter("estFinal", estFinal);
            //Modificado el 8-06-2015
            cq.setParameter("parada", true);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    public List<TiempoEstacionMarchaTipo> buscarParadasIntermediasIdMarchaTipoDesc(int idMarchaTipo, double estInicio, double estFinal){
      EntityManager em=getEntityManager();
        try {
            
            TypedQuery<TiempoEstacionMarchaTipo> cq=em.createNamedQuery("TiempoEstacionMarchaTipo.findParadasIntermediasDesc",TiempoEstacionMarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
            cq.setParameter("estInicio", estInicio);
            cq.setParameter("estFinal", estFinal);
            //Modificado el 8-06-2015
            cq.setParameter("parada", true);
             return cq.getResultList();
           } finally {
            em.close();
        } 
    }
    
}
