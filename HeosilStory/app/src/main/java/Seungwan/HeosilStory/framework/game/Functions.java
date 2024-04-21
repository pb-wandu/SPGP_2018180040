package Seungwan.HeosilStory.framework.game;

// 게임에서 사용되는 함수들
public class Functions {

    // 직사각형 범위 내 있는지 판정
    public static Boolean ifInsideRect(float x, float y, float sx, float sy, float ex, float ey) {
        if (x >= sx && x <= ex && y >= sy && y <= ey) {
            return true;
        } else {
            return false;
        }
    }
}
