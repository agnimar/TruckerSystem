package fxControllers;

import Personel.Cargo;
import Personel.Forum;
import hibernate.ForumHib;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;

public class CreateForum {
    public TextField titleField;
    public TextArea descriptionField;
    private ForumHib forumHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.forumHib = new ForumHib(entityManagerFactory);
    }

    public void createForum() {
        Forum forum = null;
        forum = new Forum(titleField.getText(), descriptionField.getText());
        forumHib.createForum(forum);
    }
}
