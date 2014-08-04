package me.oue.weingo.main;

/**
 * Created by Kota on 2014/08/04.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

public class HelloTabWidget extends TabActivity{
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //画像リソースを使うための準備(/res/drawableへアクセス）
        Resources res = getResources();
        //tabHostへの参照を取得
        TabHost tabHost =getTabHost();
        //それぞれのタブの設定に使う
        TabHost.TabSpec spec;
        //それぞれのインテントを渡すのに使う
        Intent intent;

        //インテントの作成(FirstActivityを呼び出すインテント)
        intent = new Intent().setClass(this,FirstActivity.class);
        //TabHost.TabSpecを"artists"というタグ名で作成
        spec = tabHost.newTabSpec("artists");
        //タブにテキストとアイコンをの表示(アイコンは、「2.」で作成したファイル名を指定すればいい。）
        spec.setIndicator("Artists",res.getDrawable(R.drawable.ic_tab_artists));
        //タブの内容にインテントを登録
        spec.setContent(intent);
        //新しくタブの作成(追加)
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, SecondActivity.class);
        spec = tabHost.newTabSpec("albums");
        spec.setIndicator("Albums",res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        intent = new Intent().setClass(this, ThirdActivity.class);
        spec = tabHost.newTabSpec("songs");
        spec.setIndicator("Songs",res.getDrawable(R.drawable.ic_tab_artists));
        spec.setContent(intent);
        tabHost.addTab(spec);

        //どのタブを選択状態にするか
        tabHost.setCurrentTab(1);

    }
}