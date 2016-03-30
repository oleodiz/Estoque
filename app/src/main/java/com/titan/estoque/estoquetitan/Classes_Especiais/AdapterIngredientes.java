package com.titan.estoque.estoquetitan.Classes_Especiais;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.titan.estoque.estoquetitan.Activitys.InformacaoIngredienteActivity;
import com.titan.estoque.estoquetitan.Activitys.LoginActivity;
import com.titan.estoque.estoquetitan.Objetos.Imagem;
import com.titan.estoque.estoquetitan.Objetos.Ingrediente;
import com.titan.estoque.estoquetitan.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Leonardo on 07/03/2016.
 */
public class AdapterIngredientes extends RecyclerView.Adapter<AdapterIngredientes.IngredienteViewHolder> {
    List<Ingrediente> ingredientes;
    Context context;
    DecimalFormat df;

    public AdapterIngredientes(List<Ingrediente> ingredientes, Context context){
        this.ingredientes = ingredientes;
        this.context = context;
        df = new DecimalFormat("00.00");
    }

    public static class IngredienteViewHolder extends RecyclerView.ViewHolder {

        CardView card_ingrediente;
        TextView txt_descricao;
        TextView txt_idIngrediente;
        TextView txt_quantidade;
        TextView txt_status;
        TextView txt_entrada;
        TextView txt_saida;
        ImageView img_ingrediente;
        ProgressBar pro_carregaImg;
        Button btn_entrada;
        Button btn_saida;
        Button btn_informacao;
        IngredienteViewHolder(View itemView) {
            super(itemView);
            card_ingrediente = (CardView)itemView.findViewById(R.id.card_ingrediente);
            txt_idIngrediente = (TextView)itemView.findViewById(R.id.txt_idIngrediente);
            txt_descricao = (TextView)itemView.findViewById(R.id.txt_descricao);
            txt_quantidade = (TextView)itemView.findViewById(R.id.txt_quantidade);
            txt_status = (TextView)itemView.findViewById(R.id.txt_status);
            txt_entrada = (TextView)itemView.findViewById(R.id.txt_entrada);
            txt_saida = (TextView)itemView.findViewById(R.id.txt_saida);
            img_ingrediente = (ImageView)itemView.findViewById(R.id.img_ingrediente);
            pro_carregaImg = (ProgressBar)itemView.findViewById(R.id.pro_carregaImg);
            btn_entrada = (Button)itemView.findViewById(R.id.btn_entrada);
            btn_saida = (Button)itemView.findViewById(R.id.btn_saida);
            btn_informacao = (Button)itemView.findViewById(R.id.btn_informacao);
        }
    }

    @Override
    public IngredienteViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_ingrediente, viewGroup, false);
        IngredienteViewHolder pvh = new IngredienteViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(IngredienteViewHolder holder, final int i) {
        holder.txt_idIngrediente.setText(ingredientes.get(i).id_ingrediente+"");
        holder.txt_descricao.setText(ingredientes.get(i).descricao);
        String quantidade = ingredientes.get(i).quantidade + ingredientes.get(i).unidade;
        holder.txt_quantidade.setText(quantidade);

        switch (ingredientes.get(i).id_status_estoque)
        {
            case 1:{
                holder.txt_status.setText(ingredientes.get(i).status);
                holder.txt_status.setTextColor(Color.rgb(218, 83, 44));
                holder.txt_status.setVisibility(View.VISIBLE);
                break;
            }
            case 2:{
                holder.txt_status.setText(ingredientes.get(i).status);
                holder.txt_status.setTextColor(Color.rgb(255, 196, 13));
                holder.txt_status.setVisibility(View.VISIBLE);
                break;
            }
        }

        new CarregaImagem().executeOnExecutor(Executors.newFixedThreadPool(4), ingredientes.get(i).id_imagem, holder);

        if(ingredientes.get(i).entrada) {
            holder.txt_entrada.setTextColor(Color.GREEN);
            holder.txt_entrada.setVisibility(View.VISIBLE);
            String valorMult = df.format(ingredientes.get(i).valorEntrada *ingredientes.get(i).quantidadeEntrada);
            String txt = "▼ +" + ingredientes.get(i).quantidadeEntrada + ingredientes.get(i).unidade + "  (R$" + valorMult + ")";
            holder.txt_entrada.setText(txt);

            // ▲
        }
        else
            holder.txt_entrada.setVisibility(View.GONE);

        if(ingredientes.get(i).saida) {
            holder.txt_saida.setTextColor(Color.RED);
            holder.txt_saida.setVisibility(View.VISIBLE);
            String txt = "▲ -" + ingredientes.get(i).quantidadeSaida+ ingredientes.get(i).unidade  ;
            holder.txt_saida.setText(txt);
        }
        else
            holder.txt_saida.setVisibility(View.GONE);

        holder.btn_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.telaEstoque.clickIngredienteEntrada(ingredientes.get(i).id_ingrediente);
            }
        });

        holder.btn_saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.telaEstoque.clickIngredienteSaida(ingredientes.get(i).id_ingrediente);
            }
        });

        holder.btn_informacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InformacaoIngredienteActivity.class);
                Bundle b = new Bundle();
                b.putInt("IdIngrediente", ingredientes.get(i).id_ingrediente);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        holder.itemView.setTag(ingredientes.get(i));

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
            if (img == null && LoginActivity.c != null)
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
