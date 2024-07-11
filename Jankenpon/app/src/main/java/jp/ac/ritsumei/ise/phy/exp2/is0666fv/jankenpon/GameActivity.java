package jp.ac.ritsumei.ise.phy.exp2.is0666fv.jankenpon;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    public void onExitTapped(View view){
        finish();
    }

    public void onRockTapped(View view){
        //myHand = rock
        ImageView myHand = (ImageView) findViewById(R.id.myHand);
        myHand.setImageResource(R.drawable.rock);

        //otherHand = random
        Random random = new Random();
        int r = random.nextInt(3);
        ImageView otherHand = (ImageView) findViewById(R.id.otherHand);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        if(r == 0){
            otherHand.setImageResource(R.drawable.rock);
            textView2.setText(String.format("あいこでしたー"));
        }
        else if(r == 1){
            otherHand.setImageResource(R.drawable.scissors);
            textView2.setText(String.format("あなたの勝ち!"));
        }
        else if (r == 2){
            otherHand.setImageResource(R.drawable.paper);
            textView2.setText(String.format("あなたの負け..."));
        }
    }

    public void onScissorsTapped(View view){
        //myHand = rock
        ImageView myHand = (ImageView) findViewById(R.id.myHand);
        myHand.setImageResource(R.drawable.scissors);

        //otherHand = random
        Random random = new Random();
        int r = random.nextInt(3);
        ImageView otherHand = (ImageView) findViewById(R.id.otherHand);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        if(r == 0){
            otherHand.setImageResource(R.drawable.rock);
            textView2.setText(String.format("あなたの負け..."));
        }
        else if(r == 1){
            otherHand.setImageResource(R.drawable.scissors);
            textView2.setText(String.format("あいこでしたー"));
        }
        else if (r == 2){
            otherHand.setImageResource(R.drawable.paper);
            textView2.setText(String.format("あなたの勝ち!"));
        }
    }

    public void onPaperTapped(View view){
        //myHand = rock
        ImageView myHand = (ImageView) findViewById(R.id.myHand);
        myHand.setImageResource(R.drawable.paper);

        //otherHand = random
        Random random = new Random();
        int r = random.nextInt(3);
        ImageView otherHand = (ImageView) findViewById(R.id.otherHand);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        if(r == 0){
            otherHand.setImageResource(R.drawable.rock);
            textView2.setText(String.format("あなたの勝ち!"));
        }
        else if(r == 1){
            otherHand.setImageResource(R.drawable.scissors);
            textView2.setText(String.format("あなたの負け..."));
        }
        else if (r == 2){
            otherHand.setImageResource(R.drawable.paper);
            textView2.setText(String.format("あいこでしたー"));
        }
    }
}