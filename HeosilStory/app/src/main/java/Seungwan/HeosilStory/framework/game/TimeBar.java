package Seungwan.HeosilStory.framework.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import Seungwan.HeosilStory.framework.view.GameView;

// 이미지 박스 오브젝트
public class TimeBar {

    // 좌표들
    public float sx, sy, ex, ey;

    // 현재 시간, 최대 시간
    public float nowTime, maxTime;

    public void setPosition(float l, float t, float r, float b){
        sx = l; sy = t; ex = r; ey = b;
    }

    public void setTime(float t){
        maxTime = t;
        nowTime = maxTime;
    }

    // 시간 업데이트
    public void updateTime() {
        // 현재 시간
        long currentTime = System.currentTimeMillis();

        if (nowTime > 0) {
            long elapsedTime = currentTime % 1000;
            nowTime -= (elapsedTime / 1000.0f);
            nowTime = Math.max(nowTime, 0);
        }

        // 0초가 되었다면 maxTime으로 채워진다.
        if (nowTime <= 0) {
            nowTime = maxTime;
        }
    }

    // 입력받은 네 좌표를 가지고 타임 바를 그린다.
    public void drawTimeBar(Canvas c) {

        // 시간을 업데이트한다.
        updateTime();

        final Paint paint = new Paint();

        // 타임 바 내부 그리기
        float nowEx = sx - (ex - sx) + (ex - sx) * 2 * nowTime / maxTime;
        float nowEy = sy + (ey - sy);
        paint.setColor(Color.rgb(120, 200, 200));
        paint.setStyle(Paint.Style.FILL);
        c.drawRect(sx, sy, nowEx, nowEy, paint);

        // 타임 바 테두리 그리기
        paint.setColor(Color.rgb(0, 0, 0));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        c.drawRect(sx, sy, ex, ey, paint);
    }
}
