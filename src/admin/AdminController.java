package admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.control.TextField;

public class AdminController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private DatePicker dob;
    @FXML
    private TableView<StudentData> studenttable;

    @FXML
    private TableColumn<StudentData, String> idcolumn;
    @FXML
    private TableColumn<StudentData, String> firstnamecolumn;
    @FXML
    private TableColumn<StudentData, String> lastnamecolumn;
    @FXML
    private TableColumn<StudentData, String> emailcolumn;
    @FXML
    private TableColumn<StudentData, String> dobcolumn;


    private dbConnection dc;
    private ObservableList<StudentData> data;

    private String sql = "SELECT * FROM students";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.dc = new dbConnection();

    }

    @FXML
    private void loadStudentData(ActionEvent actionEvent){
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()){
                this.data.add(new StudentData(rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException ex){
            System.err.println("Error "+ex);
        }

        this.idcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("ID"));
        this.firstnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("firstName"));
        this.lastnamecolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("lastName"));
        this.emailcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("email"));
        this.dobcolumn.setCellValueFactory(new PropertyValueFactory<StudentData,String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(data);
    }

    @FXML
    private void AddStudent(ActionEvent actionEvent){
        String sqlinsert = "INSERT INTO students (id, fname, lname, email, DOB) VALUES(?,?,?,?,?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmp = conn.prepareStatement(sqlinsert);

            stmp.setString(1,this.id.getText());
            stmp.setString(2,this.firstname.getText());
            stmp.setString(3,this.lastname.getText());
            stmp.setString(4,this.email.getText());
            stmp.setString(5,this.dob.getEditor().getText());

            stmp.execute();
            stmp.close();

        }catch (SQLException se){
            se.printStackTrace();
        }
    }


    @FXML
    private void clearFields(ActionEvent actionEvent){

        this.id.setText("");
        this.firstname.setText("");
        this.lastname.setText("");
        this.email.setText("");
        this.dob.setValue(null);

    }

}
