package com.matthewmarcos.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SpaceRocks extends BaseGame {
    public void create() {
        super.create();
        setActiveScreen(new LevelScreen());
    }
}
