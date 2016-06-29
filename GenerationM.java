package generation;

import java.io.*;
import java.util.*;

public class GenerationM
{
	public static void main(String[] args) throws IOException 
	{
		Scanner scn = new Scanner(System.in);
		File f = new File("graph.txt");
		PrintWriter outf = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		
		System.out.print("Введите пожалуйста число вершин в графе: ");
		int N = scn.nextInt();
		while(N < 2)
		{
			System.out.println("Число вершин графа задано неверно!!! Попробуйте еще раз!");
			N = scn.nextInt();
		}
		
		boolean matr[][] = new boolean [N][N];	// матрица смежности
		boolean reltop[] = new boolean [N];	// связанные вершины
		
		System.out.print("Введите пожалуйста процент ребер в графе: ");
		int k = scn.nextInt();
		while(k == 0 || k < 100/N || k > 100)
		{
			System.out.println("Процент ребер в графе задан неверно!!! Попробуйте еще раз!");
			k = scn.nextInt();
		}
		
		long q = (long)(k*N*(N-1)/200), curAdges;
		
		reltop[(int)(Math.random()*N)] = true;
		for(curAdges = 0; curAdges < N-1; curAdges++)
		{
			int i = (int)(Math.random()*N),
				j = (int)(Math.random()*N);
			
			while(!reltop[i]) 
			{
				i++;
				i %= N;
			}
			
			if(reltop[i])
			{	
				while(reltop[j]) 
				{
					j++;
					j %= N;
				}
				
				reltop[j] = true;
				matr[i][j] = true;
			}
		}
		
		
		for(;curAdges < q; curAdges++)
		{
			int i = (int)(Math.random()*N),
				j = (int)(Math.random()*N);
			
			while(matr[i][j] || matr[j][i] || (i == j))
			{
				int step = (int)(Math.random()*4);
				switch(step)
				{
				case 0: i++; i %= N; break;
				case 1: j++; j %= N; break;
				case 2: i--; i = (i < 0) ? N-1 : i; break;
				case 3: j--; j = (j < 0) ? N-1 : j; break;
				}				
			}
			
			matr[i][j] = true;
		}
		
		outf.printf("%d %d", curAdges, N);
		outf.println();
		outf.flush();
		
		
		for(int i = 0; i < N; i++)
			for(int j = 0; j < N; j++)
				if(matr[i][j])
				{
					int e = 1+(int)(Math.random()*100);
					outf.printf("%d %d %d", i, j, e);
					outf.println();
					outf.flush();
				}
		System.out.println("Граф успешно создан!");
		scn.close();
	}
}