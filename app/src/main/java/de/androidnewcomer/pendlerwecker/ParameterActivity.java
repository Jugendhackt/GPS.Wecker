package de.androidnewcomer.pendlerwecker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class ParameterActivity extends AppCompatActivity {

    int INTENT_MAP = 31;

    int PLACE_PICKER_REQUEST = 1;

    EditText NGradedit;
    EditText NMinutenedit;
    EditText NSekundenedit ;
    EditText EGradedit;
    EditText EMinutenedit;
    EditText ESekundenedit;
    EditText Meteredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        NGradedit = findViewById(R.id.NGrad);
        NMinutenedit = findViewById(R.id.NMinuten);
        NSekundenedit = findViewById(R.id.NSekunden);
        EGradedit = findViewById(R.id.EGrad);
        EMinutenedit = findViewById(R.id.EMinuten);
        ESekundenedit = findViewById(R.id.ESekunden);
        Meteredit = findViewById(R.id.Meter);

        final Button WelcomeButton = findViewById(R.id.button_parameter);

        Button MapButton = findViewById(R.id.mapButton);

        MapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getBaseContext(),MapActivity.class);
                startActivityForResult(intent, INTENT_MAP);



            }
        });

        WelcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Geo_Service.isGeoActive == true){
                    Toast.makeText(getBaseContext(),R.string.button_ausfuehren_disabled, Toast.LENGTH_LONG).show();
                }

                else {
                try {


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
                    Toast.makeText(getBaseContext(), R.string.Fehlermeldung, Toast.LENGTH_LONG).show();}
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_MAP) {
            if (resultCode == RESULT_OK) {

                Double langitude = data.getDoubleExtra("lang", 10.4040);
                Double longitude = data.getDoubleExtra("long", 10.4040);

                int langitudeInt = Integer.valueOf(langitude.intValue());
                int longitudeInt = Integer.valueOf(longitude.intValue());


                Double langitudeMinuten = (langitude - langitudeInt) * 60;
                int langitudeMinutenInt = Integer.valueOf(langitudeMinuten.intValue());

                Double longitudeMinuten = (longitude - longitudeInt) * 60;
                int longitudeMinutenInt = Integer.valueOf(longitudeMinuten.intValue());


                Double langitudeSekunden = (langitudeMinuten - langitudeMinutenInt) * 60;
                int langitudeSekundenInt = Integer.valueOf(langitudeSekunden.intValue());

                Double longitudeSekunden = (longitudeMinuten - longitudeMinutenInt) * 60;
                int longitudeSekundenInt = Integer.valueOf(longitudeSekunden.intValue());


                NGradedit.setText(String.valueOf(langitudeInt));
                EGradedit.setText(String.valueOf(longitudeInt));

                NMinutenedit.setText(String.valueOf(langitudeMinutenInt));
                EMinutenedit.setText(String.valueOf(longitudeMinutenInt));

                NSekundenedit.setText(String.valueOf(langitudeSekundenInt));
                ESekundenedit.setText(String.valueOf(longitudeSekundenInt));



               // NSekundenedit.setText(String.valueOf(langitudeSekundenInt));
               // ESekundenedit.setText(String.valueOf(longitudeSekundenInt));

            }
        }
    }





    }




