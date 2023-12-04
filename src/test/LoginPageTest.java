package test;// /path/to/LoginPageTest.java
import Personel.Manager;
import Personel.Trucker;
import fxControllers.LoginPage;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.embed.swing.JFXPanel;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


class LoginPageTest {
    // Initialize JavaFX Toolkit
    private LoginPage loginPage;

    @Mock
    private ManagerHib mockManagerHib;

    @Mock
    private TruckerHib mockTruckerHib;

    @BeforeEach
    void setUp() {
        new JFXPanel();

        MockitoAnnotations.openMocks(this);
        loginPage = new LoginPage();
        loginPage.managerHib = mockManagerHib;
        loginPage.truckerHib = mockTruckerHib;
        loginPage.emailField = new TextField();
        loginPage.passwordField = new PasswordField();
        loginPage.managerCheck = new CheckBox();
    }

    @Test
    void testPerformanceWithDifferentCredentials() {
        when(mockManagerHib.getManagerByLoginData(anyString(), anyString())).thenReturn(new Manager());
        when(mockTruckerHib.getTruckerByLoginData(anyString(), anyString())).thenReturn(new Trucker());

        String[][] credentials = {{"test", "test"}, {"trucker@example.com", "truckerPassword"}};
        boolean[] managerCheck = {true, false};

        for (int i = 0; i < credentials.length; i++) {
            loginPage.emailField.setText(credentials[i][0]);
            loginPage.passwordField.setText(credentials[i][1]);
            loginPage.managerCheck.setSelected(managerCheck[i]);

            long startTime = System.currentTimeMillis();
            try {
                loginPage.login();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            assertTrue(duration < 1000, "Login with credentials " + credentials[i][0] + " took too long");
        }
    }
}
