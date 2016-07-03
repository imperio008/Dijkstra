package algoritm;


import grapics.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.*;

public class Dijkstra {
    public int numV;
    public double matrix[][];
    public double dist[];
    public int parent[];
    public static final double INF = Integer.MAX_VALUE / 10;
    public boolean in_tree[];
    public static int cur = 0; // вершина, с которой работаем
    public int min_dist;

    public Dijkstra(int numV, Vector<Controller.GraphStruct> vertex) {
        this.numV = numV;
        dist = new double[numV];
        parent = new int[numV];
        in_tree = new boolean[numV];
        init(vertex);
    }

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
        for (int i = 0; i < numV; i++) {
            for (int j = 0; j < numV; j++) System.out.print(matrix[i][j] + "  ");
            System.out.println();
        }
        for (int i = 0; i < numV; i++)
            dist[i] = INF; // машинная бесконечность,
        // т. е. любое расстояние будет меньше данного
        dist[0] = 0;
    }

    public void dijkstra(Vector<Controller.GraphStruct> vertex, Controller.GraphStruct temp) {
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
                if (!in_tree[i] && dist[i] < min_dist) {
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
    public void oneStep(Vector<Controller.GraphStruct> vertex, Controller.GraphStruct temp){
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
        if(!in_tree[cur]) initVertex(vertex);
    }

    public void initVertex(Vector<Controller.GraphStruct> vertex) {
        for (int counter = 0; counter < numV; counter++) {
            vertex.get(counter).first = counter + 1;
            vertex.get(counter).second = parent[counter] + 1;
            vertex.get(counter).weight = dist[counter];
        }
    }
    public boolean isEnd() {
        return in_tree[cur];
    }

    public double getDist(int i) {
        return dist[i - 1];
    }

    public void clear() {
        numV = 0;
        matrix = null;
        dist = null;
        parent = null;
        in_tree = null;
        cur = 0;
    }
}
