package rat.poison.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.kotcrab.vis.ui.VisUI
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.overlay.App
import java.io.File

var updateFonts = true

class AssetManager: AssetManager() {
    val fonts = mutableMapOf<String, FreeTypeFontGenerator>()

    fun find(name: String): Any? {
        return when {
            this.contains(name) -> {
                this.get(name)
            }
            fonts.contains("$SETTINGS_DIRECTORY/Assets/Fonts/$name.ttf") -> {
                fonts["$SETTINGS_DIRECTORY/Assets/Fonts/$name"]
            }
            this.contains("$SETTINGS_DIRECTORY/Assets/Images/$name.png") -> {
                this.get("$SETTINGS_DIRECTORY/Assets/Images/$name.png")
            }
            else -> null
        }
    }

    fun updateFontsList() {
        File("$SETTINGS_DIRECTORY/Assets/Fonts").listFiles()?.forEach {
            if (!fonts.containsKey(it.toString())) {
                val generator = FreeTypeFontGenerator(Gdx.files.internal(it.toString()))
                fonts[it.toString()] = generator
            }
        }
    }

    fun loadMusic() {
        File("$SETTINGS_DIRECTORY\\hitsounds\\").listFiles()?.forEach {
            this.load(it.toString(), Sound::class.java)
        }
        this.finishLoading()
    }

    fun updateFonts() {
        if (!updateFonts) return

        updateFontsList()

        val defaultFont = "$SETTINGS_DIRECTORY\\Assets\\Fonts\\${curSettings["FONT"].replace("\"", "")}.ttf"
        val font = fonts[defaultFont]
        if (font != null) {
            //apply font settings
            val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
            parameter.size = curSettings.int["FONT_SIZE"]
            parameter.color = curSettings.colorGDX["FONT_COLOR"]

            //border
            parameter.borderWidth = curSettings.float["FONT_BORDER_WIDTH"]
            parameter.borderColor = curSettings.colorGDX["FONT_BORDER_COLOR"]
            parameter.borderStraight = curSettings.bool["FONT_BORDER_USE_STRAIGHT"]

            //shadow
            parameter.shadowColor = curSettings.colorGDX["FONT_SHADOW_COLOR"]
            parameter.shadowOffsetX = curSettings.int["FONT_SHADOW_OFFSET_X"]
            parameter.shadowOffsetY = curSettings.int["FONT_SHADOW_OFFSET_Y"]

            parameter.kerning = curSettings.bool["FONT_INCLUDE_KERNING"]

            //just a bruh moment
            parameter.characters += "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
            parameter.characters += "aąbcćdeęfghijklłmnńoóprsśtuwyzźżAĄBCĆDEĘFGHIJKLŁMNŃOÓPRSŚTUWYZŹŻ"

            parameter.flip = curSettings.bool["FONT_FLIP"]
            parameter.genMipMaps = curSettings.bool["FON_GEN_MIP_MAPS"]
            parameter.gamma = curSettings.float["FONT_GAMMA"]

            val skin = Skin()

            val generatedFont = font.generateFont(parameter)
            App.textRenderer = generatedFont
            if (!VisUI.isLoaded()) {
                skin.add("default-font", generatedFont, BitmapFont::class.java)
                skin.addRegions(TextureAtlas(Gdx.files.internal(("skin/tinted.atlas"))))
                skin.load(Gdx.files.internal("skin/tinted.json"))
                VisUI.load(skin)
            }
        }
        else {
            curSettings["FONT"] = "VisOpenSans"
            updateFonts()
        }
        updateFonts = false
    }


    fun loadAssets() {
        loadMusic()
        File("$SETTINGS_DIRECTORY/Assets/Images").listFiles()?.forEach {
            this.load(it.toString(), Texture::class.java)
        }
        this.finishLoading()
    }
}