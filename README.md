![alt tag](https://raw.githubusercontent.com/n0ise9914/wait/master/screenshot/states.png)
# Wait
#### Tired of making loadings, no-network warnings and working progresses for different parts of your app?<br/>
#### Using wait you create these layouts once, then use them anywhere of your app via waitLayout.
#Setup
Add wait to your build.gradle dependencies.
```gradle
dependencies {
    compile "com.n0ize:wait:0.0.1"
}
```
# Usage
- add waitLayout to your layout (just remember to put this view on top of all other views).
Some Examples:
```xml
  <com.n0ize.wait.WaitLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
```
- now you access it from your activity or fragment.
```java
//binding to waitLayout
WaitLayout waitLayout = (WaitLayout) findViewById(R.id.wait_layout);

//show loading
waitLayout.setState(State.LOADING);

//hide loading
waitLayout.setState(State.NOTE);

//show nothing to show view
waitLayout.setState(State.EMPTY);

//show retry layout
waitLayout.setState(State.RETRY);

//show working progress
waitLayout.setState(State.WORKING);

//this event triggers when user clicks on retry button and network be avalable. 
waitLayout.setOnConnectedListener(new OnConnectedListener() {
    @Override
    public void OnConnected() {
        Log.i(TAG, "MainActivity.OnConnected: ");
    }
});
```   
   
# Customization
you can override loading, no-network and working layouts with your owns,<br/>
just by putting the code below inside [Application](https://developer.android.com/reference/android/app/Application.html) class instance of your app.
```java

final Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Roboto.ttf");

WaitConfiguration config = new WaitConfiguration.Builder()
		.setRetryMessage("Check your connection and try again.")
		.setRetryButtonText("retry")
		.setTypeFace(typeface)
		.setLoadingViewRes(R.layout.loading_layout)
		.setWorkingViewRes(R.layout.working_layout)
		.setRetryViewRes(R.layout.retry_layout)
		.build();

Wait.setConfiguration(config);
```
#### Note: anyway if you wanna have more different custom waitLayouts, you can set your custom views directly to waitLayout.<br/>
#### Example:
```java
waitLayout.setLoadingView(R.layout.custom_loading);
```
#### Note: for custom retry layouts, i suggest you to use @+id/retry_button for your retry button, otherwise you should handle network events yourself.
## Customizing appearance
put lines below inside AppTheme in styles.xml.
```xml
<item name="wt_working_progress_color">#ffa201</item>
<item name="wt_loading_progress_color">#ffdd00</item>
<item name="wt_background_color">#ffdd00</item>
```
# Network loss handling
theory: we have three fragments inside a viewPager on our activity, we open up the app and see three retry layouts<br/>
now ater clicking retry button connection established, here wait can tell your another fragments to get refreshed.<br/>
put the code below inside your fragments, it well be automatically triggered when connection establishes.
```java
Wait.addOnConnectedListeners(new OnConnectedListener() {
    @Override
    public void OnConnected() {
	loadData();
    }
});
```
#### Note: dont forget to call Wait.refresh() in onDestory of your activity (otherwise you'll run into trouble).
