package ui;

import model.*;

import java.util.Scanner;

public class SnakesAndLadders {

    public static final Scanner lector = new Scanner(System.in);
    public static final Board board = new Board();

    public static void main(String[] args) {
        SnakesAndLadders main = new SnakesAndLadders();
        System.out.println("---------------------------------------------------------");
        System.out.println("Welcome to: |!|Snakes|And|Ladders|!|");
        boolean stop = false;
        while (!stop) {
            System.out.println("---------------------------------------------------------");
            System.out.println("[1.] ||Play||");
            System.out.println("[2.] ||Exit||");
            System.out.print("Choose an option: ");
            int opt = lector.nextInt();
            lector.nextLine();
            System.out.println("---------------------------------------------------------");
            switch (opt) {
                case 1 -> {
                    main.startGame();
                    main.menu();
                }
                case 2 -> {
                    System.out.println("Thanks for playin'!");
                    System.out.println("---------------------------------------------------------");
                    stop = true;
                }
                default -> System.out.println("Invalid option!!");
            }
        }
    }


    public void menu() {
        long start = System.currentTimeMillis();
        boolean stop = false;
        while (!stop) {
            System.out.println("----------------------------------------------------------");
            System.out.print("Let's play!");
            board.showBoard();
            String msg = turn(board.manageTurn());
            System.out.println(msg);
            System.out.println("----------------------------------------------------------");
            System.out.println("[1.] ||Throw dice||");
            System.out.println("[2.] ||Watch the snakes and ladders||");
            System.out.print("Select your option: ");
            int opt = lector.nextInt();
            switch (opt) {
                case 1 -> {
                    boolean flag = move();
                    if (flag) {
                        long endTime = System.currentTimeMillis();
                        double totalTime = ((endTime - start) / 1000.0);
                        addScoreRegistry(totalTime);
                        System.out.println("||And the winner is: !!|| GG");
                        board.scoreRanking();
                        resetAll();
                        stop = true;
                    }
                }
                case 2 -> {
                    System.out.println("----------------------------------------------------------");
                    System.out.print("Snakes and ladders in the board: ");
                    board.showObstacles();
                    System.out.print("\n----------------------------------------------------------\n");
                }
                default -> {
                    System.out.println("----------------------------------------------------------");
                    System.out.print("Option unavailable");
                    System.out.print("\n----------------------------------------------------------\n");
                }
            }
        }
    }

    private void startGame() {
        System.out.print("Enter the number of rows: ");
        int rows = lector.nextInt();
        lector.nextLine();
        if (rows <= 1 || rows >= 90) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Invalid number.");
            System.out.println("----------------------------------------------------------");
            startGame();
            return;
        } else {
            board.setRows(rows);
        }
        System.out.print("Enter the number of columns: ");
        int columns = lector.nextInt();
        lector.nextLine();
        if (columns <= 1 || columns >= 90) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Invalid number.");
            System.out.println("----------------------------------------------------------");
            startGame();
            return;
        } else {
            board.setColumns(columns);
        }
        System.out.print("Enter the number of snakes: ");
        int snakes = lector.nextInt();
        System.out.print("Enter the number of ladders: ");
        int ladders = lector.nextInt();
        if (snakes < 0 || ladders < 0) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Invalid number, retype the num.");
            System.out.println("----------------------------------------------------------");
            startGame();
            return;
        } else if (snakes + ladders >= ((board.getRows() * board.getColumns() - 2) / 2)) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Cannot draw many ladders|snakes, retype again.");
            System.out.println("----------------------------------------------------------");
            startGame();
            return;
        } else {
            board.setSnakes(snakes);
            board.setLadders(ladders);
        }
        int measures = columns * rows;
        board.setMeasures(measures);
        for (int i = 0; i < measures; i++) {
            board.addPointer(i, measures);
        }
        board.addPlayers();
        for (int i = 0; i < snakes; i++) {
            board.generateSnakes();
            board.setSID(1);
        }
        for (int i = 0; i < ladders; i++) {
            board.generateLadders();
            board.setLID(1);
        }
    }

    public String turn(int turn) {
        String msg;
        if (turn == 1) {
            msg = "\nIt's * turn:";
        } else if (turn == 2) {
            msg = "\nIt's + turn:";
        } else {
            msg = "\nIt's X turn:";
        }
        return msg;
    }

    public boolean move() {
        int squares = board.throwDice();
        System.out.println("----------------------------------------------------------");
        System.out.print("Move " + squares + " squares.\n");
        System.out.print("----------------------------------------------------------\n");
        boolean flag = board.movePlayer(board.searchPlayerSquare(board.manageTurnPlayer()),
                board.squaresToMove(board.searchPlayerSquare(board.manageTurnPlayer()), squares), board.manageTurnPlayer());
        if (!flag) {
            board.passTurn();
        }
        return flag;
    }

    public void addScoreRegistry(double totalTime){
        double score = (600-totalTime)/6.0;
        board.addScoreRegistry(board.getTail(), score);
    }

    public void resetAll(){
        board.setPlayer1(null);
        board.setPlayer2(null);
        board.setPlayer3(null);
        board.setSID(-board.getSID()+65);
        board.setLID(-board.getLID()+1);
        board.setTotalCol(1);
        board.setTotalRow(1);
        board.resetAll();
    }

}
