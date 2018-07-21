/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crear.vlan.logica;

import Central.publicVariables;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Prueba
 */
public class VLANYaAsignadaController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    Label lblNumVLAN;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNumVLAN.setText(String.valueOf(publicVariables.num_vlan_fin));
        try {
            publicVariables.conexion.insertTo(publicVariables.num_vlan_fin, publicVariables.empresa, publicVariables.city);
        } catch (SQLException ex) {
            Logger.getLogger(VLANYaAsignadaController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ClickOnSi() throws IOException{
        Central.ScenarioCentral.secondaryWindows.close();
        
        System.out.println("Segundo si activo");
        Parent root=FXMLLoader.load(getClass().getResource("/com/crear/vlan/"
            + "interfaz/VLANCreadaBox.fxml"));
        Scene escena = new Scene(root);
        Central.ScenarioCentral.secondaryWindows.setScene(escena);
        Central.ScenarioCentral.secondaryWindows.show();
        
    }
    
    public void ClickOnNo(){
        
    }
    
}
