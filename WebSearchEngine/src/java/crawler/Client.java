/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
/**
 *
 * @author emmafsy
 */
public class Client {
    BufferedReader reader = null;
    public Client(){}
    public String getHtml(URL url){
        StringBuilder buffer = new StringBuilder();
        try{
            //System.out.println("Start getting html");
            //URLConnection connection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            //connection.setRequestMethod("GET");
            //connection.setDoOutput(true);
            connection.connect();
            
            if(connection.getResponseCode() < 400){
                System.out.println(url.toString());
            
            
            
            InputStreamReader input = new InputStreamReader(connection.getInputStream());
            reader = new BufferedReader(input);
            
            String line = null;
            while((line = reader.readLine())!=null){
                //String line = reader.readLine();
                if(!line.trim().isEmpty()){
                    buffer.append(line + "\n");
                }               
            }    
            }
            
            
                         
            //connection.disconnect();
        
        }catch(MalformedURLException ex){
            System.out.println("Fail to connect to url" + url.toString());
            ex.printStackTrace();
        }catch(IOException e){
            
            e.printStackTrace();
        }finally{
            try{
                if(reader!= null){
                    reader.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        
        return buffer.toString();
    }
    
}
