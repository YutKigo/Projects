package jp.ac.ritsumei.ise.phy.exp2.is0666fv.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class GameActivity2 extends AppCompatActivity implements SensorEventListener {
    private SensorManager sManager;
    private Sensor accSensor;
    private float x, y, z;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        sManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE) ;
        accSensor = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ;
    }

    public void onEndTapped(){
        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent) ;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0] ;
            y = event.values[1] ;
            z = event.values[2] ;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void startSensing(){
        sManager.registerListener(this, accSensor, SensorManager.SENSOR_DELAY_UI);
    }
    public void stopSensing(){
        sManager.unregisterListener(this);
    }
    public float getAccX(){ return this.x; }
    public float getAccY(){ return this.y; }
    public float getAccZ(){ return this.z; }
}