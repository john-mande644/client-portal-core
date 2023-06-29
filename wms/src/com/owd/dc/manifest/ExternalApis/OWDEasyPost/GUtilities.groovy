package com.owd.dc.manifest.ExternalApis.OWDEasyPost

/**
 * Created by danny on 7/13/2017.
 */
class GUtilities {

    public static void main(String[] args){

        try{
           // System.out.println(getZPLFromUrl("https://easypost-files.s3-us-west-2.amazonaws.com/files/postage_label/20170630/8eb0163d432a4117ad2293f989a7a3b8.zpl"))


        }catch (Exception e){
            e.printStackTrace()
        }


    }


    public static String getZPLFromUrl(String url) throws Exception{
        def theUrl = url.toURL();

        return theUrl.text;




    }
}
