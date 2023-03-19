package model;

public class Registry {

    // Attributes
    private Player root;

    public void add(Player player) {
        if (root == null) {
            root = player;
        } else {
            add(root, player);
        }
    }

    private void add(Player current, Player player) {
        if (player.getScore() < current.getScore()) {
            if (current.getLeft() == null) {
                current.setLeft(player);
            } else {
                add(current.getLeft(), player);
            }
        } else if (player.getScore() > current.getScore()) {
            if (current.getRight() == null) {
                current.setRight(player);
            } else {
                add(current.getRight(), player);
            }
        } else {
            //do nothing
        }
    }

    public void top() {
        System.out.printf("|%10s\t   |    %4s    |", "PlayerWinner", "FinalScore");
        System.out.println();
        top(root);
    }

    private void top(Player current) {
        if (current == null) {
            return;
        }
        top(current.getRight());
        System.out.format("|%10s\t   |    %.2f    |", current.getId(), current.getScore());
        System.out.println();
        top(current.getLeft());
    }

}