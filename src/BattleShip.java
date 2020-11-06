import java.util.*;

public class BattleShip {

    private char[][] board = board = new char[10][10];
    Scanner kbd;
    private boolean[][] coordinateMarkedPlayer = new boolean[10][10];
    private boolean[][] coordinateMarkedComputer = new boolean[10][10];
    private int computerShips = 0;
    private int playerShips = 0;

    public BattleShip() {
        System.out.println();
        System.out.println("Deploy your ships:");
        addPlayersShips();
        System.out.println();
        displayBoard();
        System.out.println();
        addComputersShips();
        System.out.println();
        battle();
    }

    private void battle() {
        int xCoordinate, yCoordinate;

        while ((computerShips > 0) && (playerShips > 0)) {

            System.out.println();
            System.out.println("YOUR TURN");

            do {
                System.out.print("Enter X coordinate: ");
                xCoordinate = kbd.nextInt();
                System.out.print("Enter Y coordinate: ");
                yCoordinate = kbd.nextInt();

                boolean isValid = validCoordinates(xCoordinate, yCoordinate);
                while (!isValid) {
                    System.out.print("Enter X coordinate: ");
                    xCoordinate = kbd.nextInt();
                    System.out.print("Enter Y coordinate: ");
                    yCoordinate = kbd.nextInt();
                    isValid = true;
                }

            } while (checkPlayerMoveCoordinates(xCoordinate, yCoordinate));

            playersMove(xCoordinate, yCoordinate);

            System.out.println();
            System.out.println("COMPUTER'S TURN");

            do {
                xCoordinate = generateRandomNumbers();
                yCoordinate = generateRandomNumbers();
            } while (checkComputerMoveCoordinates(xCoordinate, yCoordinate));
            computersMove(xCoordinate, yCoordinate);

            System.out.println();
            displayBoard();
            displayScore(playerShips, computerShips);
        }

        if (playerShips > computerShips) {
            System.out.println("Hooray! You win the battle :)");
        } else {
            System.out.println("Computer won the game!.");
        }
    }

    //Works.
    private boolean validCoordinates(int x, int y) {
        return ((x >= 0 && x < board.length) && (y >= 0 && y < board.length));
    }


    private boolean checkPlayerMoveCoordinates(int x, int y) {
        return coordinateMarkedPlayer[x][y];
    }

    private boolean checkComputerMoveCoordinates(int x, int y) {
        return coordinateMarkedComputer[x][y];
    }

    private void computersMove(int xCoordinate, int yCoordinate) {

        if (board[xCoordinate][yCoordinate] == '1') {
            System.out.println("The Computer sunk one of your ships!");
            playerShips--;
            board[xCoordinate][yCoordinate] = 'x';

        } else if (board[xCoordinate][yCoordinate] == '2') {
            System.out.println("The Computer sunk one of its own ships");
            computerShips--;
            board[xCoordinate][yCoordinate] = '!';

        } else if (board[xCoordinate][yCoordinate] == '\u0000') {
            System.out.println("Computer missed.");
        }
        setCoordinateMarkedComputer(xCoordinate, yCoordinate);
    }


    private void playersMove(int xCoordinate, int yCoordinate) {

        if (board[xCoordinate][yCoordinate] == '2') {
            System.out.println("Boom! You sunk the ship!");
            board[xCoordinate][yCoordinate] = '!';
            computerShips--;

        } else if (board[xCoordinate][yCoordinate] == '1') {
            System.out.println("Oh no, you sunk your own ship :(");
            board[xCoordinate][yCoordinate] = 'x';
            playerShips--;

        } else if (board[xCoordinate][yCoordinate] == '\u0000') {
            System.out.println("Sorry, you missed");
            board[xCoordinate][yCoordinate] = '-';

        }
        setCoordinateMarkedPlayer(xCoordinate, yCoordinate);
    }


    private void displayScore(int p, int c) {
        System.out.println();
        System.out.println("Your ships: " + p + " |" + " Computer ships: " + c);
    }

    private void setCoordinateMarkedPlayer(int x, int y) {
        coordinateMarkedPlayer[x][y] = true;
    }

    private void setCoordinateMarkedComputer(int x, int y) {
        coordinateMarkedComputer[x][y] = true;
    }

    private void addComputersShips() {
        System.out.println("Computer is deploying ships");
        int i = 0;
        while (i < 5) {
            int xCoordinate = generateRandomNumbers();
            int yCoordinate = generateRandomNumbers();

            if (coordinatesNotUsed(xCoordinate, yCoordinate)) {
                board[xCoordinate][yCoordinate] = '2';
                computerShips++;
                System.out.println((i + 1) + ". " + "ship DEPLOYED");
                i++;
            }
        }
        System.out.println("-----------------------------");
    }

    private void addPlayersShips() {
        kbd = new Scanner(System.in);
        int xCoordinate;
        int yCoordinate;
        int i = 0;
        while (i < 5) {
            System.out.print("Enter X coordinate for your ship: ");
            xCoordinate = kbd.nextInt();
            System.out.print("Enter Y coordinate for your ship: ");
            yCoordinate = kbd.nextInt();

            if (validCoordinates(xCoordinate, yCoordinate)) {

                if (coordinatesNotUsed(xCoordinate, yCoordinate)) {
                    board[xCoordinate][yCoordinate] = '1';
                    playerShips++;
                    i++;
                }
            }
        }
    }

    //Works.
    private int generateRandomNumbers() {
        return (int) (Math.random() * 10);
    }

    private boolean coordinatesNotUsed(int x, int y) {
        return board[x][y] != '1';
    }

    //Working code.
    public void displayBoard() {
        System.out.println("       " + "0123456789");
        for (int i = 0; i < board.length; i++) {
            System.out.print("    " + i + " |");
            for (int j = 0; j < board[i].length; j++) {

                if (board[i][j] == '\u0000') {
                    System.out.print(' ');
                } else if (board[i][j] == '1') {
                    System.out.print("@");
                } else if (board[i][j] != '2') {
                    System.out.print(board[i][j]);
                } else {
                    System.out.print(' ');
                }
            }
            System.out.print("| " + i);
            System.out.println();
        }
        System.out.println("       " + "0123456789");
    }
}