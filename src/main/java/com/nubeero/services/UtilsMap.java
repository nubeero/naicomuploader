

package com.nubeero.services;

import java.util.*;


public class UtilsMap {
    
       
        //HashMap<Integer,String> map=new HashMap<Integer,String>();//Creating HashMap    
        
        
        public static void main(String[] args){
             HashMap<Integer,String>maps = new HashMap<Integer,String>();
                maps.put(1,"ALIU");
                maps.put(2,"VENDOR");
                maps.put(3,"PROCESS");
                maps.put(4,"MARKETS");
            
                
                for(Map.Entry m: maps.entrySet()){
                    System.out.println(m.getKey()+" : "+m.getValue());
                }
        }


}
