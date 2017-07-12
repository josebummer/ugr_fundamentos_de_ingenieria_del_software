/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ana anaya
 */
class Cita {
    private static int TiempoDeCita = 10; // variable de ámbito de clase que indica el tiempo que tiene asignado cada cita
    private Calendar fecha;
    private TipoEstado estado = TipoEstado.LIBRE;   
    private Medico miMedico;
    private Paciente miPaciente;
    
    // A partir del número de cita de esa fecha se calcula la hora de la cita
    // a cada cita se le reservan 10 minutos
    Cita(Calendar fecha, int numeroCita, Medico medico) { 
        this.fecha =(Calendar) fecha.clone();
        this.fecha.set(Calendar.HOUR_OF_DAY,9);
        this.fecha.set(Calendar.MINUTE,0);
        this.fecha.add(fecha.MINUTE,numeroCita*TiempoDeCita);
        this.miMedico = medico;
    }
    
    public Calendar getFecha(){
        return fecha;
    }
    
    List anularCita() throws Exception{
        List datosAnulacion = new ArrayList();
        Calendar hoy = Calendar.getInstance();
        if(hoy.after(fecha) || estado == TipoEstado.ATENDIDA) throw new Exception("LA CITA NO PUEDE SER CANCELADA");
        this.liberarCita();
        String nombreMedico = miMedico.getNombre();
        datosAnulacion.add(fecha.getTime().clone());
        datosAnulacion.add(nombreMedico);
        
        return datosAnulacion;
    }
    
    public TipoEstado getEstado(){
        return estado;
    }
    
    List obtenerDatos(){
        List datosCita = new ArrayList();
        String nombreMedico = miMedico.getNombre();
        String especialidad = miMedico.getEspecialidad();
        datosCita.add(fecha.getTime().clone());
        datosCita.add(nombreMedico);
        datosCita.add(especialidad);
        
        return datosCita;
    }
    
    void liberarCita(){
        estado = TipoEstado.LIBRE;
        miPaciente = null;
    }
    
    boolean esTuMedico(Medico medico){
        return miMedico.equals(medico);
    }
    
    void asignaPaciente(Paciente paciente){
        estado = TipoEstado.PENDIENTE;
        miPaciente = paciente;
        paciente.incluirCita(this);
    }
    
    public void setEstado(TipoEstado e){
        estado = e;
    }
    
    boolean esTuPaciente(Paciente paciente){
        if(miPaciente == null) return false;
        else return (miPaciente.getDNI().equals(paciente.getDNI()));    
    }
    
    // Redefinición de toString, cuando la cita tenga asignado el Paciente 
    // tendrás que quitar los comentarios que hay en este código
    @Override
    public String toString()
     {
          SimpleDateFormat hmsf = new SimpleDateFormat("hh:mm");
          String salida =  "hora: " + hmsf.format(fecha.getTime()) +" (" + estado.toString()+ ")";
//          if (miPaciente != null)
//              salida += "Paciente: " + miPaciente.getNombre();
          salida +=  "\n";
          return salida;
     }
    
}
