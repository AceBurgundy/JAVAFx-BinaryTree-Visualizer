package org.openjfx.BinaryTree;

import javafx.scene.layout.Pane;
import javafx.scene.text.*;
import javafx.fxml.FXML;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.Cursor;
import javafx.scene.transform.Rotate;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    public Pane screen;

    @FXML
    public TextField arrayInput;

    @FXML
    public Button createButton;

    public Label traversalResult;

    final Cursor hand = Cursor.cursor("HAND");

    final Color buttonBoxShadowColor = Color.rgb(176, 153, 153);

    ArrayList<Node> nodeList = new ArrayList<>();

    ArrayList<Button> arrayOfButtons = new ArrayList<>();

    Alert alert = new Alert(Alert.AlertType.ERROR);

    private enum Direction {
        LEFT,
        RIGHT,
        NONE
    }

    private Node root;

    public class Node {

        final int value;
        Node left, right;
        final double x, y;
        final Circle nodeCircle;

        Node(double x, double y, int value, Circle nodeCircle, Text nodeCircleValue) {
            this.value = value;
            left = null;
            right = null;
            this.x = x;
            this.y = y;
            this.nodeCircle = nodeCircle;
        }

        public void createNode(int value) {

            if (value < this.value) {
                if (left == null) {
                    left = drawNode(x - 65, y + 70, value, Direction.LEFT, this.value);
                } else {
                    left.createNode(value);
                }
            } else if (value > this.value) {
                if (right == null) {
                    right = drawNode(x + 65, y + 70, value, Direction.RIGHT, this.value);
                } else {
                    right.createNode(value);
                }
            }
        }
    }

    private Node drawNode(double x, double y, int value, Direction direction, int valueOfParent) {

        Circle circle = new Circle();
        circle.setRadius(20.f);
        circle.setCenterX(x);
        circle.setCenterY(y);

        circle.setFill(Color.rgb(254, 242, 191));

        Rectangle arrow = new Rectangle();
        arrow.setWidth(5);
        arrow.setHeight(51);
        arrow.setY(y - 62.5);

        if (direction == Direction.RIGHT) {

            if (valueOfParent == root.value) {
                x += 40;
                arrow.setHeight(82.3);
                circle.setCenterX(x);
                arrow.setY(y - 77.8);
                arrow.setX(arrow.getX() + x - 57.8);
                arrow.setFill(Color.PINK);
                arrow.getTransforms().add(new Rotate(-54.8, arrow.getX() + 4, arrow.getY() + 37));
            } else {
                arrow.setX(arrow.getX() + x - 30);
                arrow.setFill(Color.PINK);
                arrow.getTransforms().add(new Rotate(-43, arrow.getX() + 4, arrow.getY() + 37));
            }
        }

        if (direction == Direction.LEFT) {

            if (valueOfParent == root.value) {
                x -= 40;
                arrow.setHeight(83);
                circle.setCenterX(x);
                arrow.setY(y - 76.7);
                arrow.setX(arrow.getX() + x + 50.7);
                arrow.setFill(Color.AQUA);
                arrow.getTransforms().add(new Rotate(53.5, arrow.getX() + 4, arrow.getY() + 37));
            } else {
                arrow.setX(arrow.getX() + x + 20);
                arrow.setFill(Color.AQUA);
                arrow.getTransforms().add(new Rotate(43, arrow.getX() + 4, arrow.getY() + 40));
            }
        }

        if (direction == Direction.NONE) {
            arrow.setOpacity(0);
        }

        Text text = new Text(String.valueOf(value));

        text.getStyleClass().add("nodeText");

        text.setX(x);
        text.setY(y);
        text.setX(text.getX() - text.getLayoutBounds().getWidth() / 1.1);
        text.setY(text.getY() + text.getLayoutBounds().getHeight() / 2.5);
        text.setFill(Color.BLACK);
        screen.getChildren().addAll(arrow, circle, text);

        return new Node(x, y, value, circle, text);
    }

    StringBuilder traversalResultString = new StringBuilder();

    public void loopThroughNodes() {
        new Thread(() -> {
            for (Node node : nodeList) {

                if (node == nodeList.get(nodeList.size() - 1)) {
                    traversalResultString.append(String.valueOf(node.value));
                } else {
                    traversalResultString.append(String.valueOf(node.value + ", "));
                }

                Platform.runLater(() -> node.nodeCircle.setFill(Color.ORANGE));
                Platform.runLater(() -> traversalResult.setText(String.valueOf(traversalResultString)));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        }).start();
    }

    public void nodeListReset() {
        for (Node node : nodeList) {
            node.nodeCircle.setFill(Color.rgb(254, 242, 191));
        }
        traversalResultString.setLength(0);
        nodeList.clear();
    }

    public void showButtonsForTraversal() {

        traversalResult = new Label();
        traversalResult.setTranslateX(40);
        traversalResult.setTranslateY(577);
        traversalResult.getStyleClass().add("traversal-result");

        Rectangle preorderBoxShadow = new Rectangle(20, 90, 185, 35);
        preorderBoxShadow.setFill(buttonBoxShadowColor);
        preorderBoxShadow.setArcHeight(5);
        preorderBoxShadow.setArcWidth(5);

        Button preorder = new Button("PREORDER");
        preorder.getStyleClass().add("button");
        preorder.setTranslateX(10);
        preorder.setTranslateY(80);
        preorder.getWidth();
        preorder.setCursor(hand);
        preorder.setOnAction(ActionEvent -> {
            nodeListReset();
            traversePreorder(root);
            loopThroughNodes();
        });
        arrayOfButtons.add(preorder);

        Rectangle inorderBoxShadow = new Rectangle(20, 160, 165, 35);
        inorderBoxShadow.setFill(buttonBoxShadowColor);
        inorderBoxShadow.setArcHeight(5);
        inorderBoxShadow.setArcWidth(5);

        Button inorder = new Button("INORDER");
        inorder.getStyleClass().add("button");
        inorder.setTranslateX(10);
        inorder.setTranslateY(150);
        inorder.setCursor(hand);
        inorder.setOnAction(ActionEvent -> {
            nodeListReset();
            traverseInorder(root);
            loopThroughNodes();
        });
        arrayOfButtons.add(inorder);

        Rectangle postorderBoxShadow = new Rectangle(20, 230, 205, 35);
        postorderBoxShadow.setFill(buttonBoxShadowColor);
        postorderBoxShadow.setArcHeight(5);
        postorderBoxShadow.setArcWidth(5);

        Button postorder = new Button("POSTORDER");
        postorder.getStyleClass().add("button");
        postorder.setTranslateX(10);
        postorder.setTranslateY(220);
        postorder.setCursor(hand);
        postorder.setOnAction(ActionEvent -> {
            nodeListReset();
            traversePostorder(root);
            loopThroughNodes();
        }
        );
        arrayOfButtons.add(postorder);

        screen.getChildren().addAll(preorderBoxShadow, preorder, inorderBoxShadow, inorder, postorderBoxShadow, postorder, traversalResult);
    }

    public void traverseInorder(Node node) {
        if (node == null) {
            return;
        }
        traverseInorder(node.left);
        nodeList.add(node);
        traverseInorder(node.right);

    }

    public void traversePreorder(Node node) {
        if (node == null) {
            return;
        }
        nodeList.add(node);
        traversePreorder(node.left);
        traversePreorder(node.right);
    }

    public void traversePostorder(Node node) {
        if (node == null) {
            return;
        }
        traversePostorder(node.left);
        traversePostorder(node.right);
        nodeList.add(node);
    }

    public void showResetTreeButton() {
        Rectangle resetBoxShadow = new Rectangle(20, 20, 125, 35);
        resetBoxShadow.setFill(buttonBoxShadowColor);
        resetBoxShadow.setArcHeight(5);
        resetBoxShadow.setArcWidth(5);

        Button reset = new Button("RESET");
        reset.getStyleClass().add("button");
        reset.setTranslateX(10);
        reset.setTranslateY(10);
        reset.setCursor(hand);
        arrayOfButtons.add(reset);

        screen.getChildren().addAll(resetBoxShadow, reset);

        reset.setOnAction(ActionEvent -> {
            screen.getChildren().clear();
            nodeListReset();

            arrayInput.setText("");
            root = null;
        });
    }

    public void runAlertBasicSettings() {
        alert.setContentText("Only accepts numbers, spaces and commas as input\n\nEx: 5, 4, 6, 7");
        alert.showAndWait();
        screen.getChildren().clear();
        arrayInput.setText("");
    }

    public void createTree(ActionEvent event) {

        showResetTreeButton();
        showButtonsForTraversal();

        arrayOfButtons.forEach(button -> {

            button.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET,
                    e -> {
                        button.setStyle("-fx-text-fill: orange;");
                    });

            button.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET,
                    e -> {
                        button.setStyle("-fx-text-fill: white;");
                    });
        }
        );

        ArrayList<Integer> list = new ArrayList<>();

        HashSet<Integer> added = new HashSet<>();

        // takes input, splits it, and uses advance for loop to iterate and to check if values that are not
        // 0-9 or ',' or ' ' or any value that may trigger NumberFormatException is found else, adds that number to list arrayList.
        for (String index : arrayInput.getText().split(",")) {

            try {

                if (arrayInput.getText().matches("[^0-9, ]")) {
                    alert.setHeaderText("Invalid Character '" + index + "' found");
                    runAlertBasicSettings();
                    list.clear();
                    return;
                } else {
                    list.add(Integer.valueOf(index.trim()));
                }

            } catch (NumberFormatException e) {
                alert.setHeaderText("Triggered Number Format Exception");
                runAlertBasicSettings();
                list.clear();
                return;
            }
        }

        //creates the root node
        root = drawNode(screen.getLayoutBounds().getWidth() / 2, 40.0, list.get(0), Direction.NONE, list.get(0));

        // loops through remaining elements and checks if each element is in the added arrayList
        // if yes, then it skips that number else adds them to added arrayList and creates a new node
        new Thread(() -> {
            for (int index : list) {

                if (added.contains(index)) {
                    continue;
                }

                added.add(index);
                Platform.runLater(() -> root.createNode(index));

                try {
                    Thread.sleep(500);
                } catch (InterruptedException v) {
                    System.out.println(v);
                }
            }
        }).start();
    }
}
