package com.curso.onbringit.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.curso.onbringit.Model.Providers;
import com.curso.onbringit.R;
import com.curso.onbringit.View.ActivityProfile;
import com.curso.onbringit.View.ActivitySignUp;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by PC-PRAF on 22/7/2017.
 */

public class ImagesInFolder extends RecyclerView.Adapter<ImagesInFolder.HolderInFolder> {

    private static ArrayList<String[]> ImagesinFolder;
    private int Activity;
    private Context Context;
    private String ItemPosition;
    private ArrayList<String[]> ImagesAdded = new ArrayList<>();
    private int CountAttached = 0;

    public ImagesInFolder(Context cntx , String ParentFolder , String item , int Activity)
    {
        this.ImagesinFolder = new ArrayList<>();
        this.Context = cntx;
        Providers provider = new Providers(this.Context);
        this.ImagesinFolder = provider.ImageInFolderProvider(ParentFolder);
        this.ItemPosition = item;
        this.Activity = Activity;

    }


    @Override
    public ImagesInFolder.HolderInFolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_imageinfolder_item,parent,false);
        HolderInFolder holderInFolder = new HolderInFolder(view);
        return holderInFolder;
    }

    @Override
    public void onBindViewHolder(final ImagesInFolder.HolderInFolder holder, final int position) {

        if(ImagesinFolder.get(position)[3]=="1"){
            holder.ng_btn_selected.setChecked(true);
        }else{
            holder.ng_btn_selected.setChecked(false);
        }

        Glide.with(this.Context).
                load(Uri.fromFile(new File(ImagesinFolder.get(position)[0]))).
                into(holder.ng_image);

        holder.ng_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Activity != 5 && Activity != 6)
                {
                    if (holder.ng_btn_selected.isChecked())
                    {
                        holder.ng_btn_selected.setChecked(false);
                        CountAttached = CountAttached -1;
                        ImagesinFolder.get(position)[3] = "0";
                        for (Iterator<String[]> iterator = ImagesAdded.iterator(); iterator.hasNext(); ) {
                            if (iterator.next()[1] == String.valueOf(position)) {
                                iterator.remove();
                            }
                        }

                        for (Iterator<ArrayList<String>> iterator = ItemsInDelivery.AttachInItem.iterator(); iterator.hasNext(); ) {
                            if (iterator.next().get(0) == String.valueOf(position)) {
                                iterator.remove();
                            }
                        }
                    } else {
                        if(CountAttached < 2 )
                        {
                            holder.ng_btn_selected.setChecked(true);
                            ImagesinFolder.get(position)[3] = "1";
                            ImagesAdded.add(new String[]{ImagesinFolder.get(position)[1], String.valueOf(position)});
                            ArrayList<String> ItemAdded = new ArrayList<String>();
                            ItemAdded.add(ItemPosition);
                            ItemAdded.add(ImagesinFolder.get(position)[0]);

                            boolean Newadded = false;

                            if (ItemsInDelivery.AttachInItem.size() > 0) {
                                for (int x = 0; x < ItemsInDelivery.AttachInItem.size(); x++) {
                                    if (ItemsInDelivery.AttachInItem.get(x).get(0).equals(ItemPosition)) {

                                        if(CountAttached == 0 && ItemsInDelivery.AttachInItem.get(x).size()>0){
                                            ItemsInDelivery.AttachInItem.get(x).clear();
                                            ItemsInDelivery.AttachInItem.get(x).add(ItemPosition);
                                            ItemsInDelivery.AttachInItem.get(x).add(ImagesinFolder.get(position)[0]);
                                        }else{
                                            ItemsInDelivery.AttachInItem.get(x).add(ImagesinFolder.get(position)[0]);
                                        }
                                        Newadded = true;
                                        CountAttached = CountAttached + 1;
                                    }
                                }
                                if (!Newadded) {
                                    ItemsInDelivery.AttachInItem.add(ItemAdded);
                                    CountAttached = CountAttached + 1;

                                }
                            } else {
                                ItemsInDelivery.AttachInItem.add(ItemAdded);
                                CountAttached = CountAttached + 1;
                            }
                        }else {
                            Toast.makeText(v.getContext(),"Solo se puede adjuntar 2 imagenes por producto",Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if(Activity == 5){
                        Intent intent = new Intent(v.getContext() , ActivitySignUp.class);
                        intent.putExtra("image_uri",ImagesinFolder.get(position)[0]);
                        v.getContext().startActivity(intent);
                    } else if(Activity == 6){
                        Intent intent = new Intent(v.getContext() , ActivityProfile.class);
                        intent.putExtra("image_uri",ImagesinFolder.get(position)[0]);
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.ImagesinFolder.size();
    }

    public class HolderInFolder extends RecyclerView.ViewHolder {

        ImageView ng_image;
        ToggleButton ng_btn_selected;

        public HolderInFolder(View itemView) {
            super(itemView);

            this.ng_image =  (ImageView) itemView.findViewById(R.id.ng_image_in_folder);
            this.ng_btn_selected = (ToggleButton) itemView.findViewById(R.id.ng_item_image_selected);
        }
    }
}
