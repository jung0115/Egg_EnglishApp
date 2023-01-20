package com.bicontest.egg;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "word_table")
public class saveWord {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "wordEng")
    private String mWordEnglish;

    @ColumnInfo(name = "wordKor")
    private String mWordKorean;

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

}
