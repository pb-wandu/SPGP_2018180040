package Seungwan.HeosilStory.framework.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import Seungwan.HeosilStory.framework.view.GameView;

// 이미지 박스 오브젝트
public class imageBox {

    // 좌표들
    public float sx, sy, ex, ey;

    public void setPosition(float l, float t, float r, float b){
        sx = l; sy = t; ex = r; ey = b;
    }

    // 입력받은 네 좌표를 가지고 버튼을 그린다.
    public void drawImageBox(Canvas c, int code) {

        final Paint btnPaint = new Paint();

        // 코드에 따라 이미지 다르게 표시할 예정
        switch(code){
            case GameView.atkBtnCode:
                btnPaint.setColor(Color.rgb(200, 120, 120));
                break;
            case GameView.defBtnCode:
                btnPaint.setColor(Color.rgb(120, 200, 120));
                break;
        }

        btnPaint.setStyle(Paint.Style.FILL);
        c.drawRect(sx, sy, ex, ey, btnPaint);
    }
}
