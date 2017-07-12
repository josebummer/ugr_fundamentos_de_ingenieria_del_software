/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

/**
 *
 * @author jose
 */
public abstract class AnotacionHC{
    private Calendar fecha;
    private String comentario;
    private Medico miMedico;
    
    AnotacionHC(String textoExplicativo, Medico medico){
        comentario = textoExplicativo;
        miMedico = medico;
        fecha = Calendar.getInstance();
    }
    
    abstract List obtenerDatos();
    
    public Medico getMedico(){
        return miMedico;
    }
    
    public String getComentario(){
        return comentario;
    }
    
    public Calendar getFecha(){
        return fecha;
    }
}
