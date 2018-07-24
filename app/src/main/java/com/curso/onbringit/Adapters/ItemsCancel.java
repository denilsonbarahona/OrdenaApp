package com.curso.onbringit.Adapters;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 21/11/2017.
 */

public class ItemsCancel extends RecyclerView.Adapter<ItemsCancel.HolderItems>  {

    private ArrayList<String[]> ItemsInOrder;
    private Activity activity;

    public ItemsCancel( Activity activity ){
        ItemsInOrder = new ArrayList<>();
        this.activity = activity;
    }

    public void AddItems(ArrayList<String[]> ItemsArray){
        this.ItemsInOrder = ItemsArray;
    }

    public void remove_item(int position){
        this.ItemsInOrder.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public HolderItems onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_delivery_item_cancel ,parent,false);
        com.curso.onbringit.Adapters.ItemsCancel.HolderItems holderItems = new ItemsCancel.HolderItems(view);
        return holderItems;
    }

    @Override
    public void onBindViewHolder(HolderItems holder, final int position) {
        holder.ngItemD.setText(String.valueOf(position + 1)+". "+ItemsInOrder.get(position)[0]);
        holder.ngItemLenght.setText(ItemsInOrder.get(position)[1]);
        holder.ngItemAdress.setText(ItemsInOrder.get(position)[3]);
        holder.ngItemNote.setText(ItemsInOrder.get(position)[2]);
        holder.ngItemAttached.setText(ItemsInOrder.get(position)[5]);
        holder.ngLyActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display display = activity.getWindowManager().getDefaultDisplay();
                int mwidth = display.getWidth();
                int mheight = display.getHeight();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                View v = LayoutInflater.from(view.getContext()).inflate(R.layout.ng_poup_cancel_item ,null);

                builder.setView(v);
                final AlertDialog dialog = builder.create();
                dialog.show();
                WindowManager.LayoutParams LP = new WindowManager.LayoutParams();
                LP.copyFrom(dialog.getWindow().getAttributes());
                LP.width  = (int)(( mwidth/2) * 2);
                LP.height = (int)(( mheight/2) * 0.9);

                TextView ng_CancelPop = (TextView) v.findViewById(R.id.ng_cancelpop);
                TextView ng_Selected = (TextView) v.findViewById(R.id.ng_schedule_selected);
                final TextView ng_ErrorMessage = (TextView) v.findViewById(R.id.ng_error_message);
                ng_ErrorMessage.setVisibility(View.GONE);
                ng_CancelPop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });

                ng_Selected.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ApiRequest api = new ApiRequest(view.getContext());
                        api.cancel_item( Integer.parseInt( ItemsInOrder.get(position)[6] ) , ng_ErrorMessage ,position ,dialog , ItemsCancel.this );
                    }
                });

                dialog.getWindow().setAttributes(LP);
            }
        });
    }

    @Override
    public int getItemCount() { return this.ItemsInOrder.size();}

    public class HolderItems extends RecyclerView.ViewHolder {

        public TextView ngItemD;
        public TextView ngItemLenght;
        public TextView ngItemAttached;
        public TextView ngItemAdress;
        public TextView ngItemNote;
        public ImageView ngImageDelete;
        public LinearLayout ngLyActions;

        public HolderItems(View itemView) {
            super(itemView);

            ngItemD = (TextView) itemView.findViewById(R.id.ng_ItemD);
            ngItemLenght = (TextView) itemView.findViewById(R.id.ng_ItemsLenght);
            ngItemAttached = (TextView) itemView.findViewById(R.id.ng_ItemAttached);
            ngItemAdress = (TextView)itemView.findViewById(R.id.ng_itemAdress);
            ngItemNote = (TextView)itemView.findViewById(R.id.ng_itemNote);
            ngImageDelete = (ImageView) itemView.findViewById(R.id.ng_Delete);
            ngLyActions = (LinearLayout) itemView.findViewById(R.id.ng_lyActions);
        }
    }
}
