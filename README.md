A work in progress project for the 'Walk to Circle' Android app.

### Google maps API key

To use the repository you will need to create a google maps API key. Create `google_maps_api.xml` file in app/src/release/res/values and app/src/debug/res/values.

```XML
<resources>
    <!--
    TODO: Before you run your application, you need a Google Maps API key.

    To get one, follow this link, follow the directions and press "Create" at the end:

    https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=EC:02:E1:AF:CA:C8:8B:79:57:43:6A:57:1A:C7:91:1F:4D:AD:2F:C9%3Bcom.evgenii.maptest

    You can also add your credentials to an existing key, using this line:
    EC:02:E1:AF:CA:C8:8B:79:57:43:6A:57:1A:C7:91:1F:4D:AD:2F:C9;com.evgenii.maptest

    Alternatively, follow the directions here:
    https://developers.google.com/maps/documentation/android/start#get-key

    Once you have your key (it starts with "AIza"), replace the "google_maps_key"
    string in this file.
    -->
    <string name="google_maps_key" translatable="false" templateMergeStrategy="preserve">YOUR KEY</string>
</resources>
```