package me.oue.weingo.main;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecordInputActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_input);

        // ボタンの初期設定
        initButtonStatus();

        // 予想的中率の計算
        calcResult();
    }

    /*
     ボタンの初期設定
     */
    private void initButtonStatus() {
        // 予想体重の保存ボタンの準備
        Button buttonBefore = (Button) findViewById(R.id.buttonBefore);
        buttonBefore.setOnClickListener(beforeButtonClicked);

        // 確定体重のボタンの準備
        Button buttonAfter = (Button) findViewById(R.id.buttonAfter);
        buttonAfter.setOnClickListener(afterButtonClicked);
    }

    /*
     予想体重ボタンクリック時の処理
     */
    private View.OnClickListener beforeButtonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v("beforeButton", "onClick");
            changeBeforeButtonDisabled();
            calcResult();
        }
    };

    /*
     確定体重ボタンクリック時の処理
     */
    private View.OnClickListener afterButtonClicked = new View.OnClickListener() {
        public void onClick(View v) {
            Log.v("afterButton", "onClick");
            changeAfterButtonDisabled();
            calcResult();
        }
    };

    /*
     予想体重ボタンの表示・非表示を変更する。
     */
    private void changeBeforeButtonDisabled() {
        EditText editTextBefore = (EditText) findViewById(R.id.editTextBefore);
        // 予想体重のテキストが入っているかをチェック。
        if (editTextBefore.getText().toString().length() > 0) {
            // ボタンを非表示にする。
            Button buttonBefore = (Button) findViewById(R.id.buttonBefore);
            buttonBefore.setVisibility(View.INVISIBLE);

            // テキストの入力もできないようにする。
            editTextBefore.setFocusable(false);
            editTextBefore.setFocusableInTouchMode(false);
            editTextBefore.setEnabled(false);
        }
    }

    /*
     確定体重ボタンの表示・非表示を変更する。
     */
    private void changeAfterButtonDisabled() {
        EditText editTextAfter = (EditText) findViewById(R.id.editTextAfter);
        // 確定体重のテキストが入っているかをチェック。
        if (editTextAfter.getText().toString().length() > 0) {
            // ボタンを非表示にする。
            Button buttonAfter = (Button) findViewById(R.id.buttonAfter);
            buttonAfter.setVisibility(View.INVISIBLE);

            // テキストの入力もできないようにする。
            editTextAfter.setFocusable(false);
            editTextAfter.setFocusableInTouchMode(false);
            editTextAfter.setEnabled(false);
        }
    }

    /*
    　予想的中率を計算
     */
    private void calcResult() {
        EditText editTextBefore = (EditText) findViewById(R.id.editTextBefore);
        EditText editTextAfter = (EditText) findViewById(R.id.editTextAfter);
        // 予想体重・確定体重のテキストが入っているかをチェック。
        if (editTextBefore.getText().toString().length() > 0 && editTextAfter.getText().toString().length() > 0) {
            // 確定体重 / 予想体重 で 予想的中率を判断
            Float result = (Float.parseFloat(editTextAfter.getText().toString()) / Float.parseFloat(editTextBefore.getText().toString())) * 100f;

            TextView textViewResultValue = (TextView) findViewById(R.id.textViewResultValue);
            textViewResultValue.setText(result.toString());

            // 的中率に応じて、色を変更。
            Resources resources = getResources();
            if (result > 110f) {
                textViewResultValue.setTextColor(resources.getColor(R.color.error));
            }
            else if(result > 105f){
                textViewResultValue.setTextColor(resources.getColor(R.color.warning));
            }
            else if(result < 95f) {
                textViewResultValue.setTextColor(resources.getColor(R.color.good));
            }
        }
    }
}