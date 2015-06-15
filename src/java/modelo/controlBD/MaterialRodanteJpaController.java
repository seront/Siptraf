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
import modelo.entity.CurvaEsfuerzo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import modelo.controlBD.exceptions.IllegalOrphanException;
import modelo.controlBD.exceptions.NonexistentEntityException;
import modelo.controlBD.exceptions.PreexistingEntityException;
import modelo.entity.MaterialRodante;

/**
 *
 * @author Kelvins Insua
 */
public class MaterialRodanteJpaController implements Serializable {

    public MaterialRodanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MaterialRodante materialRodante) throws PreexistingEntityException, Exception {
        if (materialRodante.getCurvaEsfuerzoList() == null) {
            materialRodante.setCurvaEsfuerzoList(new ArrayList<CurvaEsfuerzo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CurvaEsfuerzo> attachedCurvaEsfuerzoList = new ArrayList<CurvaEsfuerzo>();
            for (CurvaEsfuerzo curvaEsfuerzoListCurvaEsfuerzoToAttach : materialRodante.getCurvaEsfuerzoList()) {
                curvaEsfuerzoListCurvaEsfuerzoToAttach = em.getReference(curvaEsfuerzoListCurvaEsfuerzoToAttach.getClass(), curvaEsfuerzoListCurvaEsfuerzoToAttach.getCurvaEsfuerzoPK());
                attachedCurvaEsfuerzoList.add(curvaEsfuerzoListCurvaEsfuerzoToAttach);
            }
            materialRodante.setCurvaEsfuerzoList(attachedCurvaEsfuerzoList);
            em.persist(materialRodante);
            for (CurvaEsfuerzo curvaEsfuerzoListCurvaEsfuerzo : materialRodante.getCurvaEsfuerzoList()) {
                MaterialRodante oldMaterialRodanteOfCurvaEsfuerzoListCurvaEsfuerzo = curvaEsfuerzoListCurvaEsfuerzo.getMaterialRodante();
                curvaEsfuerzoListCurvaEsfuerzo.setMaterialRodante(materialRodante);
                curvaEsfuerzoListCurvaEsfuerzo = em.merge(curvaEsfuerzoListCurvaEsfuerzo);
                if (oldMaterialRodanteOfCurvaEsfuerzoListCurvaEsfuerzo != null) {
                    oldMaterialRodanteOfCurvaEsfuerzoListCurvaEsfuerzo.getCurvaEsfuerzoList().remove(curvaEsfuerzoListCurvaEsfuerzo);
                    oldMaterialRodanteOfCurvaEsfuerzoListCurvaEsfuerzo = em.merge(oldMaterialRodanteOfCurvaEsfuerzoListCurvaEsfuerzo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMaterialRodante(materialRodante.getIdMaterialRodante()) != null) {
                throw new PreexistingEntityException("MaterialRodante " + materialRodante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MaterialRodante materialRodante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
//            MaterialRodante persistentMaterialRodante = em.find(MaterialRodante.class, materialRodante.getIdMaterialRodante());
//            List<CurvaEsfuerzo> curvaEsfuerzoListOld = persistentMaterialRodante.getCurvaEsfuerzoList();
//            List<CurvaEsfuerzo> curvaEsfuerzoListNew = materialRodante.getCurvaEsfuerzoList();
//            List<String> illegalOrphanMessages = null;
//            for (CurvaEsfuerzo curvaEsfuerzoListOldCurvaEsfuerzo : curvaEsfuerzoListOld) {
//                if (!curvaEsfuerzoListNew.contains(curvaEsfuerzoListOldCurvaEsfuerzo)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain CurvaEsfuerzo " + curvaEsfuerzoListOldCurvaEsfuerzo + " since its materialRodante field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<CurvaEsfuerzo> attachedCurvaEsfuerzoListNew = new ArrayList<CurvaEsfuerzo>();
//            for (CurvaEsfuerzo curvaEsfuerzoListNewCurvaEsfuerzoToAttach : curvaEsfuerzoListNew) {
//                curvaEsfuerzoListNewCurvaEsfuerzoToAttach = em.getReference(curvaEsfuerzoListNewCurvaEsfuerzoToAttach.getClass(), curvaEsfuerzoListNewCurvaEsfuerzoToAttach.getCurvaEsfuerzoPK());
//                attachedCurvaEsfuerzoListNew.add(curvaEsfuerzoListNewCurvaEsfuerzoToAttach);
//            }
//            curvaEsfuerzoListNew = attachedCurvaEsfuerzoListNew;
//            materialRodante.setCurvaEsfuerzoList(curvaEsfuerzoListNew);
            materialRodante = em.merge(materialRodante);
//            for (CurvaEsfuerzo curvaEsfuerzoListNewCurvaEsfuerzo : curvaEsfuerzoListNew) {
//                if (!curvaEsfuerzoListOld.contains(curvaEsfuerzoListNewCurvaEsfuerzo)) {
//                    MaterialRodante oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo = curvaEsfuerzoListNewCurvaEsfuerzo.getMaterialRodante();
//                    curvaEsfuerzoListNewCurvaEsfuerzo.setMaterialRodante(materialRodante);
//                    curvaEsfuerzoListNewCurvaEsfuerzo = em.merge(curvaEsfuerzoListNewCurvaEsfuerzo);
//                    if (oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo != null && !oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo.equals(materialRodante)) {
//                        oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo.getCurvaEsfuerzoList().remove(curvaEsfuerzoListNewCurvaEsfuerzo);
//                        oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo = em.merge(oldMaterialRodanteOfCurvaEsfuerzoListNewCurvaEsfuerzo);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = materialRodante.getIdMaterialRodante();
                if (findMaterialRodante(id) == null) {
                    throw new NonexistentEntityException("The materialRodante with id " + id + " no longer exists.");
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
            MaterialRodante materialRodante;
            try {
                materialRodante = em.getReference(MaterialRodante.class, id);
                materialRodante.getIdMaterialRodante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The materialRodante with id " + id + " no longer exists.", enfe);
            }
//            List<String> illegalOrphanMessages = null;
//            List<CurvaEsfuerzo> curvaEsfuerzoListOrphanCheck = materialRodante.getCurvaEsfuerzoList();
//            for (CurvaEsfuerzo curvaEsfuerzoListOrphanCheckCurvaEsfuerzo : curvaEsfuerzoListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This MaterialRodante (" + materialRodante + ") cannot be destroyed since the CurvaEsfuerzo " + curvaEsfuerzoListOrphanCheckCurvaEsfuerzo + " in its curvaEsfuerzoList field has a non-nullable materialRodante field.");
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
            em.remove(materialRodante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MaterialRodante> findMaterialRodanteEntities() {
        return findMaterialRodanteEntities(true, -1, -1);
    }

    public List<MaterialRodante> findMaterialRodanteEntities(int maxResults, int firstResult) {
        return findMaterialRodanteEntities(false, maxResults, firstResult);
    }

    private List<MaterialRodante> findMaterialRodanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MaterialRodante.class));
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

    public MaterialRodante findMaterialRodante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MaterialRodante.class, id);
        } finally {
            em.close();
        }
    }

    public int getMaterialRodanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MaterialRodante> rt = cq.from(MaterialRodante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
     public List<Integer> listarIdMR() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Integer> cq = em.createNamedQuery("MaterialRodante.listarIdMR", Integer.class);
            return cq.getResultList();
        } finally {
            em.close();
        }
    }
}
