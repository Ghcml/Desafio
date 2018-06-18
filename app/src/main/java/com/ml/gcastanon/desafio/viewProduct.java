package com.ml.gcastanon.desafio;
// Import nativos
import android.app.ProgressDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
// Importo libreria volley para manejos de peticiones
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
//Importo libreria Picasso para manejo de imagens
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class viewProduct extends AppCompatActivity {
    private static  final String URL_BASE="https://api.mercadolibre.com";
    private static  final String URL_SEARCH="/items/";
    ImageView ivViewProduct;
    TextView tvDimensiones,tvMarca;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creo y inicializo pantalla de carga
        progressDialog = new ProgressDialog(viewProduct.this);
        progressDialog.setMessage("Cargando producto...");
        progressDialog.setTitle("Por favor espere");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        Bundle bundle= getIntent().getExtras();//Creo una burbuja para manejar los datos traidos de la anterior activicty

        TextView tvTitleVP = findViewById(R.id.tvTitleVP);
        assert bundle != null;
        tvTitleVP.setText(bundle.getString("Title"));
        TextView tvPriceVP = findViewById(R.id.tvPriceVP);
        tvPriceVP.setText("Precio: "+"$$"+bundle.getString("Price"));
        TextView tvMercadoPagoVP = findViewById(R.id.tvMercadoPagoVP);

        if (bundle.getBoolean("acceptMp")){
            tvMercadoPagoVP.setText(R.string.accept_Mp);
            tvMercadoPagoVP.setTextColor(ContextCompat.getColor(this.getApplicationContext(),R.color.colorAcceptMp));
        }
        //ejecuto consulta para obetener mas carateristicas del producto a partir de su ID
        getCaracteristica(bundle.getString("idProduct"));

    }

/*
* Metodo: getCaracteristicas
* Parametros: Id del producto
*
* */

    public void getCaracteristica(String idProducto){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String urlConsulta=URL_BASE+URL_SEARCH+idProducto;
        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, urlConsulta,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("pictures"); //Json que trae las url de imagenes
                            JSONArray jsonArrayAtr = response.getJSONArray("attributes");//Json que trae los atributos del producto
                            JSONObject jsonProductDetail = jsonArray.getJSONObject(0);

                            //Se deja uno pero no es una buena manejarlo ya que no todos los productos estan completos
                            JSONObject jsonProductAtr = jsonArrayAtr.getJSONObject(1);

                            ivViewProduct = findViewById(R.id.ivVP);
                            Picasso.get().load(jsonProductDetail.getString("url")).resize(
                                    500,500).centerInside().into(ivViewProduct); //Seteo la url a la Image View

                            String Valor = String.valueOf(response.getInt("sold_quantity"));
                            tvDimensiones= findViewById(R.id.tvDimensiones);
                            tvDimensiones.setText("Cantidades vendidos: "+Valor);//Seteo la cantidad vendida

                            tvMarca = findViewById(R.id.tvMarca);
                            tvMarca.setText("Marca: "+jsonProductAtr.getString("value_name")); //Seteo la marca

                            progressDialog.dismiss();//termino el progreso una vez que se carga todo
                        } catch (JSONException e) {
                            e.printStackTrace(); progressDialog.dismiss();
                        }
                    }
                },  new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Ah ocurrido un error vuelva a intentarlo",Toast.LENGTH_LONG).show();
            }
        });
    queue.add(request);
    }
}
