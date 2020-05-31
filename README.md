# LoadingView

![DOWNLOAD](https://img.shields.io/bintray/v/lhoyong/maven/com.github.lhoyong:loadingview)
![Build](https://github.com/lhoyong/LoadingView/workflows/Build/badge.svg)


Horizontal style progressbar.



## OverView

<img src="https://github.com/lhoyong/LoadingView/blob/master/art/overview.gif" width = "264" height = "464"/>


## Setup

Add `root` project build.gradle
~~~xml
allprojects {
    repositories {
        jcenter()
    }
}
~~~

Add `modules` build.gradle

~~~~xml
dependencies {
    implementation 'com.github.lhoyong:loadingview:latestVersion'
}
~~~~



## Usage

Basic example in `xml`

~~~~xml
<com.github.lhoyong.loadingview.LoadingView
        android:layout_width="match_parent"
        android:layout_height="10dp"
        android:layout_marginTop="10dp"
        app:loading_animation="bounce" // choose animation
        app:loading_background_color="#eaeaea" // background color 
        app:loading_color="#cc1247" // loading progressbar color
        app:loading_duration="1000" // animation duration
        app:loading_indicator_width="150dp" // progressbar width
        app:loading_radius="10dp" // progressbar radius 
/> 
~~~~


With manually in code.
 
~~~~kotlin
loadingview.duration = 3000L
loadingview.loadingAnimation = LoadingAnimation.ACCELERATE
loadingview.progressColor = ContextCompat.getColor(this, R.color.black)
~~~~

## Attributes

| Attribute        | Type           | Default  | Example |
| ---------------- |:--------------:| --------:| ------: |
| loading_color      | Color | #000000 | app:loading_color="#cc1247"|
| loading_background_color      | Color      |   transparent | app:loading_background_color="#eaeaea" |
| loading_animation | LoadingAnimation      |    LoadingAnimation.NONE | app:loading_animation="none" |
| loading_duration | Int      |    3000 | app:loading_duration="3000" |
| loading_indicator_width | Dimension      |    100dp | app:loading_indicator_width="150dp" |
| loading_radius | Dimension      |    5dp | app:loading_radius="10dp" |


## License

	Copyright (C) 2020 lhoyong.
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
	   http://www.apache.org/licenses/LICENSE-2.0
	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
