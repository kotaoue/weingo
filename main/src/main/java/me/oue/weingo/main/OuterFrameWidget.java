package me.oue.weingo.main;

/**
 * Created by Kota on 2014/08/04.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;

public class OuterFrameWidget extends TabActivity {
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
        spec.setIndicator(res.getString(R.string.input_tag_title), res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        // グラフを表示するタブの準備
        intent = new Intent().setClass(this, RecordViewActivity.class);
        spec = tabHost.newTabSpec("view_tab");
        spec.setIndicator(res.getString(R.string.view_tag_title), res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        // いずれかのタブを選んで表示する
        tabHost.setCurrentTab(0);

    }
}