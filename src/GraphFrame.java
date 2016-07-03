package grapics;

import algoritm.Dijkstra;
import com.sun.prism.*;

import javax.swing.*;
import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.LinkedList;
import java.util.Vector;
import java.lang.Math;

/**
 * Created by rossh on 01.07.2016.
 */
public class GraphFrame extends JFrame {
    Dijkstra
    int widht = 700, height = 500;
    JButton JBtn;
    JLabel JLbl;
    JLabel SLbl;
    JFrame head = this;
    JFrame fdg;//
    Graphics g;

    List<> [] qwe = new LinkedList<Integer> [100];
    int numOfUnits;

    private Vector<GraphStruct> vertex = new Vector<GraphStruct>();
    GraphStruct temp;

    double pi = Math.PI;
    double angle;// //numOfUnits - число вершин графа
    int r = height / 4, //hDiaSize - высота окна
            hset = 300, //сдвиг центра окружности, в которую вписан граф, от левого верхнего угла по вертикали
            wset = 300;//сдвиг по горизонтали

    int
            x1, y1, x2, y2;

    public GraphFrame(int[] readyArr) {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        this.setSize(widht, height);
        this.setTitle("Алгоритм Дейкстры. Зарисовка графа");
        init(readyArr);
        angle = 2 * pi / numOfUnits;


    }

    public void init(int[] readyArr) {
        numOfUnits = readyArr[0];
        for (int i = 2; i < readyArr.length; i += 3) {
            temp = new GraphStruct(readyArr[i], readyArr[i + 1], readyArr[i + 2]);
            vertex.add(temp);
        }
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        String temp;
        for (int i = 0; i < vertex.size(); i++) {         //abc - вектор типа pair<int,int>, abc.size=число ребер
            x1 = (int) (r * Math.cos(angle * vertex.get(i).first) + hset); //abc[i].first - начальная вершина ребра
            y1 = (int) (r * Math.sin(angle * vertex.get(i).first) + wset);
            x2 = (int) (r * Math.cos(angle * vertex.get(i).second) + hset);//abc[i].second - конечная вершина ребра
            y2 = (int) (r * Math.sin(angle * vertex.get(i).second) + wset);
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawLine(x1, y1, x2, y2);
            //g2.drawRect(x1-5, y1-5, 5, 5);
            g2.fillRect(x1-2, y1-2, 7, 7);
            g2.fillRect(x2-2, y2-2, 7, 7);

            x1 += 10;
            y1 += 10;
            x2 += 10;
            y2 += 10; //рисование имен вершин
            temp = Integer.toString(vertex.get(i).first);
            g2.drawString(temp, x1, y1);
            temp = Integer.toString(vertex.get(i).second);
            g2.drawString(temp, x2, y2);
            temp = Double.toString(vertex.get(i).weight);

            g2.drawString(temp, (x1 + x2) / 2, (y1 + y2) / 2);
            //  g.drawText(x1, y1,);
            //  str = QString::number(abc[i].second);
            // paintGraph.drawText(x2, y2, 20, 20, Qt::AlignCenter | Qt::AlignVCenter , str);
        }

    }

    private class GraphStruct {
        private int first;
        private int second;
        private double weight;

        public GraphStruct(int first, int second, double weight) {
            this.first = first;
            this.second = second;
            this.weight = weight;
        }
    }
}
