package com.example.afinal.Model;


import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class WBStatus {
    
    public String idstr;
        
    public String text ;
        
    public String created_at;
        
    public Date createdDate ;
    //来源
        
    public String source ;
    //转发评论数
        
    public int reposts_count ;
        
    public int comments_count ;
        
    public int attitudes_count ;
    //用户
        
    public WBUser user ;

    //图片链接
    @JSONField(serialize = false,deserialize = false)
    public Pic_urls pic_urls ;
        
    public WBStatus retweeted_status ;
}
