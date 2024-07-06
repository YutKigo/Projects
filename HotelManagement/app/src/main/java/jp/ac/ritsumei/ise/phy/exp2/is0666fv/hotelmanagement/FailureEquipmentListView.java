package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FailureEquipmentListView extends AppCompatActivity {
    private TextView listTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_failure_equipment_list_view);

        listTextView = findViewById(R.id.listTextView);
    }



    protected void onResume() {
        super.onResume();

        listTextView.append("\n"); //最初改行しておく

        /* 各部屋の情報にアクセスするため, RoomManagementを起動 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* forループで全部屋の設備不良報告を参照 */
        for (int floor = 2; floor < 9; floor++) {
            for (int roomNumber = 1; roomNumber < 16; roomNumber++) {
                /* 部屋変数roomの設備不良報告を参照する */
                Room room = roomManagement.getRoomInformation(roomNumber, floor);

                /* 設備不良報告がnullでなければTextViewに表示 */
                String reportText = "";
                if(room.getFailureEquipment() != null){
                    reportText = room.getFailureEquipment() + "\n";
                    listTextView.append(reportText);
                }
            }
        }
    }

    public void backButtonTapped(View view){
        finish();
    }



}