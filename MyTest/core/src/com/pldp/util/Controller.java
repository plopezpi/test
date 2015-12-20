package com.pldp.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pldp.platernia.Rectangle;

public class Controller {
	private SpriteBatch batch;
    private boolean renderOnScreen = true; //Android.equals(Gdx.app.getType());
    private Texture img;
    private Rectangle rectangle;
    private Rectangle left;
    private Rectangle right;
    private Rectangle up;
    private Rectangle down;
	private Input input;

    public Controller(Input input, SpriteBatch batch) {
    	this.batch = batch;
    	this.input = input;
        if (renderOnScreen) {
            img = new Texture(Gdx.files.internal("arrows.png"));
        }
        long px = 20L;
        long py = 20L;
        long w = 130L;
        long h = 130L;
        rectangle = new Rectangle(px, py, w, h);
        left = new Rectangle(px, py, w/3, h);
        right = new Rectangle(px + 2 * w / 3, py, w/3, h);

        up = new Rectangle(px, py + 2 * h / 3, w, h/3);
        down = new Rectangle(px, py, w, h/3);
    }

    public void render() {
        if (renderOnScreen) {
            batch.draw(img, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
    }

    public boolean left() {
        return Gdx.input.isKeyPressed(Keys.LEFT) ||
                renderOnScreen && input.touched && left.inside(input.x, input.y);
    }

    public boolean right() {
        return Gdx.input.isKeyPressed(Keys.RIGHT) ||
                renderOnScreen && input.touched && right.inside(input.x, input.y);
    }

    public boolean up() {
        return Gdx.input.isKeyPressed(Keys.UP) ||
                renderOnScreen && input.touched && up.inside(input.x, input.y);
    }

    public boolean down() {
        return Gdx.input.isKeyPressed(Keys.DOWN) ||
                renderOnScreen && input.touched && down.inside(input.x, input.y);
    }
}

