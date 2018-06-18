package com.ml.gcastanon.desafio.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ml.gcastanon.desafio.Clases.Producto;
import com.ml.gcastanon.desafio.R;
import com.ml.gcastanon.desafio.viewProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ml.gcastanon.desafio.R.color.colorAcceptMp;

public class searchAdapter extends BaseAdapter {
    private ArrayList<Producto> dataList;
    private Activity activity;


    public searchAdapter(Activity activity,ArrayList<Producto> storeSource){
       this.dataList=storeSource;
       this.activity=activity;

    }

    @Override
    public int getCount(){
        return dataList.size();
    }

    @Override
    public Object getItem(int position){
        return  dataList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =layoutInflater.inflate(R.layout.item_producto,null);
        }

        final Producto producto= dataList.get(position);



        ImageView ivProducto = (ImageView)view.findViewById(R.id.ivProducto);
        Uri imagen = Uri.parse(producto.getUrlImag());
        Picasso.get().load(imagen).resize(300,300).into(ivProducto);


        TextView tvTitle= (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText(producto.getTitle());

        TextView tvPrice= (TextView) view.findViewById(R.id.tvPrice);
        tvPrice.setText("$"+producto.getPrice());

        TextView tvAcceptMp= (TextView) view.findViewById(R.id.tvAcceptMP);
        if (producto.isAcceptMp()){

        tvAcceptMp.setText(R.string.accept_Mp);
        tvAcceptMp.setTextColor(ContextCompat.getColor(activity.getApplicationContext(),R.color.colorAcceptMp));

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity.getApplicationContext(),viewProduct.class);
                i.putExtra("Title",producto.getTitle());
                i.putExtra("Price",producto.getPrice());
                i.putExtra("Image",producto.getUrlImag());
                i.putExtra("acceptMP",producto.isAcceptMp());
                i.putExtra("idProduct",producto.getIdProducto());
                activity.startActivity(i);
                Toast.makeText(activity.getApplicationContext(),"resultado"+producto.getTitle(),Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

}
