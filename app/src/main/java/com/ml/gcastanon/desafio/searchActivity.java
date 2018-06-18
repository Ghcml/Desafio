package com.ml.gcastanon.desafio;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ml.gcastanon.desafio.Adapters.searchAdapter;
import com.ml.gcastanon.desafio.Clases.Producto;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class searchActivity extends AppCompatActivity {
    private static  final String URL_BASE="https://api.mercadolibre.com/sites/MLA";
    private static  final String URL_SEARCH="/search?q=";
    private static ArrayList<Producto> listProducto = new ArrayList<>();

    private LinearLayout listViewProduct;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_search);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();

        listViewProduct = findViewById(R.id.listViewProduct);



       // buscador
        handleSearch();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_m:
                //start search dialog
                super.onSearchRequested();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void handleSearch() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);
            getListMeli(searchQuery,getApplicationContext());



        }else if(Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selectedSuggestionRowId =  intent.getDataString();
            //execution comes here when an item is selected from search suggestions
            //you can continue from here with user selected search item
            Toast.makeText(this, "selected search suggestion "+selectedSuggestionRowId,
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void getListMeli(String nombreProducto, final Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        listProducto.clear();
        String urlConsulta=URL_BASE+URL_SEARCH+nombreProducto;
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,urlConsulta,
                null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray =  response.getJSONArray("results");
                            for (int i= 0;i<10;i++){
                                JSONObject jsonProduct = jsonArray.getJSONObject(i);
                                Producto producto = new Producto(jsonProduct.getString("title"),
                                        jsonProduct.getString("price"),
                                        jsonProduct.getBoolean("accepts_mercadopago"),jsonProduct.getString("thumbnail"),jsonProduct.getString("id"));
                                listProducto.add(producto);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            e.getMessage();
                        }
                        searchAdapter adapter = new searchAdapter(activity,listProducto);

                        int cantAdapter = adapter.getCount();
                        for (int i = 0; i<cantAdapter; i++){
                            View item = adapter.getView(i,null,null);
                            item.setTag(i);
                            listViewProduct.addView(item);
                        }

                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, (CharSequence) error,Toast.LENGTH_LONG);

            }
        }
        );
        queue.add(request);

    }


}
