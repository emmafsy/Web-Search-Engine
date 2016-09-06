/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;
import java.io.IOException;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author emmafsy
 */
public class Gather implements Runnable {
    
    private String id;
    private Distributer distributer;
    private Client client;
    private File file;
    private BufferedWriter bfWriter;
    private TextAnalyser analyser;
    
    
    public Gather(String id, Distributer distributer){
        this.id = id;
        this.distributer = distributer;
        createFile();
        client = new Client();
        analyser = new TextAnalyser();
           
    }
    public void createFile(){
        file = new File("RawFiles\\Text" + id + ".txt");
        try{
            file.createNewFile();
            bfWriter = new BufferedWriter(new FileWriter(file));
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void run() {
        int depth = 0;
        
        while(depth++ <= 200){
            URL url = distributer.getURL();
            //System.out.println(url.toString());
            String doc = client.getHtml(url);
            //System.out.println(doc);
            if(doc.length()!=0){
                ArrayList<URL> urls = analyser.analyse(bfWriter, url, doc);
                if(!urls.isEmpty()){
                    //System.out.println(urls);
                    distributer.addURL(urls);
                }
            }
            
        }
    }
    
}
