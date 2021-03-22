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
public class MappingRow {
    String groupname;
    String naicomname;
    String omnname;

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getNaicomname() {
        return naicomname;
    }

    public void setNaicomname(String naicomname) {
        this.naicomname = naicomname;
    }

    public String getOmnname() {
        return omnname;
    }

    public void setOmnname(String omnname) {
        this.omnname = omnname;
    }

    @Override
    public String toString() {
        return "MappingRow{" + "groupname=" + groupname + ", naicomname=" + naicomname + ", omnname=" + omnname + '}';
    }
    
    
    
}
