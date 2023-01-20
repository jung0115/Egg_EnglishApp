package com.bicontest.egg.FirstPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bicontest.egg.MainActivity;
import com.bicontest.egg.R;
import com.bicontest.egg.saveWord;

import java.util.ArrayList;
import java.util.List;

// 초기 페이지 - 관심 단어 선택
public class FirstSelectActivity extends AppCompatActivity {

    private String[][] words = {{"apple", "사과"}, {"computer", "컴퓨터"}, {"science", "과학"}, {"student", "학생"}, {"August", "8월"}};

    // 디바이스에 데이터를 저장하기 위한 작업
    public SharedPreferences mSharedPreferences;
    public SharedPreferences.Editor editor;
    RoomDB database;

    // 영단어 리스트 표시에 필요한 것들
    private RecyclerView mRecyclerView;
    private List<SelectViewItem> mList;
    private SelectAdapter mRecyclerViewAdapter;

    private Button skipBtn, completeBtn;  // 건너뛰기, 선택완료 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_select);

        // 디바이스에 데이터를 저장하기 위한 작업
        mSharedPreferences = getSharedPreferences("firstSelectWords", MODE_PRIVATE);
        editor = mSharedPreferences.edit();

        skipBtn = findViewById(R.id.select_word_skipbtn);          // 건너뛰기 버튼
        completeBtn = findViewById(R.id.select_word_completebtn);  // 선택완료 버튼

        database = RoomDB.getInstance(this);
//        mList = database.mainDao().getAll();
        firstInit();

        // 리스트에 단어의 영어, 한글 정보 전달
        for(int i = 0; i < 5; i++){
            addItem(words[i][0], words[i][1]);
        }

        mRecyclerViewAdapter = new SelectAdapter(mList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2)); // 수직 리스트 + 한 줄에 2개씩 grid
        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false)); // 수평 리스트

        getChecked();


        // 건너뛰기 버튼 클릭 시
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FirstSelectActivity.this, MainActivity.class); // 메인 페이지로 이동
                startActivity(intent);
                finish(); // 현재 액티비티 파괴
            }
        });

        // 선택완료 버튼 클릭 시
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i< mList.size();i++){
                    //여기서 roomDB 작성하기(영어 + 한글 단어를 리스트 형식으로 저장하게)
                    SelectViewItem item = mList.get(i);
                    if (item.getWordChecked()==true){
                        saveWord data = new saveWord();
                        data.setWordEnglish(item.getWordEnglish());
                        data.setWordKorean(item.getWordKorean());
                        database.mainDao().insert(data);
                    }
                }
                Intent intent = new Intent(FirstSelectActivity.this, MainActivity.class); // 메인 페이지로 이동
                startActivity(intent);
                finish(); // 현재 액티비티 파괴
            }
        });
    }

    // 단어 리스트에 필요한 부분
    private void firstInit(){
        mRecyclerView = (RecyclerView) findViewById(R.id.select_word_recyclerview);
        mList = new ArrayList<>();
    }

    // 리스트에 단어의 영어, 한글 정보 추가
    private void addItem(String wordEnglish, String wordKorean){
        SelectViewItem item = new SelectViewItem();

        item.setWordEnglish(wordEnglish);
        item.setWordKorean(wordKorean);

        mList.add(item);
    }
    private void getChecked() {
        for (int i = 0; i< mList.size();i++){
            SelectViewItem item = mList.get(i);
            //그냥 확인용
        }
    }

}