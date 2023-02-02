package com.bicontest.egg.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bicontest.egg.MainPages.RecommendAdapter;
import com.bicontest.egg.MainPages.RecommendViewItem;
import com.bicontest.egg.MainPages.ToggleWordsViewItem;
import com.bicontest.egg.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class SearchResultFragment extends Fragment {
    private String[][] searchWords = {{"apple", "사과"}, {"computer", "컴퓨터"}, {"science", "과학"}, {"student", "학생"}, {"August", "8월"}};
    private String[][][] toggleWords = {{{"banana", "바나나"}, {"grape", "포도"}}, {{"Test", "시험"}}, {{"Test", "시험"}}, {{"Test", "시험"}}, {{"Test", "시험"}}};

    // 검색어 = 검색할 단어
    private String mSearchWord;
    private ArrayList<ResultWords> resultWords = new ArrayList<ResultWords>();
    private ArrayList<Integer> rocations = new ArrayList<Integer>();

    // 파이어베이스 DB 접근
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    // 검색 결과 리스트 표시에 필요한 것들
    private RecyclerView mSearchRecyclerView;
    private ArrayList<RecommendViewItem> mSearchList;
    private RecommendAdapter mSearchRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 검색어 데이터 받아오기
        mSearchWord = this.getArguments().getString("SearchWord").toLowerCase();
        //Log.d("Test", mSearchWord + "-----------------------------");

        getWordsThread.start();

        // 데이터 가져오는 동안 기다리기
        try {
            getWordsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // recylerview 세팅
        firstInit();

        mSearchRecyclerViewAdapter = new RecommendAdapter(mSearchList);
        mSearchRecyclerView.setAdapter(mSearchRecyclerViewAdapter);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity())); // 수직 리스트
    }

    // 검색 결과 리스트에 필요한 부분
    private void firstInit(){
        // 검색 결과 리스트
        mSearchRecyclerView = (RecyclerView) requireActivity().findViewById(R.id.search_result_recyclerview);
        mSearchList = new ArrayList<>();
    }

    // 검색 결과 리스트에 단어의 영어, 한글 정보 추가
    private void addSearchItem(RecommendViewItem searchItem){
        mSearchList.add(searchItem);

        mSearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    // 연관어 부분
    private ArrayList<ToggleWordsViewItem> buildToggleWords(String[][] toggleWord) {
        ArrayList<ToggleWordsViewItem> mToggleWordsList = new ArrayList<>();

        for (int i=0; i<toggleWord.length; i++) {
            ToggleWordsViewItem toggleItem = new ToggleWordsViewItem(toggleWord[i][0], toggleWord[i][1]);
            //Log.println(Log.DEBUG, "debug", "----------------------------------------------------------------");
            //Log.println(Log.DEBUG, "Data", toggleWord[i][0] + " " + toggleWord[i][1]);

            mToggleWordsList.add(toggleItem);
        }

        return mToggleWordsList;
    }

    // 파이어베이스에서 데이터 가져와서 검색 결과 리스트 만들기
    Thread getWordsThread = new Thread(new Runnable() {
        @Override
        public void run() {
            database = FirebaseDatabase.getInstance();   // 데이터베이스 선언, 할당
            myRef = database.getReference("words");

            myRef.addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String word = snapshot.child("eng").getValue().toString().toLowerCase();
                                int rocation = word.indexOf(mSearchWord);

                                // 해당 단어가 검색어를 포함하고 있는 경우
                                if(rocation > -1) {
                                    RecommendViewItem searchItem = new RecommendViewItem();
                                    searchItem.setWordEnglish(snapshot.child("eng").getValue().toString()); // 영어
                                    searchItem.setWordKorean(snapshot.child("kor").getValue().toString());  // 한글
                                    searchItem.setToggleItem(buildToggleWords(toggleWords[0]));             // 연관어

                                    resultWords.add(new ResultWords(searchItem, rocation));

                                    Log.d("SearchText", "검색 결과 : " + snapshot.getValue());
                                }
                            }
                            //Log.d("Test3", "-----------------------------------");

                            // 검색어의 위치를 기준으로 정렬
                            Collections.sort(resultWords);

                            // 정확도 순 상위 20개
                            for(int i = 0; i < resultWords.size(); i++) {
                                addSearchItem(resultWords.get(i).mSearchResult);
                                //Log.d("Test", resultWords.get(i).mSearchResult.toString());
                                //Log.d("Test", "-------------------------------");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );
        }
    });

    // 검색어 위치 기준으로 단어 정렬
    class ResultWords implements Comparable<ResultWords> {
        private RecommendViewItem mSearchResult;
        private int rocation;

        public ResultWords(RecommendViewItem mSearchResult, int rocation) {
            this.mSearchResult = mSearchResult;
            this.rocation = rocation;
        }

        @Override
        public int compareTo(ResultWords resultWords) {
            if (resultWords.rocation < rocation) {
                return 1;
            } else if (resultWords.rocation > rocation) {
                return -1;
            }
            return 0;
        }
    }
}