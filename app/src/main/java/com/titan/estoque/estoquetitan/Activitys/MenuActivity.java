package com.titan.estoque.estoquetitan.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;
import com.titan.estoque.estoquetitan.R;

public class MenuActivity extends AppCompatActivity {


    ButtonRectangle btn_entrada, btn_saida, btn_relatorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        btn_entrada = (ButtonRectangle) findViewById(R.id.btn_entrada);
        btn_saida = (ButtonRectangle) findViewById(R.id.btn_saida);
        btn_relatorio = (ButtonRectangle) findViewById(R.id.btn_relatotio);


        btn_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EntradaActivity.class);
                startActivity(intent);
            }
        });

        btn_saida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, EntradaActivity.class);
                startActivity(intent);
            }
        });
        btn_relatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
