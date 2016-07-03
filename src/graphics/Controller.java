package grapics;


import algoritm.Dijkstra;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Controller implements Initializable {
    @FXML
    Label outNameLabel;
    @FXML
    TextField fileField;
    @FXML
    TextArea outputTextArea;
    @FXML
    Button startButton;
    @FXML
    Button loadButton;
    @FXML
    Button nextStepButton;
    @FXML
    Pane Pane1;
    @FXML
    Circle CircleGraph;
    @FXML
    AnchorPane AnchPane;
    @FXML
    Button clearButton;
    @FXML
    TabPane tabPane1 = new TabPane();
    @FXML
    Tab tab1 = new Tab();
    @FXML
    Tab tab2 = new Tab();
    @FXML
    Button finalButton;
    int[] inpInt = null;
    int[] readyArr = null;
    int numV;
    int size = 0;
    GraphFrame frm;
    GraphStruct temp;
    Dijkstra algoritm;
    public static final double INF = Integer.MAX_VALUE / 10;
    double m1, m2;

    double x1, y1, x2, y2, l1, l2;
    private Vector<GraphStruct> vertex;

    public class GraphStruct {
        public int first;
        public int second;
        public double weight;

        public GraphStruct(int first, int second, double weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }

    public void paint_GRAPH(Circle R) {
        Label V1, V2, V3, W;
        Line edge;
        // Отрисовка графа
        for (int i = 0; i < vertex.size(); i++) {
            x1 = R.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).first) + R.getCenterX();
            y1 = R.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).first) + R.getCenterY();
            x2 = R.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).second) + R.getCenterX();
            y2 = R.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).second) + R.getCenterY();

            edge = new Line(x1, y1, x2, y2);
            edge.setStrokeWidth(7);
            edge.setStroke(Color.WHITE);
            Pane1.getChildren().addAll(edge, new Circle(x1, y1, 9, Color.GOLD), new Circle(x2, y2, 9, Color.GOLD));
            x1 += -4;
            y1 += -9;
            x2 += -4;
            y2 += -9; //рисование имен вершин
            V1 = new Label("" + vertex.get(i).first);
            V1.setLayoutX(x1);
            V1.setLayoutY(y1);
            V2 = new Label("" + vertex.get(i).second);
            V2.setLayoutX(x2);
            V2.setLayoutY(y2);
            V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(12,16,45); -fx-font-family: \"Impact\";");
            V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(12,16,45); -fx-font-family: \"Impact\";");
            Pane1.getChildren().addAll(V1, V2);
        }
        x1 = R.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(0).first) + R.getCenterX();//отрисовка 1ой вершины
        y1 = R.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(0).first) + R.getCenterY();
        V1 = new Label("" + vertex.get(0).first);
        x2 = x1 - 4;
        y2 = y1 - 9;
        V1.setLayoutX(x2);
        V1.setLayoutY(y2);
        V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(255,255,255); -fx-font-family: \"Impact\";");
        Pane1.getChildren().addAll(new Circle(x1, y1, 9, Color.RED), V1);
        V2 = new Label("0");
        x1 += 10;
        y1 += 10;
        V2.setLayoutX(x1);
        V2.setLayoutY(y1);
        V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(255,255,255); -fx-font-family: \"Impact\";");
        Pane1.getChildren().addAll(V2);

    }

    private void paint_GRAPH_RED() {
        Label V1, V2, W;
        Line edge;
        for (int i = 0; i < numV; i++) {
            x1 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).first) + CircleGraph.getCenterX();
            y1 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).first) + CircleGraph.getCenterY();
            x2 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).second) + CircleGraph.getCenterX();
            y2 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).second) + CircleGraph.getCenterY();
            if (algoritm.getDist(vertex.get(i).first) != INF) {
                l1 = x1 + 10;
                l2 = y1 + 10;
                W = new Label(algoritm.getDist(vertex.get(i).first) + "");
                W.setLayoutX(l1);
                W.setLayoutY(l2);
                W.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: blue; -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(W);
                Pane1.getChildren().addAll(new Circle(x1, y1, 9, Color.GOLD));
            } else if (algoritm.getDist(vertex.get(i).second) != INF) {
                m1 = x2 + 10;
                m2 = y2 + 10;
                W = new Label(algoritm.getDist(vertex.get(i).second) + "");
                W.setLayoutX(m1);
                W.setLayoutY(m2);
                W.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: blue; -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(W);
            }
            if ((algoritm.getDist(vertex.get(i).second) != INF) || (algoritm.getDist(vertex.get(i).first) != INF)) {
                edge = new Line(x1, y1, x2, y2);
                edge.setStrokeWidth(4);
                edge.setStroke(Color.RED);
                Pane1.getChildren().addAll(edge, new Circle(x2, y2, 9, Color.RED), new Circle(x1, y1, 9, Color.RED));
                x1 += -4;
                y1 += -9;
                x2 += -4;
                y2 += -9; //рисование имен вершин
                V1 = new Label("" + vertex.get(i).first);
                V1.setLayoutX(x1);
                V1.setLayoutY(y1);
                V2 = new Label("" + vertex.get(i).second);
                V2.setLayoutX(x2);
                V2.setLayoutY(y2);
                V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(12,16,45); -fx-font-family: \"Impact\";");
                V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(12,16,45); -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(V1, V2);
            }
        }
    }

    private void paint_Vertex() {
        Label V1;
        for (int i = 0; i < numV; i++) {
            if (algoritm.in_tree[i]) {
                x1 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * (i + 1)) + CircleGraph.getCenterX();
                y1 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * (i + 1)) + CircleGraph.getCenterY();
                Pane1.getChildren().addAll(new Circle(x1, y1, 9, Color.RED));
                x1 += -4;
                y1 += -9;
                i += 1;
                V1 = new Label("" + i);
                i--;
                V1.setLayoutX(x1);
                V1.setLayoutY(y1);
                V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(12,16,45); -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(V1);
            }
        }
    }

    public void init(int[] readyArr) {
        vertex = new Vector<GraphStruct>();
        numV = readyArr[0];
        for (int i = 2; i < readyArr.length; i += 3) {
            temp = new GraphStruct(readyArr[i], readyArr[i + 1], readyArr[i + 2]);
            vertex.add(temp);
        }
        for (int i = 0; i < (vertex.size() - 1); i++) {
            if ((vertex.get(i + 1).second == vertex.get(i).first) && (vertex.get(i).second == vertex.get(i + 1).first))
                if (vertex.get(i).weight > vertex.get(i + 1).weight) {
                    vertex.remove(i);
                    i--;
                } else {
                    vertex.remove(i + 1);
                    i--;
                }
        }
        readyArr = null;
        algoritm = new Dijkstra(numV, vertex);
    }

    @FXML
    public void finalButtonClicked(ActionEvent event) {
        System.out.println("final clicked");
        algoritm.dijkstra(vertex, temp);
        paint_GRAPH_RED();
        clearButton.setDisable(false);
        nextStepButton.setDisable(true);
    }

    @FXML
    public void clearButtonClicked(ActionEvent event) {
        System.out.println("clear clicked");
        algoritm.clear();
        algoritm = null;
        //inpInt = null;
        readyArr = null;
        vertex = null;
        numV = 0;
        size = 0;
        temp = null;
        x1 = 0;
        x2 = 0;
        y1 = 0;
        y2 = 0;
        l1 = 0;
        l2 = 0;
        m1 = 0;
        m2 = 0;
        Pane1.getChildren().clear();
        clearButton.setDisable(true);
        SingleSelectionModel<Tab> selectionModel = tabPane1.getSelectionModel();

        if (selectionModel.getSelectedIndex() == 0) {
            selectionModel.select(1);
        } else {
            selectionModel.select(0);
        }
    }

    @FXML
    public void nextStepButtonClicked(ActionEvent event) {
        System.out.println("next clicked");
        //algoritm.dijkstra(vertex,temp);
        algoritm.oneStep(vertex, temp);
        if (algoritm.isEnd()) {
            paint_GRAPH_RED();
            clearButton.setDisable(false);
            nextStepButton.setDisable(true);
        } else paint_Vertex();
    }


    @FXML
    public void loadButtonClicked(ActionEvent event) {
        System.out.println("load clicked");
        fileOk();
    }

    @FXML
    public void startButtonClicked(ActionEvent actionEvent) {
        System.out.println("start clicked");
        startButton.setDisable(true);
        int j;
        for (j = 0; j < inpInt.length; j++) if (inpInt[j] == 0) break;
        readyArr = new int[j];
        for (int i = 0; i < j; i++) readyArr[i] = inpInt[i];
        init(readyArr);
        paint_GRAPH(CircleGraph);
        nextStepButton.setDisable(false);
        finalButton.setDisable(true);
        SingleSelectionModel<Tab> selectionModel = tabPane1.getSelectionModel();

        if (selectionModel.getSelectedIndex() == 0) {
            selectionModel.select(1);
        } else {
            selectionModel.select(0);
        }
    }


    @FXML
    public void fileOk() {
        int i = 0;
        String buff, buff2 = new String();
        DataInputStream input = null;
        try (DataInputStream streambuff = new DataInputStream(new FileInputStream(fileField.getText()))) {
            size = streambuff.available();
        } catch (IOException ex2) {
            outputTextArea.setText("Wrong path");
            outputTextArea.setVisible(true);
            return;
        }
        inpInt = new int[size];
        try {
            input = new DataInputStream(new FileInputStream(fileField.getText()));

            while ((buff = input.readLine()) != null) {
                StringTokenizer stoken = new StringTokenizer(buff);
                while (stoken.hasMoreTokens()) {
                    int ibuff = Integer.parseInt(stoken.nextToken());
                    inpInt[i] = ibuff;
                    buff2 += Integer.toString(ibuff) + " ";
                    if (i % 3 == 1) buff2 += "\n";
                    //System.out.println(inpInt[i]);
                    i++;
                }
            }
        } catch (IOException ex) {

        }

        outputTextArea.setText(buff2);
        outputTextArea.setVisible(true);
        startButton.setDisable(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setDisable(true);
        finalButton.setDisable(true);
        fileField.setText("graph.txt");
        nextStepButton.setDisable(true);
        clearButton.setDisable(true);
    }
}
