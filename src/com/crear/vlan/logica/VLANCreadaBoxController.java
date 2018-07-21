/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crear.vlan.logica;

import Central.publicVariables;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Prueba
 */
public class VLANCreadaBoxController implements Initializable {
    
    @FXML
    Label lblNumVLAN;
            
    @FXML
    Label lblCiudad;
    
    @FXML
    Label lblEmpresa;
            
    @FXML
    Label lblFecha;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblNumVLAN.setText(String.valueOf(publicVariables.num_vlan_fin));
        lblCiudad.setText(publicVariables.city);
        lblEmpresa.setText(publicVariables.empresa);
        /**
         *EN ESTA SECCION SE DEBE AGREGAR LA VLAN A LA BASE DE DATOS
         * Y SE LA DEBE CREAR EN EL DISPOSITIVO PERTINENTE
         */
    }    
    
}