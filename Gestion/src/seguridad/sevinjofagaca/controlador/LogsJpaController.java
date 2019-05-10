/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package seguridad.sevinjofagaca.controlador;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import seguridad.sevinjofagaca.controlador.exceptions.NonexistentEntityException;


import seguridad.sevinjofagaca.controlador.exceptions.PreexistingEntityException;

import seguridad.sevinjofagaca.modelo.Logs;

/**
 *

 *
 * @author sevin

 */
public class LogsJpaController implements Serializable {

    public LogsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    

    public void create(Logs logs) throws PreexistingEntityException, Exception {

        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(logs);
            em.getTransaction().commit();


        } catch (Exception ex) {
            if (findLogs(logs.getId()) != null) {
                throw new PreexistingEntityException("Logs " + logs + " already exists.", ex);
            }
            throw ex;

        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logs logs) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            logs = em.merge(logs);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logs.getId();
                if (findLogs(id) == null) {
                    throw new NonexistentEntityException("The logs with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logs logs;
            try {
                logs = em.getReference(Logs.class, id);
                logs.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logs with id " + id + " no longer exists.", enfe);
            }
            em.remove(logs);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logs> findLogsEntities() {
        return findLogsEntities(true, -1, -1);
    }

    public List<Logs> findLogsEntities(int maxResults, int firstResult) {
        return findLogsEntities(false, maxResults, firstResult);
    }

    private List<Logs> findLogsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logs.class));
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

    public Logs findLogs(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logs.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logs> rt = cq.from(Logs.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
