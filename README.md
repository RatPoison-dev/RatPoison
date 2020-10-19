#### [Support Ratto (Ratpoison)](https://www.patreon.com/ratto)

#### Support Spravedlivo (Ratpoison + [Installer](https://github.com/SPRAVEDLIVO/RatInstaller/))
**Donate with Bitcoin:** bc1qmxkd2dalzwdy0dhhqh8v64m9xj0nz0g52l292e

#### Support Jire (Charlatano)
**Donate with Bitcoin:** 3ErbuRTxtFqvMQ7F2Z8SfpgAXjonsQs8Vc
**Or with PayPal:** enisitysoft@gmail.com
**Or with CashApp:** $jires

---

This branch is being updated at [TheFuckingRat's Github Repository](https://github.com/TheFuckingRat/RatPoison)

The official branch (Charlatano) is located at [Jire's GitHub Repository](https://github.com/Jire/Charlatano).

---

## 📝 Table of Contents

- [Beginner's guide video](#video)
- [Compilation](#compilation)
- [Common issues](#issues)
- [Contributing](#contributing)
- [Recent update](#update)
- [Grenade helper](#grenade-helper)
- [Available Features](#features)

## Beginner's guide video <a name = "video"></a>

[![video](https://img.youtube.com/vi/LXBTAnnwahU/0.jpg)](https://www.youtube.com/watch?v=LXBTAnnwahU)

Recommended to watch before continuing.

## Compilation <a name = "compilation"></a>
The first step is to compile the source code into a usable cheat program.

* Download the latest updates (which includes the newest features) by downloading directly as a ZIP.
* Extract the contents from the archive to any folder (to your desktop, for example).
* Double click on the "_installer_" (_installer.exe_) script and wait for it to complete.

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will be in a directory called **RatPoison 1.7**.

From within the **RatPoison 1.7** directory, you can start the cheat by running
the _"Start RatPoison 1.7"_ script.
If you have chosen to randomize the cheat name, you should open the file ending in .bat which should show ![BatFile](https://i.imgur.com/JZAhmtr.png) as an icon.

You can launch the cheat at any time after launching CSGO.

## Common issues <a name = "issues"></a>
* Overlay is black/doesn't go transparent/isn't showing up
    * The overlay only works on Windows 10, it doesn't turn transparent on W7
    * CSGO needs to be borderless/windowed for the overlay to show up on top of CS, otherwise it's just an app in your taskbar
    * JDK 1.8 is also known to cause this problem, make sure you have JDK 12 or later installed

* Game looks framey despite having normal FPS (example 144hz/144fps, but looks like 60)
    * Lowering opengl fps to 30 helps
    * Enabling gsync & disabling vsync if supported
   
* Cheat is stuck at launching? Make sure you have checked all of those steps:
    * you are running currently most up-to-date version of RatPoison
    * you disabled all anti-cheat clients working on your computer
    * your RatPoison folder is placed somewhere with all running permissions
    * you don't use RatPoison with some other cheats running
    * you aren't currently running VAC bypass (running the bat file with administrator privileges should work)
    * you restarted your computer

    If nothing else works then you can try running the bat file as admin.

## Contributing <a name = "contributing"></a>
Open to pull requests

Credit to [Mr. Noad](https://github.com/10urshin), [2772/Matt](https://github.com/matt1tk), [Eston](https://github.com/sotakoira), and [Spravedlivo](https://github.com/SPRAVEDLIVO) for a lot of help throughout this project


## Recent update <a name = "update"></a>

1.7 Full Update
* Important
	* Added backtrack
	* Fixed ForEntities loop not fully iterating, consistency should be improved for features in Aim & Visuals tabs
	
* New
	* Added Helmet, Kevlar, Flashed, Scoped, and Ammo settings to Box ESP
	* Added locale options in menu, you can change menu language with premade localization files
	* Added glow and box health based options
	* Added flicker free glow
	* Added static fov option
	* Added draw aim fov, draw trigger fov, for static fov only
	* Added nightmode/fullbright option
	* Added aim on shot option, aimbot will only aim when your gun can shoot
	* Added gaussian blur option when menu is open
	* Added hold target aimbot options
	* Added force bone key + selector (to categories only)
	* Added keybinds window under options tab to display all keybinds, set to -1 to disable
	* Added entity dead and dormant check to prevent ghost footsteps
	
* Fixes
	* Triggerbot has been fixed
	* Fixed hitsound error when menu disabled, hitsound only available when using the menu
	* Fixed ranks tab layout
	* Fixed advanced aim settings error when using 0 X/Y Variation
	* Fixed automatic weapons shooting with cursor enabled (during buy menu)
	* Fixed nearest bone not working with menu disabled
	* Fixed cmd read/write files when menu is disabled
	
* Miscellaneous
	* Updated ranks tab, should work better & show all players
	* Reworked override settings
	* Minor adjustments to headwalk and spectatorlist
	* Glow esp is now one write per entity instead of multiple
	* Changed RANK names to abbreviations to prevent hiding elements
	* Perfect aim is now calculated per aim move rather than per target
	* Loading settings/configs should only load .txt or .cfg files respectively
	* Scanner can now save/load/delete configs, and display ranks/kills/deaths/wins
	* Changed default settings to look more legit
	* Fully disabled knife changer
	* Removed knife changer for now
	* Force aim bone should update properly on config change
	* Color Picker is now similar to most mainstream cheats, less ugly
	* Visuals toggle key should now disable bomb timer, spectator list, recoil crosshair
		* Bomb timer, spectator list, recoil crosshair require 'Enable ESP' to be checked in the visuals tab to work
	* Box details can be used without having the bounding box enabled
	* Removed flick scroll for now
	* M249 is now considered a rifle 
	* Changed Flat Aim name to Write Angles on menu
	* Changed Path Aim name to Mouse Movement on menu
	* All ui elements are drawn on 1 stage using 1 input processor instead of 4
	* Cleaned up menu for better consistency across tabs
	* Skinchanger should forceupdate properly, should see less lag & faster activation when equipping new weapons
	* Skinchanger tab should now update with configs
	* Updated list of skins for skinchanger
	* Skinchanger will only update the local players bought weapons, not dropped weapons

## Grenade helper <a name = "grenade-helper"></a>
Overlay Only
Configurable in the Nade Helper Tab. Files hold all positions from when saved in the menu. The template file included is Mirage.txt, load
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


## Available Features <a name = "features"></a>
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
    * clrRender chams
        * Teammates, Enemies
    * Arrow indicators
        * Teammates, Enemies, Weapons, Grenades, Bomb, Bomb Carrier, Defusers
    * Box (Health, Armor, Name, Weapon)
        * Teammates, Enemies, Defusers
    * Skeleton
        * Teammates, Enemies
    * Snaplines
        * Teammates, Enemies, Weapons, Bomb, Bomb Carrier
    * Footsteps
        * Teammates, Enemies
    * FOV Changer
    * Skin Changer
    * Knife Changer (Models Only)

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
    * Last second bomb defusal
    * Door spam
    * Weapon spam
    * Self nade
