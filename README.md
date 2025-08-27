<h1>Weather App</h1>

<p>I made this application from scratch (based on MVVM  pattern) using the official documentation, online videos and some advice from Claude AI</p>
<p>This application is meant for learning and diving into a new environment like Android Studio! I've never programmed in Kotlin and I was also curious about trying jetpack compose</p>
<p>I know that the app is far from finished, in fact I have many features I want to implement in the future, but I think it can be considered at least presentable</p>


<h2>Features</h2>
<ul>
  <li><p>Weather Information in a certain place</p></li>
  <li><p>City Research</p></li>
  <li><p>Caching for previous data</p></li>
  <li><p>List of all cities the user has searched</p></li>
</ul>

<h2>Supported Versions</h2>
<p>Tested on:</p>
<ul>
  <li>Android API 36 (Virtual Device)</li>
  <li>Android 13 (Physical Device)</li>
</ul>


<h2>Setup Instructions</h2>
<p>If you want to try the application you have to:</p>
<ul>
  <li><p>Create an account on <a href="https://openweathermap.org/">OpenWeather</a></p></li>
  <li><p>Generate an API key</p></li>
  <li><p>Create a local.properties file in the root directory of the project</p></li>
 <li><p>Add your API Key in the local.properties file:</p>
  <pre>
    sdk.dir=C:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
    WEATHER_API_KEY=your_api_key_here
  </pre></li>
  <li><p>Wait for Gradle sync to complete</p></li>
</ul>
<p>"this is only for security matters. In this case the APIs aren't sensitive, but I decided to secure it anyway</p>


<h2>Future Updates</h2>
<ul>
  <li>Better UI in the main page and city search page</li>
  <li>Storing user's past research even when the app is closed</li>
</ul>


<h2>Mentions</h2>
<h3>All the icons I used are available on <a href= "https://lottiefiles.com/">LottieFiles</a></h3>
<h3>Weather data provided by <a href="https://openweathermap.org/">OpenWeatherMap API</a>(free version)</h3>


<h2>Screenshots</h2>
<p align="center">
  <img src="screenshots/Screen1.jpg" width="250" alt="Main Screen" style="margin: 0 10px;">
  <img src="screenshots/Screen2.jpg" width="250" alt="City Search" style="margin: 0 10px;">
  <img src="screenshots/Screen3.jpg" width="250" alt="Weather Details" style="margin: 0 10px;">
  <br>
  <em>Main Weather Display | City Search | Search Results with History</em>
</p>
