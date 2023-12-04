package fxControllers;

import Personel.Cargo;
import hibernate.CargoHib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class UpdateCargo {
    public Button updateButton;
    public TextField companyField;
    public TextField productField;
    public TextField weightField;
    public CheckBox expirationCheck;
    private CargoHib cargoHib;
    private Cargo selectedCargo;
    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, Cargo selectedCargo) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedCargo = selectedCargo;
        this.cargoHib = new CargoHib(entityManagerFactory);
        fillFields();
    }

    private void fillFields() {
        Cargo cargo = (Cargo) cargoHib.getCargoById(selectedCargo.getId());
        companyField.setText(cargo.getContractedCompany());
        productField.setText(cargo.getProduct());
        weightField.setText(String.valueOf(cargo.getWeight()));
        expirationCheck.setSelected(cargo.isHasExpiration());
    }

    public void updateCargo() throws IOException {
        Cargo cargo = (Cargo) cargoHib.getCargoById(selectedCargo.getId());
        cargo.setContractedCompany(companyField.getText());
        cargo.setProduct(productField.getText());
        cargo.setWeight(Integer.parseInt(weightField.getText()));
        cargo.setHasExpiration(expirationCheck.isSelected());
        cargoHib.updateCargo(cargo);
    }
}
