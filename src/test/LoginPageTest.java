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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


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
    void testPerformanceUnderDifferentLoadConditions() {
        // Configure mock behavior for valid credentials
        when(mockManagerHib.getManagerByLoginData("admin", "admin")).thenReturn(new Manager());
        when(mockTruckerHib.getTruckerByLoginData("trc", "trc")).thenReturn(new Trucker());

        String[][] credentials = {{"admin", "admin"}, {"trc", "trc"}};
        boolean[] managerCheck = {true, false};

        for (int load = 100; load <= 1000; load += 300) {
            long totalDuration = 0;
            for (int i = 0; i < credentials.length; i++) {
                loginPage.emailField.setText(credentials[i][0]);
                loginPage.passwordField.setText(credentials[i][1]);
                loginPage.managerCheck.setSelected(managerCheck[i]);

                long startTime = System.currentTimeMillis();
                try {
                    for (int j = 0; j < load; j++) {
                        boolean loginSuccess = loginPage.login(); // Login multiple times to simulate load
                        System.out.println("Login attempt with " + credentials[i][0] + ": " + (loginSuccess ? "Success" : "Failure"));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                long endTime = System.currentTimeMillis();

                totalDuration += endTime - startTime;
            }

            long averageDuration = totalDuration / credentials.length;
            assertTrue(averageDuration < 2000, "Average login duration under load " + load + " is too long: " + averageDuration + "ms");
            System.out.println("Average duration for load " + load + ": " + averageDuration + "ms");
        }
    }

    @Test
    void testResourceUsageDuringLogin() {
        // Test resource usage for login
        loginPage.emailField.setText("admin");
        loginPage.passwordField.setText("admin");
        loginPage.managerCheck.setSelected(true);

        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // Trigger garbage collection for more accurate memory usage data
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        try {
            loginPage.login();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        assertTrue(memoryUsed < 50000000, "Memory usage for login is too high: " + memoryUsed + " bytes"); // Adjust threshold as needed
        System.out.println("Memory used for login: " + memoryUsed + " bytes");
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 2500, 500}) // Updated load levels
    void testPerformanceUnderLoad(int load) {
        // Determine user type based on load
        boolean isManager = (load == 500);
        String[] credentials = isManager ? new String[]{"admin", "admin"} : new String[]{"trc", "trc"};

        // Configure mock behavior
        if (isManager) {
            when(mockManagerHib.getManagerByLoginData("admin", "admin")).thenReturn(new Manager());
        } else {
            when(mockTruckerHib.getTruckerByLoginData("trc", "trc")).thenReturn(new Trucker());
        }

        // Set credentials and manager check
        loginPage.emailField.setText(credentials[0]);
        loginPage.passwordField.setText(credentials[1]);
        loginPage.managerCheck.setSelected(isManager);

        // Performance test
        long startTime = System.currentTimeMillis();
        try {
            for (int j = 0; j < load; j++) {
                loginPage.login();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        // Assertions and logging for performance
        long duration = endTime - startTime;
        long expectedMaxDuration = isManager ? 2500 : 1500; // Adjust thresholds as needed
        assertTrue(duration < expectedMaxDuration, "Login duration under load " + load + " is too long: " + duration + "ms");

        // Memory usage test
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        try {
            loginPage.login();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = memoryAfter - memoryBefore;
        long expectedMaxMemory = isManager ? 100000000 : 75000000; // Adjust as needed
        assertTrue(memoryUsed < expectedMaxMemory, "Memory usage for login is too high: " + memoryUsed + " bytes");

        System.out.println("Load: " + load + ", Duration: " + duration + "ms, Memory Used: " + memoryUsed + " bytes");
    }
}
