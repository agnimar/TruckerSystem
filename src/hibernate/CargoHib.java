package hibernate;

import Personel.Cargo;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CargoHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CargoHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void updateCargo(Cargo cargo) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cargo);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public void deleteCargo(Cargo cargo){
        entityManager.getTransaction().begin();
        entityManager.remove(cargo);
        entityManager.getTransaction().commit();
    }

    public Cargo getCargoById(int id) {
        entityManager = emf.createEntityManager();
        Cargo cargo = null;
        try {
            entityManager.getTransaction().begin();
            cargo = entityManager.find(Cargo.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such cargo by given Id");
        }
        return cargo;
    }

    public List<Cargo> getAllCargo() {
        entityManager = emf.createEntityManager();
        try {
            CriteriaQuery<Object> query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Cargo.class));
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
