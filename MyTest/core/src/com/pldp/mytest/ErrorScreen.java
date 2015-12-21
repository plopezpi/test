/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pldp.mytest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pldp.platernia.Entity;
import com.pldp.platernia.Skinner;
import com.pldp.util.Rectangle;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author plopezpi
 */
class ErrorScreen implements Screen {
    private MyTest game;
    private final OrthographicCamera camera;
    private final String msg;
    private final Exception e;
    public ErrorScreen(MyTest game, String msg, Exception e) {
        this.game = game;
        this.msg = msg;
        this.e = e;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(140 / 255.f, 193 / 255.f, 232 / 255.f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, String.format("%s %s", msg, e.getMessage()), 10, camera.viewportHeight - 15);
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        game.font.draw(game.batch, errors.toString(), 10, camera.viewportHeight - 30);
        game.batch.end();
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
    }
    
}
