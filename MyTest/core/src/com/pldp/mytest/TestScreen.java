package com.pldp.mytest;

import static com.badlogic.gdx.Application.ApplicationType.Android;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.pldp.platernia.Effect;
import com.pldp.platernia.Entity;
import com.pldp.platernia.Options;
import com.pldp.platernia.Physics;
import com.pldp.platernia.Rectangle;
import com.pldp.platernia.Universe;
import java.util.ArrayList;
import java.util.List;

public class TestScreen implements Screen {

    private MyTest game;
    private Texture img;
    private OrthographicCamera camera;
    private Universe u = new Universe();
    private Entity e = new Entity(u);
    private Entity draggedEntity;

    private class Input {

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
    private Input input = new Input();

    private class Controller {
        private boolean renderOnScreen = true; //Android.equals(Gdx.app.getType());
        private Texture img;
        private Rectangle rectangle;

        public Controller() {
            if (renderOnScreen) {
                img = new Texture(Gdx.files.internal("arrows.png"));
            }
            rectangle = new Rectangle(20L, 20L, 130L, 130L);
        }

        public void render() {
            if (renderOnScreen) {
                game.batch.draw(img, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
            }
        }

        public boolean left() {
            return Gdx.input.isKeyPressed(Keys.LEFT) ||
                    renderOnScreen && input.touched && 
                    input.x >= rectangle.x && input.x < rectangle.x + rectangle.width / 3;
        }

        public boolean right() {
            return Gdx.input.isKeyPressed(Keys.RIGHT) ||
                    renderOnScreen && input.touched && 
                    input.x >= rectangle.x + 2 * rectangle.width / 3 && input.x < rectangle.x + rectangle.width;
        }
    }
    Controller controller = new Controller();

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
        u.tick();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        input.update(camera);
        game.batch.begin();
        for (Entity e : u.getEntities()) {
            final Rectangle r = e.getRectangle();
            game.batch.draw(img,
                    r.x, r.y,
                    r.width, r.height);
            game.font.draw(game.batch, String.format("%d, %d", r.x, r.y), 10, 100);
        }
        if (null != controller) {
            controller.render();
        }
        game.batch.end();
        if (controller.left()) {
            final Physics p = e.getPhysics();
            p.vx -= 1000L * delta;
        }
        if (controller.right()) {
            final Physics p = e.getPhysics();
            p.vx += 1000L * delta;
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
            game.batch.begin();
            game.font.draw(game.batch, String.format("%d, %d", input.x, input.y), 10, 130);
            game.batch.end();
            if (draggedEntity == null) {
                for (Entity e : u.getEntities()) {
                    if (e.getRectangle().inside(input.x, input.y)) {
                        draggedEntity = e;
                        e.getPhysics().vx = 0;
                        e.getPhysics().vy = 0;
                        break;
                    }
                }
            } else {
                Rectangle r = draggedEntity.getRectangle();
                r.x += input.dx;
                r.y += input.dy;
                lastDx = input.dx;
                lastDy = input.dy;
            }
        } else if (draggedEntity != null) {
            final Physics p = draggedEntity.getPhysics();
            p.vx = lastDx;
            p.vy = lastDy;
            draggedEntity = null;
        }
    }
}
