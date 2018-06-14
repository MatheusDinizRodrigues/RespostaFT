package com.example.matheus.respostaft;

import android.app.Activity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Principal extends Activity {
    private GraphView grafico;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        grafico = (GraphView) findViewById(R.id.graph);
        grafico.setTitle("Titulo do Grafico");

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0.5,0.36),
                new DataPoint(1,0.47),
              //  new DataPoint(1.5,0.48),
        });
        grafico.addSeries(series);
    }
}
