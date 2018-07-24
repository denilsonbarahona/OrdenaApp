package com.curso.onbringit.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.curso.onbringit.Adapters.Orders;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 30/8/2017.
 */

public class TrackingActivity extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.activity_ordes, container , false);

        RecyclerView OrdersRv = (RecyclerView) Rootview.findViewById(R.id.ng_rvOrders);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        Orders orders = new Orders();
        OrdersRv.setAdapter(orders);
        OrdersRv.setLayoutManager(linearLayoutManager);

        return Rootview;
    }
}
