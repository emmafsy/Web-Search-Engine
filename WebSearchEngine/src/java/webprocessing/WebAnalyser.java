/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webprocessing;
import util.DBconnection;
import util.MD5;
import java.util.ArrayList;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author emmafsy
 */
public class WebAnalyser {
    private DBconnection db;
    private int offset;
    private MD5 md5;
    private String root;
    private Page page;
    private int count = 0;
    
    public WebAnalyser(){}
    public WebAnalyser(String rootDirectory){
        this.root = rootDirectory;
        page = new Page();
        md5 = new MD5();
        db = new DBconnection();
    }
    
    public void createIndex(){
        ArrayList<String> fileNames = getSubFiles(root);
        for(String file : fileNames){
            //System.out.println(fileNames);
            createIndex(file);
        }
        
    }
    public void createIndex(String file){
        try{
            FileReader reader = new FileReader(file);
            BufferedReader bfr = new BufferedReader(reader);
            String line;
            offset = 0;
            int oldOffset = 0;
            
            while((line = bfr.readLine())!=null){
                oldOffset = offset;
                offset += line.length() + 1;
                
                String url = line.substring(line.indexOf(":")+1, line.length());
                
                int index = url.length() - 1;
                while(index >= 0 && url.charAt(index) == '\\'){
                    url = url.substring(0, index);
                    index--;
                }
                
                System.out.println(url);
                count++;
                skiplines(bfr);
                
                String content = readContent(bfr);
                
                String contentMD5 = md5.getMD5ofStr(content);
                page.setPage(url, oldOffset, contentMD5, file);
                page.addDB(db);
                
            }
            bfr.close();
            System.out.println(count);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
    public void skiplines(BufferedReader bfr){
        try{
            String tmp;
            while(!(tmp = bfr.readLine()).trim().isEmpty()){
                //System.out.println(tmp);
                offset = offset + tmp.length() + 1;
            }
            offset+=1;
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public String readContent(BufferedReader bfr){
        StringBuffer sb = new StringBuffer();
        try{
            String line;
            while((line = bfr.readLine())!=null){
                offset += line.length() + 1;
                if(line.trim().isEmpty()){
                    break;
                }
                else{
                    sb.append(line + "\n");
                }
            }
            offset += 1;
        }catch(IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
    
    public ArrayList<String> getSubFiles(String file){
        File rootFile = new File(file);
        String path = rootFile.getAbsolutePath();
        ArrayList<String> files = new ArrayList<String>();
        if(!rootFile.exists()){
            System.out.println("The provided directory does not exist");
        }
        String[] subs = rootFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name){
                return !name.equals(".DS_Store");
            }
        });
        for(String sub : subs){
            files.add(file + "/" + sub);
            //System.out.println(sub);
        }
        return files;
    }
    
    public String getContent(String fileName, int offset){
        BufferedReader bfReader = null;
        String content = "";
        try{
            FileReader fileReader = new FileReader(fileName);
            bfReader = new BufferedReader(fileReader);
            bfReader.skip(offset);
            bfReader.readLine();
            skiplines(bfReader);
            content = readContent(bfReader);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            if(bfReader!=null){
                try{
                    bfReader.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content;
            
    }
    public Page getRawPage(String url){
        String query = "select * from pages where url='" + url + "'";
        ResultSet rs = db.executeQuery(query);
        
        String content = "";
        String raw = "";
        int offset = 0;
        try{
            while(rs.next()){
                content = rs.getString("content");
                offset = Integer.parseInt(rs.getString("offset"));
                raw = rs.getString("raw");
            }
            return new Page(url, offset, content, raw);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
        
    }
    
    public static void main(String[] args){
        WebAnalyser analyser = new WebAnalyser("RawFiles");
        analyser.createIndex();
    }
       
}
    

