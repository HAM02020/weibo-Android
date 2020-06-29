package com.example.afinal.Model;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;


import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WBUserAccount {
    private static WBUserAccount instance = new WBUserAccount();
    public static WBUserAccount Shared()
    {
        return instance;
    }

    public static final String fileName = "userAccount.json";

    public static void setInstance(WBUserAccount instance) {
        WBUserAccount.instance = instance;
    }

    public boolean isLogin(){
        return  access_token != null;
    }

    public String access_token ;
        
    public String uid ;
        
    public String name ;
    //用户头像地址 180*180
        
    public String avatar_large ;

    private WBUserAccount()
    {


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public WBUserAccount(Context context){
        if(Files.exists(Paths.get(context.getFilesDir().getAbsolutePath() + "/" + fileName))){

        }
    }
    public void SaveAccount(String path) throws JSONException {
        JSONObject result = new JSONObject();
        result.put("access_token",access_token);
        result.put("uid",uid);
        try {
            writeFile(path+"/"+fileName,result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 写文件到.Json
     * @param filePath
     * @param sets
     * @throws IOException
     */
    public void writeFile(String filePath, String sets)
            throws IOException {
        FileWriter fw = new FileWriter(filePath);
        PrintWriter out = new PrintWriter(fw);
        out.write(sets);
        out.println();
        fw.close();
        out.close();
    }

    /**
     * 读Json文件
     * @param path
     * @return
     */
    public static WBUserAccount initFromFile(String path) {
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr = laststr + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return JSON.parseObject(laststr,WBUserAccount.class);
    }
}
