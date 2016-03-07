package com.titan.estoque.estoquetitan.Classes_Especiais;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.titan.estoque.estoquetitan.Activitys.LoginActivity;
import com.titan.estoque.estoquetitan.Objetos.Imagem;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.R;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Leonardo on 07/03/2016.
 */
public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.IngredienteViewHolder> {
    List<Ingrediente> ingredientes;
    Context context;
    public AdapterIngredientes(List<Ingrediente> ingredientes, Context context){
        this.ingredientes = ingredientes;
    }

    public static class IngredienteViewHolder extends RecyclerView.ViewHolder {
        CardView card_ingrediente;
        TextView txt_descricao;
        TextView txt_quantidade;
        TextView txt_status;
        ImageView img_ingrediente;
        ProgressBar pro_carregaImg;
        IngredienteViewHolder(View itemView) {
            super(itemView);
            card_ingrediente = (CardView)itemView.findViewById(R.id.card_ingrediente);
            txt_descricao = (TextView)itemView.findViewById(R.id.txt_descricao);
            txt_quantidade = (TextView)itemView.findViewById(R.id.txt_quantidade);
            txt_status = (TextView)itemView.findViewById(R.id.txt_status);
            img_ingrediente = (ImageView)itemView.findViewById(R.id.img_ingrediente);
            pro_carregaImg = (ProgressBar)itemView.findViewById(R.id.pro_carregaImg);
        }
    }

    @Override
    public IngredienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_ingrediente, viewGroup, false);
        IngredienteViewHolder pvh = new IngredienteViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(IngredienteViewHolder holder, int i) {
        holder.txt_descricao.setText(ingredientes.get(i).descricao);
        String quantidade = ingredientes.get(i).quantidade + ingredientes.get(i).unidade;
        holder.txt_quantidade.setText(quantidade);
        switch (ingredientes.get(i).id_status_estoque)
        {
            case 1:{
                holder.txt_status.setText(ingredientes.get(i).status);
                holder.txt_status.setTextColor(Color.rgb(218,83,44));
                break;
            }
            case 2:{
                holder.txt_status.setText(ingredientes.get(i).status);
                holder.txt_status.setTextColor(Color.rgb(255,196,13));
                break;
            }
        }

        new CarregaImagem().executeOnExecutor(Executors.newFixedThreadPool(4),ingredientes.get(i).id_imagem,holder );


    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private class CarregaImagem extends AsyncTask<Object, Void, Void> {
        Imagem img;
        IngredienteViewHolder holder;
        @Override
        protected Void doInBackground(Object... id) {

            img = LoginActivity.obterImagem((int) id[0]);
            if (img == null)
                img = LoginActivity.c.getImagem((int)id[0]);
            holder = (IngredienteViewHolder)id[1];
            publishProgress();

            return null;
        }
        @Override
        protected void onProgressUpdate(Void... progress) {

            if (img != null && img.id_imagem != 0) {
                LoginActivity.imagens.add(img);
                holder.img_ingrediente.setImageBitmap(decodeBase64(img.imagem));
            }
            holder.pro_carregaImg.setVisibility(View.INVISIBLE);

        }
        @Override
        protected void onPostExecute(Void result) {

        }
    }

    @Override
    public int getItemCount() {
        return ingredientes.size();
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
