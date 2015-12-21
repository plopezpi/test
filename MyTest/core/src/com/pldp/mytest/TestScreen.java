package com.pldp.mytest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.pldp.platernia.Entity;
import com.pldp.platernia.Options;
import com.pldp.platernia.Physics;
import com.pldp.platernia.Skinner;
import com.pldp.platernia.Stage;
import com.pldp.platernia.Skinner.Sprite;
import com.pldp.util.Rectangle;
import com.pldp.platernia.Universe;
import com.pldp.util.Controller;
import com.pldp.util.Input;
import java.util.List;

public class TestScreen implements Screen {

    private final MyTest game;
    private final Texture img;
    private final Texture skin;
    private final OrthographicCamera camera;
    private final Universe u;
    private final Entity e;
    private Entity draggedEntity;

    private final Input input = new Input();
    private final Controller controller;
    private final Options o;
    private final List<Sprite> spritesBg;
    private final List<Sprite> spritesFg;

    public TestScreen(final MyTest game) {
        this.game = game;
        this.o = new Options();
        controller = new Controller(input, game.batch);
        img = new Texture(Gdx.files.internal("badlogic.jpg"));
        skin = new Texture(Gdx.files.internal("skin.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Stage s = new Stage("1.bmp");
        spritesBg = Skinner.skinBg(s);
        spritesFg = Skinner.skinFg(s);
        u = s.createUniverse(o);
        e = u.getEntity("Player");
    }

    @Override
    public void show() {
    }

    long dx;
    long dy;

    @Override
    public void render(float delta) {
        String msg = processInput(delta);
        u.tick();
        Gdx.gl.glClearColor(140 / 255.f, 193 / 255.f, 232 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        dx = -e.getRectangle().x / o.scale - o.blockLen / 2 + (long) camera.viewportWidth / 2;
        dy = -e.getRectangle().y / o.scale - o.blockLen / 2 + (long) camera.viewportHeight / 2;
        renderSprites(spritesBg);
        for (Entity e : u.getEntities()) {
            final Rectangle r = e.getRectangle();
            if (!e.isCollidable()) {
                game.batch.draw(img,
                        dx + r.x / o.scale, dy + r.y / o.scale,
                        r.width / o.scale, r.height / o.scale);
            }
        }
        renderSprites(spritesFg);
        if (null != controller) {
            controller.render();
        }
        
        long textSzy = 15L;
        long y = Gdx.graphics.getHeight();
        for (Entity e : u.getEntities()) {
            final Rectangle r = e.getRectangle();
            if (!e.isCollidable()) {
                y -= textSzy;
                game.font.draw(game.batch, String.format("%s: %d, %d", e.getName(), r.x, r.y), 10, y);
            }
        }
        if (msg != null) {
            game.font.draw(game.batch, msg, 10, 130);
        }
        game.batch.end();

    }

    private void renderSprites(List<Sprite> ss) {
        for (Sprite s : ss) {
            game.batch.draw(skin,
                    dx + s.pos.x * o.blockLen, dy + s.pos.y * o.blockLen,
                    o.blockLen, o.blockLen,
                    s.u, s.v,
                    s.u2, s.v2);
        }
    }

    private String processInput(float delta) {
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
        return doDrag();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        skin.dispose();
    }

    private long lastDx;
    private long lastDy;

    private String doDrag() {
        if (input.touched) {
            long x = (input.x - dx) * o.scale;
            long y = (input.y - dy) * o.scale;
            if (draggedEntity == null) {
                for (Entity e : u.getEntities()) {
                    if (!e.isCollidable() && e.getRectangle().inside(x, y)) {
                        draggedEntity = e;
                        e.getPhysics().vx = 0;
                        e.getPhysics().vy = 0;
                        break;
                    }
                }
            } else {
                Rectangle r = draggedEntity.getRectangle();
                r.x += input.dx * o.scale;
                r.y += input.dy * o.scale;
                lastDx = input.dx * o.scale;
                lastDy = input.dy * o.scale;
            }
            return String.format("%d, %d", x, y);
        } else if (draggedEntity != null) {
            final Physics p = draggedEntity.getPhysics();
            p.vx = lastDx;
            p.vy = lastDy;
            draggedEntity = null;
        }
        return null;
    }
}
