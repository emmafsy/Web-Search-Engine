/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;
import java.util.ArrayList;
import java.net.URL;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.MalformedURLException;
/**
 *
 * @author emmafsy
 */
public class HtmlParser {
    public HtmlParser(){};
    
    public ArrayList<URL> findURLs(String doc){
        String str = "<[a|A]\\s+href=([^>]*\\s*>)";
        
        Pattern pattern = Pattern.compile(str, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(doc);
        ArrayList<URL> urls = new ArrayList<URL>();
        
        String tmp;
        while(matcher.find()){
            try{
                tmp = matcher.group();
                tmp = tmp.substring(tmp.indexOf("\"")+1);
                if(!tmp.contains("\""))continue;
                tmp = tmp.substring(0,tmp.indexOf("\""));
                
                if(tmp.startsWith("http"))
                    urls.add(new URL(tmp));
                
            }catch(MalformedURLException e){
                e.printStackTrace();
            }
        }
        return urls;
       
    }
    //remove the tags of html
    public String html2Text(String inputString) 
            
	{    	
		String htmlStr = inputString;
		String textStr ="";    
		Pattern p_script,p_style,p_html,p_filter;    
		Matcher m_script,m_style,m_html,m_filter;      
	          
	    try { 
	    	
	    	String regEx_script = "<script[^>]*?>[\\s\\S]*?</script>";    
	    	
	    	String regEx_style = "<style[^>]*?>[\\s\\S]*?</style>"; 
	    	
	    	String regEx_html = "<[^>]+>";
	        String[] filter = {"&quot;", "&nbsp;"};
	    	
	        p_script = Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);    
	        m_script = p_script.matcher(htmlStr);    
	        htmlStr = m_script.replaceAll(""); 
	   
	        p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);    
	        m_style = p_style.matcher(htmlStr);    
	        htmlStr = m_style.replaceAll(""); 
	           
	        p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);    
	        m_html = p_html.matcher(htmlStr);    
	        htmlStr = m_html.replaceAll("");
	           
	       
	        for(int i = 0; i < filter.length; i++)
	        {
	        	p_filter = Pattern.compile(filter[i],Pattern.CASE_INSENSITIVE);    
	        	m_filter = p_filter.matcher(htmlStr);    
		        htmlStr = m_filter.replaceAll(""); 
	        }
	        
	        textStr = htmlStr;    
	           
	    }catch(Exception e) {    
	       System.err.println("Html2Text: " + e.getMessage());    
	    }    
	          
	    return textStr;
	}
    
}
