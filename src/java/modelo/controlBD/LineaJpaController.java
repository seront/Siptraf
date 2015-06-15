/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.controlBD;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.entity.Restriccion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.Afluencia;
import modelo.entity.Segmento;
import modelo.entity.Estacion;
import modelo.entity.CircuitoVia;
import modelo.entity.Linea;

/**
 *
 * @author Kelvins Insua
 */
public class LineaJpaController implements Serializable {

    public LineaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Linea linea) throws PreexistingEntityException, Exception {
        if (linea.getRestriccionList() == null) {
            linea.setRestriccionList(new ArrayList<Restriccion>());
        }
        if (linea.getSegmentoList() == null) {
            linea.setSegmentoList(new ArrayList<Segmento>());
        }
        if (linea.getEstacionList() == null) {
            linea.setEstacionList(new ArrayList<Estacion>());
        }
        if (linea.getCircuitoViaList() == null) {
            linea.setCircuitoViaList(new ArrayList<CircuitoVia>());
        }
        if (linea.getAfluenciaList() == null) {
            linea.setAfluenciaList(new ArrayList<Afluencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Restriccion> attachedRestriccionList = new ArrayList<Restriccion>();
            for (Restriccion restriccionListRestriccionToAttach : linea.getRestriccionList()) {
                restriccionListRestriccionToAttach = em.getReference(restriccionListRestriccionToAttach.getClass(), restriccionListRestriccionToAttach.getRestriccionPK());
                attachedRestriccionList.add(restriccionListRestriccionToAttach);
            }
            linea.setRestriccionList(attachedRestriccionList);
            List<Segmento> attachedSegmentoList = new ArrayList<Segmento>();
            for (Segmento segmentoListSegmentoToAttach : linea.getSegmentoList()) {
                segmentoListSegmentoToAttach = em.getReference(segmentoListSegmentoToAttach.getClass(), segmentoListSegmentoToAttach.getSegmentoPK());
                attachedSegmentoList.add(segmentoListSegmentoToAttach);
            }
            linea.setSegmentoList(attachedSegmentoList);
            List<Estacion> attachedEstacionList = new ArrayList<Estacion>();
            for (Estacion estacionListEstacionToAttach : linea.getEstacionList()) {
                estacionListEstacionToAttach = em.getReference(estacionListEstacionToAttach.getClass(), estacionListEstacionToAttach.getEstacionPK());
                attachedEstacionList.add(estacionListEstacionToAttach);
            }
            linea.setEstacionList(attachedEstacionList);
            List<CircuitoVia> attachedCircuitoViaList = new ArrayList<CircuitoVia>();
            for (CircuitoVia circuitoViaListCircuitoViaToAttach : linea.getCircuitoViaList()) {
                circuitoViaListCircuitoViaToAttach = em.getReference(circuitoViaListCircuitoViaToAttach.getClass(), circuitoViaListCircuitoViaToAttach.getCircuitoViaPK());
                attachedCircuitoViaList.add(circuitoViaListCircuitoViaToAttach);
            }
            linea.setCircuitoViaList(attachedCircuitoViaList);
             List<Afluencia> attachedAfluenciaList = new ArrayList<Afluencia>();
            for (Afluencia afluenciaListAfluenciaToAttach : linea.getAfluenciaList()) {
                afluenciaListAfluenciaToAttach = em.getReference(afluenciaListAfluenciaToAttach.getClass(), afluenciaListAfluenciaToAttach.getAfluenciaPK());
                attachedAfluenciaList.add(afluenciaListAfluenciaToAttach);
            }
            linea.setAfluenciaList(attachedAfluenciaList);
            em.persist(linea);
            for (Restriccion restriccionListRestriccion : linea.getRestriccionList()) {
                Linea oldLineaOfRestriccionListRestriccion = restriccionListRestriccion.getLinea();
                restriccionListRestriccion.setLinea(linea);
                restriccionListRestriccion = em.merge(restriccionListRestriccion);
                if (oldLineaOfRestriccionListRestriccion != null) {
                    oldLineaOfRestriccionListRestriccion.getRestriccionList().remove(restriccionListRestriccion);
                    oldLineaOfRestriccionListRestriccion = em.merge(oldLineaOfRestriccionListRestriccion);
                }
            }
            for (Segmento segmentoListSegmento : linea.getSegmentoList()) {
                Linea oldLineaOfSegmentoListSegmento = segmentoListSegmento.getLinea();
                segmentoListSegmento.setLinea(linea);
                segmentoListSegmento = em.merge(segmentoListSegmento);
                if (oldLineaOfSegmentoListSegmento != null) {
                    oldLineaOfSegmentoListSegmento.getSegmentoList().remove(segmentoListSegmento);
                    oldLineaOfSegmentoListSegmento = em.merge(oldLineaOfSegmentoListSegmento);
                }
            }
            for (Estacion estacionListEstacion : linea.getEstacionList()) {
                Linea oldLineaOfEstacionListEstacion = estacionListEstacion.getLinea();
                estacionListEstacion.setLinea(linea);
                estacionListEstacion = em.merge(estacionListEstacion);
                if (oldLineaOfEstacionListEstacion != null) {
                    oldLineaOfEstacionListEstacion.getEstacionList().remove(estacionListEstacion);
                    oldLineaOfEstacionListEstacion = em.merge(oldLineaOfEstacionListEstacion);
                }
            }
            for (CircuitoVia circuitoViaListCircuitoVia : linea.getCircuitoViaList()) {
                Linea oldLineaOfCircuitoViaListCircuitoVia = circuitoViaListCircuitoVia.getLinea();
                circuitoViaListCircuitoVia.setLinea(linea);
                circuitoViaListCircuitoVia = em.merge(circuitoViaListCircuitoVia);
                if (oldLineaOfCircuitoViaListCircuitoVia != null) {
                    oldLineaOfCircuitoViaListCircuitoVia.getCircuitoViaList().remove(circuitoViaListCircuitoVia);
                    oldLineaOfCircuitoViaListCircuitoVia = em.merge(oldLineaOfCircuitoViaListCircuitoVia);
                }
            }
             for (Afluencia afluenciaListAfluencia : linea.getAfluenciaList()) {
                Linea oldLineaOfAfluenciaListAfluencia = afluenciaListAfluencia.getLinea();
                afluenciaListAfluencia.setLinea(linea);
                afluenciaListAfluencia = em.merge(afluenciaListAfluencia);
                if (oldLineaOfAfluenciaListAfluencia != null) {
                    oldLineaOfAfluenciaListAfluencia.getAfluenciaList().remove(afluenciaListAfluencia);
                    oldLineaOfAfluenciaListAfluencia = em.merge(oldLineaOfAfluenciaListAfluencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLinea(linea.getIdLinea()) != null) {
                throw new PreexistingEntityException("Linea " + linea + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Linea linea) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            Linea persistentLinea = em.find(Linea.class, linea.getIdLinea());
//            List<Restriccion> restriccionListOld = persistentLinea.getRestriccionList();
//            List<Restriccion> restriccionListNew = linea.getRestriccionList();
//            List<Segmento> segmentoListOld = persistentLinea.getSegmentoList();
//            List<Segmento> segmentoListNew = linea.getSegmentoList();
//            List<Estacion> estacionListOld = persistentLinea.getEstacionList();
//            List<Estacion> estacionListNew = linea.getEstacionList();
//            List<CircuitoVia> circuitoViaListOld = persistentLinea.getCircuitoViaList();
//            List<CircuitoVia> circuitoViaListNew = linea.getCircuitoViaList();
//            List<String> illegalOrphanMessages = null;
//            for (Restriccion restriccionListOldRestriccion : restriccionListOld) {
//                if (!restriccionListNew.contains(restriccionListOldRestriccion)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Restriccion " + restriccionListOldRestriccion + " since its linea field is not nullable.");
//                }
//            }
//            for (Segmento segmentoListOldSegmento : segmentoListOld) {
//                if (!segmentoListNew.contains(segmentoListOldSegmento)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Segmento " + segmentoListOldSegmento + " since its linea field is not nullable.");
//                }
//            }
//            for (Estacion estacionListOldEstacion : estacionListOld) {
//                if (!estacionListNew.contains(estacionListOldEstacion)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Estacion " + estacionListOldEstacion + " since its linea field is not nullable.");
//                }
//            }
//            for (CircuitoVia circuitoViaListOldCircuitoVia : circuitoViaListOld) {
//                if (!circuitoViaListNew.contains(circuitoViaListOldCircuitoVia)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain CircuitoVia " + circuitoViaListOldCircuitoVia + " since its linea field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Restriccion> attachedRestriccionListNew = new ArrayList<Restriccion>();
//            for (Restriccion restriccionListNewRestriccionToAttach : restriccionListNew) {
//                restriccionListNewRestriccionToAttach = em.getReference(restriccionListNewRestriccionToAttach.getClass(), restriccionListNewRestriccionToAttach.getRestriccionPK());
//                attachedRestriccionListNew.add(restriccionListNewRestriccionToAttach);
//            }
//            restriccionListNew = attachedRestriccionListNew;
//            linea.setRestriccionList(restriccionListNew);
//            List<Segmento> attachedSegmentoListNew = new ArrayList<Segmento>();
//            for (Segmento segmentoListNewSegmentoToAttach : segmentoListNew) {
//                segmentoListNewSegmentoToAttach = em.getReference(segmentoListNewSegmentoToAttach.getClass(), segmentoListNewSegmentoToAttach.getSegmentoPK());
//                attachedSegmentoListNew.add(segmentoListNewSegmentoToAttach);
//            }
//            segmentoListNew = attachedSegmentoListNew;
//            linea.setSegmentoList(segmentoListNew);
//            List<Estacion> attachedEstacionListNew = new ArrayList<Estacion>();
//            for (Estacion estacionListNewEstacionToAttach : estacionListNew) {
//                estacionListNewEstacionToAttach = em.getReference(estacionListNewEstacionToAttach.getClass(), estacionListNewEstacionToAttach.getEstacionPK());
//                attachedEstacionListNew.add(estacionListNewEstacionToAttach);
//            }
//            estacionListNew = attachedEstacionListNew;
//            linea.setEstacionList(estacionListNew);
//            List<CircuitoVia> attachedCircuitoViaListNew = new ArrayList<CircuitoVia>();
//            for (CircuitoVia circuitoViaListNewCircuitoViaToAttach : circuitoViaListNew) {
//                circuitoViaListNewCircuitoViaToAttach = em.getReference(circuitoViaListNewCircuitoViaToAttach.getClass(), circuitoViaListNewCircuitoViaToAttach.getCircuitoViaPK());
//                attachedCircuitoViaListNew.add(circuitoViaListNewCircuitoViaToAttach);
//            }
//            circuitoViaListNew = attachedCircuitoViaListNew;
//            linea.setCircuitoViaList(circuitoViaListNew);
            linea = em.merge(linea);
//            for (Restriccion restriccionListNewRestriccion : restriccionListNew) {
//                if (!restriccionListOld.contains(restriccionListNewRestriccion)) {
//                    Linea oldLineaOfRestriccionListNewRestriccion = restriccionListNewRestriccion.getLinea();
//                    restriccionListNewRestriccion.setLinea(linea);
//                    restriccionListNewRestriccion = em.merge(restriccionListNewRestriccion);
//                    if (oldLineaOfRestriccionListNewRestriccion != null && !oldLineaOfRestriccionListNewRestriccion.equals(linea)) {
//                        oldLineaOfRestriccionListNewRestriccion.getRestriccionList().remove(restriccionListNewRestriccion);
//                        oldLineaOfRestriccionListNewRestriccion = em.merge(oldLineaOfRestriccionListNewRestriccion);
//                    }
//                }
//            }
//            for (Segmento segmentoListNewSegmento : segmentoListNew) {
//                if (!segmentoListOld.contains(segmentoListNewSegmento)) {
//                    Linea oldLineaOfSegmentoListNewSegmento = segmentoListNewSegmento.getLinea();
//                    segmentoListNewSegmento.setLinea(linea);
//                    segmentoListNewSegmento = em.merge(segmentoListNewSegmento);
//                    if (oldLineaOfSegmentoListNewSegmento != null && !oldLineaOfSegmentoListNewSegmento.equals(linea)) {
//                        oldLineaOfSegmentoListNewSegmento.getSegmentoList().remove(segmentoListNewSegmento);
//                        oldLineaOfSegmentoListNewSegmento = em.merge(oldLineaOfSegmentoListNewSegmento);
//                    }
//                }
//            }
//            for (Estacion estacionListNewEstacion : estacionListNew) {
//                if (!estacionListOld.contains(estacionListNewEstacion)) {
//                    Linea oldLineaOfEstacionListNewEstacion = estacionListNewEstacion.getLinea();
//                    estacionListNewEstacion.setLinea(linea);
//                    estacionListNewEstacion = em.merge(estacionListNewEstacion);
//                    if (oldLineaOfEstacionListNewEstacion != null && !oldLineaOfEstacionListNewEstacion.equals(linea)) {
//                        oldLineaOfEstacionListNewEstacion.getEstacionList().remove(estacionListNewEstacion);
//                        oldLineaOfEstacionListNewEstacion = em.merge(oldLineaOfEstacionListNewEstacion);
//                    }
//                }
//            }
//            for (CircuitoVia circuitoViaListNewCircuitoVia : circuitoViaListNew) {
//                if (!circuitoViaListOld.contains(circuitoViaListNewCircuitoVia)) {
//                    Linea oldLineaOfCircuitoViaListNewCircuitoVia = circuitoViaListNewCircuitoVia.getLinea();
//                    circuitoViaListNewCircuitoVia.setLinea(linea);
//                    circuitoViaListNewCircuitoVia = em.merge(circuitoViaListNewCircuitoVia);
//                    if (oldLineaOfCircuitoViaListNewCircuitoVia != null && !oldLineaOfCircuitoViaListNewCircuitoVia.equals(linea)) {
//                        oldLineaOfCircuitoViaListNewCircuitoVia.getCircuitoViaList().remove(circuitoViaListNewCircuitoVia);
//                        oldLineaOfCircuitoViaListNewCircuitoVia = em.merge(oldLineaOfCircuitoViaListNewCircuitoVia);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = linea.getIdLinea();
                if (findLinea(id) == null) {
                    throw new NonexistentEntityException("The linea with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Linea linea;
            try {
                linea = em.getReference(Linea.class, id);
                linea.getIdLinea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The linea with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<Restriccion> restriccionListOrphanCheck = linea.getRestriccionList();
//            for (Restriccion restriccionListOrphanCheckRestriccion : restriccionListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Linea (" + linea + ") cannot be destroyed since the Restriccion " + restriccionListOrphanCheckRestriccion + " in its restriccionList field has a non-nullable linea field.");
//            }
//            List<Segmento> segmentoListOrphanCheck = linea.getSegmentoList();
//            for (Segmento segmentoListOrphanCheckSegmento : segmentoListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Linea (" + linea + ") cannot be destroyed since the Segmento " + segmentoListOrphanCheckSegmento + " in its segmentoList field has a non-nullable linea field.");
//            }
//            List<Estacion> estacionListOrphanCheck = linea.getEstacionList();
//            for (Estacion estacionListOrphanCheckEstacion : estacionListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Linea (" + linea + ") cannot be destroyed since the Estacion " + estacionListOrphanCheckEstacion + " in its estacionList field has a non-nullable linea field.");
//            }
//            List<CircuitoVia> circuitoViaListOrphanCheck = linea.getCircuitoViaList();
//            for (CircuitoVia circuitoViaListOrphanCheckCircuitoVia : circuitoViaListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Linea (" + linea + ") cannot be destroyed since the CircuitoVia " + circuitoViaListOrphanCheckCircuitoVia + " in its circuitoViaList field has a non-nullable linea field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(linea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Linea> findLineaEntities() {
        return findLineaEntities(true, -1, -1);
    }

    public List<Linea> findLineaEntities(int maxResults, int firstResult) {
        return findLineaEntities(false, maxResults, firstResult);
    }

    private List<Linea> findLineaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Linea.class));
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

    public Linea findLinea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Linea.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Linea> rt = cq.from(Linea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Linea bucarLinea(String nombreLinea) {
        EntityManager em=getEntityManager();
        try {
            
            TypedQuery<Linea> cq=em.createNamedQuery("Linea.findByNombreLinea",Linea.class);
            cq.setParameter("nombreLinea", nombreLinea);
             return cq.getSingleResult();
           } finally {
            em.close();
        }
        
        
        
    }
     
      public List<Integer> listarId() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("Linea.listarId", Integer.class);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    
}
