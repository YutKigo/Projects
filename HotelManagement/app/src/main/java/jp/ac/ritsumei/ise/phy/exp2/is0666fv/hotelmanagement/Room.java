package jp.ac.ritsumei.ise.phy.exp2.is0666fv.hotelmanagement;

import java.io.Serializable;


public class Room implements Serializable {
    //RoomクラスではSerializableインターフェースを実装することでRoomクラスのインスタンスをバイト列変換でき, 異なるクラス間でのIntentを介した受け渡しが可能になる

    /* Roomクラスの基本的情報に関するフィールド変数 */
    private int roomNumber; //部屋番号
    private String status; //部屋状態変数 初期値:vacant,
    private int cleaningStep; //清掃段階 客がいる場合は0

    /* RoomクラスのHousekeepingViewで使用されるフィールド変数 */
    private boolean isCleaningStartButtonTapped; //CleaningStartButtonが押されたかどうかを判定
    private boolean isCleaningFinishButtonTapped; //CleaningFinishButtonが押されたかどうかを判定
    private boolean isInspectionStartButtonTapped; //InspectionStartButtonが押されたかどうかを判定
    private boolean isInspectionFinishButtonTapped = true; //InspectionFinishButtonが押されたかどうか判定
    private String cleaningStartTime = " "; //清掃開始時刻を記録するString変数(画面遷移後も値を保持するためstaticに)
    private String cleaningFinishTime = " "; //清掃終了時刻を記録するString変数(画面遷移後も値を保持するためstaticに)
    private boolean isCheckedBED = false; //BEDのCheckBoxがチェックされたかどうかを判定
    private boolean isCheckedSHOWER = false; //SHOWERのCheckBoxがチェックされたかどうかを判定
    private String stuffName; //EditTextに入力される清掃者の名前
    private String failureEquipment = null; //EditTextに入力される設備不良報告
    private String lostProperty = null; //EditTextに入力される忘れ物報告

    //Constructor
    Room(int roomNumber){
        this.roomNumber = roomNumber;
        this.status = "Vacant";
        this.cleaningStep = 0;
        this.failureEquipment = null;
        this.lostProperty = null;
    }

    /* 各フィールド変数のGETメソッド */
    public int getRoomNumber(){
        return this.roomNumber;
    }
    public String getStatus(){
        return this.status;
    }
    public int getCleaningStep(){
        return this.cleaningStep;
    }

    public boolean isCleaningStartButtonTapped() {
        return isCleaningStartButtonTapped;
    }

    public boolean isCleaningFinishButtonTapped() {
        return isCleaningFinishButtonTapped;
    }

    public boolean isInspectionStartButtonTapped() {
        return isInspectionStartButtonTapped;
    }

    public boolean isInspectionFinishButtonTapped() {
        return isInspectionFinishButtonTapped;
    }

    public String getCleaningStartTime() {
        return cleaningStartTime;
    }

    public String getCleaningFinishTime() {
        return cleaningFinishTime;
    }

    public boolean isCheckedBED() {
        return isCheckedBED;
    }

    public boolean isCheckedSHOWER() {
        return isCheckedSHOWER;
    }

    public String getStuffName(){ return stuffName; }

    public String getFailureEquipment(){ return failureEquipment; }

    public String getLostProperty(){ return lostProperty; }


    /* 各フィールド変数のSETメソッド */
    public void changeStatus(String newStatus){
        this.status = newStatus;
    }
    public void changeCleaningStep(int newStep){
        this.cleaningStep = newStep;
    }

    public void setCleaningStartButtonTapped(boolean cleaningStartButtonTapped) {
        isCleaningStartButtonTapped = cleaningStartButtonTapped;
    }

    public void setCleaningFinishButtonTapped(boolean cleaningFinishButtonTapped) {
        isCleaningFinishButtonTapped = cleaningFinishButtonTapped;
    }

    public void setInspectionStartButtonTapped(boolean inspectionStartButtonTapped) {
        isInspectionStartButtonTapped = inspectionStartButtonTapped;
    }

    public void setInspectionFinishButtonTapped(boolean inspectionFinishButtonTapped) {
        isInspectionFinishButtonTapped = inspectionFinishButtonTapped;
    }

    public void setCleaningStartTime(String cleaningStartTime) {
        this.cleaningStartTime = cleaningStartTime;
    }

    public void setCleaningFinishTime(String cleaningFinishTime) {
        this.cleaningFinishTime = cleaningFinishTime;
    }

    public void setIsCheckedBED(boolean isCheckedBED) {
        this.isCheckedBED = isCheckedBED;
    }

    public void setIsCheckedSHOWER(boolean isCheckedSHOWER) {
        this.isCheckedSHOWER = isCheckedSHOWER;
    }

    public void setStuffName(String stuffName){
        this.stuffName = stuffName;
    }

    public void setFailureEquipment(String failureEquipment){
        this.failureEquipment = failureEquipment;
    }

    public void setLostProperty(String lostProperty){
        this.lostProperty = lostProperty;
    }

    public void initialRoom(){
        this.status = "Vacant";
        this.stuffName = "";
        this.cleaningStartTime = "";
        this.cleaningFinishTime = "";
        this.isCleaningStartButtonTapped = false; //CleaningStartButtonが押されたかどうかを判定
        this.isCleaningFinishButtonTapped = false; //CleaningFinishButtonが押されたかどうかを判定
        this.isInspectionStartButtonTapped = false; //InspectionStartButtonが押されたかどうかを判定
        this.isInspectionFinishButtonTapped = true;
        this.isCheckedBED = false; //BEDのCheckBoxがチェックされたかどうかを判定
        this.isCheckedSHOWER = false;
        this.lostProperty = null;
        this.failureEquipment = null;
    }
}
