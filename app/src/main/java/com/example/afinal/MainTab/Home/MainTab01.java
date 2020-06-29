package com.example.afinal.MainTab.Home;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.afinal.R;
import com.example.afinal.Tools.NetworkManager;
import com.example.afinal.Tools.WBHttpMethod;
import com.example.afinal.ViewModel.WBStatusListViewModel;
import com.example.afinal.ViewModel.WBStatusViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainTab01 extends Fragment implements View.OnClickListener {

    private View thisView;
    private LinearLayoutManager myLinearLayoutManager;
    private RecyclerView myRecyclerView;
    private MyRecycleViewAdapter myRecycleViewAdapter;

    private WBStatusListViewModel listViewModel= new WBStatusListViewModel();;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_home,container,false);




        return thisView;


    }
    public void getStatusList(boolean isPullup){


        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                listViewModel.statusList = (List<WBStatusViewModel>) msg.obj;
                setupUI();
            }
        };

        listViewModel.getStatusList(isPullup,handler);
    }


    private void setupUI() {

        myRecyclerView = thisView.findViewById(R.id.rv_status);
        myLinearLayoutManager = new LinearLayoutManager(thisView.getContext());
        myRecyclerView.setLayoutManager(myLinearLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //初始化适配器
        myRecycleViewAdapter = new MyRecycleViewAdapter(listViewModel.statusList);
        //设置适配器
        myRecyclerView.setAdapter(myRecycleViewAdapter);




    }


    @Override
    public void onClick(View v) {

    }

}
