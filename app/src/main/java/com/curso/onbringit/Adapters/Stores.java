package com.curso.onbringit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.curso.onbringit.View.ActivityProducts;

import java.util.ArrayList;

public class Stores extends RecyclerView.Adapter<Stores.StoresHolder> {


    private Context context;
    private  ArrayList<String[]> Stores= new ArrayList<>();
    private ApiRequest apiRequest;

    public Stores(Context cntx){
        this.context = cntx;
        this.apiRequest = new ApiRequest(this.context);

    }

    public void PushStores(ArrayList<String[]> Stores_){
        this.Stores = Stores_;
        this.notifyDataSetChanged();
    }

    @Override
    public StoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_stores_items,parent, false);
        StoresHolder holder = new StoresHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StoresHolder holder, final int position) {

            SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
            Sql_Query sql_query = new Sql_Query(db,context);
            String Token = sql_query.GetApiToken();

            Glide.with(this.context).load(apiRequest.getUrl()+Stores.get(position)[3]+"/"+Token).asBitmap()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(holder.StoreImage);

            holder.StoreName.setText(Stores.get(position)[1]);
            holder.StoreOwner.setText(Stores.get(position)[2]);
            holder.StoreLy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent product_intent = new Intent(view.getContext() , ActivityProducts.class);
                    product_intent.putExtra("store_id" ,Integer.valueOf( Stores.get(position)[0]) );
                    view.getContext().startActivity(product_intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return this.Stores.size();
    }

    public class StoresHolder extends RecyclerView.ViewHolder {

        ImageView StoreImage;
        TextView  StoreName;
        TextView  StoreOwner;
        LinearLayout StoreLy;

        public StoresHolder(View itemView) {
            super(itemView);

            this.StoreLy = (LinearLayout)itemView.findViewById(R.id.ng_store);
            this.StoreImage = (ImageView) itemView.findViewById(R.id.ng_store_image);
            this.StoreName = (TextView) itemView.findViewById(R.id.ng_store_name);
            this.StoreOwner = (TextView) itemView.findViewById(R.id.ng_store_owner);
        }
    }
}
