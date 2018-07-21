package com.BD.coneccion;


import static Central.publicVariables.datos;
import com.clases.secundarias.VLAN;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;
import static java.util.Spliterators.iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author grupo1
 */
public class DBConnector {
    
    //VARIABLES NECESARIAS PARA LA CONEXION A LA BASE DE DATOS
    private String URL="jdbc:mysql://localhost:3306/database_sispro";
    private String username="root";
    private String password="";
    private Connection conexion = null;
    
    //VARIABLE AUXILIAR
    public Boolean estado_conexion = false;
    public Boolean estado_busqueda = false;
    
    //OBJETO QUE NOS PERMITIRA REALIZAR LAS CONSULTAS
    private PreparedStatement conmutador;
    private ResultSet respuesta;
    
    public int totalNumber;
    
    
    //METODO PARA REALIZAR LA CONECCION A LA BD
    public  Connection connect(){
           
        try {
            //SE ESPECIFICA EL DRIVER PARA LA CONEXION A LA BD
            Class.forName("com.mysql.jdbc.Driver");
            
            /*ONEXION ESPECIFICANDO LA DIRECCION DE LA BD,
            EL USUA
            SE REALIZA LA CONEXION ESPECIFICANDO LA DIRECCION DE LA BD,
            EL USUARIO Y LA CONTRASEÑA DE LA BASE DE DATOS
            */
            conexion=(Connection) DriverManager.getConnection(URL, username, password);
            
            //SE MUESTRA UN MENSAJE SI LA CONEXION FUE EXITOSA
            this.estado_conexion=true;
            
        } catch (ClassNotFoundException | SQLException ex) {
            //SI LA CONEXION A LA BD NO SE PUDO DAR, SE MUESTRA UN MENSAJE DE
            //ERROR
            //System.out.println("Error al conectar a la BD");
        }
        
        return conexion;
    }
    
    //METODO PARA REALIZAR CONSULTAS A LA BD
    public String getDato(String datoInf){
        
        try {
            return respuesta.getString(datoInf);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
  
    }
    
    /**
     * ESTE METODO PERMITE SABER EL NUMERO DE REGISTRON EN LA BASE DE DATOS
     * @param campo
     * @param tabla
     * @param condicion
     * @param valorCondicion
     * @return
     * @throws SQLException 
     */
    public int numberOfFields(String command) throws SQLException{
        
        conmutador=conexion.prepareStatement(command);
        respuesta=conmutador.executeQuery();
        int total = 0;
        while (respuesta.next()){
           //Obtienes la data que necesitas...
           total++;
        }
        respuesta.first();
        System.out.println(command + String.valueOf(total));
        return total;
    }
    
    //METODO PARA REALIZAR UNA BUSQUEDA EN LA BASE DE DATOS
    //ESTE METODO TAMBIEN SE UTILIZA PARA VERIFICAR SI EXISTE
    //UN DATO EN LA BASE DE DATOS
    public void buscarDato(String campo,String tabla,String parametroBusqueda,String valorParametro, Boolean activeCondicion) throws SQLException{
        if(!activeCondicion){
            String comando = "select "+campo+" from "+tabla;
            /*OBTENEMOS EL NUMERO DE REGISTROS PARA POSIBLES USOS*/
            totalNumber=numberOfFields(comando);
//            System.out.println("Total de datos segun la condicion: "+this.totalNumber);
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(DBConnector.class.getName()).log(Level.SEVERE, null, ex);
//            }
            //SE GENERA LA CONSULTA A LA BASE DE DATOS
            conmutador=conexion.prepareStatement("select "+campo+" from "+tabla);
            respuesta=conmutador.executeQuery();
        }else{
            //String comando = "select "+campo+" from "+tabla+" where "+condicion+"=?";
            String comando = "select "+campo+" from "+tabla;
            /*OBTENEMOS EL NUMERO DE REGISTROS PARA POSIBLES USOS*/
            totalNumber=numberOfFields(comando);
            //System.out.println("Total de datos segun la condicion: "+this.totalNumber);

            //SE GENERA LA CONSULTA A LA BASE DE DATOS
            conmutador=conexion.prepareStatement("select "+campo+" from "+tabla+" where "+parametroBusqueda+"=?");
            conmutador.setString(1, valorParametro);
            respuesta=conmutador.executeQuery();
        }
        

        //SE REALIZAN LAS VALIDACIONES NECESARIAS
        if(respuesta.next()){
            System.out.println("Exite");
            
            this.estado_busqueda=true;
        }else{
            System.out.println("No existe");
            this.estado_busqueda=false;
        }        
    }
    
    
    public Connection getConexion(){
        return conexion;
    }
    
    public void insertTo(int num_vlan, String name_empresa, String ciudad) throws SQLException{
        try{
            //SE GENERA LA CONSULTA A LA BASE DE DATOS
            conmutador=conexion.prepareStatement("insert into "+ciudad.toLowerCase()+"_clientes_vlans"+" (numero_vlan,nombre_empresa) values (?,?)");
            conmutador.setInt(1,num_vlan);
            conmutador.setString(2, name_empresa);
            conmutador.executeUpdate();
        }catch(Exception e){
            
        }
        
    }
    
    public int getTotalDatos(){
        return this.totalNumber;
    }

    public void createListData() throws SQLException{
        
        datos.clear();
        
        /*
         * SE USA EL NUMERO DE REGISTROS PARA GUARDAR LOS DATOS DE LA VLAN
         * DE UNA CIUDAD EN PARTICULAR
         */
        
        /*
        SE RECORRE LA TABLA CON UN BUCLE FOR Y USANDO EL METODO ABSOLUTE
        EL CUAL UBICA a resultset EN UNA FILA
        */        
        for(int i=1;i<=this.totalNumber;i++){
            respuesta.absolute(i);
            VLAN infoVLAN = new VLAN();
            infoVLAN.setNameEmpresa(respuesta.getString("nombre_empresa"));
            infoVLAN.setNumberVLAN(respuesta.getInt("numero_vlan"));
            /**
             * SE GUARDA EN LA LISTA DE DATOS RELEVANTES
             */
            datos.add(infoVLAN);
        }
    }
}
