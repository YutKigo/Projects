package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class SelectFloorView extends AppCompatActivity {
    private MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_floor_view);
    }

    //各回のselectRoomViewへの遷移処理: Intentで選択されたフロアを送信
    public void floor2Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 2);
        startActivity(intent);
    }
    public void floor3Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 3);
        startActivity(intent);
    }
    public void floor4Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 4);
        startActivity(intent);
    }
    public void floor5Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 5);
        startActivity(intent);
    }
    public void floor6Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 6);
        startActivity(intent);
    }
    public void floor7Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 7);
        startActivity(intent);
    }
    public void floor8Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 8);
        startActivity(intent);
    }
    public void floor9Tapped(View view){
        Intent intent = new Intent(this, SelectRoomView.class);
        intent.putExtra("selectedFloor", 9);
        startActivity(intent);
    }
    public void buckButtonTapped(View view){
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        myHotelApp.saveRoomManagement();
        finish();
    }
}