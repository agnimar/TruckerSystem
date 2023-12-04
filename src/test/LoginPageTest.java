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

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 2500}) // Load levels
    void testDurationUnderLoad(int load) {
        // Test for both manager and trucker for each load
        performLoadTest(load, true); // Test with manager
        performLoadTest(load, false); // Test with trucker
    }

    @ParameterizedTest
    @ValueSource(ints = {100, 500, 2500}) // Load levels
    void testMemoryUsageUnderLoad(int load) {
        // Test for both manager and trucker for each load
        performMemoryTest(load, true); // Test with manager
        performMemoryTest(load, false); // Test with trucker
    }

    private void performLoadTest(int load, boolean isManager) {
        // Setup for test
        setupTestEnvironment(isManager);

        // Duration test
        long startTime = System.currentTimeMillis();
        try {
            for (int j = 0; j < load; j++) {
                loginPage.login();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        // Assertions and logging for duration
        long duration = endTime - startTime;
        long expectedMaxDuration = isManager ? 2500 : 1500;
        assertTrue(duration < expectedMaxDuration, "Login duration under load " + load + " for " + (isManager ? "manager" : "trucker") + " is too long: " + duration + "ms");

        System.out.println("Load: " + load + " for " + (isManager ? "manager" : "trucker") + ", Duration: " + duration + "ms");
    }

    private void performMemoryTest(int load, boolean isManager) {
        // Setup for test
        setupTestEnvironment(isManager);

        // Memory usage test
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        try {
            for (int j = 0; j < load; j++) {
                loginPage.login();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsedBytes = memoryAfter - memoryBefore;
        double memoryUsedMB = memoryUsedBytes / 1048576.0; // Convert bytes to MB
        double expectedMaxMemoryMB = (isManager ? 100000000 : 75000000) / 1048576.0; // Convert expected max memory to MB
        assertTrue(memoryUsedMB < expectedMaxMemoryMB, "Memory usage under load " + load + " for " + (isManager ? "manager" : "trucker") + " is too high: " + memoryUsedMB + " MB");

        System.out.println("Load: " + load + " for " + (isManager ? "manager" : "trucker") + ", Memory Used: " + String.format("%.2f MB", memoryUsedMB));
    }

    private void setupTestEnvironment(boolean isManager) {
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
    }
}
