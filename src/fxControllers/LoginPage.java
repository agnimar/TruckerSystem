package fxControllers;

import Personel.Manager;
import Personel.Trucker;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginPage {
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    public CheckBox managerCheck;
    private EntityManagerFactory entityManagerFactory;
    public ManagerHib managerHib;
    public TruckerHib truckerHib;
    private boolean isTestEnvironment = false;

    public LoginPage() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("TruckSystem");
        this.managerHib = new ManagerHib(entityManagerFactory);
        this.truckerHib = new TruckerHib(entityManagerFactory);
    }

    // For testing purposes
    public LoginPage(ManagerHib managerHib, TruckerHib truckerHib, boolean isTestEnvironment) {
        this.managerHib = managerHib;
        this.truckerHib = truckerHib;
        this.isTestEnvironment = isTestEnvironment;
    }

    public boolean login() throws IOException {
        if (managerCheck.isSelected()) {
            Manager manager = managerHib.getManagerByLoginData(emailField.getText(), passwordField.getText());
            if (manager != null) {
                if (!isTestEnvironment) {
                    navigate("../fxml/front-page.fxml");
                }
                return true;
            } else {
                if (!isTestEnvironment) {
                    FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User login report", "No such user or wrong credentials");
                }
                return false;
            }
        } else {
            Trucker trucker = truckerHib.getTruckerByLoginData(emailField.getText(), passwordField.getText());
            if (trucker != null) {
                if (!isTestEnvironment) {
                    navigate("../fxml/front-page.fxml");
                }
                return true;
            } else {
                if (!isTestEnvironment) {
                    FxUtils.generateAlert(Alert.AlertType.INFORMATION, "User login report", "No such user or wrong credentials");
                }
                return false;
            }
        }
    }

    private void navigate(String fxmlFile) throws IOException {
        if (passwordField.getScene() != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource(fxmlFile));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = (Stage) passwordField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    public void signUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../fxml/sign-up-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setTitle("Register");
        stage.setScene(scene);
        stage.show();
        SignUpPage signUpPage = fxmlLoader.getController();
        signUpPage.setData(entityManagerFactory);
    }

}
