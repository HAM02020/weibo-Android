package com.example.afinal.ViewModel;

import com.example.afinal.Model.Pic_urls;
import com.example.afinal.Model.WBStatus;

import java.util.ArrayList;
import java.util.List;

public class WBStatusViewModel
{
    public WBStatus status;
    private Pic_urls pic_urls;
    public String retweetedStr;
    public List<String> wap360s;
    public List<String> larges;
    public WBStatusViewModel(WBStatus status)
    {
        pic_urls = new Pic_urls();
        this.status = status;
        //判断是否有转发微博
        boolean isRetweetedStatus = status.retweeted_status!=null ? true : false;

        pic_urls = !isRetweetedStatus? status.pic_urls : status.retweeted_status.pic_urls;
        retweetedStr = !isRetweetedStatus ? "" : status.retweeted_status.text;
        wap360s = new ArrayList<String>();
        larges = new ArrayList<String>();

        for (int i = 0; i < pic_urls.thumbnail_pic.size(); i++)
        {
            String wap360 = pic_urls.thumbnail_pic.get(i).replace("/thumbnail/", "/wap360/");
            String large = pic_urls.thumbnail_pic.get(i).replace("/thumbnail/", "/large/");
            wap360s.add(wap360);
            larges.add(large);
        }


    }
}
