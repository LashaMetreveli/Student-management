package LoginApp;

import admin.AdminController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import students.StudentController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class LoginController implements Initializable {

    LoginModel loginModel = new LoginModel();

    @FXML
    private Label dbstatus;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ComboBox<option> combobox;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginStatus;



    @Override
    public void initialize(URL url, ResourceBundle rb){

        if(this.loginModel.isDatabaseConnected()){
            this.dbstatus.setText("Connected");
        }else { this.dbstatus.setText("Not Connected"); }

            this.combobox.setItems(FXCollections.observableArrayList(option.values()));
    }

    @FXML
    public void Login(ActionEvent event){
        try {

            if(this.loginModel.isLogin(this.username.getText(), this.password.getText(),((option)this.combobox.getValue()).toString())) {
                Stage stage = (Stage) this.loginButton.getScene().getWindow();
                stage.close();

                switch (((option) this.combobox.getValue()).toString()) {
                    case "Admin":
                        adminLogin();
                        break;
                    case "Student":
                        studentLogin();
                        break;
                }

            }else {
                this.loginStatus.setText("Wrong Username or Password");
            }

        }catch (Exception loacalExeption){
            loacalExeption.printStackTrace();

        }

    }

    public void studentLogin(){
        try {
            Stage userStage  = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root  = (Pane)loader.load(getClass().getResource("/students/StudentFXML.fxml").openStream());

            StudentController studentController = (StudentController) loader.getController();

            Scene scene = new Scene(root);
            userStage.setScene(scene);
            userStage.setTitle("Student DashBoard");
            userStage.setResizable(false);
            userStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void adminLogin(){
        try {
            Stage adminstage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader();
            Pane adminroot = (Pane)adminLoader.load(getClass().getResource("/admin/AdminFXML.fxml").openStream());

            AdminController adminController = (AdminController) adminLoader.getController();

            Scene scene = new Scene(adminroot);
            adminstage.setScene(scene);
            adminstage.setTitle("Admin DashBoard");
            adminstage.setResizable(false);
            adminstage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
