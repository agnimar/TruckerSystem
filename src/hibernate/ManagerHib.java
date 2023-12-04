package hibernate;

import Personel.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ManagerHib {
    public EntityManager entityManager = null;
    public EntityManagerFactory emf = null;

    public ManagerHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createManager(Manager manager) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(manager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateManager(Manager manager) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(manager);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteManager(Manager manager){
        entityManager.getTransaction().begin();
        entityManager.remove(manager);
        entityManager.getTransaction().commit();
    }

    public List<Manager> getAllManager() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Manager.class));
            Query q = entityManager.createQuery(query);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return new ArrayList<>();
    }

    public Manager getManagerById(int id) {
        entityManager = emf.createEntityManager();
        Manager manager = null;
        try {
            entityManager.getTransaction().begin();
            manager = entityManager.find(Manager.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return manager;
    }

    public Manager getManagerByLoginData(String email, String psw) {
        entityManager = emf.createEntityManager();
        Query query = null;
        CriteriaQuery<Manager> queryManager = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        queryManager = cb.createQuery(Manager.class);
        Root<Manager> root = queryManager.from(Manager.class);
        queryManager.select(root).where(cb.and(cb.like(root.get("email"), email), cb.like(root.get("password"), psw)));

        try {
            if (queryManager != null) query = entityManager.createQuery(queryManager);
            return (Manager) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
