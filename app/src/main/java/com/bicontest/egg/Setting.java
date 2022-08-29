package com.bicontest.egg;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        TextView abc = (TextView)findViewById(R.id.abc);

        EditText flashEdit = (EditText) findViewById(R.id.flashEdit);
        flashEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //입력하기 전
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EditText에 변화가 있을 때
            }

            @Override
            public void afterTextChanged(Editable s) {
                //입력이 끝났을 때
                String flashText = flashEdit.getText().toString();
                if(flashText.getBytes().length <= 0){
                    abc.setText("숫자를 입력하세요");
                }
                else {
                    int flashsec = Integer.parseInt(flashText);
                    ((glovalVariable) getApplication()).setFlashSecond(flashsec);
                    int a = ((glovalVariable) getApplication()).getFlashSecond();
                    abc.setText(Integer.toString(a));
                }
            }
        });
    }
}