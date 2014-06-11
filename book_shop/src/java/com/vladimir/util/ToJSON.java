package com.vladimir.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import org.owasp.esapi.ESAPI;


/**
 *This utility will convert a database data into json format.
 * 
 * @author Vladimir
 */
public class ToJSON {
    
    /**
     * This will convert database records into a JSON array.
     * Simply pass a ResultSet from a database connection and it will loop 
     * through and return a JSON array
     * 
     * @param rs - database ResultSet
     * @return - JSON array
     * @throws Exception
     */
    public JSONArray toJSONArray(ResultSet rs) throws Exception {
    
        JSONArray json = new JSONArray();
        String temp = null;
        
        try {
        
//            get column names
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            
//            loop through each row of the ResultSet
            while( rs.next() ) {
            
//                get number of columns
                int numColumns = rsmd.getColumnCount();
                
//                each row  in the ResultSet convert to a JSON Object
                JSONObject obj = new JSONObject();
                
//                loop through all the columns and place them into the JSON object
                for(int i = 1; i < numColumns + 1; i++) {
                
                    String columnName = rsmd.getColumnName(i);
                    
                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        obj.put(columnName, rs.getArray(columnName));
                        /*Debug*/ System.out.println("ToJSON: ARRAY");
                    }
                    else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        obj.put(columnName, rs.getInt(columnName));
                        /*Debug*/ System.out.println("ToJSON: BIGINT");
                    }
                    else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        obj.put(columnName, rs.getBoolean(columnName));
                        /*Debug*/ System.out.println("ToJSON: BOOLEAN");
                    }
                    else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        obj.put(columnName, rs.getBlob(columnName));
                        /*Debug*/ System.out.println("ToJSON: BLOB");
                    }
                    else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        obj.put(columnName, rs.getDouble(columnName));
                        /*Debug*/ System.out.println("ToJSON: DOUBLE");
                    }
                    else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        obj.put(columnName, rs.getFloat(columnName));
                        /*Debug*/ System.out.println("ToJSON: FLOAT");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.INTEGER){
                        obj.put(columnName, rs.getInt(columnName));
                        /*Debug*/ System.out.println("ToJSON: INTEGER");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
                        obj.put(columnName, rs.getNString(columnName));
                        /*Debug*/ System.out.println("ToJSON: NVARCHAR");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
                        
                        // prevent cross-site scripting
                        temp = rs.getString(columnName);
                        temp = ESAPI.encoder().canonicalize(temp);
                        temp = ESAPI.encoder().encodeForHTML(temp);
                        obj.put(columnName, temp);
                        
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.TINYINT){
                        obj.put(columnName, rs.getInt(columnName));
                        /*Debug*/ System.out.println("ToJSON: TINYINT");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
                        obj.put(columnName, rs.getInt(columnName));
                        /*Debug*/ System.out.println("ToJSON: SMALLINT");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.DATE){
                        obj.put(columnName, rs.getDate(columnName));
                        /*Debug*/ System.out.println("ToJSON: DATE");
                    }
                    else if (rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
                        obj.put(columnName, rs.getTimestamp(columnName));
                        /*Debug*/ System.out.println("ToJSON: TIMESTAMP");
                    }
                    else {
                        obj.put(columnName, rs.getObject(columnName));
                        /*Debug*/ System.out.println("ToJSON: Object: " + columnName);
                    }
                } // end for
                
                json.put(obj);
                
            } // end while
            
        } catch(SQLException ex) {
            Logger.getLogger(ToJSON.class.getName()).log(Level.SEVERE, "Could not create a JSON object.", ex);
        }
        
        return json;
    }
}
