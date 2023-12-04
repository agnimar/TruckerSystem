package test;

import Personel.Comment;
import Personel.Forum;
import fxControllers.CreateReply;
import hibernate.CommentHib;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateReplyTest {
    @Mock private EntityManagerFactory entityManagerFactory;
    @Mock private CommentHib commentHib;
    @Mock private Comment selectedComment;

    @Mock private Forum exampleForum;
    private CreateReply controllerUnderTest;

    @Before
    public void setUp() {
        // initialize JavaFX toolkit
        new JFXPanel();
        // initialize mocks
        MockitoAnnotations.initMocks(this);
        // create a new controller instance
        controllerUnderTest = new CreateReply();
        controllerUnderTest.titleField = new TextField();
        controllerUnderTest.descriptionField = new TextArea();
        // set up the controller
        exampleForum = new Forum("title", "description");
        // create a real parent comment
        Comment parentComment = new Comment("parent title", "parent content", exampleForum);
        // create a real selected comment and set its parent comment id
        selectedComment = new Comment("title", "content", exampleForum);
        selectedComment.setParentComment(parentComment); // set the parent comment here
        controllerUnderTest.setData(entityManagerFactory, selectedComment);
        controllerUnderTest.commentHib = commentHib;
    }

    @Test
    public void testCreateReply() {
        // Setup
        controllerUnderTest.titleField.setText("reply title");
        controllerUnderTest.descriptionField.setText("reply description");
        ArgumentCaptor<Comment> createdCommentCaptor = ArgumentCaptor.forClass(Comment.class);

        // Exercise
        try {
            controllerUnderTest.createReply();
            // Verify
            verify(commentHib).createComment(createdCommentCaptor.capture());
            Comment createdComment = createdCommentCaptor.getValue();
            assertNotNull(createdComment);
            assertEquals("reply title", createdComment.getTitle());
            assertEquals("reply description", createdComment.getCommentSection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
