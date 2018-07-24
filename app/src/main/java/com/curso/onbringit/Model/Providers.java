package com.curso.onbringit.Model;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by PC-PRAF on 9/7/2017.
 */

public class Providers
{
    private Context context;

    public  Providers(Context contexto){
        this.context = contexto;
    }


    public ArrayList<String[]> ImagesFoldersProvider(){

        int countItems=0;
        int allMediaCount = 0;
        ArrayList<String> AddedFolders=new ArrayList<>();
        ArrayList<String[]> ImagesFolders = new ArrayList<>();

        Cursor ImageProvider = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,
                new String[]{MediaStore.Images.Media._ID ,
                        MediaStore.Images.Media.BUCKET_ID ,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME ,
                        MediaStore.Images.Media.DATA
                },
                null    ,
                null    ,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" DESC , "+MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );

        if(ImageProvider.moveToFirst()){
            do{

                allMediaCount++;

                if(!AddedFolders.contains("All Media"))
                {
                    ImagesFolders.add( new String[]{
                            ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.DATA)),
                            "All Media"  ,
                            "0"
                    });
                    AddedFolders.add("All Media");
                }

                if(!AddedFolders.contains(ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_ID))))
                {
                    if(countItems > 0) {
                        ImagesFolders.get(ImagesFolders.size()-1)[2]=String.valueOf(countItems);
                    }
                    ImagesFolders.add( new String[]{
                            ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.DATA)),
                            ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))  ,
                            "0"
                    });
                    AddedFolders.add(ImageProvider.getString(ImageProvider.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)));
                    countItems = 1;
                }else{
                    countItems=countItems+1;
                }

            }while(ImageProvider.moveToNext());
        }
        ImagesFolders.get(ImagesFolders.size()-1)[2]=String.valueOf(countItems);
        ImagesFolders.get(0)[2]=String.valueOf(allMediaCount);
        return  ImagesFolders;
    }


    public ArrayList<String[]> ImageInFolderProvider(String FolderParent) {
        ArrayList<String[]> imagesInfolder= new ArrayList<>();

        Cursor imageprovider = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI ,
                new String[]{
                        MediaStore.Images.Media._ID ,
                        MediaStore.Images.Media.BUCKET_ID ,
                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME ,
                        MediaStore.Images.Media.DATA
                } ,
                MediaStore.Images.Media.DATA+" LIKE ?" ,
                new String[]{"%"+FolderParent+"%"},
                MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        );

        if(imageprovider.moveToFirst()){
            do{
                imagesInfolder.add(new String[]{
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media.DATA)) ,
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media._ID)) ,
                        imageprovider.getString(imageprovider.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)),
                        "0"
                });
            }while(imageprovider.moveToNext());
        }

        return  imagesInfolder;
    }


}
