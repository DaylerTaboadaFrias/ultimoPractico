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
public class DiGrafo< T extends Comparable<T>> extends Grafo<T> {
    public DiGrafo(){
        super();
    }
    @Override
    public void insertarArista(T verticeOrigen , T verticeDestino)throws ExcepcionOrdenInvalido{
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
        List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posicionDeVerticeDeOrigen);
        adyacentesDelOrigen.add(posicionDeVerticeDeDestino);
        Collections.sort(adyacentesDelOrigen);
        
    }
    //14. En un grafo dirigido solo usando como base la lógica de un recorrido (dfs o bfs) encuentre 
    //desde que vértices se puede llegar a un vértice a.
    public List<T> verticesHaciaUnVerticeA(T verticeA){
        List<T> listaDeVertices = new LinkedList<>();
        List<Boolean> marcados = new LinkedList<>();
        for (int i = 0; i < this.listaDeVertices.size() ; i++) {
            if (i != this.listaDeVertices.indexOf(verticeA)) {
                        marcados= inicializaMarcados();
                if(verificarCaminodeBhaciaA(marcados,this.listaDeVertices.indexOf(verticeA),i)){
                listaDeVertices.add(this.listaDeVertices.get(i));
            }
            }
            
        }
        return listaDeVertices;
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
    //2. En un grafo dirigido implementar un algoritmo para encontrar si es débilmente conexo
    public boolean esDebilmenteConexo(){
        List<T> recorrido = new ArrayList<>();
        List<Boolean> marcados = inicializaMarcados();
        int posicionDeVerticeInicial =0;
        while(! (posicionDeVerticeInicial == -1)){
            dfs(recorrido,marcados,posicionDeVerticeInicial);
            if(estanTodosMarcados(marcados)){
                return true;
            }else{
                posicionDeVerticeInicial = buscarIndiceConVerticeConAritaMarcada(marcados);
            }
        }
            
        return false;
    }
    //fin
    
    
    
    //1. En un grafo dirigido implementar un algoritmo para encontrar si el grafo tiene ciclos
    
    
    public boolean existeCicloEnGrafo(){
        List<Boolean> marcados = new LinkedList<>();
        marcados = inicializaMarcados();
        int indiceDeVerticeInicial = 0 ;
        T vertice = this.listaDeVertices.get(indiceDeVerticeInicial);
        while(indiceDeVerticeInicial != -1){
            if(dfsCicloEnUnGrafo(this.listaDeVertices.get(indiceDeVerticeInicial),marcados)){
                return true;
            }else{
                if(this.estanTodosMarcados(marcados)){
                    return false;
                }else{
                    indiceDeVerticeInicial = this.buscarIndiceNoMarcado(marcados);
                }
            }
            
        }
        return false;
    }
    //fin
    
    //5. En un grafo dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo
    public int cantidadDeIslasDirigido() throws ExcepcionOrdenInvalido{
        Grafo<T> grafoAuxiliar = new Grafo<>();
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            grafoAuxiliar.insertarVertice(this.listaDeVertices.get(i));
        }
        for (int i = 0; i < this.listasDeAdyacencia.size(); i++) {
            List<Integer> adyacentesDeVertice = this.listasDeAdyacencia.get(i);
            for (int j = 0; j < adyacentesDeVertice.size(); j++) {
                grafoAuxiliar.insertarArista(this.listaDeVertices.get(i),  this.listaDeVertices.get(adyacentesDeVertice.get(j)));
            }
        }
        return this.cantidadDeIslas();
    }
    //fin


    private int buscarIndiceConVerticeConAritaMarcada(List<Boolean> marcados) {
        for (int i = 0; i < marcados.size(); i++) {
            if(!marcados.get(i)){
                List<Integer> listaDeAdyacentesDelNoMarcado = this.listasDeAdyacencia.get(i);
                for (int j = 0; j < listaDeAdyacentesDelNoMarcado.size(); j++) {
                    if(estaMarcadoElVertice(marcados, j)){
                        return i;
                    }
                }
            }
        }
        // -1 Si no encuentra 
        return -1;
    }

    private boolean dfsCicloEnUnGrafo(T vertice, List<Boolean> marcados) {
        marcarVertice(marcados, this.listaDeVertices.indexOf(vertice));
        Boolean b = false;
        List<Integer> listaDeAdyacentesAlVertice = this.listasDeAdyacencia.get(this.listaDeVertices.indexOf(vertice));
        for (Integer adyacenteAlVertice : listaDeAdyacentesAlVertice) {
            if(! this.estaMarcadoElVertice(marcados,adyacenteAlVertice)){
                b = dfsCicloEnUnGrafo(this.listaDeVertices.get(adyacenteAlVertice), marcados);
            }else{
                return true;
            }
        }
        if(b){
            return true;
        }
        return false;
    }

    void insertarArista(int i, int i0, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean verificarCaminodeBhaciaA(List<Boolean> marcados ,int verticeA,int verticeB) {
        marcarVertice(marcados, verticeB);
        boolean verificar = false;
        List<Integer> listaDeAdyacentes = this.listasDeAdyacencia.get(verticeB);
        for (Integer proximoVertice : listaDeAdyacentes) {
            if((! estaMarcadoElVertice(marcados, proximoVertice)) && verificar==false){
                verificar = verificarCaminodeBhaciaA(marcados, verticeA, proximoVertice);
            }
        }
        if(verticeA == verticeB||verificar){
            return true;
        }
        return false;
    }



    
}
