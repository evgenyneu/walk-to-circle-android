# Walk to Circle for Android

This app is made for walking and discovering places around you.

<a href='https://play.google.com/store/apps/details?id=com.evgenii.walktocircle' title='Get Walk to Circle on Google Play'><img src='https://raw.githubusercontent.com/evgenyneu/walk-to-circle-android/master/Graphics/Github/google_play_badge.png' height="60" alt='Get Walk to Circle on Google Play'></a>

Walk to Circle is [also available](https://github.com/evgenyneu/walk-to-circle-ios) on iOS.


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


### Congratulations images

* **Image 3** [Pluto](https://en.wikipedia.org/wiki/Pluto) by NASA / Johns Hopkins University Applied Physics Laboratory / Southwest Research Institute, Sources: [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Pluto-01_Stern_03_Pluto_Color_TXT.jpg), [www.nasa.gov](http://www.nasa.gov/sites/default/files/thumbnails/image/crop_p_color2_enhanced_release.png).

* **Image 4** [Blue Marble Western Hemisphere](http://earthobservatory.nasa.gov/IOTD/view.php?id=8108) by NASA images by Reto Stöckli, based on data from NASA and NOAA. Instrument: Terra - MODIS, Sources: [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Blue_Marble_Western_Hemisphere.jpg), [earthobservatory.nasa.gov](http://earthobservatory.nasa.gov/IOTD/view.php?id=8108).

* **Image 7**: [Emission nebula NGC 6357](https://en.wikipedia.org/wiki/NGC_6357), by NASA, ESA and Jesœs Ma­z Apellÿniz (Instituto de astrof­sica de Andaluc­a, Spain). Acknowledgement: Davide De Martin (ESA/Hubble), Sources: [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:EmissionNebula_NGC6357.jpg), [www.spacetelescope.org](https://www.spacetelescope.org/images/heic0619a/).

* **Image 9**: [Sombrero Galaxy](https://en.wikipedia.org/wiki/Sombrero_Galaxy) by NASA/ESA and The Hubble Heritage Team (STScI/AURA). Sources: [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:M104_ngc4594_sombrero_galaxy_hi-res.jpg), [hubblesite.org](http://hubblesite.org/newscenter/newsdesk/archive/releases/2003/28/image/a).

* **Image 12**: [Abdim's storks in a storm](https://commons.wikimedia.org/wiki/File:Abdim%27s_storks_in_a_storm_(Etosha,_2013).jpg) by Yathin S Krishnappa. Sources: [Wikimedia Commons](https://commons.wikimedia.org/wiki/File:Abdim%27s_storks_in_a_storm_(Etosha,_2013).jpg).

* **Christmas Light Knots** by [dimitri_c](http://www.freeimages.com/photographer/dimitri_c-47343), Source: [freeimages.com](http://www.freeimages.com/photo/christmas-light-knots-1160023)



## Google maps API key

In order to enable Google Map in the app you will need to create a google maps API key. Create `google_maps_api.xml` file in app/src/release/res/values and app/src/debug/res/values.

https://support.google.com/cloud/answer/6158862?hl=en#creating-android-api-keys

```XML
<resources>
    <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve">YOUR KEY</string>
</resources>
```

## TODO

1. User `setExpirationDuration` method in `LocationRequest` object to stop location updates after 30 minutes. Currently this is done with custom code (I was not aware there is a method for it)


## Feeback is welcome

Please create an issue ticket if you have a problem with the app or just want to chat.
