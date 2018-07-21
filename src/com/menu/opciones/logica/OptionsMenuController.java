/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.menu.opciones.logica;

import AlertClass.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import com.menu.opciones.opcionesBar.logica.OptionsAvailablesController;
/**
 * FXML Controller class
 *
 * @author Jimmy G
 */
public class OptionsMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hambuger;
    
    @FXML
    private StackPane rootPane;
    @FXML
    private AnchorPane mainContainer;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        OptionsAvailablesController.setPanes(rootPane, mainContainer);
        
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("/com/menu/opciones/opcionesBar/grafico/optionsAvailables.fxml"));
            drawer.setSidePane(box);
        
            HamburgerBackArrowBasicTransition burgerTask2;
            burgerTask2 = new HamburgerBackArrowBasicTransition(hambuger);
            burgerTask2.setRate(-1);
            hambuger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                burgerTask2.setRate(burgerTask2.getRate()*-1);
                burgerTask2.play();

                if(drawer.isShown()){
                    drawer.close();
                }else{
                    drawer.open();
                }
        });
        
    } catch (IOException ex) {
            Logger.getLogger(OptionsMenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}