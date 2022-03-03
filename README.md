# Albums

This app aims to display a list of song titles.

I built this app following a MVVM architecture as it is the recommended way by Google to build a clean app.
This architecture includes the following layers, each one depending on the next one :
- UI layer
- ViewModel layer
- Repository layer
- Data source layer

I used the following libraries and components recommended by Google :

- Gson :
Recommended library to build and read JSON data.

- Retrofit :
Recommended library to easily perform http queries. I also used a Gson converter to directly map results to model instances.

- Hilt :
Recommended library to perform dependency injections. Hilt is built on top of Dagger and allows writing less boilerplate code.

- Lifecycle components :
ViewModel and LiveData components are the perfect way to monitor data as it follows Activity / Fragment lifecycle and survive to configuration changes (for example, screen orientation). UI layer can observe LiveData and be notified when it changes.

- Coroutines :
Coroutines allow to easily perform async operations with Kotlin, without the need of callbacks.

- Glide :
Recommended library to fetch and display images from urls. It includes options to crop and transform the image to display it properly.