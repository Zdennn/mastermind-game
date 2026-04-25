# Logik (Mastermind)

A simple Java Swing logic game inspired by Mastermind. Players try to guess a hidden color combination using a limited number of rows and receive feedback for exact and partial matches.

## Features

- Java Swing desktop application
- Custom game settings before the game starts
  - Number of colors (5-8)
  - Color repetition allowed or forbidden
  - Number of slots per row (4 or 5)
  - Feedback mode: exact position or non-positional evaluation
- Interactive color selection and round evaluation
- New game restart support

## Classes

- `App.java`: Main entry point to launch the application.
- `LogikFrame.java`: Main application window, GUI components definition, and event handling.
- `LogikData.java`: Class managing the game logic, combination generation, and evaluation algorithm.
- `SettingsDialog.java`: Dialog window for configuring game parameters before starting.
- `Nastaveni.java`: Data class holding the configured game parameters.

## Prerequisites

- Java Development Kit (JDK) version 8 or higher.
- Ensure `java` and `javac` commands are available in your system's PATH.

## How to Run

1. Open a terminal in the project root.
2. Compile the source files:
```
javac -d bin src/*.java
```

3. Run the application:
```
java -cp bin App
```

## Gameplay Description

- The application generates a hidden color combination at the start of each game.
- Use the color palette at the bottom to choose a color and fill the current guess row.
- Press `Vyhodnocení kola` to evaluate the current guess.
- The game shows feedback using exact and partial match logic.
- The game ends when the combination is guessed or the maximum number of attempts is reached.

## Game Settings

The settings dialog allows you to configure:

- Number of available colors
- Whether colors may repeat in the hidden combination
- Number of slots in each guess row
- Evaluation mode for feedback

## Notes

The source is currently split into Swing UI and game logic classes. The main entry point is `src/App.java`.

The source code and GUI of this project are in Czech, as it was originally built for a local audience. This English README is provided to explain the app's logic and features to a broader audience.