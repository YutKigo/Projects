package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HousekeepingView extends AppCompatActivity {
    //private  Room room;
    private int selectedFloor;
    private int selectedRoomNumber;
    private Button cleaningStartButton;
    private Button cleaningFinishButton;
    private Button inspectionStartButton;
    private Button inspectionFinishedButton;
    private TextView roomNumberDisplay; //RoomSetupViewのroomNumberDisplayと区別するため, 2としている
    private TextView cleaningStartTimeDisplay; //清掃開始の時刻を表示するTextView
    private TextView cleaningFinishTimeDisplay; //清掃終了の時刻を表示するTextView
    private CheckBox checkBED;
    private CheckBox checkSHOWER;
    private EditText nameEditText;

    private MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housekeeping_view);

        /* RoomSetupViewからのIntentを取得 */
        selectedFloor = getIntent().getIntExtra("selectedFloor", 0);
        selectedRoomNumber = getIntent().getIntExtra("selectedRoomNumber", 0);

        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* 部屋番号表示のTextViewを取得し部屋番号を表示 */
        roomNumberDisplay = findViewById(R.id.roomNumberDisplay);
        roomNumberDisplay.setText(String.valueOf(selectedFloor * 100 + selectedRoomNumber));

        /* 各種ボタンの取得と表示設定 */
        cleaningStartButton = findViewById(R.id.cleaningStartButton);
        cleaningFinishButton = findViewById(R.id.cleaningFinishedButton);
        inspectionStartButton = findViewById(R.id.inspectionStartButton);
        inspectionFinishedButton = findViewById(R.id.inspectionFinishedButton);

        /* 時刻表示のTextViewを取得 (表示はボタン押下後) */
        cleaningStartTimeDisplay = findViewById(R.id.cleaningStartTimeDisplay);
        cleaningFinishTimeDisplay = findViewById(R.id.cleaningFinishTimeDisplay);

        /* CheckBoxを取得 */
        checkBED = findViewById(R.id.checkBED);
        checkSHOWER = findViewById(R.id.checkSHOWER);
        checkBED.setChecked(room.isCheckedBED());
        checkSHOWER.setChecked(room.isCheckedSHOWER());

        /* CheckBoxが手動でチェックされたとき,画面遷移後も値を保持できるよう設定 */
        checkBED.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    room.setIsCheckedBED(true); //
                }

            }
        });
        checkSHOWER.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){
                    room.setIsCheckedSHOWER(true);
                }
            }
        });

        /* EditTextを取得 */
        nameEditText = findViewById(R.id.nameEditText);
    }

    public void onResume(){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        super.onResume();
        cleaningStartTimeDisplay.setText("Start: " + room.getCleaningStartTime());
        cleaningFinishTimeDisplay.setText("Finish: " + room.getCleaningFinishTime());

        /* ボタンの視覚設定変更 */
        /* 画面遷移が起こっても変更されないようにボタンが押されたときのbooleanで判定 */
        /* 各種boolean変数はstatic */
        cleaningStartButton.setEnabled(room.isInspectionFinishButtonTapped());
        cleaningFinishButton.setEnabled(room.isCleaningStartButtonTapped());
        inspectionStartButton.setEnabled(room.isCleaningFinishButtonTapped());
        inspectionFinishedButton.setEnabled(room.isInspectionStartButtonTapped());

        /* CheckBoxの設定変更 */
        checkBED.setChecked(room.isCheckedBED());
        checkSHOWER.setChecked(room.isCheckedSHOWER());

        /* 清掃者の名前入力を表示　*/
        nameEditText.setText(room.getStuffName());



    }



    public void cleaningStartButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* onCreate時のボタン視覚変更用boolean変数設定 */
        room.setCleaningStartButtonTapped(true); //this button
        room.setCleaningFinishButtonTapped(false);
        room.setInspectionStartButtonTapped(false);
        room.setInspectionFinishButtonTapped(false);

        /* ボタンの視覚設定変更 */
        cleaningStartButton.setEnabled(false);
        cleaningFinishButton.setEnabled(true);
        inspectionStartButton.setEnabled(false);
        inspectionFinishedButton.setEnabled(false);

        /* 部屋のステータス変更 */
        room.changeStatus("Cleaning In Progress");

        /* 清掃開始時刻の取得とテキスト表示 */
        room.setCleaningStartTime(new SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        cleaningStartTimeDisplay.setText("Start: " + room.getCleaningStartTime());
    }

    public void cleaningFinishButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* onCreate時のボタン視覚変更用boolean変数設定 */
        room.setCleaningStartButtonTapped(false);
        room.setCleaningFinishButtonTapped(true); //this button
        room.setInspectionStartButtonTapped(false);
        room.setInspectionFinishButtonTapped(false);

        /* ボタンの視覚設定変更 */
        cleaningStartButton.setEnabled(false);
        cleaningFinishButton.setEnabled(false);
        inspectionStartButton.setEnabled(true);
        inspectionFinishedButton.setEnabled(false);

        /* CheckBoxの設定変更 */
        room.setIsCheckedBED(true);
        room.setIsCheckedSHOWER(true);
        checkBED.setChecked(room.isCheckedBED());
        checkSHOWER.setChecked(room.isCheckedSHOWER()); // 清掃が完了したらfinishedにチェックを入れる

        /* 部屋のステータスの変更 */
        room.changeStatus("Inspection Required");

        /* 清掃終了時刻の取得とテキスト表示 */
        room.setCleaningFinishTime(new SimpleDateFormat("MM/dd HH:mm:ss", Locale.getDefault()).format(new Date()));
        cleaningFinishTimeDisplay.setText("Finish: " + room.getCleaningFinishTime());

    }

    public void inspectionStartButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* onCreate時のボタン視覚変更用boolean変数設定 */
        room.setCleaningStartButtonTapped(false);
        room.setCleaningFinishButtonTapped(false);
        room.setInspectionStartButtonTapped(true); //this button
        room.setInspectionFinishButtonTapped(false);

        /* ボタンの視覚設定変更 */
        cleaningStartButton.setEnabled(false);
        cleaningFinishButton.setEnabled(false);
        inspectionStartButton.setEnabled(false);
        inspectionFinishedButton.setEnabled(true);

        /* 部屋のステータスの変更 */
        room.changeStatus("Inspection In Progress");
    }

    public void inspectionFinishedButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* onCreate時のボタン視覚変更用boolean変数設定 */
        room.setCleaningStartButtonTapped(false);
        room.setCleaningFinishButtonTapped(false);
        room.setInspectionStartButtonTapped(false);
        room.setInspectionFinishButtonTapped(true); //this button

        /* ボタンの視覚設定変更 */
        cleaningStartButton.setEnabled(false);
        cleaningFinishButton.setEnabled(false);
        inspectionStartButton.setEnabled(false);
        inspectionFinishedButton.setEnabled(false);

        /* 部屋のステータス変更 */
        room.changeStatus("Vacant");

    }

    public void resetButtonTapped(View view){
        //初期設定にリセット

        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* 清掃開始・終了時刻のテキスト表示リセット */
        room.setCleaningStartTime(" ");
        room.setCleaningFinishTime(" ");
        cleaningStartTimeDisplay.setText("Start: " + room.getCleaningStartTime());
        cleaningFinishTimeDisplay.setText("Finish: " + room.getCleaningFinishTime());

        /* ボタンが押されたかの判定リセット */
        room.setCleaningStartButtonTapped(false);
        room.setCleaningFinishButtonTapped(false);
        room.setInspectionStartButtonTapped(false);
        room.setInspectionFinishButtonTapped(false);

        /* ボタンの視覚設定リセット */
        cleaningStartButton.setEnabled(true);
        cleaningFinishButton.setEnabled(false);
        inspectionStartButton.setEnabled(false);
        inspectionFinishedButton.setEnabled(false);

        /* CheckBoxのチェックをリセット */
        room.setIsCheckedBED(false);
        room.setIsCheckedSHOWER(false);
        checkSHOWER.setChecked(false);
        checkBED.setChecked(false);

        /* EditTextの内容をリセット */
        nameEditText.setText("");
    }

    /* 清掃者の名前入力を保持するためのlistener */
    public void editTextCompleteButtonTapped(View view){
        /* 選択された部屋を取得・変数roomに格納 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();
        Room room = roomManagement.getRoomInformation(selectedRoomNumber, selectedFloor);

        /* EditTextを取得し, roomのフィールド変数に格納 → onResumeでも使用可能に */
        nameEditText = findViewById(R.id.nameEditText);
        room.setStuffName(nameEditText.getText().toString());
    }


    public void backButtonTapped(View view){
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        myHotelApp.saveRoomManagement();
        finish();
    }
}