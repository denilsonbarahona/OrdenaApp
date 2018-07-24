package com.curso.onbringit.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiRequest;
import com.curso.onbringit.R;
import com.curso.onbringit.View.Activity_add_to_cart;

import java.util.ArrayList;

public class ProductsAdapter  extends RecyclerView.Adapter<ProductsAdapter.ProductsHolder> {

    private ArrayList<String[]> Productos;
    private Context context;
    private ApiRequest apiRequest;
    private Activity activity;
    private int option_amount;

    public ProductsAdapter(Context cntx , Activity act){
        this.context = cntx;
        this.apiRequest = new ApiRequest(this.context);
        this.activity = act;
    }

    public void PushProducts(ArrayList<String[]> products){

        this.Productos = products;
        this.notifyDataSetChanged();
    }


    @Override
    public ProductsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_product_item , parent , false);
        ProductsHolder productsHolder = new ProductsHolder(view);
        return productsHolder;
    }


    @Override
    public void onBindViewHolder(ProductsHolder holder, int position) {

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        holder.Product_Name.setText(Productos.get(position)[1]);
        holder.product_price.setText("L. "+Productos.get(position)[2]);

        Glide.with(this.context).load(apiRequest.getUrl()+Productos.get(position)[3]+"/"+Token).asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(holder.product_image);

        holder.Quantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
            }
        });

        holder.Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Activity_add_to_cart.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.Productos.size();
    }



    public class ProductsHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_price;
        TextView Product_Name;
        ToggleButton Quantity;
        LinearLayout Product;

        public ProductsHolder(View itemView) {
            super(itemView);

            product_image = (ImageView) itemView.findViewById(R.id.ng_product_image);
            product_price = (TextView) itemView.findViewById(R.id.ng_product_price);
            Product_Name = (TextView) itemView.findViewById(R.id.ng_product_name);
            Quantity = (ToggleButton) itemView.findViewById(R.id.ng_item_image_selected);
            Product = (LinearLayout) itemView.findViewById(R.id.ng_product);

        }
    }
}
