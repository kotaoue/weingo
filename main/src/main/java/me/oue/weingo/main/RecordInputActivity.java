package me.oue.weingo.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RecordInputActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_input);

        Button buttonBefore = (Button) findViewById(R.id.buttonBefore);
        buttonBefore.setTextColor(R.color.after_text);
        // buttonBefore.setOnClickListener(this);

        // TextView textview1=(TextView)findViewById(R.id.textview1);
        // textview1.setTextColor(Color.RED);
    }
}