package fxControllers;

import Personel.Truck;
import Personel.Trucker;
import hibernate.TruckHib;
import hibernate.TruckerHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CreateTruck {
    public Button createButton;
    public TextField numPlateField;
    public TextField makeField;
    public TextField modelField;
    public TextField yearField;
    public TextField locationField;
    public CheckBox transitField;
    private TruckHib truckHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.truckHib = new TruckHib(entityManagerFactory);
    }

    public void createField() throws IOException {
        Truck truck = null;
        truck = new Truck(numPlateField.getText(), makeField.getText(), modelField.getText(), Integer.parseInt(yearField.getText()), transitField.isSelected(), locationField.getText());
        truckHib.createTruck(truck);
    }
}
