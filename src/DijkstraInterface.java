package src;

/**
 * интерфейс алгоритма Дейкстры
 *
 */
public interface DijkstraInterface {
    void readFile(); // считывание данных из файла

    void algorithm();// реализация алгоритма

    void printFile();// вывод данных в файл

    void printWay(); // процедура восстановления кратчайшего пути по массиву
    // предком
}