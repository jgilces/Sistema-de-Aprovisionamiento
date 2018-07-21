/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menu.opciones.opcionesBar.logica;

import AlertClass.AlertMaker;
import Central.ScenarioCentral;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import Central.publicVariables;
import javafx.scene.Scene;
/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class OptionsAvailablesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton crVLAN;
    
    private static StackPane rootPane;
    private static AnchorPane mainContainer;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crVLAN.setFocusTraversable(false);
    }
    
    public static void setPanes(StackPane root_,AnchorPane container){
        rootPane=root_;
        mainContainer=container;
    }
    
    public void crearVLAN() throws IOException{
        Central.ScenarioCentral.centerWindow.hide();
        publicVariables.VLAN_there=true;
        Parent root = FXMLLoader.load(getClass().getResource("/com/crear/vlan/interfaz/createdVLANonPEForm.fxml"));
        //AlertMaker.showMaterialDialog(rootPane, root, mainContainer);
        Scene scene=new Scene(root);
            
        ScenarioCentral.centerWindow.setScene(scene);
        ScenarioCentral.centerWindow.show();
    }
    
    public void crearVRF() throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource("/com/crear/vrf/interfaz/crearVRFonPE.fxml"));
        AlertMaker.showMaterialDialog(rootPane, root, mainContainer);
    }
    
}
