package com.bicontest.egg.FirstPages;

// 최초 화면에서 선택하는 단어
public class SelectViewItem {
    private String mWordEnglish;
    private String mWordKorean;
    private Boolean mWordChecked = false;

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
