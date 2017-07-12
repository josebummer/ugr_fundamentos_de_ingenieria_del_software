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
public class Paciente {
    private String dni;
    private String nombre;
    private String numeroTarjeta;
    private String telefono;
    private boolean activo;
    private List<Cita> misCitas;
    private HistoriaClinica miHistoria;
    
    Paciente(String pdni, String pnombre, String pnumeroTarjeta, String ptelefono){
        dni = pdni;
        nombre = pnombre;
        numeroTarjeta = pnumeroTarjeta;
        telefono = ptelefono;
        activo = true;
        misCitas = new ArrayList();
        miHistoria = new HistoriaClinica();
        HistoriaClinica.NumeroDeHistorias++;
  }
    
    private int existeCita(Calendar fecha) {
        int pos = -1;
        boolean salir = false;
        for( int i = 1; i <= misCitas.size() && !salir; i++ ){
            Calendar f = misCitas.get(i).getFecha();
            if((f.get(Calendar.DATE) == fecha.get(Calendar.DATE)) && (f.get(Calendar.MONTH)==fecha.get(Calendar.MONTH)) && (f.get(Calendar.YEAR)== fecha.get(Calendar.YEAR)) && (f.get(Calendar.HOUR_OF_DAY) == fecha.get(Calendar.HOUR_OF_DAY)) && (f.get(Calendar.MINUTE)==fecha.get(Calendar.MINUTE))){
                pos = i;
                salir = true;
            }
        }
        return pos;
    }
    
    private Cita buscarCita(Calendar fecha) throws Exception {
       int pos = existeCita(fecha);
       if(pos == -1) throw new Exception("NO EXISTE CITA CON ESA FECHA"); 
       return misCitas.get(pos);
    }
    
    List anularCita(Calendar fecha) throws Exception{
        List datosConfirmacion = new ArrayList();
        Cita cita = this.buscarCita(fecha);
        List datosAnulacion = cita.anularCita();
        datosConfirmacion.add(nombre);
        datosConfirmacion.add(datosAnulacion);
        misCitas.remove(cita);
        
        return datosConfirmacion;
    }
    
    List consultarCitas(){
        List listadoCitas = new ArrayList();
        List datosCita = new ArrayList();
        for( Cita c : misCitas ){
            if(c.getEstado() == TipoEstado.PENDIENTE){
                datosCita = c.obtenerDatos();
                listadoCitas.add(datosCita);
            }
        }
        
        return listadoCitas;
    }
    
    List obtenerDatosClinicos(){
        List infoPaciente = new ArrayList();
        infoPaciente.add(nombre);
        List datosHistoria = miHistoria.obtenerDatosHistoriaClinica();
        infoPaciente.add(datosHistoria);
        return infoPaciente;
    }
    
    List<String> obtenerDatos(){
        List<String> infoPaciente = new ArrayList();
        infoPaciente.add(dni);
        infoPaciente.add(nombre);
        infoPaciente.add(numeroTarjeta);
        infoPaciente.add(telefono);
        return infoPaciente;
    }
    
    List<String> bajaClinica(){
        List<String> infoBajaClinica = new ArrayList();
        for( Cita c : misCitas ){
            c.liberarCita();
        }
        infoBajaClinica.add(dni);
        infoBajaClinica.add(nombre);
        activo = false;
        misCitas.clear();
        return infoBajaClinica;
    }
    
    List diagnostico(String codDiagnostico, String textoExplicativo, Medico medico){
        List infoDiagnostico = new ArrayList();
        infoDiagnostico.add(nombre);
        List datosDiagnostico = miHistoria.nuevoDiagnostico(codDiagnostico, textoExplicativo,medico);
        infoDiagnostico = datosDiagnostico;
        
        return infoDiagnostico;
    }
    
    public String getNumeroTarjeta(){
        return numeroTarjeta;
    }
    
    private List<Cita> seleccionarCitasPosteriores(Calendar fecha){
        List<Cita> citas = new ArrayList();
        for( Cita c : misCitas ){
            if(c.getFecha().after(fecha)) citas.add(c);
        }
        return citas;
    }
    
    boolean tienesCita(Medico medico){
        boolean tienesCita = false;
        List<Cita> citas = this.seleccionarCitasPosteriores(Calendar.getInstance());
        for( int i = 1; i <= citas.size() && !tienesCita; i++ ){
            Cita c = citas.get(i);
            tienesCita = c.esTuMedico(medico);
        }
        return tienesCita;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    void incluirCita(Cita cita){
        misCitas.add(cita);
    }
    
    public String getDNI(){
        return dni;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    @Override
    public String toString(){     
        return  "\t\t" + dni +  "\t\t" + nombre + "\t\t" + numeroTarjeta + "\t\t" + telefono + "\n";
    }
}
