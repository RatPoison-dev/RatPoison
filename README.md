### Support Jire (Charlatano)

**Donate with Bitcoin:** 3GqvcPY4rKcMxKU3jE59A2UHfV3NAyUhA6  
**Or with PayPal:** thomasgnappo@gmail.com

---

Open to pull requests

Credit to Mr. Noad for a lot of help throughout this project

---

**Content updates are paused**

**Bug fixes only**

---
1.5.3

* Overlay default to csgo width/height, fallback on OVERLAY_WIDTH/HEIGHT
* Changed RCS update speed 10 > 4 to improve it's accuracy
* Added always force aim checkbox 
* Added alive check to bone trigger to prevent shooting at entities such as vents

**Hotfix 1**
* (probably) Fixed Box ESP crashing the overlay
* Added setting option to use GL3/Gles3, might fix shader compatibility issues

---
1.5.2 

* Names of some settings have changed, I recommend to reconfig from default

* Chams should update faster (every 1/4 second instead of half)
* Bone Trigger changes
    * Added incross option
    * Can choose between incross and infov, or use both 
    * Removed options to trigger inside FOV for torso bones (3-7) and head (8)
        * Now always loops through 3-8 and will trigger on the first bone within the fov (starting from 3 to 8)
    * Now inside the aim tab, not a separate tab
    * Settings are customizable for each weapon category (pistol, rifle, etc)
    * Wont shoot immediately after scoping in (assuming scoped only && has a delay)
* Headwalk changes
    * Fixed key presses
    * Will only attempt to stay on players head when not moving around (pressing WASD)
    * Head walk updates every 2ms instead of 5ms
    * Removed key toggle, if enabled will auto activate ontop of a players head
    * Can be enabled/disabled without experimental 
* Possible fix for box esp crashing the overlay
* Fixed self chams coloring when disabled
* Added check to auto strafe for in air only
* Added OpenGL FPS to menu
* Added global target swap delay
* Menu alpha will affect the override weapons menu
* Small changes to default aim settings
* Overlay grabs width/height from settings instead of from CSGO
* Disabled aimbot with knife/grenade out
* Force aiming with bone trigger enabled shouldn't shuffle between certain bones, only the aimbots selected bone
* Menu Key moved from Misc tab to Options
* Removed bunny hop key setting, is now always VK_SPACE
* Nade helpers foot lineup uses the games matrix instead of w2s-ing an ellipse which wasn't accurate

**Hotfix 1**
* Possible fix for snapline error
* Increased nearest bone memory size to prevent crashing

---
1.5.1

* Bone trigger should be responsive and usable now
    * Fixed delay issues

* Removed Aim Key setting (perm mouse1)
* Small changes to nade helper drawing lines
* The name of the position will be under the circle to throw at instead of position on the ground
* Small Cleanup
    * GunAimOverride.kts is readable and less of a mess if you choose to edit via text files instead of the menu, but still a block
* All guns have their override disabled by default, and use default settings
* KTS settings files changed to .txts
* A warning will appear to clear the current positions in nade helper to prevent accidental clears before saving
* The main menu is now topmost vs the override weapons panel, spectator list, bomb timer
* Auto strafe moved to keypresses instead of writing to the game
* Strafe calls every 2ms instead of every 5ms to help accuracy
* Added aim strafe
* Updated visual colors on menu for consistency

**Hotfixes/Version Updates**
* Hotfix 1
    * Auto strafer slider interval changes from .2 to .1
    * Added checks to strafehelper
    * Bone trigger updates every 10ms instead of every 4ms
    * CursorEnable called when needed, shouldn't jump when typing/robot movement spam in chat
    
* Hotfix 2
    * More checks to prevent keypresses while typing
    * Possible fix to random movement presses
    
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

If you have older versions of saved txt files from before Hotfix 6 these will cause an error when loaded using RP. 
To fix you need to edit the text file and add the nade type (Flash, Smoke, Molly, Frag) after the position name, see the example Mirage.txt file for an example, case sensitive and must be spelled correctly

**Hotfixes/Version Updates**
* Hotfix 1
    * Added settings checks for nade tracer & helper
    * Updating the viewmatrix for w2s is called once per overlay update instead of multiple times throughout all visual scripts using it
    * Extra for crash fixes
    
* Hotfix 2 
    * Fixed nade helper not showing spots near position correctly
    * Combo can be used without having the hitmarker enabled

* Hotfix 3
    * Made override weapons more intuitive to enable/disable and edit
    * Hitmarker outline wont draw when hitmarker is disable but combo is enabled

* Hotfix 4 
    * Radar updates slower to help prevent other esp flickering
    * Cursor enabled should update faster (if typing/in buy menu) to prevent bhopping when chatting/automatic weapons shooting in buy menu
    * Weapon Override menu will appear/disappear when loading a cfg
    * Menu isn't resizable again
    
* Hotfix 5
    * Flash max alpha slider is from 1-255
    * Nade tracer has a color option now
    * Fix saving to default hitsound name + flash max alpha
    * Nade helper moved from the options tab to it's own tab
    * Combined visuals checkboxes and their associated color
    * Added checkbox for nade helper
    
* Hotfix 6
    * Override Weapons should update values when changing the selected weapon
    * Possible fix for fast stop 'issuing too many commands to server' with radio spam/scroll jumping
    * Possible fix for certain entities not glowing
    * Reduced number of reads in aim script
    * Nade helper select file in appropriate spot
    * Nade helper now only shows based on what grenade you are holding (hold a decoy to view all for editing purposes)
        * Previously created versions will not work, see above under "Grenade Helper" for info
        
* Hotfix 7
    * Perfect Aim Collapsible should collapse correctly now
    * Overriden Weapons now updates properly
    * Indicator defuse kits should work again
    * Small reduction to # of reads
    
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
    * Enemy Snaplines
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
    * FOV, InCross
    * Customizable for each weapon category (Pistol/Rifle/Sniper/Shotgun/SMG)
    * Aimbot on activation
    * Optional trigger key
    * Initial shot delay

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
[Java Development Kit (JDK) 13](https://www.oracle.com/technetwork/java/javase/downloads/jdk13-downloads-5672538.html)
installed. [Alternative.](https://jdk.java.net/13/) This is NOT the same thing as the regular Java Runtime (JRE), and you MUST install the JDK
in order to build Rat Poison, Charlatano, and any other Java software, and you MUST install JDK 13

The first step is to compile the source code into a usable cheat program.

* Download the latest updates (which includes the newest features) by downloading directly as a ZIP
* Extract the contents from the archive to any folder (to your desktop, for example)
* Make sure you have Java Development Kit 13 installed, in Program Files\Java
* Double click on the "_build_" (_build.bat_) script and wait for it to complete

To set your JAVA_HOME read [here](https://javatutorial.net/set-java-home-windows-10)

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will in a directory called **RatPoison 1.5.3**.

From within the **RatPoison 1.5.3** directory, you can start the cheat by running
the _"Start RatPoison 1.5.3"_ script.

You can launch the cheat any time after launching CSGO.

---

**Beginner's Guide Video**

[![Beginner's Video](http://i.imgur.com/pVLW5Yr.png)](https://www.youtube.com/watch?v=PapjDtR0OMY "Charlatano")

---

Common Issues
* Overlay is black/doesn't go transparent/isn't showing up
    * The overlay only works on Windows 10, it doesn't turn transparent on W7
    * CSGO needs to be borderless/windowed for the overlay to show up on top of CS, otherwise it's just an app in your taskbar
