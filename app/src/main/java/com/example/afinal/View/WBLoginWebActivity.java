package com.example.afinal.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.fastjson.JSON;
import com.example.afinal.Model.WBUserAccount;
import com.example.afinal.R;
import com.example.afinal.Tools.NetworkManager;
import com.example.afinal.Tools.WBCommon;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WBLoginWebActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        webView = findViewById(R.id.webview);
        String urlStr = "https://api.weibo.com/oauth2/authorize?client_id="+ WBCommon.WBAppKey+"&redirect_uri="+WBCommon.WBRedirectURI;
        webView.loadUrl(urlStr);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String urlStr = request.getUrl().toString();
                view.loadUrl(urlStr);
                Log.i("woaini",urlStr);

                if(!urlStr.startsWith("http://baidu.com")){
                    return true;
                }

                String code = urlStr.split("=")[1];
                Log.i("code=",code);
                NetworkManager.Shared().getAccessToken(code, new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String body = response.body().string();
                        WBUserAccount userAccount = JSON.parseObject(body,WBUserAccount.class);
                        WBUserAccount.setInstance(userAccount);
                        try {
                            WBUserAccount.Shared().SaveAccount(getFilesDir().getAbsolutePath());
                            Log.i("woaini",getFilesDir().getAbsolutePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                finish();
                return true;
            }

        });

    }
}
