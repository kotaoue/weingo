package me.oue.weingo.main;

/**
 * Created by Kota on 2014/08/04.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // リソースファイルのインスタンスを作成
        Resources res = getResources();

        // タイトルバーを非表示にする。
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // ウィンドウを準備。
        setContentView(R.layout.main);

        //　tabHostのインスタンス
        TabHost tabHost = getTabHost();

        // 設定を一時的に保存する変数
        TabHost.TabSpec spec;       // タブ設定
        Intent intent;              // インテント

        // 体重を記録するタブの準備
        intent = new Intent().setClass(this, RecordInputActivity.class);
        spec = tabHost.newTabSpec("input_tab");

        // タブ用のViewを作成。
        View before_tab_view = View.inflate(getApplication(), R.layout.before_tab_view, null);
        TextView before_tab_text = (TextView) before_tab_view.findViewById(R.id.TextViewTabTitle);
        before_tab_text.setText(res.getString(R.string.input_tag_title));
        spec.setIndicator(before_tab_view);
        spec.setContent(intent);
        tabHost.addTab(spec);

        // グラフを表示するタブの準備
        intent = new Intent().setClass(this, RecordViewActivity.class);
        spec = tabHost.newTabSpec("view_tab");
        // タブ用のViewを作成。
        View after_tab_view = View.inflate(getApplication(), R.layout.after_tab_view, null);
        TextView after_tab_text = (TextView) after_tab_view.findViewById(R.id.TextViewTabTitle);
        after_tab_text.setText(res.getString(R.string.view_tag_title));
        spec.setIndicator(after_tab_view);
        // spec.setIndicator(res.getString(R.string.view_tag_title));
        spec.setContent(intent);
        tabHost.addTab(spec);

        // いずれかのタブを選んで表示する
        tabHost.setCurrentTab(0);

    }
}