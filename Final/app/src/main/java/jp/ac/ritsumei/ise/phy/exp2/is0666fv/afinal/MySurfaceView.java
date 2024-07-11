package jp.ac.ritsumei.ise.phy.exp2.is0666fv.afinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.location.Location;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    private SurfaceHolder sHolder;
    private Thread thread;
    private GameActivity gameActivity;
    private int touchCount = 0;
    private boolean isTouchUp = false; //タッチされたのち離されたことを判定
    private float touchDownX; //画面がタッチされたx座標
    private float touchDownY; //画面がタッチされたy座標
    private float touchUpX; //画面タッチが終了したx座標
    private float touchUpY; //画面タッチが終了したy座標
    private double touchAngle; //画面をタッチした点と終了したテントを結ぶ線分とx軸との偏角
    private long touchStartTime; //タッチされた時の時刻
    private long touchEndTime; //タッチが終了した時の時刻
    private float t; //押されていた時間
    private float x = 0; //手球のx座標
    private float y = 400; //手球のy座標
    private float radiusHand = 50; //手球の半径
    private float velocityX; //手球のx方向速度
    private float velocityY; //手球のy方向速度
    private boolean isInPocketHand = false; //手球がポケットに入ったことを判定
    private int n = 6; //的球の個数
    private Paint[] pc = new Paint[n]; //的球のPaint
    private float[] cx = new float[n]; //的球のx座標
    private float[] cy = new float [n]; //的球のy座標
    private float[] radiusTarget = new float[n]; //的球の半径
    private float[] velocityCX = new float[n]; //的球のx方向速度
    private float[] velocityCY = new float[n]; //的球のx方向速度
    private boolean[] isInPocket = new boolean[n]; //的球がポケットに入ったことを判定
    private float deceleration = 0.98f; //摩擦:減速度
    private boolean gameFinished = false; //ゲーム終了を判定

    public MySurfaceView(Context context) {
        super(context);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
    }

    private void initialize(){
        sHolder = getHolder();
        sHolder.addCallback(this);

        //ゲーム開始
        gameFinished = false;

        //的球の初期位置の設定
        cx[0] = 0;    cy[0] = -100;
        cx[1] = -50;  cy[1] = -200;
        cx[2] = 50;   cy[2] = -200;
        cx[3] = -100; cy[3] = -300;
        cx[4] = 0;    cy[4] = -300;
        cx[5] = 100;  cy[5] = -300;

        //的球のパラメータの初期化
        for(int i = 0; i < n; i++){
            radiusTarget[i] = 50;
            velocityCX[i] = 0;
            velocityCY[i] = 0;
            pc[i] = new Paint();
            isInPocket[i] = false;
        }

        //的球の色を個々に指定
        pc[0].setColor(Color.YELLOW);
        pc[1].setColor(Color.BLUE);
        pc[2].setColor(Color.RED);
        pc[3].setColor(0xFFFF00FF); //紫
        pc[4].setColor(0xFFFFA500); //オレンジ
        pc[5].setColor(Color.BLACK);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    public boolean onTouchEvent(MotionEvent event){
        //画面がタッチされたとき
        if(event.getAction() == MotionEvent.ACTION_DOWN){

            //画面タッチされた時刻, 座標を保存
            touchStartTime = System.currentTimeMillis();
            touchDownX = event.getX();
            touchDownY = event.getY();

            //ゲーム終了後の画面タッチを検知 → GameActivityクラスのonEndTappedを呼び出す
            //ゲーム終了後に画面に一回触れるとスタートの画面へ
            if(gameFinished == true){
                gameActivity.onEndTapped();
                touchCount = 0;
            }else{
                touchCount++;
            }
        }
        //画面タッチが終了した時
        if(event.getAction() == MotionEvent.ACTION_UP){
            touchEndTime = System.currentTimeMillis(); //画面タッチが終了した時の時刻を保存
            t = touchEndTime - touchStartTime; //開始と終了の差分からタッチ持続時間を計算
            touchUpX = event.getX();
            touchUpY = event.getY();
            isTouchUp = false;
            //画面タッチの点と画面タッチが終了した点を結んだ線とx軸との偏角
            touchAngle = Math.atan2(touchUpY - touchDownY, touchUpX - touchDownX);
            if(t > 50){
                //偏角に応じた長さの比を速度にそのまま適用
                //速度はタッチ持続時間に依存するようにtを関係させる
                velocityX = (float) ((t / 100) * Math.cos(touchAngle));
                velocityY = (float) ((t / 100) * Math.sin(touchAngle));
            }
            isTouchUp = true;
        }
        return true;
    }

    private void drawCanvas(){
        Canvas c = sHolder.lockCanvas() ;
        c.drawColor(0xFF008833) ; //背景色は深緑
        Paint p = new Paint() ; //手球のpaint
        p.setColor(Color.WHITE) ;
        Paint paintRect = new Paint(); //ポケットのpaint
        paintRect.setColor(Color.BLACK);
        //ポケットの描画
        c.drawRect(new Rect(0, 0, 120, 120), paintRect);
        c.drawRect(new Rect(c.getWidth()-120, 0, c.getWidth(), 120), paintRect);
        c.drawRect(new Rect(0, c.getHeight()-120, 120, c.getHeight()), paintRect);
        c.drawRect(new Rect(c.getWidth()-120, c.getHeight()-120, c.getWidth(), c.getHeight()), paintRect);
        c.drawRect(new Rect(0, c.getHeight()/2-60, 120, c.getHeight()/2+60), paintRect);
        c.drawRect(new Rect(c.getWidth()-120, c.getHeight()/2-60, c.getWidth(), c.getHeight()/2+60), paintRect);

        c.drawCircle(c.getWidth() / 2 + x, c.getHeight() / 2 + y, radiusHand, p); //手球の描画
        for(int i = 0; i < n; i++){
            c.drawCircle(c.getWidth() / 2 + cx[i], c.getHeight() / 2 + cy[i], radiusTarget[i], pc[i]); //的球の描画
        }

        //ゲームが進行中の処理
        if(gameFinished == false){
            //タッチされたのち離された時にのみ処理
            if(isTouchUp){
                //手球の処理
                //手球を指定の初速度で移動
                x += velocityX;
                y += velocityY;

                //手球を指定の減速度で減速
                velocityX = velocityX * deceleration;
                velocityY = velocityY * deceleration;


                //速度が小さくなったら速度を0にする 逆方向の移動でも制御可能なように絶対値をとる
                if(Math.abs(velocityX) < 0.1){ velocityX = 0;}
                if(Math.abs(velocityY) < 0.1){ velocityY = 0;}

                //画面端で跳ね返る
                //単純に速度を逆にすると画面端で円が振動してしまうので, 跳ね返る時に位置を調整
                if (x + radiusHand >= c.getWidth() / 2) {
                    x = c.getWidth() / 2 - radiusHand;
                    velocityX *= -1; // 速度を逆向きにする
                } else if (x - radiusHand <= (-1) * c.getWidth() / 2) {
                    x = (-1) * c.getWidth() / 2 + radiusHand;
                    velocityX *= -1; // 速度を逆向きにする
                }
                if (y + radiusHand >= c.getHeight() / 2) {
                    y = c.getHeight() / 2 - radiusHand;
                    velocityY *= -1; // 速度を逆向きにする
                } else if (y - radiusHand <= (-1) * c.getHeight() / 2) {
                    y = (-1) * c.getHeight() / 2 + radiusHand;
                    velocityY *= -1; // 速度を逆向きにする
                }

                //手球がポケットに入った時の処理
                if(x < -c.getWidth()/2 + 120 && y < -c.getHeight()/2 + 120){
                    radiusHand = 0;
                    isInPocketHand = true;
                }
                else if(c.getWidth()/2-120 < x && y < -c.getHeight()/2 + 120){
                    radiusHand = 0;
                    isInPocketHand = true;
                }
                else if(x < -c.getWidth()/2 + 120 && c.getHeight()/2-120 < y){
                    radiusHand = 0;
                    isInPocketHand = true;
                }
                else if(c.getWidth()/2-120 < x && c.getHeight()/2-120 < y){
                    radiusHand = 0;
                    isInPocketHand = true;
                }
                else if(-c.getWidth()/2 < x && x < -c.getWidth()/2 + 120 && -60 < y && y < 60){
                    radiusHand = 0;
                    isInPocketHand = true;
                }
                else if(c.getWidth()/2-120 < x && x < c.getWidth()/2 && -60 < y && y < 60){
                    radiusHand = 0;
                    isInPocketHand = true;
                }

                //的球の処理
                for(int i = 0; i < n; i++){
                    //的球を指定の初速度で移動
                    cx[i] += velocityCX[i];
                    cy[i] += velocityCY[i];

                    //的球を指定の減速度で減速
                    velocityCX[i] = velocityCX[i] * deceleration;
                    velocityCY[i] = velocityCY[i] * deceleration;

                    //速度が小さくなったら速度を0にする 逆方向の移動でも制御可能なように絶対値をとる
                    if(Math.abs(velocityCX[i]) < 0.1){ velocityCX[i] = 0;}
                    if(Math.abs(velocityCY[i]) < 0.1){ velocityCY[i] = 0;}

                    //画面端で跳ね返る
                    if (cx[i] + radiusTarget[i] >= c.getWidth() / 2) {
                        cx[i] = c.getWidth() / 2 - radiusTarget[i];
                        velocityCX[i] *= -1;
                    } else if (cx[i] - radiusTarget[i] <= (-1) * c.getWidth() / 2) {
                        cx[i] = (-1) * c.getWidth() / 2 + radiusTarget[i];
                        velocityCX[i] *= -1;
                    }
                    if (cy[i] + radiusTarget[i] >= c.getHeight() / 2) {
                        cy[i] = c.getHeight() / 2 - radiusTarget[i];
                        velocityCY[i] *= -1;
                    } else if (cy[i] - radiusTarget[i] <= (-1) * c.getHeight() / 2) {
                        cy[i] = (-1) * c.getHeight() / 2 + radiusTarget[i];
                        velocityCY[i] *= -1;
                    }

                    //手球と的球の衝突処理
                    //処理内容: 手球と的球の速度を衝突角度と運動エネルギー保存則を用いて更新
                    float distance = distance(x, y, cx[i], cy[i]);
                    if(distance <= 2 * radiusHand){
                        //衝突した時の円の中心同士の線分とx軸との偏角 → 衝突された方が進む方向
                        double directionAngle = Math.atan2(cy[i] - y, cx[i] - x);
                        //各方向の速度ベクトルから円のスピードを計算 v = (vx^2 + vy^2)^(1/2)
                        float velocity = (float)Math.sqrt(velocityX * velocityX + velocityY * velocityY);
                        //更新前の衝突した方の円の速度を保存しておく → 衝突された方の速度の更新に使用
                        float v_temp = velocity;
                        //各方向の速度の比から衝突角度を計算
                        double collisionAngle = Math.atan2(velocityY, velocityX);
                        //各方向の速度を更新
                        //衝突後は, 衝突した方の円の速度を減速させるため係数1/5をかける
                        velocityX = velocity * (float)Math.cos(collisionAngle - directionAngle) * 1/5;
                        velocityY = velocity * (float)Math.sin(collisionAngle - directionAngle) * 1/5;

                        //保存していた衝突した方の円の速度を衝突された方の円の速度に反映
                        float velocityC = v_temp;
                        //衝突された方が進む方向(directionAngle)へ速度を指定
                        velocityCX[i] = velocityC * (float)Math.cos(directionAngle);
                        velocityCY[i] = velocityC * (float)Math.sin(directionAngle);

                        //位置を微調整する
                        float overlap = (2 * radiusHand - distance) / 2;
                        x -= overlap * Math.cos(directionAngle);
                        y -= overlap * Math.sin(directionAngle);
                        cx[i] += overlap * Math.cos(directionAngle);
                        cy[i] += overlap * Math.sin(directionAngle);
                    }

                    //的球がポケットに入った時の処理
                    //ポケットに入った円がまだポケットに入っていない円に作用しないようにに入った円の座標値は画面外へ, さらに速度と半径を無くす
                    if(cx[i] < -c.getWidth()/2 + 120 && cy[i] < -c.getHeight()/2 + 120){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }
                    else if(c.getWidth()/2-120 < cx[i] && cy[i] < -c.getHeight()/2 + 120){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }
                    else if(cx[i] < -c.getWidth()/2 + 120 && c.getHeight()/2-120 < cy[i]){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }
                    else if(c.getWidth()/2-120 < cx[i] && c.getHeight()/2-120 < cy[i]){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }
                    else if(-c.getWidth()/2 < cx[i] && cx[i] < -c.getWidth()/2 + 120 && -60 < cy[i] && cy[i] < 60){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }
                    else if(c.getWidth()/2-120 < cx[i] && cx[i] < c.getWidth()/2 && -60 < cy[i] && cy[i] < 60){
                        radiusTarget[i] = 0;
                        isInPocket[i] = true;
                        velocityCX[i] = 0;
                        velocityCY[i] = 0;
                        cx[i] = 5000;
                        cy[i] = 5000;
                    }

                    //的球同士の衝突処理
                    //iは衝突する円, jは衝突される円
                    for (int j = 0; j < n; j++) {
                        if (i != j) { // 自身との衝突を避ける
                            float distanceTargets = distance(cx[i], cy[i], cx[j], cy[j]);
                            if (distanceTargets <= 2 * radiusTarget[i]) {
                                //衝突した時の円の中心同士の線分とx軸との偏角 → 衝突された方が進む方向
                                double directionAngle = Math.atan2(cy[j] - cy[i], cx[j] - cx[i]);
                                //各方向の速度ベクトルから円のスピードを計算 v = (vx^2 + vy^2)^(1/2)
                                float velocityCi = (float) Math.sqrt(velocityCX[i] * velocityCX[i] + velocityCY[i] * velocityCY[i]);
                                //更新前の衝突した方の円の速度を保存しておく → 衝突された方の速度の更新に使用
                                float vc_temp = velocityCi;
                                //各方向の速度の比から衝突角度を計算
                                double collisionAngle = Math.atan2(velocityCX[i], velocityCY[i]);

                                //各方向の速度を更新
                                //衝突後は, 衝突した方の円の速度を減速させるため係数3/5をかける
                                velocityCX[i] = velocityCi * (float) Math.cos(collisionAngle - directionAngle) * 3/5;
                                velocityCY[i] = velocityCi * (float) Math.sin(collisionAngle - directionAngle) * 3/5;

                                //保存していた衝突した方の円の速度を衝突された方の円の速度に反映
                                float velocityCj = vc_temp;
                                //衝突された方が進む方向(directionAngle)へ速度を指定
                                velocityCX[j] = velocityCj * (float) Math.cos(directionAngle) * 4/5;
                                velocityCY[j] = velocityCj * (float) Math.sin(directionAngle) * 4/5;

                                // 位置を微調整
                                float overlap = (2 * radiusTarget[i] - distanceTargets) / 2;
                                cx[i] -= overlap * Math.cos(directionAngle);
                                cy[i] -= overlap * Math.sin(directionAngle);
                                cx[j] += overlap * Math.cos(directionAngle);
                                cy[j] += overlap * Math.sin(directionAngle);
                            }
                        }
                    }
                }

                //全ての的球がポケットに入るとゲームが終了
                if(isInPocket[0] && isInPocket[1] && isInPocket[2] && isInPocket[3] && isInPocket[4] && isInPocket[5]){
                    gameFinished = true;
                }
                if(isInPocketHand){
                    gameFinished = true;
                }
            }

        }else{//ゲーム終了後の処理 gameFinished == true
            //全ての的球がポケットに入って終了
            if(isInPocket[0] && isInPocket[1] && isInPocket[2] && isInPocket[3] && isInPocket[4] && isInPocket[5]){
                Paint pt = new Paint();
                pt.setColor(Color.BLACK);
                pt.setTextSize(160.0f);
                c.drawColor(Color.WHITE);
                c.drawText("合計ショット",c.getWidth()/2 - 500, c.getHeight()/2 - 300, pt);
                c.drawText(String.valueOf(touchCount), c.getWidth()/2 - 100, c.getHeight()/2, pt);
            }

            //手球がポケットに入って終了
            if(isInPocketHand){
                Paint pt = new Paint();
                pt.setColor(Color.WHITE);
                pt.setTextSize(160.0f);
                c.drawColor(Color.BLACK);
                c.drawText("GAME OVER", c.getWidth()/2-500, c.getHeight()/2, pt);
            }
        }
        sHolder.unlockCanvasAndPost(c) ;
    }

    public float distance(float x1, float y1, float x2, float y2){
        return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }

    static final long FPS = 30 ;
    static final long FTIME = 1000 / FPS ;
    @Override
    public void run() {
        long loopC = 0 ; // ループ数のカウンタ
        long wTime = 0 ; // 次の描画までの待ち時間(ミリ秒)

        long sTime = System.currentTimeMillis() ; // 開始時の現在時刻
        while (thread != null) {
            try {
                loopC++ ;
                drawCanvas() ;
                wTime = (loopC * FTIME) - (System.currentTimeMillis() - sTime) ;
                if (wTime > 0) {
                    Thread.sleep(wTime) ;
                }
            } catch (InterruptedException e) {

            }
        }
    }
}
