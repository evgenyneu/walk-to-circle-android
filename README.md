# Walk to Circle for Android

This app is made for walking and discovering places around you.

<a href='https://play.google.com/store/apps/details?id=com.evgenii.walktocircle' title='Get Walk to Circle on Google Play'><img src='https://raw.githubusercontent.com/evgenyneu/walk-to-circle-android/master/Graphics/Github/google_play_badge.png' height="60" alt='Get Walk to Circle on Google Play'></a>

Walk to Circle is [also available](https://itunes.apple.com/us/app/walk-to-circle/id955310614) on iOS.


<img src="https://raw.githubusercontent.com/evgenyneu/walk-to-circle-android/master/GooglePlayMetadata/Screenshots/1080_1920_2.png" alt="Walk to Circle for Android  screenshot" width="360">

## Credit

### Sounds

* **Blop** Recorded by Mark DiAngelo. http://soundbible.com/2067-Blop.html

* **Click sound** Recorded by Mike Koenig http://soundbible.com/783-Click.html

* **Large door slam** Recorded by stephan http://soundbible.com/1551-Large-Door-Slam.html

* **Pin drop** Recorded by Mike Koenig http://soundbible.com/1073-Pin-Drop.html

* **Snow Ball Throw And Splat** Recorded by Mike Koenig http://soundbible.com/632-Snow-Ball-Throw-And-Splat.html


### Applause and cheer sounds

* **Applause 1** https://www.freesound.org/people/xtrgamr/sounds/243799/

* **Applause 2** http://www.soundjay.com/applause-sounds-1.html

* **Applause 3** https://www.freesound.org/people/xtrgamr/sounds/252808/

* **Applause 4** http://www.soundjay.com/applause-sounds-1.html

* **Applause 6** https://www.freesound.org/people/johansyd/sounds/35964/

* **Applause 7** Recorded by Mike Koenig http://soundbible.com/1700-5-Sec-Crowd-Cheer.html

* **Applause 9** http://www.soundjay.com/applause-sounds-1.html

* **Applause 12** https://www.freesound.org/people/mlteenie/sounds/169233/

* **Applause 15** https://www.freesound.org/people/AlaskaRobotics/sounds/221567/

* **Applause 20** Recorded by Stephan http://soundbible.com/693-Fireworks-Finale.html



## Google maps API key

To use the repository you will need to create a google maps API key. Create `google_maps_api.xml` file in app/src/release/res/values and app/src/debug/res/values.

https://support.google.com/cloud/answer/6158862?hl=en#creating-android-api-keys

```XML
<resources>
    <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve">YOUR KEY</string>
</resources>
```

## Scaling drawables

* *ldpi*: 0.75x
* *mdpi*: 1x
* *hdpi*: 1.5x
* *xhdpi*: 2x
* *xxhdpi*: 3x
* *xxxhdpi*: 4x (Launcher icon only)


## Feeback is welcome

Please create an issue ticket if you have a problem with the source code or just want to chat.
