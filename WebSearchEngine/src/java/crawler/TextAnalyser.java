/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;

import util.HtmlParser;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import java.util.Date;
import java.net.InetAddress;

/**
 *
 * @author emmafsy
 */
public class TextAnalyser {
    private HtmlParser hp;
    public TextAnalyser(){}
    
    public ArrayList<URL> analyse(BufferedWriter bfWriter, URL url,String doc){
        hp = new HtmlParser();
        ArrayList<URL> urls = hp.findURLs(doc);
        writeDoc(bfWriter,url,doc);
        return urls;
    }
    public void writeDoc(BufferedWriter bfWriter, URL url, String doc){
        try{
            String urlStr = "url:" + url.toString() + "\n";
            Date date = new Date();
            String dateStr = "date:" + date.toString() + "\n";
                    
            InetAddress ip = InetAddress.getByName(url.getHost());
            String ipstr = "ip:" + ip.toString() + "\n";
            String size = "size:" + doc.length() + "\n";
            
            bfWriter.append(urlStr);
            bfWriter.append(dateStr);
            bfWriter.append(ipstr);
            bfWriter.append(size);
            
            bfWriter.newLine();
            bfWriter.append(doc);
            bfWriter.newLine();
            bfWriter.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
        
    }
    
}
