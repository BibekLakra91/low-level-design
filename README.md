**Magical Arena Game**

Welcome to the Magical Arena Game, a Java implementation where players engage in thrilling turn-based combat. In this fantasy world, players are defined by their attributes: "health," "strength," and "attack," all represented as positive integers. The player with lower health gets the first attack. In case of equal health, a coin toss decides who strikes first. Combatants take turns attacking by rolling dice, with outcomes determining damage and defense. The game concludes when a player's health reaches zero.

### Classes

1. **Player**
   - Attributes:
      - `name`: Player's name
      - `health`: Player's health (positive integer)
      - `attack`: Player's attack value (positive integer)
      - `strength`: Player's strength value (positive integer)
   - Methods:
      - `getName()`: Get player's name
      - `setName(String Name)`: Set player's name
      - `getHealth()`: Get player's health
      - `setHealth(int Health)`: Set player's health
      - `getAttack()`: Get player's attack value
      - `setAttack(int Attack)`: Set player's attack value
      - `getStrength()`: Get player's strength value
      - `setStrength(int Strength)`: Set player's strength value
      - `rollDice()`: Simulate rolling a 6-sided dice
      - `takeDamage(int damage)`: Inflict damage to the player
      - `isAlive()`: Check if the player is alive
      - `show()`: Display player details with name as the title
      - `show(int roll, String turn)`: Display player details during a turn

2. **MagicalArena**
   - Methods:
      - `test(int n)`: Test the game with n sets of input files
      - `test_fight(Player p1, Player p2, PrintWriter outputWriter)`: Simulate a fight for testing
     - `test_show()`: Print details of players before the fight starts
      - `test_show(Player p, int roll, String turn, PrintWriter outputWriter)`: Print details during testing in output files
      - `result(Player p1, Player p2)`: Get the result of the game
      - `summary(Player attacker, Player defender, int damage)`: Generate a summary of the combat
      - `turn(Player p1, Player p2)`: Determine which player's turn it is
      - `simulate_fight(Player p1, Player p2)`: Simulate a fight with user prompts

3. **InvalidInputException**
   - Custom exception class for invalid input values

### Usage

1. **Player Initialization**
   - Players can be initialized through user input during runtime or via input files.
   - The `Player(String Name, int Health, int Strength, int Attack)` constructor performs input validation, throwing `InvalidInputException` for invalid values.
   - The `Player()` constructor initiates an infinite loop for invalid input, prompting users to enter valid values.

2. **MagicalArena Testing**
   - The `test(int n)` method runs the game for n sets of input files, writing results to corresponding output files (e.g., `inputZ.txt` produces output in `outputZ.txt`, where Z is an integer between 1 and n). Create more such input and output files inside `src/data` if you like
   - The `test_fight(Player p1, Player p2, int test_id)` simulates a fight during testing.
   - The `test_show(Player p, int roll, String turn, PrintWriter outputWriter)` displays details during testing.

3. **Simulation**
   - The `simulate_fight(Player p1, Player p2)` method simulates a fight with user prompts for each round.

### How to Run

1. Compile the code using a Java compiler or directly open with `IntelliJ IDEA` after extracting the code.
2. Create input and output files if you like, inside the `src/data` folder (e.g., `input4.txt`, `output4.txt`) with player details.
3. By default, there are 3 input files and 3 output files.
4. Create instances of `MagicalArena` (e.g., `arena`) in testing code as shown below. Run the program and choose to simulate a fight with prompts (`arena.test(n)`) or test the game with input files (`arena.simulate_fight(p1,p2)`).
5. View the results in the console or output files.

### Example Test Code

```java
public class Main {
    public static void main(String[] args) {
        // Testing with input files n=2
        MagicalArena arena = new MagicalArena();
        arena.test(2);
    }
}
```

```java
public class Main{
   public static void main(String[] args) {
       // Simulating fight with prompts
      System.out.println("P1");
      Player p1= new Player(); // player 1
      System.out.print("\n");
      System.out.println("P2");
      Player p2= new Player(); // player 2
      p1.show();
      p2.show();
      MagicalArena arena = new MagicalArena();
      arena.simulate_fight(p1, p2);
   }
}
```

### Error Handling
1. During testing, negative values for attributes throw errors.
2. During simulation, negative values for attributes initiate an infinite loop, breaking when positive values are entered.
3. If both players have the same health at the start, the coin toss mechanism gets invoked.
4. Empty input files or an index of an input file greater than `n` in `test(int n)` throws a `NoSuchElementException`.