/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crear.vlan.logica;

import AlertClass.ErroresMensajes;
import Central.ScenarioCentral;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Central.publicVariables;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Prueba
 */
public class poseeVLANController implements Initializable {

    @FXML
    private Label lblTextoWarning;
    @FXML
    private JFXButton btnSi;
    @FXML
    private JFXButton btnNo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblTextoWarning.setText(ErroresMensajes.texto.toUpperCase());
    }

    public void clickOnSi() throws IOException {

        ScenarioCentral.secondaryWindows.close();
        publicVariables.btnSiState = true;
        if (publicVariables.verifiIfVLANExist(publicVariables.numero_vlan)) {//SI LA VLAN YA EXISTE
            System.out.println("Estoy en posee vlan btnSI");
            /**
             * SE BUSCA UN NUMERO DE VLAN DESDE EL 1 SECUENCIALMENTE
             */
            int new_vlan = 1;
            while (publicVariables.verifiIfVLANExist(new_vlan)) {
                System.out.println(new_vlan);
                System.out.println("VLAN Existente");
                new_vlan++;
            }
            publicVariables.num_vlan_fin = new_vlan;
            Parent root = FXMLLoader.load(getClass().getResource("/com/crear/vlan/interfaz"
                    + "/VLANYaAsignada.fxml"));
            Scene escena = new Scene(root);
            Central.ScenarioCentral.secondaryWindows.setScene(escena);
            Central.ScenarioCentral.secondaryWindows.show();
        } else {//SI LA VLAN NO EXISTE
            publicVariables.num_vlan_fin = publicVariables.numero_vlan;
            Central.ScenarioCentral.secondaryWindows.close();

            try {
                publicVariables.conexion.insertTo(publicVariables.numero_vlan, publicVariables.empresa, publicVariables.city);
                System.out.println("ESTOY HERO ESPOL "+publicVariables.numero_vlan + publicVariables.empresa + publicVariables.city);
            } catch (SQLException ex) {
                Logger.getLogger(VLANYaAsignadaController.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("Segundo si activo");
            Parent root = FXMLLoader.load(getClass().getResource("/com/crear/vlan/"
                    + "interfaz/VLANCreadaBox.fxml"));
            Scene escena = new Scene(root);
            Central.ScenarioCentral.secondaryWindows.setScene(escena);
            Central.ScenarioCentral.secondaryWindows.show();
        }
    }

    public void clickOnNo() {
        ScenarioCentral.secondaryWindows.close();
        //mainContainerVlan.setEffect(null);
    }
}
