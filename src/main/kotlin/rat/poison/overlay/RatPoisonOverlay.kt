

package rat.poison.overlay

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx.gl
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.utils.inBackground
import it.unimi.dsi.fastutil.objects.ObjectArrayList

object RatPoisonOverlay : ApplicationAdapter() {
	
	@Volatile var created: Boolean = false
	
	lateinit var batch: SpriteBatch
	lateinit var camera: OrthographicCamera
	lateinit var shapeRenderer: ShapeRenderer
	lateinit var textRenderer: BitmapFont
	
	private val bodies = ObjectArrayList<RatPoisonOverlay.() -> Unit>()
	
	override fun create() {
		camera = OrthographicCamera(gameWidth.toFloat(), gameHeight.toFloat()).apply {
			setToOrtho(true, gameWidth.toFloat(), gameHeight.toFloat())
		}
		batch = SpriteBatch().apply { projectionMatrix = camera.combined }
		shapeRenderer = ShapeRenderer().apply { setAutoShapeType(true) }
		textRenderer = BitmapFont(true).apply { color = Color.RED }
		
		created = true
	}
	
	override fun render() {
		gl.apply {
			glEnable(GL20.GL_BLEND)
			glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
			glClearColor(0F, 0F, 0F, 0F)
			glClear(GL20.GL_COLOR_BUFFER_BIT)
			
			if (inBackground) return
			
			camera.setToOrtho(true, gameWidth.toFloat(), gameHeight.toFloat())
			batch.projectionMatrix = camera.combined
			shapeRenderer.projectionMatrix = camera.combined
			
			for (i in 0..bodies.size - 1) bodies[i]()
			
			glDisable(GL20.GL_BLEND)
		}
	}
	
	override fun dispose() {
		batch.dispose()
		shapeRenderer.dispose()
		textRenderer.dispose()
	}
	
	operator fun invoke(body: RatPoisonOverlay.() -> Unit) {
		bodies.add(body)
	}
	
}