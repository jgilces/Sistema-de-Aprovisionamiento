/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.telnet.connector;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.telnet.*;

/**
 *
 * @author Administrador
 */
public class TelnetConnector {
    //SE CREA UN OBJETO CLIENTE 
    private TelnetClient telnet;//Cliente Telnet
    private InputStream in;
    private PrintStream out;
    private String prompt; //Prompt en la cosola
    private String prompt2;
    private String prompt3;
    private Boolean state_telnet_con;
    
    /**
    CONSTRUCTOR DE LA CLASE, INICIALIZA LAS VARIABLES
    **/
    public TelnetConnector(){
        telnet=new TelnetClient();
        prompt = "#"; //Prompt en la cosola
        prompt2 = "?";
        prompt3 = "!";
        state_telnet_con=false;
    }
    
    /**
     * EL METODO SIGUIENTE SE ENCARGA DE CONECTAR AL DISPOSITIVO USANDO
     * LA DIRECCION IP DEL MISMO
     * @param ip
     */
    public void telnetConnectTo(String ip){
        try {
            /*PERMITE REALIZAR LA CONECION TELNET*/
            telnet.connect(ip);

            in = telnet.getInputStream();
            out = new PrintStream(telnet.getOutputStream());
            this.state_telnet_con=true;
            
        } catch (IOException ex) {
            this.state_telnet_con=false;
        }
    }
    
    public void authUser(String user, String pass){
          
        /*SE ENVIAN LOS COMANDOS RESPECTIVOS AL DISPOSITIVO*/
        this.sendCommand("Username:", user,true);
        this.sendCommand("Password:", pass, true);
        
    }
    
    /**
     * METODO QUE SE ENCARGA DE LEER LINEA POR LINEA LA CONSOLA DEL DISPOSITIVO
     * Y ASI SABER DESDE DONDE SE ESCRIBE EL COMANDO
     * @param pattern
     * @return 
     */
    public String readUntil(String pattern) {
        try {
            char lastChar = pattern.charAt(pattern.length() - 1);
            StringBuffer sb = new StringBuffer();
            //sb.
            boolean found = false;
            char ch = (char) in.read();
            while (true) {
                //System.out.print(ch);
                sb.append(ch);
                if (ch == lastChar) {
                    if (sb.toString().endsWith(pattern)) {
                        return sb.toString();
                    }
                }
                ch = (char) in.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * METODO QUE ESCRIBE SOBRE LA CONSOLA DEL DISPOSITIVO EL COMANDO QUE SE LE
     * ENVIA
     * @param value 
     */
    public void write(String value) {
        try {
            out.println(value);
            out.flush();
            //System.out.println(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * METODO GENERAL QUE ENGLOBA A READUNTIL Y WRITE PARA ENVIAR EL COMANDO
     * DESEADO
     * @param prompt_
     * @param command_or_data
     * @param auth
    **/
    public void sendCommand(String prompt_,String command_or_data, Boolean auth) {
        
//        /*UTILIZAMOS LAS VARIABLES LA INSERCION DE CONMANDOS
//        AL DISPOSITIVO*/
//        in = telnet.getInputStream();
//        out = new PrintStream(telnet.getOutputStream());
        
        try{
            readUntil(prompt_);
            write(command_or_data);
        }catch(Exception e){
            System.out.println("Error al enviar el comando");
        }   
    }
    
    public Boolean getStatusConnection(){
        return this.state_telnet_con;
    }
}
