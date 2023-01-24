package dev.southcity.touchcopter

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils

class TouchCopterGame : ApplicationAdapter() {
    companion object {
        const val SCREEN_WIDTH = 360f
        const val SCREEN_HEIGHT = 240f
        const val SCREEN_MARGIN = 16f
    }

    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var camera: OrthographicCamera
    private lateinit var helicopter: Helicopter

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        camera = OrthographicCamera()
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT)
        helicopter = Helicopter()
    }

    override fun render() {
        val deltaTime = Gdx.graphics.deltaTime

        helicopter.update(deltaTime)

        ScreenUtils.clear(0f, 0f, 0f, 1f)
        batch.projectionMatrix = camera.combined

        batch.begin()
        helicopter.draw(batch)
        font.draw(batch, helicopter.position.toString(), SCREEN_MARGIN, SCREEN_HEIGHT - SCREEN_MARGIN)
        batch.end()
    }

    override fun dispose() {
        batch.dispose()
        helicopter.dispose()
    }
}