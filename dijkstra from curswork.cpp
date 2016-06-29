/**Данный код из курсовой работы по ПиАА
 */

unsigned **metro = new unsigned*[NUM_STATIONS],	// матрица смежности
			 start(0), end(0),	// номера начальной и конечной станций
			 *dist = new unsigned[NUM_STATIONS], // расстояния от заданной вершины (для Дейкстра)
			 *parent = new unsigned[NUM_STATIONS];	// хранит номер предыдущей вершины, с помощью которой мы попали в i-ую (-/-)


void dijkstra(unsigned **metro, unsigned *dist, unsigned *parent, unsigned start)
{
   // in_tree[i] == true, если для вершины i
   // уже посчитано минимальное расстояние
   bool in_tree[NUM_STATIONS] = {false};

   for(int i(0); i < NUM_STATIONS; i++)
	   dist[i] = INT_MAX; // машинная бесконечность,
      // т. е. любое расстояние будет меньше данного

   dist[start] = 0;

   unsigned cur(start); // вершина, с которой работаем

   // пока есть необработанная вершина
   while(!in_tree[cur])
   {
      in_tree[cur] = true;

      for(int i(0); i < NUM_STATIONS; i++)
      {
         // если между cur и i есть ребро
         if(metro[cur][i])
         {
            // считаем расстояние до вершины i:
            // расстояние до cur + вес ребра
            unsigned d(dist[cur] + metro[cur][i]);
            // если оно меньше, чем уже записанное
            if(d < dist[i])
            {
               dist[i] = d;   // обновляем его
               parent[i] = cur; // и "родителя"
            }
         }
      }

      // ищем нерассмотренную вершину
      // с минимальным расстоянием
      unsigned min_dist(INT_MAX);
      for(int i(0); i < NUM_STATIONS; i++)
      {
         if(!in_tree[i] && dist[i] < min_dist)
         {
            cur = i;
            min_dist = dist[i];
         }
      }
   }

   // Теперь:
   // в dist[i] минимальное расстояние от start до i
   // в parent[i] вершина, из которой лежит оптимальный путь в i
}
