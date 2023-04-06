package com.example.rolparkuor;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener
    {

    private SensorManager sensorManager;
    private Sensor sensorGiroscopio;
    private TextView txtNumero;
    private Button btnDadosColetados;

    private BancoDeDadosHelper bancoDeDadosHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
        {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumero = findViewById(R.id.txtNumero);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorGiroscopio = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


            // Inicializa o banco de dados
        bancoDeDadosHelper = new BancoDeDadosHelper(this);
        db = bancoDeDadosHelper.getWritableDatabase();

        //bancoDeDadosHelper.limparTodasTabelas();

            Button btnDadosColetados = findViewById(R.id.btnDadosColetados);
            btnDadosColetados.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, DadosColetadosActivity.class);
                    startActivity(intent);
                }
            });
        }
        public void onSensorChanged(SensorEvent event)
            {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
                {
                int i = 1;
                float numero = event.values[2]; // Obtém o valor do eixo z do giroscópio
                txtNumero.setText(Float.toString(numero));

                if(numero != 0.0)
                    {
                    // Insere o valor no banco de dados
                    ContentValues valores = new ContentValues();
                    valores.put(bancoDeDadosHelper.getColunaTitulo(), "Meu título");
                    valores.put(bancoDeDadosHelper.getColunaNumero(), numero);
                    valores.put(bancoDeDadosHelper.getColunaRanking(), i);
                    long resultado = db.insert(bancoDeDadosHelper.getTabelaMeusDados(), null, valores);
                    i = i + 1;
                    }
                }
            }

    public void verDadosColetados(View view)
        {
        Intent intent = new Intent(this, DadosColetadosActivity.class);
        startActivity(intent);
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Não faz nada aqui
    }

    @Override
    protected void onResume()
        {
        super.onResume();
        sensorManager.registerListener(this, sensorGiroscopio, SensorManager.SENSOR_DELAY_NORMAL);
        }

    @Override
    protected void onPause()
        {
        super.onPause();
        sensorManager.unregisterListener(this);
        }
    }















































