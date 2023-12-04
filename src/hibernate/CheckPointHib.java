package hibernate;

import Personel.CheckPoint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CheckPointHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CheckPointHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createCheckPoint(CheckPoint checkPoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(checkPoint);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateCheckPoint(CheckPoint checkPoint) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(checkPoint);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteCheckPoint(CheckPoint checkPoint){
        entityManager.getTransaction().begin();
        entityManager.remove(checkPoint);
        entityManager.getTransaction().commit();
    }

    public CheckPoint getCheckPointById(int id) {
        entityManager = emf.createEntityManager();
        CheckPoint checkPoint = null;
        try {
            entityManager.getTransaction().begin();
            checkPoint = entityManager.find(CheckPoint.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such checkpoint by given Id");
        }
        return checkPoint;
    }

    public List<CheckPoint> getAllCheckPoint() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(CheckPoint.class));
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
}
