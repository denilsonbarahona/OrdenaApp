package com.curso.onbringit.Adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.curso.onbringit.R;
import com.curso.onbringit.View.AttachActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 8/7/2017.
 */

public class ItemsInDelivery extends RecyclerView.Adapter<ItemsInDelivery.HolderInDelivery> {

    public static ArrayList<String[]> ItemsInDelivery;
    public static ArrayList<ArrayList<String>> AttachInItem;
    private int Activity;
    private int process = 0;

    public ItemsInDelivery(int process , int activity)
    {
        this.Activity = activity;
        this.ItemsInDelivery = new ArrayList<>();
        this.AttachInItem = new ArrayList<>();
        this.process = process;
        // if process == 1 then do adding items to order
        // if process == 2 then do update schedule order
    }

    public String GetItemInDelivery(){
        String Items = "{ Items: [ ";
        for(int x = 0 ; x < this.ItemsInDelivery.size(); x++){
          Items +=" { \"name\": \""+this.ItemsInDelivery.get(x)[0]+"\","+
                  "   \"quantity\": \""+this.ItemsInDelivery.get(x)[1]+"\","+
                  "   \"address_item\": \""+this.ItemsInDelivery.get(x)[3]+"\",";
                  if(this.ItemsInDelivery.get(x).length == 6){
                      Items+=" \"producto_id\": \""+this.ItemsInDelivery.get(x)[5]+"\",";
                  }else{
                      Items+=" \"producto_id\": \"\",";
                  }
          Items+= "   \"note\": \""+this.ItemsInDelivery.get(x)[2]+"\" }";

            if( x < this.ItemsInDelivery.size() - 1){
                Items +=",";
            }
        }

        Items +=" ] }";
        return  Items;
    }

    public void AddNewItem(String NewItem , String Quantity , String Note ,String Adress) {
        this.ItemsInDelivery.add(new String[]{NewItem , Quantity , Note , Adress});
        notifyDataSetChanged();
    }

    public void AddItemsArray(ArrayList<String[]> ItemInOrder){
        this.ItemsInDelivery = ItemInOrder;
        notifyDataSetChanged();
    }

    public void PushItemsArray(ArrayList<String[]> ItemInOrder){
       for(int l = 0; l < ItemInOrder.size(); l++){
           this.ItemsInDelivery.add(ItemInOrder.get(l));
       }
       notifyDataSetChanged();
    }


    public void RefreshItems()
    {
        notifyDataSetChanged();
    }

    @Override
    public ItemsInDelivery.HolderInDelivery onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_delivery_item,parent,false);
        HolderInDelivery holderInDelivery = new HolderInDelivery(view);
        return holderInDelivery;
    }

    @Override
    public void onBindViewHolder(ItemsInDelivery.HolderInDelivery holder, final int position) {
            holder.ngItemD.setText(String.valueOf(position+1)+". "+ItemsInDelivery.get(position)[0]);
            holder.ngItemLenght.setText(ItemsInDelivery.get(position)[1]);
            holder.ngItemAdress.setText(ItemsInDelivery.get(position)[3]);
            holder.ngItemNote.setText(ItemsInDelivery.get(position)[2]);

            if(process == 2){
                holder.ngImageAttach.setImageResource(R.drawable.ic_edit);
            }else{
                holder.ngImageAttach.setImageResource(R.drawable.ic_attach);
            }

            if(AttachInItem.size()>0)
            {
                for(int x = 0; x < AttachInItem.size();x++) {
                    if(AttachInItem.get(x).get(0).equals(String.valueOf(position))) {
                        if(this.process == 2){
                            holder.ngItemAttached.setText("Archívos Adjuntos ( "+String.valueOf((AttachInItem.get(x).size()-1) + Integer.parseInt( ItemsInDelivery.get(position)[4] ))+" )");
                        }else{
                            holder.ngItemAttached.setText("Archívos Adjuntos ( "+String.valueOf(AttachInItem.get(x).size()-1)+" )");
                        }
                        break;
                    }else{
                        if(this.process == 2){
                            holder.ngItemAttached.setText("Archívos Adjuntos ( "+ItemsInDelivery.get(position)[4]+" )");
                        }else{
                            holder.ngItemAttached.setText("Archívos Adjuntos ( 0 )");
                        }
                    }
                }
            }else{
                if(this.process == 2){
                    if(ItemsInDelivery.size()<6){
                        holder.ngItemAttached.setText("Archívos Adjuntos ( 0 )");
                    }else{
                        holder.ngItemAttached.setText("Archívos Adjuntos ( "+ItemsInDelivery.get(position)[4]+" )");
                    }
                }else{
                    holder.ngItemAttached.setText("Archívos Adjuntos ( 0 )");
                }
            }

            if(process != 2)
            {
                holder.ngImageAttach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(), AttachActivity.class);
                        intent.putExtra("item_position",String.valueOf(position));
                        intent.putExtra("activity",Activity);
                        view.getContext().startActivity(intent);
                    }
                });

            }else{

            }

            holder.ngImageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int x = 0; x < AttachInItem.size();x++) {
                        if(AttachInItem.get(x).get(0).equals(String.valueOf(position))) {
                             AttachInItem.remove(x);
                             break;
                        }else if(Integer.valueOf(AttachInItem.get(x).get(0))>position){
                                AttachInItem.get(x).
                                        set(0,String.valueOf(Integer.valueOf(AttachInItem.get(x).get(0))-1));
                        }
                    }
                    ItemsInDelivery.remove(position);
                    notifyDataSetChanged();
                }
            });
    }

    @Override
    public int getItemCount() {
        return ItemsInDelivery.size();
    }

    public class HolderInDelivery extends RecyclerView.ViewHolder {

        public TextView ngItemD;
        public TextView ngItemLenght;
        public TextView ngItemAttached;
        public TextView ngItemAdress;
        public TextView ngItemNote;
        public ImageView ngImageAttach;
        public ImageView ngImageDelete;

        public HolderInDelivery(View itemView) {
            super(itemView);

            ngItemD = (TextView) itemView.findViewById(R.id.ng_ItemD);
            ngItemLenght = (TextView) itemView.findViewById(R.id.ng_ItemsLenght);
            ngItemAttached = (TextView) itemView.findViewById(R.id.ng_ItemAttached);
            ngItemAdress = (TextView)itemView.findViewById(R.id.ng_itemAdress);
            ngItemNote = (TextView)itemView.findViewById(R.id.ng_itemNote);
            ngImageAttach = (ImageView) itemView.findViewById(R.id.ng_Attach);
            ngImageDelete = (ImageView) itemView.findViewById(R.id.ng_Delete);
        }
    }
}
