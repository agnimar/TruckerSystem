package fxControllers;

import Personel.CheckPoint;
import hibernate.CheckPointHib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class UpdateCheckPoint {
    public Button updateButton;
    public TextField countryField;
    public TextField addressField;
    private CheckPointHib checkPointHib;
    private CheckPoint selectedCheckPoint;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, CheckPoint selectedCheckPoint) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedCheckPoint = selectedCheckPoint;
        this.checkPointHib = new CheckPointHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        CheckPoint checkPoint = (CheckPoint) checkPointHib.getCheckPointById(selectedCheckPoint.getId());
        countryField.setText(checkPoint.getCountry());
        addressField.setText(checkPoint.getAddress());
    }

    public void updateCheckPoint() throws IOException {
        CheckPoint checkPoint = (CheckPoint) checkPointHib.getCheckPointById(selectedCheckPoint.getId());
        checkPoint.setCountry(countryField.getText());
        checkPoint.setAddress(addressField.getText());
        checkPointHib.updateCheckPoint(checkPoint);
    }
}
