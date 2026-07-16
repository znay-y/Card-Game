# Casino Card Games

## About

Casino Card Games is a Java console application that allows players to choose from multiple card games using a single menu system. The program uses a shared deck of cards and player profile across all games.

## Games

### Blackjack
Play a simplified version of Blackjack against a computer-controlled dealer.

**Features**
- Player vs Dealer gameplay
- Hit
- Stand
- Double Down
- Automatic dealer turns
- Win, lose, and draw detection

### Ride the Bus
Play a simplified version of Ride the Bus by predicting whether the next card will be higher or lower.

**Features**
- Higher or Lower guessing
- Chip-based gameplay
- Multiple rounds
- Shared player profile

---

## Program Features

- Main menu for selecting games
- Shared player profile
- Shared card and deck system
- Computer-controlled opponents
- Console-based interface
- Multiple playable games

---

## Program Structure

```
Casino/
│── Main.java          # Main menu and program entry point
│── Blackjack.java     # Blackjack game logic
│── RideTheBus.java    # Ride the Bus game logic
│── Card.java          # Card class
│── Player.java        # Player profile and chip management
```

---

## How to Play

### Blackjack

1. Select **Blackjack** from the main menu.
2. Both the player and dealer receive two cards.
3. Choose one of the following actions:
   - Hit
   - Stand
   - Double Down
4. The dealer automatically plays their turn.
5. The winner is determined by comparing hand values.

### Ride the Bus

1. Select **Ride the Bus** from the main menu.
2. A card is dealt.
3. Guess whether the next card will be:
   - Higher
   - Lower
4. Continue guessing correctly to progress.
5. An incorrect guess costs chips.

---

## How to Run

Compile the project:

```bash
javac *.java
```

Run the program:

```bash
java Main
```

Select a game from the main menu to begin playing.

---

## Limitations

### Blackjack
- Face cards do not use traditional Blackjack values.
- Aces always count as 1.
- Dealer AI is simplified.

### General
- Console interface only.
- Limited input validation.
- No save/load functionality.
- No multiplayer support.

---

# Version History

## Version 1.0

### Added
- Initial project release
- Standard 52-card deck
- Blackjack game
- Dealer AI
- Winner detection
- Console menu system

---

## Version 1.1

### Added
- Separated each class into its own Java file
- Ride the Bus game
- Shared player profile
- Chip tracking system
- Main menu supporting multiple games

---

# Future Improvements

- Traditional Blackjack card values
- Smarter dealer AI
- Improved input validation
- Card shuffling animations
- Additional casino games
- Save and load player profiles
- Statistics tracking
- Betting system
- Graphical User Interface (GUI)
- Sound effects
- Multiplayer support