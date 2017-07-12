/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

/**
 *
 * @author jose
 */
public class Diagnostico extends AnotacionHC {
    private String codDiagnostico;
    
    Diagnostico(String pcodDiagnostico, String textoExplicativo, Medico medico) {
        super(textoExplicativo,medico);
        codDiagnostico = pcodDiagnostico;
    }
    
    @Override
    List obtenerDatos(){
        List datosDiagnostico = new ArrayList();
        datosDiagnostico.add(super.getMedico().getNombre());
        datosDiagnostico.add(super.getFecha().getTime());
        datosDiagnostico.add(super.getComentario());
        
        return datosDiagnostico;
    }
    
}
