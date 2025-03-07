/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package modul4cnth1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class MainController implements Initializable {

private User selectedUser;
//atribut
     @FXML
    private Button btnAdd;
     
    @FXML
    private Button btnDelete;
      
    @FXML
    private Button btnUpdate;
    
    @FXML
    private TextField txtUsername;
     
    @FXML
    private PasswordField txtPassword;
      
    @FXML
    private TextField txtFullname;
       
       


    private void clearFields() {
        txtUsername.clear();
        txtPassword.clear();
        txtFullname.clear();
        selectedUser = null; 
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void selectUser(User user) {
        if (user != null) {
            selectedUser = user;
            txtUsername.setText(user.getUsername());
            txtFullname.setText(user.getFullname());
            txtPassword.setText(user.getPassword());
        }
    }

    @FXML
    private void addUser() {
        String username = txtUsername.getText();
        String fullname = txtFullname.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            showAlert("Input Error", "Field tidak boleh kosong!");
            return;
        }

        User newUser = new User(username, password, fullname);
        UserDAO.addUser(newUser);
        loadDataUsers(); 
        clearFields();
    }

    @FXML
    private void updateUser() {
        if (selectedUser == null) {
            showAlert("Selection Error", "Tidak ada user yang dipilih!");
            return;
        }

        String username = txtUsername.getText();
        String fullname = txtFullname.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
            showAlert("Input Error", "Field tidak boleh kosong!");
            return;
        }

        selectedUser.setUsername(username);
        selectedUser.setPassword(password);
        selectedUser.setFullname(fullname);
        
       
        UserDAO.updateUser(selectedUser);
        loadDataUsers(); 
        clearFields();
    }

    @FXML
    private void deleteUser() {
        if (selectedUser == null) {
            showAlert("Selection Error", "Tidak ada user yang dipilih!");
            return;
        }

//        UserDAO.deleteUser(selectedUser.getUsername());
UserDAO.deleteUser(selectedUser.getUsername());

        loadDataUsers(); 
        clearFields();
    }
    
    
    
//    Update
//    Delete
    //    Read

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colFullname;
    
    @FXML
    private TableColumn<User, String> colPassword;

    
    @FXML
    private TableView<User> Tbluser;

    private void loadDataUsers() {
        ObservableList<User> userList = UserDAO.getUsers(); // Pastikan UserDAO.getUsers() mengembalikan ObservableList<User>
        Tbluser.setItems(userList);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set nilai untuk kolom tabel
        colUsername.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        colFullname.setCellValueFactory(new PropertyValueFactory<User,String>("fullname"));
        colPassword.setCellValueFactory(new PropertyValueFactory<User,String>("password"));

        loadDataUsers();
        
        Tbluser.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectUser(newValue)
        );
    }
}

    

    