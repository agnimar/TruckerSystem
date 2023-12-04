package fxControllers;

import Personel.Manager;
import Personel.Trucker;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPage implements Initializable {
    @FXML
    public Button signUpButton;
    @FXML
    public TextField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField lastnameField;
    @FXML
    public TextField phoneField;
    @FXML
    public DatePicker birthField;
    @FXML
    public TextField emailField;
    @FXML
    public CheckBox adminField;
    @FXML
    public CheckBox medicalCheck;
    @FXML
    public CheckBox managerCheck;
    @FXML
    public TextField distanceField;
    @FXML
    public CheckBox licenceCheck;
    private ManagerHib managerHib;
    private TruckerHib truckerHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.managerHib = new ManagerHib(entityManagerFactory);
        this.truckerHib = new TruckerHib(entityManagerFactory);
    }
    public void disableFields() {
        if(managerCheck.isSelected() == false){
            medicalCheck.setDisable(false);
            licenceCheck.setDisable(false);
            distanceField.setDisable(false);
            adminField.setDisable(true);
        } else {
            medicalCheck.setDisable(true);
            licenceCheck.setDisable(true);
            distanceField.setDisable(true);
            adminField.setDisable(false);
        }
    }
    public void SignUp() throws IOException {
        if(!managerCheck.isSelected()){
            Trucker trucker = null;
            trucker = new Trucker(passwordField.getText(), nameField.getText(), lastnameField.getText(), birthField.getValue(), phoneField.getText(), emailField.getText(), medicalCheck.isSelected(), licenceCheck.isSelected(), Integer.valueOf(distanceField.getText()));
            truckerHib.createTrucker(trucker);
        } else {
            Manager manager = null;
            manager = new Manager(passwordField.getText(), nameField.getText(), lastnameField.getText(), birthField.getValue(), phoneField.getText(), emailField.getText(), adminField.isSelected());
            managerHib.createManager(manager);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../fxml/login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) passwordField.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableFields();
    }
}
