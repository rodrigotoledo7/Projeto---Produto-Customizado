package com.example.pagamentodecompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button bttotal, btpagar;
    RadioGroup grupo, grupo2;
    CheckBox cbarroz, cbcarne, cbpao, cbleite, cbovos;
    RadioButton rbdesconto, rbcinco, rbdez, rbquinze;
    EditText edtvalor;
    TextView tvvalor;
    double total, desconto;
    String txtitem, saida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bttotal = findViewById(R.id.bttotal);
        btpagar = findViewById(R.id.btpagar);
        grupo = findViewById(R.id.grupo);
        grupo2 = findViewById(R.id.grupo2);
        cbarroz = findViewById(R.id.cbarroz);
        cbcarne = findViewById(R.id.cbcarne);
        cbpao = findViewById(R.id.cbpao);
        cbleite = findViewById(R.id.cbleite);
        cbovos = findViewById(R.id.cbovos);
        rbdesconto = findViewById(R.id.rbdesconto);
        rbcinco = findViewById(R.id.rbcinco);
        rbdez = findViewById(R.id.rbdez);
        rbquinze = findViewById(R.id.rbquinze);
        edtvalor = findViewById(R.id.edtvalor);
        tvvalor = findViewById(R.id.tvvalor);

        bttotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total=0;
                txtitem="";
                if (cbarroz.isChecked()) {
                    total +=350; txtitem+="Slackline\n";
                }
                if (cbcarne.isChecked()) {
                    total +=1230; txtitem+="Patins\n";
                }
                if (cbpao.isChecked()) {
                    total +=2200; txtitem+="Sk8\n";
                }
                if (cbleite.isChecked()) {
                    total +=9.5; txtitem+="Energéticos\n";
                }
                if (cbovos.isChecked()) {
                    total +=7.5; txtitem+="Barras de Cereal\n";
                }
                tvvalor.setText(String.format("Valor: R$%5.2f",total));
            }
        });
        grupo2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                desconto=0;
                saida="";
                if (rbdesconto.isChecked()) {
                    saida="Pagamento sem desconto";
                    desconto = (total*0);
                }
                else if (rbcinco.isChecked()) {
                    saida="Pagamento com 5% de desconto";
                    desconto = (total*5/100);
                }
                else if (rbdez.isChecked()) {
                    saida="Pagamento com 10% de desconto";
                    desconto = (total*10/100);
                }
                else if (rbquinze.isChecked()) {
                    saida="Pagamento com 15% de desconto";
                desconto = (total*15/100);
                }
            }
        });

        btpagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtvalor.getText().toString().trim().equals(""))
                {
                    Toast.makeText(MainActivity.this, "Valor incompatível com a compra!", Toast.LENGTH_SHORT).show();
                }
                else {
                    double valor = Double.parseDouble(edtvalor.getText().toString());
                    double forma = (total-desconto);
                    double troco = (valor-forma);
                    if (valor >= 0 && valor < forma)
                    {
                        Toast.makeText(MainActivity.this, "Valor incompatível com a compra!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setIcon(R.mipmap.sacola);
                        alerta.setTitle("Resumo da compra");
                        String text = String.format("Itens no carrinho:\n" +
                                        "%sValor total da compra: R$%5.2f\n" +
                                        "Valor pago: R$%5.2f\n%s\n" +
                                        "Desconto:R$%5.2f\n" +
                                        "Valor com desconto:" +
                                        "R$%5.2f\n" +
                                        "Troco: R$%5.2f\n",
                                txtitem, total, valor, saida, desconto,forma, troco);
                        alerta.setMessage(text);
                        alerta.setNeutralButton("OK", null);
                        alerta.show();
                    }
                }
            }
        });
    }
}