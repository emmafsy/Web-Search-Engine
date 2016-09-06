/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
/**
 *
 * @author emmafsy
 */
public class Distributer {
    private static Queue<URL> urlPool = new LinkedList<URL>();
    private static ArrayList<URL> visited = new ArrayList<URL>();
    
    public Distributer(Queue<URL> urlPool){
        this.urlPool = urlPool;
    }
    public synchronized URL getURL(){
        while(urlPool.isEmpty()){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        this.notify();
        URL url = urlPool.poll();
        visited.add(url);
        return url;
        
    }
    public synchronized void addURL(URL url){
        if(!visited.contains(url) && !urlPool.contains(url)){
            urlPool.offer(url);
        }
    }
    public synchronized void addURL(ArrayList<URL> urls){
        for(URL url : urls){
            if(!visited.contains(url) && !urlPool.contains(url)){
                urlPool.offer(url);
            }
        }
    }
    
}
