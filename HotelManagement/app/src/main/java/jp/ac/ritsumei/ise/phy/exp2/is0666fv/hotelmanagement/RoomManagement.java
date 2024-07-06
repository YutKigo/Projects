package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

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


}
