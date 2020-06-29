package com.example.afinal.MainTab.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.afinal.R;
import com.example.afinal.ViewModel.WBStatusListViewModel;
import com.example.afinal.ViewModel.WBStatusViewModel;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyTVHolder> {

    private List<WBStatusViewModel> statuses;

    public MyRecycleViewAdapter(List<WBStatusViewModel> statuses) {
        this.statuses = statuses;
    }

    @NonNull
    @Override
    public MyTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyTVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyTVHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return statuses.size();
    }

    class MyTVHolder extends RecyclerView.ViewHolder {
        //TextView mTextView;

        MyTVHolder(View itemView) {
            super(itemView);
            //mTextView = (TextView) itemView.findViewById(R.id.tv_txt);
        }
    }

}
