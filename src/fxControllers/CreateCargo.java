package fxControllers;

import Personel.Cargo;
import hibernate.CargoHib;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class CreateCargo {
    public Button createButton;
    public TextField companyField;
    public TextField productField;
    public TextField weightField;
    public CheckBox expirationCheck;
    private CargoHib cargoHib;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.cargoHib = new CargoHib(entityManagerFactory);
    }

    public void createCargo() throws IOException {
        Cargo cargo = null;
        final int MAX_WEIGHT = 18500;

        int weight = Integer.parseInt(weightField.getText());

        if (weight > MAX_WEIGHT) {
            throw new IllegalArgumentException("Weight limit exceeded");
        }

        cargo = new Cargo(companyField.getText(), productField.getText(), weight, expirationCheck.isSelected());
        cargoHib.createCargo(cargo);
    }
}
