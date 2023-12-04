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

public class UpdateTrucker {
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
    private Trucker selectedTrucker;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, Trucker selectedTrucker) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTrucker = selectedTrucker;
        this.truckerHib = new TruckerHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Trucker trucker = (Trucker) truckerHib.getTruckerById(selectedTrucker.getId());
        emailField.setText(trucker.getEmail());
        passwordField.setText(trucker.getPassword());
        nameField.setText(trucker.getName());
        lastnameField.setText(trucker.getLastname());
        birthField.setValue(trucker.getDateOfBirth());
        phoneField.setText(trucker.getPhoneNum());
        medicalField.setSelected(trucker.isHasValidMedicalCard());
        driverField.setSelected(trucker.isHasValidDriversLicence());
        distanceField.setText(String.valueOf(trucker.getFullDrivenDistance()));
    }

    public void updateTrucker() throws IOException {
        Trucker trucker = (Trucker) truckerHib.getTruckerById(selectedTrucker.getId());
        trucker.setPassword(passwordField.getText());
        trucker.setName(nameField.getText());
        trucker.setLastname(lastnameField.getText());
        trucker.setDateOfBirth(birthField.getValue());
        trucker.setPhoneNum(phoneField.getText());
        trucker.setEmail(emailField.getText());
        trucker.setHasValidMedicalCard(medicalField.isSelected());
        trucker.setHasValidDriversLicence(driverField.isSelected());
        trucker.setFullDrivenDistance(Integer.parseInt(distanceField.getText()));
        truckerHib.updateTrucker(trucker);
    }
}
