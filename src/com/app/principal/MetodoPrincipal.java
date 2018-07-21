/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.principal;

import AlertClass.ErroresMensajes;
import Central.ScenarioCentral;
import static Central.publicVariables.conexion;
import com.BD.coneccion.DBConnector;
import javafx.application.Application;
import static javafx.application.Platform.exit;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author Jimmy G
 */
public class MetodoPrincipal extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //SE CONECTA A LA BASE DE DATOS DESDE AQUI
        conexion.connect();
        
        /*
        SI NO SE CONECTA A LA BASE DE DATOS MUESTRA UN ERROR EN PANTALLA,
        CASO CONTRARIO MUESTRA LA PANTALLA DE LOGIN DEL USUARIO
        */
        if(!(conexion.estado_conexion)){
            //SE INICIALIZA EL MENSAJE DE ERROR
            ErroresMensajes.texto="error al conectar a la base de datos.";
            
            //SE CREA LA INTERFAZ GRAFICA PARA MOSTRAR EL ERROR
            Parent root = FXMLLoader.load(getClass().getResource("/com/error"
                    + "/template/interfaz/alertErrorMaker.fxml"));
        
            //SE CREA EL ESCENARIO SOBRE EL CUAL SERA MOSTRADO EL MENSAJE
            //DE ERROR
            Scene scene = new Scene(root);
            
            //SE AGREGA LA ESCENA A LA VENTANA A MOSTRAR
            ScenarioCentral.centerWindow.initStyle(StageStyle.UNDECORATED);
            ScenarioCentral.centerWindow.setScene(scene);
            ScenarioCentral.centerWindow.show();
        }else{
            //SE CREA LA INTERFAZ GRAFICA PARA MOSTRAR EL ERROR
            Parent root = FXMLLoader.load(getClass().getResource("/com/login/"
                    + "app/interfaz/LoginUser.fxml"));
            
            //SE CREA EL ESCENARIO SOBRE EL CUAL SERA MOSTRADO EL MENSAJE
            //DE ERROR
            Scene scene = new Scene(root);
            
            //SE AGREGA LA ESCENA A LA VENTANA A MOSTRAR
            ScenarioCentral.centerWindow.initStyle(StageStyle.UNDECORATED);
            ScenarioCentral.centerWindow.setScene(scene);
            ScenarioCentral.centerWindow.show();
        }
        
        
        
    } 
    
    public static void main(String[] args) {
        launch(args);
    }
}
