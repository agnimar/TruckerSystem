package tests;

import Personel.Manager;
import Personel.Trucker;
import fxControllers.CreateDestination;
import hibernate.DestinationHib;
import hibernate.TruckerHib;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class LimitTest {

    @BeforeClass
    public static void initToolkit() {
        // Initialize JavaFX toolkit to be able to test JavaFX components
        new JFXPanel();
    }

    @Test
    public void testSubmitDistanceLimit() {
        // Create mock objects for EntityManagerFactory, DestinationHib, and TruckerHib
        EntityManagerFactory entityManagerFactory = mock(EntityManagerFactory.class);
        DestinationHib destinationHib = mock(DestinationHib.class);
        TruckerHib truckerHib = mock(TruckerHib.class);

        // Create a mock user object
        Manager user = mock(Manager.class);

        // Create a mock trucker list
        List<Trucker> truckerList = new ArrayList<>();
        truckerList.add(mock(Trucker.class));

        // Create a mock ChoiceBox and ComboBox
        ChoiceBox statusField = mock(ChoiceBox.class);
        ComboBox driverCB = mock(ComboBox.class);

        // Create a new CreateDestination object and set its properties
        CreateDestination createDestination = new CreateDestination();
        createDestination.entityManagerFactory = entityManagerFactory;
        createDestination.destinationHib = destinationHib;
        createDestination.userHib = truckerHib;
        createDestination.user = user;
        createDestination.truckerList = truckerList;
        createDestination.statusField = statusField;
        createDestination.driverCB = driverCB;
        createDestination.distanceField = mock(TextField.class);
        createDestination.startPointField = mock(TextField.class);
        createDestination.endPointField = mock(TextField.class);
        createDestination.departureField = mock(DatePicker.class);
        createDestination.arrivalField = mock(DatePicker.class);

        // Set the distance field to a value greater than 5000
        when(createDestination.distanceField.getText()).thenReturn("6000");

        // Call the submit() method and verify that an error message is shown to the user using FxUtils.generateAlert()
        createDestination.submit();
        //verifyStatic(FxUtils.class);
        FxUtils.generateAlert(eq(Alert.AlertType.ERROR), eq("Distance Limit Exceeded"), eq("The distance cannot exceed 5000."));
    }

}