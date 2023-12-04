package fxControllers;

import Personel.Destination;
import Personel.Manager;
import Personel.Status;
import Personel.Trucker;
import hibernate.DestinationHib;
import hibernate.ManagerHib;
import hibernate.TruckerHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateDestination implements Initializable {
    public TextField distanceField;
    public TextField startPointField;
    public TextField endPointField;
    public DatePicker departureField;
    public DatePicker arrivalField;
    public ChoiceBox statusField;
    public ComboBox driverCB;
    public ComboBox managerCB;
    @FXML
    private EntityManagerFactory entityManagerFactory;
    @FXML
    private DestinationHib destinationHib;
    private TruckerHib userHib;
    private ManagerHib managerHib;
    public List<Trucker> truckerList;
    public List<Manager> managerList;
    private Destination destination;
    private boolean edit;

    public void setData(EntityManagerFactory entityManagerFactory, Destination destination, boolean edit) {
        this.entityManagerFactory = entityManagerFactory;
        this.destination = destination;
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.edit = edit;

        truckerList = userHib.getAllTrucker();
        driverCB.getItems().setAll(truckerList);

        managerList = managerHib.getAllManager();
        managerCB.getItems().setAll(managerList);

    }

    private void fillFields() {
        destination = (Destination) destinationHib.getDestinationById(destination.getId());
    }

    public void submit() {
        if(edit){
            fillFields();
            destinationHib.updateDestination(destination);
        }else {
            Destination destination = null;
            destination = new Destination(Double.valueOf(distanceField.getText()), startPointField.getText(), endPointField.getText(), departureField.getValue(), arrivalField.getValue(), Status.valueOf(statusField.getSelectionModel().getSelectedItem().toString()));
            destinationHib.updateDestination(destination);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        statusField.getItems().add(Status.AVAILABLE.toString());
        statusField.getItems().add(Status.INPROGRESS.toString());
        statusField.getItems().add(Status.FINISHED.toString());
    }
}
