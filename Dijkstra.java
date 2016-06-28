import javax.swing.*;

public class Dijkstra extends JFrame{
    private boolean[] check;
    private int[] dist;
    private int array[][];
    private JPanel generalPanel;
    private JPanel graphPanel;
    private JPanel generationPanel;
    private JPanel buttonPanel;
    private JButton downloadGraphButton;
    private JButton startButton;
    private JProgressBar algoritmProgressBar;
    private JButton nextButton;
    private JTextField txtFileInfoField;
    private JLabel aboutTxtInfoLabel;

    public Dijkstra()
    {
        super("Sosi pisos nooborot");
        setSize(640, 480);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public int[] work(int[][] a, int start) {
        check = new boolean[a.length];
        dist = new int[a.length];
        dist[start] = 0;
        int k = start;
        for (int i = 0; i < dist.length; i++) {
            if (i != start) {
                dist[i] = 1000000000;
                check[i] = false;
            }
        }

        for (int i = 0; i < dist.length; i++) {
            if (a[start][i] != 0) {
                dist[i] = a[start][i];
            }
        }
        check[start] = true;
        for (int i = 0; i < a.length - 1; i++) {
            start = find();
            for (int j = 0; j < a.length; j++) {
                if ((a[start][j] != 0) && ((dist[start] + a[start][j]) < dist[j])) {
                    dist[j] = dist[start] + a[start][j];
                }
            }
            check[start] = true;
        }
        for (int i = 0; i < a.length; i++) {
            if (dist[i] == 1000000000) {
                dist[i] = 0;
            }

        }

        return dist;
    }

    public int[][] transfier() {

        array = new int[][]{{4}, {1, 5}, {1, 2, 4}, {2}, {1, 4}};
        int a[][] = new int[array.length][array.length];
        int matrix[][] = new int[array.length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                matrix[i][array[i][j] - 1] = 1;
            }
        }
        for (int i = 0; i < array.length; i++) {

            a[i] = work(matrix, i);
        }
        print(matrix);
        System.out.println(" Матрица наименьших расстяний: ");
        print(a);

        return a;
    }

    private int find() {
        int min = 1000000000;
        int k = 0;
        for (int i = 0; i < dist.length; i++) {
            if ((dist[i] < min) && (check[i] == false)) {
                min = dist[i];
                k = i;
            }
        }

        return k;
    }

    public void print(int a[][]) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                System.out.print(a[i][j] + "    ");

            }
            System.out.println();
        }

    }


}
