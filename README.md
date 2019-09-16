### Support Jire (Charlatano)

**Donate with Bitcoin:** 3GqvcPY4rKcMxKU3jE59A2UHfV3NAyUhA6  
**Or with PayPal:** thomasgnappo@gmail.com

---

Open to pull requests

Credit to Mr. Noad for a lot of help throughout this project

---

1.5
I didn't plan to release this as soon as I have, but I haven't been working on it as much recently, so it was best to push with all the fixes/changes it has.
Not heavily tested, bugs expected.

**HIGHLIGHTS**
* Default VisUI skin changed to [tinted](https://github.com/kotcrab/vis-ui-contrib/tree/master/skins/tinted)

* Improvements to box esp 
    * Added an armor bar
    * Health and armor bars have black outlines (currently non-customizable)
    * Heath/Armor bar can be moved to either left or right of the box
    * Name/Weapon text can be moved to either top or bottom of the box
    
* Expanded on the previous cfg save/load system
    * Uses a drop down box to select a CFG to load/delete
    * Can now create as many CFGS as you want
    * Saves to settings\CFGS\
    * Added delete cfg button (deletes selected cfg)
    * Save button prompts for a cfg name, typing the name of a previously saved CFG overwrites that cfg
    
* Other ESPs shouldn't flicker when using glow

* Added a RANKS tab to the menu, will show ranks when available (Incomplete, semi-viable)

* Added Defuse Kit ESP options (box & indicator only currently)

* Added visual hitmarker (customizable)

* Added 'Adrenaline' option in visuals - on landing a shot/based on bomb time left

* Added a map grenade throw helper (currently Mirage only) - see below for details/usage

* Added enemy snaplines

**Other**

* General cleanup
* Cleaned up UI Tab code to scale easily, plug string/variable name into a function for a complete table instead of having to type out the table, slider, label, checkbox, etc
* Recoil crosshair is now only visible when the menu isn't visible, or the menu is visible, the recoil crosshair or sniper crosshair is enabled, and RCS is the opened tab
* Small improvements to r/w in box and skeleton esp
* Fixed bone trigger shot delay, works as an initial delay, will still full spray aslong as a target is kept in fov
* Removed skeleton show dormant from text file, script
* Added checks to bone trigger to fix non-automatics not shooting when constantly locked, and manually firing
* Fixed weapons not unglowing when disabling esp
* Fixed bone trigger shooting (clicking) on startup
* Glow/Chams esp should turn respective parts off when disabled (fixes problems such as weapons staying glowed)
* Added options in bone trigger to enable it for certain weapon categories only (pistol/rifle/etc)
* Added a cfg of the legit settings to reset to them if needed
* Added a blank cfg with all settings disabled/at their lowest values
* Cleaned up all settings files, rewrote comments for settings
    * Moved some settings to new areas to make more sense
* Nearest bone should be more accurate, should aim at head(instead of neck)/feet/hands now
* Changed auto stop to key presses instead of writing to the game
* Option in chams for self color (hands/gun)
* Visuals colors should change correctly, instead of snapping to a certain colors only
* All visuals have their own colors, not a global "team color" etc for all
* Fixed crashing in Danger Zone
* Fixed checking/unchecking "Weapon Override" changing the override settings to the categories settings
* All ESPs should work in danger zone
* RCS changed from 1 smoothing bar back to X/Y smoothing
    
**Overlay Lag**

Overlay lag/fps issues should be heavily reduced

**Grenade Helper**
Overlay Only
Configurable in the Options tab, under the CFG settings. Files hold all positions from when saved in the menu. The template file included is Mirage.txt, load
into a test/offline Mirage map, make sure it's selected in the drop down and then click the 'Load From File' button. Then press the key corresponding to the 
'Nade Helper Toggle Key' (Default F2) to toggle visibility of the positions around the map. They are only visible when within a certain distance, to make sure you can see them
the main spots are T spawn, A ramp, and top mid, if you don't see any in these areas press the toggle key a few times to see if they do pop up, if the file is loaded
(testable in the cmd, should say 'Loading Positions'), and walk around a bit to see if you were not in range before. The 'Clear Currently Loaded' button clears all positions currently loaded. The 'Create Grenade Position'
button saves your current position, and where you are looking, and asks you to enter a name for the position (ex. 'Smoke Mid'), so go to a position, test the throw and if it's how how you want
it go back to that spot, aim appropriately and save the position (or save before and delete the position if you have to). The 'Delete At Current Position' button deletes a position if you are standing on top of it, and 
is highlighted blue. The starting head position isn't completely centered on your screen, to line yourself up with the position, look straight down and center your cursor with the
middle of the circle, then look up and aim at the circle in the air.

**Hotfixes**
* Hotfix 1
    * Added settings checks for nade tracer & helper
    * Updating the viewmatrix for w2s is called once per overlay update instead of multiple times throughout all visual scripts using it
    * Extra for crash fixes
    
* Hotfix 2 
    * Fixed nade helper not showing spots near position correctly
    * Combo can be used without having the hitmarker enabled

---

Base is Charlatano available [here](https://github.com/Jire/Charlatano)

---

**Available Features**
* Menu (Borderless/Fullscreen only)

* Aimbot
    * Flat Aim: Write to view angles
    * Path aim: Mouse movement
    * Group aimbot settings (Pistol/Rifle/Sniper/Shotgun/SMG)
    * Individual aimbot settings (AK/M4/etc)
    * Aim bone, speed, FOV, strictness, smoothness
    * (non-silent) Perfect aim, customize FOV and % chance to activate
    
* RCS
    * RCS for all weapons, customizable X & Y smoothing
    * Customizable recoil crosshair, width/length/positioning/color

* Visuals
    * Radar
    * Glow
        * Teammates, Enemies, Weapons, Grenades, Bomb, Bomb Carrier, Aimbot Target
    * clrRender chams, brightness
        * Teammates, Enemies
    * Arrow indicators
        * Teammates, Enemies, Weapons, Grenades, Bomb, Bomb Carrier
    * Box (Health, Armor, Name, Weapon)
        * Teammates, Enemies
    * Skeleton
        * Teammates, Enemies

* Bone Trigger
    * Customizable FOV, Head/Torso selection
    * Optional for each weapon category (Pistol/Rifle/Sniper/Shotgun/SMG)
    * Enable aimbot on activation (Magnet)
    * Activate on trigger key

* Danger Zone compatible

* Misc
    * Knife bot
    * Hitsounds
    * Hitmarker
    * Bomb timer (Text Menu, Visual Bars)
    * Spectator list (Text Menu)
    * Optional sniper/scoped crosshair
    * Reduced flash
    * Bunny hop
    * Auto strafe
    * Fast stop
    
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
directory, and will in a directory called **RatPoison 1.5**.

From within the **RatPoison 1.5** directory, you can start the cheat by running
the _"Start RatPoison 1.5"_ script.

Before starting the cheat, you should already be spawned in a game with a weapon in hand!

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")

---

Common Issues (Will be updated over time)
* Overlay is black/doesn't go transparent/isn't showing up
    * The overlay only works on Windows 10, it doesn't turn transparent on W7
    * CSGO needs to be borderless/windowed for the overlay to show up ontop of CS, otherwise it's just a app in your taskbar and doesn't attach
