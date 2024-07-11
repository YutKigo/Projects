package jp.ac.ritsumei.ise.phy.exp2.is0666fv.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOneButtonTapped(View view) {
        Intent intent = new Intent(this, GameActivity.class) ;
        startActivity(intent) ;
    }

    public void onTwoButtonTapped(View view) {
        Intent intent = new Intent(this, GameActivity2.class) ;
        startActivity(intent) ;
    }
}