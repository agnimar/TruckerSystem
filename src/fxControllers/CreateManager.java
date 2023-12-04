package fxControllers;

import Personel.Manager;
import hibernate.ManagerHib;
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

public class CreateManager {

    public Button createButton;
    public TextField passwordField;
    public TextField nameField;
    public TextField lastnameField;
    public TextField phoneField;
    public DatePicker birthField;
    public TextField emailField;
    public CheckBox adminField;
    private ManagerHib managerHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.managerHib = new ManagerHib(entityManagerFactory);
    }

    public void createManager() throws IOException {
        Manager manager = null;
        manager = new Manager(passwordField.getText(), nameField.getText(), lastnameField.getText(), birthField.getValue(), phoneField.getText(), emailField.getText(), adminField.isSelected());
        managerHib.createManager(manager);
    }
}
