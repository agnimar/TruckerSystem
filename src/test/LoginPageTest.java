package test;// /path/to/LoginPageTest.java
import Personel.Manager;
import Personel.Trucker;
import fxControllers.LoginPage;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.embed.swing.JFXPanel;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;


class LoginPageTest {
    private LoginPage loginPage;

    @Mock
    private ManagerHib mockManagerHib;

    @Mock
    private TruckerHib mockTruckerHib;

    @BeforeEach
    void setUp() {
        new JFXPanel();
        MockitoAnnotations.openMocks(this);
        loginPage = new LoginPage(mockManagerHib, mockTruckerHib, true); // Pass true for isTestEnvironment
        loginPage.emailField = new TextField();
        loginPage.passwordField = new PasswordField();
        loginPage.managerCheck = new CheckBox();
    }

    @Test
    void testPerformanceWithDifferentCredentials() {
        // Configure mock behavior for valid credentials
        when(mockManagerHib.getManagerByLoginData("admin", "admin")).thenReturn(new Manager());
        when(mockTruckerHib.getTruckerByLoginData("trc", "trc")).thenReturn(new Trucker());

        // Test with both valid and invalid credentials
        String[][] credentials = {{"admin", "admin"}, {"trc", "trc"}, {"invalidUser@example.com", "wrongPassword"}};
        boolean[] managerCheck = {true, false, false};

        for (int i = 0; i < credentials.length; i++) {
            loginPage.emailField.setText(credentials[i][0]);
            loginPage.passwordField.setText(credentials[i][1]);
            loginPage.managerCheck.setSelected(managerCheck[i]);

            long startTime = System.currentTimeMillis();
            try {
                boolean loginSuccess = loginPage.login();
                System.out.println("Login attempt with " + credentials[i][0] + ": " + (loginSuccess ? "Success" : "Failure"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();

            long duration = endTime - startTime;
            assertTrue(duration < 1000, "Login with credentials " + credentials[i][0] + " took too long");
            System.out.println("Duration for " + credentials[i][0] + ": " + duration + "ms");
        }
    }

}
