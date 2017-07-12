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
public class HistoriaClinica {
    static int NumeroDeHistorias = 0;
    private Calendar fechaApertura;
    private int numeroHistoria;
    private List<AnotacionHC> misAnotaciones;
    
    HistoriaClinica(){
        fechaApertura = Calendar.getInstance();
        numeroHistoria = NumeroDeHistorias+1;
        misAnotaciones = new ArrayList();
    }
    
    List obtenerDatosHistoriaClinica(){
        List datosHistoria = new ArrayList();
        List datosAnotacion = new ArrayList();
        datosHistoria.add(numeroHistoria);
        datosHistoria.add(fechaApertura.getTime().clone());
        for( AnotacionHC a : misAnotaciones ){
            datosAnotacion = a.obtenerDatos();
            datosHistoria.add(datosAnotacion);
        }
        
        return datosHistoria;
    }
    
    List nuevoDiagnostico(String codDiagnostico, String textoExplicativo, Medico medico){
        AnotacionHC unDiagnostico = new Diagnostico(codDiagnostico,textoExplicativo,medico);
        List datosDiagnostico = unDiagnostico.obtenerDatos();
        misAnotaciones.add(unDiagnostico);
        return datosDiagnostico;
    }
}
