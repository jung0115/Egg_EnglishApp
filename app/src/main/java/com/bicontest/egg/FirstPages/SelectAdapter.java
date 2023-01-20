package com.bicontest.egg.FirstPages;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomMasterTable;

import com.bicontest.egg.R;

import java.util.ArrayList;
import java.util.List;

// 최초 화면에서 선택하는 단어
public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.ViewHolder> {
    private RoomDB database;
    private Activity context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView word_english;
        private TextView word_korean;
        private RelativeLayout word_layout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            word_english = (TextView) itemView.findViewById(R.id.select_word_english);
            word_korean = (TextView) itemView.findViewById(R.id.select_word_korean);
            word_layout = (RelativeLayout) itemView.findViewById(R.id.select_word_layout);

            // 단어를 클릭했을 때
            word_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 클릭된 아이템의 위치 찾아내기
                    int pos = getAdapterPosition();
                    SelectViewItem item = mList.get(pos);
                    if (pos != RecyclerView.NO_POSITION) {
                        if (item.getWordChecked()==false){
                            word_layout.setBackgroundResource(R.drawable.border_background_select);
                            item.setWordChecked(true);
                        }
                        else {
                            word_layout.setBackgroundResource(R.drawable.border_background_unselect);
                            item.setWordChecked(false);
                        }
                        // 선택 된 단어 배경색 변경

                    }
                }
            });
        }
    }

    private List<SelectViewItem> mList = null;

    public SelectAdapter(List<SelectViewItem> mList) {
        this.mList = mList;
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        View view = inflater.inflate(R.layout.first_select_item, parent, false);
        SelectAdapter.ViewHolder vh = new SelectAdapter.ViewHolder(view);
        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull SelectAdapter.ViewHolder holder, int position) {
        SelectViewItem item = mList.get(position);
        database = RoomDB.getInstance(context);


        holder.word_english.setText(item.getWordEnglish());  // 영어
        holder.word_korean.setText(item.getWordKorean());   // 한글
        if (item.getWordChecked()==true){
            holder.word_layout.setBackgroundResource(R.drawable.border_background_select);
        }
        else {
            holder.word_layout.setBackgroundResource(R.drawable.border_background_unselect);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
