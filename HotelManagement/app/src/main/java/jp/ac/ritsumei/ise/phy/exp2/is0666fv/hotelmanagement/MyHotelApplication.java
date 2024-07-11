package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class MyHotelApplication extends Application {
    private RoomManagement roomManagement;

    private static final String PREFS_NAME = "MyHotelPrefs";
    private static final String KEY_ROOM_MANAGEMENT = "RoomManagement";

    //onCreate method : アプリが起動された時に最初に呼び出され, アプリ全体の初期化を行うメソッド
    public void onCreate(){
        super.onCreate();
        roomManagement = new RoomManagement();
        loadRoomManagement();
    }

    public RoomManagement getRoomManagement(){
        return this.roomManagement;
    }

    public void saveRoomManagement() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_ROOM_MANAGEMENT, roomManagement.toJson());
        editor.apply();
    }

    private void loadRoomManagement() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_ROOM_MANAGEMENT, null);
        if (json != null) {
            roomManagement = RoomManagement.fromJson(json);
        } else {
            roomManagement = new RoomManagement();
        }
    }

    public void initialize(){
        roomManagement.initialRoomManagement();
    }
}
