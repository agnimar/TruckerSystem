package hibernate;

import Personel.Manager;
import Personel.Truck;
import Personel.Trucker;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TruckerHib {
    public EntityManager entityManager = null;
    public EntityManagerFactory emf = null;

    public TruckerHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createTrucker(Trucker trucker) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(trucker);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateTrucker(Trucker trucker) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(trucker);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteTrucker(Trucker trucker){
        entityManager.getTransaction().begin();
        entityManager.remove(trucker);
        entityManager.getTransaction().commit();
    }

    public Trucker getTruckerById(int id) {
        entityManager = emf.createEntityManager();
        Trucker trucker = null;
        try {
            entityManager.getTransaction().begin();
            trucker = entityManager.find(Trucker.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such user by given Id");
        }
        return trucker;
    }

    public List<Trucker> getAllTrucker() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Trucker.class));
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

    public Trucker getTruckerByLoginData(String email, String psw) {
        entityManager = emf.createEntityManager();
        Query query = null;
        CriteriaQuery<Trucker> queryManager = null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        queryManager = cb.createQuery(Trucker.class);
        Root<Trucker> root = queryManager.from(Trucker.class);
        queryManager.select(root).where(cb.and(cb.like(root.get("email"), email), cb.like(root.get("password"), psw)));

        try {
            if (queryManager != null) query = entityManager.createQuery(queryManager);
            return (Trucker) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
