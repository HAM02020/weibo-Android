package com.example.afinal.Tools;


import android.os.Handler;
import android.util.Log;

import com.example.afinal.Model.WBUserAccount;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;





public class NetworkManager extends OkHttpClient {



    private Request request;
    private HttpUrl url;


    private static NetworkManager instance = new NetworkManager();
    public static NetworkManager Shared()
    {
        return instance;
    }

    public void request(WBHttpMethod method, String urlStr, Map<String,String> params,Callback callback){
        Request.Builder requestBuilder = new Request.Builder();

        if(method.equals(WBHttpMethod.GET)){
            HttpUrl.Builder urlBuilder = HttpUrl.parse(urlStr).newBuilder();

            if(params!=null){
                for(Map.Entry<String,String> param : params.entrySet()){
                    urlBuilder.addQueryParameter(param.getKey(),param.getValue());
                }
            }

            url = urlBuilder.build();

            requestBuilder.url(url)
                    .get();
        }
        else if(method.equals(WBHttpMethod.POST)){
            //构建表单制造者
            FormBody.Builder formBuilder = new FormBody.Builder();
            //向表单制造者赋值
            for(Map.Entry<String,String> param : params.entrySet()){
                formBuilder.add(param.getKey(),param.getValue());
            }
            //从表单制造者生成body
            RequestBody requestBody = formBuilder.build();
            //request 制造者赋值
            requestBuilder.post(requestBody)
                    .url(urlStr);
        }
        request = requestBuilder.build();
        newCall(request).enqueue(callback);

    }
    public void getAccessToken(String code,Callback callback){
        String urlStr = "https://api.weibo.com/oauth2/access_token";

        Map<String,String> params = new ConcurrentHashMap<>();
        params.put("client_id",WBCommon.WBAppKey);
        params.put("client_secret",WBCommon.WBAPPSecret);
        params.put("grant_type","authorization_code");
        params.put("code",code);
        params.put("redirect_uri",WBCommon.WBRedirectURI);

        request(WBHttpMethod.POST, urlStr, params, callback);
    }
    public void tokenRequest(String url,Map<String,String> params,Callback callback){
        params.put("access_token",WBUserAccount.Shared().access_token);
        request(WBHttpMethod.GET,url,params,callback);
    }
}
