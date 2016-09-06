/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import util.DBconnection;
import webprocessing.Page;
import webprocessing.WebAnalyser;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import util.LuceneUtil;
/**
 *
 * @author emmafsy
 */
public class ForwardIndex {
    private DBconnection db;
    private Map<String,List<String>> indexMap;
    private WebAnalyser analyser;
    
    
    public ForwardIndex(){
        db = new DBconnection();
        indexMap = new HashMap<String,List<String>>();
        analyser = new WebAnalyser();
    }
    
    public Map<String, List<String>> createForwardIndex(){
        try{
            List<String> segment = new ArrayList<String>();
            String query = "select * from pages";
            ResultSet rs = db.executeQuery(query);
            String url;
            String file;
            
            int offset = 0;
            System.out.println("Start creating forward index");
            
            while(rs.next()){
                url = rs.getString("url");
                file = rs.getString("raw");
                offset = Integer.parseInt(rs.getString("offset"));
                String content = analyser.getContent(file, offset);
                
                Analyzer analyzer = new StopAnalyzer();
                segment = LuceneUtil.tokenizeString(analyzer, content);
                
                
                indexMap.put(url, segment);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Finished, the size of forward index is " + indexMap.size());
        return indexMap;
    }
    public static void main(String[] args) {
		

		ForwardIndex forwardIndex = new ForwardIndex();
		Map<String, List<String>> indexMap = forwardIndex.createForwardIndex();
		
		for (Iterator iter = indexMap.entrySet().iterator(); iter.hasNext();) {
			
			Map.Entry entry = (Map.Entry) iter.next();    
		    String url = (String) entry.getKey();
		    ArrayList<String> words = (ArrayList<String>) entry.getValue();

		    //System.out.println(url + " : " + words.size());
		}
                //System.out.println(indexMap.get("https://www.washingtonpost.com/news/morning-mix/wp/2016/06/27/super-racist-pool-safety-poster-prompts-red-cross-apology/?tid=pm_pop_b"));
		
	}
    
    
}
