/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nubeero.services;

import com.nubeero.data.Attribute;
import com.nubeero.data.DataGroupObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.nubeero.data.MappingRow;
import com.nubeero.data.NaicomModel;
import com.nubeero.utils.FileUtilities;
import static com.nubeero.utils.FileUtilities.readNaicomJson;
import static com.nubeero.utils.FileUtilities.writeJsonToFile;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
public class ReadExcelData {
    
    
    
   
    
    public Map getFileName(String fname){
        
         Map<Integer, List<String>> data = new HashMap<>();
        try{
            
            FileInputStream file = new FileInputStream(new File(fname));
            Workbook workbook = new XSSFWorkbook(file);
            //FileInputStream fstream = new FileInputStream(fname);
            
            
            
            Sheet sheet = workbook.getSheetAt(0);

           System.out.println(sheet.getSheetName());
            int i = 0;
            for (Row row : sheet) {
                data.put(i, new ArrayList<String>());
                for (Cell cell : row) {
                    switch (cell.getCellTypeEnum()) {
                        case STRING:  
                           data.get(new Integer(i)).add(cell.getRichStringCellValue().getString()); 
                        break;
                        case NUMERIC:
                            
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data.get(i).add(cell.getDateCellValue() + "");
                            } else {
                                data.get(i).add(cell.getNumericCellValue() + "");
                            }
                        break;
                        case BOOLEAN:
                            data.get(i).add(cell.getBooleanCellValue() + "");

                        break;
                        case FORMULA: 
                            data.get(i).add(cell.getCellFormula() + "");

                        break;
                        
                        default: data.get(new Integer(i)).add(" ");
                        
                    }
                }
                i++;
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
        return data;
    } 
    

    
    public static void processStaging(String jsonfilepath,String excelfilepath){
        try{
            
            
            
           ArrayList<MappingRow> maprows = readNaicomJson(jsonfilepath);
           
           if(maprows.size() > 1){
               FileInputStream fis = new FileInputStream(new File(excelfilepath));
            Workbook wb = WorkbookFactory.create(fis);
            
            //rename to data 
            int mycurrrent = wb.getActiveSheetIndex();
            Sheet datasheet = wb.getSheet("data");
            Sheet mappingsheet = wb.createSheet("mapping");
            Sheet omnsource = wb.createSheet("omnsource");
          // Sheet mappingsheet =
         
             System.out.println("Getting Map Sheet "+mappingsheet.getSheetName());
             System.out.println("Getting Data Sheet "+datasheet.getSheetName());
            int header= datasheet.getFirstRowNum();
            Row rowheader = datasheet.getRow(header);
          
            int rowCnt = 0;
            Row frow = mappingsheet.createRow(rowCnt);
            Cell cell1 = frow.createCell(0);
            
            cell1.setCellValue("GROUPNAME");
            Cell cell2 = frow.createCell(1);
            cell2.setCellValue("NAICOMNAME");
            Cell cell3 = frow.createCell(2);
            cell3.setCellValue("OMNNAME");
            for(int x =0;x<maprows.size();x++){
                //write tthe naicom data into the mappng excel
                Row rowx = mappingsheet.createRow(++rowCnt);
                Cell gncell = rowx.createCell(0);
                        gncell.setCellValue(maprows.get(x).getGroupname());
                        
                 Cell naicomcell = rowx.createCell(1);
                 naicomcell.setCellValue(maprows.get(x).getNaicomname());
            }
           int xCnt=0;
           Row row = omnsource.createRow(0);
           Cell cell = row.createCell(0);
           cell.setCellValue("n/a");
            for(int j = 0; j < rowheader.getLastCellNum();j++){
               row = omnsource.createRow(++xCnt);
               cell = row.createCell(0);
               cell.setCellValue(rowheader.getCell(j).getStringCellValue());
            }
            fis.close();
            FileOutputStream fos = new FileOutputStream(excelfilepath);
            wb.write(fos);
            wb.close();
            fos.close();
           }
            
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
       
    private static void startProcessing(String modeltype,String filePath)
    {
        GsonBuilder gbuilder = new GsonBuilder().setPrettyPrinting();
   
        Gson tableJsonObject =  gbuilder.create();
        ArrayList <DataGroupObject> dataobjectlist = new ArrayList<DataGroupObject>();
     // JsonElement element = tableJsonObject.toJsonTree(attList, new TypeToken<List<Attribute>>() {}.getType());
     //  JsonArray jsonArray = element.getAsJsonArray();
        
        try{
         /* First need to open the file. */
            FileInputStream fis = new FileInputStream(filePath.trim());
   
         /* Create the workbook object to access excel file. */
            //Workbook excelWookBook = new XSSFWorkbook(fInputStream)
         /* Because this example use .xls excel file format, so it should use HSSFWorkbook class. For .xlsx format excel file use XSSFWorkbook class.*/;
            Workbook excelWorkBook = WorkbookFactory.create(fis);
         //Workbook excelWorkBook = new XSSFWorkbook(fInputStream);
            // Get all excel sheet count.
            int totalSheetNumber = excelWorkBook.getNumberOfSheets();
            // Loop in all excel sheet.
         System.out.println("Sheet Number: "+totalSheetNumber);
                // Get current sheet.
                Sheet   mappingsheet = excelWorkBook.getSheet("mapping");
                Sheet   datasheet = excelWorkBook.getSheet("data");
                // Get sheet name.
                System.out.println("Getting Map Sheet "+mappingsheet.getSheetName());
                System.out.println("Getting Data Sheet "+datasheet.getSheetName());
                if(mappingsheet.getSheetName() != null && mappingsheet.getSheetName().length() > 0)
                {
                    
                    // Get current sheet data in a list table.
                    List<List<String>> mappingSheetDataTable = getSheetDataList(mappingsheet);
                    List<List<String>> dataSheetDataTable = getSheetDataList(datasheet);
                    // Generate JSON format of above sheet data and write to a JSON file.
                    
                    for(int t = 1; t < dataSheetDataTable.size();t++){
                                      
                    NaicomModel nmObject = new NaicomModel();
                    DataGroupObject obj1 = getJSONMappingFromData(t,"Basic Info",mappingSheetDataTable,dataSheetDataTable);
                    DataGroupObject obj2 = getJSONMappingFromData(t,"Detail Info",mappingSheetDataTable,dataSheetDataTable);
                    DataGroupObject obj3 = getJSONMappingFromData(t,"Insured Info",mappingSheetDataTable,dataSheetDataTable);
                    dataobjectlist.add(obj1); dataobjectlist.add(obj2); dataobjectlist.add(obj3);
                    nmObject.setDataGroup(dataobjectlist);
                    nmObject.setType(modeltype);
                    nmObject.setSid("922836c8-86f5-4831-9f48-7097ac97982d");
                    nmObject.setToken("GMH8CyNX0kjINiiS9YYxSJ9IcJesl5gt");
                    System.out.println(tableJsonObject.toJson(nmObject));
                    
                        writeJsonToFile("results_"+t+".txt",tableJsonObject.toJson(nmObject));
                    }
                          
            
                }
           
            // Close excel work book object. 
            excelWorkBook.close();
        }catch(Exception ex){
            ex.printStackTrace();
            System.err.println(ex.getMessage());
        }
    }
    
    
    

    
    
    /* 
        Return sheet data in a two dimensional list. 
        Each element in the outer list is represent a row, 
        each element in the inner list represent a column.
        The first row is the column name row.
    */
    private static List<List<String>> getSheetDataList(Sheet sheet)
    {
        List<List<String>> ret = new ArrayList<List<String>>();
        // Get the first and last sheet row number.
        int firstRowNum = sheet.getFirstRowNum();
        int lastRowNum = sheet.getLastRowNum();
        if(lastRowNum > 0)
        {
              int headerowcount = sheet.getRow(0).getLastCellNum();
            // Loop in sheet rows.
            for(int i=firstRowNum; i<lastRowNum + 1; i++)
            {
                 Row row = sheet.getRow(i);
                
                   
                // Get current row object.
               
                // Get first and last cell number.
                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getLastCellNum();
                // Create a String list to save column data in a row.
                //System.out.println("Last Cell "+lastCellNum);
                List<String> rowDataList = new ArrayList<String>();
                // Loop in the row cells.
                for(int j = firstCellNum; j < lastCellNum; j++)
                {
                    
                    try{
                        // Get current cell.
                    Cell cell = row.getCell(j);
                    // System.out.println("Last Column "+j+" Cell"+cell.getCellType());
                    // Get cell type.
                    int cellType = cell.getCellType();
                        if(cellType == CellType.NUMERIC.getCode())
                        {
                            if(HSSFDateUtil.isCellDateFormatted(cell)){
                                Date stringCellDate = cell.getDateCellValue();
                                 rowDataList.add(new SimpleDateFormat("MM/dd/YYYY HH:mm:ss").format(stringCellDate));
                            }else{
                                  double numberValue = cell.getNumericCellValue();
                            // BigDecimal is used to avoid double value is counted use Scientific counting method.
                            // For example the original double variable value is 12345678, but jdk translated the value to 1.2345678E7.
                            String stringCellValue = BigDecimal.valueOf(numberValue).toPlainString();
                            rowDataList.add(stringCellValue);
                            }
                          
                        }else if(cellType == CellType.STRING.getCode())
                        {
                            String cellValue = cell.getStringCellValue();
                            rowDataList.add(cellValue);
                        }else if(cellType == CellType.BOOLEAN.getCode())
                        {
                            boolean numberValue = cell.getBooleanCellValue();
                            String stringCellValue = String.valueOf(numberValue);
                            rowDataList.add(stringCellValue);
                        }else if(cellType == CellType.BLANK.getCode())
                        {
                            rowDataList.add("");
                        } else{
                             rowDataList.add("");
                        }
                    }catch(NullPointerException ne){
                        // rowDataList.add("n/a");
                        continue;
                    }
                 
                }
                // Add current row data list in the return list.
                ret.add(rowDataList);
            }
        }
        return ret;
    }
    
    
    
     /* Return a JSON string from the string list. */
    private static DataGroupObject getJSONMappingFromList(String maptype,List<List<String>> mappingTable,List<List<String>> dataTable)
    {
      
        String ret = "";
        DataGroupObject dataGroupObject = new DataGroupObject();
          ArrayList <Attribute> attList = new ArrayList<Attribute>();
       
        if(mappingTable != null)
        {
            
            int rowCount = mappingTable.size();
            
            if(rowCount > 1)
            {
                
                // The first row is the header row, stre each column name.
                List<String> mappingHeaderRow = mappingTable.get(0);
                int columnCount = mappingHeaderRow.size();
                
                // Loop in the row data list.
                for(int i=1; i<rowCount; i++)
                {
                    List<String> mappingDataRow = mappingTable.get(i);
                    if(mappingDataRow.get(0).equalsIgnoreCase(maptype)){
                    List<String> dataHeaderRow = dataTable.get(0);
                                   Attribute obj = new Attribute();
                                   obj.setName(mappingDataRow.get(1));
                                   obj.setValue("n/a");
                                 
                         for(int a = 0; a < dataHeaderRow.size();a++){
                             
                              if(dataHeaderRow.get(a).equalsIgnoreCase(mappingDataRow.get(2))){
                                  //System.out.println("OMNColumn: "+mappingDataRow.get(1));
                                  //System.out.println("Naicom Column: "+mappingDataRow.get(2));
                                    //  System.out.println("Data Value: "+dataTable.get(1).get(a));
                                   
                                  
                                   //data Table first row.  Change this to loop through remaning rows for final  implementation
                                   //obj.setValue(dataTable.get(u).get(a));
                                   obj.setValue(dataTable.get(1).get(a));
                                   System.out.println(obj);
                                    continue;
                              }
                              
                                
                          }
                         attList.add(obj);
                      
                    } 
                     
                     
                 
                   

                }
                // Return string format data of JSONObject object.
                
            }
        }
         
   
   dataGroupObject.setAttArray(attList);
   dataGroupObject.setGroupCount("0");
   dataGroupObject.setGroupName(maptype);
   dataGroupObject.setGroupTag("0");
    
   System.out.println(dataGroupObject);
   
        return dataGroupObject;
      
    }

    
     private static DataGroupObject getJSONMappingFromData(int datarow,String maptype,List<List<String>> mappingTable,List<List<String>> dataTable)
    {
      
        String ret = "";
        DataGroupObject dataGroupObject = new DataGroupObject();
          ArrayList <Attribute> attList = new ArrayList<Attribute>();
       
        if(mappingTable != null)
        {
            
            int rowCount = mappingTable.size();
            if(rowCount > 1)
            {
                
                // The first row is the header row, stre each column name.
                List<String> mappingHeaderRow = mappingTable.get(0);
                int columnCount = mappingHeaderRow.size();
                
                // Loop in the row data list.
                for(int i=1; i<rowCount; i++)
                {
                    List<String> mappingDataRow = mappingTable.get(i);
                    if(mappingDataRow.get(0).equalsIgnoreCase(maptype)){
                    List<String> dataHeaderRow = dataTable.get(0);
                    List<String> actualDataRow = dataTable.get(datarow);
                                   Attribute obj = new Attribute();
                                   obj.setName(mappingDataRow.get(1));
                                   obj.setValue("n/a");
                                //   System.out.println("Data header Size: "+dataHeaderRow.size());
                                //  System.out.println("Data row "+actualDataRow.size());
                         for(int a = 0; a < dataHeaderRow.size();a++){
                              if(dataHeaderRow.get(a).equalsIgnoreCase(mappingDataRow.get(2)) ){
                                //  System.out.println("Naicom Column: "+mappingDataRow.get(1));
                                // System.out.println("OMN Column: "+mappingDataRow.get(2));
                                //  System.out.println("Data Row: "+(datarow));
                                //  System.out.println("Data Value: "+dataTable.get(datarow).get(a)+" Data Column"+dataHeaderRow.get(a));
                                   //data Table first row.  Change this to loop through remaning rows for final  implementation
                                   //obj.setValue(dataTable.get(u).get(a));
                                   if(a < actualDataRow.size())
                                   obj.setValue(actualDataRow.get(a));
                                   
                                   
                                   System.out.println(obj);
                                   continue;
                              }
                               
                          }
                         attList.add(obj);
                      
                    } 
                     
                     
                 
                   

                }
                // Return string format data of JSONObject object.
                
            }
        }
         
   
   dataGroupObject.setAttArray(attList);
   dataGroupObject.setGroupCount("0");
   dataGroupObject.setGroupName(maptype);
   dataGroupObject.setGroupTag("0");
    
   System.out.println(dataGroupObject);
   
        return dataGroupObject;
      
    }
 
    

    
    
    
    
    public static void main(String[] args){
       FileUtilities fs = new FileUtilities();
        String filemode = fs.getProperty("FILEMODE");
        String modeltype = fs.getProperty("RECORDTYPE");
         String jsonfile = fs.getProperty("INPUTJSON");
            String excelfile = fs.getProperty("INPUTEXCEL");
        if(filemode.equalsIgnoreCase("STAGE")){
            
            System.out.print("STAGING");
           
            processStaging(jsonfile,excelfile);
            
        }else if(filemode.equalsIgnoreCase("PROCESS")){
               System.out.print("PROCESSING");
               startProcessing(modeltype,excelfile);
        }
    

     
        //P@ssw0rd.
        //C@ntg3tme1n
    }
}
