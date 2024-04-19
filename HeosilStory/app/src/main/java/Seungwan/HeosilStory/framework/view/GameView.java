package Seungwan.HeosilStory.framework.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

import Seungwan.HeosilStory.app.MainActivity;
import Seungwan.HeosilStory.game.imageBox;

// import android.graphics.Bitmap;
// import android.graphics.BitmapFactory;
// import android.util.Log;
// import java.util.ArrayList;

public class GameView extends View implements Choreographer.FrameCallback {
    private static final String TAG = GameView.class.getSimpleName();
    private final Activity activity;

    // ### (임시) 현재 스테이지를 1-1로 지정함
    private int nowWorld = MainActivity.nowWorld = 1;
    private int nowStage = MainActivity.nowStage = 1;

    // 현재 터치한 좌표
    private float touchX = -1;
    private float touchY = -1;

    // 각 버튼별 코드
    public static final int atkBtnCode = 11;
    public static final int defBtnCode = 12;

    public GameView(Context context) {
        super(context);
        this.activity = (Activity) context;

        // 전체화면 설정
        setFullScreen();

        // 현재 표시중인 메뉴
        MainActivity.nowShowingMenu = "GameMenu";


        // Resources res = getResources();
        // Bitmap ballBitmap = BitmapFactory.decodeResource(res, R.mipmap.soccer_ball_240);
        // Ball.setBitmap(ballBitmap);

        scheduleUpdate();
    }

    private void scheduleUpdate() {
        Choreographer.getInstance().postFrameCallback(this);
    }

    @Override
    public void doFrame(long nanos) {
        update();
        invalidate();
        if (isShown()) {
            scheduleUpdate();
        }
    }

    // 최대 크기로 설정
    public void setFullScreen() {
        int flags = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        setSystemUiVisibility(flags);
    }

    public Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final RectF borderRect = new RectF
            (0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint = new Paint();
    private final Paint stageinfoPaint = new Paint();

    private final Paint touchPointPaint = new Paint();

    // 이미지 박스 오브젝트 (공격, 방어 버튼)
    imageBox leftAtkBtn = new imageBox();
    imageBox leftDefBtn = new imageBox();
    imageBox rightAtkBtn = new imageBox();
    imageBox rightDefBtn = new imageBox();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float) w / (float) h;
        float game_ratio = SCREEN_WIDTH / SCREEN_HEIGHT;

        if (view_ratio > game_ratio) {
            float scale = h / SCREEN_HEIGHT;
            transformMatrix.setTranslate((w - h * game_ratio) / 2, 0);
            transformMatrix.preScale(scale, scale);
        } else {
            float scale = w / SCREEN_WIDTH;
            transformMatrix.setTranslate(0, (h - w / game_ratio) / 2);
            transformMatrix.preScale(scale, scale);
        }
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        canvas.concat(transformMatrix);

        // 게임 화면 그리기
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.rgb(200, 255, 200));
        canvas.drawRect(borderRect, borderPaint);

        canvas.restore();

        // 게임 화면의 시작 x, y 및 끝 x, y
        float[] startPoint = {borderRect.left, borderRect.top};
        float[] endPoint = {borderRect.right, borderRect.bottom};
        transformMatrix.mapPoints(startPoint);
        transformMatrix.mapPoints(endPoint);
        float marginSize = 20;
        float gameStartX = startPoint[0] + marginSize;
        float gameStartY = startPoint[1] + marginSize;
        float gameEndX = endPoint[0] - marginSize;
        float gameEndY = endPoint[1] - marginSize;

        float gameScreenSizeX = gameEndX - gameStartX;
        float gameScreenSizeY = gameEndY - gameStartY;

        // 게임 화면 내부 그리기 (실제 게임 범위)
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.rgb(255, 255, 255));
        canvas.drawRect(gameStartX, gameStartY, gameEndX, gameEndY, borderPaint);

        // 스테이지 정보 텍스트 표시
        stageinfoPaint.setColor(Color.BLACK);
        stageinfoPaint.setTextSize(40f);
        canvas.drawText("현재 스테이지 : [" + nowWorld + " - " + nowStage + "]",
                gameStartX + 20f, gameStartY + 50f,
                stageinfoPaint);

        // 상 / 하단 레이아웃 구분 (3:2) 기준 Y
        float dividingY = (gameStartY + gameEndY) * (3.0f / 5.0f);

        // 하단 레이아웃 영역 그리기
        borderPaint.setStyle(Paint.Style.FILL);
        borderPaint.setColor(Color.rgb(180, 180, 180));
        canvas.drawRect(gameStartX, dividingY, gameEndX, gameEndY, borderPaint);

        // 공격, 방어 버튼 크기
        float buttonAtkSizeX = gameScreenSizeX * 1 / 5;
        float buttonDefSizeX = gameScreenSizeX * 1 / 5;
        float buttonAtkSizeY = gameScreenSizeY * 1 / 5;
        float buttonDefSizeY = gameScreenSizeY * 1 / 5;

        // 각 버튼의 시작 지점
        float leftButtonAtkStartX = gameStartX;
        float leftButtonDefStartX = gameStartX;
        float leftButtonAtkStartY = dividingY;
        float rightButtonAtkStartY = dividingY;
        float rightButtonAtkStartX = gameEndX - buttonAtkSizeX;
        float rightButtonDefStartX = gameEndX - buttonDefSizeX;
        float leftButtonDefStartY = dividingY + buttonAtkSizeY;
        float rightButtonDefStartY = dividingY + buttonDefSizeY;


        leftAtkBtn.drawImageBox(canvas, atkBtnCode, leftButtonAtkStartX, leftButtonAtkStartY,
                leftButtonAtkStartX+buttonAtkSizeX, rightButtonDefStartY);
        rightAtkBtn.drawImageBox(canvas, atkBtnCode, rightButtonAtkStartX, rightButtonAtkStartY,
                gameEndX, rightButtonDefStartY);
        leftDefBtn.drawImageBox(canvas, defBtnCode, leftButtonDefStartX, leftButtonDefStartY,
                leftButtonAtkStartX+buttonAtkSizeX, gameEndY);
        rightDefBtn.drawImageBox(canvas, defBtnCode, rightButtonDefStartX, rightButtonDefStartY,
                gameEndX, gameEndY);

        // ### 테스트용 - 클릭시 해당 위치에 사각형 표시
        if (touchX != -1 && touchY != -1) {
            float rectSize = 25f;
            float rectLeft = touchX - rectSize / 2;
            float rectTop = touchY - rectSize / 2;
            float rectRight = touchX + rectSize / 2;
            float rectBottom = touchY + rectSize / 2;
            touchPointPaint.setColor(Color.RED);
            touchPointPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, touchPointPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            // 입력받은 좌표를 저장한다.
            touchX = event.getX();
            touchY = event.getY();

            update();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void update() {

    }
}
