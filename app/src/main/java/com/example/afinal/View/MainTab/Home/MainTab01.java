package com.example.afinal.View.MainTab.Home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.afinal.R;
import com.example.afinal.ViewModel.WBStatusListViewModel;
import com.example.afinal.ViewModel.WBStatusViewModel;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.List;

public class MainTab01 extends Fragment implements View.OnClickListener {

    private View thisView;
    private LinearLayoutManager myLinearLayoutManager;
    private LRecyclerView myRecyclerView;
    private MyRecycleViewAdapter myRecycleViewAdapter;
    private LRecyclerViewAdapter lRecyclerViewAdapter;
    private WBStatusListViewModel listViewModel= new WBStatusListViewModel();;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_home,container,false);

        setupUI();



        return thisView;


    }
    public void getStatusList(boolean isPullup){


        @SuppressLint("HandlerLeak") final Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                listViewModel.statusList = (List<WBStatusViewModel>) msg.obj;
                myRecycleViewAdapter.vieModelList = listViewModel.statusList;
                myRecyclerView.refreshComplete(1);
                lRecyclerViewAdapter.notifyDataSetChanged();
            }
        };

        listViewModel.getStatusList(isPullup,handler);
    }


    private void setupUI() {

        myRecyclerView = thisView.findViewById(R.id.rv_status);
        myLinearLayoutManager = new LinearLayoutManager(thisView.getContext());
        //StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        myRecyclerView.setLayoutManager(myLinearLayoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //初始化适配器
        myRecycleViewAdapter = new MyRecycleViewAdapter(listViewModel.statusList);
        lRecyclerViewAdapter = new LRecyclerViewAdapter(myRecycleViewAdapter);
        //设置适配器
        myRecyclerView.setAdapter(lRecyclerViewAdapter);

        myRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                getStatusList(false);

            }
        });
        myRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //上拉刷新
                getStatusList(true);
            }
        });



    }


    @Override
    public void onClick(View v) {

    }

}
