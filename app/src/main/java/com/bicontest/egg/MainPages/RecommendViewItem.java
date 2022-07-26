package com.bicontest.egg.MainPages;

import java.util.ArrayList;
import java.util.List;

// 메인 화면에서 보이는 추천 단어
public class RecommendViewItem {
    private String mWordEnglish;
    private String mWordKorean;
    private ArrayList<ToggleWordsViewItem> mToggleItem; // 추천 단어 토글을 열면 보이는 연관어

    public String getWordEnglish() {
        return mWordEnglish;
    }

    public void setWordEnglish(String wordEnglish) {
        this.mWordEnglish = wordEnglish;
    }

    public String getWordKorean() {
        return mWordKorean;
    }

    public void setWordKorean(String wordKorean) {
        this.mWordKorean = wordKorean;
    }

    public ArrayList<ToggleWordsViewItem> getToggleItem() {
        return mToggleItem;
    }

    public void setToggleItem(ArrayList<ToggleWordsViewItem> toggleItem) {
        this.mToggleItem = toggleItem;
    }
}
