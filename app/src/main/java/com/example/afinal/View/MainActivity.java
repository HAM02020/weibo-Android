package com.example.afinal.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.afinal.R;
import com.example.afinal.View.MainTab.Home.MainTab01;
import com.example.afinal.View.MainTab.MainTab02;
import com.example.afinal.View.MainTab.MainTab03;
import com.example.afinal.View.MainTab.MainTab04;
import com.example.afinal.Model.WBUserAccount;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity  implements View.OnClickListener {


    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<Fragment>();

    private RadioGroup rg_group;


    MainTab01 tab01;
    MainTab02 tab02;
    MainTab03 tab03;
    MainTab04 tab04;


    @SuppressLint("HandlerLeak")
    public Handler homePressHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            new AlertDialog.Builder(getBaseContext()).setTitle("真的要退出吗？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("woaini","reeeeeeeeeee");
        //登陆后进行刷新
        if(!WBUserAccount.Shared().isLogin()){
            return;
        }
        WBUserAccount.setInstance(WBUserAccount.initFromFile(getFilesDir().getAbsolutePath()+"/"+WBUserAccount.fileName));
        tab01.getStatusList(true);
    }

    @Override
    public void onBackPressed() {
        String text = "";
        text = "确认退出吗？";
        new AlertDialog.Builder(this)
                .setTitle(text)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupMainView();

        if(Files.exists(Paths.get(getFilesDir().getAbsolutePath()+"/"+WBUserAccount.fileName))){
            WBUserAccount.setInstance(WBUserAccount.initFromFile(getFilesDir().getAbsolutePath()+"/"+WBUserAccount.fileName));
            Log.i("woaini",WBUserAccount.Shared().uid);
            Log.i("woaini",getFilesDir().getAbsolutePath());
            tab01.getStatusList(true);
        }
        else{
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),WBLoginWebActivity.class);
            startActivity(intent);
        }





    }




    private void setupMainView()
    {
        rg_group = findViewById(R.id.rg_group);

        findViewById(R.id.rb_home).setOnClickListener(this);
        findViewById(R.id.rb_discover).setOnClickListener(this);
        findViewById(R.id.rb_message).setOnClickListener(this);
        findViewById(R.id.rb_profile).setOnClickListener(this);


        tab01 = new MainTab01();
        tab02 = new MainTab02();
        tab03 = new MainTab03();
        tab04 = new MainTab04();
        mFragments.add(tab01);
        mFragments.add(tab02);
        mFragments.add(tab03);
        mFragments.add(tab04);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        {

            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0)
            {
                return mFragments.get(arg0);
            }
        };

        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            private int currentIndex;

            @Override
            public void onPageSelected(int position)
            {
                RadioButton r;
                switch (position)
                {
                    case 0:
                        r =  findViewById(R.id.rb_home);
                        r.setChecked(true);
                        break;
                    case 1:
                        r =  findViewById(R.id.rb_discover);
                        r.setChecked(true);
                        break;
                    case 2:
                        r =  findViewById(R.id.rb_message);
                        r.setChecked(true);
                        break;
                    case 3:
                        r =  findViewById(R.id.rb_profile);
                        r.setChecked(true);
                        //rg_group.setBackgroundColor(Color.parseColor("#34b78a"));
                        break;
                }

                currentIndex = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2)
            {

            }

            @Override
            public void onPageScrollStateChanged(int arg0)
            {
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_home:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_discover:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rb_message:
                mViewPager.setCurrentItem(2);
                break;
            case R.id.rb_profile:
                mViewPager.setCurrentItem(3);
                break;
        }

    }
}