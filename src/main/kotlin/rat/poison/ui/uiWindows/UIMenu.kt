package rat.poison.ui.uiWindows

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.OptionDialogAdapter
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisWindow
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import org.lwjgl.glfw.GLFW
import rat.poison.*
import rat.poison.overlay.App
import rat.poison.overlay.App.appOverlay
import rat.poison.scripts.misc.sendPacket
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.changed
import rat.poison.ui.uiTabs.*
import rat.poison.ui.uiUpdate
import kotlin.math.sign
import kotlin.system.exitProcess

val mainTabbedPane = TabbedPane()
    var aimTab = AimTab()
    var visualsTab = VisualsTab()
    var rcsTab = RcsTab()
    var miscTab = MiscTabs()
    var ranksTab = RanksTab()
    var nadeHelperTab = NadeHelperTab()
    var skinChangerTab = SkinChangerTab()
    var optionsTab = OptionsTab()
    var configsTab = ConfigsTab()

private var uid = 0//randInt(2, 999999)

class UIMenu : VisWindow("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid") {
    private val normHeight = 750F
    private val normWidth = 950F
    var activeTab: Tab = aimTab

    //Changed through runtime
    var wantedHeight = normHeight
    var wantedWidth = normWidth

    //Main content pane for all tabs
    private val mainTabbedPaneContent = VisTable(false)

    //Scroll pane for the content pane, content pane goes inside
    val mainScrollPane = ScrollPane(mainTabbedPaneContent) //Init scroll pane containing main content pane

    private var isResizingHeight = false
    private var isResizingWidth = false

    var lastCheckedTab = VisTextButton("")

    init {
        defaults().left()

        addCloseButton()

        mainTabbedPaneContent.padTop(10F)
        mainTabbedPaneContent.padBottom(10F)
        mainTabbedPaneContent.align(Align.top)

        mainScrollPane.setFlickScroll(false)
        mainScrollPane.setScrollbarsVisible(true)

        //Main ui window settings
        x = 960F
        y = 540F
        align(Align.topLeft)
        isResizable = true

        //Add tabs to the tab header
        mainTabbedPane.add(aimTab)
        mainTabbedPane.add(visualsTab)
        mainTabbedPane.add(rcsTab)
        mainTabbedPane.add(miscTab)
        mainTabbedPane.add(ranksTab)
        mainTabbedPane.add(nadeHelperTab)
        mainTabbedPane.add(skinChangerTab)
        mainTabbedPane.add(optionsTab)
        mainTabbedPane.add(configsTab)

        //Set aim tab as the first (init) tab
        mainTabbedPane.switchTab(aimTab)
        //Add aim tab content to the table
        mainTabbedPaneContent.add(aimTab.contentTable).left()

        val buttonColor = Color(.13F, 0.66F, 1F, 1F)
        val buttonClicked = Color(.13F, 0.4F, 1F, 1F)

        //AimButton
        val aimButton = VisTextButton("Aim", "tab-bar")
        aimButton.color = buttonColor

        aimButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(aimTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = aimButton
            activeTab = aimTab
            aimButton.color = buttonClicked
            true
        }

        //VisualsButton
        val visualsButton = VisTextButton("Visuals", "tab-bar")
        visualsButton.color = buttonColor

        visualsButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(visualsTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = visualsButton
            activeTab = visualsTab
            visualsButton.color = buttonClicked
            true
        }

        //RcsButton
        val rcsButton = VisTextButton("RCS", "tab-bar")
        rcsButton.color = buttonColor

        rcsButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(rcsTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = rcsButton
            activeTab = rcsTab
            rcsButton.color = buttonClicked
            true
        }

        //MiscButton
        val miscButton = VisTextButton("Misc", "tab-bar")
        miscButton.color = buttonColor

        miscButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(miscTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = miscButton
            activeTab = miscTab
            miscButton.color = buttonClicked
            true
        }

        //RanksButton
        val ranksButton = VisTextButton("Ranks", "tab-bar")
        ranksButton.color = buttonColor

        ranksButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(ranksTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = ranksButton
            activeTab = ranksTab
            ranksButton.color = buttonClicked
            true
        }

        //NadeHelperButton
        val nadeHelperButton = VisTextButton("Nade Helper", "tab-bar")
        nadeHelperButton.color = buttonColor

        nadeHelperButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(nadeHelperTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = nadeHelperButton
            activeTab = nadeHelperTab
            nadeHelperButton.color = buttonClicked
            true
        }

        //SkinsButton
        val skinsButton = VisTextButton("Skins", "tab-bar")
        skinsButton.color = buttonColor

        skinsButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(skinChangerTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = skinsButton
            activeTab = skinChangerTab
            skinsButton.color = buttonClicked
            true
        }

        //OptionsButton
        val optionsButton = VisTextButton("Options", "tab-bar")
        optionsButton.color = buttonColor

        optionsButton.changed { _, _ ->
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(optionsTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = optionsButton
            activeTab = optionsTab
            optionsButton.color = buttonClicked
            true
        }

        //ConfigsButton
        val configsButton = VisTextButton("Configs", "tab-bar")
        configsButton.color = buttonColor

        configsButton.changed { _, _ ->
            configsTab.updateCFGList()
            configsTab.updateSkinCfgList()
            mainTabbedPaneContent.clear()
            mainTabbedPaneContent.add(configsTab.contentTable).growX().left()
            lastCheckedTab.color = buttonColor
            lastCheckedTab = configsButton
            activeTab = configsTab
            configsButton.color = buttonClicked
            true
        }

        //Add tab pane & scroll pane to main ui window
        add(aimButton).growX().prefWidth(105F).left()
        add(visualsButton).growX().prefWidth(105F).left()
        add(rcsButton).growX().prefWidth(105F).left()
        add(miscButton).growX().prefWidth(105F).left()
        add(ranksButton).growX().prefWidth(105F).left()
        add(nadeHelperButton).growX().prefWidth(105F).left()
        add(skinsButton).growX().prefWidth(105F).left()
        add(optionsButton).growX().prefWidth(105F).left()
        add(configsButton).growX().prefWidth(105F).left().row()
        add(mainScrollPane).left().growX().prefWidth(950F).colspan(9)
        centerWindow()
        pack()

        //Update all tab content
        uiUpdate()
        changeWidth()
        changeHeight()
    }

    fun closeMenu() {
        haltProcess = true
        disableAllEsp()
        sendPacket(true)
        //GLFW.glfwTerminate()
    }


    public override fun close() {
        Dialogs.showOptionDialog(App.menuStage, "Warning", "Are you sure you want to close RatPoison?", Dialogs.OptionDialogType.YES_NO, object: OptionDialogAdapter() {
            override fun yes() {
                println("Close button pressed. Unloading...")
                closeMenu()
            }
        })
    }

    private val defaultAlpha by lazy(LazyThreadSafetyMode.NONE) {
        curSettings.float["MENU_ALPHA"]
    }

    internal fun changeAlpha(alpha: Float = defaultAlpha) {
        color.a = alpha
    }

    private fun changeHeight() {
        if (!isResizingHeight) {
            isResizingHeight = true
            Thread {
                while (true) {
                    val difHeight = wantedHeight - height
                    val dChange = sign(difHeight) * 2

                    if (height in wantedHeight - 4..wantedHeight + 4) {
                        isResizingHeight = false
                        break
                    }

                    height += dChange
                    y -= dChange
                    Thread.sleep(1)
                }
            }.start()
        }
    }

    private fun changeWidth() {
        if (!isResizingWidth) {
            isResizingWidth = true
            Thread {
                while (true) {
                    val difWidth = wantedWidth - width
                    val dChange = sign(difWidth)

                    if (width in wantedWidth - 4..wantedWidth + 4) {
                        isResizingWidth = false
                        break
                    }

                    width += dChange * 2
                    //x -= dChange*2
                    Thread.sleep(1)
                }
            }.start()
        }
    }

    fun updateTitle() {
        titleLabel.setText("$TITLE $F_VERSION - [$M_VERSION $BRANCH] - $LOADED_CONFIG - UID: $uid")
    }
}

