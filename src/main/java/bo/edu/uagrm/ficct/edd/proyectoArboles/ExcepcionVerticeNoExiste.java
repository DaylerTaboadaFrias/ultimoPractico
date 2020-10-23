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
public class ExcepcionVerticeNoExiste  extends Exception{
     public ExcepcionVerticeNoExiste(){
    super("Error , el vertice no exite");
    }
    public ExcepcionVerticeNoExiste(String mensaje){
    super(mensaje);
    }
}
