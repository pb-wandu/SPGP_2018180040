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

    public GameView(Context context) {
        super(context);
        this.activity = (Activity)context;

        // 경계선 그리기
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(0.1f);
        borderPaint.setColor(Color.BLACK);

        // 전체화면 설정
        setFullScreen();

        Resources res = getResources();
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
    private final Paint borderPaint;

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
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            update();
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    private void update() {

    }
}

