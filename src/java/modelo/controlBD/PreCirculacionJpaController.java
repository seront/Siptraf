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
import modelo.entity.Ruta;
import modelo.entity.EstacionTramoParada;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.PreCirculacion;
import modelo.entity.PreCirculacionPK;

/**
 *
 * @author Kelvins Insua
 */
public class PreCirculacionJpaController implements Serializable {

    public PreCirculacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PreCirculacion preCirculacion) throws PreexistingEntityException, Exception {
        if (preCirculacion.getPreCirculacionPK() == null) {
            preCirculacion.setPreCirculacionPK(new PreCirculacionPK());
        }
        if (preCirculacion.getEstacionTramoParadaList() == null) {
            preCirculacion.setEstacionTramoParadaList(new ArrayList<EstacionTramoParada>());
        }
        preCirculacion.getPreCirculacionPK().setIdRuta(preCirculacion.getRuta().getRutaPK().getIdRuta());
        preCirculacion.getPreCirculacionPK().setIdProgramacionHoraria(preCirculacion.getRuta().getRutaPK().getIdProgramacionHoraria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ruta ruta = preCirculacion.getRuta();
            if (ruta != null) {
                ruta = em.getReference(ruta.getClass(), ruta.getRutaPK());
                preCirculacion.setRuta(ruta);
            }
            List<EstacionTramoParada> attachedEstacionTramoParadaList = new ArrayList<EstacionTramoParada>();
            for (EstacionTramoParada estacionTramoParadaListEstacionTramoParadaToAttach : preCirculacion.getEstacionTramoParadaList()) {
                estacionTramoParadaListEstacionTramoParadaToAttach = em.getReference(estacionTramoParadaListEstacionTramoParadaToAttach.getClass(), estacionTramoParadaListEstacionTramoParadaToAttach.getEstacionTramoParadaPK());
                attachedEstacionTramoParadaList.add(estacionTramoParadaListEstacionTramoParadaToAttach);
            }
            preCirculacion.setEstacionTramoParadaList(attachedEstacionTramoParadaList);
            em.persist(preCirculacion);
            if (ruta != null) {
                ruta.getPreCirculacionList().add(preCirculacion);
                ruta = em.merge(ruta);
            }
            for (EstacionTramoParada estacionTramoParadaListEstacionTramoParada : preCirculacion.getEstacionTramoParadaList()) {
                PreCirculacion oldPreCirculacionOfEstacionTramoParadaListEstacionTramoParada = estacionTramoParadaListEstacionTramoParada.getPreCirculacion();
                estacionTramoParadaListEstacionTramoParada.setPreCirculacion(preCirculacion);
                estacionTramoParadaListEstacionTramoParada = em.merge(estacionTramoParadaListEstacionTramoParada);
                if (oldPreCirculacionOfEstacionTramoParadaListEstacionTramoParada != null) {
                    oldPreCirculacionOfEstacionTramoParadaListEstacionTramoParada.getEstacionTramoParadaList().remove(estacionTramoParadaListEstacionTramoParada);
                    oldPreCirculacionOfEstacionTramoParadaListEstacionTramoParada = em.merge(oldPreCirculacionOfEstacionTramoParadaListEstacionTramoParada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPreCirculacion(preCirculacion.getPreCirculacionPK()) != null) {
                throw new PreexistingEntityException("PreCirculacion " + preCirculacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PreCirculacion preCirculacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        preCirculacion.getPreCirculacionPK().setIdRuta(preCirculacion.getRuta().getRutaPK().getIdRuta());
        preCirculacion.getPreCirculacionPK().setIdProgramacionHoraria(preCirculacion.getRuta().getRutaPK().getIdProgramacionHoraria());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            PreCirculacion persistentPreCirculacion = em.find(PreCirculacion.class, preCirculacion.getPreCirculacionPK());
//            Ruta rutaOld = persistentPreCirculacion.getRuta();
//            Ruta rutaNew = preCirculacion.getRuta();
//            List<EstacionTramoParada> estacionTramoParadaListOld = persistentPreCirculacion.getEstacionTramoParadaList();
//            List<EstacionTramoParada> estacionTramoParadaListNew = preCirculacion.getEstacionTramoParadaList();
//            List<String> illegalOrphanMessages = null;
//            for (EstacionTramoParada estacionTramoParadaListOldEstacionTramoParada : estacionTramoParadaListOld) {
//                if (!estacionTramoParadaListNew.contains(estacionTramoParadaListOldEstacionTramoParada)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain EstacionTramoParada " + estacionTramoParadaListOldEstacionTramoParada + " since its preCirculacion field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            if (rutaNew != null) {
//                rutaNew = em.getReference(rutaNew.getClass(), rutaNew.getRutaPK());
//                preCirculacion.setRuta(rutaNew);
//            }
//            List<EstacionTramoParada> attachedEstacionTramoParadaListNew = new ArrayList<EstacionTramoParada>();
//            for (EstacionTramoParada estacionTramoParadaListNewEstacionTramoParadaToAttach : estacionTramoParadaListNew) {
//                estacionTramoParadaListNewEstacionTramoParadaToAttach = em.getReference(estacionTramoParadaListNewEstacionTramoParadaToAttach.getClass(), estacionTramoParadaListNewEstacionTramoParadaToAttach.getEstacionTramoParadaPK());
//                attachedEstacionTramoParadaListNew.add(estacionTramoParadaListNewEstacionTramoParadaToAttach);
//            }
//            estacionTramoParadaListNew = attachedEstacionTramoParadaListNew;
//            preCirculacion.setEstacionTramoParadaList(estacionTramoParadaListNew);
            preCirculacion = em.merge(preCirculacion);
//            if (rutaOld != null && !rutaOld.equals(rutaNew)) {
//                rutaOld.getPreCirculacionList().remove(preCirculacion);
//                rutaOld = em.merge(rutaOld);
//            }
//            if (rutaNew != null && !rutaNew.equals(rutaOld)) {
//                rutaNew.getPreCirculacionList().add(preCirculacion);
//                rutaNew = em.merge(rutaNew);
//            }
//            for (EstacionTramoParada estacionTramoParadaListNewEstacionTramoParada : estacionTramoParadaListNew) {
//                if (!estacionTramoParadaListOld.contains(estacionTramoParadaListNewEstacionTramoParada)) {
//                    PreCirculacion oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada = estacionTramoParadaListNewEstacionTramoParada.getPreCirculacion();
//                    estacionTramoParadaListNewEstacionTramoParada.setPreCirculacion(preCirculacion);
//                    estacionTramoParadaListNewEstacionTramoParada = em.merge(estacionTramoParadaListNewEstacionTramoParada);
//                    if (oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada != null && !oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada.equals(preCirculacion)) {
//                        oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada.getEstacionTramoParadaList().remove(estacionTramoParadaListNewEstacionTramoParada);
//                        oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada = em.merge(oldPreCirculacionOfEstacionTramoParadaListNewEstacionTramoParada);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PreCirculacionPK id = preCirculacion.getPreCirculacionPK();
                if (findPreCirculacion(id) == null) {
                    throw new NonexistentEntityException("The preCirculacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PreCirculacionPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PreCirculacion preCirculacion;
            try {
                preCirculacion = em.getReference(PreCirculacion.class, id);
                preCirculacion.getPreCirculacionPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The preCirculacion with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<EstacionTramoParada> estacionTramoParadaListOrphanCheck = preCirculacion.getEstacionTramoParadaList();
//            for (EstacionTramoParada estacionTramoParadaListOrphanCheckEstacionTramoParada : estacionTramoParadaListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This PreCirculacion (" + preCirculacion + ") cannot be destroyed since the EstacionTramoParada " + estacionTramoParadaListOrphanCheckEstacionTramoParada + " in its estacionTramoParadaList field has a non-nullable preCirculacion field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            Ruta ruta = preCirculacion.getRuta();
//            if (ruta != null) {
//                ruta.getPreCirculacionList().remove(preCirculacion);
//                ruta = em.merge(ruta);
//            }
            em.remove(preCirculacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PreCirculacion> findPreCirculacionEntities() {
        return findPreCirculacionEntities(true, -1, -1);
    }

    public List<PreCirculacion> findPreCirculacionEntities(int maxResults, int firstResult) {
        return findPreCirculacionEntities(false, maxResults, firstResult);
    }

    private List<PreCirculacion> findPreCirculacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PreCirculacion.class));
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

    public PreCirculacion findPreCirculacion(PreCirculacionPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PreCirculacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getPreCirculacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PreCirculacion> rt = cq.from(PreCirculacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
       public List<Integer> listarIdPreCirclacion(int idPH) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("PreCirculacion.listarId", Integer.class);
            cq.setParameter("idProgramacionHoraria", idPH);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }    
    public int buscarIdPreCirclacionMax(int idPH) {
        EntityManager em = getEntityManager();
        int salida=0;
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("PreCirculacion.buscarIdMax", Integer.class);            
            cq.setParameter("idProgramacionHoraria", idPH);
            salida=cq.getSingleResult();
            return salida;
        }catch(NullPointerException e){
            salida=0;
            return salida;
        } finally {
            em.close();
        }
    }    
    public List<PreCirculacion> buscarPorPrefijo(int idPrefijo) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PreCirculacion> cq = em.createNamedQuery("PreCirculacion.findByPrefijoNumeracion", PreCirculacion.class);            
            cq.setParameter("prefijoNumeracion", idPrefijo);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }    
    public List<PreCirculacion> listarPreCirculaciones(int idProgramacionHoraria) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PreCirculacion> cq = em.createNamedQuery("PreCirculacion.findByIdProgramacionHoraria", PreCirculacion.class);            
            cq.setParameter("idProgramacionHoraria", idProgramacionHoraria);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
    public PreCirculacion buscarPorPH(int idProgramacionHoraria,int idPrecirculacion) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PreCirculacion> cq = em.createNamedQuery("PreCirculacion.buscarIdPH", PreCirculacion.class);            
            cq.setParameter("idProgramacionHoraria", idProgramacionHoraria);
            cq.setParameter("idPrecirculacion", idPrecirculacion);
            return cq.getSingleResult();
        } finally {
            em.close();
        }
    }
    public List<PreCirculacion> buscarPorRuta(int idRuta,int idProgramacion) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<PreCirculacion> cq = em.createNamedQuery("PreCirculacion.buscarPorRuta", PreCirculacion.class);
            cq.setParameter("idRuta", idRuta);
            cq.setParameter("idProgramacionHoraria", idProgramacion);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
}
