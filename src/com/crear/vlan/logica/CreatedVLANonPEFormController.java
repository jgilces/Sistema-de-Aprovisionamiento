/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.crear.vlan.logica;

import AlertClass.AlertMaker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import AlertClass.*;
import Central.ScenarioCentral;
import Central.publicVariables;
import static Central.publicVariables.conexion;
import static Central.publicVariables.datos;
import com.clases.secundarias.VLAN;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import Central.publicVariables;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Jimmy G
 */
public class CreatedVLANonPEFormController implements Initializable {
      
    @FXML
    public JFXTextField txtCiudad;
    @FXML
    public JFXTextField txtEmpresa;
    @FXML
    public JFXTextField txtNumVlan;
    @FXML
    StackPane rootPaneVlan;
    @FXML
    AnchorPane mainContainerVlan;
    
    /*
        Variables que recogen los datos de las cajas de texto
        */
    String nombre_empresa;
    String nombre_ciudad;
    String num_vlan;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
        
    @FXML
    public void closeDialog(){
        AlertMaker.dialog.close();
    }
    
    public void verifyCreacionOfVLAN() throws IOException, SQLException{
        
        nombre_empresa=txtEmpresa.getText();
        nombre_ciudad=txtCiudad.getText();
        num_vlan=txtNumVlan.getText();
        
        
        
        //SE PROCEDE A VERIFICAR LA INFORMACION PROPORCIONADA POR EL USUARIO
        //ERROR DE CAMPOS VACIOS
        //conexion.insertTo(125, "SW", "Guayaquil");
        if(nombre_empresa.isEmpty() || nombre_ciudad.isEmpty() 
                || num_vlan.isEmpty()){//
            ErroresMensajes.texto="hay campos incompletos.\nTodos los "
                    + "campos son obligatorios.";
            Parent root=FXMLLoader.load(getClass().getResource("/com/error/"
                    + "template/interfaz/alertErrorMaker.fxml"));
            AlertMaker.showMaterialDialog(rootPaneVlan, root, mainContainerVlan);
        }else if(!isCorrectVlanNumber(Integer.parseInt(num_vlan))&&
                !(nombre_empresa.isEmpty() && nombre_ciudad.isEmpty()
                && num_vlan.isEmpty())){//
            ErroresMensajes.texto="El numero de la VLAN debe estar entre "
                    + "1 y 4096.";
            Parent root=FXMLLoader.load(getClass().getResource("/com/error/"
                    + "template/interfaz/alertErrorMaker.fxml"));
            AlertMaker.showMaterialDialog(rootPaneVlan, root, mainContainerVlan);
        }else {//OTROS ERRORES
            
            publicVariables.empresa=nombre_empresa;
            publicVariables.numero_vlan=Integer.parseInt(num_vlan);
            publicVariables.city=nombre_ciudad;
            //
            extraerData(nombre_ciudad);
            //
            if(verificaDisponibilidadDeVLAN()){//EXISTEN VLAN PARA ASIGNAR?
                //SI EXISTE VLANS DISPONIBLE
                //SE VERIFICA SI LA EMPRESA POSEE YA VLANS ASIGNADAS
                if(verificaSiEmpresaTieneVLAN()){// SI POSEE ENTONCES
                    //if(!publicVariables.btnSiState){
                        ErroresMensajes.texto="LA EMPRESA "+nombre_empresa+
                            " POSEE VLAN(S) ASIGANADA(S).";
                        Parent root=FXMLLoader.load(getClass().getResource("/com/"
                                + "crear/vlan/interfaz/poseeVLAN.fxml"));
                        //AlertMaker.showMaterialDialog(rootPaneVlan, root, mainContainerVlan);
                        Scene escena = new Scene(root);
                        escena.setFill(Color.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.initStyle(StageStyle.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.setScene(escena);
                        Central.ScenarioCentral.secondaryWindows.show();
                    
                } else {//SI NO POSEE
                    if (publicVariables.verifiIfVLANExist(publicVariables.numero_vlan)){//SI LA VLAN YA EXISTE
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
                        escena.setFill(Color.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.initStyle(StageStyle.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.setScene(escena);
                        Central.ScenarioCentral.secondaryWindows.show();
                    } else {//SI LA VLAN NO EXISTE
                        Central.ScenarioCentral.secondaryWindows.close();
                        publicVariables.num_vlan_fin = Integer.valueOf(num_vlan);

                        try {
                            publicVariables.conexion.insertTo(publicVariables.num_vlan_fin, publicVariables.empresa, publicVariables.city);
                        } catch (SQLException ex) {
                            Logger.getLogger(VLANYaAsignadaController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                                
                        System.out.println("Segundo si activo");
                        Parent root = FXMLLoader.load(getClass().getResource("/com/crear/vlan/"
                                + "interfaz/VLANCreadaBox.fxml"));
                        Scene escena = new Scene(root);
                        escena.setFill(Color.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.initStyle(StageStyle.TRANSPARENT);
                        Central.ScenarioCentral.secondaryWindows.setScene(escena);
                        Central.ScenarioCentral.secondaryWindows.show();
                    }
                }
                
            }else{//SI NO EXISTEN VLANS PARA ASIGNAR
                errorWindowEstablish("Proceso no completado, no existen"
                        + " VLANs disponibles para " + nombre_ciudad);
            }
        }
        
    }
    
    /**
     * ESTE METODO PERMITE CONFIGURAR LA PANTALLA DE ERROR PARA DESPUES
     * MOSTRARLA.
     * @param errorMessage
     * @throws IOException 
     */
    public void errorWindowEstablish(String errorMessage) throws IOException{
        ErroresMensajes.texto=errorMessage;
            Parent root=FXMLLoader.load(getClass().getResource("/com/error"
                    + "/template/interfaz/alertErrorMaker.fxml"));
            
            if(publicVariables.setScene){
                ScenarioCentral.secondaryWindows.show();
            }else{
                publicVariables.setScene=true;
                //SE CREA EL ESCENARIO SOBRE EL CUAL SERA MOSTRADO EL MENSAJE
                //DE ERROR
                Scene scene = new Scene(root);

                //SE AGREGA LA ESCENA A LA VENTANA A MOSTRAR
                //ScenarioCentral.secondaryWindows.initStyle(StageStyle.UNDECORATED);
                ScenarioCentral.secondaryWindows.setScene(scene);
                ScenarioCentral.secondaryWindows.show();
            }
            publicVariables.setScene=false;
    }
    
    /**
     *SI EL VALOR DE LA VLAN ESTA DENTRO DE LOS RANGOS PERMITIDOS
     * SI EL VALOR DE LA VLAN ESTA DENTRO DE LOS RANGOS PERMITIDOS
     */
    private boolean isCorrectVlanNumber(int number) {
        Boolean estate=false;
        
        if(!(number>4096 || number<1)){
            estate=true;
        }
        
        return estate;
    }
    
    /**
     * METODO QUE EXTRAE LA INFORMACION DE VLANS DE UNA CIUDAD EN PARTICULAR
     * @throws SQLException 
     */
    private void extraerData(String ciudad) throws SQLException{
        
        String tabla = ciudad.toLowerCase() + "_clientes_vlans";
        
        
        //SE FILTRAN LOS DATOS CORRESPONDIENTE A LA CIUDAD EN PARTICULAR
        conexion.buscarDato("numero_vlan,nombre_empresa", tabla,"","",false);
        
        /**
         * EL CONTROLADOR DE LA BASE DE DATOS CREA UNA LISTA DE LAS VLAN
         * EXISTENTES EN LA CIUDAD
         */
        conexion.createListData();
        
    }

    /**
     * METODO QUE VERIFICA SI EXISTEN VLAN DISPONIBLES EN LA CIUDAD
     * PARA SER ASIGNADAS
     * @return 
     */
    private boolean verificaDisponibilidadDeVLAN() {
        Boolean res=false;
        /*SE PREGUNTA AL CONTROLADOR DE LA BASE DE DATOS EL NUMERO DE
          ELEMENTOS QUE POSEE LA CIUDAD SELECCIONADA.
          SI EL NUMERO CALCULADO ES EL MAXIMO PERMITO, ESTE METODO RETORNA
          FALSE, LO QUE INDIQUE QUE NO HAY VLANS PARA ASIGNAR EN LA CIUDAD
        */
        if(conexion.getTotalDatos()<4096) res=true;
        
        return res;
        
        
    }

    /**
     * METODO QUE VERIFICA SI LA EMPRESA EXISTE EN LA BASE DE DATOS, EL HECHO
     * DE EXISTIR EN LA BASE DE DATOS ES INDICIO PARA CONCLUIR DE QUE
     * LA EMPRESA POSEE YA VLAN ASIGNADA, RETORNA TRUE SI ESE ES EL CASO
     * @return 
     */
    private boolean verificaSiEmpresaTieneVLAN() {
        Boolean res = false;
        
        //SE ITERA CADA ELEMENTOS DE LA BASE DE DATOS
        for(Object info: datos){
            VLAN infoVLAN = (VLAN) info;
            if(infoVLAN.getNameEmpresa().equals(nombre_empresa)){
                res=true;
                break;
            }
        }
        
        return res;
        
        
    }
    
   

    
}