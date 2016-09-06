/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crawler;
import java.net.URL;
import java.util.Queue;
import java.util.LinkedList;
import java.net.MalformedURLException;
/**
 *
 * @author emmafsy
 */
public class StartCrawler {
    private static final int NUM_THREADS = 5;
    private Queue<URL> urls;
    private Distributer distributer;
    
    public StartCrawler(){}
    
    public StartCrawler(Queue<URL> urls){
        this.urls = urls;
    }
    
    public void start() {
        distributer = new Distributer(urls);
        for(int i = 0; i<NUM_THREADS; i++){
            Thread gather = new Thread(new Gather(String.valueOf(i), distributer));
            gather.start();
        }
    }
    
    public static void main(String[] args){
        Queue<URL> urls = new LinkedList<URL>();
        try{
            urls.offer(new URL("http://www.cnn.com/"));
            urls.offer(new URL("http://www.wsj.com/"));
            urls.offer(new URL("http://www.bloomberg.com/"));
            urls.offer(new URL("https://news.google.com/"));
            urls.offer(new URL("https://www.washingtonpost.com/regional/"));
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        StartCrawler crawler = new StartCrawler(urls);
        crawler.start();
        
        System.out.println("Finished!");
    }
    
    
}
