/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

/**
 *
 * @author Asus
 */
public class pruebaGrafo {
       public static void main(String[] argumentos) throws Exception{
           DiGrafo<Integer> digrafo = new DiGrafo<>();
           Grafo<Integer> grafo = new Grafo<>();
           DiGrafoPesado<Integer> digrafopesado = new DiGrafoPesado<>();
           GrafoPesado<Integer> grafopesado = new GrafoPesado<>();
           digrafo.insertarVertice(0);
           digrafo.insertarVertice(1);
           digrafo.insertarVertice(2);
           digrafo.insertarVertice(3);
           digrafo.insertarVertice(4);
           digrafo.insertarArista(0, 1);
           digrafo.insertarArista(0, 2 );
           digrafo.insertarArista(0, 3);
           digrafo.insertarArista(2, 4);
           digrafo.insertarArista(3, 4);
           digrafo.insertarArista(4, 1);
           
           
           grafo.insertarVertice(0);
           grafo.insertarVertice(1);
           grafo.insertarVertice(2);
           grafo.insertarVertice(3);
           grafo.insertarVertice(4);
           grafo.insertarArista(0, 1);
           grafo.insertarArista(0, 2 );
           grafo.insertarArista(0, 3);
           grafo.insertarArista(2, 4);
           grafo.insertarArista(3, 4);
           grafo.insertarArista(4, 1);
           
           digrafopesado.insertarVertice(0);
           digrafopesado.insertarVertice(1);
           digrafopesado.insertarVertice(2);
           digrafopesado.insertarVertice(3);
           digrafopesado.insertarVertice(4);
           digrafopesado.insertarArista(0, 1,10);
           digrafopesado.insertarArista(0, 2,8 );
           digrafopesado.insertarArista(0, 3,19);
           digrafopesado.insertarArista(2, 4,100);
           digrafopesado.insertarArista(3, 4,14);
           digrafopesado.insertarArista(4, 1,18);
           
           grafopesado.insertarVertice(0);
           grafopesado.insertarVertice(1);
           grafopesado.insertarVertice(2);
           grafopesado.insertarVertice(3);
           grafopesado.insertarVertice(4);
           grafopesado.insertarArista(0, 1,10);
           grafopesado.insertarArista(0, 2,8 );
           grafopesado.insertarArista(0, 3,19);
           grafopesado.insertarArista(2, 4,100);
           grafopesado.insertarArista(3, 4,14);
           grafopesado.insertarArista(4, 1,18);
           
           System.out.println("Grafo dirigido :" + digrafo);
           
           System.out.println("Grafo no dirigido :" + grafo);
           
           System.out.println("Grafo dirigido pesado :" + digrafo);
           
           System.out.println("Grafo no dirigido pesado :" + grafo);
           
           System.out.println("1. En un grafo dirigido implementar un algoritmo para encontrar si el grafo tiene ciclos :");
           System.out.println("Resultado " + digrafo.existeCicloEnGrafo());
           System.out.println("2. En un grafo dirigido implementar un algoritmo para encontrar si es débilmente conexo:");
           System.out.println("Resultado " + digrafo.esDebilmenteConexo());
           System.out.println("3. En un grafo no dirigido implementar un algoritmo para encontrar si el grafo tiene ciclo :");
           System.out.println("Resultado " + grafo.existeCicloEnGrafo());
           System.out.println("4. En un grafo no dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo :");
           System.out.println("Resultado " + grafo.existeCicloEnGrafo());
           System.out.println("5. En un grafo dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo :");
           
           System.out.println("6. En un grafo dirigido implementar el algoritmo de Warshal, que luego muestre entre que vértices hay camino :");
           System.out.println("");
           System.out.println(" 7. En un grafo dirigido usando la implementación del algoritmo de Floyd-Warsall encontrar los caminos de costo mínimo entre un vértice a y el resto. Mostrar los costos y cuáles son los caminos:");
           System.out.println(digrafopesado.FloydWarshall(0));
           System.out.println("8. En un grafo dirigido pesado implementar el algoritmo de Dijsktra que muestre cual es el camino de costo mínimo entre un vértice a y b y cual el costo:");
           System.out.println(digrafopesado.costoMinimoDesdeAhaciaB(0, 1));
           System.out.println("9. En un grafo dirigido pesado implementar el algoritmo de Dijsktra que muestre con que vértices hay caminos de costo mínimo partiendo desde un vértice a y con qué costo.");
           System.out.println(digrafopesado.caminosConCostoMinimoDesdeA(0));
           System.out.println("10. En un grafo no dirigido pesado implementar el algoritmo de Kruskal que muestre cual es el grafo encontrado por el algoritmo");
           System.out.println(grafopesado.Kruskal());
           System.out.println("11. En un grafo no dirigido pesado implementar el algoritmo de Prim que muestre cual es el grafo encontrado por el algoritmo");
           System.out.println(grafopesado.Prim());
           System.out.println("12. En un grafo dirigido implementar al algoritmo de ordenamiento topológico. Debe mostrar cual es el orden de los vértices según este algoritmo.");
           System.out.println("");
           System.out.println("13. En un grafo dirigido pesado implementar el algoritmo de Ford-Fulkerson");
           System.out.println("");
           System.out.println("14. En un grafo dirigido solo usando como base la lógica de un recorrido (dfs o bfs) encuentre desde que vértices se puede llegar a un vértice a.");
           System.out.println(digrafo.verticesHaciaUnVerticeA(0));
           
           
           

           
       }
}
