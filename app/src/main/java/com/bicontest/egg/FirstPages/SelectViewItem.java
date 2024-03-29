package com.bicontest.egg.FirstPages;

// 최초 화면에서 선택하는 단어

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class SelectViewItem {

    private int id;

    private String mWordEnglish;

    private String mWordKorean;

    private Boolean mWordChecked = false;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

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

    public  Boolean getWordChecked() { return mWordChecked; }

    public void setWordChecked(Boolean wordChecked) { this.mWordChecked = wordChecked; }
}
