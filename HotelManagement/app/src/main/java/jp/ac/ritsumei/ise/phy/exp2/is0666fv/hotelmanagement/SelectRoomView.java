package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectRoomView extends AppCompatActivity {
    private RoomManagement roomManagement;
    private TextView floorDisplay;
    private TextView roomNumberDisplay[] = new TextView[15];
    private ImageButton roomButton[] = new ImageButton[15];
    private int selectedFloor;
    private MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room_view);
        /* Intentで選択されたフロアを取得する */
        selectedFloor = getIntent().getIntExtra("selectedFloor", 0);

        /* TextViewの取得とフロア表示 */
        floorDisplay = (TextView) findViewById(R.id.floorDisplay);
        floorDisplay.setText(selectedFloor + "F");

        /* 各部屋の部屋番号表示用のTextViewを取得 */
        roomNumberDisplay[0] = findViewById(R.id.r01Display);
        roomNumberDisplay[1] = findViewById(R.id.r02Display);
        roomNumberDisplay[2] = findViewById(R.id.r03Display);
        roomNumberDisplay[3] = findViewById(R.id.r04Display);
        roomNumberDisplay[4] = findViewById(R.id.r05Display);
        roomNumberDisplay[5] = findViewById(R.id.r06Display);
        roomNumberDisplay[6] = findViewById(R.id.r07Display);
        roomNumberDisplay[7] = findViewById(R.id.r08Display);
        roomNumberDisplay[8] = findViewById(R.id.r09Display);
        roomNumberDisplay[9] = findViewById(R.id.r10Display);
        roomNumberDisplay[10] = findViewById(R.id.r11Display);
        roomNumberDisplay[11] = findViewById(R.id.r12Display);
        roomNumberDisplay[12] = findViewById(R.id.r13Display);
        roomNumberDisplay[13] = findViewById(R.id.r14Display);
        roomNumberDisplay[14] = findViewById(R.id.r15Display);

        /* 各部屋番号を表示 */
        for(int i = 0; i < roomNumberDisplay.length; i++){
            roomNumberDisplay[i].setText(String.valueOf(selectedFloor * 100 + i + 1));
        }

        /* 各部屋のButtonを取得 */
        roomButton[0] = findViewById(R.id.roomButton1);
        roomButton[1] = findViewById(R.id.roomButton2);
        roomButton[2] = findViewById(R.id.roomButton3);
        roomButton[3] = findViewById(R.id.roomButton4);
        roomButton[4] = findViewById(R.id.roomButton5);
        roomButton[5] = findViewById(R.id.roomButton6);
        roomButton[6] = findViewById(R.id.roomButton7);
        roomButton[7] = findViewById(R.id.roomButton8);
        roomButton[8] = findViewById(R.id.roomButton9);
        roomButton[9] = findViewById(R.id.roomButton10);
        roomButton[10] = findViewById(R.id.roomButton11);
        roomButton[11] = findViewById(R.id.roomButton12);
        roomButton[12] = findViewById(R.id.roomButton13);
        roomButton[13] = findViewById(R.id.roomButton14);
        roomButton[14] = findViewById(R.id.roomButton15);




    }

    //onResume method:ページが開かれた時に必ず呼び出され, 視覚的効果の更新などを行う
    protected void onResume(){
        super.onResume();

        /* 各部屋のボタンにRoomStatusを画像表示 */
        for(int i = 0; i < roomButton.length; i++){
            /* MyHotelApplicationクラスのインスタンスを取得 */
            MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

            /* RoomManagementクラスのインスタンスを取得 */
            RoomManagement roomManagement = myHotelApp.getRoomManagement();

            /* roomNumberとフロア指定して, 変数roomに代入, さらにその部屋状態を参照できる */
            Room room = roomManagement.getRoomInformation(i+1, selectedFloor);

            /* 部屋の状態によって表示する画像を変更 */
            if(room.getStatus().equals("Vacant")){
                roomButton[i].setImageResource(R.drawable.vacant);
            }
            else if(room.getStatus().equals("Cleaning In Progress")){
                roomButton[i].setImageResource(R.drawable.cleaning_in_progress);
            }
            else if(room.getStatus().equals("Customer Staying")){
                roomButton[i].setImageResource(R.drawable.customer_staying);
            }
            else if(room.getStatus().equals("CheckedOUT")){
                roomButton[i].setImageResource(R.drawable.checked_out);
            }
            else if(room.getStatus().equals("Inspection Required")){
                roomButton[i].setImageResource(R.drawable.inspection_required);
            }
            else if(room.getStatus().equals("Inspection In Progress")){
                roomButton[i].setImageResource(R.drawable.inspection_in_progress);
            }


        }
    }


    public void roomButtonTapped(View view){
        Intent intent = new Intent(this, RoomSetupView.class);

        /* 押下されたボタンのIDによってIntentで送る内容を変更 */
        int intentRoomNumber = 0;
        int buttonID = view.getId();
        if(buttonID == R.id.roomButton1){
            intentRoomNumber = 1;
        }
        else if(buttonID == R.id.roomButton2){
            intentRoomNumber = 2;
        }
        else if(buttonID == R.id.roomButton3){
            intentRoomNumber = 3;
        }
        else if(buttonID == R.id.roomButton4){
            intentRoomNumber = 4;
        }
        else if(buttonID == R.id.roomButton5){
            intentRoomNumber = 5;
        }
        else if(buttonID == R.id.roomButton6){
            intentRoomNumber = 6;
        }
        else if(buttonID == R.id.roomButton7){
            intentRoomNumber = 7;
        }
        else if(buttonID == R.id.roomButton8){
            intentRoomNumber = 8;
        }
        else if(buttonID == R.id.roomButton9){
            intentRoomNumber = 9;
        }
        else if(buttonID == R.id.roomButton10){
            intentRoomNumber = 10;
        }
        else if(buttonID == R.id.roomButton11){
            intentRoomNumber = 11;
        }
        else if(buttonID == R.id.roomButton12){
            intentRoomNumber = 12;
        }
        else if(buttonID == R.id.roomButton13){
            intentRoomNumber = 13;
        }
        else if(buttonID == R.id.roomButton14){
            intentRoomNumber = 14;
        }
        else if(buttonID == R.id.roomButton15){
            intentRoomNumber = 15;
        }

        intent.putExtra("selectedFloor", selectedFloor);
        intent.putExtra("selectedRoomNumber", intentRoomNumber);
        startActivity(intent);
    }

    public void backButtonTapped(View view){
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        myHotelApp.saveRoomManagement();
        finish();

    }
}