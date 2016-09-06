/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import index.InvertedIndex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import util.LuceneUtil;

/**
 *
 * @author emmafsy
 */
public class Response {
    private ResultGetter resultGetter;
    private Map<String, Set<String>> invertedIndexMap;
    private List<Result> results;
    private Analyzer analyzer;
    private InvertedIndex invertedIndex;
    
    public Response(){
        invertedIndex = new InvertedIndex();
        invertedIndexMap = invertedIndex.createInvertedIndex();
        analyzer = new StopAnalyzer();
        resultGetter = new ResultGetter();
    }
    public List<Result> getResponse(String request){
        results = new ArrayList<Result>();
        List<String> keyWords = LuceneUtil.tokenizeString(analyzer, request);
        Set<String> tmp = new HashSet<>();
        List<String> urls = new ArrayList<>();
        for(String word : keyWords){
            tmp = invertedIndexMap.get(word);
            if(tmp!=null){
                urls = mergeURL(urls, tmp);
            }
        }
        try{
            if(urls.size()!= 0){
                for(String url : urls){
                    Result r = resultGetter.getResult(url);
                    if(r!=null){
                        results.add(r);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return results;
    }
    public List<String> mergeURL(List<String> urls, Set<String> set){
        List<String> result = new ArrayList<>();
        
        if(urls == null || urls.size() == 0){
            result.addAll(set);
            return result;
        }
        for(String url : set){
            if(urls.contains(url)){
                result.add(url);
            }
        }
        return result;
        
        
    }
    public static void main(String[] args) {

		Response response = new Response();
		List<Result> results = response.getResponse("stock");
		
		System.out.println("Start searching");
		for(Result result : results)
		{
			System.out.println(result.getTitle());
			System.out.println(result.getDescription());
			System.out.println(result.getUrl());
		}
		
	}
}
