package com.curso.onbringit.Model;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.curso.onbringit.Adapters.Headings;
import com.curso.onbringit.Adapters.ItemsCancel;
import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.Adapters.ItemsInOrder;
import com.curso.onbringit.Adapters.OrderSchedule;
import com.curso.onbringit.Adapters.Orders;
import com.curso.onbringit.Adapters.ProductsAdapter;
import com.curso.onbringit.Adapters.Stores;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiExecute.address_execute;
import com.curso.onbringit.Model.ApiExecute.headings_executes;
import com.curso.onbringit.Model.ApiExecute.items_executes;
import com.curso.onbringit.Model.ApiExecute.orders_executes;
import com.curso.onbringit.Model.ApiExecute.stores_executes;
import com.curso.onbringit.Model.ApiExecute.user_excutes;
import com.curso.onbringit.Model.ApiExecute.products_executes;
import com.curso.onbringit.View.Activity_splash_screen;
import com.curso.onbringit.View.BottomNavigation;
import com.jaredrummler.materialspinner.MaterialSpinner;
import org.json.JSONObject;
import java.util.concurrent.ExecutionException;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by PC-PRAF on 19/9/2017.
 */

public class ApiRequest {


    //private String url ="xxxxxx";
    private String url ="xxxxxxx";
    //private String url ="xxxxxx";
    Retrofit retrofit;
    ApiServices Service;
    Context context;

    public ApiRequest(Context contex){
        retrofit = new Retrofit.Builder().
        baseUrl(this.url).
        addConverterFactory(GsonConverterFactory.create()).
        build();
        this.context = contex;
        Service = retrofit.create(ApiServices.class);
    }

    public String getUrl() {
        return url;
    }

    public void Send_Order(){
        orders_executes pedidos_executes = new orders_executes(ApiRequest.this.context , Service);
        try {
            boolean orders_request = pedidos_executes.execute(1).get();
            if(orders_request){
                Intent intent = new Intent(ApiRequest.this.context,BottomNavigation.class);
                intent.putExtra("Activity",2);
                ApiRequest.this.context.startActivity(intent);
            }else{
                Toast.makeText(ApiRequest.this.context,"Error de conexion al realizar el pedido",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void Send_AtmOrder(){
        orders_executes orders_executes = new orders_executes(ApiRequest.this.context , Service);
        try {
            boolean atm_request = orders_executes.execute(2).get();
            if(atm_request){
                Intent intent = new Intent(ApiRequest.this.context,BottomNavigation.class);
                intent.putExtra("Activity",1);
                ApiRequest.this.context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void Send_ScheduleOrden(int ScheduleOption){
        orders_executes executes = new orders_executes(ApiRequest.this.context , Service , ScheduleOption);
        try {
            boolean schedule_request = executes.execute(3).get();
            if(schedule_request){
                Intent intent = new Intent(ApiRequest.this.context,BottomNavigation.class);
                intent.putExtra("Activity",2);
                ApiRequest.this.context.startActivity(intent);
            }
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void get_Orders(Orders orders , int accion) {
        orders_executes executes = new orders_executes(ApiRequest.this.context , Service , orders , null, accion);
        try {
            boolean schedule_request = executes.execute(4).get();
            if(!schedule_request){
                Toast.makeText(ApiRequest.this.context,"Error al cargar las ordenes realizadas",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void get_scheduleOrders(OrderSchedule orderSchedule){

        orders_executes executes = new orders_executes(ApiRequest.this.context , Service , null , orderSchedule , 0);
        try {
            boolean schedule_request= executes.execute(5).get();
            if(!schedule_request){
                Toast.makeText(ApiRequest.this.context,"Error al cargar la programación de ordenes",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void get_schedule_info(EditText delivery_to , EditText phone , MaterialSpinner schedule ,
                                  TextView Address , TextView Address_Description , String order_id ){

        orders_executes executes = new orders_executes(ApiRequest.this.context , Service , order_id ,
                                                            delivery_to , phone , Address , Address_Description , schedule  );
        try {
            boolean schedule_info = executes.execute(6).get();
            if(!schedule_info){
                Toast.makeText(ApiRequest.this.context,"Error al cargar la información",Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,"Error al cargar la información",Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,"Error al cargar la información",Toast.LENGTH_LONG).show();
        }

    }

    public void get_item_schedule(ItemsInDelivery ItemInOrderSchedule , String order_id){

        orders_executes executes = new orders_executes( order_id , ItemInOrderSchedule , ApiRequest.this.context , Service);
        try {
            boolean items_order = executes.execute(7).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void update_schedule_order(String OrderId , JSONObject info_items , JSONObject info_delivery ) {

        orders_executes executes = new orders_executes(info_delivery , info_items , OrderId , ApiRequest.this.context , Service);
        try {
            boolean update_response = executes.execute(8).get();
            if(update_response)
            {
                Intent intent = new Intent(context,BottomNavigation.class);
                intent.putExtra("Activity",3);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                ItemsInDelivery.ItemsInDelivery.clear();
            }else{
                Toast.makeText(ApiRequest.this.context, "Error, no se puede actualizar la información de la orden" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage() ,Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage() ,Toast.LENGTH_LONG).show();
        }

    }

    public void cancel_order(String OrderId , int position , TextView MessageError ,
                             android.support.v7.app.AlertDialog alertDialog , Orders ordersclass){

        orders_executes orders_executes = new orders_executes(this.context , Service , OrderId);
        try {
            boolean order_response = orders_executes.execute(9).get();
            if(order_response){
                Toast.makeText(context , "La Orden fue cancelada.",Toast.LENGTH_LONG).show();
                alertDialog.cancel();
                ordersclass.RefreshData(position);
            }else{
                MessageError.setVisibility(View.VISIBLE);
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage() ,Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage() ,Toast.LENGTH_LONG).show();
        }

    }

    public void show_order_item(ItemsInOrder itemsInOrder , String orderId){

        orders_executes executes = new orders_executes(ApiRequest.this.context , Service , orderId , itemsInOrder);
        try {
            boolean items_order = executes.execute(10).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public void show_cancel_item(ItemsCancel itemsInOrder , String orderId){

        items_executes executes = new items_executes(ApiRequest.this.context , Service , orderId , itemsInOrder);
        try {
            boolean items_order = executes.execute(1).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public void cancel_item(int producto_id , TextView MessageError , int position ,
                            android.support.v7.app.AlertDialog alertDialog , ItemsCancel itemsCancel){


        items_executes executes = new items_executes(ApiRequest.this.context , Service , producto_id);
        try {
            boolean items_order = executes.execute(2).get();
            if(items_order){
                Toast.makeText(context , "El producto fue cancelado", Toast.LENGTH_LONG).show();
                alertDialog.cancel();
                itemsCancel.remove_item(position);
            }else{
                MessageError.setVisibility(View.VISIBLE);
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context, e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public boolean get_user_api_id(String email , String nombre , String telefono , String image_profile , int hasImageProfile ){

        boolean response;
        user_excutes user_excutes = new user_excutes(ApiRequest.this.context , Service , email , nombre , telefono , hasImageProfile);
        try {
            boolean user_request = user_excutes.execute(2).get();
            if(user_request)
            {
                if(hasImageProfile == 1){
                    upload_image_profile(image_profile , com.curso.onbringit.Model.ApiExecute.user_excutes.user_api_id);
                }
                response = true;
            }else{
                response = false;
            }
        } catch (Exception e) {
            response = false;
            Toast.makeText(ApiRequest.this.context , e.getMessage() , Toast.LENGTH_LONG).show();
        }

        return  response;
    }

    public boolean add_address(String AddressName , String AddressDescription , int default_){
        address_execute address_execute = new address_execute(ApiRequest.this.context , Service , AddressName ,
                                                              AddressDescription , 0 , default_);
        boolean response = false;
        try {
            response =  address_execute.execute(1).get();
        } catch (Exception e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return  response;
    }

    public void upload_image_profile(String image_profile , int user_api_id ){

        user_excutes user_excutes = new user_excutes(ApiRequest.this.context , Service , image_profile , user_api_id);
        try {
            boolean user_request = user_excutes.execute(4).get();
            if(!user_request)
            {
                Toast.makeText(context ,"Error, no se puede cargar la imagen del perfil",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.context , e.getMessage() , Toast.LENGTH_LONG).show();
        }

    }

    public  void update_user_profile(JSONObject Datos){

        user_excutes user_excutes = new user_excutes(ApiRequest.this.context , Service , Datos);
        try {
            boolean user_request = user_excutes.execute(3).get();
            if(user_request)
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();

                Toast.makeText(context ,"Se actualizó la información del perfil",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(context, BottomNavigation.class);
                context.startActivity(intent);

            }else{
                Toast.makeText(context ,"Error verifique su conexión a internet",Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.context , e.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    public void deleteprofile(JSONObject params , Sql_Query query , android.support.v7.app.AlertDialog dialog) {

        user_excutes user_excutes = new user_excutes(ApiRequest.this.context, Service, params);
        try {
            boolean user_request = user_excutes.execute(5).get();
            if (user_request) {
                query.deleteAccount();
                dialog.cancel();
                Intent intent = new Intent(context, Activity_splash_screen.class);
                context.startActivity(intent);

            } else {
                Toast.makeText(context, "Error, no se puede eliminar el perfil de ordena", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this.context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void get_headings(Headings headings){

        headings_executes executes = new headings_executes(ApiRequest.this.context , Service , headings);
        try {
            boolean items_order = executes.execute(1).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    public void get_stores(Stores stores , int heading){

        stores_executes executes = new stores_executes(ApiRequest.this.context , Service , stores , heading);
        try {
            boolean items_order = executes.execute(1).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    public void get_products(ProductsAdapter products , int store){

        products_executes executes = new products_executes(ApiRequest.this.context , Service , store , products);
        try {
            boolean items_order = executes.execute(1).get();
            if(!items_order){
                Toast.makeText(ApiRequest.this.context, "Error al cargar la información" ,Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            Toast.makeText(ApiRequest.this.context,e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

}