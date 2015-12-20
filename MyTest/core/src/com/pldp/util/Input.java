package com.pldp.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Input {

    private boolean wasTouched;
    private long lastTouchX;
    private long lastTouchY;
    public boolean touched;
    public long x;
    public long y;
    public long dx;
    public long dy;

    public void update(Camera camera) {
        touched = Gdx.input.isTouched();
        if (touched) {
            final Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            x = (long) touchPos.x;
            y = (long) touchPos.y;
            if (wasTouched) {
                dx = x - lastTouchX;
                dy = y - lastTouchY;
            } else {
                dx = 0;
                dy = 0;
            }
            lastTouchX = x;
            lastTouchY = y;
        }
        wasTouched = touched;
    }
}

