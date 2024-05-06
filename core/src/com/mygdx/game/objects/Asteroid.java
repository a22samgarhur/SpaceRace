package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.mygdx.game.helpers.AssetManager;
import com.mygdx.game.utils.Methods;
import com.mygdx.game.utils.Settings;

import java.util.Random;

public class Asteroid extends Scrollable {

    private float runTime;
    private Circle collisionCircle;

    public Asteroid(float x, float y, float width, float height, float velocity) {
        super(x, y, width, height, velocity);
        runTime = Methods.randomFloat(0, 1);
        // Creem el cercle
        collisionCircle = new Circle();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        runTime += delta;

        // Actualitzem el cercle de col·lisions (punt central de l'asteroide i del radi).
        collisionCircle.set(position.x + width / 2.0f, position.y + width / 2.0f, width / 2.0f);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);
        // Obtenim un número al·leatori entre MIN i MAX
        float newSize = Methods.randomFloat(Settings.MIN_ASTEROID, Settings.MAX_ASTEROID);
        // Modificarem l'alçada i l'amplada segons l'al·leatori anterior
        width = height = 34 * newSize;
        // La posició serà un valor aleatòri entre 0 i l'alçada de l'aplicació menys l'alçada
        position.y = new Random().nextInt(Settings.GAME_HEIGHT - (int) height);

    }

    // Getter pel runTime
    public float getRunTime() {

        return runTime;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw((TextureRegion) AssetManager.asteroidAnim.getKeyFrame(runTime), position.x, position.y, width, height);
    }

    // Retorna true si hi ha col·lisió
    public boolean collides(Spacecraft nau) {

        if (position.x <= nau.getX() + nau.getWidth()) {
        // Comprovem si han col·lisionat sempre que l'asteroide es trobi a la mateixa alçada que l'spacecraft
            return (Intersector.overlaps(collisionCircle, nau.getCollisionRect()));
        }
        return false;
    }
}

