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
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;


public class LuceneUtil {
    public LuceneUtil(){}
    
    public static List<String> tokenizeString(Analyzer analyzer, String doc){
        HtmlParser parser = new HtmlParser();
	String text = parser.html2Text(doc);
        List<String> result = new ArrayList<>();
        try{
            TokenStream stream = analyzer.tokenStream(null,new StringReader(text));
            stream.reset();
            while(stream.incrementToken()){
                result.add(stream.getAttribute(CharTermAttribute.class).toString());
            }
            stream.end();
            stream.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        return result;
    }
    
}
    

