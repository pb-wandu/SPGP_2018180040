package Seungwan.HeosilStory;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

import android.view.Choreographer;
import android.view.MotionEvent;
import android.view.View;

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

    public GameView(Context context) {
        super(context);
        this.activity = (Activity)context;

        // 경계선 그리기
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.BLACK);

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
    };

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
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }

    public static final float SCREEN_WIDTH = 9.0f;
    public static final float SCREEN_HEIGHT = 16.0f;
    private final Matrix transformMatrix = new Matrix();
    private final RectF borderRect = new RectF(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    private final Paint borderPaint = new Paint();
    private final Paint stageinfoPaint = new Paint();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        float view_ratio = (float)w / (float)h;
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
        canvas.drawRect(borderRect, borderPaint);

        canvas.restore();

        // 스테이지 정보 텍스트 표시
        stageinfoPaint.setColor(Color.BLACK);
        stageinfoPaint.setTextSize(40f);
        canvas.drawText("현재 스테이지 : [" + nowWorld + " - " + nowStage + "]",
                borderRect.left + 20f, borderRect.top + 50f,
                stageinfoPaint);
        
        // ### 테스트용 - 클릭시 해당 위치에 사각형 표시
        if (touchX != -1 && touchY != -1) {
            float rectSize = 25f; // Size of the rectangle
            float rectLeft = touchX - rectSize / 2;
            float rectTop = touchY - rectSize / 2;
            float rectRight = touchX + rectSize / 2;
            float rectBottom = touchY + rectSize / 2;

            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.RED);
            rectPaint.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, rectPaint);
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

