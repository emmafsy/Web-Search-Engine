/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webprocessing;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import util.DBconnection;

/**
 *
 * @author emmafsy
 */
public class Page {
    private String url;
    private int offset;
    private String content;
    private String raw;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }
    
    public Page(){}
    public Page(String url, int offset, String content, String raw){
        this.url = url;
        this.offset = offset;
        this.content = content;
        this.raw = raw;
    }
    public void setPage(String url, int offset, String content, String raw){
        this.url = url;
        this.offset = offset;
        this.content = content;
        this.raw = raw;
    }
    public void addDB(DBconnection db){
        String sql = "insert into pages(url, offset, content, raw) values ('" 
                + url + "', '" + offset + "', '" + content + "', '" + raw + "')";
        
        db.executeUpdate(sql);
                
    }
    
}
