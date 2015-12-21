package com.pldp.mytest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pldp.util.Logger;

public class MyTest extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Logger L;

    public MyTest(Logger L) {
        this.L = L;
    }

    @Override
    public void create() {
        try {
            batch = new SpriteBatch();
            //Use LibGDX's default Arial font.
            font = new BitmapFont();
            setScreen(new TestScreen(this));
        } catch (Exception e) {
            log("Error on create", e);
        }
    }

    @Override
    public void render() {
        try {
            super.render();
        } catch (Exception e) {
            log("Error on render", e);
        }
    }

    @Override
    public void dispose() {
        try {
            batch.dispose();
            font.dispose();
        } catch (Exception e) {
            log("Error on dispose", e);
        }
    }

    private void log(String msg, Exception e) {
        //L.e(msg, e);
        setScreen(new ErrorScreen(this, msg, e));
    }
}
