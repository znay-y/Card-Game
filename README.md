# Card Game

## About Program 

This project is a simple game of cards letting the user play a variety of games with a set amount of cards. 

### Features

- CPU to play against
- Class to stores data for cards

### How to play  
- Player and dealer are dealt two cards. 
- Player actions: 
    - Hit 
    - Double 
    - Stand 
- Dealer automatically draws cards during its turn. 
- Determines the winner based on the card totals. 
- Busting (going over 21) results in a loss.

### How to Run

Compile the program:

```bash
javac TheCardGames.java
```
Run the program:
```bash
java TheCardGames
```
Select a game from the main menu.

### Limitations

- Face cards do not use traditional Blackjack values.
- Aces always have a value of 1.
- Input validation is limited.
- The dealer's behaviour is simplified.

## Version History

### Version 1.0
- Initial release.
- Added a standard 52-card deck.
- Implemented Blackjack gameplay.
- Added dealer AI.
- Added winner detection.
- Added console menu system.

### Future Improvements
- Traditional Blackjack scoring.
- Additional card games.
- Improved input validation.
- Better dealer AI.
- Card shuffling improvements.
- Graphical User Interface (GUI).
- Game statistics and score tracking.
