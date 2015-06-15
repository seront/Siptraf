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
import modelo.entity.RestriccionMarchaTipo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.TiempoEstacionMarchaTipo;
import modelo.entity.GraficoMarchaTipo;
import modelo.entity.MarchaTipo;

/**
 *
 * @author Kelvins Insua
 */
public class MarchaTipoJpaController implements Serializable {

    public MarchaTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MarchaTipo marchaTipo) throws PreexistingEntityException, Exception {
        if (marchaTipo.getRestriccionMarchaTipoList() == null) {
            marchaTipo.setRestriccionMarchaTipoList(new ArrayList<RestriccionMarchaTipo>());
        }
        if (marchaTipo.getTiempoEstacionMarchaTipoList() == null) {
            marchaTipo.setTiempoEstacionMarchaTipoList(new ArrayList<TiempoEstacionMarchaTipo>());
        }
        if (marchaTipo.getGraficoMarchaTipoList() == null) {
            marchaTipo.setGraficoMarchaTipoList(new ArrayList<GraficoMarchaTipo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RestriccionMarchaTipo> attachedRestriccionMarchaTipoList = new ArrayList<RestriccionMarchaTipo>();
            for (RestriccionMarchaTipo restriccionMarchaTipoListRestriccionMarchaTipoToAttach : marchaTipo.getRestriccionMarchaTipoList()) {
                restriccionMarchaTipoListRestriccionMarchaTipoToAttach = em.getReference(restriccionMarchaTipoListRestriccionMarchaTipoToAttach.getClass(), restriccionMarchaTipoListRestriccionMarchaTipoToAttach.getRestriccionMarchaTipoPK());
                attachedRestriccionMarchaTipoList.add(restriccionMarchaTipoListRestriccionMarchaTipoToAttach);
            }
            marchaTipo.setRestriccionMarchaTipoList(attachedRestriccionMarchaTipoList);
            List<TiempoEstacionMarchaTipo> attachedTiempoEstacionMarchaTipoList = new ArrayList<TiempoEstacionMarchaTipo>();
            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipoToAttach : marchaTipo.getTiempoEstacionMarchaTipoList()) {
                tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipoToAttach = em.getReference(tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipoToAttach.getClass(), tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipoToAttach.getTiempoEstacionMarchaTipoPK());
                attachedTiempoEstacionMarchaTipoList.add(tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipoToAttach);
            }
            marchaTipo.setTiempoEstacionMarchaTipoList(attachedTiempoEstacionMarchaTipoList);
            List<GraficoMarchaTipo> attachedGraficoMarchaTipoList = new ArrayList<GraficoMarchaTipo>();
            for (GraficoMarchaTipo graficoMarchaTipoListGraficoMarchaTipoToAttach : marchaTipo.getGraficoMarchaTipoList()) {
                graficoMarchaTipoListGraficoMarchaTipoToAttach = em.getReference(graficoMarchaTipoListGraficoMarchaTipoToAttach.getClass(), graficoMarchaTipoListGraficoMarchaTipoToAttach.getGraficoMarchaTipoPK());
                attachedGraficoMarchaTipoList.add(graficoMarchaTipoListGraficoMarchaTipoToAttach);
            }
            marchaTipo.setGraficoMarchaTipoList(attachedGraficoMarchaTipoList);
            em.persist(marchaTipo);
            for (RestriccionMarchaTipo restriccionMarchaTipoListRestriccionMarchaTipo : marchaTipo.getRestriccionMarchaTipoList()) {
                MarchaTipo oldMarchaTipoOfRestriccionMarchaTipoListRestriccionMarchaTipo = restriccionMarchaTipoListRestriccionMarchaTipo.getMarchaTipo();
                restriccionMarchaTipoListRestriccionMarchaTipo.setMarchaTipo(marchaTipo);
                restriccionMarchaTipoListRestriccionMarchaTipo = em.merge(restriccionMarchaTipoListRestriccionMarchaTipo);
                if (oldMarchaTipoOfRestriccionMarchaTipoListRestriccionMarchaTipo != null) {
                    oldMarchaTipoOfRestriccionMarchaTipoListRestriccionMarchaTipo.getRestriccionMarchaTipoList().remove(restriccionMarchaTipoListRestriccionMarchaTipo);
                    oldMarchaTipoOfRestriccionMarchaTipoListRestriccionMarchaTipo = em.merge(oldMarchaTipoOfRestriccionMarchaTipoListRestriccionMarchaTipo);
                }
            }
            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo : marchaTipo.getTiempoEstacionMarchaTipoList()) {
                MarchaTipo oldMarchaTipoOfTiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo = tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo.getMarchaTipo();
                tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo.setMarchaTipo(marchaTipo);
                tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo = em.merge(tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo);
                if (oldMarchaTipoOfTiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo != null) {
                    oldMarchaTipoOfTiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoList().remove(tiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo);
                    oldMarchaTipoOfTiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo = em.merge(oldMarchaTipoOfTiempoEstacionMarchaTipoListTiempoEstacionMarchaTipo);
                }
            }
            for (GraficoMarchaTipo graficoMarchaTipoListGraficoMarchaTipo : marchaTipo.getGraficoMarchaTipoList()) {
                MarchaTipo oldMarchaTipoOfGraficoMarchaTipoListGraficoMarchaTipo = graficoMarchaTipoListGraficoMarchaTipo.getMarchaTipo();
                graficoMarchaTipoListGraficoMarchaTipo.setMarchaTipo(marchaTipo);
                graficoMarchaTipoListGraficoMarchaTipo = em.merge(graficoMarchaTipoListGraficoMarchaTipo);
                if (oldMarchaTipoOfGraficoMarchaTipoListGraficoMarchaTipo != null) {
                    oldMarchaTipoOfGraficoMarchaTipoListGraficoMarchaTipo.getGraficoMarchaTipoList().remove(graficoMarchaTipoListGraficoMarchaTipo);
                    oldMarchaTipoOfGraficoMarchaTipoListGraficoMarchaTipo = em.merge(oldMarchaTipoOfGraficoMarchaTipoListGraficoMarchaTipo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMarchaTipo(marchaTipo.getIdMarchaTipo()) != null) {
                throw new PreexistingEntityException("MarchaTipo " + marchaTipo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MarchaTipo marchaTipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            MarchaTipo persistentMarchaTipo = em.find(MarchaTipo.class, marchaTipo.getIdMarchaTipo());
//            List<RestriccionMarchaTipo> restriccionMarchaTipoListOld = persistentMarchaTipo.getRestriccionMarchaTipoList();
//            List<RestriccionMarchaTipo> restriccionMarchaTipoListNew = marchaTipo.getRestriccionMarchaTipoList();
//            List<TiempoEstacionMarchaTipo> tiempoEstacionMarchaTipoListOld = persistentMarchaTipo.getTiempoEstacionMarchaTipoList();
//            List<TiempoEstacionMarchaTipo> tiempoEstacionMarchaTipoListNew = marchaTipo.getTiempoEstacionMarchaTipoList();
//            List<GraficoMarchaTipo> graficoMarchaTipoListOld = persistentMarchaTipo.getGraficoMarchaTipoList();
//            List<GraficoMarchaTipo> graficoMarchaTipoListNew = marchaTipo.getGraficoMarchaTipoList();
//            List<String> illegalOrphanMessages = null;
//            for (RestriccionMarchaTipo restriccionMarchaTipoListOldRestriccionMarchaTipo : restriccionMarchaTipoListOld) {
//                if (!restriccionMarchaTipoListNew.contains(restriccionMarchaTipoListOldRestriccionMarchaTipo)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain RestriccionMarchaTipo " + restriccionMarchaTipoListOldRestriccionMarchaTipo + " since its marchaTipo field is not nullable.");
//                }
//            }
//            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListOldTiempoEstacionMarchaTipo : tiempoEstacionMarchaTipoListOld) {
//                if (!tiempoEstacionMarchaTipoListNew.contains(tiempoEstacionMarchaTipoListOldTiempoEstacionMarchaTipo)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain TiempoEstacionMarchaTipo " + tiempoEstacionMarchaTipoListOldTiempoEstacionMarchaTipo + " since its marchaTipo field is not nullable.");
//                }
//            }
//            for (GraficoMarchaTipo graficoMarchaTipoListOldGraficoMarchaTipo : graficoMarchaTipoListOld) {
//                if (!graficoMarchaTipoListNew.contains(graficoMarchaTipoListOldGraficoMarchaTipo)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain GraficoMarchaTipo " + graficoMarchaTipoListOldGraficoMarchaTipo + " since its marchaTipo field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<RestriccionMarchaTipo> attachedRestriccionMarchaTipoListNew = new ArrayList<RestriccionMarchaTipo>();
//            for (RestriccionMarchaTipo restriccionMarchaTipoListNewRestriccionMarchaTipoToAttach : restriccionMarchaTipoListNew) {
//                restriccionMarchaTipoListNewRestriccionMarchaTipoToAttach = em.getReference(restriccionMarchaTipoListNewRestriccionMarchaTipoToAttach.getClass(), restriccionMarchaTipoListNewRestriccionMarchaTipoToAttach.getRestriccionMarchaTipoPK());
//                attachedRestriccionMarchaTipoListNew.add(restriccionMarchaTipoListNewRestriccionMarchaTipoToAttach);
//            }
//            restriccionMarchaTipoListNew = attachedRestriccionMarchaTipoListNew;
//            marchaTipo.setRestriccionMarchaTipoList(restriccionMarchaTipoListNew);
//            List<TiempoEstacionMarchaTipo> attachedTiempoEstacionMarchaTipoListNew = new ArrayList<TiempoEstacionMarchaTipo>();
//            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipoToAttach : tiempoEstacionMarchaTipoListNew) {
//                tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipoToAttach = em.getReference(tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipoToAttach.getClass(), tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipoToAttach.getTiempoEstacionMarchaTipoPK());
//                attachedTiempoEstacionMarchaTipoListNew.add(tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipoToAttach);
//            }
//            tiempoEstacionMarchaTipoListNew = attachedTiempoEstacionMarchaTipoListNew;
//            marchaTipo.setTiempoEstacionMarchaTipoList(tiempoEstacionMarchaTipoListNew);
//            List<GraficoMarchaTipo> attachedGraficoMarchaTipoListNew = new ArrayList<GraficoMarchaTipo>();
//            for (GraficoMarchaTipo graficoMarchaTipoListNewGraficoMarchaTipoToAttach : graficoMarchaTipoListNew) {
//                graficoMarchaTipoListNewGraficoMarchaTipoToAttach = em.getReference(graficoMarchaTipoListNewGraficoMarchaTipoToAttach.getClass(), graficoMarchaTipoListNewGraficoMarchaTipoToAttach.getGraficoMarchaTipoPK());
//                attachedGraficoMarchaTipoListNew.add(graficoMarchaTipoListNewGraficoMarchaTipoToAttach);
//            }
//            graficoMarchaTipoListNew = attachedGraficoMarchaTipoListNew;
//            marchaTipo.setGraficoMarchaTipoList(graficoMarchaTipoListNew);
            marchaTipo = em.merge(marchaTipo);
//            for (RestriccionMarchaTipo restriccionMarchaTipoListNewRestriccionMarchaTipo : restriccionMarchaTipoListNew) {
//                if (!restriccionMarchaTipoListOld.contains(restriccionMarchaTipoListNewRestriccionMarchaTipo)) {
//                    MarchaTipo oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo = restriccionMarchaTipoListNewRestriccionMarchaTipo.getMarchaTipo();
//                    restriccionMarchaTipoListNewRestriccionMarchaTipo.setMarchaTipo(marchaTipo);
//                    restriccionMarchaTipoListNewRestriccionMarchaTipo = em.merge(restriccionMarchaTipoListNewRestriccionMarchaTipo);
//                    if (oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo != null && !oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo.equals(marchaTipo)) {
//                        oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo.getRestriccionMarchaTipoList().remove(restriccionMarchaTipoListNewRestriccionMarchaTipo);
//                        oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo = em.merge(oldMarchaTipoOfRestriccionMarchaTipoListNewRestriccionMarchaTipo);
//                    }
//                }
//            }
//            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo : tiempoEstacionMarchaTipoListNew) {
//                if (!tiempoEstacionMarchaTipoListOld.contains(tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo)) {
//                    MarchaTipo oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo = tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo.getMarchaTipo();
//                    tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo.setMarchaTipo(marchaTipo);
//                    tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo = em.merge(tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo);
//                    if (oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo != null && !oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo.equals(marchaTipo)) {
//                        oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo.getTiempoEstacionMarchaTipoList().remove(tiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo);
//                        oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo = em.merge(oldMarchaTipoOfTiempoEstacionMarchaTipoListNewTiempoEstacionMarchaTipo);
//                    }
//                }
//            }
//            for (GraficoMarchaTipo graficoMarchaTipoListNewGraficoMarchaTipo : graficoMarchaTipoListNew) {
//                if (!graficoMarchaTipoListOld.contains(graficoMarchaTipoListNewGraficoMarchaTipo)) {
//                    MarchaTipo oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo = graficoMarchaTipoListNewGraficoMarchaTipo.getMarchaTipo();
//                    graficoMarchaTipoListNewGraficoMarchaTipo.setMarchaTipo(marchaTipo);
//                    graficoMarchaTipoListNewGraficoMarchaTipo = em.merge(graficoMarchaTipoListNewGraficoMarchaTipo);
//                    if (oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo != null && !oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo.equals(marchaTipo)) {
//                        oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo.getGraficoMarchaTipoList().remove(graficoMarchaTipoListNewGraficoMarchaTipo);
//                        oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo = em.merge(oldMarchaTipoOfGraficoMarchaTipoListNewGraficoMarchaTipo);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = marchaTipo.getIdMarchaTipo();
                if (findMarchaTipo(id) == null) {
                    throw new NonexistentEntityException("The marchaTipo with id " + id + " no longer exists.");
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
            MarchaTipo marchaTipo;
            try {
                marchaTipo = em.getReference(MarchaTipo.class, id);
                marchaTipo.getIdMarchaTipo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The marchaTipo with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<RestriccionMarchaTipo> restriccionMarchaTipoListOrphanCheck = marchaTipo.getRestriccionMarchaTipoList();
//            for (RestriccionMarchaTipo restriccionMarchaTipoListOrphanCheckRestriccionMarchaTipo : restriccionMarchaTipoListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This MarchaTipo (" + marchaTipo + ") cannot be destroyed since the RestriccionMarchaTipo " + restriccionMarchaTipoListOrphanCheckRestriccionMarchaTipo + " in its restriccionMarchaTipoList field has a non-nullable marchaTipo field.");
//            }
//            List<TiempoEstacionMarchaTipo> tiempoEstacionMarchaTipoListOrphanCheck = marchaTipo.getTiempoEstacionMarchaTipoList();
//            for (TiempoEstacionMarchaTipo tiempoEstacionMarchaTipoListOrphanCheckTiempoEstacionMarchaTipo : tiempoEstacionMarchaTipoListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This MarchaTipo (" + marchaTipo + ") cannot be destroyed since the TiempoEstacionMarchaTipo " + tiempoEstacionMarchaTipoListOrphanCheckTiempoEstacionMarchaTipo + " in its tiempoEstacionMarchaTipoList field has a non-nullable marchaTipo field.");
//            }
//            List<GraficoMarchaTipo> graficoMarchaTipoListOrphanCheck = marchaTipo.getGraficoMarchaTipoList();
//            for (GraficoMarchaTipo graficoMarchaTipoListOrphanCheckGraficoMarchaTipo : graficoMarchaTipoListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This MarchaTipo (" + marchaTipo + ") cannot be destroyed since the GraficoMarchaTipo " + graficoMarchaTipoListOrphanCheckGraficoMarchaTipo + " in its graficoMarchaTipoList field has a non-nullable marchaTipo field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(marchaTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MarchaTipo> findMarchaTipoEntities() {
        return findMarchaTipoEntities(true, -1, -1);
    }

    public List<MarchaTipo> findMarchaTipoEntities(int maxResults, int firstResult) {
        return findMarchaTipoEntities(false, maxResults, firstResult);
    }

    private List<MarchaTipo> findMarchaTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MarchaTipo.class));
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

    public MarchaTipo findMarchaTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MarchaTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarchaTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MarchaTipo> rt = cq.from(MarchaTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public MarchaTipo bucarMarchaTipo(int idMarchaTipo) {
        EntityManager em=getEntityManager();
        try {
            
            TypedQuery<MarchaTipo> cq=em.createNamedQuery("MarchaTipo.findByIdMarchaTipo",MarchaTipo.class);
            cq.setParameter("idMarchaTipo", idMarchaTipo);
             return cq.getSingleResult();
           } finally {
            em.close();
        }
        
        
        
    }
     
       public List<Integer> listarIdMarchas() {
       EntityManager em=getEntityManager();
        try {            
//            TypedQuery<MarchaTipo> cq=em.createNamedQuery("MarchaTipo.listarIdMarchas",MarchaTipo.class);
            TypedQuery<Integer> cq=em.createNamedQuery("MarchaTipo.listarIdMarchas", Integer.class);
//            cq.setParameter("idLinea", idLinea);
//             return cq.getResultList();
//            List<Integer> ides=new ArrayList<>();
             return cq.getResultList();
           } finally {
            em.close();
        }
    }
    
       public List<modelo.entity.MarchaTipo> listarMarchaTipoAsc(int idLinea) {
       EntityManager em= getEntityManager();
        try {
            
            TypedQuery<modelo.entity.MarchaTipo> cq=em.createNamedQuery("MarchaTipo.listarMTAsc",modelo.entity.MarchaTipo.class);
            cq.setParameter("idLinea", idLinea);
             return cq.getResultList();
           } finally {
            em.close();
        }
    }
    public List<modelo.entity.MarchaTipo> listarMarchaTipoDesc(int idLinea) {
       EntityManager em=getEntityManager();
        try {            
            TypedQuery<modelo.entity.MarchaTipo> cq=em.createNamedQuery("MarchaTipo.listarMTDesc",modelo.entity.MarchaTipo.class);
            cq.setParameter("idLinea", idLinea);
             return cq.getResultList();
           } finally {
            em.close();
        }
    }
}
