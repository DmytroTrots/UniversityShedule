package Schedule.ControllerFiles;

import Schedule.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController {
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = 800;
    int height = 600;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginField;

    @FXML
    private Text IncorrectLoginText;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button LoginButton;

    @FXML
    private Button SignUpButton;

    @FXML
    private ImageView CloseStageButton;

    @FXML
    void CloseStageAction(MouseEvent event) {
        Stage primaryStage = (Stage) CloseStageButton.getScene().getWindow();
        primaryStage.close();
    }

    //Переход с страницы авторизации на страницу регистрации
    @FXML
    void SignUpWindowOpen(ActionEvent event) throws IOException {
        SignUpButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/Schedule/VisualFiles/SignUpWindow.fxml"));

        Stage SignUpStage = new Stage();
        SignUpStage.setScene(new Scene(root, width, height));
        SignUpStage.initStyle(StageStyle.UNDECORATED);
        SignUpStage.show();
    }

    //Переход со страницы авторизации на страницу с расписанием
    @FXML
    void LoginButtonClick(ActionEvent event) throws IOException {
        if(LoginField.getText().isBlank() == false || PasswordField.getText().isBlank() == false){
            loginUser();
        }
        else{
            IncorrectLoginText.setVisible(true);
        }

    }

    //
    private void loginUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDb = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM schedule.users WHERE login = '" + LoginField.getText().trim() + "'AND pass = '" + PasswordField.getText() + "'";
        try{
            Statement statement = connectDb.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    IncorrectLoginText.setText("You are welcome");
                    IncorrectLoginText.setVisible(true);

                    LoginButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/Schedule/VisualFiles/mainApp.fxml"));
                    Stage mainAppStage = new Stage();
                    mainAppStage.setScene(new Scene(root, width, height));
                    mainAppStage.initStyle(StageStyle.UNDECORATED);
                    mainAppStage.show();
                }
                else{
                    IncorrectLoginText.setVisible(true);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    void initialize() {

    }
}
