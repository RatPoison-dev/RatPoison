Update 1.3

* Replaced script.eval for improved performance
    * Faster saving (1-5 seconds)
* Removed override weapons because of possible fps problems, to be implemented later
* Removed aim assist mode and aim assist strictness, as the same can be accomplished easier with aim speed, aim smoothness, and aim strictness
* Removed now useless variables, action log, swap speed min/max
* Removed variables that from viewable settings shouldn't be configured; max entities, cleanup time, process name, garbage collect on startup, process access flags, client module name, engine module name 
* Removed indicator esp (possibly reimplemented later)
* Aimbot will choose closest target (or none) when aiming & bone trigger aiming, will hold target when force aiming
* Fixed & cleaned up bone trigger issues
* Fixed find target global var issues
* Better performance overall

---

Base is Charlatano available [here](https://github.com/Jire/Charlatano)

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
directory, and will in a directory called **RatPoison 1.3**.

From within the **RatPoison 1.3** directory, you can start the cheat by running
the _"Start RatPoison 1.3"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")
