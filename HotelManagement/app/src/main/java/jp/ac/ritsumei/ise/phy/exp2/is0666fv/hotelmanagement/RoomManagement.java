package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class RoomManagement {
    private Room[][] rooms = new Room[8][15];
    RoomManagement(){
        /* 全ての部屋を定義し, 管理下に置く */
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 15; j++){
                rooms[i][j] = new Room((i+2) * 100 + j);
            }
        }
    }

    public Room getRoomInformation(int roomNumber, int floor){
        int roomIndex = roomNumber - 1;
        int floorIndex = floor - 2;
        return rooms[floorIndex][roomIndex];
    }

    public String checkRoomInformation(int roomNumber){
        int f = (roomNumber / 100)  - 2; //floorをroomNumberから求める
        int r = roomNumber % 100; //部屋の下二桁をroomNumberから求める
        return this.rooms[f][r].getStatus();
    }

    public void initialRoomManagement() {
        try {
            if (rooms == null) {
                Log.e("RoomManagement", "Rooms array is null");
                return;
            }
            for (int i = 0; i < rooms.length; i++) {
                if (rooms[i] == null) {
                    Log.e("RoomManagement", "Rooms array at index " + i + " is null");
                    continue;
                }
                for (int j = 0; j < rooms[i].length; j++) {
                    if (rooms[i][j] == null) {
                        Log.e("RoomManagement", "Room " + ((i + 2) * 100 + j) + " is null");
                    } else {
                        rooms[i][j].initialRoom();
                        Log.d("RoomManagement", "Room " + ((i + 2) * 100 + j) + " initialized");
                    }
                }
            }
            Log.d("RoomManagement", "All rooms initialized");
        } catch (Exception e) {
            Log.e("RoomManagement", "Error initializing rooms", e);
        }
    }


    // RoomManagementの状態をJSON形式に変換して保存
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this.rooms);
    }

    // JSON形式のデータからRoomManagementの状態を復元
    public static RoomManagement fromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken<Room[][]>() {}.getType();
        Room[][] rooms = gson.fromJson(json, type);
        RoomManagement roomManagement = new RoomManagement();
        roomManagement.rooms = rooms;
        return roomManagement;
    }




}
