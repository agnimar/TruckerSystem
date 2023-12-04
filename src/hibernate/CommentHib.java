package hibernate;

import Personel.Comment;
import Personel.Forum;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CommentHib {
    EntityManager entityManager = null;
    EntityManagerFactory emf = null;

    public CommentHib(EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    public void createComment(Comment comment) {
        entityManager = emf.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(comment);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    public Comment getCommentById(int id) {
        entityManager = emf.createEntityManager();
        Comment comment = null;
        try {
            entityManager.getTransaction().begin();
            comment = entityManager.find(Comment.class, id);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("No such comment by given Id");
        }
        return comment;
    }
}
