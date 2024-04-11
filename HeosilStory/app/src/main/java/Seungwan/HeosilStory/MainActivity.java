package Seungwan.HeosilStory;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.os.Bundle;

// import android.content.Intent;
// import android.net.Uri;
// import android.text.Editable;
// import android.text.TextWatcher;
// import android.util.Log;
// import android.widget.SeekBar;

import Seungwan.HeosilStory.R;
import Seungwan.HeosilStory.databinding.ActivityMainBinding;

/* -----[메인 액티비티]----- */
public class MainActivity extends AppCompatActivity {

    // Binding 사용
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    // 현재 메뉴 표시
    private String nowShowingMenu = "MainMenu";

    // 메인 화면 메뉴로 이동
    public void moveToMainMenu(View view) {
        nowShowingMenu = "MainMenu";
        setShowingMenu();
    }

    // 작전 구역 선택 (스테이지 선택) 메뉴로 이동
    public void moveToStageSelect(View view) {
        nowShowingMenu = "StageSelect";
        setShowingMenu();
    }

    // 작전실 (강화) 메뉴로 이동
    public void moveToUpgradeRoom(View view) {
        nowShowingMenu = "UpgradeRoom";
        setShowingMenu();
    }

    // 현재 보여주는 메뉴 설정
    public void setShowingMenu(){
        switch(nowShowingMenu){
            case "MainMenu":
                binding.MainMenuView.setVisibility(View.VISIBLE);
                binding.StageSelectView.setVisibility(View.GONE);
                binding.UpgradeView.setVisibility(View.GONE);
                break;

            case "StageSelect":
                binding.MainMenuView.setVisibility(View.GONE);
                binding.StageSelectView.setVisibility(View.VISIBLE);
                binding.UpgradeView.setVisibility(View.GONE);
                break;

            case "UpgradeRoom":
                binding.MainMenuView.setVisibility(View.GONE);
                binding.StageSelectView.setVisibility(View.GONE);
                binding.UpgradeView.setVisibility(View.VISIBLE);
                break;
        }
    }
}