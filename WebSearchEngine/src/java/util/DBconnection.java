/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author emmafsy
 */

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
public class DBconnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "4119503";
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 3307;
    private static final String DB_NAME = "Web";
    private Statement stm = null;
    private PreparedStatement prestm = null;
    private Connection connection = null;
    
    public DBconnection(){
        try{
            String dbURL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
            stm = connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public DBconnection (String sql){
        try{
            String dbURL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, USERNAME, PASSWORD);
            this.prepareStatement(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void prepareStatement(String sql){
        try{
            prestm = connection.prepareStatement(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public Connection getConnection(){
        return connection;
    }
    public void setString(int index, String value){
        try{
            prestm.setString(index, value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setInt(int index, int value){
        try{
            prestm.setInt(index, value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void setBoolean(int index,boolean value)
    { 
        try
        {
            prestm.setBoolean(index,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setDate(int index,Date value) throws SQLException 
    {
        try
        {
            prestm.setDate(index,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setLong(int index,long value) throws SQLException 
    {
        try
        {
            prestm.setLong(index,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setFloat(int index,float value) throws SQLException 
    {
        try
        {
            prestm.setFloat(index,value);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void setBinaryStream(int index,InputStream in,int length) throws SQLException
    {
        try
        {
            prestm.setBinaryStream(index,in,length);
        }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
    public void clearParameters()throws SQLException
    {
        try
        {
            prestm.clearParameters();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public PreparedStatement getPreparedStatement()
    {
        return prestm;
    }
    
    public Statement getStatement() 
    {
        return stm;
    }
    
    public ResultSet executeQuery(String sql)
    {
        try
        {
            if (stm != null) 
                return stm.executeQuery(sql);
            else 
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public ResultSet executeQuery()
    {
        try
        {
            if (prestm != null) 
                return prestm.executeQuery();
            else
                return null;
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void executeUpdate(String sql)
    {
        try
        {
            if (stm != null)
                stm.executeUpdate(sql);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void executeUpdate()
    {
        try
        {
            if (prestm != null)
                prestm.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void close()     
    {
        try
        {
            if (stm != null) 
            {
                stm.close();
                stm = null;
            }
            if (prestm != null) 
            {
                prestm.close();
                prestm = null;
            }
            connection.close();
            connection = null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

