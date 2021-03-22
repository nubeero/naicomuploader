/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nubeero.utils;

import com.nubeero.data.MappingRow;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FileUtilities {
    
    
        
    public static ArrayList readNaicomJson(String fname){
        
        JSONParser parser=new JSONParser();
     
       ArrayList<MappingRow> res = new ArrayList<MappingRow>();
        try{
                FileReader infile = new FileReader(fname);
                //FileInputStream fis = new FileInputStream(infile); 
                
          try{
                JSONObject obj = (JSONObject) parser.parse(infile);
//                JSONArray objlist  = (JSONArray);
                    JSONArray obj2 = (JSONArray)obj.get("DataGroup");
                    //Get Basic Info
                    for(int x = 0;x < obj2.size();x++){
                        JSONObject item = (JSONObject)obj2.get(x);
                        JSONArray itemlist = (JSONArray)item.get("AttArray");
                        
                        Iterator<JSONObject> attobj = itemlist.iterator();
                        
                        while(attobj.hasNext()){
                            MappingRow row = new MappingRow();
                            row.setGroupname(item.get("GroupName").toString());
                            row.setNaicomname(attobj.next().get("Name").toString());

                         
                           res.add(row);
                           
                             System.out.println(row.toString());
                        }
                        
                    }
                    /*
                    JSONArray basicdata  =  (JSONArray) ((JSONObject)obj2.get(0)).get("AttArray");
                    Iterator<JSONObject> listobj = basicdata.iterator();
                    while(listobj.hasNext()){
                        System.out.println("Item: "+listobj.next().get("Name"));
                    }
                    JSONArray detaildata  =  (JSONArray) ((JSONObject)obj2.get(1)).get("AttArray");
                    JSONArray insuredata  =  (JSONArray) ((JSONObject)obj2.get(2)).get("AttArray");
                        */
          }catch(ParseException pe){
              pe.printStackTrace();
          }
         
        }catch(IOException  io){
            io.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
       return res;
    }
    
    public static void writeJsonToFile(String fname,String jsonString){
         FileWriter fw=null;
      
        try{
            fw  = new FileWriter(fname);
            fw.write(jsonString);
            System.out.println("Successfully wrote: "+"File");
        }catch(IOException ex){
            ex.printStackTrace();
            
        }finally{
            try{
                     fw.flush();
                fw.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
       
        }
    }
    
    
    public static void readJsonFile(String fname){
        
        JSONParser parser=new JSONParser();
     
        
        try{
                FileReader infile = new FileReader(fname);
                //FileInputStream fis = new FileInputStream(infile); 
                
          try{
                JSONObject obj = (JSONObject) parser.parse(infile);
//                JSONArray objlist  = (JSONArray);
                    JSONArray obj2 = (JSONArray)obj.get("DataGroup");
                    //Get Basic Info
                    JSONArray basicdata  =  (JSONArray) ((JSONObject)obj2.get(0)).get("AttArray");
                    //Get Detail Info
                    Iterator<JSONObject> listobj = basicdata.iterator();
                    
                    while(listobj.hasNext()){
                        System.out.println("Item: "+listobj.next().get("Name"));
                    }
                    JSONArray detaildata  =  (JSONArray) ((JSONObject)obj2.get(1)).get("AttArray");
                    //Get Insurere Info
                    JSONArray insuredata  =  (JSONArray) ((JSONObject)obj2.get(2)).get("AttArray");
              
          }catch(ParseException pe){
              pe.printStackTrace();
          }
         
        }catch(IOException  io){
            io.printStackTrace();
        }
       
    }
    
    
    public  String getProperty(String propname){
        String result = "";
        //InputStream input = getClass().getClassLoader().getResourceAsStream("default.properties")
        try(InputStream input = new FileInputStream("default.properties")){
            Properties prop = new Properties();
            if (input == null) {
                System.out.println("Sorry, unable to find default.properties");
                return result;
            }
            prop.load(input);
            result = prop.getProperty(propname);
        }catch(IOException io){
            io.printStackTrace();
        }
       
        return result;
    }
    
    public static void main(String [] args){
        //Read the naicom Json and create the Mapping columns 
        
       // readNaicomJson("CompanyLoadLifeIndividualJson.txt");
     FileUtilities fs = new FileUtilities();
     System.out.println(System.getProperty("user.dir"));
        System.out.println(fs.getProperty("FILEMODE"));
    }
    
    
    
    
    
}
