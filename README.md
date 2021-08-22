# SlidingButton

![SlidingButton](https://i.ibb.co/JFvj2P5/Screen-Shot-2021-08-21-at-12-54-13.png)
## Setup 
### Gradle

Add this to your project level `build.gradle`:
```groovy
allprojects {
 repositories {
    maven { url "https://jitpack.io" }
 }
}
```
Add this to your app `build.gradle`:
```groovy
dependencies {
    implementation 'com.github.edtslib:SlidingButton:1.0.1'
}
```
# Usage

The SlidingButton is very easy to use. Just add it to your layout like any other view.
##### Via XML

Here's a basic implementation.

```xml
    <id.co.edtslib.slidingbutton.SlidingButton
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:id="@+id/slidingButton"
        android:layout_marginStart="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginTop="16dp"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:text="Hello World!" />
```
### Attributes information

An example is shown below.

```xml
        app:activated="true"
        app:textColor="@color/test_color"
        app:slideColor="#ffff00"
        app:arrowColor="@color/bg_arrow"
        app:textStyle="@style/TextStyle"
```

##### _app:text_
[string]: text of button

##### _app:activated_
[boolean]: true is mean initial state of SlidingButton is enabled for slide, default true

##### _app:textColor_
[integer]: color resource of button text, default: 

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- normal button text color -->
    <item android:color="#FFFFFF" android:state_activated="false" />
    <!-- slide over button text color -->
    <item android:color="@color/colorBlue" android:state_activated="true" />
</selector>
```

##### _app:textStyle_
[reference]: style of button text, default not set

##### _app:slideColor_
[color]: color of slide, default blue

##### _app:arrowColor_
[reference]: color resource of arrow container, default:

```xml
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- color when arrow drag -->
    <item android:color="#ffffff" android:state_selected="true"/>
    <!-- color when arrow idle -->
    <item android:color="@color/colorBlue"/>
</selector>
```

### Listening for slide actions on the SlidingButton

You can set a listener to be notified when the user slides across the SlidingButton. An example is shown below.

```kotlin
        val slidingButton = findViewById<SlidingButton>(R.id.slidingButton)
        slidingButton.delegate = object : SlidingButtonDelegate {
            override fun onCompleted() {
                Toast.makeText(this@MainActivity, "Add some action on here", Toast.LENGTH_SHORT).show()
            }
        }
```
### Method for slide actions on the SlidingButton


```kotlin
    // enabled/disabled slide button view
    override fun setActivated(activated: Boolean)
    
    // reset slide button to initial state 
    fun reset()
```





