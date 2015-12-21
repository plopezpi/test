/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pldp.mytest;

import static com.badlogic.gdx.Application.ApplicationType.Android;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class BaseScreen implements Screen {

    public final MyTest game;
    private final OrthographicCamera camera;
    public BaseScreen(MyTest game) {
        this.game = game;
        this.camera = new OrthographicCamera();
    }

    @Override
    public void resize(int width, int height) {
        if(Android.equals(Gdx.app.getType())) {
            camera.setToOrtho(false, width / 2, height / 2);
        } else {
            camera.setToOrtho(false, width, height);
        }
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

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
    }
    
}
