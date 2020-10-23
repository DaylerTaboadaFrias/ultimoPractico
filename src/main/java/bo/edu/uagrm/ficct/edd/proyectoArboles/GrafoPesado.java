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
public class GrafoPesado<T extends Comparable<T>> {
    protected List<T> listaDeVertices ;
    protected List<List<AdyacenteConPeso>> listasDeAdyacencia;
    protected static final int LIMITE = -1;
    protected static final Double INFINITO = Double.MAX_VALUE;
    public GrafoPesado(){
        this.listaDeVertices = new ArrayList<>();
        this.listasDeAdyacencia= new ArrayList<>();
    }
    protected void insertarVertice(T vertice ) throws ExcepcionOrdenInvalido{
        if(this.existeVertice(vertice)){
            throw new ExcepcionOrdenInvalido("Vertice ya existe en el grafo");
        }
        this.listaDeVertices.add(vertice);
        List<AdyacenteConPeso> listaDeAdyacentesDelVertice = new ArrayList<>();
        this.listasDeAdyacencia.add(listaDeAdyacentesDelVertice);
    }
    public void insertarArista(T verticeOrigen , T verticeDestino ,double costo)throws ExcepcionOrdenInvalido{
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
        List<AdyacenteConPeso> adyacentesDelOrigen = this.listasDeAdyacencia.get(posicionDeVerticeDeOrigen);
        AdyacenteConPeso adyacente = new AdyacenteConPeso(posicionDeVerticeDeDestino,costo);
        adyacentesDelOrigen.add(adyacente);
        Collections.sort(adyacentesDelOrigen);
        if(posicionDeVerticeDeDestino != posicionDeVerticeDeOrigen){
            adyacente = new AdyacenteConPeso(posicionDeVerticeDeOrigen,costo);
            List<AdyacenteConPeso> adyacentesDelDestino = this.listasDeAdyacencia.get(posicionDeVerticeDeDestino);
            adyacentesDelDestino.add(adyacente);
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

    //10. En un grafo no dirigido pesado implementar el algoritmo de Kruskal que 
    //muestre cual es el grafo encontrado por el algoritmo
    public GrafoPesado<T> Kruskal() throws ExcepcionOrdenInvalido{
        GrafoPesado<T> grafoAuxiliar = new GrafoPesado<>();
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            grafoAuxiliar.insertarVertice(this.listaDeVertices.get(i));
        }
        List<Arista> listaDeCostos = new LinkedList<>();
        listaDeCostos = listarCostosAristas();
        Collections.sort(listaDeCostos);
        int i =0;
        while(! listaDeCostos.isEmpty()){
             int indiceOrigen = listaDeCostos.get(i).getIndiceVerticeInicio();
             int indiceDestino = listaDeCostos.get(i).getIndiceVerticeFinal();
             Double costo = listaDeCostos.get(i).getCosto();
             grafoAuxiliar.insertarArista(this.listaDeVertices.get(indiceOrigen),this.listaDeVertices.get(indiceDestino) , costo);
             if (grafoAuxiliar.existeCicloEnGrafo()) {
                grafoAuxiliar.eliminarArista(this.listaDeVertices.get(indiceOrigen), this.listaDeVertices.get(indiceDestino));
            }
             listaDeCostos.remove(i);
        }
        return grafoAuxiliar;
    }
    //fin
    
    //11. En un grafo no dirigido pesado implementar el algoritmo de Prim que 
    //muestre cual es el grafo encontrado por el algoritmo
    public GrafoPesado<T> Prim() throws ExcepcionOrdenInvalido{
        GrafoPesado<T> grafoAuxiliar = new GrafoPesado<>();
        for (int i = 0; i < this.listaDeVertices.size(); i++) {
            grafoAuxiliar.insertarVertice(this.listaDeVertices.get(i));
        }
        int indiceVertice = 0;
        while(indiceVertice != -1){
            List<AdyacenteConPeso> adyacentes = this.listasDeAdyacencia.get(indiceVertice);
            Arista adyacenteDestino = buscarIndiceMenor(grafoAuxiliar,adyacentes,indiceVertice);
            if(adyacenteDestino.getIndiceVerticeFinal() != -1){
            grafoAuxiliar.insertarArista(this.listaDeVertices.get(indiceVertice), this.listaDeVertices.get(adyacenteDestino.getIndiceVerticeFinal()),
                    adyacenteDestino.getCosto());
            }
            indiceVertice = adyacenteDestino.getIndiceVerticeFinal();
        }   
        return grafoAuxiliar;
    }
    //fin

    
    
    protected boolean existeAdyacencia(T verticeOrigen, T verticeDestino) {
        int posicionDeVerticeDeOrigen = this.posicionDeVertice(verticeOrigen);
         int posicionDeVerticeDeDestino = this.posicionDeVertice(verticeDestino);
         List<AdyacenteConPeso> adyacentesDelOrigen = this.listasDeAdyacencia.get(posicionDeVerticeDeOrigen);
         AdyacenteConPeso adyacente = new AdyacenteConPeso(posicionDeVerticeDeDestino);
         return adyacentesDelOrigen.contains(adyacente);
    }
    public void eliminarVertice(T verticeAEliminar) throws ExcepcionOrdenInvalido{
        if(! this.existeVertice(verticeAEliminar)){
            throw new ExcepcionOrdenInvalido("El vertice no existe");
        }
        int posicionDelVerticeAEliminar = this.posicionDeVertice(verticeAEliminar);
        this.listaDeVertices.remove(posicionDelVerticeAEliminar);
        this.listasDeAdyacencia.remove(posicionDelVerticeAEliminar);
        for(List<AdyacenteConPeso> adyacentesDeUnVertice : this.listasDeAdyacencia ){
            AdyacenteConPeso ayacenteAEliminar = new AdyacenteConPeso(posicionDelVerticeAEliminar);
            if(adyacentesDeUnVertice.contains(ayacenteAEliminar)){
                int posicionDelVerticeComoAdyacente = adyacentesDeUnVertice.indexOf(ayacenteAEliminar);
                adyacentesDeUnVertice.remove(posicionDelVerticeComoAdyacente);
            
            }
            for(int i = 0 ; i < adyacentesDeUnVertice.size() ; i ++){
                AdyacenteConPeso adyacenteAuxiliar  = adyacentesDeUnVertice.get(i);
                int posicionAdyacente = adyacenteAuxiliar.getIndiceVertice();
                if(posicionAdyacente > posicionDelVerticeAEliminar){
                    posicionAdyacente--;
                    adyacenteAuxiliar.setIndiceVertice(posicionAdyacente);
//                    adyacentesDeUnVertice.set(i,posicionAdyacente);
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
                List<AdyacenteConPeso> listaAdyacenteActual = this.listasDeAdyacencia.get(j);
                AdyacenteConPeso adyacente = new AdyacenteConPeso(indiceVerticeActual);
                if(listaAdyacenteActual.contains(adyacente)){
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
        AdyacenteConPeso pesoA = new AdyacenteConPeso(posicionDelVerticeOrigen);
        AdyacenteConPeso pesoB = new AdyacenteConPeso(posicionDelVerticeDestino);
        int poscionEnAdyacentesOrigen = this.listasDeAdyacencia.get(posicionDelVerticeOrigen).indexOf(pesoB);
        int poscionEnAdyacentesDestino = this.listasDeAdyacencia.get(posicionDelVerticeDestino).indexOf(pesoA);
        this.listasDeAdyacencia.get(posicionDelVerticeOrigen).remove(poscionEnAdyacentesOrigen);
        
        this.listasDeAdyacencia.get(posicionDelVerticeDestino).remove(poscionEnAdyacentesDestino);
        
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
           List<AdyacenteConPeso> adyacenciasDeVerticeEnTurno = this.listasDeAdyacencia.get(posicionDeVerticeEnTurno);
            for (AdyacenteConPeso posicionDeAdyacente : adyacenciasDeVerticeEnTurno) {
                if(! estaMarcadoElVertice(marcados,posicionDeAdyacente)){
                    colaDeVertices.offer(this.listaDeVertices.get(posicionDeAdyacente.getIndiceVertice()));
                    marcarVertice(marcados,posicionDeAdyacente.getIndiceVertice());
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

    protected boolean estaMarcadoElVertice(List<Boolean> marcados, AdyacenteConPeso posicion) {
        return marcados.get(posicion.getIndiceVertice());
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
        List<AdyacenteConPeso> adyacenciaDeVerticeEnTurno = this.listasDeAdyacencia.get(posicionEnTurno);
        for (AdyacenteConPeso posicionDeAdyacente : adyacenciaDeVerticeEnTurno) {
            if(! estaMarcadoElVertice(marcados, posicionDeAdyacente)){
                dfs(recorrido,marcados,posicionDeAdyacente.getIndiceVertice());
            }
        }
    }
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
    
    public boolean existeCicloEnGrafo() throws ExcepcionOrdenInvalido{
        List<Boolean> marcados = this.inicializaMarcados();
        int posicionInicialDelVertice = 0 ;
        GrafoPesado<T> grafoAuxiliar = new GrafoPesado<>();
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

    private boolean dfsCicloEnUnGrafo(GrafoPesado<T> grafoAuxiliar,T vertice,  List<Boolean> marcados) throws ExcepcionOrdenInvalido {
        Boolean b=false;
        marcarVertice(marcados,this.listaDeVertices.indexOf(vertice));
        List<AdyacenteConPeso> adyacenciaDeVerticeEnTurno = this.listasDeAdyacencia.get(this.listaDeVertices.indexOf(vertice));
        for (AdyacenteConPeso posicionDeAdyacente : adyacenciaDeVerticeEnTurno) {
            if(! estaMarcadoElVertice(marcados, posicionDeAdyacente)){
                grafoAuxiliar.insertarArista(vertice,this.listaDeVertices.get(posicionDeAdyacente.getIndiceVertice()),posicionDeAdyacente.getCosto());
                b = dfsCicloEnUnGrafo(grafoAuxiliar, this.listaDeVertices.get(posicionDeAdyacente.getIndiceVertice()), marcados);
            }else{
                if(!grafoAuxiliar.existeAdyacencia(vertice,this.listaDeVertices.get(posicionDeAdyacente.getIndiceVertice()))){
                    return true;
                }
            }            
        }
        if(b==true){
            return true;
        }
        return false;
    }

    private List<Arista> listarCostosAristas() {
        List<Arista> lista = new LinkedList<>();
        Arista arista ;
        for (int i = 0; i < this.listasDeAdyacencia.size() ; i++) {
            for (int j = 0; j < this.listasDeAdyacencia.get(i).size(); j++) {
                int indiceActual = i;
                int indiceDestino = this.listasDeAdyacencia.get(i).get(j).getIndiceVertice();
                Double costo = this.listasDeAdyacencia.get(i).get(j).getCosto();
                if (! verificarSiExiste(lista, indiceActual, indiceDestino)) {
                   arista = new Arista(indiceActual,indiceDestino,costo);
                   lista.add(arista); 
                }
                
            }
        }
            
        return  lista;
    }
    
    public boolean verificarSiExiste(List<Arista> lista ,int origen , int destino){
        for (int i = 0; i < lista.size(); i++) {
            int listaOrigen = lista.get(i).getIndiceVerticeInicio();
            int listaDestino = lista.get(i).getIndiceVerticeFinal();
            double costo = lista.get(i).getCosto();
            if(origen == listaDestino && destino==listaOrigen){
                return true;
            }
        }
        return false;
    }
    
    private Boolean[][] inicializarMatrizMarcada() {
       Boolean marcados[][] = new Boolean[this.listasDeAdyacencia.size()][this.listasDeAdyacencia.size()];
        for (int i = 0; i < this.listasDeAdyacencia.size(); i++) {
            for (int j = 0; j < this.listasDeAdyacencia.size(); j++) {
                marcados[i][j]=false;
            }
        }
       return marcados;
    }

    private boolean estaMatrizMarcada(Boolean[][] marcados) {
        for (int i = 0; i < marcados.length; i++) {
            for (int j = 0; j < marcados[i].length; j++) {
                return marcados[i][j]==false;
            }
        }
        return true;
    }

    private Arista buscarIndiceMenor(GrafoPesado<T> grafoAuxiliar, List<AdyacenteConPeso> adyacentes, int indice) {
        List<Arista> listaAdyacentesOrdenados = listarAdyacentesOrdenados(indice);
        for (AdyacenteConPeso adyacente : adyacentes) {
            if(! grafoAuxiliar.existeAdyacencia(this.listaDeVertices.get(indice), this.listaDeVertices.get(adyacente.getIndiceVertice()))){
                return listaAdyacentesOrdenados.get(0);
            }else{
                listaAdyacentesOrdenados.remove(0);
            }
        }
        Arista negativa = new Arista(-1, -1, 0);
        return negativa;
    }

    private List<Arista> listarAdyacentesOrdenados(int indice) {
       List<Arista> lista = new LinkedList<>();
        Arista arista ;
            for (int j = 0; j < this.listasDeAdyacencia.get(indice).size(); j++) {
                int indiceActual = indice;
                int indiceDestino = this.listasDeAdyacencia.get(indice).get(j).getIndiceVertice();
                Double costo = this.listasDeAdyacencia.get(indice).get(j).getCosto();
                if (! verificarSiExiste(lista, indiceActual, indiceDestino)) {
                   arista = new Arista(indiceActual,indiceDestino,costo);
                   lista.add(arista); 
                }
                
            }
        return lista;
    }







}
