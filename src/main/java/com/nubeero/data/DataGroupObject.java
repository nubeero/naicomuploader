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

public class DataGroupObject {
    String GroupName;
    String GroupCount;
    String GroupTag;
    ArrayList<Attribute> AttArray;

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public String getGroupCount() {
        return GroupCount;
    }

    public void setGroupCount(String GroupCount) {
        this.GroupCount = GroupCount;
    }

    public String getGroupTag() {
        return GroupTag;
    }

    public void setGroupTag(String GroupTag) {
        this.GroupTag = GroupTag;
    }

    public ArrayList<Attribute> getAttArray() {
        return AttArray;
    }

    public void setAttArray(ArrayList<Attribute> AttArray) {
        this.AttArray = AttArray;
    }

    @Override
    public String toString() {
        return "DataGroupObject{" + "GroupName=" + GroupName + ", GroupCount=" + GroupCount + ", GroupTag=" + GroupTag + ", AttArray=" + AttArray + '}';
    }

   

    
    
}
