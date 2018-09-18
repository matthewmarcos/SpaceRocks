package com.matthewmarcos.spacerocks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SpaceShip extends BaseActor {
    public int shieldPower;
    private Thrusters thrusters;
    private Shield shield;
    private float warpCooldown;
    private float lastWarp;

    public SpaceShip(float x, float y, Stage s) {
        super(x, y, s, "SpaceShip");

        loadTexture("spaceship.png");
        setBoundaryPolygon(8);
        setAcceleration(200);
        setMaxSpeed(100);
        setDeceleration(200);
        thrusters = new Thrusters(0, 0, s);
        addActor(thrusters);
        thrusters.setPosition(-thrusters.getWidth(), getHeight() / 2 - thrusters.getHeight() / 2);
        shield = new Shield(0, 0, s);
        addActor(shield);
        shield.centerAtPosition(getWidth() / 2, getHeight() / 2);
        shieldPower = 100;

        warpCooldown = 1.5f;
        lastWarp = 0f;
    }

    public void act(float dt) {
        super.act(dt);

        // Rotation Speed
        float degreesPerSecond = 120;
        if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            rotateBy(degreesPerSecond * dt);
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            rotateBy(-degreesPerSecond * dt);
        }
        if(Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W)) {
            accelerateAtAngle(getRotation());
            thrusters.setVisible(true);
        }
        else {
            thrusters.setVisible(false);
        }

        // Set warp cooldown
        if(lastWarp > 0f) {
            lastWarp -= dt;

            if(lastWarp < 0) {
                lastWarp = 0;
            }
        }

        applyPhysics(dt);
        wrapAroundWorld(); // Move to the opposite end of the map @ border

        shield.setOpacity(shieldPower / 100f);
        if(shieldPower <= 0) {
            shield.setVisible(false);
        }
    }

    public void warp() {
        if(getStage() == null) {
            return;
        }

        if(this.isWarpReady()) {
            // Still on cooldown
            return;
        }

        lastWarp = warpCooldown;
        Warp warp1 = new Warp(0, 0, this.getStage());
        warp1.centerAtActor(this);
        setPosition(MathUtils.random(800), MathUtils.random(600));
        Warp warp2 = new Warp(0, 0, this.getStage());
        warp2.centerAtActor(this);
    }

    public boolean isWarpReady() {
        return lastWarp <= 0;
    }

    public boolean isLaserReady() {
        return lastWarp <= 0;
    }

    public void shoot() {
        if(getStage() == null) {
            return;
        }
        Laser laser = new Laser(0, 0, this.getStage());
        laser.centerAtActor(this);
        laser.setRotation(this.getRotation());
        laser.setMotionAngle(this.getRotation());
    }
}
