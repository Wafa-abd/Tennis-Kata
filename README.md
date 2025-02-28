# Tennis Kata Project

This project is a Java implementation of the rules of tennis. It allows you to simulate a tennis match between two players based on a given sequence of points.

## Prerequisites

- **Java 11**: Ensure that you have Java 11 installed on your machine.

## Tennis Rules

The program follows the standard rules of tennis, including the following concepts:

- **Game**: A game is won by the first player to score at least 4 points with a margin of 2 points over their opponent.
- **Set**: A set is won by the first player to win at least 6 games with a margin of 2 games over their opponent.
- **Advantage**: If both players reach 40-40 (deuce), the game enters "advantage." The player who scores the next point gains the advantage. If they score again, they win the game. Otherwise, the score returns to deuce.
- **Match**: To win a match, a player must win 2 sets.

You can read more about tennis scoring [here](https://en.wikipedia.org/wiki/Tennis_scoring_system).

## How the Program Works

The program operates through a main class that takes the following inputs:

1. **player1**: The name of the first player.
2. **player2**: The name of the second player.
3. **sequence**: A string representing the sequence of points scored by each player. For example, "ABABBA" means player A scores a point, then player B, then A, and so on.

## Conclusion
This project provides a simple yet effective way to simulate a tennis match based on a sequence of points. It adheres to the standard rules of tennis and includes proper exception handling for robust error management.
Feel free to explore the code, run the simulations, and extend the functionality as needed. Enjoy your tennis match simulations!
