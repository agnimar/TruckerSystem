package fxControllers;

import Personel.*;
import hibernate.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.annotations.Check;
import utils.FxUtils;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FrontPage implements Initializable {
    public TabPane allTabs;
    public Tab managerTab;
    public Tab truckerTab;
    public Tab checkPointTab;
    public Tab cargoTab;
    public Tab truckTab;
    public Tab destinationTab;
    public Button viewManagerField;
    public Button deleteManagerField;
    public Button updateManagerField;
    public Button createManagerField;
    public ListView<Manager> managerList;
    public ListView<Trucker> truckerList;
    public ListView<CheckPoint> checkPointList;
    public ListView<Cargo> cargoList;
    public ListView<Truck> truckList;
    public ListView<Destination> destinationList;
    public User user;
    public Manager manager;
    public Trucker trucker;
    public AnchorPane managerPane;
    public AnchorPane truckerPane;
    public AnchorPane checkPointPane;
    public AnchorPane destinationPane;
    public AnchorPane truckPane;
    public BarChart<String, Integer> barChart;
    public DatePicker departureFilter;
    public DatePicker arrivalFilter;
    public ListView<Forum> forumList;
    public TreeView commentTree;
    public TextField truckerFilter;
    private EntityManagerFactory entityManagerFactory;
    private ManagerHib managerHib;
    private TruckerHib truckerHib;
    private CheckPointHib checkPointHib;
    private CargoHib cargoHib;
    private TruckHib truckHib;
    private DestinationHib destinationHib;
    private ForumHib forumHib;
    public ChoiceBox statusFilter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusFilter.getItems().add(Status.AVAILABLE.toString());
        statusFilter.getItems().add(Status.INPROGRESS.toString());
        statusFilter.getItems().add(Status.FINISHED.toString());
    }
    public void setDataManager(EntityManagerFactory entityManagerFactory, User user, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        this.managerHib = new ManagerHib(entityManagerFactory);
        this.truckerHib = new TruckerHib(entityManagerFactory);
        this.checkPointHib = new CheckPointHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.forumHib = new ForumHib(entityManagerFactory);
        this.user = user;
        this.manager = manager;
        disableData();
        fillList();
    }

    public void setDataTrucker(EntityManagerFactory entityManagerFactory, User user, Trucker trucker) {
        this.entityManagerFactory = entityManagerFactory;
        this.managerHib = new ManagerHib(entityManagerFactory);
        this.truckerHib = new TruckerHib(entityManagerFactory);
        this.checkPointHib = new CheckPointHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.forumHib = new ForumHib(entityManagerFactory);
        this.user = user;
        this.trucker = trucker;
        disableData();
        fillList();
    }

    private void disableData() {
        if (user.getClass() == Trucker.class) {
            allTabs.getTabs().remove(managerTab);
            allTabs.getTabs().remove(truckerTab);
            destinationPane.setDisable(true);
            checkPointPane.setDisable(true);
            truckPane.setDisable(true);
        } else if (user.getClass() == Manager.class && manager.isAdmin() == false) {
            allTabs.getTabs().remove(destinationTab);
            managerPane.setDisable(true);
            truckerPane.setDisable(true);
        }
    }

    private void fillList() {
        managerList.getItems().clear();
        truckerList.getItems().clear();
        checkPointList.getItems().clear();
        cargoList.getItems().clear();
        truckList.getItems().clear();
        destinationList.getItems().clear();
        forumList.getItems().clear();

        List<Manager> allManager = managerHib.getAllManager();
        allManager.forEach(c -> managerList.getItems().add(c));

        List<Trucker> allTrucker = truckerHib.getAllTrucker();
        allTrucker.forEach(c -> truckerList.getItems().add(c));

        List<CheckPoint> allCheckPoint = checkPointHib.getAllCheckPoint();
        allCheckPoint.forEach(c -> checkPointList.getItems().add(c));

        List<Cargo> allCargo = cargoHib.getAllCargo();
        allCargo.forEach(c -> cargoList.getItems().add(c));

        List<Truck> allTruck = truckHib.getAllTruck();
        allTruck.forEach(c -> truckList.getItems().add(c));

        List<Destination> allDestination = destinationHib.getAllDestination();
        allDestination.forEach(c -> destinationList.getItems().add(c));

        List<Forum> allForum = forumHib.getAllForum();
        allForum.forEach(c -> forumList.getItems().add(c));
    }

    public void refresh() {
        fillList();
    }
    //------------------HOME----------------------
    public void signOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You're about to logout!");
        alert.setContentText("Do you really want to logout?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader fxmlLoader = new FXMLLoader(LoginPage.class.getResource("../fxml/login-page.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = (Stage) truckPane.getScene().getWindow();
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void viewYourData() {
        String userInfo;
        userInfo = "ID: " + user.getId() +
                "\nPassword: " + user.getPassword() +
                "\nName: " + user.getName() +
                "\nLast name: " + user.getLastname() +
                "\nEmail: " + user.getEmail();

        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Data about user", userInfo.toString());
        truckerList.getItems().clear();
        fillList();
    }

    public void editYourData() throws IOException {
        if (user.getClass() == Trucker.class) {
            FXMLLoader fxmlLoader = new FXMLLoader(UpdateTrucker.class.getResource("../fxml/update-trucker-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Update");
            stage.setScene(scene);
            stage.show();
            UpdateTrucker updateTrucker = fxmlLoader.getController();
            updateTrucker.setData(entityManagerFactory, trucker);
            truckerList.getItems().clear();
            fillList();
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(UpdateManager.class.getResource("../fxml/update-manager-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("Update");
            stage.setScene(scene);
            stage.show();
            UpdateManager updateManager = fxmlLoader.getController();
            updateManager.setData(entityManagerFactory, manager);
            managerList.getItems().clear();
            fillList();
        }
    }
    //------------------FORUM----------------------
    public void deleteForum() {
        forumHib.deleteForum(forumHib.getForumById(forumList.getSelectionModel().getSelectedItem().getId()));
        forumList.getItems().clear();
        fillList();
    }

    public void updateForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateForum.class.getResource("../fxml/update-forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Update forum");
        stage.setScene(scene);
        stage.show();
        UpdateForum updateForum = fxmlLoader.getController();
        updateForum.setData(entityManagerFactory, forumList.getSelectionModel().getSelectedItem());
        forumList.getItems().clear();
        fillList();
    }

    public void createForum() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateForum.class.getResource("../fxml/create-forum-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Create forum");
        stage.setScene(scene);
        stage.show();
        CreateForum createForum = fxmlLoader.getController();
        createForum.setData(entityManagerFactory);
        forumList.getItems().clear();
        fillList();
    }

    public void loadComments() {
        List<Comment> comments = forumHib.getForumById(forumList.getSelectionModel().getSelectedItem().getId()).getComments();
        commentTree.setRoot(new TreeItem<>(new Comment()));
        commentTree.setShowRoot(false);
        commentTree.getRoot().setExpanded(true);
        comments.forEach(comment -> addTreeItem(comment, commentTree.getRoot()));
    }

    public void createComment() throws IOException{
        Stage stage = new Stage();
        stage.setTitle("Create comment");
        stage.setScene(new Scene(loadCreateCommentPage()));
        stage.show();
        CreateComment createComment = getFxmlLoader().getController();
        setCreateCommentData(createComment);
    }

    public void createReply() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateForum.class.getResource("../fxml/create-reply-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Create reply");
        stage.setScene(scene);
        stage.show();
        CreateReply createReply = fxmlLoader.getController();
        createReply.setData(entityManagerFactory, ((TreeItem<Comment>) commentTree.getSelectionModel().getSelectedItem()).getValue());
    }

    private FXMLLoader getFxmlLoader() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(CreateForum.class.getResource("../fxml/create-comment-page.fxml"));
        return fxmlLoader;
    }

    private Parent loadCreateCommentPage() throws IOException {
        return getFxmlLoader().load();
    }


    private void setCreateCommentData(CreateComment createComment){
        createComment.setData(entityManagerFactory, forumList.getSelectionModel().getSelectedItem());
    }

    private void addTreeItem(Comment comment, TreeItem parent) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parent.getChildren().add(treeItem);
        comment.getReplies().forEach(r -> addTreeItem(r, treeItem));
    }
    //------------------MANAGER----------------------
    public void viewManager() throws SQLException {
        ManagerHib managerHib = new ManagerHib(entityManagerFactory);
        Manager managerDb = managerHib.getManagerById(managerList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Manager data", managerDb.toString());
        managerList.getItems().clear();
        fillList();
    }

    public void deleteManager() {
        managerHib.deleteManager(managerHib.getManagerById(managerList.getSelectionModel().getSelectedItem().getId()));
        managerList.getItems().clear();
        fillList();
    }

    public void updateManager() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateManager.class.getResource("../fxml/update-manager-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateManager updateManager = fxmlLoader.getController();
        updateManager.setData(entityManagerFactory, managerList.getSelectionModel().getSelectedItem());
        managerList.getItems().clear();
        fillList();
    }

    public void createManager() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateManager.class.getResource("../fxml/create-manager-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateManager createManager = fxmlLoader.getController();
        createManager.setData(entityManagerFactory);
        managerList.getItems().clear();
        fillList();
    }
    //------------------TRUCKER----------------------
    public void viewTrucker() {
        TruckerHib truckerHib = new TruckerHib(entityManagerFactory);
        Trucker truckerDb = truckerHib.getTruckerById(truckerList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Trucker data", truckerDb.toString());
        truckerList.getItems().clear();
        fillList();
    }

    public void updateTrucker() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateTrucker.class.getResource("../fxml/update-trucker-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateTrucker updateTrucker = fxmlLoader.getController();
        updateTrucker.setData(entityManagerFactory, truckerList.getSelectionModel().getSelectedItem());
        truckerList.getItems().clear();
        fillList();
    }

    public void createTrucker() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTrucker.class.getResource("../fxml/create-trucker-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateTrucker createTrucker = fxmlLoader.getController();
        createTrucker.setData(entityManagerFactory);
        truckerList.getItems().clear();
        fillList();
    }

    public void deleteTrucker() {
        truckerHib.deleteTrucker(truckerHib.getTruckerById(truckerList.getSelectionModel().getSelectedItem().getId()));
        truckList.getItems().clear();
        fillList();
    }
    //------------------CHECKPOINT----------------------
    public void viewCheckPoint() {
        CheckPointHib checkPointHib = new CheckPointHib(entityManagerFactory);
        CheckPoint checkPointDb = checkPointHib.getCheckPointById(checkPointList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "CheckPoint data", checkPointDb.toString());
        checkPointList.getItems().clear();
        fillList();
    }

    public void deleteCheckPoint() {
        checkPointHib.deleteCheckPoint(checkPointHib.getCheckPointById(checkPointList.getSelectionModel().getSelectedItem().getId()));
        checkPointList.getItems().clear();
        fillList();
    }

    public void updateCheckPoint() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateCheckPoint.class.getResource("../fxml/update-checkpoint-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateCheckPoint updateCheckPoint = fxmlLoader.getController();
        updateCheckPoint.setData(entityManagerFactory, checkPointList.getSelectionModel().getSelectedItem());
        checkPointList.getItems().clear();
        fillList();
    }

    public void createCheckPoint() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateCheckPoint.class.getResource("../fxml/create-checkpoint-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateCheckPoint createCheckPoint = fxmlLoader.getController();
        createCheckPoint.setData(entityManagerFactory);
        checkPointList.getItems().clear();
        fillList();
    }
    //------------------CARGO----------------------
    public void viewCargo() {
        CargoHib cargoHib = new CargoHib(entityManagerFactory);
        Cargo cargoDb = cargoHib.getCargoById(cargoList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Cargo data", cargoDb.toString());
        cargoList.getItems().clear();
        fillList();
    }

    public void deleteCargo() {
        cargoHib.deleteCargo(cargoHib.getCargoById(cargoList.getSelectionModel().getSelectedItem().getId()));
        cargoList.getItems().clear();
        fillList();
    }

    public void updateCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateCheckPoint.class.getResource("../fxml/update-cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateCargo updateCargo = fxmlLoader.getController();
        updateCargo.setData(entityManagerFactory, cargoList.getSelectionModel().getSelectedItem());
        checkPointList.getItems().clear();
        fillList();
    }

    public void createCargo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateCargo.class.getResource("../fxml/create-cargo-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateCargo createCargo = fxmlLoader.getController();
        createCargo.setData(entityManagerFactory);
        cargoList.getItems().clear();
        fillList();
    }
    //------------------TRUCK----------------------
    public void viewTruck() {
        TruckHib truckHib = new TruckHib(entityManagerFactory);
        Truck truckDb = truckHib.getTruckById(truckList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Truck data", truckDb.toString());
        truckList.getItems().clear();
        fillList();
    }

    public void deleteTruck() {
        truckHib.deleteTruck(truckHib.getTruckById(truckList.getSelectionModel().getSelectedItem().getId()));
        truckList.getItems().clear();
        fillList();
    }

    public void updateTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateTruck.class.getResource("../fxml/update-truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateTruck updateTruck = fxmlLoader.getController();
        updateTruck.setData(entityManagerFactory, truckList.getSelectionModel().getSelectedItem());
        truckList.getItems().clear();
        fillList();
    }

    public void createTruck() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTruck.class.getResource("../fxml/create-truck-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateTruck createTruck = fxmlLoader.getController();
        createTruck.setData(entityManagerFactory);
        truckList.getItems().clear();
        fillList();
    }
    //------------------DESTINATION----------------------
    public void viewDestination() {
        DestinationHib destinationHib = new DestinationHib(entityManagerFactory);
        Destination destinationDb = destinationHib.getDestinationById(destinationList.getSelectionModel().getSelectedItem().getId());
        FxUtils.generateAlert(Alert.AlertType.INFORMATION, "Truck data", destinationDb.toString());
        destinationList.getItems().clear();
        fillList();
    }

    public void deleteDestination() {
        destinationHib.deleteDestination(destinationHib.getDestinationById(destinationList.getSelectionModel().getSelectedItem().getId()));
        destinationList.getItems().clear();
        fillList();
    }

    public void updateDestination() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UpdateDestination.class.getResource("../fxml/update-destination-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        UpdateDestination updateDestination = fxmlLoader.getController();
        updateDestination.setData(entityManagerFactory, destinationList.getSelectionModel().getSelectedItem(), true);
        destinationList.getItems().clear();
        fillList();
    }

    public void createDestination() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateDestination.class.getResource("../fxml/create-destination-page.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Trucker Shop");
        stage.setScene(scene);
        stage.show();
        CreateDestination createDestination = fxmlLoader.getController();
        createDestination.setData(entityManagerFactory, manager);
        destinationList.getItems().clear();
        fillList();
    }

    public void createReply(ActionEvent actionEvent) {
    }

    public void filterData(ActionEvent actionEvent) {
        LocalDate localDateDeparture = departureFilter.getValue();
        LocalDate localDateArrival = arrivalFilter.getValue();
        destinationList.getItems().clear();
        destinationHib.getDataByFilter(Status.valueOf(statusFilter.getSelectionModel().getSelectedItem().toString()), localDateDeparture, localDateArrival, Integer.valueOf(truckerFilter.getText())).forEach(e -> {
            destinationList.getItems().add(e);
        });
    }

    public void removeFilters(ActionEvent actionEvent) {
        List<Destination> destinations = destinationHib.getAllDestination();
        destinationList.getItems().clear();
        destinations.forEach(scientificPaper -> destinationList.getItems().add(scientificPaper));
    }
}
