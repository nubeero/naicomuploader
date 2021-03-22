/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nubeero.data;

/**
 *
 * @author val
 */
public class Attribute {
    private String Name;
    private String Value;

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String Value) {
        this.Value = Value;
    }

    @Override
    public String toString() {
        return "Attribute{" + "Name=" + Name + ", Value=" + Value + '}';
    }

    
    
}
