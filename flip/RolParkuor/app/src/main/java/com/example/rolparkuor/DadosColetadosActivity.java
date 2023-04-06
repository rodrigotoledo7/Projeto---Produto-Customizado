package com.example.rolparkuor;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DadosColetadosActivity extends AppCompatActivity {

    private BancoDeDadosHelper bancoDeDadosHelper;
    private SQLiteDatabase db;
    private ListView listaDadosColetados;
    private Button btnVoltar;


    public DadosColetadosActivity() {
        // construtor padrão sem argumentos
    }

    /*public DadosColetadosActivity(View view) {
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_coletados);

        bancoDeDadosHelper = new BancoDeDadosHelper(this);
        db = bancoDeDadosHelper.getReadableDatabase();

        Cursor cursor = db.query(
                bancoDeDadosHelper.getTabelaMeusDados(),
                null,
                null,
                null,
                null,
                null,
                null
        );

        listaDadosColetados = findViewById(R.id.listaDadosColetados);
        ArrayList<String> dados = new ArrayList<String>();

        if (cursor.moveToFirst()) {
            do {
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(bancoDeDadosHelper.getColunaTitulo()));
                float numero = cursor.getFloat(cursor.getColumnIndexOrThrow(bancoDeDadosHelper.getColunaNumero()));
                int ranking = cursor.getInt(cursor.getColumnIndexOrThrow(bancoDeDadosHelper.getColunaRanking()));
                String dado = titulo + ": " + Float.toString(numero) + " (" + Integer.toString(ranking) + ")";
                dados.add(dado);
            } while (cursor.moveToNext());
        } else {
            dados.add("Nenhum dado coletado ainda.");
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_dado, R.id.titulo, dados);

        listaDadosColetados.setAdapter(adapter);

        Button btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crie um Intent para iniciar a MainActivity
                Intent intent = new Intent(DadosColetadosActivity.this, MainActivity.class);
                // Inicie a MainActivity
                startActivity(intent);
                // Encerre a DadosColetadosActivity
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }
}