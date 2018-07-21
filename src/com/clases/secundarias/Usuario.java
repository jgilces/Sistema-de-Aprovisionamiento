/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clases.secundarias;

/**
 *
 * @author Prueba
 */
public class Usuario {
    
    private  String name_pe;
    private  String name_user;
    private  String ip_address;
    private  String pass;
    
    //METODO GETTER
    public String getDato(String data){
        String data_=null;
        
        switch(data.toLowerCase()){
            case "name_pe":
                data_ = name_pe;
                break;
            case "user":
                data_ = name_user;
                break;
            case "pass":
                data_ = pass;
                break;
            case "ip":
                data_ = ip_address;
                break;
        }
        
        return data_;
    }
    
    public void setPE(String pe_){
        this.name_pe=pe_;
    }
    
    public void setUser(String u){
        this.name_user=u;
    }
    
    public void setIP(String ip){
        this.ip_address=ip;
    }
    
    public void setPass(String pass_){
        this.pass=pass_;
    }
}
