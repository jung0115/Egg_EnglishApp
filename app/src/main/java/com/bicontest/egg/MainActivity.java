package com.bicontest.egg;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bicontest.egg.MainPages.MainFragment;
import com.bicontest.egg.Search.SearchResultFragment;

public class MainActivity extends AppCompatActivity {

    // fragment 제어를 위한 부분
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private MainFragment mainFragment;
    private SearchResultFragment searchResultFragment;

    private Toolbar mSearchBar;       // 툴바
    private SearchView mSearchView;   // 검색창
    private ImageButton mSettingBtn;  // 설정 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // fragment 제어를 위한 부분: MainFragment <-> SearchResultFragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mainFragment = new MainFragment();

        // 시작은 MainFragment로 세팅
        fragmentManager.beginTransaction().replace(R.id.main_container, mainFragment).commit();

        // 툴바
        mSearchBar = findViewById(R.id.search_toolbar);
        setSupportActionBar(mSearchBar);

        // 검색 창
        mSearchView = (SearchView) findViewById(R.id.searchview);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //Log.println(Log.DEBUG,"Test", "---------------------------------------------------");
                //Log.println(Log.DEBUG,"Test", query);

                // 검색 리스트를 보여주는 SearchResultFragment로 검색어 데이터 전달
                Bundle bundle = new Bundle();
                bundle.putString("SearchWord", query);
                searchResultFragment = new SearchResultFragment();
                searchResultFragment.setArguments(bundle);

                // 검색어 입력 시 검색 Fragment 나타나기
                fragmentManager.beginTransaction().add(R.id.main_container, searchResultFragment).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Log.println(Log.DEBUG,"Test", "---------------------------------------------------");
                //Log.println(Log.DEBUG,"Test", "검색어 입력");
                String searchWord = newText;

                return false;
            }
        });

        // 설정 버튼
        mSettingBtn = (ImageButton) findViewById(R.id.toolbar_setting_btn);
        mSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.println(Log.DEBUG, "debug", "----------------------------------------------------------------");
                //Log.println(Log.DEBUG, "debug", "설정 버튼 클릭");
                Intent intent = new Intent(getApplicationContext(), Setting.class); // 설정 페이지로 이동
                startActivity(intent);
            }
        });
    }
}