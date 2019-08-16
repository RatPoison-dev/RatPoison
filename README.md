### Support Jire (Charlatano)

**Donate with Bitcoin:** 3GqvcPY4rKcMxKU3jE59A2UHfV3NAyUhA6  
**Or with PayPal:** thomasgnappo@gmail.com

---

1.4.1

* Made # of hitsounds scalable, add sound files to the hitsounds folder in settings
* Added skeet, metal, error, oof hitsounds, any hitsounds you want to add yourself must be atleast 300ms long, and for formatting anti-error purposes, export your own .wav in audacity
* Fixed delay issue with the COD hitsound
* Added an option to only auto strafe when bhopping (holding the bhop key)
* Fixed smoothness being out of 10 instead of the updated max of 5, should help with making settings more or less legit looking
* Default settings updated
    * Fixed left over default override settings being out of range

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
directory, and will in a directory called **RatPoison 1.4.1**.

From within the **RatPoison 1.4.1** directory, you can start the cheat by running
the _"Start RatPoison 1.4.1"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")
