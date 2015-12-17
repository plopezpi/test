package com.pldp.mytest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.pldp.platernia.Effect;
import com.pldp.platernia.Entity;
import com.pldp.platernia.Options;
import com.pldp.platernia.Rectangle;
import com.pldp.platernia.Universe;

public class TestScreen implements Screen {

    private MyTest game;
    Texture img;
    OrthographicCamera camera;
    Universe u = new Universe();
    Entity e = new Entity();

    public TestScreen(final MyTest game) {
        this.game = game;
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        
        e.setRectangle(new Rectangle(50L, 50L, 50L, 50L));
        u.addEntity(e);
        e.getEffects().add(new Effect() {
            @Override
            public boolean tick(Entity e, Options o) {
                final Rectangle r = e.getRectangle();
                if (r.y > 0L) {
                    r.y = r.y - 1L;
                }
                return true;
            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        u.tick();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        
        game.batch.begin();
        for (Entity e : u.getEntities()) {
            final Rectangle r = e.getRectangle();
            game.batch.draw(img,
                    r.x, r.y,
                    r.width, r.height);
            game.font.draw(game.batch, String.format("%d, %d", r.x, r.y), 10, 100);
        }
        game.batch.end();
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            Rectangle r = e.getRectangle();
            r.x -= 1000L * delta;
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            Rectangle r = e.getRectangle();
            r.x += 1000L * delta;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
