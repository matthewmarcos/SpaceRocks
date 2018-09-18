package com.matthewmarcos.spacerocks;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class LevelScreen extends BaseScreen {
    private SpaceShip spaceship;
    private boolean gameOver;

    public void initialize() {
        BaseActor space = new BaseActor(0, 0, mainStage);
        space.loadTexture("space.png");
        space.setSize(800, 600);
        BaseActor.setWorldBounds(space);

        new Rock(600, 500, mainStage);
        new Rock(600, 300, mainStage);
        new Rock(600, 100, mainStage);
        new Rock(400, 100, mainStage);
        new Rock(200, 100, mainStage);
        new Rock(200, 300, mainStage);
        new Rock(200, 500, mainStage);
        new Rock(400, 500, mainStage);

        spaceship = new SpaceShip(400, 300, mainStage);

        gameOver = false;
    }

    public void update(float dt) {

        generateRocks();

        for(BaseActor rockActor : BaseActor.getList(mainStage, "Rock")) {
            if(rockActor.overlaps(spaceship)) {
                if(spaceship.shieldPower <= 0) {
                    gameOver();
                }
                else {
                    spaceship.shieldPower -= 34;
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    rockActor.remove();
                }
            }

            for(BaseActor laserActor : BaseActor.getList(mainStage, "Laser")) {
                if(laserActor.overlaps(rockActor)) {
                    Explosion boom = new Explosion(0, 0, mainStage);
                    boom.centerAtActor(rockActor);
                    laserActor.remove();
                    rockActor.remove();
                }
            }
        }

        if(!gameOver && BaseActor.count(mainStage, "Rock") == 0) {
            BaseActor messageWin = new BaseActor(0, 0, uiStage);
            messageWin.loadTexture("message-win.png");
            messageWin.centerAtPosition(400, 300);
            messageWin.setOpacity(0);
            messageWin.addAction(Actions.fadeIn(1));
            gameOver = true;
        }
    }

    public void generateRocks() {

    }

    private void gameOver() {
        gameOver = true;

        Explosion boom = new Explosion(0, 0, mainStage);
        boom.centerAtActor(spaceship);
        spaceship.remove();
        spaceship.setPosition(-1000, -1000);

        BaseActor gameOver = new BaseActor(0, 0, uiStage);
        gameOver.centerAtPosition(200, 300);
        gameOver.setOpacity(0);
        gameOver.addAction(Actions.fadeIn(1));
        gameOver.loadTexture("message-lose.png");
    }

    public boolean keyDown(int keycode) {
        if(keycode == Keys.X) {
            spaceship.warp();
        }
        if(keycode == Keys.SPACE) {
            spaceship.shoot();
        }
        return false;
    }
}
