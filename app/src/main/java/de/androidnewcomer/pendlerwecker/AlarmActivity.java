package de.androidnewcomer.pendlerwecker;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AlarmActivity extends AppCompatActivity {
    Thread vibratorthr=new Thread(){
        public void run() {
            while (true){
                Vibrator vibrator=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                vibrator.vibrate(1000);
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        break;
                }
            }}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        final MediaPlayer ring= MediaPlayer.create(AlarmActivity.this,R.raw.biebbieb);

        try {
            ring.setLooping(true);
            ring.start();
        } catch (Exception e){}


        vibratorthr.start();


        Button stopAlarm = findViewById(R.id.stopAlarm);
        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                vibratorthr.interrupt();
                try {
                    ring.stop();
                } catch (Exception e) {}
                finish();
            }
        });
    }

}
