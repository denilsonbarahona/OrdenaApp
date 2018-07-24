package com.curso.onbringit.Model;
import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by PC-PRAF on 19/9/2017.
 */

public class ApiSchemaResponse
{

    public class Pedido_Response
    {
        private String mensaje;
        private int    status;

        @SerializedName("orden")
        private String orden;
        @SerializedName("adjuntos")
        private JSONObject adjuntos;
        @SerializedName("datos")
        private String datos;

        @SerializedName("token")
        private String token;

        public String getorden(){ return this.orden; }
        public JSONObject getadjuntos(){ return  this.adjuntos; }
        public String getdatos(){ return  this.datos; }
        public String getmensaje(){
            return  mensaje;
        }
        public int  getStatus(){ return status; }
        public void setmensaje(String mensaje){
            this.mensaje = mensaje;
        }
        public void  setstatus(int Status){
            this.status = Status;
        }
        public void setorden(String orden){ this.orden = orden; }
        public void setadjuntos(JSONObject adjuntos){ this.adjuntos = adjuntos; }
        public void setdatos(String datos){ this.datos = datos; }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class ATM_Pedido_Response
    {
        private String mensaje;
        private int    status;

        @SerializedName("datos")
        private String datos;

        @SerializedName("token")
        private String token;

        public String getdatos(){ return this.datos; }
        public String getmensaje(){
            return  mensaje;
        }
        public int  getStatus(){ return status; }
        public void setmensaje(String mensaje){
            this.mensaje = mensaje;
        }
        public void setstatus(int Status){
            this.status = Status;
        }
        public void setdatos(String datos){ this.datos = datos; }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class Pedido_Programado_Response
    {
        private String mensaje;
        private int    status;

        @SerializedName("orden")
        private String orden;
        @SerializedName("adjuntos")
        private String adjuntos;
        @SerializedName("datos")
        private String datos;
        @SerializedName("token")
        private String token;

        public String getorden(){ return this.orden; }
        public String getadjuntos(){ return  this.adjuntos; }
        public String getdatos(){ return  this.datos; }
        public String getmensaje(){
            return  mensaje;
        }
        public int  getStatus(){ return status; }
        public void setmensaje(String mensaje){
            this.mensaje = mensaje;
        }
        public void  setstatus(int Status){
            this.status = Status;
        }
        public void setorden(String orden){ this.orden = orden; }
        public void setadjuntos(String adjuntos){ this.adjuntos = adjuntos; }
        public void setdatos(String datos){ this.datos = datos; }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class listar_ordenes
    {

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public class ordenes_data
        {

            private String orden_id;
            private String direccion_entrega;
            private int estado_id;
            private String estado;
            private String fecha;
            private int accion;
            private int diff;
            private String entrega_id;

            public int getDiff() { return diff; }

            public void setDiff(int diff) { this.diff = diff; }

            public String getOrden_id() { return orden_id; }

            public void setOrden_id(String orden_id) {
                this.orden_id = orden_id;
            }

            public String getDireccion_entrega() {
                return direccion_entrega;
            }

            public void setDireccion_entrega(String direccion_entrega) { this.direccion_entrega = direccion_entrega; }

            public int getEstado_id() {
                return estado_id;
            }

            public void setEstado_id(int estado_id) {
                this.estado_id = estado_id;
            }

            public String getEstado() {
                return estado;
            }

            public void setEstado(String estado) {
                this.estado = estado;
            }

            public String getFecha() {
                return fecha;
            }

            public void setFecha(String fecha) {
                this.fecha = fecha;
            }

            public int getAccion() {
                return accion;
            }

            public void setAccion(int accion) {
                this.accion = accion;
            }

            public String getEntrega_id() {
                return entrega_id;
            }

            public void setEntrega_id(String entrega_id) {
                this.entrega_id = entrega_id;
            }
        }

        @SerializedName("orden")
        private int usuario;
        @SerializedName("token")
        private String token;

        private List <ordenes_data > mensaje;
        private int    status;

        public List< ordenes_data > getmensaje(){
            return  mensaje;
        }
        public int  getStatus(){ return status; }
        public int  getUsuario(){ return  usuario; }

        public void  setmensaje(List<ordenes_data > mensaje){
            this.mensaje = mensaje;
        }
        public void  setstatus(int Status){
            this.status = Status;
        }
        public void  setUsuario(int usuario ){ this.usuario = usuario; }
    }


    public class listar_ordenes_programadas
    {

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public class ordenes_data {

            private int orden_id;
            private String direccion_entrega;
            private String proximaEntrega;
            private String entrega_id;

            public int getOrden_id() {
                return orden_id;
            }

            public void setOrden_id(int orden_id) {
                this.orden_id = orden_id;
            }

            public String getDireccion_entrega() {
                return direccion_entrega;
            }

            public void setDireccion_entrega(String direccion_entrega) { this.direccion_entrega = direccion_entrega; }

            public String getProximaEntrega() {
                return proximaEntrega;
            }

            public void setProximaEntrega(String proximaEntrega) { this.proximaEntrega = proximaEntrega; }

            public String getEntrega_id() {
                return entrega_id;
            }

            public void setEntrega_id(String entrega_id) {
                this.entrega_id = entrega_id;
            }
        }

        @SerializedName("usuario")
        private int usuario;
        @SerializedName("token")
        private String token;

        private List <ordenes_data > mensaje;
        private int    status;

        public List< ordenes_data > getmensaje(){
            return  mensaje;
        }

        public int  getStatus(){ return status; }
        public int  getUsuario(){ return  usuario; }

        public void  setmensaje(List<ordenes_data > mensaje){
            this.mensaje = mensaje;
        }
        public void  setstatus(int Status){
            this.status = Status;
        }
        public void  setUsuario(int usuario ){ this.usuario = usuario; }
    }


    public class datos_entrega_orden_programada
    {
        private String entregar;
        private String telefono;
        private String programado;
        private int    programado_id;
        private String direccion_entrega;
        private String descripcion_direccion;
        private int    status;
        private int direccion_id;

        @SerializedName("orden_id")
        private int orden_id;
        @SerializedName("token")
        private String token;


        public String getEntregar() {
            return entregar;
        }

        public void setEntregar(String entregar) {
            this.entregar = entregar;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getProgramado() {
            return programado;
        }

        public void setProgramado(String programado) {
            this.programado = programado;
        }

        public int getProgramado_id() {
            return programado_id;
        }

        public void setProgramado_id(int programado_id) {
            this.programado_id = programado_id;
        }

        public String getDireccion_entrega() {
            return direccion_entrega;
        }

        public void setDireccion_entrega(String direccion_entrega) {
            this.direccion_entrega = direccion_entrega;
        }

        public String getDescripcion_direccion() {
            return descripcion_direccion;
        }

        public void setDescripcion_direccion(String descripcion_direccion) {
            this.descripcion_direccion = descripcion_direccion;
        }

        public int getOrden_id() {
            return orden_id;
        }

        public void setOrden_id(int orden_id) {
            this.orden_id = orden_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getDireccion_id() {
            return direccion_id;
        }

        public void setDireccion_id(int direccion_id) {
            this.direccion_id = direccion_id;
        }
    }


    public class listar_item_orden{

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public   class ItemsData{

            private String nombre;
            private String cantidad;
            private String lugar_compra;
            private int    adjuntos;
            private String nota;
            private String estado_d;
            private int    id;

            public String getNombre() {
                return nombre;
            }

            public void setNombre(String nombre) {
                this.nombre = nombre;
            }

            public String getCantidad() {
                return cantidad;
            }

            public void setCantidad(String cantidad) {
                this.cantidad = cantidad;
            }

            public String getLugar_compra() {
                return lugar_compra;
            }

            public void setLugar_compra(String lugar_compra) {
                this.lugar_compra = lugar_compra;
            }

            public int getAdjuntos() {
                return adjuntos;
            }

            public void setAdjuntos(int adjuntos) {
                this.adjuntos = adjuntos;
            }

            public String getNota() {
                return nota;
            }

            public void setNota(String nota) {
                this.nota = nota;
            }

            public String getEstado_d() {
                return estado_d;
            }

            public void setEstado_d(String estado_d) {
                this.estado_d = estado_d;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        @SerializedName("orden_id")
        private int orden_id;
        @SerializedName("token")
        private String token;
        private int status;
        private List<ItemsData> mensaje;

        public int getOrden_id() {
            return orden_id;
        }

        public void setOrden_id(int orden_id) {
            this.orden_id = orden_id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public List<ItemsData> getMensaje() {
            return mensaje;
        }

        public void setMensaje(List<ItemsData> mensaje) {
            this.mensaje = mensaje;
        }

    }


    public class editar_orden_programada
    {

        private String mensaje;
        private int status;

        @SerializedName("informacion_entrega")
        private String info_entrega;

        @SerializedName("items_entrega")
        private String items_entrega;

        @SerializedName("order_id")
        private String order_id;

        @SerializedName("token")
        private String token;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getInfo_entrega() {
            return info_entrega;
        }

        public void setInfo_entrega(String info_entrega) {
            this.info_entrega = info_entrega;
        }

        public String getItems_entrega() {
            return items_entrega;
        }

        public void setItems_entrega(String items_entrega) {
            this.items_entrega = items_entrega;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class cancelar_orden
    {

        private String mensaje;
        private int status;

        @SerializedName("orden_id")
        private String orden_id;

        @SerializedName("token")
        private String token;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getOrden_id() {
            return orden_id;
        }

        public void setOrden_id(String orden_id) {
            this.orden_id = orden_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class cancelar_producto
    {
        private String mensaje;
        private int status;

        @SerializedName("producto_id")
        private int producto_id;

        @SerializedName("token")
        private String token;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getProducto_id() {
            return producto_id;
        }

        public void setProducto_id(int producto_id) {
            this.producto_id = producto_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class obtener_usuario_api_id
    {

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public class items{

            private int usuario_id;
            private String token;

            public int getUsuario_id() {
                return usuario_id;
            }

            public void setUsuario_id(int usuario_id) {
                this.usuario_id = usuario_id;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }
        private List<items> mensaje;
        private int status;

        @SerializedName("email")
        private String email;

        @SerializedName("token")
        private String token;

        @SerializedName("nombre")
        private String nombre;

        @SerializedName("telefono")
        private String telefono;

        public List<items> getMensaje() {
            return mensaje;
        }

        public void setMensaje(List<items> mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) { this.email = email; }
    }


    public class subir_imagen
    {
        private String mensaje;
        private int status;

        @SerializedName("imagen_encode")
        private int imagen_encode;

        @SerializedName("usuario_api_id")
        private int usuario_api_id;

        @SerializedName("token")
        private String token;

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getImagen_Encode() {
            return imagen_encode;
        }

        public void setImagen_Encode(int imagen_encode) {
            this.imagen_encode = imagen_encode;
        }

        public int getUsuario_api_id() {
            return usuario_api_id;
        }

        public void setUsuario_api_id(int usuario_api_id) {
            this.usuario_api_id = usuario_api_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class conexion_escalar_api
    {
        private int mensaje;
        private int status;

        @SerializedName("parametro")
        private int parametro;

        @SerializedName("token")
        private String token;

        public int getMensaje() {
            return mensaje;
        }

        public void setMensaje(int mensaje) {
            this.mensaje = mensaje;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getParametro() {
            return parametro;
        }

        public void setParametro(int parametro) {
            this.parametro = parametro;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }


    public class listar_rubros{

        public class rubro
        {
            private int Rubro_Codigo;
            private String Rubro_Nombre;

            public int getRubro_Codigo() {
                return Rubro_Codigo;
            }

            public void setRubro_Codigo(int rubro_Codigo) {
                Rubro_Codigo = rubro_Codigo;
            }

            public String getRubro_Nombre() {
                return Rubro_Nombre;
            }

            public void setRubro_Nombre(String rubro_Nombre) {
                Rubro_Nombre = rubro_Nombre;
            }
        }

        private List < rubro> mensaje;
        private int status;

        public List<rubro> getmensaje() {
            return mensaje;
        }

        public void setmensaje(List<rubro> mensaje) {
            this.mensaje = mensaje;
        }

        public int getstatus() {
            return status;
        }

        public void setstatus(int status) {
            this.status = status;
        }

    }


    public class listar_locales{

        public class local
        {
            private int Local_Codigo;
            private String Local_Nombre;
            private String   Local_Imagen;

            public int getLocal_Codigo() {
                return Local_Codigo;
            }

            public void setLocal_Codigo(int local_codigo) {
                Local_Codigo = local_codigo;
            }

            public String getLocal_Nombre() {
                return Local_Nombre;
            }

            public void setLocal_Nombre(String local_nombre) {
                Local_Nombre = local_nombre;
            }

            public String getLocal_Imagen() {  return Local_Imagen;  }

            public void setLocal_Imagen(String local_Imagen) {  Local_Imagen = local_Imagen;  }
        }

        private List < local> mensaje;
        private int status;

        public List<local> getmensaje() {
            return mensaje;
        }

        public void setmensaje(List<local> mensaje) {
            this.mensaje = mensaje;
        }

        public int getstatus() {
            return status;
        }

        public void setstatus(int status) {
            this.status = status;
        }

    }



    public class listar_productos{

        public class producto
        {
            private int producto_codigo;
            private String producto_nombre;
            private String producto_imagen;
            private String producto_precio;
            private String producto_medida;

            public int getProducto_Codigo() {
                return producto_codigo;
            }

            public void setProducto_Codigo(int local_codigo) {
                producto_codigo = local_codigo;
            }

            public String getProducto_Nombre() {
                return producto_nombre;
            }

            public void setProducto_Nombre(String local_nombre) {
                producto_nombre = local_nombre;
            }

            public String getProducto_Imagen() {  return producto_imagen;  }

            public void setProducto_Imagen(String local_Imagen) {  producto_imagen = local_Imagen;  }

            public String getProducto_precio() {
                return producto_precio;
            }

            public void setProducto_precio(String producto_precio) {
                this.producto_precio = producto_precio;
            }

            public String getProducto_medida() {
                return producto_medida;
            }

            public void setProducto_medida(String producto_medida) {
                this.producto_medida = producto_medida;
            }
        }

        private List < producto> mensaje;
        private int status;

        public List<producto> getmensaje() {
            return mensaje;
        }

        public void setmensaje(List<producto> mensaje) {
            this.mensaje = mensaje;
        }

        public int getstatus() {
            return status;
        }

        public void setstatus(int status) {
            this.status = status;
        }

    }



}
