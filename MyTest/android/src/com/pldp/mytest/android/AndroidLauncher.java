package com.pldp.mytest.android;

import android.os.Bundle;
import android.content.Context;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.pldp.mytest.MyTest;
import com.pldp.util.Logger;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new MyTest(new Logger() {
            @Override
            public void e(String msg, Exception e) {
                Context context = getApplicationContext();
                CharSequence text = msg;
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

        }), config);
    }
}
