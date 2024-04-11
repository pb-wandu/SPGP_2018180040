package Seungwan.HeosilStory;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

        // 시작시 실행하는 것들
        setShowingMenu();  // 현재 보여주는 메뉴 설정
        ShowNowModeInfo(); // 현재 모드에 대한 정보 표시
    }

    // 현재 메뉴, 현재 모드
    private String nowShowingMenu = "MainMenu";
    private String nowMode = "StoryMode";

    // 클리어한 스테이지 정보 표시
    private int clearedWorld = 0;
    private int clearedStage = 0;

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

    // 모드 왼쪽 버튼
    public void ModeLeftButtonClicked(View view) {
        switch(nowMode) {
            case "StoryMode":
                nowMode = "infiniteMode";
                break;
            case "infiniteMode":
                nowMode = "BossMode";
                break;
            case "BossMode":
                nowMode = "StoryMode";
                break;
        }

        // 현재 모드에 대한 정보를 보여준다
        ShowNowModeInfo();
    }

    // 모드 오른쪽 버튼
    public void ModeRightButtonClicked(View view) {
        switch(nowMode) {
            case "StoryMode":
                nowMode = "BossMode";
                break;
            case "BossMode":
                nowMode = "infiniteMode";
                break;
            case "infiniteMode":
                nowMode = "StoryMode";
                break;
        }

        // 현재 모드에 대한 정보를 보여준다
        ShowNowModeInfo();
    }

    // 현재 모드에 대한 정보를 보여준다
    public void ShowNowModeInfo(){
        switch(nowMode) {
            case "StoryMode":
                binding.NowModeText.setText("[스토리 모드]");
                binding.NowModeInfoText.setText("[허실차원]에 대한 이야기를 따라가는 모드입니다.");
                binding.MainGameStartBtn.setVisibility(View.GONE);
                binding.MainUpgradeBtn.setVisibility(View.VISIBLE);
                binding.MainStageSelectBtn.setVisibility(View.VISIBLE);

                // 최대 진행 스테이지 표시
                if ((clearedWorld == 0) && (clearedStage == 0)){
                    binding.NowModeStatusText.setText("플레이 기록 없음");
                }
                else{
                    binding.NowModeStatusText.setText("최대 진행 스테이지 : [" + clearedWorld + "-" + clearedStage + "]");
                }

                break;

            case "BossMode":
                binding.NowModeText.setText("[보스 모드]");
                binding.NowModeInfoText.setText("[스토리 모드]에서 등장한 강적과 대결하는 모드입니다.");
                binding.MainGameStartBtn.setVisibility(View.VISIBLE);
                binding.MainUpgradeBtn.setVisibility(View.GONE);
                binding.MainStageSelectBtn.setVisibility(View.GONE);

                // 최대 진행 기록 표시
                binding.NowModeStatusText.setText("(모드 잠김)");

                break;

            case "infiniteMode":
                binding.NowModeText.setText("[영원 모드]");
                binding.NowModeInfoText.setText("최대한 많은 적을 쓰러트리는 모드입니다.");
                binding.MainGameStartBtn.setVisibility(View.VISIBLE);
                binding.MainUpgradeBtn.setVisibility(View.GONE);
                binding.MainStageSelectBtn.setVisibility(View.GONE);

                // 최대 진행 기록 표시
                binding.NowModeStatusText.setText("(모드 잠김)");

                break;
        }
    }
}