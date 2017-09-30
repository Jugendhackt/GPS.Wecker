package de.androidnewcomer.pendlerwecker;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        final MediaPlayer ring= MediaPlayer.create(AlarmActivity.this,R.raw.biebbieb);
        ring.setLooping(true);
        ring.start();
        

        Button stopAlarm = findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ring.stop();
                finish();
            }
        });
    }

}
