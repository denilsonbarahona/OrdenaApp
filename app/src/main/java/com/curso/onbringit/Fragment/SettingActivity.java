package com.curso.onbringit.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.curso.onbringit.Adapters.Settings;
import com.curso.onbringit.R;


/**
 * Created by PC-PRAF on 2/9/2017.
 */

public class SettingActivity extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.activity_setting,container , false);

        RecyclerView recyclerViewSettings = (RecyclerView)RootView.findViewById(R.id.ng_rvSettings);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        Settings settingsAdapter = new Settings(this.getContext() , getActivity());

        recyclerViewSettings.setAdapter(settingsAdapter);
        recyclerViewSettings.setLayoutManager(linearLayoutManager);

        return RootView;
    }
}
