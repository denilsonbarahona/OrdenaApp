package com.curso.onbringit.Adapters;

import android.content.ClipData;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 16/11/2017.
 */

public class ItemsInOrder extends RecyclerView.Adapter<ItemsInOrder.HolderItems> {

    private ArrayList<String[]> ItemsInOrder;

    public ItemsInOrder(){
        ItemsInOrder = new ArrayList<>();
    }

    public void AddItems(ArrayList<String[]> ItemsArray){
        this.ItemsInOrder = ItemsArray;
    }

    @Override
    public ItemsInOrder.HolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_delivery_item ,parent,false);
        HolderItems holderItems = new HolderItems(view);
        return holderItems;
    }

    @Override
    public void onBindViewHolder(ItemsInOrder.HolderItems holder, int position) {
        holder.ngItemD.setText(String.valueOf(position + 1)+". "+ItemsInOrder.get(position)[0]);
        holder.ngItemLenght.setText(ItemsInOrder.get(position)[1]);
        holder.ngItemAdress.setText(ItemsInOrder.get(position)[3]);
        holder.ngItemNote.setText(ItemsInOrder.get(position)[2]);
        holder.ngLyActions.setVisibility(View.GONE);
        holder.ngItemAttached.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.ItemsInOrder.size();
    }

    public class HolderItems extends RecyclerView.ViewHolder {

        public TextView ngItemD;
        public TextView ngItemLenght;
        public TextView ngItemAttached;
        public TextView ngItemAdress;
        public TextView ngItemNote;
        public ImageView ngImageAttach;
        public ImageView ngImageDelete;
        public LinearLayout ngLyActions;


        public HolderItems(View itemView) {
            super(itemView);

            ngItemD = (TextView) itemView.findViewById(R.id.ng_ItemD);
            ngItemLenght = (TextView) itemView.findViewById(R.id.ng_ItemsLenght);
            ngItemAttached = (TextView) itemView.findViewById(R.id.ng_ItemAttached);
            ngItemAdress = (TextView)itemView.findViewById(R.id.ng_itemAdress);
            ngItemNote = (TextView)itemView.findViewById(R.id.ng_itemNote);
            ngImageAttach = (ImageView) itemView.findViewById(R.id.ng_Attach);
            ngImageDelete = (ImageView) itemView.findViewById(R.id.ng_Delete);
            ngLyActions = (LinearLayout) itemView.findViewById(R.id.ng_lyActions);
        }


    }
}
