package com.bicontest.egg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Setting extends AppCompatActivity {
    private SharedPreferences preferences;
    private EditText flashEdit;

    private TextView abc;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        abc = (TextView)findViewById(R.id.abc);

        flashEdit = (EditText) findViewById(R.id.flashEdit);

        Switch flash_random = (Switch) findViewById(R.id.flashRandom);

        //SharedPreferences 사용해서 디바이스에 정보 저장
        //getSharedPreferences("파일이름",'모드')
        //모드 => 0 (읽기,쓰기가능)
        //모드 => MODE_PRIVATE (이 앱에서만 사용가능)
        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("flashSec", "5");
        getPreferences();


        flash_random.setOnCheckedChangeListener(new flashSwitchRandomListener());

        //이렇게 안 해주면 flashSecond 수정 후에 activity 새로고침 되면 flash_editText 내용이 초기화됨
//        flashEdit.setText(Integer.toString(((glovalVariable) getApplication()).getFlashSecond()));
        flashEdit.addTextChangedListener(new TextWatcher() {

            // 안 쓰는 거 주석 처리했더니 에러 남
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
                    editor.putString("flashSec", Integer.toString(flashsec));
                    editor.commit();

                    ((glovalVariable) getApplication()).setFlashSecond(flashsec);
                    int a = ((glovalVariable) getApplication()).getFlashSecond();

                    abc.setText(Integer.toString(a));
                }
            }
        });

    }
    private void getPreferences(){
        //getString(KEY,KEY값이 없을때 대체)
        flashEdit.setText(preferences.getString("flashSec",""));
//        abc.setText(Integer.toString(preferences.getInt("flashSec",5)));
    }
//    private void setFlashRandom(Boolean isChecked){
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putBoolean("flashRandom", isChecked);
//        editor.commit();
//        abc.setText(String.valueOf(isChecked));
//        getFlashRandom();
//    }
//    private void getFlashRandom(){
//        //getString(KEY,KEY값이 없을때 대체)
//        flash_random.setChecked(preferences.getBoolean("flashRandom", true));
//    }

    //switch
    class flashSwitchRandomListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            setFlashRandom(isChecked);

            if(isChecked) {
                ((glovalVariable) getApplication()).setFlashRandBool(true);
            }
            else {
                ((glovalVariable) getApplication()).setFlashRandBool(false);
            }
            ((flash)flash.CONTEXT).onResume();

        }
    }
}