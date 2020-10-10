package com.example.afinal.View.MainTab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.afinal.R;

public class MainTab04 extends Fragment implements View.OnClickListener {

    private View thisView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisView = inflater.inflate(R.layout.fragment_profile,container,false);
        setupUI();
        return thisView;


    }


    private void setupUI() {


    }


    @Override
    public void onClick(View v) {

    }

}
