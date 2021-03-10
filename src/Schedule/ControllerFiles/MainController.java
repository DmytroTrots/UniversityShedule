package Schedule.ControllerFiles;

import Schedule.AllSubjectsTable;
import Schedule.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<AllSubjectsTable> TableSchedule = new TableView<>();

    @FXML
    private TableColumn<AllSubjectsTable, String> TableScheduleMonday;

    @FXML
    private TableColumn<AllSubjectsTable, String> TableScheduleTuesday;

    @FXML
    private TableColumn<AllSubjectsTable, String> TableScheduleWednesday;

    @FXML
    private TableColumn<AllSubjectsTable, String> TableScheduleThursday;

    @FXML
    private TableColumn<AllSubjectsTable, String> TableScheduleFriday;

    AllSubjectsTable allSubjectsTable;

    @FXML
    void initialize(){
        try {
            SetTableView();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void SetTableView() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();
        String getSubjectsSQL = "SELECT * FROM schedule.fit2f9g";
        ResultSet set = connectDb.createStatement().executeQuery(getSubjectsSQL);
        final ObservableList<AllSubjectsTable> observableList = FXCollections.observableArrayList();
        TableScheduleMonday.setCellValueFactory(new PropertyValueFactory<>("monday"));
        TableScheduleTuesday.setCellValueFactory(new PropertyValueFactory<>("tuesday"));
        TableScheduleWednesday.setCellValueFactory(new PropertyValueFactory<>("wednesday"));
        TableScheduleThursday.setCellValueFactory(new PropertyValueFactory<>("thursday"));
        TableScheduleFriday.setCellValueFactory(new PropertyValueFactory<>("friday"));
        while(set.next()){
            allSubjectsTable = new AllSubjectsTable(set.getString("monday"), set.getString("tuesday"), set.getString("wednesday"),
                    set.getString("thursday"), set.getString("friday"));
            observableList.add(allSubjectsTable);
        }
        TableSchedule.setItems(observableList);
    }



}

