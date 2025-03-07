/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modul4cnth1;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.*;
/**
 *
 * @author Lenovo
 */
public class UserDAO {
    public static ObservableList<User> getUsers() {
        // ObservableList untuk menyimpan data user
        ObservableList<User> userList = FXCollections.observableArrayList();
        String query = "select * from user";

        try {
            // Membuat koneksi ke database
            Connection koneksi = DBConnection.getConnection();
            // Membuat statement
            Statement stmt = koneksi.createStatement();
            // Query untuk mengambil data user
            ResultSet rs = stmt.executeQuery(query);
            
             // Menambahkan data ke ObservableList
            while (rs.next()) {
                String username = rs.getString("username");
                String fullname = rs.getString("fullname");
                String password = rs.getString("password");

                userList.add(new User(
                    username,
                    password,
                    fullname
                ));
            }

            // Menutup koneksi
            rs.close();
            stmt.close();
            koneksi.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        
     
        return userList;
    }
    

    public static void addUser(User user) {
        String query = "INSERT INTO user (username, password, fullname) VALUES (?, MD5(?), ?)";
    
    try {
        Connection koneksi = DBConnection.getConnection();
        PreparedStatement smt = koneksi.prepareStatement(query);
        
        smt.setString(1, user.getUsername());
        smt.setString(2, user.getPassword());
        smt.setString(3, user.getFullname());
        
        smt.executeUpdate();
        
        smt.close();
        koneksi.close();
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public static void updateUser(User user) {
  String query = "UPDATE user SET fullname = ?, password = ? WHERE username = ?";


    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
        mst.setString(1, user.getFullname()); 
        mst.setString(2, user.getPassword()); 
        mst.setString(3, user.getUsername()); 

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

 public static void deleteUser(String username) {
    String query = "DELETE FROM user WHERE username = ?";

    try (Connection koneksi = DBConnection.getConnection()) {
        PreparedStatement mst = koneksi.prepareStatement(query);
        mst.setString(1, username);

        mst.executeUpdate();
        mst.close();
        koneksi.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}   
    
}