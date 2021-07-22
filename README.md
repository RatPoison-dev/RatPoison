[![Pull Requests](https://img.shields.io/github/issues-pr/TheRatCode/RatPoison.svg)](https://github.com/TheRatCode/RatPoison/pulls/)
[![Release](https://img.shields.io/github/release/TheRatCode/RatPoison.svg)](https://github.com/TheRatCode/RatPoison/releases/)
![Code size](https://img.shields.io/github/languages/code-size/TheRatCode/RatPoison.svg)
![GitHub repo size](https://img.shields.io/github/repo-size/TheRatCode/RatPoison.svg)
[![Discord](https://discord.com/api/guilds/785830829439320095/widget.png)](https://discord.gg/TGsp8SNcFZ)
![Downloads](https://img.shields.io/github/downloads/TheFuckingRat/RatPoison/total.svg)

## ⚠️ __RatPoison is VAC detected, use at own risk. For more updates join our [Discord server](https://discord.gg/TGsp8SNcFZ)__ ⚠️

## Support our Team

Hundreds of hours have gone into developing Charlatano and RatPoison. Feel free to show our team some love.

* **_[Ratto](https://github.com/TheRatCode)_** on [Patreon](https://www.patreon.com/ratto)
* **_[Spravedlivo](https://github.com/Spravedlivo)_** on [Patreon](https://www.patreon.com/spravedlivo)
    * Or with BTC: _bc1qmxkd2dalzwdy0dhhqh8v64m9xj0nz0g52l292e_
    * Or with PayPal: _wewestteam1@gmail.com_
* **_[Jire](https://github.com/Jire)_** on [Patreon](https://www.patreon.com/Jire)
    * Or with BTC: _3ErbuRTxtFqvMQ7F2Z8SfpgAXjonsQs8Vc_
    * Or with CashApp: _jires_

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

* Console is being spammed with GLFW errors
    * Usually a result of unsupported or misconfigured hardware
    * In some cases <code>GLFW_PLATFORM_ERROR</code> can be fixed by [forcing dedicated GPU on java.exe process](https://github.com/libgdx/libgdx/issues/6048#issuecomment-660468942).
    * If RatPoison 1.6 worked fine for you but 1.7 spams OpenGL errors, try editing [this](https://github.com/TheRatCode/RatPoison/blob/82f7a76e6115c8840e4188cffdb8f10b831ae385/src/main/kotlin/rat/poison/RatPoison.kt#L183) line to <code>useOpenGL3(true, 3, 2)</code> and rebuild the cheat.

* Game looks framey despite having normal FPS (example 144hz/144fps, but looks like 60)
    * Lowering opengl fps to 30 helps
    * Enabling gsync & disabling vsync if supported
   
* Cheat is stuck at launching? Make sure you have checked all of those steps:
    * CS:GO is running
    * you are running currently most up-to-date version of RatPoison
    * you disabled all anti-cheat clients working on your computer
    * your RatPoison folder is placed somewhere with all running permissions
    * you don't use RatPoison with some other cheats running
    * you aren't currently running VAC bypass (running the bat file with administrator privileges should work)
    * you restarted your computer
    
 		**If nothing else works then you can try running the bat file as admin.**
	
* Map is unusually bright after joining the server/Nightmode does not work
	* Untick Disable Post Processing option in Misc tab
	
* Backtrack does not seem to work, hit rate is awfully low
	* Unbind mouse1 in console, disable spotted only in backtrack settings or use triggerbot with shoot backtrack enabled.
	
* Installer is detected by antivirus as a malicious program
	* False-positive, add the executable to exclusions or exclude the whole folder in your antivirus.

## Contributing <a name = "contributing"></a>
Open to pull requests

Credit to [Mr. Noad](https://github.com/10urshin), [2772/Matt](https://github.com/matt1tk), [Eston](https://github.com/sotakoira), and [Spravedlivo](https://github.com/SPRAVEDLIVO) for a lot of help throughout this project


## Recent update <a name = "update"></a>

1.7.1.4

* Gradle 7.1.1 & Kotlin 1.5.21 support
* Bumped dependencies
* Updated to work with latest game update
* Lowered default OpenGL requirement to 4.0

1.7.1.3

* Changed dwbSendPackets repository to [frk1/hazedumper](https://github.com/frk1/hazedumper/)
* Box ESP crash fix

1.7.1.2

* Changed dwbSendPackets repository to [JokinAce/CSGO-Offsets](https://github.com/JokinAce/CSGO-Offsets)

1.7.1.1

* Added new operation skins
* Fixed ESP showing wrong weapons due to a game update

1.7.1

* Fixed nadehelper crashes
* Hitsounds no longer randomly play in menu

1.7 Full Update
* Important
	* Added backtrack
	* Fixed ForEntities loop not fully iterating, consistency should be improved for features in Aim & Visuals tabs
	* Added installer executable
		* Installer automatically downloads appropriate JDK, allows the cheat to be updated and built without any hassle
	
* New
	* Added Helmet, Kevlar, Flashed, Scoped, Ammo and Money settings to Box ESP
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
	* Added legit radar option, shows enemies making footstep noise within set distance
	* Added appless menu type
	* Added disable post processing option (does not work with nightmode)
	* Added far radar box, draws a box around enemies spotted by other players
	* Added head level helper, draws a line on your sight with customizable deadzone
	* Added smoke check to Glow, Box, Snaplines and Indicator
	* Added auto throwing to nade helper
	* Added temporary name change, resets after joining another server
	* Added stable RCS, applies to total aimpunch/recoil over time
	
* Fixes
	* Triggerbot has been fixed
	* Fixed hitsound error when menu disabled, hitsound only available when using the menu
	* Fixed ranks tab layout
	* Fixed advanced aim settings error when using 0 X/Y Variation
	* Fixed automatic weapons shooting with cursor enabled (during buy menu)
	* Fixed nearest bone not working with menu disabled
	* Fixed cmd read/write files when menu is disabled
	* Fixed taser spamming SetAim failure with Override Weapons enabled
	* Fixed teammates are enemies not aimbotting friendly players
	* Fixed fast stop blocking movement on high latency servers
	* Fixed bomb timer and spectator list opacity sliders not updating after loading a config
	
* Miscellaneous
	* Updated ranks tab, should work better & show all players
	* Reworked override settings
	* Minor adjustments to head walk and spectator list
	* Glow ESP is now one write per entity instead of multiple
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
	* Updated nade helper to work with menu disabled
	* Removed standalone autostrafer
	* Changed chams brightness slider to allow more precise adjustments
	* FOV sliders now allow float values
	* Changed step values of some sliders
	* Added more information in debug screen


## Available Features <a name = "features"></a>
* Menu (Windowed/Fullscreen Windowed only)
	* Appless: Menu shows as an app on the taskbar

* Aimbot
    * Flat Aim: Write to view angles
    * Path aim: Mouse movement
    * Group aimbot settings (Pistol/Rifle/Sniper/Shotgun/SMG)
    * Individual aimbot settings (AK/M4/etc)
    * Aim bone, force bone, aim on shot, speed, FOV, strictness, smoothness
    * (non-silent) Perfect aim, customizable FOV and % chance to activate
	* Backtrack: Shoot enemies up to 200 ms back in time

* RCS
    * RCS for all weapons, customizable X & Y accuracy
	* Types: Legacy and Stable
    * Customizable recoil crosshair, type/width/length/positioning/color

* Visuals
    * Radar
		* Legit radar
    * Glow (Normal, Model, Visible, Visible Flicker)
        * Teammates, Enemies, Weapons, Grenades, Bomb, Bomb Carrier, Aimbot Target
    * clrRender chams
        * Teammates, Enemies, Self, Aimbot Target
    * Arrow indicators
        * Teammates, Enemies, Weapons, Grenades, Bomb, Bomb Carrier, Defusers
    * Box (Name, Health, Armor, Helmet, Kevlar, Weapon, Money, Ammo, Flashed, Scoped)
        * Teammates, Enemies, Defusers, Weapons
    * Skeleton
        * Teammates, Enemies
    * Snaplines
        * Teammates, Enemies, Weapons, Bomb, Bomb Carrier, Defuse Kits
    * Footsteps
        * Teammates, Enemies
	* Hitmarker
	* Nade tracer
    * FOV Changer
    * Skin Changer
	* Spread Circle
	* Head Level Helper

* Bone Trigger
    * FOV, InCross
    * Customizable for each weapon category (Pistol/Rifle/Sniper/Shotgun/SMG)
    * Aimbot on activation
    * Optional trigger key
    * Initial shot delay
	* Between shots delay
	* Shoot at backtrack

* Danger Zone compatible

* Misc
    * Knife bot
    * Hitsounds
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
	* Nade helper
    * Self nade
	* Kill bind
	* Disable Post Processing
	* Temporary name change

## Check out YourKit!
[![YourKit](https://www.yourkit.com/images/yklogo.png)](https://www.yourkit.com/)

YourKit supports open source projects with innovative and intelligent tools 
for monitoring and profiling Java and .NET applications.
YourKit is the creator of [YourKit Java Profiler](https://www.yourkit.com/java/profiler/),
[YourKit .NET Profiler](https://www.yourkit.com/.net/profiler/),
and [YourKit YouMonitor](https://www.yourkit.com/youmonitor/).
