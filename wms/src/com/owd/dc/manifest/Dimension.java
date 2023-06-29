package com.owd.dc.manifest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: danny
 * Date: May 4, 2010
 * Time: 3:27:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class Dimension {

     private String length = "";
     private    String width ="";
     private   String height = "";
    public Dimension(String s){
        load(s);
    }
    private void load(String s){
        List<Float> l = new ArrayList<Float>();
        for(String ss: s.split("[xX]")){
            l.add(Float.parseFloat(ss));
        }
        System.out.println(l);
        Collections.sort(l);
        System.out.println(l);
        width = l.get(0).toString();
        height = l.get(1).toString();
        length = l.get(2).toString();

    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
