package com.plopezpi.app;

import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	public static final String EXTRA_MESSAGE = "com.plopezpi.app.EXTRA_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void sendMessage(View view) {
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
}
