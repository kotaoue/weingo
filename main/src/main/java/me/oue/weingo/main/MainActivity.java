package me.oue.weingo.main;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

        private static final String TAB[] = {"tab1", "tab2","tab3" };

        @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                //TabHostオブジェクト取得
                TabHost tabhost = getTabHost();

                //Tab1設定
                TabHost.TabSpec tab1 = tabhost.newTabSpec(TAB[0]);
                tab1.setIndicator(this.getResources().getString(R.string.tab1));
                tab1.setContent(R.id.tab1);
                tabhost.addTab(tab1);

                //Tab2設定
                TabHost.TabSpec tab2 = tabhost.newTabSpec(TAB[1]);
                tab2.setIndicator(this.getResources().getString(R.string.tab2));
                tab2.setContent(R.id.tab2);
                tabhost.addTab(tab2);
                //初期表示するタブ
                tabhost.setCurrentTab(0);
            }
           }