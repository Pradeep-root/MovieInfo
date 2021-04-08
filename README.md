# Mobile Assignment CS
Updated README file by Pradeep kumar Deshmukh, included information about assignment implementation.

## About
This is an Android assignment project developed in Kotlin with MVVM architecture and for testing Mockk and Junit libraries are used. 

### UI/UX
Followed the desing guidlines given as below.
* [Android](https://app.abstract.com/share/0c431216-05c1-45d7-8304-f9e6566276bf)
* Constraint layout
* Custom view created for RatingView by using extending the Andorid View class where startAngle = -90f, maxAngle = 360f, maxProgress = 100 and drown circle using canvas, also included the logic of color inside this view in less then 50% it becomes Yellow and in 50% and more it becomes Green. In this class also placed the logic of percentage rating inside the circle by finding the center of the circle view.
* Recyclerview for horizontal and vertical movie list.
* Glide library for image display.

### Implementation
1. **Architecture** : **_MVVM_** : Keeping separate the UI, business logic, and data resources, ViewModel class extends by ViewModel() it is a Jetpack component, also falowed the Repository methedology. 
2. **Libraries and Jetpack component** 
	- **_Retrofit_** : Retrofit is a REST Client for Java and Android. It makes it relatively easy to retrieve and upload JSON (or other structured data) via a REST based webservice. In Retrofit you configure which converter is used for the data serialization. Typically for JSON you use GSon.

	- **_okhttp3 interceptor_** :  Retrofit uses the OkHttp library for HTTP requests and interceptor is used for network expetions handling and debugging. 

	- **_Coroutines_** : For asynchronous or non-blocking programming. When creating server-side, mobile applications, it's important to provide an experience that is not only fluid from the user's perspective, but scalable when needed.

	- **_Dagger-Hilt_** : For dependency injection.

	- **_LiveData_** : Sharing data anywhere in the class by using observer methedology.

	- **_Glide_** : Glide is an Image Loader Library for Android developed by bumptech and is a library that is recommended by Google. It has been used in many Google open source projects including Google official application. One of the key features of successful and effective image loading is caching.

	- **_Mockk_** : For testing it is new and easy test envoirnment that reducess lots of boiler code. 

	- **_Truth_** : For asserting the result this is good and easy library provied by Google.

## Needfull Handlings
- Shown loading data event handling during web call.
- Web Error handling 
- No Internet connectivity error handling (All type of network connectivity check).
- Proper indication for image loading and error.

<img src="https://user-images.githubusercontent.com/26626575/113974875-49f2e380-985c-11eb-9b72-28ea53d14c6f.png" width="440" height="860">
<img src="https://user-images.githubusercontent.com/26626575/113974882-4cedd400-985c-11eb-9bf7-c6923cee4fa3.png" width="440" height="860">

