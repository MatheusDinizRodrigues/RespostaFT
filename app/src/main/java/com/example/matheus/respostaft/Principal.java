package com.example.matheus.respostaft;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Principal extends Activity implements View.OnClickListener{
    private GraphView grafico;
    private Spinner grauNumerador;
    private Spinner grauDenominador;
    private Spinner sinal;
    private LinearLayout layoutnumerador;
    private LinearLayout layoutdenominador;
    private Button ok;
    private Button geragrafico;
    private ArrayList<EditText> coeficientes;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        flag = false;
        coeficientes = new ArrayList<EditText>();
        grauNumerador = (Spinner)findViewById(R.id.spinner);
        grauDenominador = (Spinner)findViewById(R.id.spinner2);
        sinal = (Spinner)findViewById(R.id.spinner3);
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(this);
        layoutnumerador = (LinearLayout)findViewById(R.id.LayoutNumerador);
        layoutdenominador = (LinearLayout)findViewById(R.id.LayoutDenominador);
        layoutdenominador.removeAllViews();
        geragrafico = (Button)findViewById(R.id.btngera);
        geragrafico.setOnClickListener(this);
        geragrafico.setEnabled(false);
        grafico = (GraphView) findViewById(R.id.graph);
        grafico.setTitle("GMF");
    }

    @Override
    public void onClick(View view) {
        if(view == ok){
            geragrafico.setEnabled(true);
            grauNumerador.setEnabled(false);
            grauDenominador.setEnabled(false);
            sinal.setEnabled(false);
            ok.setEnabled(false);
           int graunumerador = 1+grauNumerador.getSelectedItemPosition();
           int graudenominador = 1+grauDenominador.getSelectedItemPosition();
           for(int i = graudenominador; i>=0;i--){
               EditText aux = new EditText(this);
               aux.setGravity(Gravity.LEFT);
               coeficientes.add(aux);
               layoutdenominador.addView(aux);
               if(i!=0){
                   TextView coeficiente = new TextView(this);
                   coeficiente.setGravity(Gravity.LEFT);
                   coeficiente.setText("S^"+(i)+"+");
                   layoutdenominador.addView(coeficiente);
               }



           }
        }
        if(view == geragrafico){
            if (flag == false){
                switch(1+grauDenominador.getSelectedItemPosition()){
                    case 1:
                        grafico.removeAllSeries();
                        grafico.addSeries(func1grau(Double.parseDouble(coeficientes.get(0).getText().toString()),Double.parseDouble(coeficientes.get(1).getText().toString())));
                        break;
                    case 2:
                        grafico.removeAllSeries();
                        grafico.addSeries(func2grau(Double.parseDouble(coeficientes.get(0).getText().toString()),Double.parseDouble(coeficientes.get(1).getText().toString()),Double.parseDouble(coeficientes.get(2).getText().toString())));
                        break;
                    case 3:
                        break;
                }
                flag = true;
                geragrafico.setText("Reiniciar");
            }
            else{
                geragrafico.setText("Gerar Grafico");
                geragrafico.setEnabled(false);
                coeficientes.removeAll(coeficientes);
                ok.setEnabled(true);
                grauDenominador.setEnabled(true);
                grauNumerador.setEnabled(true);
                sinal.setEnabled(true);
                layoutdenominador.removeAllViews();
                flag = false;
            }


        }
    }
    private LineGraphSeries<DataPoint> func1grau(double a,double b){
        DataPoint[] dados = new DataPoint[1000];
        double x = 0;
        if(a == 1 && b!=0){
            for(int i = 0; i<1000; i++){
                x = x +0.001;
                DataPoint d = new DataPoint(x,Math.pow(Math.E,-1*b*x));
                dados[i] = d;
            }
        }
        LineGraphSeries<DataPoint> resposta = new LineGraphSeries<DataPoint>(dados);
        return resposta;
    }
    private LineGraphSeries<DataPoint> func2grau(double a,double b, double c){
        double x1,x2;
        DataPoint[] dados = new DataPoint[500];
        x1 = (-b + Math.sqrt(b*b-(4*a*c)))/(2*a);
        x2 = (-b - Math.sqrt(b*b-(4*a*c)))/(2*a);
        if(x1 != x2){
            System.out.println("e diferente");
            double x = 0;
            double A = 1/(-1*x1+x2);
            double B=  1/(-1*x2+x1);
            for(int i = 0; i<500; i++){
                x = x+0.01;
                DataPoint d = new DataPoint(x,A*(Math.pow(Math.E,-1*x1*x))+B*(Math.pow(Math.E,-1*x2*x)));
                System.out.println(Math.pow(Math.E,-1*x1*x)+Math.pow(Math.E,-1*x2*x));
                dados[i]=d;
            }
            LineGraphSeries<DataPoint> resposta = new LineGraphSeries<DataPoint>(dados);
            return resposta;
        }
        else{
            double x = 0;
            for(int i = 0; i<500; i++){
                x = x+0.01;
                DataPoint d = new DataPoint(x,x*Math.pow(Math.E,-1*x1*x));
                dados[i]=d;
            }
            LineGraphSeries<DataPoint> resposta = new LineGraphSeries<DataPoint>(dados);
            return resposta;

        }

    }

}
