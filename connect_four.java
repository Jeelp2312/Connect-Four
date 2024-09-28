// Connect Four Project
// Program is a text-based connect four game.

// Importing the scanner class from the java.util package
import java.util.Scanner;

// Importing the arrays class from the java.util package
import java.util.Arrays;


// Defines the Main class
class Main {
  // Defines the main method
  public static void main(String[] args) {
    // Defines the scanner object
    Scanner In = new Scanner(System.in);

    // Defines integer variables
    int turnNum = 1, validCol;

    // Defines string variables
    String p1, p2;

    // Defines boolean variables
    boolean gameOver = false, quitProgram = false, gameDraw = false;
    
    // Introduction and rules of the game methods are called upon
    intro();
    gameRules();

    // Asks the user(s) to input the names of the two players, makes sure that the names are not blank
    do {
      // Asks the user for their name
      System.out.print("\nPlayer 1, please enter your name: ");
      p1 = In.nextLine();

      // If the name is blank, the user is asked to input a name again
      if (p1.equals("")) {
        System.out.println("Please enter a valid name.");
      }
    } while (p1.length() == 0);
    do {
      // Asks the user for their name
      System.out.print("\nPlayer 2, please enter your name: ");
      p2 = In.nextLine();

      // If the name is blank, the user is asked to input a name again
      if (p2.equals("")) {
        System.out.println("Please enter a valid name.");
      }
    } while (p2.length() == 0);

    // Defines the array that will be used to store the game board values
    String[][] gameBoard = initBoard();

    // A while loop that will run until the user(s) wish to quit the game
    while (!quitProgram) {
      // The dislayBoard method is called upon to display the game board to the user(s)
      displayBoard(gameBoard);

      // The getInt method is called upon to assign a valid column number to the variable validCol
      validCol = getInt(gameBoard, turnNum, p1, p2);

      // The placePiece method is called upon to place a piece on the game board and updates the board accordingly
      gameBoard = placePiece(gameBoard, validCol, turnNum);

      // The determineWinner method is called upon to check if the game is over by a win or a tie situation
      gameOver = determineWinner(gameBoard, p1, p2);
      gameDraw = determineDraw(gameBoard);
      turnNum++;  // Increments the turn number by 1, next player's turn

      // If the game is over by a tie, then game is reset for another game
      if (gameDraw) {
        // Final game board is displayed
        displayBoard(gameBoard);

        // Resets the game board and turn number to defaut values
        System.out.println("Here's a new board to play Connect Four.");
        gameBoard = initBoard();
        turnNum = 1;
        gameDraw = false;
      }
      
      // If the game is over by a win, then the user will be asked if they want to play again or quit the program
      if (gameOver) {
        // The displayBoard method is called upon to display the final game board to the user(s)
        displayBoard(gameBoard);

        // The replayGame method is called upon to ask if the user(s) want to play again, assigns value to quitProgram
        quitProgram = replayGame();

        // If the user wishes to play again, then the game board is reset and the turn number is reset to 1
        if (!quitProgram) {
          // Variables are reset to defuat values, calls initBoard method to reset board
          gameBoard = initBoard();
          turnNum = 1;
          gameOver = false;
        }
      }
    }

    In.close();  // Closes the scanner object
  }


  // Introduction method
  public static void intro() {
    // Defines string variable
    String seperation = "- - - - - - - - - - - - - - - - -";
    
    // Prints the introduction message(s)
    System.out.println("Welcome to the Connect Four Game!");
    System.out.println(seperation);
  }


  // The rules for the connect four game are presented to the user
  public static void gameRules() {
    System.out.println("Here are the Rules of the Game:");
    System.out.println("1. The game is played on a 6 (rows) x 7 (columns) grid.");
    System.out.println("2. Each player takes turns dropping their piece into a column.");
    System.out.println("3. The objective is to connect four of your pieces in a row, either vertically, horizontally, or diagonally.");
    System.out.println("4. The first player to connect four of their pieces wins the game.");
    System.out.println("5. If all the spaces on the grid are filled and no player has connected four of their pieces, the game is a draw.");
  }


  // Method initializes the game board and returns it with a 2-D array
  public static String[][] initBoard() {
    // Defines integer variables
    int rows = 6, cols = 7;
    
    // Defines the string 2D array representing the game board
    String[][] board = new String[rows][cols];

    // Initializing the game board
    for (int i = 0; i < rows; i++) {  // Loops through the rows of the board
      for (int j = 0; j < cols; j++) {  // Loops through the columns of the board
        board[i][j] = ".";  // Assigns each space a '.'
      }
    }

    return board;  // Returns the initialized game board
  }


  // Method displays the game board method to the user(s)
  public static void displayBoard(String[][] board) {
    // Defines string variable
    String seperation = "- - - - - - - - - - - - - - - - -";
    
    // Prints the game board title and column number numbers on the top of the board
    System.out.println(seperation);
    System.out.println("        Current Board      ");
    System.out.println("  1   2   3   4   5   6   7");
    
    // Loops through the rows and columns of the game board to display the board
    for (int row = 0; row < board.length; row++) {  // Loops through the rows of the board
      System.out.print("| ");  // Prints the left side barrier
      for (int col = 0; col < board[row].length; col++) {  // Loops through the columns of the board
        System.out.print(board[row][col] + " | ");  // Prints the middle barrier between columns
      }
      System.out.println();  // Moves to the next line after each row
    }
    System.out.println(" --- --- --- --- --- --- ---");  // Prints the bottom barrier
    System.out.println(seperation);
  }


  // Method recieves input on user selected column to place a piece and returns the column number
  public static int getInt(String[][] board, int turnNum, String p1, String p2) {
    // Sets up the scanner object
    Scanner In = new Scanner(System.in);

    // Defines string variable
    String input;

    // Defines integer variable
    int col = 0;

    // Defines boolean variables
    boolean validMove = false, isInteger;

    // If the turn number is odd, then it is player 1's turn
    if (turnNum % 2 == 1) {
      System.out.println(p1 + ", please enter a column to place your piece in.");
      System.out.println("Your pieces are the X's.\n");
    }
    // If the turn number is even, then it is player 2's turn
    else if (turnNum % 2 == 0) {
      System.out.println(p2 + ", please enter a column to place your piece in.");
      System.out.println("Your pieces are the O's.\n");
    }
    
    // While loop runs until a valid column number has been selected
    while (!validMove) {
      // Asks the user to input a column number
      System.out.print("Select a column number (1-7): ");
      input = In.nextLine();
      isInteger = true;  // Assumes the input is an integer

      // Determines if the input given is an integer value
      if (input != "") {  // Checks if input contains any value besides being blank
        for (int i = 0; i < input.length(); i++) {
          if (input.charAt(i) != '0' && input.charAt(i) != '1' && input.charAt(i) != '2' && input.charAt(i) != '3' && input.charAt(i) != '4' && input.charAt(i) != '5' && input.charAt(i) != '6' && input.charAt(i) != '7' && input.charAt(i) != '8' && input.charAt(i) != '9') {
            isInteger = false;  // If the input is not an integer, then the variable is set to false
          }
        }
      }
      // If the column entered was blank, then isInteger is set to false
      else {
        isInteger = false;  // Value is set to false as input is not an integer
      }

      // If the input is an integer, then the value is checked for being a valid column number 
      if (isInteger) {
        col = Integer.parseInt(input);  // Converts the string input to an integer value

        // Checks if the column number is outside the valid column number range
        if (col > 7 || col < 1) {
          System.out.println(col + " is not a valid column.");  // Asks the user for a valid column number
        }
        // If the column number is valid, checks if the column is full or not
        else {
          // Calls the validMove method to check if the column is full or not
          validMove = validMove(board, col);
        }
      }
      // If the input is not an integer, then the user is asked to input a valid integer column number
      else {
        System.out.println(input + " is not an integer.");
      }
    }

    return col;  // Returns the valid column number
  }


  // Method determines whether a column number selected by the user is valid
  public static boolean validMove(String[][] board, int column) {
    // Defines integer variable
    int col = column - 1;

    // Defines boolean variable
    boolean validMove;

    // Checks if at least one space in the column is empty by checking the first row of the column
    if (board[0][col].equals(".")) {
      validMove = true;  // Sets the validMove variable to true
    }
    // If the column is full, then the user is told to select a different column
    else {
      validMove = false;  // Sets the validMove variable to false
      System.out.println("Please enter a valid column number.");
    }

    return validMove;  // Returns a boolean value of whether the column is a valid selection or not
  }


  // Method which updates the board by placing a piece in the user's selected column
  public static String[][] placePiece(String[][] board, int column, int turnNum) {
    // Defines string variable
    String piece = ".";

    // Defines integer variable
    int col = column - 1;

    // Determines which player is placing their piece by checking the turn number
    if (turnNum % 2 == 1) {
      piece = "X";
    }
    else if (turnNum % 2 == 0) {
      piece = "O";
    }

    // Places a piece in the lowest empty space in the given column
    for (int row = board.length - 1; row >= 0; row--) {  // Loops through the rows of the board from the bottom up
      if (board[row][col].equals(".")) {  // Checks if their is an space marked by a '.'
        // Places the piece in the lowest empty space in the given column and breaks out of the loop
        board[row][col] = piece;
        break;
      }
    }

    return board;  // Returns the updated game board
  }


  // Method checks if the game is over by checking if the board is full, resulting in a tie/draw
  public static boolean determineDraw(String[][] board) {
    // Defines string variable
    String seperation = "- - - - - - - - - - - - - - - - -";
    
    // Defines boolean variables
    boolean gameOver = false, spaceEmpty = false;

    // Loops through the top row to check if there is any empty spots left
    for (int col = 0; col < board[0].length; col++) {  // Loops through the columns of the board
      // Checks if there is an empty space in the top row of the board
      if (board[0][col].equals(".")) {  
        spaceEmpty = true;
      }
    }
    // Checks if there is no empty space, then game is resulted in a tie or a draw
    if (spaceEmpty == false) {
      // Game draw message is displayed and gameOver variable is set to true
      System.out.println(seperation);
      System.out.println("The Game has Resulted in a Draw!");
      gameOver = true;
    }

    return gameOver;  // Returns a boolean value of whether the game is over or not
  }

  
  // Method determines if there is a game winner and returns if the game is over or not
  public static boolean determineWinner(String[][] board, String p1, String p2) {
    // Defines string variable
    String seperation = "- - - - - - - - - - - - - - - - -";
    
    // Defines boolean variables
    boolean gameOver = false;
    
    // Checks for a horizontal win by seeing the X's or O's in a row horizontally
    for (int row = 0; row < board.length; row++) {  // Loops through all the rows of the board
      for (int col = 0; col < board[row].length - 3; col++) {  // Loops through the left four columns of the board
        // Checks if the three spaces to the right of the selected row/column piece are all X's
        if (board[row][col].equals("X") && board[row][col + 1].equals("X") && board[row][col + 2].equals("X") && board[row][col + 3].equals("X")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p1 + " has Won the Game!");
          gameOver = true;
        }
        // Checks if the three spaces to the right of the selected row/column piece are all O's
        else if (board[row][col].equals("O") && board[row][col + 1].equals("O") && board[row][col + 2].equals("O") && board[row][col + 3].equals("O")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p2 + " has Won the Game!");
          gameOver = true;
        }
      }
    }

    // Checks for a vertical win by seeing the X's or O's in a row vertically
    for (int col = 0; col < board[0].length; col++) {  // Loops through all the columns of the board
      for (int row = 0; row < board.length - 3; row++) {  // Loops through the top three rows of the board
        // Checks if the three spaces below the selected row/column piece are all X's
        if (board[row][col].equals("X") && board[row + 1][col].equals("X") && board[row + 2][col].equals("X") && board[row + 3][col].equals("X")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p1 + " has Won the Game!");
          gameOver = true;
        }
        // Checks if the three spaces below the selected row/column piece are all O's
        else if (board[row][col].equals("O") && board[row + 1][col].equals("O") && board[row + 2][col].equals("O") && board[row + 3][col].equals("O")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p2 + " has Won the Game!");
          gameOver = true;
        }
      }
    }
    
    // Check for diagonal win by counting the number of X's or O's in a row down and to the left
    for (int row = 0; row < board.length - 3; row++) {  // Loops through the top three rows of the board
      for (int col = 3; col < board[row].length; col++) {  // Loops through the right four columns of the board
        // Checks if the four spaces diagonally down and to the left from the selected row/column piece are all X's
        if (board[row][col].equals("X") && board[row + 1][col - 1].equals("X") && board[row + 2][col - 2].equals("X") && board[row + 3][col - 3].equals("X")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p1 + " has Won the Game!");
          gameOver = true;
        }
        // Checks if the four spaces diagonally down and to the left from the selected row/column piece are all O's
        else if (board[row][col].equals("O") && board[row + 1][col - 1].equals("O") && board[row + 2][col - 2].equals("O") && board[row + 3][col - 3].equals("O")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p2 + " has Won the Game!");
          gameOver = true;
        }
      }
    }

    // Check for diagonal win by counting the number of X's or O's in a row down and to the right
    for (int row = 0; row < board.length - 3; row++) {  // Loops through the top three rows of the board
      for (int col = 0; col < board[row].length - 3; col++) {  // Loops through the left four columns of the board
        // Checks if the four spaces diagonally down and to the right from the selected row/column piece are all X's
        if (board[row][col].equals("X") && board[row + 1][col + 1].equals("X") && board[row + 2][col + 2].equals("X") && board[row + 3][col + 3].equals("X")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p1 + " has Won the Game!");
          gameOver = true;
        }
        // Checks if the four spaces diagonally down and to the right from the selected row/column piece are all O's
        else if (board[row][col].equals("O") && board[row + 1][col + 1].equals("O") && board[row + 2][col + 2].equals("O") && board[row + 3][col + 3].equals("O")) {
          // Game over message is displayed and gameOver variable is set to true
          System.out.println(seperation);
          System.out.println("Connect Four! " + p2 + " has Won the Game!");
          gameOver = true;
        }
      }
    }
    
    return gameOver;  // Returns a boolean value of whether the game is over or not
  }


  // Method asks the user if they want to replay the game and returns if the game is to be played again or not
  public static boolean replayGame() {
    // Sets up the scanner object
    Scanner In = new Scanner(System.in);
    
    // Defines string variables
    String response, seperation = "- - - - - - - - - - - - - - - - -";
    
    // Defines boolean variable
    boolean quitGame = false;
    
    // Do-while loop asks the user if they want to replay the game or not
    do {
      // Asks the user if they want to replay the game
      System.out.println("Would you like to play Connect Four again?");;
      System.out.print("Response (Yes/No): ");
      response = In.nextLine();

      // Checks if the user's response is valid, either "yes" or "no"
      if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
        System.out.println("Please enter either \"yes\" or \"no\".\n");
      }
    } while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));  // Runs until the user's response is valid

    // If the user wants to quit the game, then the quitGame variable is set to true
    if (response.equalsIgnoreCase("no")) {
      // Program ending message is displayed
      quitGame = true;
      System.out.println(seperation);
      System.out.println("Thank you for playing Connect Four!");
    }
    // If the game is to be played again, then the quitGame variable is set to false
    else {
      // Replay game message is displayed
      quitGame = false;
      System.out.println(seperation);
      System.out.println("Here's a new board to play Connect Four.");
    }

    return quitGame;  // Returns a boolean value of whether the game is to be played again or not
  }
}
