# Sudoku Game 4x4

## Table of Contents

- [Description](#description)
- [Features of the Application](#features-of-the-application)
- [Preview](#preview)
- [Technologies Used](#technologies-used)

## Description

Sudoku Game 4x4 is a simplified version of the classic Sudoku puzzle designed for kids. The game objective is to fill the grid with numbers 1 to 4, ensuring that each row, colum, and 2x2 subgrid contains all four numbers.

This application was created using Kotlin multiplatform and designed for deployment on two platforms, Android and Desktop.

## Features of the Application

The application mainly consists of the following three pages:

* **Homepage:** Upon opening the application, the user lands on the homepage asking them to enter their name. The homepage also contains the rules of the game.
* **Game play page:** This is the page where the game play happens.
  * The player's name is displayed on the top-left.
  * A timer is displayed on the top-right.
  * The sudoku puzzle is displayed in the center of the page.
  * Under the sudoku grid, there are four buttons, 1, 2, 3, and 4, allowing the user to enter numbers to the sudoku grid by tapping them.
  * At the bottom of the page, there are four functionalities buttons, respectively for clearing a cell, clearing all user inputs, restarting with a new puzzle, and going back to the homepage.
* **Game result page:** The game result page shows up automatically after completion of the game and displays the time taken to complete the game. The user has the option to play a new game, or to go back to the homepage.

## Preview

Mobile preview:
<img src="docs/images/mobile-preview" alt="mobile preview">

Desktop preview:
<img src="docs/images/desktop-preview" alt="desktop preview">


## Technologies Used

* **Kotlin Multiplatform** was used for writing the project
* **Jetpack Compose** was used for the UI designs