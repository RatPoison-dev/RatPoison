Update 1.2 + 1.2.5 + 1.2.6

* Aimbot should be a lot better at finding the correct target with multiple enemies near the crosshair

* Added customizable aim settings for all weapons
* Saving will work in background, shouldn't freeze game/not responding overlay
* 3 total CFGs possible instead of original 1
* When using glow you now have the option to show what enemy the aimbot will target
* Small cleanup
* Bone trigger is more accurate towards finding the chest bone (3 through 7 now instead of just 6)
* Fixed aim on bone trigger

Latest Hotfixes:
Fixed MENU = false launching the overlay anyways, (possibly) fixed FPS issues & degrading over time, fixed settings returning to default randomly, (possibly) fixed aimbot/trigger not working with a new weapon, improved cfg save times, fixed saving to default reading cfg files, (possibly) fixed crashing in non-comp games when showing aimbot's target, fixed grenade and highlight color being on the right sides, fixed highlight color missing on init. Fixed menu being too big on init, (possibly) fixed toggling esp off crashing, aim duration default 1 -> 4, OPENGL_FPS 60 - > 30, (possibly) fixed aimbot target crashing game, fixed aim not working in danger zone


WARNING: This update has not been tested extensively, I assume many issues will pop up, please submit an issue on github
with information on the issue, the settings used (create a cfg file and put it in a pastebin), and evidence (such as a picture/video)
or a way to easily replicate the issue.
The custom per weapon settings are available in the GunAimOverride.kts file, in arrays, the gun is the variable name, with the array
information at the top.

Known/Possible Issues:
* Individual weapon aim settings don't override while saving (Tested)
* FPS degradation over time (Unknown)
* Bhop 100% cpu usage/out of memory (Unknown)
* Aimbot/Trigger bot stop working when switching to a new weapon (Possibly Fixed)

(Tested) = I have replicated and know it is an issue.
(unknown) = I can't replicate/haven't encountered the issue myself.

---

The Default Menu Key is F1

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
directory, and will in a directory called **RatPoison 1.2.6**.

From within the **RatPoison 1.2.6** directory, you can start the cheat by running
the _"Start RatPoison 1.2.6"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")
