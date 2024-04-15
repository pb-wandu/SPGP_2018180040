package Seungwan.HeosilStory;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

// import android.content.Intent;
// import androidx.activity.EdgeToEdge;
// import androidx.core.graphics.Insets;
// import androidx.core.view.ViewCompat;
// import androidx.core.view.WindowInsetsCompat;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }
}