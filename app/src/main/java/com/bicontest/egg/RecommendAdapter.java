package com.bicontest.egg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.RecommendViewHolder> {

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        TextView word_english;
        TextView word_korean;

        // 추천 영단어 열고닫기 버튼&내용
        private ImageButton mOpenBtn;                    // 열기 버튼
        private ImageButton mCloseBtn;                   // 닫기 버튼
        private RelativeLayout mSubrecommnedWords;  // 추천단어의 연관어 내용 부분

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

            word_english = (TextView) itemView.findViewById(R.id.recommend_word_english);
            word_korean = (TextView) itemView.findViewById(R.id.recommend_word_korean);

            // 추천 단어의 연관단어 목록을 열기 위한 부분
            mOpenBtn = (ImageButton) itemView.findViewById(R.id.recommned_open_btn);
            mCloseBtn = (ImageButton) itemView.findViewById(R.id.recommned_close_btn);
            mSubrecommnedWords = (RelativeLayout) itemView.findViewById(R.id.subrecommned_words);

            // 열기 버튼을 눌렀을 때 작동
            mOpenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭된 아이템의 위치 찾아내기
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        mOpenBtn.setVisibility(View.GONE);              // 열기 버튼 숨기기
                        mCloseBtn.setVisibility(View.VISIBLE);          // 닫기 버튼 보이기
                        mSubrecommnedWords.setVisibility(View.VISIBLE); // 연관어 목록 보이기
                    }
                }
            });

            // 닫기 버튼을 눌렀을 때 작동
            mCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭된 아이템의 위치 찾아내기
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        mOpenBtn.setVisibility(View.VISIBLE);           // 열기 버튼 보이기
                        mCloseBtn.setVisibility(View.GONE);             // 닫기 버튼 숨기기
                        mSubrecommnedWords.setVisibility(View.GONE);    // 연관어 목록 숨기기
                    }
                }
            });
        }
    }

    private ArrayList<RecommendViewItem> mList = null;

    public RecommendAdapter(ArrayList<RecommendViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public RecommendAdapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recommend_item, parent, false);
        RecommendAdapter.RecommendViewHolder vh = new RecommendAdapter.RecommendViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull RecommendAdapter.RecommendViewHolder holder, int position) {
        RecommendViewItem item = mList.get(position);

        holder.word_english.setText(item.getWordEnglish());  // 영어
        holder.word_korean.setText(item.getWordKorean());   // 한글
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
