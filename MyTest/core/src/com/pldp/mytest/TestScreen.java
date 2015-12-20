package com.pldp.mytest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.pldp.platernia.Effect;
import com.pldp.platernia.Entity;
import com.pldp.platernia.Options;
import com.pldp.platernia.Physics;
import com.pldp.platernia.Rectangle;
import com.pldp.platernia.Universe;
import com.pldp.util.Controller;
import com.pldp.util.Input;

public class TestScreen implements Screen {

    private MyTest game;
    private Texture img;
    private OrthographicCamera camera;
    private Universe u = new Universe();
    private Entity e = new Entity(u);
    private Entity draggedEntity;

    private Input input = new Input();

    private Controller controller;

    private long scale = 100L;
    public TestScreen(final MyTest game) {
        this.game = game;
        controller = new Controller(input, game.batch);
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        e.setRectangle(new Rectangle(50L * scale, 50L * scale, 50L * scale, 50L * scale));
        u.addEntity(e);
        e.getEffects().add(new Effect() {
            @Override
            public boolean tick(Entity e, Options o) {
                final Physics p = e.getPhysics();
                if (p.vx != 0) {
                    p.vx *= 0.90;
                }
                if (p.vy != 0) {
                    p.vy *= 0.90;
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
    	processInput(delta);
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
                    r.x / scale, r.y / scale,
                    r.width / scale, r.height / scale);
            game.font.draw(game.batch, String.format("%d, %d", r.x, r.y), 10, 100);
        }
        if (null != controller) {
            controller.render();
        }
        game.batch.end();
        
    }

    private void processInput(float delta) {
        long v = 10000L;
    	input.update(camera);
        if (controller.left()) {
            final Physics p = e.getPhysics();
            p.vx -= v * delta;
        }
        if (controller.right()) {
            final Physics p = e.getPhysics();
            p.vx += v * delta;
        }
        if (controller.up()) {
            final Physics p = e.getPhysics();
            p.vy += v * delta;
        }
        if (controller.down()) {
            final Physics p = e.getPhysics();
            p.vy -= v * delta;
        }
        doDrag();
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
    
    private long lastDx;
    private long lastDy;
    private void doDrag() {
        if (input.touched) {
        	long x = input.x * scale;
        	long y = input.y * scale;
            game.batch.begin();
            game.font.draw(game.batch, String.format("%d, %d", input.x, input.y), 10, 130);
            game.batch.end();
            if (draggedEntity == null) {
                for (Entity e : u.getEntities()) {
                    if (e.getRectangle().inside(x, y)) {
                        draggedEntity = e;
                        e.getPhysics().vx = 0;
                        e.getPhysics().vy = 0;
                        break;
                    }
                }
            } else {
                Rectangle r = draggedEntity.getRectangle();
                r.x += input.dx * scale;
                r.y += input.dy * scale;
                lastDx = input.dx * scale;
                lastDy = input.dy * scale;
            }
        } else if (draggedEntity != null) {
            final Physics p = draggedEntity.getPhysics();
            p.vx = lastDx;
            p.vy = lastDy;
            draggedEntity = null;
        }
    }
}
