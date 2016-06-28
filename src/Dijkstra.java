package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.io.File;
import java.io.FileNotFoundException;

public abstract class Dijkstra implements DijkstraInterface {

    private static int INF = Integer.MAX_VALUE / 2;
    Vector<GraphData> list = new Vector<GraphData>();
    Vector<Integer> ways = new Vector<Integer>();
    Vector<Integer> road = new Vector<Integer>();
    int n;// количество вершин
    int m = 0;// количество дуг
    int startVertex;// вершина из которой найдем все кратчайшие пути
    private int dist[]; // массив для хранения расстояния от стартовой вершины
    private int pred[];// массив предков, необходимых для восстановления
    // кратчайшего пути из стартовой вершины

    /* алгоритм */
    public void algorithm(int start) {
        dist[start] = 0; //кратчайшее расстояние до стартовой вершины равно 0
        for (int iter = 0; iter < n; ++iter) {
            int v = -1;
            int distV = INF;
            //выбираем вершину, кратчайшее расстояние до которого еще не найдено
            for (int i = 0; i < n; ++i) {
                if (used[i]) {
                    continue;
                }
                if (distV < dist[i]) {
                    continue;
                }
                v = i;
                distV = dist[i];
            }
            //рассматриваем все дуги, исходящие из найденной вершины
            for (int i = 0; i < adj[v].size(); ++i) {
                int u = adj[v].get(i);
                int weightU = weight[v].get(i);
                //релаксация вершины
                if (dist[v] + weightU < dist[u]) {
                    dist[u] = dist[v] + weightU;
                    pred[u] = v;
                }
            }
            //помечаем вершину v просмотренной, до нее найдено кратчайшее расстояние
            used[v] = true;
        }
    }
}

    /* считывание из файла */
    public void readFile() {
        Scanner sc;
        try {
            sc = new Scanner(new File("input.txt"));
            try {
                if (sc.hasNextInt()) { // возвращает истинну если с потока ввода
                    // можно считать целое число
                    startVertex = sc.nextInt();// считывает целое число с потока
                    // ввода и сохраняет в
                    // переменную
                } else
                    System.out.println("В файле недостаточно данных");

                for (int j = 0; j < m; j++) {
                    GraphData T = new GraphData();
                    if (sc.hasNextInt()) {
                        T.vertexFrom = sc.nextInt();
                        T.vertexFrom--;
                    }
                    if (sc.hasNextInt()) {
                        T.vertexTo = sc.nextInt();
                        T.vertexTo--;
                    }
                    if (sc.hasNextInt()) {
                        T.weight = sc.nextInt();
                    }
                    list.add(T);

                }
            } catch (Exception ex) {
                System.out.println("Файл пуст!");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Файл не существует!");
        }
    }

}