### Support Jire (Charlatano)

**Donate with Bitcoin:** 3GqvcPY4rKcMxKU3jE59A2UHfV3NAyUhA6  
**Or with PayPal:** thomasgnappo@gmail.com

---

1.4

* Nearest Bone is now based off of your recoil instead of the center of your screen
* Box ESP details now shows player name
* Resized health bar in Box ESP details
* Fixed default override settings being out of range
* Added show bomb carrier settings to glow esp and indicator esp
* Fixed indicator esp being the wrong color when changed before reinitializing
* Cleaned up Scripts kts file, renamed to MiscScripts and moved relevant script enables to their own files
* SpectatorList and BombTimer moved to their own new files
* Added crosshair option for snipers
* Small changes to bomb timer bars
* Bomb timer and spectator list now save position/alpha through saves

---

1.3.4

* Updated default settings to look more legit
* Rewrote recoil crosshair for (easier) readability
* Added rCrosshair x offset, rCrosshair y offset to allow for the recoil crosshair to be adjusted to work on borderless or windowed without issue
* libGDX 1.9.3 -> 1.9.9
* VisUI 1.1.1 -> 1.4.2
* Jdk 1.8 -> 12
* Added X button to main menu, which closes the program
* Updated visual of spectator list, and bomb timer
    * Updated bomb timer layout to match spectator list
* Fixed issue with override weapons not working with weapon overrides already enabled on launch
* Fixed issue with override weapons defaulting to STOMACH when NEAREST was selected
* Fixed bone trigger only working with menu enabled
* Fixed aim speed snapping when less than 10
* Aim speed is now between 1 - 5 (aim speed is ms delay per move)
* Aim strictness & smoothness slider max changed from 10 -> 5
* Cleaned up all menu tabs excluding Options

* Hotfixes
    * Fixed overlay defaulting to OVERLAY_WIDTH/HEIGHT, will use CSGO's W&H if initialized fast enough
    * Fixed issue with not being able to plant bomb, shooting in menu
    * Fixed W2S being offset due to an issue when initializing overlay
    * Fixed gradle causing errors with kotlin dependencies in IDE
    * Added DEBUG variable in general.kts to help find initialization issues

---

1.3.3 + 1.3.2.3

* Added auto strafe
* Added spectator list (updated to show spectators of entity being spectated by local player (1.3.3))
* Added fast stop (1.3.3)
* Added NEAREST to aim bone selection (1.3.3)
* Added MENU_APP to draw the overlay on an app other than CSGO for fullscreen users (1.3.3)
* Fixed rifle settings not being used
* Readded bone trigger delay & added to menu
* Fixed aimbot fucking up when aiming at targets a certain way due to yawDiff calculation error, shown [here](https://gyazo.com/e297d0e2e07b4a730611e81b8279cc5f)
    * When the target is highlighted light blue it was seen as being inside the aimbot's fov
* Fixed aimbot not working in a special case, shown [here](https://gyazo.com/ae8a9d1dde6fcdecb07e29bf64d7054c)
* Fixed override weapons only using AK47's settings (1.3.3)
* Fixed RCS issue causing jumps in aim, shown [here](https://gyazo.com/d5ae6fb790e19e7aeea6e6e11132f4c2) (1.3.3)
* (possibly) Fixed showing dormant entities on esps

* Possible Issue: Glow esp might crash on startup, shown [here](https://gyazo.com/fd42b3ffc712f40dd80166578ae5d003)

---

1.3.2.2

* Added knife bot (Credit to Mr. Noad)
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

This branch is being updated at [TheFuckingRat's Github Repository](https://github.com/TheFuckingRat/RatPoison)
The official branch is located at [Jire's GitHub Repository](https://github.com/Jire/Charlatano)

Before doing anything, make sure you have
[Java Development Kit (JDK) 12](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html)
installed. [Alternative.](https://jdk.java.net/12/) This is NOT the same thing as the regular Java Runtime (JRE), and you MUST install the JDK
in order to build Charlatano, as well as any other Java software, and you MUST install JDK 12

The first step is to compile the source code into a usable cheat program.

* Download the latest updates (which includes the newest features) by downloading directly as a ZIP
* Extract the contents from the archive to any folder (to your desktop, for example)
* Make sure you have Java Development Kit 12 installed, in Program Files\Java
* Double click on the "_build_" (_build.bat_) script and wait for it to complete

To set your JAVA_HOME go [here](https://javatutorial.net/set-java-home-windows-10)

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will in a directory called **RatPoison 1.4**.

From within the **RatPoison 1.4** directory, you can start the cheat by running
the _"Start RatPoison 1.4"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")
