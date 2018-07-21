/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Central;

import AlertClass.ErroresMensajes;
import com.BD.coneccion.DBConnector;
import com.clases.secundarias.VLAN;
import java.io.IOException;
import java.util.LinkedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author Prueba
 */
public class publicVariables {
    
    //SE CREA UN OBJETO DBCONNECTOR
    public static DBConnector conexion = new DBConnector();
    public static Boolean VLAN_there=false;
    
    //LISTA QUE GUARDA DATOS RELEVANTES
    public static LinkedList datos = new LinkedList();
    
    //VARIABLES USADAS POR EL FORM CREAR VLAN
    public static Boolean btnSiState=false;
    public static int num_vlan_fin;
    public static int numero_vlan;
    public static String city;
    public static String empresa;
    
    //VERIFICA LA APERTURA DE LA VENTANA DE ERROR
    public static Boolean setScene=false;
    
    //public static Parent root;
    
    /**
     * ESTE METODO VERIFICA SI LA VLAN PROPORCIONADA POR EL ADMINISTRADOR DE 
     * RED EXISTE EN LA BASE DE DATOS, EN CASO DE EXISTIR RETORNA TRUE
     * @param vlan
     * @param num_vlan 
     * @return  
     */
    public static Boolean verifiIfVLANExist(int vlan) {
        
        Boolean st = false;
        
        //SE ITERA CADA ELEMENTOS DE LA BASE DE DATOS
        for(Object info: datos){
            VLAN infoVLAN = (VLAN) info;
            if(infoVLAN.getNumberVLAN()==vlan){
                System.out.println("MI VALOR ES TRUE" + String.valueOf(infoVLAN.getNumberVLAN()));
                st=true;
                break;
            }
        }
        
        return st;
    }
    
    
     public static void secondWarninWindow(String mensaje, Parent root) throws IOException{
        ErroresMensajes.texto=mensaje;
        
        if(setScene){
            ScenarioCentral.secondaryWindows.show();
        }else{
            setScene=true;
            //SE CREA EL ESCENARIO SOBRE EL CUAL SERA MOSTRADO EL MENSAJE
            //DE ERROR
            Scene scene = new Scene(root);

            //SE AGREGA LA ESCENA A LA VENTANA A MOSTRAR
            //ScenarioCentral.secondaryWindows.initStyle(StageStyle.UNDECORATED);
            ScenarioCentral.secondaryWindows.setScene(scene);
            ScenarioCentral.secondaryWindows.show();
            }
        setScene=false;
    }
    
}
