A work in progress project for the 'Walk to Circle' Android app.

### Google maps API key

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