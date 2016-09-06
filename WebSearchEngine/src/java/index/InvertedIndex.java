/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author emmafsy
 */
public class InvertedIndex {
    private Map<String, List<String>> forwardIndexMap;
    private Map<String, Set<String>> invertedIndexMap;
    private ForwardIndex forwardIndex;
    
    public InvertedIndex(){
        forwardIndex = new ForwardIndex();
        forwardIndexMap = forwardIndex.createForwardIndex();
    }
    
    public Map<String, Set<String>> createInvertedIndex(){
        System.out.println("Start creating invertedIndex:");
        invertedIndexMap = new HashMap<>();
        Iterator itr = forwardIndexMap.entrySet().iterator();
        while(itr.hasNext()){
            Map.Entry record = (Map.Entry)itr.next();
            String url = (String)record.getKey();
            List<String> tokens = (List<String>)record.getValue();
            for(String token : tokens){
                if(!invertedIndexMap.containsKey(token)){
                    Set<String> list = new HashSet<>();
                    list.add(url);
                    invertedIndexMap.put(token, list);
                }
                else{
                    invertedIndexMap.get(token).add(url);
                }
            }
        }
        System.out.println("Finished! The size of the invertedIndex is " + invertedIndexMap.size());
        return invertedIndexMap;
    }
    public static void main(String[] args){
        InvertedIndex invertIndex = new InvertedIndex();
        Map<String, Set<String>> indexMap = invertIndex.createInvertedIndex();
        System.out.println(indexMap.get("sports"));
    }
    
}
