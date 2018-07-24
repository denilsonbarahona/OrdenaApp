package com.curso.onbringit.Model;


import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by PC-PRAF on 19/9/2017.
 */

public interface ApiServices {

    @FormUrlEncoded()
    @POST("enviar_pedido")
    Call<ApiSchemaResponse.Pedido_Response> enviar_pedido(@Field("orden") String orden ,
                                                          @Field("adjuntos") JSONObject Adjuntos ,
                                                          @Field("datos") String datos ,
                                                          @Field("token") String token);

    @FormUrlEncoded()
    @POST("enviar_pedidoATM")
    Call<ApiSchemaResponse.ATM_Pedido_Response> enviar_pedidoATM(@Field("datos")String datos , @Field("token") String token);

    @FormUrlEncoded()
    @POST("enviar_pedidoProgramado")
    Call<ApiSchemaResponse.Pedido_Programado_Response>enviar_pedidoProgramado(@Field("orden") String orden ,
                                                                              @Field("adjuntos") JSONObject Adjuntos ,
                                                                              @Field("datos") String datos ,
                                                                              @Field("token") String token);
    @GET("listar_ordenes/{usuario}/{accion}/{token}")
    Call<ApiSchemaResponse.listar_ordenes> listar_ordenes(@Path("usuario") String usuario , @Path("accion") int accion , @Path("token") String token);

    @GET("listar_ordenes_programadas/{usuario}/{token}")
    Call<ApiSchemaResponse.listar_ordenes_programadas> listar_ordenes_programadas(@Path("usuario") String usuario , @Path("token") String token);

    @GET("dato_orden_programada/{orden_id}/{token}")
    Call<ApiSchemaResponse.datos_entrega_orden_programada> dato_entrega_orden_programada(@Path("orden_id") String orden_id , @Path("token") String token);

    @GET("listar_item_orden/{orden_id}/{token}")
    Call<ApiSchemaResponse.listar_item_orden> listar_item_orden(@Path("orden_id") String orden_id , @Path("token") String token);

    @FormUrlEncoded()
    @POST("editar_orden_programada")
    Call<ApiSchemaResponse.editar_orden_programada> editar_orden_programada(@Field("info_entrega") JSONObject entrega ,
                                                                            @Field("info_items") JSONObject items   ,
                                                                            @Field("order_id") String Orderid   ,
                                                                            @Field("token") String token);

    @FormUrlEncoded()
    @POST("cancelar_orden")
    Call<ApiSchemaResponse.cancelar_orden> cancelar_orden(@Field("orden_id") String orden_id , @Field("token") String token);

    @FormUrlEncoded()
    @POST("cancelar_producto")
    Call<ApiSchemaResponse.cancelar_producto> cancelar_producto(@Field("producto_id") int producto_id , @Field("token") String token);

    @GET("obtener_usuario_api_id/{email}/{nombre}/{telefono}")
    Call<ApiSchemaResponse.obtener_usuario_api_id> obtener_usuario_api_id(@Path("email") String email ,
                                                                          @Path("nombre") String nombre ,
                                                                          @Path("telefono") String telefono );

    @FormUrlEncoded()
    @POST("subir_imagen_perfil")
    Call<ApiSchemaResponse.subir_imagen> subir_imagen_perfil(@Field("imagen_encode") String imagen ,
                                                             @Field("usuario_api_id") int usuario ,
                                                             @Field("token") String token);

    @FormUrlEncoded()
    @POST("actualizar_perfil_usuario")
    Call<ApiSchemaResponse.conexion_escalar_api> actualizar_perfil(@Field("parametro") JSONObject Datos ,@Field("token") String token);

    @FormUrlEncoded()
    @POST("eliminar_cuenta")
    Call<ApiSchemaResponse.conexion_escalar_api> eliminar_perfil(@Field("parametro") JSONObject Datos , @Field("token") String token);

    @GET("token/{email}")
    Call<ApiSchemaResponse.obtener_usuario_api_id> obtener_token(@Path("email") String Email );

    @FormUrlEncoded()
    @POST("registrar_direccion")
    Call<ApiSchemaResponse.conexion_escalar_api> nueva_direccion (@Field("parametro") JSONObject Datos , @Field("token") String token);

    @GET("listar_rubros/{token}")
    Call<ApiSchemaResponse.listar_rubros> listar_rubros(@Path("token") String token );

    @GET("listar_locales/{token}/{rubro}")
    Call<ApiSchemaResponse.listar_locales> listar_locales(@Path("token") String token , @Path("rubro") int rubro );

    @GET("listar_productos/{token}/{local}")
    Call<ApiSchemaResponse.listar_productos> listar_productos(@Path("token") String token , @Path("local") int local );


}
