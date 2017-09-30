package de.androidnewcomer.pendlerwecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ParameterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);
        Button WelcomeButton = findViewById(R.id.button_parameter);
        WelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    EditText NGradedit = findViewById(R.id.NGrad);
                    EditText NMinutenedit = findViewById(R.id.NMinuten);
                    EditText NSekundenedit = findViewById(R.id.NSekunden);
                    EditText EGradedit = findViewById(R.id.EGrad);
                    EditText EMinutenedit = findViewById(R.id.EMinuten);
                    EditText ESekundenedit = findViewById(R.id.ESekunden);
                    EditText Meteredit = findViewById(R.id.Meter);

                    int NGrad = Integer.parseInt(NGradedit.getText().toString());
                    int NMinuten = Integer.parseInt(NMinutenedit.getText().toString());
                    int NSekunden = Integer.parseInt(NSekundenedit.getText().toString());
                    int EGrad = Integer.parseInt(EGradedit.getText().toString());
                    int EMinuten = Integer.parseInt(EMinutenedit.getText().toString());
                    int ESekunden = Integer.parseInt(ESekundenedit.getText().toString());
                    double meter = Double.parseDouble(Meteredit.getText().toString());

                    double lat = NMinuten / 60 + NSekunden / 3600 + NGrad;
                    double lon = EMinuten / 60 + ESekunden / 3600 + EGrad;

                    Intent intent = new Intent(getBaseContext(), Geo_Service.class);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lon", lon);
                    intent.putExtra("meter", meter);
                    startService(intent);

                } catch (NumberFormatException e) {
                    Toast.makeText(getBaseContext(), R.string.Fehlermeldung, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
