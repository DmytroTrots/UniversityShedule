package Schedule.ControllerFiles;

import Schedule.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignUpController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField PasswordSignUpField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField SurnameField;

    @FXML
    private TextField FatherField;

    @FXML
    private TextField GroupField;

    @FXML
    private TextField CourseField;

    @FXML
    private TextField LoginSignUpField;

    @FXML
    private TextField EmailField;

    @FXML
    private Button SignUpPageButton;

    @FXML
    private ComboBox<String> FacultyComboBox;

    @FXML
    private Button PreviousPageButton;

    @FXML
    private ImageView CloseStageSignButton;

    @FXML
    private Text InvalidData;

    @FXML
    void CloseStageSignAction(MouseEvent event) {
        Stage SignUpStage = (Stage) CloseStageSignButton.getScene().getWindow();
        SignUpStage.close();
    }


    //Переход со страницы регистрации на страницу авторизации
    @FXML
    void PreviousPageButtonAction(ActionEvent event) throws IOException {
        PreviousPageButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/Schedule/VisualFiles/LoginWindow.fxml"));

        Stage previousStage = new Stage();
        previousStage.setTitle("SignUp");
        previousStage.setScene(new Scene(root, 800, 600));
        previousStage.initStyle(StageStyle.UNDECORATED);
        previousStage.show();
    }

    @FXML
    void Registration(ActionEvent event) throws IOException {
        if(!PasswordSignUpField.getText().isBlank() || !NameField.getText().isBlank() || !FatherField.getText().isBlank() || FacultyComboBox.getValue() == "Факультет" ||
                !CourseField.getText().isBlank() || !GroupField.getText().isBlank() || !LoginSignUpField.getText().isBlank() || !EmailField.getText().isBlank() || !PasswordSignUpField.getText().isBlank()){
            registerUser();
        }
        else{
            InvalidData.setVisible(true);
        }

    }

    public void registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = NameField.getText();
        String surname = SurnameField.getText();
        String fathersname = FatherField.getText();
        String faculty = FacultyComboBox.getValue();
        int course = Integer.parseInt(CourseField.getText());
        int studentgroup = Integer.parseInt(GroupField.getText());
        String login = LoginSignUpField.getText();
        String email = EmailField.getText();
        String pass = PasswordSignUpField.getText();
        String insertFields = "INSERT INTO schedule.users (firstname, surname, fathersname, faculty, course, studentgroup, login, email, pass) VALUES ('";
        String insertValues = firstname + "','" + surname + "','" + fathersname + "','" + faculty + "','" + course + "','" + studentgroup + "','" + login + "','" + email + "','" + pass + "')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    @FXML
    void initialize() {
        FacultyComboBox.getItems().addAll("ФИТ", "ФРГТБ", "ФФО", "ФЕМП", "ФФБС", "ФМТП", "ФТМ");
    }
}
