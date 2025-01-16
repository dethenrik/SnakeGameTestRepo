# SnakeGameTestRepo

Welcome to **SnakeGameTestRepo**! This is a simple Snake game built with **Jetpack Compose** in **Kotlin** for Android. The project demonstrates menus, game logic, persistent data storage, asynchronous calls, and more.

---

## Table of Contents

1. [Overview](#overview)  
2. [Features](#features)  
3. [Requirements](#requirements)  
4. [Installation](#installation)  
5. [Running the App](#running-the-app)  
6. [Known Errors](#known-errors)  
7. [License](#license)

---

## Overview

**SnakeGameTestRepo** showcases a basic Snake game with:
- A **Menu screen** to navigate between the game, high scores, async examples, and settings.
- A **Settings** screen to switch between **Button-based** or **Swipe-based** movement controls.
- A **High Scores** screen that persists and displays high scores using `SharedPreferences`.
- Basic **async** operations (like calling a demo API).

The code is organized in Kotlin files with Jetpack Compose, and uses `build.gradle.kts` scripts for configuration.

---

## Features

- **Snake Game Logic**:
  - Automatically moving snake with collision checks.
  - Score increments when the snake eats food.
- **Dynamic List** (High Scores).
- **Persistent Data** via `SharedPreferences`.
- **Menu and Settings** to switch control schemes (buttons or swipe gestures).
- **Asynchronous Example** using a simple network call.

---

## Requirements

- **Operating System**: Windows 11 (tested)
- **Android Studio**: Ladybug Feature Drop \| 2024.2.2 or newer
- **Gradle**: 8.2.0 or newer
- **Kotlin Version**: 1.9.23 or newer
- **Compile SDK**: 35
- **Min SDK**: 24
- **Jetpack Compose**: Latest stable version

Make sure you have the following **Android SDK components** installed:
- **Platform Tools** (includes `adb`)
- **SDK Platform** for API 35 (or the one specified in your local configuration)
- **Emulator System Image** for the virtual device you plan to run

---

## Installation

1. **Clone** this repository to your local machine:
   ```bash
   git clone https://github.com/dethenrik/SnakeGameTestRepo.git
Open the project in Android Studio:

In Android Studio, click File > Open.
Select the folder where you cloned SnakeGameTestRepo.
Allow Android Studio to Sync Gradle.
Check Project Settings:

In the Project pane in Android Studio, expand Gradle Scripts.
Confirm build.gradle.kts settings match your environment (minSdk, targetSdk, etc.).
Running the App
Create or Start an Emulator:

In Android Studio, open Device Manager from the right toolbar or Tools > Device Manager.
Either create a new virtual device or start an existing one (e.g., Pixel 6, API 35).
Run/Debug:

Select Run > Run 'app' (or use the green triangle in the toolbar).
Choose your launched emulator from the device list.
Wait for the Gradle build to finish and the app to install.
Once it’s installed:

You should see the Menu screen with options to start the game, view high scores, etc.
Known Errors
Below are a few errors that have been encountered and possible fixes:

Device emulator-5554 not found

Cause: The AVD is not running or adb is not detecting it.
Solution:
Ensure your emulator is actually running (check Device Manager).
Restart adb by running adb kill-server then adb start-server in a terminal.
Close conflicting emulators and re-run your device.
Gradle Sync Failures

Cause: Missing plugins, incorrect Kotlin or Compose versions, or outdated Gradle.
Solution:
Update your Gradle version in gradle-wrapper.properties.
Make sure the Compose version matches your Kotlin version in build.gradle.kts.
In Android Studio, go to File > Settings > Build... > Gradle, confirm the correct settings.
Missing SDK or Wrong API Level

Cause: The project uses compileSdk 35, but your system might not have it installed.
Solution:
Open File > Settings > Appearance & Behavior > System Settings > Android SDK.
Install Android SDK Platform 35 and any required tools.
If you encounter an error not listed here, check Logcat in Android Studio for further insight or open an issue on this GitHub repository.
