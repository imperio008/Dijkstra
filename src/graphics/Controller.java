package graphics;


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

/**
 * Класс Controller наследованный от Initializable
 * В данном классе находится GUI
 * Визуализация выполнена на Javafx
 *
 * @author Хафизов Ильнур
 */

public class Controller implements Initializable {
    @FXML                   //все переменные с пометками @FXML являются элементами GUI
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
    int[] inpInt = null;        //массив для считывания из файла
    int[] readyArr = null;      //массив для обработки данных
    int numV;                   //количество вершин
    int size = 0;               //размер файла
    GraphStruct temp;           //вспомогательная переменная
    Dijkstra algoritm;          //переменная в которой будет обрабатываться алгоритм
    public static final double INF = Integer.MAX_VALUE / 10;
    double x1, y1, x2, y2, l1, l2, m1, m2;  //переменные для координат
    private Vector<GraphStruct> vertex;     //массив рёбер

    /**
     * Класс GraphStruct
     * Хранит в себе все имеющиеся рёбра
     */
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

    /**
     * Метод реагирующий на нажатие кнопки "Финальный шаг"
     * В данном методе вызывается алгоритм Дейкстры, который проводится с данной точки и работает до конца(до завершения)
     * После выполнения алгоритма прорисовывается граф с минимальными путями (paint_GRAPH_RED)
     * Так же выводится список смежности в поле outputTextArea
     *
     * @param event событие нажатия кнопки
     */
    @FXML
    public void finalButtonClicked(ActionEvent event) {
        algoritm.dijkstra(vertex);
        paint_GRAPH_RED();
        clearButton.setDisable(false);
        nextStepButton.setDisable(true);
        finalButton.setDisable(true);
        printToArea();
    }

    /**
     * Метод реагирующий на нажатие кнопки "Очистить граф"
     * В этом методе обнуляются все переменные, освобождается память, также очищается панель с рисовкой графа
     * Так же после нажатия программа переключается между вкладками
     *
     * @param event событие нажатия кнопки
     */
    @FXML
    public void clearButtonClicked(ActionEvent event) {
        algoritm.clear();
        algoritm = null;
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
        outputTextArea.setText("");
    }

    /**
     * Метод реагирующий на нажатие кнопки "Следующий шаг"
     * В этом методе запускается один шаг алгоритма Дейкстры
     * Также прорисовывается вершина которая была обработана
     * Если алгоритм заканчивается после нажатия кнопки, вырисовывается граф с минимальными путями(paint_GRAPH_RED)
     *
     * @param event событие нажатия кнопки
     */
    @FXML
    public void nextStepButtonClicked(ActionEvent event) {
        algoritm.oneStep(vertex);
        if (algoritm.isEnd()) {
            paint_GRAPH_RED();
            clearButton.setDisable(false);
            nextStepButton.setDisable(true);
            finalButton.setDisable(true);
            printToArea();
        } else paint_Vertex();
    }

    /**
     * Метод реагирующий на нажатие кнопки "Загрузить"
     * Вызывает метод fileOk() который выполняет считывание с файла
     * Название файла вводится в соответствующее поле
     *
     * @param event событие нажатия кнопки
     */
    @FXML
    public void loadButtonClicked(ActionEvent event) {
        fileOk();
    }

    /**
     * Метод реагирующий на нажатие кнопки "Нарисовать граф"
     * Метод выполняет обработку поступивших из файла данный и рисует начальный граф (paint_GRAPH)
     * После нажания на кнопку выполняется переключения между вкладками программы
     *
     * @param event событие нажатия кнопки
     */
    @FXML
    public void startButtonClicked(ActionEvent event) {
        startButton.setDisable(true);
        init(inpInt);
        paint_GRAPH();
        nextStepButton.setDisable(false);
        finalButton.setDisable(false);
        SingleSelectionModel<Tab> selectionModel = tabPane1.getSelectionModel();

        if (selectionModel.getSelectedIndex() == 0) {
            selectionModel.select(1);
        } else {
            selectionModel.select(0);
        }
    }

    /**
     * Метод рисующий граф введенный из файла
     * Также помечает начальную вершину рисую под ней вес
     */
    @FXML
    public void paint_GRAPH() {
        Label V1, V2, V3;
        Line edge;
        // Отрисовка графа
        for (int i = 0; i < vertex.size(); i++) {
            x1 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).first) + CircleGraph.getCenterX();
            y1 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).first) + CircleGraph.getCenterY();
            x2 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(i).second) + CircleGraph.getCenterX();
            y2 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(i).second) + CircleGraph.getCenterY();
            V3 = new Label("" + vertex.get(i).weight);
            V3.setLayoutX((x1 + x2) / 2 + 10);
            V3.setLayoutY((y1 + y2) / 2 + 10);
            edge = new Line(x1, y1, x2, y2);
            edge.setStrokeWidth(4);
            edge.setStroke(Color.BLUE);
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
            V3.setStyle("-fx-font-weight: bold; -fx-font-size: 13px; -fx-text-fill: rgb(157,255,254); -fx-font-family: \"Impact\";");
            Pane1.getChildren().addAll(V1, V2, V3);
        }
        x1 = CircleGraph.getRadius() * Math.cos(2 * Math.PI / numV * vertex.get(0).first) + CircleGraph.getCenterX();//отрисовка 1ой вершины
        y1 = CircleGraph.getRadius() * Math.sin(2 * Math.PI / numV * vertex.get(0).first) + CircleGraph.getCenterY();
        V2 = new Label("0");
        x1 += 10;
        y1 += 10;
        V2.setLayoutX(x1);
        V2.setLayoutY(y1);
        V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: rgb(255,255,255); -fx-font-family: \"Impact\";");
        Pane1.getChildren().addAll(V2);
    }

    /**
     * Метод рисующий минимальные пути на графах(красными линиями)
     * Также под каждой вершиной рисуется расстояние от начальной вершины
     */
    @FXML
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
                W.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: rgb(255,242,212); -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(new Circle(x1, y1, 9, Color.GOLD), W);
            } else if (algoritm.getDist(vertex.get(i).second) != INF) {
                m1 = x2 + 10;
                m2 = y2 + 10;
                W = new Label(algoritm.getDist(vertex.get(i).second) + "");
                W.setLayoutX(m1);
                W.setLayoutY(m2);
                W.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: rgb(255,242,212); -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(W);
            }
            if ((algoritm.getDist(vertex.get(i).second) != INF) && (algoritm.getDist(vertex.get(i).first) != INF)) {
                edge = new Line(x1, y1, x2, y2);
                edge.setStrokeWidth(4);
                edge.setStroke(Color.RED);
                Pane1.getChildren().addAll(edge, new Circle(x2, y2, 9, Color.DARKVIOLET), new Circle(x1, y1, 9, Color.DARKVIOLET));
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
                V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: \"Impact\";");
                V2.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(V1, V2);
            }
        }
    }

    /**
     * Метод рисует вершину, которая была обработана за последний шаг
     */
    @FXML
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
                V1.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white; -fx-font-family: \"Impact\";");
                Pane1.getChildren().addAll(V1);
            }
        }
    }

    /**
     * Метод обрабатывающий входные данные из файла
     *
     * @param inpInt массив, который был считан из файла
     */
    public void init(int[] inpInt) {
        vertex = new Vector<GraphStruct>();
        int j;
        for (j = 0; j < inpInt.length; j++) if (inpInt[j] == 0) break;
        readyArr = new int[j];
        for (int i = 0; i < j; i++) readyArr[i] = inpInt[i];
        numV = readyArr[0];
        for (int i = 1; i < readyArr.length; i += 3) {
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

    /**
     * Метод, выводяший списки смежности в поле, находящейся в первой вкладке программы
     */
    public void printToArea() {
        String buff = new String();
        ArrayList[] adj = new ArrayList[numV];
        for (int i = 0; i < numV; ++i) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < numV; i++) {
            adj[vertex.get(i).first - 1].add(vertex.get(i).second);
            adj[vertex.get(i).second - 1].add(vertex.get(i).first);
        }
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < adj[i].size(); j++) {
                int n = i + 1;
                Integer a = (Integer) adj[i].get(j);
                if (n == a) {
                    adj[i].remove(j);
                }
            }
        }
        for (int i = 0; i < numV; i++) {
            buff = buff + (i + 1) + ": ";
            for (int j = 0; j < adj[i].size(); j++) {
                buff = buff + " -> " + adj[i].get(j);
            }
            buff += "\n";
        }
        outputTextArea.setText(buff);
    }

    /**
     * Метод, считывающий информацию из файла
     */
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
                    if (i % 3 == 0) buff2 += "\n";
                    i++;
                }
            }
        } catch (IOException ex) {

        }

        outputTextArea.setText(buff2);
        outputTextArea.setVisible(true);
        startButton.setDisable(false);
    }

    /**
     * Инициализация программы перед запуском
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setDisable(true);
        finalButton.setDisable(true);
        fileField.setText("graph.txt");
        nextStepButton.setDisable(true);
        clearButton.setDisable(true);
    }
}
