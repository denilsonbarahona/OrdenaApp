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
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

/**
 * Created by PC-PRAF on 30/8/2017.
 */

public class OrdersActivity extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View Rootview = inflater.inflate(R.layout.activity_ordes, container , false);

        RecyclerView OrdersRv = (RecyclerView) Rootview.findViewById(R.id.ng_rvOrders);
        ApiRequest apiRequest = new ApiRequest(this.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        Orders orders = new Orders(1 , this.getActivity());
        OrdersRv.setAdapter(orders);
        OrdersRv.setLayoutManager(linearLayoutManager);
        apiRequest.get_Orders(orders ,1 );

        return Rootview;
    }


}
