package com.example.afinal.View.MainTab.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.afinal.R;

import com.example.afinal.ViewModel.WBStatusViewModel;

import java.util.List;

public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyTVHolder> {

    public List<WBStatusViewModel> vieModelList;
    private Context context;
    public MyRecycleViewAdapter(List<WBStatusViewModel> vieModel) {
        this.vieModelList = vieModel;
    }

    @NonNull
    @Override
    public MyTVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyTVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.status,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyTVHolder holder, int position) {
        WBStatusViewModel viewModel = vieModelList.get(position);
        boolean isRetweetStatus = viewModel.isRetweetedStatus;
        int pic_count = viewModel.wap360s.size();
        //头像
        Glide.with(context).load(viewModel.status.user.profile_image_url).into(holder.iv_avatar);
        holder.tv_name.setText(viewModel.status.user.screen_name);
        holder.tv_text.setText(viewModel.status.text);
        holder.tv_timeago.setText(viewModel.status.created_at);

        if(!isRetweetStatus){
            holder.retweetedLayout.setVisibility(View.INVISIBLE);

        }else {
            holder.retweetedLayout.setVisibility(View.VISIBLE);
            holder.tv_retweetedname.setText("@"+viewModel.status.retweeted_status.user.screen_name);
            holder.tv_retweetedText.setText(viewModel.status.retweeted_status.text);
        }
        setupPics(holder,pic_count,position);
    }

    private void setupPics(@NonNull MyTVHolder holder,int pic_count,int position){
        WBStatusViewModel viewModel = vieModelList.get(position);

        GridLayout pics = viewModel.isRetweetedStatus? holder.gd_retweeted_pics:holder.gd_pics;
        ImageView single_pic = viewModel.isRetweetedStatus? holder.iv_retweeted_single:holder.iv_single;
        if(pic_count == 0){
            return;
        }
        else if(pic_count==1){
            pics.setVisibility(View.INVISIBLE);
            Glide.with(context).load(viewModel.wap360s.get(0)).into(single_pic);
        }else{
            pics.setVisibility(View.VISIBLE);
            single_pic.setVisibility(View.INVISIBLE);
            pics.setUseDefaultMargins(true);

            int row=0;
            int column=0;
            if(pic_count == 4){
                pics.setRowCount(2);
                pics.setColumnCount(2);
            }else if(pic_count<=9){
                pics.setRowCount(3);
                pics.setColumnCount(3);
            }

            for(int i = 0;i<viewModel.wap360s.size();i++){
                if(pic_count!=4){
                    column = i%3;
                    if(i<3)
                        row=0;
                    if(i>=3&&i<6)
                        row=1;
                    if(i>=6&&i<9)
                        row=2;
                }else {
                    column = i%2;
                    row = i<2?0:1;
                }

                ImageView iv = new ImageView(context);
                iv.setMaxHeight(300);
                iv.setAdjustViewBounds(true);
                iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                Glide.with(context).load(viewModel.wap360s.get(i)).into(iv);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, 1.0f),GridLayout.spec(column, 1.0f));
                pics.addView(iv,params);
            }
        }
    }


    @Override
    public int getItemCount() {
        return vieModelList.size();
    }

    class MyTVHolder extends RecyclerView.ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_timeago;
        TextView tv_text;
        ImageView iv_single;
        GridLayout gd_pics;

        //被转发微博
        RelativeLayout retweetedLayout;
        TextView tv_retweetedname;
        TextView tv_retweetedText;
        ImageView iv_retweeted_single;
        GridLayout gd_retweeted_pics;

        MyTVHolder(View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_timeago = itemView.findViewById(R.id.tv_timeAgo);
            tv_text = itemView.findViewById(R.id.tv_text);
            iv_single = itemView.findViewById(R.id.iv_singlePic);
            gd_pics = itemView.findViewById(R.id.gd_pics);

            retweetedLayout = itemView.findViewById(R.id.layout_retweeted);
            tv_retweetedname = itemView.findViewById(R.id.tv_retweetedName);
            tv_retweetedText = itemView.findViewById(R.id.tv_retweetedText);
            iv_retweeted_single = itemView.findViewById(R.id.iv_retweeted_singlePic);
            gd_retweeted_pics = itemView.findViewById(R.id.gd_retweetedpics);



        }
    }

}
