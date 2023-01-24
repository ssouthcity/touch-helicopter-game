package dev.southcity.touchcopter

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable
import kotlin.random.Random

class Helicopter : Disposable {
    companion object {
        const val SPEED: Float = 100f
    }

    private val texture: Texture = Texture("plane.png")

    val position: Vector2 = Vector2(
        TouchCopterGame.SCREEN_WIDTH / 2f - texture.width / 2f,
        TouchCopterGame.SCREEN_HEIGHT / 2f - texture.height / 2f
    )

    private val velocity: Vector2 = Vector2(1f, 0f).setLength(SPEED)

    fun update(delta: Float) {
        if (!Gdx.input.isTouched) {
            return
        }

        val destination = Vector2(
            Gdx.input.x.toFloat() / Gdx.graphics.width * TouchCopterGame.SCREEN_WIDTH - texture.width / 2,
            TouchCopterGame.SCREEN_HEIGHT - Gdx.input.y.toFloat() / Gdx.graphics.height * TouchCopterGame.SCREEN_HEIGHT - texture.height / 2,
        )

        velocity.set(destination.x - position.x, destination.y - position.y)

        if (velocity.len() < 0.1f) {
            return
        }

        velocity.setLength(SPEED * delta)

        position.add(velocity)

        if (position.x <= 0 || position.x + texture.width >= TouchCopterGame.SCREEN_WIDTH) {
            position.x = position.x.coerceIn(0f, TouchCopterGame.SCREEN_WIDTH - texture.width) // coercing ensures that the plane doesn't escape the screen for a single frame when at high speeds
        }

        if (position.y <= 0 || position.y + texture.height >= TouchCopterGame.SCREEN_HEIGHT) {
            position.y = position.y.coerceIn(0f, TouchCopterGame.SCREEN_HEIGHT - texture.height)
        }
    }

    fun draw(batch: SpriteBatch) {
        // this call can be simplified by using libgdx's "Sprite" class, but that comes with extra overhead
        batch.draw(
            texture,
            position.x, position.y, // coordinates
            texture.width / 2f, texture.height / 2f, // origin for scale and rotation
            texture.width.toFloat(), texture.height.toFloat(), // dimensions
            1f, 1f, // scaling
            velocity.angleDeg() - 90, // rotation
            0, 0, // source rect bottom left
            texture.width, texture.height, // source rect dimensions
            false, false // flips
        )
    }

    override fun dispose() {
        texture.dispose()
    }
}