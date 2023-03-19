package model;

public class Player {

    // Attributes
    String id;
    double score;

    // Links
    private Player right;
    private Player left;

    // Constructor
    public Player(String name, double score) {
        this.id = name;
        this.score = score;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Player getRight() {
        return right;
    }

    public void setRight(Player right) {
        this.right = right;
    }

    public Player getLeft() {
        return left;
    }

    public void setLeft(Player left) {
        this.left = left;
    }

}