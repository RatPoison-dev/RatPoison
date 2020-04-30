### Support Jire (Charlatano)

**Donate with Bitcoin:** 3GqvcPY4rKcMxKU3jE59A2UHfV3NAyUhA6  
**Or with PayPal:** thomasgnappo@gmail.com

---

This branch is being updated at [TheFuckingRat's Github Repository](https://github.com/TheFuckingRat/RatPoison)
The official branch is located at [Jire's GitHub Repository](https://github.com/Jire/Charlatano).

---

**Beginner's Guide Video**

--> **[Beginner's Video](https://www.youtube.com/watch?v=BnvuJJCyF9Y "Rat Poison")** <-- Recommended to watch before continuing.

---

**Beginner's Guide**


*Note: AdoptOpenJDK is now automatically installed, and the java home variable automatically set!
This means you won't _need_ Java Development Kit 13. If the hack isn't working, make sure to uninstall any installed JDK first!*

The first step is to compile the source code into a usable cheat program.

* Download the latest updates (which includes the newest features) by downloading directly as a ZIP.
* Extract the contents from the archive to any folder (to your desktop, for example).
* Double click on the "_build_" (_build.bat_) script and wait for it to complete.
* If you don't have the JDK you will have the option to install it through the build.bat file, this is recommended.

Once those steps are complete, the usable cheat program can be found within the _build_
directory, and will be in a directory called **RatPoison 1.6**.

From within the **RatPoison 1.6** directory, you can start the cheat by running
the _"Start RatPoison 1.6"_ script.
If you have chosen to randomize the cheat name, you should open the file ending in .bat which should show ![BatFile](https://i.imgur.com/JZAhmtr.png) as an icon.

You can launch the cheat at any time after launching CSGO.

---

**Common Issues**
* Overlay is black/doesn't go transparent/isn't showing up
    * The overlay only works on Windows 10, it doesn't turn transparent on W7
    * CSGO needs to be borderless/windowed for the overlay to show up on top of CS, otherwise it's just an app in your taskbar

* Game looks framey despite having normal FPS (example 144hz/144fps, but looks like 60)
    * Lowering opengl fps to 30 helps

---

Open to pull requests

Credit to Mr. Noad & 2772/Matt for a lot of help throughout this project

---

**Bug fixes only**

**ROADMAP:**
* Add bomb in fov check for last second bomb Defuse
* Add auto update script (from github)

---

**Recent Update**

Update 1.6

* Skin Changer
    * Skin Changer for all weapons, with skins per weapon options
    * Knife Changer, some have broken animations, and no skin selection

* Indicator Visuals
    * Should now work properly (same as radar)
    * Heavily cleaned up indicator code, reduced multiple redundancies
    * Added Indicator Arrow Size option
    * Removed OnScreen and Oval options for now
    * Indicator color now uses the selected colors alpha, not just a static 0.5
    * Now properly colors indicator of entity if they are the bomb carrier

* Box Esp
    * Skeleton is now lumped in with box visuals
    * Can change box detail text color (instead of just white)
    * Fixed box esp crash caused by ConcurrentModification & libgdx threading issues
    * Box of box visuals is now much more accurate and dynamic

* Glow Esp
    * Now properly colors entity if they are the bomb carrier

* Snaplines Esp
    * Added team, bomb, bomb carrier, weapons snaplines and colors
    * Added line width
    
* Added Step Esp
    * Update timer
    * Step TTL (Time to live)
    * As text or circle
    
* Possible fix for headwalk activating incorrectly
* Choosing a target while spraying now activates properly based on your recoil's position rather than crosshair
* Option to use aim after X shots for rifles and smgs
* Disabled zeus causing bone trigger and aim crashing
* Added weapon spam (famas/glock/scope spam) and door spam
* Added self nade, throws nade in mid air to damage yourself. mostly used for griefing. (hold nade and press "self nade" in menu)
* Misc tab reformatted for testing
* Default settings have been updated to look legit
* Debug now shows misc information on screen
* Recoil crosshair adjusts correctly based on FOV

* Removed aim strictness
    * Strictness served as an additional smoothing, which added confusion and redundancy
    * Smoothness is smoothing applied to the calculated angle towards the target
    * Speed is the ms delay between mouse movements (path aim) from your current angle to the calculated target angle, or between writing angles (flat aim)
    * Aim strictness/aim sensitivity is now always your ingame sensitivity + .5 to prevent jittery snapping
    * Aim Speed is now maxed at 10 instead of 5
    * Added multiple more aim settings for randomization (will be added to override weapons eventually)
        * Aim X/Y Variation per mouse movement, and a deadzone where it wont activate (path aim only)
        * Configurable X/Y RCS for the aimbot instead of max constantly, and a variation amount
        * Speed Divisor which divides the target mouse movement by that #
    
* Added RANDOM aim bone option, picks one of the 4 selectable bones
    * Will hold selected bone until aim is released or target is invalid

* Added option to disable a vis check when force aiming, to force aim through walls
* Target swap delay now works properly
* Added FOV Changer (Misc Tab)
    * Options for normal weapons, awp zoom levels (defaulted for clear scope)

* Added discord link to menu

---

**Grenade Helper**
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
