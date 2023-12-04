package hibernate;


import Personel.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class TruckHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public TruckHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(truck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateTruck(Truck truck) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(truck);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteTruck(Truck truck){
        entityManager.getTransaction().begin();
        entityManager.remove(truck);
        entityManager.getTransaction().commit();
    }

    public Truck getTruckById(int id) {
        entityManager = emf.createEntityManager();
        Truck truck = null;
        try {
            entityManager.getTransaction().begin();
            truck = entityManager.find(Truck.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such truck by given Id");
        }
        return truck;
    }

    public List<Truck> getAllTruck() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Truck.class));
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
