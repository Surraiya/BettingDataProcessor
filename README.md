# Java-Betting-Program
This Java project processes betting data for multiple players and matches.

## Table of Contents

- [Introduction](#introduction)
- [Usage](#usage)
- [Example Input](#example-input)
- [Example Output](#example-output)
- [How to Run](#how-to-run)
- [Technologies Used](#technologies-used)

## Introduction

All operations in this project are instantaneous. Players can perform three types of operations: Bet, Deposit, and Withdraw. Matches have two sides (A and B) and can result in A winning, B winning, or a draw. The program processes data from input files, calculates player balances, win rates, and casino balances, and writes the results to an output file.

## Usage

1. Clone the repository.
2. Place the input files (`player_data.txt` and `match_data.txt`) in the project's resource folder.
3. Run the Java program.
4. View the results in the generated `result.txt` file.

## Example Input

### Player Data Input
```plaintext
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,DEPOSIT,,4000,
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,BET,abae2255-4255-4304-8589-737cdff61640,500,A
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4,WITHDRAW,,200,
```
player_data.txt

#### Explanation:

- First value is player ID – A random UUID.

- Second value is player operation – One of 3 operations: DEPOSIT, BET, WITHDRAW.

- Third value (if exist) is a match Id – A random UUID.

- Fourth value is the coin number player use for that operation.

- Fifth value (if exist) is the side of the match the player places the bet on.

### Match Data Input
```plaintext
abae2255-4255-4304-8589-737cdff61640,1.45,0.75,A
```
match_data.txt

#### Explanation:

- First value is the match Id – A random UUID.

- Second value is the return rate for A side.

- Third value is the return rate for B side.

- Fourth value is the result of the match.

## Example Output
In the results.txt file there are 3 expected result groups separated by empty line between each group. Results.txt file should be written into the same directory as your Main class:

#### Legitimate player: 
List of all legitimate player IDs followed with their final balance and their betting win rate (Win rate is calculated by number of won game / number of placed bets)
```plaintext
Legitimate Players:
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0.80
```
- player ID, balance number, win rate are separated by space.

- win rate is big decimal number with 2 decimal characters.

- The list is ordered by player ID.

- Player is legitimate if they did not attempt any illegal action.

#### Illegitimate Players
List of all illegitimate players represented by their first illegal operation

```plaintext
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 BET abae2255-4255-4304-8589-737cdff61640 5000 A
```
- The list is ordered by player ID.

- In case of WITHDRAW operation, empty values should be presented as “null” 
```plaintext
4925ac98-833b-454b-9342-13ed3dfd3ccf WITHDRAW null 8093 null
```

#### Casino Balance
Coin changes in casino host balance.

- Only players BET operations impact casino balance, e.g Player1 won 500 coins while Player2 lose 2000 coins from betting, so host balance will get extra 1500 coins.

- Illegal actions don’t impact casino balance.

The final result.txt file should look like this:
```plaintext
Legitimate Players:
163f23ed-e9a9-4e54-a5b1-4e1fc86f12f4 4321 0.80

Illegitimate Players:
4925ac98-833b-454b-9342-13ed3dfd3ccf BET abae2255-4255-4304-8589-737cdff61640 5000 A

Casino Balance:
1500
```

## How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Maven

### Build and Run
1. **Clone the Repository:**

   ```bash
   git clone https://github.com/Surraiya/BettingDataProcessor.git
    ```
2. **Navigate to the Project Directory:**
```bash
cd BettingDataProcessor
```

3. **Build the Project:**
Execute the following Maven command to clean, compile, and package the project:
```bash
mvn clean install
```

4. **Run the Application:**
```bash
java -jar target/BettingDataProcessor-1.0-SNAPSHOT.jar
```


## Technologies Used
- [Java](https://www.java.com/)
- [Maven](https://maven.apache.org/)
- [Lombok](https://projectlombok.org/) - for boilerplate code reduction
- [TestNG](https://testng.org/doc/) - for testing
