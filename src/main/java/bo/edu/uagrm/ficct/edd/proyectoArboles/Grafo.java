/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.uagrm.ficct.edd.proyectoArboles;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Asus
 */
public class Grafo<T extends Comparable<T>> {
    protected List<T> listaDeVertices ;
    protected List<List<Integer>> listasDeAdyacencia;
    protected static final int LIMITE = -1;
    public Grafo(){
        this.listaDeVertices = new ArrayList<>();
        this.listasDeAdyacencia= new ArrayList<>();
    }
    protected void insertarVertice(T vertice ) throws ExcepcionOrdenInvalido{
        if(this.existeVertice(vertice)){
            throw new ExcepcionOrdenInvalido("Vertice ya existe en el grafo");
        }
        this.listaDeVertices.add(vertice);
        List<Integer> listaDeAdyacentesDelVertice = new ArrayList<>();
        this.listasDeAdyacencia.add(listaDeAdyacentesDelVertice);
    }
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
        if(posicionDeVerticeDeDestino != posicionDeVerticeDeOrigen){
            List<Integer> adyacentesDelDestino = this.listasDeAdyacencia.get(posicionDeVerticeDeDestino);
            adyacentesDelDestino.add(posicionDeVerticeDeOrigen);
            Collections.sort(adyacentesDelDestino);
        }
    }
    protected boolean existeVertice(T vertice) {
        return this.posicionDeVertice(vertice)!=-1;
    }
    
    protected int cantidadDeVertices(){
        return this.listaDeVertices.size();
    }
    
    protected int posicionDeVertice(T vertice){
        for(int i = 0 ; i < this.listaDeVertices.size() ; i++){
            T verticeEnTurno =this.listaDeVertices.get(i);
            if(verticeEnTurno.compareTo(vertice)==0){
                return i;
            }
        }
        return -1;
    }

    protected boolean existeAdyacencia(T verticeOrigen, T verticeDestino) {
        int posicionDeVerticeDeOrigen = this.posicionDeVertice(verticeOrigen);
         int posicionDeVerticeDeDestino = this.posicionDeVertice(verticeDestino);
         List<Integer> adyacentesDelOrigen = this.listasDeAdyacencia.get(posicionDeVerticeDeOrigen);
         return adyacentesDelOrigen.contains(posicionDeVerticeDeDestino);
    }
    public void eliminarVertice(T verticeAEliminar) throws ExcepcionOrdenInvalido{
        if(! this.existeVertice(verticeAEliminar)){
            throw new ExcepcionOrdenInvalido("El vertice no existe");
        }
        int posicionDelVerticeAEliminar = this.posicionDeVertice(verticeAEliminar);
        this.listaDeVertices.remove(posicionDelVerticeAEliminar);
        this.listasDeAdyacencia.remove(posicionDelVerticeAEliminar);
        for(List<Integer> adyacentesDeUnVertice : this.listasDeAdyacencia ){
            if(adyacentesDeUnVertice.contains(posicionDelVerticeAEliminar)){
                int posicionDelVerticeComoAdyacente = adyacentesDeUnVertice.indexOf(posicionDelVerticeAEliminar);
                adyacentesDeUnVertice.remove(posicionDelVerticeComoAdyacente);
            
            }
            for(int i = 0 ; i < adyacentesDeUnVertice.size() ; i ++){
                int posicionAdyacente = adyacentesDeUnVertice.get(i);
                if(posicionAdyacente > posicionDelVerticeAEliminar){
                    posicionAdyacente--;
                    adyacentesDeUnVertice.set(i,posicionAdyacente);
                }
            }
        }
    }
    
    public int cantidadDeAristas(){
        int contador = 0;
        for(int i = 0 ; i < this.listaDeVertices.size() ; i++){
            int indiceAdyacentesActual = i;
            int indiceVerticeActual = i;
            for(int j = indiceAdyacentesActual ; j < this.listasDeAdyacencia.size() ; j++){
                List<Integer> listaAdyacenteActual = this.listasDeAdyacencia.get(j);
                if(listaAdyacenteActual.contains(indiceVerticeActual)){
                    contador = contador + 1 ;
                }
            }
        }
        return contador ;
    }
    public void eliminarArista(T verticeOrigen , T verticeDestino) throws ExcepcionOrdenInvalido{
        if(! this.existeVertice(verticeOrigen)){
            throw new ExcepcionOrdenInvalido("El vertice no existe");
        }
        if(! this.existeVertice(verticeDestino)){
            throw new ExcepcionOrdenInvalido("El vertice no existe");
        }
        int posicionDelVerticeOrigen = this.posicionDeVertice(verticeOrigen);
        int posicionDelVerticeDestino = this.posicionDeVertice(verticeDestino);
        this.listasDeAdyacencia.get(posicionDelVerticeDestino).remove(posicionDelVerticeOrigen);
        
        this.listasDeAdyacencia.get(posicionDelVerticeOrigen).remove(posicionDelVerticeDestino);
        
    }
    
    
    protected int gradoDe(T vertice) throws ExcepcionVerticeNoExiste{
        if(this.existeVertice(vertice)){
            throw new ExcepcionVerticeNoExiste();
        }
        int indiceVertice = this.posicionDeVertice(vertice);
        
        return this.listasDeAdyacencia.get(indiceVertice).size();
    }
    @Override
    public String toString() {
        return "Grafo{" + "listaDeVertices=" + listaDeVertices + ", listasDeAdyacencia=" + listasDeAdyacencia + '}';
    }
    
    public List<T> bfs(T verticeInicial){
        List<T> recorrido = new ArrayList<>();
        if(this.existeVertice(verticeInicial)){
            return recorrido;
        }
        List<Boolean> marcados = inicializaMarcados();
        Queue<T> colaDeVertices = new LinkedList<>();
        colaDeVertices.offer(verticeInicial);
        int posicionDeVerticeInicial = posicionDeVertice(verticeInicial);
        marcarVertice(marcados ,posicionDeVerticeInicial);
        do{
           T verticeEnTurno = colaDeVertices.poll();
           recorrido.add(verticeEnTurno);
           int posicionDeVerticeEnTurno = posicionDeVertice(verticeEnTurno);
           List<Integer> adyacenciasDeVerticeEnTurno = this.listasDeAdyacencia.get(posicionDeVerticeEnTurno);
            for (Integer posicionDeAdyacente : adyacenciasDeVerticeEnTurno) {
                if(! estaMarcadoElVertice(marcados,posicionDeAdyacente)){
                    colaDeVertices.offer(this.listaDeVertices.get(posicionDeAdyacente));
                    marcarVertice(marcados,posicionDeAdyacente);
                }
            }
        }while(! colaDeVertices.isEmpty());
        
        
        return recorrido;
    }

    protected List<Boolean> inicializaMarcados() {
        List<Boolean> marcados = new ArrayList<>();
        for (int i = 0; i < this.cantidadDeVertices(); i++) {
            marcados.add(Boolean.FALSE);
        }
        return marcados;
    }

    protected void marcarVertice(List<Boolean> marcados, int posicionDeVerticeInicial) {
        marcados.set(posicionDeVerticeInicial, Boolean.TRUE);
    }

    protected boolean estaMarcadoElVertice(List<Boolean> marcados, Integer posicion) {
        return marcados.get(posicion);
    }
    
    public List<T> dfs(T verticeInicial){
        List<T> recorrido = new ArrayList<>();
        if(this.existeVertice(verticeInicial)){
            return recorrido;
        }
        List<Boolean> marcados = inicializaMarcados();
        int posicionDeVerticeInicial = posicionDeVertice(verticeInicial);
        dfs(recorrido,marcados,posicionDeVerticeInicial);
        return recorrido;
    }

    protected void dfs(List<T> recorrido, List<Boolean> marcados, int posicionEnTurno) {
        marcarVertice(marcados,posicionEnTurno);
        recorrido.add(this.listaDeVertices.get(posicionEnTurno));
        List<Integer> adyacenciaDeVerticeEnTurno = this.listasDeAdyacencia.get(posicionEnTurno);
        for (Integer posicionDeAdyacente : adyacenciaDeVerticeEnTurno) {
            if(! estaMarcadoElVertice(marcados, posicionDeAdyacente)){
                dfs(recorrido,marcados,posicionDeAdyacente);
            }
        }
    }
    
    //4. En un grafo no dirigido implementar un algoritmo para encontrar el número de islas que hay en el grafo
    public int cantidadDeIslas(){
        List<T> recorrido = new ArrayList<>();
        List<Boolean> marcados = inicializaMarcados();
        int posicionDeVerticeInicial =0;
        int cantidadDeIslaas = 0;
        while(! (posicionDeVerticeInicial == -1)){
            cantidadDeIslaas = cantidadDeIslaas + 1 ;
            dfs(recorrido,marcados,posicionDeVerticeInicial);
            if(estanTodosMarcados(marcados)){
                return cantidadDeIslaas;
            }else{
                posicionDeVerticeInicial = buscarIndiceNoMarcado(marcados);
            }
        }
            
        return cantidadDeIslaas;
    }
    //fin
    
    
    //3. En un grafo no dirigido implementar un algoritmo para encontrar si el grafo tiene ciclo
    public boolean existeCicloEnGrafo() throws ExcepcionOrdenInvalido{
        List<Boolean> marcados = this.inicializaMarcados();
        int posicionInicialDelVertice = 0 ;
        Grafo<T> grafoAuxiliar = new Grafo<>();
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            grafoAuxiliar.insertarVertice(this.listaDeVertices.get(i));
        }
        while(posicionInicialDelVertice != -1){
            T vertice = this.listaDeVertices.get(posicionInicialDelVertice);
            if(this.dfsCicloEnUnGrafo(grafoAuxiliar,vertice,marcados)){
                return true;
            }else{
                if(this.estanTodosMarcados(marcados)){
                    return false;
                }else{
                    posicionInicialDelVertice = this.buscarIndiceNoMarcado(marcados);
                }
            }
            
        }
        return false;
    }
    //fin        
    public boolean esConexo(){
        List<T> elRecorrido = bfs(this.listaDeVertices.get(0));
        return elRecorrido.size() == this.cantidadDeVertices();
    }
    
   protected int buscarIndiceNoMarcado(List<Boolean> marcados) {
        for (int i = 0; i < marcados.size(); i++) {
            if (marcados.get(i)==false) {
                return i ;
            }
        }
        return -1;
    }
    protected boolean estanTodosMarcados(List<Boolean> marcados) {
        for (int i = 0; i < marcados.size(); i++) {
            if (!marcados.get(i)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfsCicloEnUnGrafo(Grafo<T> grafoAuxiliar,T vertice,  List<Boolean> marcados) throws ExcepcionOrdenInvalido {
        Boolean b;
        marcarVertice(marcados,this.listaDeVertices.indexOf(vertice));
        List<Integer> adyacenciaDeVerticeEnTurno = this.listasDeAdyacencia.get(this.listaDeVertices.indexOf(vertice));
        for (Integer posicionDeAdyacente : adyacenciaDeVerticeEnTurno) {
            if(! estaMarcadoElVertice(marcados, posicionDeAdyacente)){
                grafoAuxiliar.insertarArista(vertice,this.listaDeVertices.get(posicionDeAdyacente));
                b = dfsCicloEnUnGrafo(grafoAuxiliar, this.listaDeVertices.get(posicionDeAdyacente), marcados);
            }else{
                if(!grafoAuxiliar.existeAdyacencia(vertice,grafoAuxiliar.listaDeVertices.get(posicionDeAdyacente))){
                    return true;
                }
            }            
        }
        return false;
    }
}
