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
public class Arista implements Comparable<Arista>{
    private int indiceVerticeInicio;
    private int indiceVerticeFinal;
    private double costo;

    public Arista(int indiceVerticeInicio,int indiceVerticeFinal, double costo) {
        this.indiceVerticeInicio = indiceVerticeInicio;
        this.indiceVerticeFinal = indiceVerticeFinal ;
        this.costo = costo;
    }

    public int getIndiceVerticeInicio() {
        return indiceVerticeInicio;
    }

    public void setIndiceVerticeInicio(int indiceVerticeInicio) {
        this.indiceVerticeInicio = indiceVerticeInicio;
    }

    public int getIndiceVerticeFinal() {
        return indiceVerticeFinal;
    }

    public void setIndiceVerticeFinal(int indiceVerticeFinal) {
        this.indiceVerticeFinal = indiceVerticeFinal;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }



    
    
    
    
    
    @Override
    public int compareTo(Arista vertice) {
        Double esteVertice = this.costo;
        Double elOtroVertice = vertice.costo;
        return esteVertice.compareTo(elOtroVertice);
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null) {
            return false;
        }
        if (getClass() != otro.getClass()) {
            return false;
        }
        Arista other = (Arista)otro;
        return this.costo == other.costo;
    }

    
    
    
    
}
