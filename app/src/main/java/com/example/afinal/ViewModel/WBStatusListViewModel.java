package com.example.afinal.ViewModel;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.example.afinal.Model.Pic_urls;
import com.example.afinal.Model.WBStatus;
import com.example.afinal.Tools.NetworkManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WBStatusListViewModel {
    public List<WBStatusViewModel> statusList ;

    public WBStatusListViewModel()
    {
        statusList = new ArrayList<WBStatusViewModel>();
    }

    public void getStatusList(final boolean isPullup, final Handler handler)
    {
        Map<String,String> params = new ConcurrentHashMap<>();
        if (statusList.size() != 0)
        {
            String since_id = isPullup ? "0": statusList.get(0).status.idstr;
            String max_id = !isPullup ? "0": statusList.get(statusList.size()-1).status.idstr;
            Long max_id_long = Long.parseLong(max_id);
            max_id_long = max_id_long > 0 ? max_id_long - 1 : 0;
            max_id = max_id_long.toString();
            params.put("since_id",since_id.toString());
            params.put("max_id",max_id.toString());

        }

        //加载微博

        NetworkManager.Shared().tokenRequest("https://api.weibo.com/2/statuses/home_timeline.json", params, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String body = response.body().string();

                JSONObject json = JSON.parseObject(body);
                JSONArray jArray = JSON.parseArray(json.getString("statuses"));

                List<WBStatusViewModel> array = new ArrayList<WBStatusViewModel>();
                Integer i = 0;
                for(Object obj : jArray)
                {
                    JSONObject j = (JSONObject)obj;
                    //把数据 反序列化成模型
                    WBStatus status = JSON.parseObject(j.toJSONString(),WBStatus.class);
//                    if(status.pic_urls == null)
//                        status.thumbnail_pic = new ArrayList<>();
//                    if(status.retweeted_status!=null && status.retweeted_status.thumbnail_pic == null)
//                        status.retweeted_status.thumbnail_pic = new ArrayList<>();
                    Log.i("woaininininini",i.toString());
                    i++;

                    Pic_urls pic_Urls = new Pic_urls();
                    JSONArray jArray1 = JSON.parseArray(j.getString("pic_urls"));
                    for (Object pic_obj : jArray1)
                    {
                        JSONObject jj = (JSONObject) pic_obj;
                        pic_Urls.thumbnail_pic.add(jj.getString("thumbnail_pic"));
                    }
                    status.pic_urls = pic_Urls;

                    //被转发微博
                    if (j.containsKey("retweeted_status"))
                    {
                        Pic_urls retweet_pic_Urls = new Pic_urls();
                        JSONArray jArray2 = JSON.parseArray(j.getJSONObject("retweeted_status").getString("pic_urls"));
                        for(Object retweeted_pic_obj : jArray2)
                        {
                            JSONObject jj = JSON.parseObject(retweeted_pic_obj.toString());
                            retweet_pic_Urls.thumbnail_pic.add(jj.getString("thumbnail_pic"));
                        }
                        status.retweeted_status.pic_urls = retweet_pic_Urls;
                    }

                    //创建WBStatusViewModel
                    WBStatusViewModel viewModel = new WBStatusViewModel(status);

                    //添加到list中
                    array.add(viewModel);


                }
                if (isPullup)
                {
                    statusList.addAll(array);
                }
                else
                {

                    array.addAll(statusList);
                    statusList = array;
                }
                Message msg = new Message();
                msg.obj=statusList;
                handler.sendMessage(msg);
            }
        });

    }
}
