package fxControllers;

import Personel.Cargo;
import Personel.Forum;
import hibernate.ForumHib;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;

public class UpdateForum {
    public TextField titleField;
    public TextArea descriptionField;
    private ForumHib forumHib;
    private Forum selectedForum;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, Forum selectedForum) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedForum = selectedForum;
        this.forumHib = new ForumHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Forum forum = (Forum) forumHib.getForumById(selectedForum.getId());
        titleField.setText(forum.getTitle());
        descriptionField.setText(forum.getDescription());
    }

    public void updateForum() {
        Forum forum = (Forum) forumHib.getForumById(selectedForum.getId());
        forum.setTitle(titleField.getText());
        forum.setDescription(descriptionField.getText());
        forumHib.updateForum(forum);
    }
}
