<p align="center"><a href="https://safeboda.com/ke/" target="_blank"><img src="https://github.com/jeremy02/Tala-Challenge/blob/main/public/tala_logo.png" width="400"></a></p>

# CASE STUDY FOR Senior Android Engineer (Kotlin) - [Tala](https://tala.co/)

---
### Description
Given the array of data in testData.json, show a vertical scrolling list of cards for each item,
matching the provided comps (screen#.png) based on the data. Users should be able to logically
interact with each card, though what the app does when the user interacts with them is up to
you.
It’s also up to you on how to allow switching between the different provided test data, but each
data item should be a separate experience (don’t show all cards for all data at the same time).
Due to the time limitations for this project, we can't answer questions related to the instructions
or implementation. If you have any questions, please use your best judgment and make a note
of any assumptions you made which you would have otherwise asked about under normal
circumstances. You won't be penalized or asked to redo the test based on any noted incorrect
assumptions.

#### Deliverable
Host the project on github or bitbucket and send us a link. This project should be set up to run
on any machine, so it should not have any unresolved or local references.
Make sure that the project compiles with Java 8 and runs on a Pixel 5 emulator.

#### Screens as Provided
Screen 1A           |  Screen 1B            |  Screen 2
:-------------------------:|:-------------------------:|:-------------------------:
![](public/screen1a.png)  |  ![](public/screen1b.png)  |  ![](public/screen2.png)

Screen 3           |  Screen 4A            |  Screen 4B
:-------------------------:|:-------------------------:|:-------------------------:
![](public/screen3.png)  |  ![](public/screen4a.png)  |  ![](public/screen4b.png)

## Architecture
* Built with Modern Android Development practices
* Utilized Usecase, Repository pattern for data
* The case study expected data to be downloaded from the assets folder, but have taken the liberty of demonstrating both use-cases where
  we get the data from [json file from here](https://github.com/jeremy02/Tala-Challenge/blob/main/app/src/main/assets/testData.json?raw=true) here using retrofit and also the locales data from the assets/locales.json file

## 📱 Download Demo on Android
Download the [APK file from here]() on your Android phone and enjoy the Demo App :)

## TODO
* Includes unit tests for Use cases, Repository, ViewModels, API Service response.
* Complete the various screens so as to create interactive elements which behave as expected for users
* Online/offline experience where if offline the data is downloaded from the API endpoints using Retrofit and Offline fallback to data in assets folder
* Include Search and filter