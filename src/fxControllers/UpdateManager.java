package fxControllers;

import Personel.Manager;
import hibernate.ManagerHib;
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

public class UpdateManager implements Initializable {
    public Button createButton;
    public TextField passwordField;
    public TextField nameField;
    public TextField lastnameField;
    public TextField phoneField;
    public DatePicker birthField;
    public TextField emailField;
    public CheckBox adminField;
    private ManagerHib managerHib;
    private Manager selectedManager;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, Manager selectedManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedManager = selectedManager;
        this.managerHib = new ManagerHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Manager manager = (Manager) managerHib.getManagerById(selectedManager.getId());
        emailField.setText(manager.getEmail());
        passwordField.setText(manager.getPassword());
        nameField.setText(manager.getName());
        lastnameField.setText(manager.getLastname());
        birthField.setValue(manager.getDateOfBirth());
        phoneField.setText(manager.getPhoneNum());
        adminField.setSelected(manager.isAdmin());
    }

    public void updateManager() throws IOException {
        Manager manager = (Manager) managerHib.getManagerById(selectedManager.getId());
        manager.setPassword(passwordField.getText());
        manager.setName(nameField.getText());
        manager.setLastname(lastnameField.getText());
        manager.setDateOfBirth(birthField.getValue());
        manager.setPhoneNum(phoneField.getText());
        manager.setEmail(emailField.getText());
        manager.setAdmin(adminField.isSelected());
        managerHib.updateManager(manager);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
