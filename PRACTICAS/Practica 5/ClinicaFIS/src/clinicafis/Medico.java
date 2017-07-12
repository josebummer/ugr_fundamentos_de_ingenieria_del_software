/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clinicafis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar; 
import java.util.Date;
import java.util.List;
import java.util.Map; 
import java.util.TreeMap;

/**
 *
 * @author ana anaya
 */
public class Medico {
    private static int NumeroDias = 5; // Variable de ámbito de clase que indica el número de días que definimos en la agenda de un médico
    private String dni;
    private String nombre;
    private String especialidad;
    private static final int MAXCITAS = 10;
    // Map en el que se guardan las citas del médico ordenadas por la key, que son fechas 
    // Cada value de este Map va a ser la lista de Citas del médico para un determinado día
    private Map<Calendar, List<Cita>> agenda = new TreeMap(); 
    
    // Constructor
    Medico(String dni, String nombre, String especialidad){
        this.dni=dni;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.definirAgenda(0);
    }
    
    // Redefinición de toString 
    @Override
    public String toString(){     
        return  "\t\t" + dni +  "\t\t" + nombre + "\t\t" + especialidad + "\n";
    }
    
    // Definir una agenda para 5 días
    // a partir de un número de dias posteriores al actual proporcionado en @aPartirDe
    public void definirAgenda(int aPartirDe){
    
        Calendar hoy = Calendar.getInstance();
        hoy.add(Calendar.DATE, aPartirDe);
        // se crean 5 entradas en la agenda a partir del numero de días indicado
        for (int i = 0; i < NumeroDias; i++) {  
            hoy.add(Calendar.DATE, i);
            List<Cita> citas = new ArrayList();     
            for (int j = 0; j < 10; j++) {
                    citas.add(new Cita(hoy,j,this));
            }
            agenda.put(hoy,citas);
            hoy = Calendar.getInstance();  
            hoy.add(Calendar.DATE, aPartirDe);
        }
    }
    
    // Proporciona las citas de un médico para el día que se le indique 
    // Si numeroDias == 1 --> hoy
    // Si numeroDias == 2 --> mañana
    // Si numeroDias == 3 --> pasado mañana
    // ....
    // Si numeroDias == -1 --> todos los días    
    List<String> obtenerCitas(int numeroDias) throws Exception{
        List<String> citas = new ArrayList();
        List<Cita> citasDelDia;
        if(numeroDias != -1){ 
            Calendar dia = Calendar.getInstance();
            dia.add(Calendar.DATE, numeroDias-1);   
            citas.add((new SimpleDateFormat("dd/mm")).format(dia.getTime())+"\n");
            citasDelDia = this.seleccionarCitasDia(dia);
            for (Cita cita : citasDelDia) {
                citas.add(cita.toString());  
            }
        }else if(numeroDias == -1)
            {
                for (Map.Entry<Calendar, List<Cita>> entry : agenda.entrySet()) {
                    Calendar key = entry.getKey();
                    List<Cita> citasDia = entry.getValue();
                    citas.add((new SimpleDateFormat("dd/mm")).format(key.getTime())+"\n");
                    for (Cita cita : citasDia) {
                            citas.add(cita.toString());  
                    }                    
                }  
            }
        return citas;   
    }
    
   // La visibilidad de esta operación es privada porque no se necesita usalar desde fuera de la clase Medico,
    // si necesitáis de ella en otro ámbito cambiarla 
   private  List<Cita> seleccionarCitasDia(Calendar dia ) throws Exception{
        List<Cita> salida = new ArrayList();         
        boolean encontrado = false; 
        for (Map.Entry<Calendar, List<Cita>> entry : agenda.entrySet()) {
            Calendar key = entry.getKey();
            if((key.get(Calendar.DATE) == dia.get(Calendar.DATE)) && (key.get(Calendar.MONTH)==dia.get(Calendar.MONTH)) && (key.get(Calendar.YEAR)== dia.get(Calendar.YEAR)) && !encontrado)
                encontrado = true;
                salida = agenda.get(key);
        }
        if(!encontrado) throw new Exception("PARA ESE DÍA NO ESTÁ DEFINIDA LA AGENDA");
        return salida;    
    
    }
   
   public String getNombre(){
       return nombre;
   }
   
   public String getEspecialidad(){
       return especialidad;
   }
   
   public String getDni(){
       return dni;
   }
   
   List<Date> obtenerPosiblesCitas(){
       Calendar fecha;
       List<Date> listaPosiblesFechas = new ArrayList();
       List<Cita> citas = seleccionarCitasPosteriores(Calendar.getInstance());
       for(int i = 1; i <= MAXCITAS;i++){
           Cita c = citas.get(i);
           if(c.getEstado() == TipoEstado.LIBRE){
               fecha = c.getFecha();
               listaPosiblesFechas.add(fecha.getTime());
           }
       }
       return listaPosiblesFechas;
   }
   
   private List<Cita> seleccionarCitasPosteriores(Calendar fecha){
        List<Cita> citas = new ArrayList();
        for (Map.Entry<Calendar, List<Cita>> entry : agenda.entrySet()) {
            Calendar key = entry.getKey();
            List<Cita> citasDia = entry.getValue();
            if(key.after(fecha)) citas.addAll(citasDia);
        } 
        return citas;
    }
   
   Cita buscarCita(Calendar fecha) throws Exception{
       List<Cita> citas = new ArrayList();
       Cita cita = null;
       boolean encontrado = false;
       for (Map.Entry<Calendar, List<Cita>> entry : agenda.entrySet()) {
            Calendar key = entry.getKey();
            if((key.get(Calendar.DATE) == fecha.get(Calendar.DATE)) && (key.get(Calendar.MONTH)==fecha.get(Calendar.MONTH)) && (key.get(Calendar.YEAR)== fecha.get(Calendar.YEAR)) && !encontrado){
                citas = agenda.get(key);
                encontrado = true;
            }
        }
       encontrado = false;
       for(Cita c : citas){
           Calendar f = c.getFecha();
           if((f.get(Calendar.HOUR_OF_DAY) == fecha.get(Calendar.HOUR_OF_DAY)) && (f.get(Calendar.MINUTE)==fecha.get(Calendar.MINUTE)) && !encontrado){
               if(c.getEstado().equals(TipoEstado.PENDIENTE)) throw new Exception("YA HAY UNA CITA A ESA HORA");
               cita = c;
               encontrado = true;
           }
       }
       return cita;
   }
   
   List asignarCita(Paciente paciente, Calendar fecha) throws Exception{
       List infoCita = new ArrayList();
       boolean encontrado = false;
       infoCita.add(paciente.getNombre());
       infoCita.add(nombre);
       infoCita.add(fecha.getTime());
       Cita cita = this.buscarCita(fecha);
       if(cita != null) encontrado = true;
       if(!encontrado) throw new Exception("PARA ESE DÍA NO ESTÁ DEFINIDA LA AGENDA");
       else cita.asignaPaciente(paciente);
       
       return infoCita;
   }
   
   void terminarConsulta(Paciente paciente) throws Exception{
       boolean encontrado = false;
       List<Cita> citas = this.seleccionarCitasDia(Calendar.getInstance());
       for( int i = 1; i <= citas.size() && !encontrado; i++ ){
           Cita c = citas.get(i);
           encontrado = c.esTuPaciente(paciente);
           if(encontrado){
               c.setEstado(TipoEstado.ATENDIDA);
           }
       }
       if(!encontrado) throw new Exception("NO EXISTE CITA PARA HOY DE ESE PACIENTE CON ESE MEDICO");
   }
}