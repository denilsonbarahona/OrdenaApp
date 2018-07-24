package com.curso.onbringit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.curso.onbringit.Model.Providers;
import com.curso.onbringit.R;
import com.curso.onbringit.View.ImageInFolder;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by PC-PRAF on 9/7/2017.
 */

public class ImagesFolders extends RecyclerView.Adapter<ImagesFolders.HolderFolderImage> {

    private Context context;
    private ArrayList<String[]> FolderImages = new ArrayList<>();
    private Providers ImageProvider;
    private String item;
    private int Activity;

    public ImagesFolders(Context contexto , String item_position, int activity)
    {
        /*
        *  Activity = 1 , ExDeliveryActivity;
        *  Activity = 2 , ScheduleActivity;
        *  Activity = 4 , ItemScheduleActivity;
        *  Activity = 5 , Attache Image
        * */
        this.Activity = activity;
        this.context = contexto;
        this.ImageProvider = new Providers(this.context);
        this.FolderImages = ImageProvider.ImagesFoldersProvider();
        this.item = item_position;
    }

    @Override
    public ImagesFolders.HolderFolderImage onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ng_attach_item,parent,false);
        HolderFolderImage holderFolderImage = new HolderFolderImage(view);
        return holderFolderImage;
    }

    @Override
    public void onBindViewHolder(ImagesFolders.HolderFolderImage holder, final int position) {
        holder.ngFolderName.setText(FolderImages.get(position)[1]);
        holder.ngImageCount.setText(FolderImages.get(position)[2]);
        Glide.with(context)
                .load(Uri.fromFile(new File(FolderImages.get(position)[0])))
                .into(holder.ngImageView);
        holder.ngImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),ImageInFolder.class);
                intent.putExtra("parent_file",FolderImages.get(position)[0]);
                intent.putExtra("FolderName",FolderImages.get(position)[1]);
                intent.putExtra("item_position", item);
                intent.putExtra("activity" , Activity);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.FolderImages.size();
    }

    public class HolderFolderImage extends RecyclerView.ViewHolder {

        LinearLayout ngImageLayout;
        ImageView ngImageView;
        TextView ngFolderName;
        TextView ngImageCount;

        public HolderFolderImage(View itemView)
        {
            super(itemView);
            ngImageLayout = (LinearLayout) itemView.findViewById(R.id.ng_layout_folder_image);
            ngImageView   = (ImageView) itemView.findViewById(R.id.ng_image_folder);
            ngFolderName  = (TextView) itemView.findViewById(R.id.ng_folder_name);
            ngImageCount  = (TextView) itemView.findViewById(R.id.ng_count_image_folder);
        }
    }
}
