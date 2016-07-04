package algoritm;


import grapics.Controller;

import java.util.*;

/**
 * Класс Dijkstra
 * В данном классе реализован алгоритм Дейкстры
 *
 * @author Ахметшина Резеда
 */
public class Dijkstra {
    public int numV;                                            //количество вершин
    public double matrix[][];                                   //матрица смежности
    public double dist[];                                       //массив с растояниями
    public int parent[];                                        //массив родителей
    public static final double INF = Integer.MAX_VALUE / 10;    //бесконечность
    public boolean in_tree[];                                   //массив хранящий состояние вершин: обработано или нет
    public static int cur = 0;                                  //вершина, с которой работаем, всегда начинаем с первой
    public int min_dist;                           //минимальное расстояние на текущий момент. Необходимо для пошаговой реализации

    /**
     * Конструктор
     *
     * @param numV   колличество вершин
     * @param vertex вектор с ребрами
     */
    public Dijkstra(int numV, Vector<Controller.GraphStruct> vertex) {
        this.numV = numV;
        dist = new double[numV];
        parent = new int[numV];
        in_tree = new boolean[numV];
        init(vertex);
    }

    /**
     * Метод обрабатывающий входной вектор и преобразующий его в матрицу смежности
     * Так же инициализирует все массивы в классе
     *
     * @param vertex
     */
    public void init(Vector<Controller.GraphStruct> vertex) {
        matrix = new double[numV][numV];
        for (int i = 0; i < numV; i++) in_tree[i] = false;
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) {
                if (i == j) matrix[i][j] = 0;
                else matrix[i][j] = INF;
            }
        }
        for (int i = 0; i < vertex.size(); i++) {
            if (vertex.get(i).weight < matrix[vertex.get(i).first - 1][vertex.get(i).second - 1]) {
                matrix[vertex.get(i).first - 1][vertex.get(i).second - 1] = vertex.get(i).weight;
                matrix[vertex.get(i).second - 1][vertex.get(i).first - 1] = vertex.get(i).weight;
            }
        }
        for (int i = 0; i < numV; i++)
            dist[i] = INF; // машинная бесконечность,
        // т. е. любое расстояние будет меньше данного
        dist[0] = 0;
    }

    /**
     * Метод выполняющий алгоритм с текущей точки до конца
     *
     * @param vertex вектор с ребрами
     */
    public void dijkstra(Vector<Controller.GraphStruct> vertex) {
        // in_tree[i] == true, если для вершины i
        // уже посчитано минимальное расстояние
        // пока есть необработанная вершина
        while (!in_tree[cur]) {
            in_tree[cur] = true;

            for (int i = 0; i < numV; i++) {
                // если между cur и i есть ребро
                if (matrix[cur][i] != INF) {
                    // считаем расстояние до вершины i:
                    // расстояние до cur + вес ребра
                    double d = dist[cur] + matrix[cur][i];
                    // если оно меньше, чем уже записанное
                    if (d < dist[i]) {
                        dist[i] = d;   // обновляем его
                        parent[i] = cur; // и "родителя"
                    }
                }
            }

            // ищем нерассмотренную вершину
            // с минимальным расстоянием
            //unsigned min_dist(INT_MAX);
            int min_dist = (int) INF;
            for (int i = 0; i < numV; i++) {
                if (!in_tree[i] && (dist[i] < min_dist)) {
                    cur = i;
                    min_dist = (int) dist[i];
                }
            }
        }


        // Теперь:
        // в dist[i] минимальное расстояние от start до i
        // в parent[i] вершина, из которой лежит оптимальный путь в i

        initVertex(vertex);
    }

    /**
     * Метод выполняющий один шаг алгоритма с текущего состояния
     *
     * @param vertex вектор с ребрами
     */
    public void oneStep(Vector<Controller.GraphStruct> vertex) {
        in_tree[cur] = true;

        for (int i = 0; i < numV; i++) {
            // если между cur и i есть ребро
            if (matrix[cur][i] != INF) {
                // считаем расстояние до вершины i:
                // расстояние до cur + вес ребра
                double d = dist[cur] + matrix[cur][i];
                // если оно меньше, чем уже записанное
                if (d < dist[i]) {
                    dist[i] = d;   // обновляем его
                    parent[i] = cur; // и "родителя"
                }
            }
        }

        // ищем нерассмотренную вершину
        // с минимальным расстоянием
        //unsigned min_dist(INT_MAX);
        min_dist = (int) INF;
        for (int i = 0; i < numV; i++) {
            if (!in_tree[i] && dist[i] < min_dist) {
                cur = i;
                min_dist = (int) dist[i];
            }
        }
        // Теперь:
        // в dist[i] минимальное расстояние от start до i
        // в parent[i] вершина, из которой лежит оптимальный путь в i
        if (!in_tree[cur]) initVertex(vertex);
    }

    /**
     * Метод записывающий в первые numV ячеек ребра с минимальнымми путями
     *
     * @param vertex вектор с ребрами
     */
    public void initVertex(Vector<Controller.GraphStruct> vertex) {
        for (int counter = 0; counter < numV; counter++) {
            vertex.get(counter).first = counter + 1;
            vertex.get(counter).second = parent[counter] + 1;
            vertex.get(counter).weight = dist[counter];
        }
    }

    /**
     * Метод проверяющий закончился ли алгоритм на данный момент
     *
     * @return true если закончен, иначе false
     */
    public boolean isEnd() {
        return in_tree[cur];
    }

    /**
     * Метод возвращает расстояние от начальной вершины до i-ой
     *
     * @param i номер вершины
     * @return расстояние до i-ой вершины
     */
    public double getDist(int i) {
        return dist[i - 1];
    }

    /**
     * Метод, очищающий все данные в классе
     */
    public void clear() {
        numV = 0;
        matrix = null;
        dist = null;
        parent = null;
        in_tree = null;
        cur = 0;
    }
}
