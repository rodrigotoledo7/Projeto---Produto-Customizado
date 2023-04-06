package com.example.rolparkuor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class BancoDeDadosHelper extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO_DADOS = 1;
    private static final String NOME_BANCO_DADOS = "MeuBancoDeDados.db";

    private static final String TABELA_MEUS_DADOS = "meus_dados";
    private static final String COLUNA_TITULO = "titulo";
    private static final String COLUNA_NUMERO = "numero";
    private static final String COLUNA_RANKING = "ranking";

    public BancoDeDadosHelper(Context context) {
        super(context, NOME_BANCO_DADOS, null, VERSAO_BANCO_DADOS);
    }

    public void limparTodasTabelas() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        List<String> tables = new ArrayList<>();

        try {
            if (cursor.moveToFirst()) {
                do {
                    tables.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        for (String table : tables) {
            db.delete(table, null, null);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CRIAR_TABELA_MEUS_DADOS = "CREATE TABLE " + TABELA_MEUS_DADOS + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUNA_TITULO + " TEXT, " +
                COLUNA_NUMERO + " REAL, " +
                COLUNA_RANKING + " INTEGER)";
        db.execSQL(SQL_CRIAR_TABELA_MEUS_DADOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Este método é chamado quando a versão do banco de dados é atualizada.
        // Neste exemplo, não fazemos nada aqui.
    }

    public String getTabelaMeusDados() {
        return TABELA_MEUS_DADOS;
    }

    public String getColunaTitulo() {
        return COLUNA_TITULO;
    }

    public String getColunaNumero() {
        return COLUNA_NUMERO;
    }

    public String getColunaRanking() {
        return COLUNA_RANKING;
    }
}
