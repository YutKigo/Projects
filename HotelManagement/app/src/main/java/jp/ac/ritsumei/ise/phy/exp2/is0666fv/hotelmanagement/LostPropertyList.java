package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LostPropertyList extends AppCompatActivity {

    private TextView listTextView2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_property_list);

        listTextView2 = findViewById(R.id.listTextView2);
    }



    protected void onResume() {
        super.onResume();

        listTextView2.append("\n"); //最初改行しておく

        /* 各部屋の情報にアクセスするため, RoomManagementを起動 */
        MyHotelApplication myHotelApp = (MyHotelApplication) getApplication();
        RoomManagement roomManagement = myHotelApp.getRoomManagement();

        /* forループで全部屋の忘れ物報告を参照 */
        for (int floor = 2; floor < 9; floor++) {
            for (int roomNumber = 1; roomNumber < 16; roomNumber++) {
                /* 部屋変数roomの忘れ物報告を参照する */
                Room room = roomManagement.getRoomInformation(roomNumber, floor);

                /* 忘れ物報告がnullでなければTextViewに表示 */
                String reportText = "";
                if(room.getLostProperty() != null){
                    reportText = room.getLostProperty() + "\n";
                    listTextView2.append(reportText);
                }
            }
        }
    }

    public void backButtonTapped(View view){
        finish();
    }
}