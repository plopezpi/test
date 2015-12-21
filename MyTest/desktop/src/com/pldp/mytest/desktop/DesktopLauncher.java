package com.pldp.mytest.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pldp.mytest.MyTest;
import com.pldp.util.Logger;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new MyTest(new Logger(){
            @Override
            public void e(String msg, Exception e) {
                System.out.println("Error: " + msg);
                e.printStackTrace();
            }
        }), config);
    }
}
