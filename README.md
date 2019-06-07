1.3.2.2
* Removed shift to body shots & shift to shoulder shots from text files
* Added neck & stomach to aim bone options
* Fixed override weapons settings not updating unless you switch weapons first

---

1.3.2.1

Hotfix
* Fixed issue with trying to modify m4a1s settings causing a crash
* Fixed issue with chams team check

---

1.3.2
* Fixed RCS tab not updating text/sliders when loading cfgs
* General file cleanup; removed ScriptsTab
* Added checkbox to disable all aims
* Disabling Aim/Esp disables all related items
* Fixed resizing menu causing issues for the visuals tab
* Moved aim key from misc tab to aim tab

Hotfixes
* Fixed Enable Override checkbox turning on when toggling Enable Aim
* Small file/comment cleanup
* Fixed CFG3 using CFG2s name
* Fixed OPENGL_FPS not being used
* Other small changes

---

1.3.1
* Fixed RCS jumping down when spraying 2+ bullets in quick succession
* Color picker initializes to the correct color instead of black
* Menu alpha cannot go below .5, menu alpha increments by .05 instead of .1
* Moved Radar Esp to visuals tab
* Fixed settings not reverting when unchecking Weapon Override
* Added weapon override info to the warning dialog
* XM is now labeled an automatic weapon
* Max punch renamed to wep delay
* Default value changes

---

1.3

* Replaced script.eval for improved performance
    * Faster saving (1-5 seconds)
* ~~Removed override weapons because of possible fps problems, to be implemented later~~ Reimplemented weapon overrides
* Removed aim assist mode and aim assist strictness, as the same can be accomplished easier with aim speed, aim smoothness, and aim strictness
* Removed now useless variables, action log, swap speed min/max
* Removed variables that from viewable settings shouldn't be configured; max entities, cleanup time, process name, garbage collect on startup, process access flags, client module name, engine module name 
* ~~Removed indicator esp (possibly reimplemented later)~~ Reimplemented indicator esp
* Aimbot will choose closest target (or none) when aiming & bone trigger aiming, will hold target when force aiming
* Fixed & cleaned up bone trigger issues
* Fixed find target global var issues
* Better performance overall

---

Base is Charlatano available [here](https://github.com/Jire/Charlatano)

---

Open to pull requests

---

**Available Features**
* Works on Danger Zone (battle royale)
* Always-on all-weapon smooth dynamic recoil control system
* Aim bot with humanized aim paths
* Low-FOV "perfect aim" (non-silent)
* Aim assistance mode for professional and high-level players
* Reduced flash
* Stream-proof OpenGL overlay with box and skeleton ESP
* Glow ESP (not stream-proof)
* Humanized bunny hop using scroll input
* "Flat" aim bot with traditional linear-regression paths
* Bone trigger with customizable FOV, and bone trigger key
* Recoil crosshair
* Ingame menu (borderless and windowed only)
* Esp indicators of enemies that are off screen

---

**Beginner's Guide**

This branch is being updated at [AStupidRat's Github Repository](https://github.com/AStupidRat/RatPoison)
The official branch is located at [Jire's GitHub Repository](https://github.com/Jire/Charlatano)

Before doing anything, make sure you have
[Java Development Kit (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
installed. This is NOT the same thing as the regular Java Runtime (JRE), and you MUST install the JDK
in order to build Charlatano, as well as any other Java software, and you MUST install JDK 8 (as far as I know)

The first step is to compile the source code into a usable cheat program.

* Visit this branches [GitHub repository](https://github.com/AStupidRat/Charlatano)
* Click on the [**Releases**](https://github.com/Rat/Charlatano/releases) tab and download the latest release ZIP archive
* OR
* Download the latest updates (which includes the newest features) by downloading directly as a ZIP
* Extract the contents from the archive to any folder (to your desktop, for example)
* Make sure you have Java Development Kit 8 or later installed
* Double click on the "_build_" (_build.bat_) script and wait for it to complete

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will in a directory called **RatPoison 1.3.2.2**.

From within the **RatPoison 1.3.2.2** directory, you can start the cheat by running
the _"Start RatPoison 1.3.2.2"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")
