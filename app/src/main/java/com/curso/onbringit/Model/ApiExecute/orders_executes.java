package com.curso.onbringit.Model.ApiExecute;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.curso.onbringit.Adapters.ItemsInDelivery;
import com.curso.onbringit.Adapters.ItemsInOrder;
import com.curso.onbringit.Adapters.OrderSchedule;
import com.curso.onbringit.Adapters.Orders;
import com.curso.onbringit.DataBase.Sql_Query;
import com.curso.onbringit.Model.ApiSchemaResponse;
import com.curso.onbringit.Model.ApiServices;
import com.curso.onbringit.View.ATMMovil;
import com.curso.onbringit.View.AddScheduleActivity;
import com.curso.onbringit.View.EditScheduleActivity;
import com.curso.onbringit.View.InfoDeliveryActivity;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by PC-PRAF on 24/12/2017.
 */

public class orders_executes extends AsyncTask<Integer , Void , Boolean> {

    private Context context;
    private ApiServices Service;
    private int schedule;
    private Orders orders;
    private OrderSchedule orderSchedule;
    private String Order_Id;
    private EditText delivery_to;
    private TextView Address;
    private EditText phone;
    private TextView Address_Description;
    private MaterialSpinner schedule_material;
    private ItemsInDelivery ItemInOrderSchedule;
    private JSONObject info_items;
    private JSONObject info_delivery;
    private int accion;
    private ItemsInOrder itemsInOrder;


    public orders_executes(Context contx , ApiServices Serv){
        this.context = contx;
        this.Service = Serv;
    }

    public orders_executes(Context contx , ApiServices Serv , int schedule_option){
        this.context = contx;
        this.Service = Serv;
        this.schedule = schedule_option;
    }

    public orders_executes(Context contx , ApiServices Serv , Orders ord , OrderSchedule ord_schedule , int accion){
        this.context = contx;
        this.Service = Serv;
        this.orders = ord;
        this.orderSchedule = ord_schedule;
        this.accion = accion;
    }

    public orders_executes(Context contx , ApiServices Serv , String OrderId){
        this.context = contx;
        this.Service = Serv;
        this.Order_Id = OrderId;
    }

    public orders_executes(Context contx , ApiServices Serv , String OrderId , ItemsInOrder itemsInOrder){
        this.context = contx;
        this.Service = Serv;
        this.Order_Id = OrderId;
        this.itemsInOrder = itemsInOrder;
    }

    public orders_executes(Context contx , ApiServices Serv , String Order_ , EditText delivery_ , EditText Phone_ ,
                           TextView Address_ , TextView Address_Descrip_ , MaterialSpinner Material_ ){

        this.context = contx;
        this.Service = Serv;
        this.Order_Id = Order_;
        this.delivery_to = delivery_;
        this.phone = Phone_;
        this.Address = Address_;
        this.Address_Description = Address_Descrip_;
        this.schedule_material = Material_;
    }

    public orders_executes(String order_  , ItemsInDelivery itemsOrder , Context contx , ApiServices Serv){
        this.Order_Id = order_;
        this.ItemInOrderSchedule = itemsOrder;
        this.context = contx;
        this.Service = Serv;
    }

    public orders_executes( JSONObject info_delivery ,  JSONObject info_items , String OrderId , Context contx , ApiServices Serv  ){

        this.Order_Id = OrderId;
        this.Service = Serv;
        this.info_items = info_items;
        this.info_delivery = info_delivery;
        this.context = contx;
    }


    @Override
    protected Boolean doInBackground(Integer... integers) {
        Boolean response = false;
        switch (integers[0]){
            case 1: response = ExpressDelivery();
                    break;
            case 2: response = AtmDelivery();
                    break;
            case 3: response = ScheduleDelivery();
                    break;
            case 4: response = GetOrders();
                    break;
            case 5: response = GetScheduleOrders();
                    break;
            case 6: response = GetInfoScheduleOrder();
                    break;
            case 7: response = GetItemSchedule();
                    break;
            case 8: response = Update_Schedule_Order();
                    break;
            case 9: response = CancelOrden();
                    break;
            case 10: response = showItemOrder();
                    break;
        }
        return response;
    }


    private boolean ExpressDelivery(){

        boolean salida = false;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        ArrayList<String[]> OrderList = ItemsInDelivery.ItemsInDelivery;
        String Images = ImageForm();
        String jsonInfoOrder = "{ \"datos\": [ " + InfoDeliveryActivity.Address+" , \"" +  InfoDeliveryActivity.Entrega+"\" , \""+  InfoDeliveryActivity.phonerNumber+"\"]}";
        String jsonOrderList = new Gson().toJson(OrderList);

        try {
            JSONObject jsonAttchedList = new JSONObject(Images);
            Call<ApiSchemaResponse.Pedido_Response> Send_Order = this.Service.enviar_pedido(jsonOrderList   , jsonAttchedList ,  jsonInfoOrder   ,  Token);
            Response send_order_response = Send_Order.execute();
            if(send_order_response.code() != 200)
            {
                if(send_order_response.code() == 401)
                {
                    user_excutes user_excutes = new user_excutes(this.context , Service);
                    boolean token_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                    if(token_request){
                        ExpressDelivery();
                    }
                }else{
                    Toast.makeText(context , "Error de servidor , no se pudo realizar el pedido" , Toast.LENGTH_LONG).show();
                    salida = false;
                }
            }else{
                salida = true;
            }
        } catch (Exception e) {
            salida = false;
            Toast.makeText(context , e.getMessage() , Toast.LENGTH_LONG).show();
        }

        return salida;
    }


    private String ImageForm(){
        ArrayList<String[]> OrderList = ItemsInDelivery.ItemsInDelivery;
        String Images ="{ 'images': [";
        for(int x = 0; x<ItemsInDelivery.AttachInItem.size() ; x++){
            for(int y = 1;y<ItemsInDelivery.AttachInItem.get(x).size();y++)
            {
                if(y == 1){
                    for(int z = 0;z<OrderList.size();z++)
                    {
                        if(z == Integer.parseInt(ItemsInDelivery.AttachInItem.get(x).get(0))){
                            Images+=" { 'producto': '"+OrderList.get(z)[0]+"' , 'imagenes':[ ";
                        }
                    }
                }

                Bitmap bitmap = BitmapFactory.decodeFile(ItemsInDelivery.AttachInItem.get(x).get(y));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                Images+="'"+ Base64.encodeToString(bytes , Base64.DEFAULT)+"'";

                if(y+1 < ItemsInDelivery.AttachInItem.get(x).size()){
                    Images+=",";
                }
            }
            Images+=" ] } ";
            if(x+1 < ItemsInDelivery.AttachInItem.size()){
                Images+=",";
            }
        }

        Images+=" ]}";

        return  Images;
    }


    private boolean AtmDelivery(){
        boolean salida;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        String jsonATMOrden = "{ \"money\": \""+ ATMMovil.moneyAtm +"\" , \"address\":\""+ATMMovil.DeliveryAddres+"\" ,"+
                "\"phone\":\""+ATMMovil.phoneNumber+"\", \"deliveryTo\": \""+ATMMovil.DeliveryTo+"\"}";


        Call<ApiSchemaResponse.ATM_Pedido_Response> Atm_request = Service.enviar_pedidoATM(jsonATMOrden , Token);
        try {
            Response AtmResponse =Atm_request.execute();
            ApiSchemaResponse.ATM_Pedido_Response response = (ApiSchemaResponse.ATM_Pedido_Response) AtmResponse.body();

            if(AtmResponse.code() == 401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                if(user_request){
                    salida = AtmDelivery();
                }else{
                    salida = false;
                }
            }else{
                if(response.getStatus() == 200){
                    salida = true;
                }else{
                    Toast.makeText(context ,response.getmensaje() , Toast.LENGTH_LONG).show();
                    salida = false;
                }
            }

        } catch (Exception e) {
            Toast.makeText(context , e.getMessage() , Toast.LENGTH_LONG).show();
            salida = false;
        }

        return salida;
    }


    private boolean ScheduleDelivery(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        ArrayList<String[]> OrderList;
        String jsonInfoOrder = "{ \"datos\": [ \""+ AddScheduleActivity.dateFirtsDelivery+"\" , \"" +
                AddScheduleActivity.TimeFirtsDelivery+"\" , \"" +
                AddScheduleActivity.Address+"\" , \""+
                AddScheduleActivity.phoneNumber+"\" , \""+
                AddScheduleActivity.DeliveryTo+"\" , "+
                schedule+"]}";

        OrderList = ItemsInDelivery.ItemsInDelivery;
        String Images = ImageForm();

        String jsonOrderList = new Gson().toJson(OrderList);

        try {
            JSONObject  jsonAttchedList = new JSONObject(Images);

            Call<ApiSchemaResponse.Pedido_Programado_Response> send_schedule =
                    Service.enviar_pedidoProgramado(jsonOrderList , jsonAttchedList , jsonInfoOrder , Token);

            Response response_schedule = send_schedule.execute();
            ApiSchemaResponse.Pedido_Programado_Response schedule_response = (ApiSchemaResponse.Pedido_Programado_Response) response_schedule.body();
            if(response_schedule.code() == 401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                if(user_request){
                    response = ScheduleDelivery();
                }else{
                    response = false;
                }
            }else{
                if(schedule_response.getStatus() == 200)
                {
                    response = true;
                }else{
                    response = false;
                    Toast.makeText(context ,schedule_response.getmensaje() , Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            response = false;
            Toast.makeText(context ,e.getMessage() , Toast.LENGTH_LONG).show();
        }

        return response;
    }


    private boolean GetOrders() {
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        ArrayList<String[]> arrayList = new ArrayList<>();
        ArrayList<String[]> User_Profile = sql_query.GetUserProfile();

        Call< ApiSchemaResponse.listar_ordenes > ListOrders = Service.listar_ordenes(User_Profile.get(0)[5] , this.accion , Token);

        try {
            Response list_orders_request = ListOrders.execute();
            if(list_orders_request.code()==401)
            {
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                if(user_request){
                    response = GetOrders();
                }else{
                    response = false;
                }

            }else{
                ApiSchemaResponse.listar_ordenes orders_response = (ApiSchemaResponse.listar_ordenes) list_orders_request.body();
                if(orders_response.getStatus() == 200){

                    for(int l = 0 ; l < orders_response.getmensaje().size() ; l++){
                        arrayList.add(new String[]{ orders_response.getmensaje().get(l).getOrden_id()
                                , orders_response.getmensaje().get(l).getFecha()
                                , "Dirección de Entrega: "+orders_response.getmensaje().get(l).getDireccion_entrega()
                                , orders_response.getmensaje().get(l).getEstado()
                                , String.valueOf( orders_response.getmensaje().get(l).getOrden_id()  )
                                , String.valueOf( orders_response.getmensaje().get(l).getEstado_id() )
                                , String.valueOf( orders_response.getmensaje().get(l).getDiff() )
                                , "Orden Número #"+orders_response.getmensaje().get(l).getEntrega_id()

                        });
                    }
                    response = true;
                    orders.AddOrders(arrayList);
                }else{
                    response = false;
                    Toast.makeText(context,"No se puede listar las ordenes realizadas; verifique su conexion a internet",Toast.LENGTH_LONG).show();
                }
            }

        } catch (Exception e) {
            response = false;
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return  response;
    }


    private boolean GetScheduleOrders(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        ArrayList<String[]> ScheduleOrders = new ArrayList<>();
        ArrayList<String[]> User_Profile = sql_query.GetUserProfile();

        Call<ApiSchemaResponse.listar_ordenes_programadas> Schedule = Service.listar_ordenes_programadas(User_Profile.get(0)[5] , Token);
        try {
            Response Schedule_Response = Schedule.execute();
            if(Schedule_Response.code()==401)
            {
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                if(user_request){
                    response = GetScheduleOrders();
                }else{
                    response = false;
                }
            }else{
                if(Schedule_Response.code()==200){
                    ApiSchemaResponse.listar_ordenes_programadas schedule_orders = (ApiSchemaResponse.listar_ordenes_programadas) Schedule_Response.body();
                    for(int l = 0 ; l < schedule_orders.getmensaje().size() ; l++){
                        ScheduleOrders.add(new String[]{ String.valueOf( schedule_orders.getmensaje().get(l).getEntrega_id() )
                                , "Dirección de Entrega: "+schedule_orders.getmensaje().get(l).getDireccion_entrega()
                                , "Proxima Entrega: "+schedule_orders.getmensaje().get(l).getProximaEntrega()
                                , String.valueOf( schedule_orders.getmensaje().get(l).getOrden_id() )
                        });
                    }
                    response = true;
                    orderSchedule.AddScheduleOrders(ScheduleOrders);
                }else{
                    response = false;
                    Toast.makeText(context,"Error al cargar la programación de ordenes",Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            response = false;
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return response;
    }


    private boolean GetInfoScheduleOrder(){

        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.datos_entrega_orden_programada> Schedule_info = Service.dato_entrega_orden_programada(Order_Id , Token);
        try {
            Response schedule_response = Schedule_info.execute();
            if(schedule_response.code() == 401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                }
                if(user_request){
                    response = GetInfoScheduleOrder();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(schedule_response.code() == 200){
                    ApiSchemaResponse.datos_entrega_orden_programada schedule_delivery = (ApiSchemaResponse.datos_entrega_orden_programada) schedule_response.body();

                    delivery_to.setText(schedule_delivery.getEntregar());
                    phone.setText(schedule_delivery.getTelefono());
                    schedule_material.setText(schedule_delivery.getProgramado());
                    Address.setText(schedule_delivery.getDireccion_entrega());
                    Address_Description.setText(schedule_delivery.getDescripcion_direccion());
                    EditScheduleActivity.ScheduleOption = schedule_delivery.getProgramado_id();
                    EditScheduleActivity.AddressApiID = schedule_delivery.getDireccion_id();
                    response = true;
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }

        } catch (IOException e) {
            response = false;
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }


        return response;
    }


    private boolean GetItemSchedule(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        ArrayList<String[]> ItemOrden = new ArrayList<>();

        Call<ApiSchemaResponse.listar_item_orden> ItemOrdenServices = Service.listar_item_orden(this.Order_Id , Token);
        try {
            Response response_items = ItemOrdenServices.execute();
            if(response_items.code() == 401)
            {
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                }
                if(user_request){
                    response = GetItemSchedule();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(response_items.code() == 200){
                    ApiSchemaResponse.listar_item_orden respuesta = (ApiSchemaResponse.listar_item_orden) response_items.body();
                    for(int l = 0 ; l < respuesta.getMensaje().size(); l++){
                        ItemOrden.add(new String[]{
                                respuesta.getMensaje().get(l).getNombre()        ,
                                respuesta.getMensaje().get(l).getCantidad()      ,
                                respuesta.getMensaje().get(l).getNota()          ,
                                respuesta.getMensaje().get(l).getLugar_compra()  ,
                                String.valueOf( respuesta.getMensaje().get(l).getAdjuntos() ) ,
                                String.valueOf(respuesta.getMensaje().get(l).getId())
                        });
                    }
                    ItemInOrderSchedule.PushItemsArray(ItemOrden);
                    response = true;
                }else{
                    Toast.makeText(context,"Error al listar los productos de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return response;
    }


    private boolean Update_Schedule_Order(){

        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.editar_orden_programada> orden_programada = Service.editar_orden_programada
                (info_delivery , info_items , Order_Id , Token);

        try {
            Response schedule_response = orden_programada.execute();
            if(schedule_response.code() == 401)
            {
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(user_request){
                    response = Update_Schedule_Order();
                }else{
                    Toast.makeText(context,"Error, no se puede actualizar la información de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else {
                if(schedule_response.code() == 200 ){
                    ApiSchemaResponse.editar_orden_programada respuesta = (ApiSchemaResponse.editar_orden_programada) schedule_response.body();
                    if(respuesta.getStatus() == 200){
                        response = true;
                    }else{
                        Toast.makeText(context,"Error, no se puede actualizar la información de la orden",Toast.LENGTH_LONG).show();
                        response =false;
                    }
                }else{
                    Toast.makeText(context,"Error, no se puede actualizar la información de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return  response;
    }


    private boolean CancelOrden(){

        boolean response;
        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();

        Call<ApiSchemaResponse.cancelar_orden> cancelar_orden = Service.cancelar_orden(Order_Id , Token);

        try {
            Response cancel_order_response = cancelar_orden.execute();
            if(cancel_order_response.code() == 401){
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                if(user_request){
                    response = GetItemSchedule();
                }else{
                    Toast.makeText(context,"Error, no se puede actualizar la información de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(cancel_order_response.code() == 200){
                    ApiSchemaResponse.cancelar_orden order_cancel = (ApiSchemaResponse.cancelar_orden) cancel_order_response.body();
                    if(order_cancel.getStatus()==200){
                        response = true;
                    }else{
                        response = false;
                    }
                }else{
                    Toast.makeText(context,"Error, no se puede cancelar la orden por favor llamar a las oficinas de ordena para cancelar la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return response;
    }


    private boolean showItemOrder(){
        boolean response;

        SQLiteDatabase db = Sql_Query.GetConnection(context).getReadableDatabase();
        Sql_Query sql_query = new Sql_Query(db,context);
        String Token = sql_query.GetApiToken();
        ArrayList<String[]> ItemOrden = new ArrayList<>();

        Call<ApiSchemaResponse.listar_item_orden> ItemOrdenServices = Service.listar_item_orden(this.Order_Id , Token);
        try {
            Response response_items = ItemOrdenServices.execute();
            if(response_items.code() == 401)
            {
                user_excutes user_excutes = new user_excutes(context , Service);
                boolean user_request = false;
                try {
                    user_request = user_excutes.executeOnExecutor(THREAD_POOL_EXECUTOR , 1).get();
                } catch (InterruptedException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                } catch (ExecutionException e) {
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
                    response = false;
                }
                if(user_request){
                    response = showItemOrder();
                }else{
                    Toast.makeText(context,"Error al cargar la información",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }else{
                if(response_items.code() == 200){
                    ApiSchemaResponse.listar_item_orden respuesta = (ApiSchemaResponse.listar_item_orden) response_items.body();
                    for(int l = 0 ; l < respuesta.getMensaje().size(); l++){
                        ItemOrden.add(new String[]{
                                respuesta.getMensaje().get(l).getNombre()        ,
                                respuesta.getMensaje().get(l).getCantidad()      ,
                                respuesta.getMensaje().get(l).getNota()          ,
                                respuesta.getMensaje().get(l).getLugar_compra()  ,
                                String.valueOf( respuesta.getMensaje().get(l).getAdjuntos() ) ,
                                String.valueOf(respuesta.getMensaje().get(l).getId())
                        });
                    }
                    itemsInOrder.AddItems(ItemOrden);
                    response = true;
                }else{
                    Toast.makeText(context,"Error al listar los productos de la orden",Toast.LENGTH_LONG).show();
                    response = false;
                }
            }
        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            response = false;
        }

        return response;
    }


}
