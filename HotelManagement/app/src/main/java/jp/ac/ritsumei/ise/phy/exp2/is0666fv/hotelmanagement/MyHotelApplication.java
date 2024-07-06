package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.app.Application;

public class MyHotelApplication extends Application {
    private RoomManagement roomManagement;

    //onCreate method : アプリが起動された時に最初に呼び出され, アプリ全体の初期化を行うメソッド
    public void onCreate(){
        super.onCreate();
        roomManagement = new RoomManagement();
    }
    public RoomManagement getRoomManagement(){
        return this.roomManagement;
    }
}
