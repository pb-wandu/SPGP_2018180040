package Seungwan.HeosilStory.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import Seungwan.HeosilStory.framework.view.GameView;

// 이미지 박스 오브젝트
public class imageBox {

    // 입력받은 네 좌표를 가지고 버튼을 그린다.
    public void drawImageBox(Canvas c, int code, float l, float t, float r, float b) {

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
        c.drawRect(l, t, r, b, btnPaint);
    }
}
