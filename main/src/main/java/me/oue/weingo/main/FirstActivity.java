package me.oue.weingo.main;

import android.app.Activity;
        import android.os.Bundle;
        import android.widget.TextView;

public class FirstActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textview = new TextView(this);
        textview.setText("This is the first tab");
        setContentView(textview);
    }
}