[![Pull Requests](https://img.shields.io/github/issues-pr/TheRatCode/RatPoison.svg)](https://github.com/TheRatCode/RatPoison/pulls/)
[![Release](https://img.shields.io/github/release/TheRatCode/RatPoison.svg)](https://github.com/TheRatCode/RatPoison/releases/)
![Code size](https://img.shields.io/github/languages/code-size/TheRatCode/RatPoison.svg)
![GitHub repo size](https://img.shields.io/github/repo-size/TheRatCode/RatPoison.svg)
[![Discord](https://dimden.dev/ratpoisonwidget)](https://dimden.dev/ratpoisonowns)
[![Downloads](https://img.shields.io/github/downloads/TheFuckingRat/RatPoison/total.svg)](https://github.com/TheRatCode/RatPoison/releases/latest)
[![GitHub Actions build status](https://img.shields.io/github/workflow/status/TheRatCode/RatPoison/Push?label=GitHub%20Actions)](https://github.com/TheRatCode/RatPoison/actions?query=workflow%3APush+)

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
- [FAQ](#FAQ)
- [Contributing](#contributing)
- [Recent update](#update)
- [Available Features](#features)

## Beginner's guide video <a name = "video"></a>

[![video](https://img.youtube.com/vi/LXBTAnnwahU/0.jpg)](https://www.youtube.com/watch?v=LXBTAnnwahU)

Recommended to watch before continuing.

## Compilation <a name = "compilation"></a>
The first step is to compile the source code into a usable cheat program.

* Download this branch [zip archive](https://github.com/TheRatCode/RatPoison/archive/beta.zip)
* Extract the contents from the archive to any folder (to your desktop, for example).
* Double click on the "_installer_" (_installer.bat_) script and wait for it to complete.

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will be in a directory called **RatPoison 1.8**.

From within the **RatPoison 1.8** directory, you can start the cheat by running
the _"Start RatPoison 1.8"_ script.
If you have chosen to randomize the cheat name, you should open the file ending in .bat which should show ![BatFile](https://i.imgur.com/JZAhmtr.png) as an icon.

You can launch the cheat at any time after launching CSGO.

## FAQ <a name = "FAQ"></a>
* How do I open menu?
    * Press <kbd>INSERT</kbd> while focused on CS:GO window.

* I want to share/download a config, where can I find them?
    * For these purposes, please use https://ratpoison.dimden.dev/

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

* **1.8 Full Update**

    <details>
	<summary>New</summary>
    
        * Added music kit changer
        * Added pelvis to bone list
        * Added customizable automatic weapons delay
        * Added option to use icons instead of weapon's name in Box ESP
        * Added open configs folder button
        * Added custom fonts support
        * Added smoke timer
        * Added blockbot
        * Added autoaccept
        
    </details>

    <details>
        <summary>Fixes</summary>
	
        * Reworked current binds system
        * Fixed Hold Aim not updating in menu
        * Fixed snaplines not drawing to the bomb
        * Fixed being unable to change spectator list position 
        * Snowball weapons were invalid to aimbot
        * Changed dwbSendPackets repository to hazedumper
        * Fixed names in Ranks tab
        * Fixed transparency in some Windows 7 cases
        
    </details>    

    <details>
	<summary>Miscellaneous</summary>
    
        * Changed default menu key to insert
        * Reworked aimbot and misc tabs, now feature sub-tabs
        * Reworked options tab, moved configs to a separate tab
        * Default config now has everything disabled
        * Bumped libgdx version to 1.9.13-SNAPSHOT
        * Improved appless mode
        * Added installer.bat which downloads the installer.exe avoiding malware detection with first download
        
    </details>


## Available Features <a name = "features"></a>

*   **Menu** - customize your settings with menu
*   **Aimbot** - aim assistance
*   **RCS** - recoil control system
*   **Visuals** - render different effects (e.g. glow) on your enemies
*   **Trigger** - automatically fires when crosshair is on enemy
*   **Skin changer** - change weapon skins
*   **Ranks** - reveal ranks of your enemies
*   **Misc** - miscellaneous features that dont' fix any of these categories

<details>
    <summary> Detailed list of features</summary>
    
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
    * Optional sniper/scoped crosshair

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
    * Movement
        * Bunny hop
        * Auto strafe
        * Fast stop
        * Head walk
		* Auto knife
		* Block Bot
	* FOV Changer
	* Bomb
		* Bomb timer (Text Menu, Visual Bars)
		* Timer menu
		* Timer bars
		* Time to explode
		* Perfect bomb defuse
	* Others
	    * Hitsound
        * Killsound
        * Reduced flash
        * Door Spam
        * Weapon Spam
        * Kill bind
        * Disable post processing
        * Temporary name change
        * Spectator list
        * Auto Accept
        * Music kit spoofer
</details>
