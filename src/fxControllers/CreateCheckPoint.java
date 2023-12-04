package fxControllers;

import Personel.CheckPoint;
import Personel.Trucker;
import hibernate.CheckPointHib;
import hibernate.TruckerHib;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CreateCheckPoint {
    public Button createButton;
    public TextField countryField;
    public TextField addressField;
    private CheckPointHib checkPointHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.checkPointHib = new CheckPointHib(entityManagerFactory);
    }

    public void createCheckPoint() throws IOException {
        CheckPoint checkPoint = null;
        checkPoint = new CheckPoint(countryField.getText(), addressField.getText());
        checkPointHib.createCheckPoint(checkPoint);
    }
}
