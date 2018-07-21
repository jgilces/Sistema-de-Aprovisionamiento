/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.error.template.logica;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import AlertClass.ErroresMensajes;
import AlertClass.AlertMaker;
import static AlertClass.AlertMaker.dialog;
import static AlertClass.AlertMaker.node_to_be_blur;
import Central.ScenarioCentral;
import Central.publicVariables;
import com.jfoenix.controls.events.JFXDialogEvent;
import static javafx.application.Platform.exit;
/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class AlertErrorMakerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    public  Label textTemp;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTexto();
    }
    
    public void setTexto(){
        textTemp.setText(ErroresMensajes.texto.toUpperCase());
    } 
    
    public void closeDialog(){
        if(!(publicVariables.conexion.estado_conexion)){
            exit();
        }else if(publicVariables.VLAN_there){
            ScenarioCentral.secondaryWindows.hide();
        }else{
            
            dialog.setOnDialogClosed((JFXDialogEvent event1) -> {
            node_to_be_blur.setEffect(null);
            });
            AlertMaker.dialog.close();
        }
    }
}
