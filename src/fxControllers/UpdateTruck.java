package fxControllers;

import Personel.Truck;
import hibernate.TruckHib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class UpdateTruck {
    public Button updateButton;
    public TextField numPlateField;
    public TextField makeField;
    public TextField modelField;
    public TextField yearField;
    public TextField locationField;
    public CheckBox transitField;

    private TruckHib truckHib;
    private Truck selectedTruck;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, Truck selectedTruck) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedTruck = selectedTruck;
        this.truckHib = new TruckHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Truck truck = (Truck) truckHib.getTruckById(selectedTruck.getId());
        numPlateField.setText(truck.getNumPlate());
        makeField.setText(truck.getMake());
        modelField.setText(truck.getModel());
        yearField.setText(String.valueOf(truck.getYear()));
        locationField.setText(truck.getLocation());
        transitField.setSelected(truck.isInTransit());
    }

    public void updateTruck() throws IOException {
        Truck truck = (Truck) truckHib.getTruckById(selectedTruck.getId());
        truck.setNumPlate(numPlateField.getText());
        truck.setMake(makeField.getText());
        truck.setModel(modelField.getText());
        truck.setYear(Integer.parseInt(yearField.getText()));
        truck.setLocation(locationField.getText());
        truck.setInTransit(transitField.isSelected());
        truckHib.updateTruck(truck);
    }
}
