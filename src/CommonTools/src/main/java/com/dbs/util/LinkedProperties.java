package com.dbs.util;
import java.util.*;
import java.io.*;

/**
 * Ordered properties implementation
*/

public class LinkedProperties extends Properties{
    private static final long serialVersionUID = 1L;

    private Map<Object, Object> linkMap = new LinkedHashMap<Object,Object>();

    public void clear(){
        linkMap.clear();
    }
    public boolean contains(Object value){
        return linkMap.containsValue(value);
    }
    public boolean containsKey(Object key){
        return linkMap.containsKey(key);
    }
    public boolean containsValue(Object value){
        return linkMap.containsValue(value);
    }
    public Enumeration elements(){
        throw new RuntimeException("Method elements is not supported in LinkedProperties class");
    }
    public Set entrySet(){
        return linkMap.entrySet();
    }
    public boolean equals(Object o){
        return linkMap.equals(o);
    }
    public Object get(Object key){
        return linkMap.get(key);
    }
    public String getProperty(String key) {
        Object oval = get(key); //here the class Properties uses super.get()
        if(oval==null)return null;
        return (oval instanceof String) ? (String)oval : null; //behavior of standard properties
    }
    public boolean isEmpty(){
        return linkMap.isEmpty();
    }
    public  Enumeration keys(){
        Set keys=linkMap.keySet();
        return Collections.enumeration(keys);
    }
    public Set keySet(){
        return linkMap.keySet();
    }
    public void list(PrintStream out) {
        this.list(new PrintWriter(out,true));
    }
    public void list(PrintWriter out) {
        out.println("-- listing properties --");
        for (Map.Entry e : (Set<Map.Entry>)this.entrySet()){
            String key = (String)e.getKey();
            String val = (String)e.getValue();
            if (val.length() > 40) {
                val = val.substring(0, 37) + "...";
            }
            out.println(key + "=" + val);
        }
    }

    public Object put(Object key, Object value){
        return linkMap.put(key, value);
    }
    public int size(){
        return linkMap.size();
    }
    public Collection values(){
        return linkMap.values();
    }

    //for test purpose only
    public static void main(String[] arg)throws Exception{
        Properties p0=new Properties();
        Properties p1=new LinkedProperties();
        p0.put("aaa","111");
        p0.put("bbb","222");
        p0.put("ccc","333");
        p0.put("ddd","444");

        p1.put("aaa","111");
        p1.put("bbb","222");
        p1.put("ccc","333");
        p1.put("ddd","444");

        System.out.println("\n--"+p0.getClass());
        p0.list(System.out);
        p0.store(System.out,"comments");
        p0.storeToXML(System.out,"comments");
        System.out.println(p0.toString());

        System.out.println("\n--"+p1.getClass());
        p1.list(System.out);
        p1.store(System.out,"comments");
        p1.storeToXML(System.out,"comments");
        System.out.println(p1.toString());
    }
}