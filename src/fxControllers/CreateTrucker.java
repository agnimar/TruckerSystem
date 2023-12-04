package fxControllers;

import Personel.Manager;
import Personel.Trucker;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CreateTrucker {
    public Button createButton;
    public TextField passwordField;
    public TextField nameField;
    public TextField lastnameField;
    public TextField phoneField;
    public DatePicker birthField;
    public TextField emailField;
    public CheckBox medicalField;
    public CheckBox driverField;
    public TextField distanceField;
    private TruckerHib truckerHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.truckerHib = new TruckerHib(entityManagerFactory);
    }

    public void createTrucker() throws IOException {
        Trucker trucker = null;
        trucker = new Trucker(passwordField.getText(), nameField.getText(), lastnameField.getText(), birthField.getValue(), phoneField.getText(), emailField.getText(), medicalField.isSelected(), driverField.isSelected(), Integer.parseInt(distanceField.getText()));
        truckerHib.createTrucker(trucker);
    }
}
