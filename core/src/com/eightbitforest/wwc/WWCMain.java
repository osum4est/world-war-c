package com.eightbitforest.wwc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.eightbitforest.wwc.handlers.GameHandler;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.file.FileChooser;

public class WWCMain extends ApplicationAdapter {
	private Stage stage;

    public GameHandler gameHandler;
	@Override
	public void create () {

        gameHandler = new GameHandler();
	}

	@Override
	public void render () {
        gameHandler.render();
        gameHandler.update(Gdx.graphics.getDeltaTime());
	}

    @Override
    public void dispose() {
        gameHandler.dispose();
    }
}
