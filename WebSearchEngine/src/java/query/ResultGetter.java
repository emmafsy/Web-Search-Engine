/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import webprocessing.Page;
import webprocessing.WebAnalyser;

/**
 *
 * @author emmafsy
 */
public class ResultGetter {
    private WebAnalyser analyser;
    Pattern p_title, p_meta;
    Matcher m_title, m_meta;
    
    public ResultGetter(){
        analyser = new WebAnalyser();
        String regEx_title = "<title[^>]*?>[\\s\\S]*?</title>"; 
        String regEx_meta = "<meta[\\s\\S]*?>";    
        p_title = Pattern.compile(regEx_title,Pattern.CASE_INSENSITIVE);
	p_meta = Pattern.compile(regEx_meta,Pattern.CASE_INSENSITIVE);
    }
    public Result getResult(String url){
        Page page;
        String title = "";
        String description = "";
        
        page = analyser.getRawPage(url);
        String content = analyser.getContent(page.getRaw(), page.getOffset());
        
        m_title = p_title.matcher(content);
        while(m_title.find()){
            title = m_title.group();
            title = title.substring(title.indexOf(">") + 1, title.lastIndexOf("<"));
            break;
        }
        m_meta = p_meta.matcher(content);
        while(m_meta.find()){
            description = m_meta.group();
            description = description.toLowerCase();
            if(description.contains("description")){
                int begin = description.indexOf("content=") + 9;
                description = String.valueOf(description.subSequence(begin,description.length()));
                int end = description.indexOf("\"");
                description = String.valueOf(description.subSequence(0,end));
                break;
            }
                
        }
        return new Result(title, url, description);
    }
    
}
