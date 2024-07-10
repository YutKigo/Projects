package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomSetupView extends AppCompatActivity {
    private int selectedFloor;
    private int selectedRoomNumber;
    private TextView roomNumberDisplay;
    private TextView roomStatusDisplay;
    private Button checkedInButton;
    private Button checkedOutButton;
    private Button cleaningStartButton;
    private EditText failureEquipmentEditText;
    private EditText lostPropertyEditText;
    private MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_setup_view);

        /* Intentで選択されたフロアと部屋番号を取得 各変数は継続して使用可能*/
        selectedFloor =  getIntent().getIntExtra("selectedFloor", 0);
        selectedRoomNumber = getIntent().getIntExtra("selectedRoomNumber", 0);

        /* 部屋番号表示のTextViewを取得し部屋番号を表示 */
        roomNumberDisplay = findViewById(R.id.roomNumberDisplay);
        roomNumberDisplay.setText(String.valueOf(selectedFloor * 100 + selectedRoomNumber));
    }

    //ページが開かれた時に必ず呼び出され, 視覚的効果の更新などを行う
    protected void onResume(){
        super.onResume();

        /* Applicationクラスのインスタンスを取得・その中のRoomManagementクラスのインスタンスを取得 */
        MyHotelApplication myHotelApp = (MyHotelApplication)getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* roomNumberとフロア指定して, 変数roomに代入, さらにその部屋状態を参照 */
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);
        roomStatusDisplay = (TextView) findViewById(R.id.roomStatusDisplay);
        roomStatusDisplay.setText(room.getStatus());
    }



    public void checkedInButtonTapped(View view){
        checkedInButton = findViewById(R.id.checkedInButton);

        /* MyHotelApplicationクラスのインスタンスを取得 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

        /* RoomManagementクラスのインスタンスを取得 */
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* roomNumberとフロア指定して, 変数roomに代入, さらにその部屋状態を参照できる */
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);
        room.changeStatus("Customer Staying");
        roomStatusDisplay = findViewById(R.id.roomStatusDisplay);
        roomStatusDisplay.setText("Customer Staying");
    }

    public void checkedOutButtonTapped(View view){
        checkedOutButton = findViewById(R.id.checkedOutButton);
        /* MyHotelApplicationクラスのインスタンスを取得 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

        /* RoomManagementクラスのインスタンスを取得 */
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* roomNumberとフロア指定して, 変数roomに代入, さらにその部屋状態を参照できる */
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);
        room.changeStatus("CheckedOUT");
        roomStatusDisplay = findViewById(R.id.roomStatusDisplay);
        roomStatusDisplay.setText("Checked OUT");
    }

    public void houseKeepingButtonTapped(View view){
        //cleaningStartButton = findViewById(R.id.houseKeepingButton);
        //cleaningStartButton.setText("Cleaning In Progress");
        //roomStatusDisplay = findViewById(R.id.roomStatusDisplay);
        //roomStatusDisplay.setText("Cleaning In Progress");

        /* MyHotelApplicationクラスのインスタンスを取得 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

        /* RoomManagementクラスのインスタンスを取得 */
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* roomNumberとフロア指定して, 変数roomに代入, さらにその部屋状態を参照できる */
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);
        //room.changeStatus("Cleaning In Progress");
        Intent intent = new Intent(this, HousekeepingView.class);
        intent.putExtra("selectedFloor", selectedFloor);
        intent.putExtra("selectedRoomNumber", selectedRoomNumber);
        startActivity(intent);
    }

    public void failureEquipmentReportButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* 報告時の日付を取得, String型に変換 */
        String reportedDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        /* 設備不良報告EditTextを取得し, 選択された部屋の設備不良フィールド変数に日付と部屋番号と共に格納 */
        failureEquipmentEditText = findViewById(R.id.FailureEquipmentEditText);
        String text = failureEquipmentEditText.getText().toString();
        room.setFailureEquipment(String.valueOf(selectedFloor * 100 + selectedRoomNumber) + ": " + reportedDate + ": " + text);

        /* 設備不良報告に成功すると, EditTextには何も表示しない */
        failureEquipmentEditText.setText("");
    }

    public void lostPropertyReportButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* 報告時の日付を取得, String型に変換 */
        String reportedDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());


        /* 忘れ物報告EditTextを取得し, 選択された部屋の忘れ物フィールド変数に日付と共に格納 */
        lostPropertyEditText = findViewById(R.id.LostPropertyEditText);
        String text = lostPropertyEditText.getText().toString();
        room.setLostProperty(String.valueOf(selectedFloor * 100 + selectedRoomNumber) + ": " + reportedDate + ": " + text);

        /* 忘れ物報告に成功すると, EditTextには何も表示しない */
        lostPropertyEditText.setText("");

    }

    public void backButtonTapped(View view){
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        myHotelApp.saveRoomManagement();
        finish();
    }
}