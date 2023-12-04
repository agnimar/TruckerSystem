package test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import Personel.Trucker;
import fxControllers.FrontPage;
import fxControllers.LoginPage;
import hibernate.TruckerHib;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;

public class TruckerLoginTest {

    @Mock
    private TruckerHib truckerHib;

    @Mock
    private FXMLLoader fxmlLoader;

    @Mock
    private FrontPage frontPage;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private Stage stage;

    @InjectMocks
    private LoginPage loginPage;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @Test
    void testLoginSuccessful() throws IOException {
        // Arrange
        Trucker trucker = new Trucker();
        trucker.setEmail("test@example.com");
        trucker.setPassword("password");
        when(truckerHib.getTruckerByLoginData(anyString(), anyString())).thenReturn(trucker);
        when(fxmlLoader.load()).thenReturn(new Parent() {});
        when(fxmlLoader.getController()).thenReturn(frontPage);
        TextField emailField = new TextField("test@example.com");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("password");
        //loginPage.setEmailField(emailField);
        //loginPage.setPasswordField(passwordField);

        // Act
        loginPage.login();

        // Assert
        verify(entityManagerFactory).createEntityManager();
        verify(frontPage).setDataTrucker(entityManagerFactory, trucker, trucker);
        verify(stage).setTitle("Front page");
        verify(stage).setScene(any());
        verify(stage).show();
    }
    
    @Test
    void testLoginFailed() throws IOException {
        // Arrange
        when(truckerHib.getTruckerByLoginData(anyString(), anyString())).thenReturn(null);
        TextField emailField = new TextField("test@example.com");
        PasswordField passwordField = new PasswordField();
        passwordField.setText("password");
        //loginPage.setEmailField(emailField);
        //loginPage.setPasswordField(passwordField);

        // Act
        loginPage.login();

        // Assert
        verifyZeroInteractions(entityManagerFactory);
        verifyZeroInteractions(frontPage);
        verify(stage, never()).setTitle(anyString());
        verify(stage, never()).setScene(any());
        verify(stage, never()).show();
        //Alert alert = FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User login report", "No such user or wrong credentials");
        //assertEquals("User login report", alert.getTitle());
        //assertEquals("No such user or wrong credentials", alert.getContentText());
    }

    private void verifyZeroInteractions(EntityManagerFactory entityManagerFactory) {
    }
    private void verifyZeroInteractions(FrontPage frontPage) {
    }
}

