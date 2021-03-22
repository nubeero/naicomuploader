/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nubeero.data;

import java.util.ArrayList;

/**
 *
 * @author val
 */
public class NaicomModel {
    String sid;
    String token;
    String type;
    ArrayList <DataGroupObject> DataGroup;

    @Override
    public String toString() {
        return "NaicomModel{" + "sid=" + sid + ", token=" + token + ", type=" + type + ", DataGroup=" + DataGroup + '}';
    }
    
    

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<DataGroupObject> getDataGroup() {
        return DataGroup;
    }

    public void setDataGroup(ArrayList<DataGroupObject> DataGroup) {
        this.DataGroup = DataGroup;
    }
    
    
}
