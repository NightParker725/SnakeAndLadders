package model;

import java.util.Random;

public class Board {

    public static final Random random = new Random();

    // Attributes
    private int rows;
    private int columns;
    private int measures;
    private int snakes;
    private int ladders;

    // Links
    private Player player1;
    private Player player2;
    private Player player3;
    private Pointer head;
    private Pointer tail;

    private int turn = 1;
    private int totalCol = 1;
    private int totalRow = 1;
    private int SID = 65;
    private int LID =66;
    private Registry registry;

    // Constructor
    public Board() {
        this.registry = new Registry();
    }

    // Getters and setters
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getMeasures() {
        return measures;
    }

    public void setMeasures(int measures) {
        this.measures = measures;
    }

    public void setSnakes(int snakes) {
        this.snakes = snakes;
    }

    public int getLadders() {
        return ladders;
    }

    public void setLadders(int ladders) {
        this.ladders = ladders;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer3() {
        return player3;
    }

    public void setPlayer3(Player player3) {
        this.player3 = player3;
    }

    public Pointer getHead() {
        return head;
    }

    public void setHead(Pointer head) {
        this.head = head;
    }

    public Pointer getTail() {
        return tail;
    }

    public void setTail(Pointer tail) {
        this.tail = tail;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTotalCol() {
        return totalCol;
    }

    public void setTotalCol(int totalCol) {
        this.totalCol = totalCol;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID += SID;
    }

    public int getLID() {
        return LID;
    }

    public void setLID(int LID) {
        this.LID += LID;
    }

    public Registry getScoreRegistry() {
        return registry;
    }

    public void setScoreRegistry(Registry registry) {
        this.registry = registry;
    }
    public void addPointer(int i, int size) {
        Pointer pointer = new Pointer(i + 1);
        if (head == null) {
            head = pointer;
            tail = pointer;
        }
        else {
            tail.setNext(pointer);
            pointer.setPrevious(tail);
            tail = pointer;
            if (tail.getNum() == size) {
                addDimensions(head, getColumns());
            }
        }
    }

    private void addDimensions(Pointer current, int columns) {
        if (current == null) {
            return;
        }
        if (current.getPrevious() != null) {
            if (current.getPrevious().getColumn() == columns) {
                totalCol = 1;
                totalRow++;
            }
        }
        current.setColumn(totalCol++);
        current.setRow(totalRow);
        addDimensions(current.getNext(), columns);
    }

    public void addPlayers() {
        addPlayers(head);
    }

    private void addPlayers(Pointer current) {
        if (current == null) {
            return;
        }
        if (head.getPlayer1() == null && head.getPlayer2() == null && head.getPlayer3() == null) {
            Player player1 = new Player("*", 0);
            Player player2 = new Player("+", 0);
            Player player3 = new Player("X", 0);
            setPlayer1(player1);
            setPlayer2(player2);
            setPlayer3(player3);
            head.setPlayer1(player1);
            head.setPlayer2(player2);
            head.setPlayer3(player3);
        } else {
            current.setPlayer1(new Player("", 0));
            current.setPlayer2(new Player("", 0));
            current.setPlayer3(new Player("", 0));
        }
        addPlayers(current.getNext());
    }

    public void generateSnakes() {
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        if (end != start) {
            searchNodes(start, end, head, head, "||SNAKES||");
        } else {
            generateSnakes();
        }
    }
    private void generateSnakes(Pointer current1, Pointer current2) {
        current1.setStatus(String.valueOf((char) SID));
        current2.setStatus(String.valueOf((char) SID));
        current1.setSnake_Ladder(current2);
    }

    public void generateLadders() {
        int start = random.nextInt(getMeasures() - 2) + 2;
        int end = random.nextInt(getMeasures() - 2) + 2;
        if (end != start) {
            searchNodes(start, end, head, head, "||LADDERS||");
        } else {
            generateLadders();
        }
    }
    private void generateLadders(Pointer current1, Pointer current2) {
        current1.setStatus(String.valueOf((char)LID));
        current2.setStatus(String.valueOf((char)LID));
        current1.setSnake_Ladder(current2);
    }

    private void searchNodes(int start, int end, Pointer current1, Pointer current2, String type) {
        if (current1.getNum() == start && current2.getNum() == end) {
            confirmStatus(current1, current2, type);
        } else if (current1.getNum() == start && current2.getNum() != end) {
            searchNodes(start, end, current1, current2.getNext(), type);
        } else if (current1.getNum() != start && current2.getNum() == end) {
            searchNodes(start, end, current1.getNext(), current2, type);
        } else {
            searchNodes(start, end, current1.getNext(), current2.getNext(), type);
        }
    }

    private void confirmStatus(Pointer current1, Pointer current2, String type) {
        if (current1.getStatus().equals("") && current2.getStatus().equals("")) {
            compareRows(current1, current2, type);
        } else {
            if (type.equals("||SNAKES||")) {
                generateSnakes();
            } else if (type.equals("||LADDERS||")) {
                generateLadders();
            }
        }
    }

    private void compareRows(Pointer current1, Pointer current2, String type) {
        if (type.equals("||SNAKES||")) {
            if (current1.getRow() == current2.getRow()) {
                generateSnakes();
            }
            else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() > current2.getRow()) {
                    generateSnakes(current1, current2);
                } else {
                    generateSnakes(current2, current1);
                }
            }
        }
        else if (type.equals("||LADDERS||")) {
            if (current1.getRow() == current2.getRow()) {
                generateLadders();
            }
            else if (current1.getRow() != current2.getRow()) {
                if (current1.getRow() < current2.getRow()) {
                    generateLadders(current1, current2);
                } else {
                    generateLadders(current2, current1);
                }
            }
        }
    }

    public void showBoard() {
        showBoard(tail, getRows(), getColumns());
    }

    private void showBoard(Pointer current, int rowCount, int columnCount) {
        if (current != null && rowCount > 0) {
            if (current.getNum() % (columnCount * 2) == 0) {
                System.out.println();
                current = showBoardRow(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            } else {
                current = showBoardRowInvested(current, columnCount);
                showBoard(current, rowCount - 1, columnCount);
            }
        }
    }

    private Pointer showBoardRow(Pointer current, int columnCount) {
        Pointer lastNode = null;
        if (current != null && columnCount > 0) {
            String players = showPlayers(current);
            String msg = String.format("%7s", "[" + current.getNum() + players + "] ");
            System.out.print(msg);
            lastNode = showBoardRow(current.getPrevious(), columnCount - 1);
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private Pointer showBoardRowInvested(Pointer current, int columnCount) {
        Pointer lastNode = null;
        if (current != null && columnCount > 0) {
            lastNode = showBoardRowInvested(current.getPrevious(), columnCount - 1);
            String players = showPlayers(current);
            String message = String.format("%7s", "[" + current.getNum() + players + "] ");
            System.out.print(message);
        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private String showPlayers(Pointer current) {
        String players = "";
        if (current.getPlayer1() != null) {
            players = players + current.getPlayer1().getId();
        }
        if (current.getPlayer2() != null) {
            players = players + current.getPlayer2().getId();
        }
        if (current.getPlayer3() != null) {
            players = players + current.getPlayer3().getId();
        }
        return players;
    }

    public void showObstacles() {
        showObstacles(tail, getRows(), getColumns());
    }

    private void showObstacles(Pointer current, int rowCount, int columnCount) {
        if (current != null && rowCount > 0) {
            if (current.getNum() % (columnCount * 2) == 0) {
                System.out.println();
                current = showS_LRow(current, columnCount);
                showObstacles(current, rowCount - 1, columnCount);
            } else {
                current = showS_LRowInvested(current, columnCount);
                showObstacles(current, rowCount - 1, columnCount);
            }
        }
    }

    private Pointer showS_LRow(Pointer current, int columnCount) {
        Pointer lastNode = null;
        if (current != null && columnCount > 0) {
            String message = String.format("%7s", "[" + current.getStatus() + "] ");
            System.out.print(message);
            lastNode = showS_LRow(current.getPrevious(), columnCount - 1);
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    private Pointer showS_LRowInvested(Pointer current, int columnCount) {
        Pointer lastNode = null;
        if (current != null && columnCount > 0) {
            lastNode = showS_LRowInvested(current.getPrevious(), columnCount - 1);
            String message = String.format("%7s", "[" + current.getStatus() + "] ");
            System.out.print(message);

        } else {
            System.out.println();
        }
        if (lastNode == null) {
            lastNode = current;
        }
        return lastNode;
    }

    public int manageTurn() {
        int turn;
        if (getTurn() == 1) {
            turn = 1;
        } else if (getTurn() == 2) {
            turn = 2;
        } else {
            turn = 3;
        }
        return turn;
    }

    public Player manageTurnPlayer() {
        Player player;
        if (getTurn() == 1) {
            player = player1;
        } else if (getTurn() == 2) {
            player = player2;
        } else {
            player = player3;
        }
        return player;
    }

    public int throwDice() {
        return random.nextInt(6) + 1;
    }

    public boolean movePlayer(Pointer current, Pointer nextPointer, Player player) {
        if (nextPointer == tail) {
            if (current.getPlayer1() == player) {
                tail.setPlayer1(player);
                current.setPlayer1(null);
            } else if (current.getPlayer2() == player) {
                tail.setPlayer2(player);
                current.setPlayer2(null);
            } else if (current.getPlayer3() == player) {
                tail.setPlayer3(player);
                current.setPlayer3(null);
            }
            return true;
        }
        else {
            if (current.getPlayer1() == player) {
                if (nextPointer.getSnake_Ladder() != null) {
                    nextPointer.getSnake_Ladder().setPlayer1(player);
                }
                else {
                    nextPointer.setPlayer1(player);
                }
                current.setPlayer1(null);
            } else if (current.getPlayer2() == player) {
                if (nextPointer.getSnake_Ladder() != null) {
                    nextPointer.getSnake_Ladder().setPlayer2(player);
                } else {
                    nextPointer.setPlayer2(player);
                }
                current.setPlayer2(null);
            } else if (current.getPlayer3() == player) {
                if (nextPointer.getSnake_Ladder() != null) {
                    nextPointer.getSnake_Ladder().setPlayer3(player);
                } else {
                    nextPointer.setPlayer3(player);
                }
                current.setPlayer3(null);
            }
            return false;
        }
    }

    public Pointer searchPlayerSquare(Player goal) {
        return searchPlayerSquare(head, goal);
    }

    private Pointer searchPlayerSquare(Pointer current, Player goal) {
        if (current == null) {
            return null;
        }
        if (current.getPlayer1() == goal) {
            return current;
        } else if (current.getPlayer2() == goal) {
            return current;
        } else if (current.getPlayer3() == goal) {
            return current;
        }
        return searchPlayerSquare(current.getNext(), goal);
    }

    public Pointer squaresToMove(Pointer current, int squares) {
        if (current == tail) {
            return tail;
        } else if (squares == 0) {
            return current;
        }
        return squaresToMove(current.getNext(), squares - 1);
    }

    public void passTurn(){
        if (getTurn() == 1) {
            setTurn(2);
        } else if (getTurn() == 2) {
            setTurn(3);
        } else {
            setTurn(1);
        }
    }

    public void addScoreRegistry(Pointer pointer, double score) {
        if (pointer.getPlayer1().getId().equals("*")) {
            Player winner = new Player("*", score);
            registry.add(winner);
        } else if (pointer.getPlayer2().getId().equals("+")) {
            Player winner = new Player("+", score);
            registry.add(winner);
        } else if (pointer.getPlayer3().getId().equals("X")) {
            Player winner = new Player("X", score);
            registry.add(winner);
        } else {
            // do nothing
        }
    }

    public void scoreRanking(){
        System.out.println("\t\t\tRANK's:");
        registry.top();
        System.out.print("------------------------------------------------\n");
    }

    public void resetAll() {
        resetAll(getHead());
    }

    private void resetAll(Pointer current) {
        if (current == null) {
            return;
        }
        if (current.getNext() == null && current.getPrevious() == null) {
            setHead(null);
            setTail(null);
            return;
        }
        if (current.getPrevious() == null) {
            current.getNext().setPrevious(null);
            setHead(current.getNext());
            resetAll(getHead());
        }
    }

}