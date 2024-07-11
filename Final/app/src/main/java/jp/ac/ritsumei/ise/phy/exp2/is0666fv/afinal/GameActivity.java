package jp.ac.ritsumei.ise.phy.exp2.is0666fv.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    //MySurfaceクラスでゲームが終了し, かつ画面がタッチされると呼び出され, activity_mainへ遷移する
    public void onEndTapped(){
        Intent intent = new Intent(this, MainActivity.class) ;
        startActivity(intent) ;
    }
}