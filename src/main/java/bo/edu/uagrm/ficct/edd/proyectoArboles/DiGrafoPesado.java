/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class DiGrafoPesado< T extends Comparable<T>> extends GrafoPesado<T> {
    public DiGrafoPesado(){
        super();
    }
    @Override
    public void insertarArista(T verticeOrigen , T verticeDestino,double costo)throws ExcepcionOrdenInvalido{
        if(! this.existeVertice(verticeOrigen)){
          throw new ExcepcionOrdenInvalido("No existe vertice de origen");
        }
        if(! this.existeVertice(verticeDestino)){
          throw new ExcepcionOrdenInvalido("No existe vertice de destino");
        }
        if( this.existeAdyacencia(verticeOrigen,verticeDestino)){
          throw new ExcepcionOrdenInvalido("El vertice ya existe");
        }
        int posicionDeVerticeDeOrigen = this.posicionDeVertice(verticeOrigen);
        int posicionDeVerticeDeDestino = this.posicionDeVertice(verticeDestino);
        AdyacenteConPeso adyacente = new AdyacenteConPeso(posicionDeVerticeDeDestino,costo);
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listasDeAdyacencia.get(posicionDeVerticeDeOrigen);
        adyacentesDelOrigen.add(adyacente);
        Collections.sort(adyacentesDelOrigen);
        
    }
    
    	//algoritmo base
    public String Dijsktra(T verticeA , T verticeB ){
        List<Boolean> marcados = new LinkedList<>();
        List<Double> costos =new LinkedList<>();
        List<Integer> predecesores = new LinkedList<>();
        int indiceActual = this.listaDeVertices.indexOf(verticeA);
        marcados = inicializaMarcados();
        costos = inicializarCostos(indiceActual);
        predecesores = inicializarPredecesores();
        while (indiceActual != -1) { 
            marcarVertice(marcados, indiceActual);
            List<AdyacenteConPeso> listaDeAdyacencias = this.listasDeAdyacencia.get(indiceActual);
            for (int i = 0; i < listaDeAdyacencias.size(); i++) {
                int indiceA = listaDeAdyacencias.get(i).getIndiceVertice();
                double costoA = costos.get(indiceA);
                double costoV = costos.get(indiceActual);
                double costoAV = listaDeAdyacencias.get(i).getCosto();
                if(costoA > costoV +  costoAV){
                    costos.set(indiceA,costoV +  costoAV);
                    predecesores.set(indiceA, indiceActual);
                }
            }
            indiceActual = buscarIndiceNoMarcadoConCostoMenor(marcados,costos);
        }
        
        return " [ "+costos + " ] , " + " [ "+predecesores + " ]  ";
    }
    
    
    
    
    //8. En un grafo dirigido pesado implementar el algoritmo de Dijsktra que muestre
    //cual es el camino de costo mínimo entre un vértice a y b y cual el costo
    public String costoMinimoDesdeAhaciaB(T verticeA , T verticeB ){
        List<Boolean> marcados = new LinkedList<>();
        List<Double> costos =new LinkedList<>();
        List<Integer> predecesores = new LinkedList<>();
        int indiceActual = this.listaDeVertices.indexOf(verticeA);
        marcados = inicializaMarcados();
        costos = inicializarCostos(indiceActual);
        predecesores = inicializarPredecesores();
        while (indiceActual != -1) { 
            marcarVertice(marcados, indiceActual);
            List<AdyacenteConPeso> listaDeAdyacencias = this.listasDeAdyacencia.get(indiceActual);
            for (int i = 0; i < listaDeAdyacencias.size(); i++) {
                int indiceA = listaDeAdyacencias.get(i).getIndiceVertice();
                double costoA = costos.get(indiceA);
                double costoV = costos.get(indiceActual);
                double costoAV = listaDeAdyacencias.get(i).getCosto();
                if(costoA > costoV +  costoAV){
                    costos.set(indiceA,costoV +  costoAV);
                    predecesores.set(indiceA, indiceActual);
                }
            }
            indiceActual = buscarIndiceNoMarcadoConCostoMenor(marcados,costos);
        }
        Integer indiceB =this.listaDeVertices.indexOf(verticeB);
        double costoMinimo = costos.get(indiceB);
        List<T> camino= new LinkedList<>();
        camino.add(verticeB);
        while(indiceB != -1){
            Integer dondeIr = predecesores.get(indiceB);
            if(dondeIr != -1){
                camino.add(this.listaDeVertices.get(dondeIr));
            }
            indiceB = predecesores.get(indiceB);
        }
        return " El costo minimo "+ costoMinimo + " y el camino" + " [ "+ camino + " ]  ";
    }
    //fin
    
    
    //9. En un grafo dirigido pesado implementar el algoritmo de Dijsktra que muestre con que vértices hay 
     //caminos de costo mínimo partiendo desde un vértice a y con qué costo.
    public String caminosConCostoMinimoDesdeA(T verticeA ){
        List<Boolean> marcados = new LinkedList<>();
        List<Double> costos =new LinkedList<>();
        List<Integer> predecesores = new LinkedList<>();
        int indiceActual = this.listaDeVertices.indexOf(verticeA);
        marcados = inicializaMarcados();
        costos = inicializarCostos(indiceActual);
        predecesores = inicializarPredecesores();
        while (indiceActual != -1) { 
            marcarVertice(marcados, indiceActual);
            List<AdyacenteConPeso> listaDeAdyacencias = this.listasDeAdyacencia.get(indiceActual);
            for (int i = 0; i < listaDeAdyacencias.size(); i++) {
                int indiceA = listaDeAdyacencias.get(i).getIndiceVertice();
                double costoA = costos.get(indiceA);
                double costoV = costos.get(indiceActual);
                double costoAV = listaDeAdyacencias.get(i).getCosto();
                if(costoA > costoV +  costoAV){
                    costos.set(indiceA,costoV +  costoAV);
                    predecesores.set(indiceA, indiceActual);
                }
            }
            indiceActual = buscarIndiceNoMarcadoConCostoMenor(marcados,costos);
        }
        Integer indiceB;
        String caminito = " ";
        
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            indiceB = i;
            List<T> camino= new LinkedList<>();
            camino.add(this.listaDeVertices.get(indiceB));
            while(indiceB != -1){
                Integer dondeIr = predecesores.get(indiceB);
                if(dondeIr != -1){
                    camino.add(this.listaDeVertices.get(dondeIr));
                }
                indiceB = predecesores.get(indiceB);
            }
            caminito = caminito + "  " + camino;
        }
        
        return "el camino" + " [ "+ caminito + " ]  ";
    }
    //fin
    
    //7. En un grafo dirigido usando la implementación del algoritmo de Floyd-Warsall encontrar los caminos 
    //de costo mínimo entre un vértice a y el resto. Mostrar los costos y cuáles son los caminos
    public String FloydWarshall(T vertice){
        int n = this.listaDeVertices.size();
        Integer predecesores[][] = new Integer[n][n];
        Double costos[][] = new Double[n][n];
        predecesores = inicializarMatrizDePredecesores(n);
        costos = inicializarMatrizDeCostos(n);
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if(costos[i][j] > costos[i][k] + costos[k][j]){
                        costos[i][k] = costos[i][k] + costos[k][j];
                        predecesores[i][j] =k;
                    }
                }
            }
        }
        int indiceVertice = this.listaDeVertices.indexOf(vertice);
        List<Double> listaDeCostos = new LinkedList<>();
        for (int i = 0; i < n;i++) {
            listaDeCostos.add(costos[indiceVertice][i]);
        }
        return "Los costos son :" + listaDeCostos;
    }
    //fin
    
    @Override
    public void eliminarArista(T verticeOrigen, T verticeDestino) throws ExcepcionOrdenInvalido {
        super.eliminarArista(verticeOrigen, verticeDestino); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int cantidadDeAristas() {
        throw new UnsupportedOperationException("No soportado en grafos dirigidos");
    }

    @Override
    protected int gradoDe(T vertice) throws ExcepcionVerticeNoExiste {
        throw new UnsupportedOperationException("No soportado en grafos dirigidos");
    }
    
    public int gradoDeEntrada(T vertice){
        return 0;
    }
    
    public int gradoDeSalida(T vertice) throws ExcepcionVerticeNoExiste{
        return this.gradoDe(vertice);
    }

    @Override
    public boolean esConexo() {
         throw new UnsupportedOperationException("No soportado en grafos dirigidos");
    }
    
    public boolean esFuertementeConexo(){
        List<T> elRecorrido;
        for(T unVertice : this.listaDeVertices){
            elRecorrido = dfs(unVertice);
            if(elRecorrido.size() != this.cantidadDeVertices()){
                return false;
            }
        }
        return true;
    }
    
//    public boolean esDebilmenteConexo(){
//        List<T> recorrido = new ArrayList<>();
//        List<Boolean> marcados = inicializaMarcados();
//        int posicionDeVerticeInicial =0;
//        while(! (posicionDeVerticeInicial == -1)){
//            dfs(recorrido,marcados,posicionDeVerticeInicial);
//            if(estanTodosMarcados(marcados)){
//                return true;
//            }else{
//                posicionDeVerticeInicial = buscarIndiceConVerticeConAritaMarcada(marcados);
//            }
//        }
//            
//        return false;
//    }
//    
//    public boolean existeCicloEnGrafo(){
//        List<Boolean> marcados = new LinkedList<>();
//        marcados = inicializaMarcados();
//        int indiceDeVerticeInicial = 0 ;
//        T vertice = this.listaDeVertices.get(indiceDeVerticeInicial);
//        while(indiceDeVerticeInicial != -1){
//            if(dfsCicloEnUnGrafo(this.listaDeVertices.get(indiceDeVerticeInicial),marcados)){
//                return true;
//            }else{
//                if(this.estanTodosMarcados(marcados)){
//                    return false;
//                }else{
//                    indiceDeVerticeInicial = this.buscarIndiceNoMarcado(marcados);
//                }
//            }
//            
//        }
//        return false;
//    }
    
//    public int cantidadDeIslasDirigido() throws ExcepcionOrdenInvalido{
//        Grafo<T> grafoAuxiliar = new Grafo<>();
//        for (int i = 0; i < this.listaDeVertices.size(); i++) {
//            grafoAuxiliar.insertarVertice(this.listaDeVertices.get(i));
//        }
//        for (int i = 0; i < this.listasDeAdyacencia.size(); i++) {
//            List<Integer> adyacentesDeVertice = this.listasDeAdyacencia.get(i);
//            for (int j = 0; j < adyacentesDeVertice.size(); j++) {
//                grafoAuxiliar.insertarArista(this.listaDeVertices.get(i),  this.listaDeVertices.get(adyacentesDeVertice.get(j)));
//            }
//        }
//        return this.cantidadDeIslas();
//    }
    


//    private int buscarIndiceConVerticeConAritaMarcada(List<Boolean> marcados) {
//        for (int i = 0; i < marcados.size(); i++) {
//            if(!marcados.get(i)){
//                List<Integer> listaDeAdyacentesDelNoMarcado = this.listasDeAdyacencia.get(i);
//                for (int j = 0; j < listaDeAdyacentesDelNoMarcado.size(); j++) {
//                    if(estaMarcadoElVertice(marcados, j)){
//                        return i;
//                    }
//                }
//            }
//        }
//        // -1 Si no encuentra 
//        return -1;
//    }
//
//    private boolean dfsCicloEnUnGrafo(T vertice, List<Boolean> marcados) {
//        marcarVertice(marcados, this.listaDeVertices.indexOf(vertice));
//        Boolean b = false;
//        List<Integer> listaDeAdyacentesAlVertice = this.listasDeAdyacencia.get(this.listaDeVertices.indexOf(vertice));
//        for (Integer adyacenteAlVertice : listaDeAdyacentesAlVertice) {
//            if(! this.estaMarcadoElVertice(marcados,adyacenteAlVertice)){
//                b = dfsCicloEnUnGrafo(this.listaDeVertices.get(adyacenteAlVertice), marcados);
//            }else{
//                return true;
//            }
//        }
//        if(b){
//            return true;
//        }
//        return false;
//    }

    private List<Integer> inicializarPredecesores() {
        List<Integer> predecesores = new ArrayList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            predecesores.add(-1);
        }
        return predecesores;
    }

    private List<Double> inicializarCostos(int indiceActual) {
        List<Double> costos = new ArrayList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            if(indiceActual == i){
                costos.add(0.0);
            }else{
                costos.add(GrafoPesado.INFINITO);
            }
            
        }
        return costos;
    }

    private int buscarIndiceNoMarcadoConCostoMenor(List<Boolean> marcados, List<Double> costos) {
       double costoMenor = GrafoPesado.INFINITO;
        for (int i = 0; i < marcados.size(); i++) {
            if(marcados.get(i)==Boolean.FALSE){
                
                for (int j = 0; j < costos.size(); j++) {
                    double costoActual = costos.get(i);
                    if(marcados.get(j)==Boolean.FALSE && costoActual< costoMenor){
                        costoMenor = costoActual;
                    }
                }
            }
        }
        if(costoMenor == GrafoPesado.INFINITO){
            costoMenor=-1;
        }
        return costos.indexOf(costoMenor);
    }


    private Integer[][] inicializarMatrizDePredecesores(int n) {
        Integer[][] predecesores = new Integer[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                predecesores[i][j] = -1;
            }
        }
        return predecesores;
    }

    private Double[][] inicializarMatrizDeCostos(int n) {
        Double[][] predecesores = new Double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(i==j){
                    predecesores[i][j] = 0.0;
                }else{
                if(existeAdyacencia(this.listaDeVertices.get(i), this.listaDeVertices.get(j))){
                    List<AdyacenteConPeso> listaAdyacente = this.listasDeAdyacencia.get(i);
                    AdyacenteConPeso adyacenteABuscar = new AdyacenteConPeso(j);
                    AdyacenteConPeso adyacente = listaAdyacente.get(listaAdyacente.indexOf(adyacenteABuscar));
                    Double costo = adyacente.getCosto();
                    predecesores[i][j] = costo;
                }else{
                    predecesores[i][j] = GrafoPesado.INFINITO;
                }}
                
            }
        }
        return predecesores;
    }



    
}
