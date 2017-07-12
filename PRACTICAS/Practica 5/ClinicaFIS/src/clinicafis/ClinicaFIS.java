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
import java.util.Date;

/**
 *
 * @author ana anaya
 */
public class ClinicaFIS {
    // patrón singleton
    private static ClinicaFIS instancia = new ClinicaFIS();
    public static ClinicaFIS getInstance() { 
        return instancia;
    }
    
    // Map en el que se guardan los médicos, la key es el dni del médico
    private Map<String, Medico> misMedicos = new HashMap();       
    // Map en el que se guardan los pacientes, la key es el dni del paciente
    private Map<String, Paciente> misPacientes = new HashMap();
    
    public void nuevoMedico(String dni,String nombre,String especialidad) throws Exception{
        if(existeMedico(dni)) throw new Exception("YA EXISTE UN MÉDICO CON ESE DNI");
        misMedicos.put(dni, new Medico(dni,nombre,especialidad));
    }     

    private boolean existeMedico(String dni) {
         return misMedicos.containsKey(dni);
    }
    
    public void definirAgenda(String dniM,int aPartirDe) throws Exception { 
         Medico unMedico = buscarMedico(dniM);
         unMedico.definirAgenda(aPartirDe);
    }

    private Medico buscarMedico(String dni) throws Exception {
       if(!existeMedico(dni)) throw new  Exception("NO EXISTE EL MÉDICO CON ESE DNI"); 
       return misMedicos.get(dni);
    }

    public List<String> consultarAgenda(String dniM,int numeroDias) throws Exception { 
        Medico unMedico = buscarMedico(dniM);
        return unMedico.obtenerCitas(numeroDias);
    }
    
    public List<String> todosLosMedico(){
        List<String> salida = new ArrayList();
        for (Map.Entry<String, Medico> entry : misMedicos.entrySet()) {            
            Medico unMedico = entry.getValue();
            salida.add(unMedico.toString());
        }
        return salida;
    }
    
    public List<String> todosLosPacientes(){
        List<String> salida = new ArrayList();
        for (Map.Entry<String, Paciente> entry : misPacientes.entrySet()) {            
            Paciente p = entry.getValue();
            salida.add(p.toString());
        }
        return salida;
    }
    
    private boolean existePaciente(String dni) {
         return misPacientes.containsKey(dni);
    }
    
    private Paciente buscarPaciente(String dni) throws Exception {
       if(!existePaciente(dni)) throw new Exception("NO EXISTE EL PACIENTE CON ESE DNI"); 
       return misPacientes.get(dni);
    }
    
    public List anularCita(String dni, Calendar fecha) throws Exception{
        List datosConfirmacion = new ArrayList();
        Paciente paciente = this.buscarPaciente(dni);
        datosConfirmacion = paciente.anularCita(fecha);
        
        return datosConfirmacion;
    }
    
    public List consultarCitas(String dni) throws Exception{
        Paciente paciente = this.buscarPaciente(dni);
        List listadoCitas = paciente.consultarCitas();
        return listadoCitas;
    }
    
    public List consultarDatosClinicos(String dni) throws Exception{
        Paciente paciente = this.buscarPaciente(dni);
        List infoPaciente = paciente.obtenerDatosClinicos();
        return infoPaciente;
    }
    
    public List<String> consultarPaciente(String dni) throws Exception{
        Paciente paciente = this.buscarPaciente(dni);
        List infoPaciente = paciente.obtenerDatos();
        return infoPaciente;
    }
    
    public List<String> eliminarPaciente(String dni) throws Exception{
        Paciente paciente = this.buscarPaciente(dni);
        List<String> infoBajaClinica = paciente.bajaClinica();
        return infoBajaClinica;
    }
    
    public List diagnosticar(String dniP, String codDiagnostico, String textoExplicativo, String dniM) throws Exception{
        Medico medico = this.buscarMedico(dniM);
        Paciente paciente = this.buscarPaciente(dniP);
        List infoDiagnostico = paciente.diagnostico(codDiagnostico,textoExplicativo,medico);
        return infoDiagnostico;
    }
    
    private boolean existePacienteConTarjeta(String numeroTarjeta) {
        boolean existe = false;
        for (Map.Entry<String, Paciente> entry : misPacientes.entrySet()) {            
            Paciente p = entry.getValue();
            if(p.getNumeroTarjeta().equals(numeroTarjeta) && !existe){
                existe = true;
            }
        }
        return existe;
    }
   
    public void crearPaciente(String dni, String nombre, String numeroTarjeta, String telefono) throws Exception{
        if(this.existePaciente(dni)) throw new Exception("YA EXISTE OTRO PACIENTE CON ESE DNI");
        if(this.existePacienteConTarjeta(numeroTarjeta)) throw new Exception("YA EXISTE OTRO PACIENTE CON ESE NUMERO DE TARJETA");
        misPacientes.put(dni, new Paciente(dni,nombre,numeroTarjeta,telefono));
    }
    
    public List<Date> obtenerPosiblesCitas(String dniM, String dniP) throws Exception{
        List<Date> listaPosiblesFechas = new ArrayList();
        Medico medico = this.buscarMedico(dniM);
        Paciente paciente = this.buscarPaciente(dniP);
        boolean tienesCita = paciente.tienesCita(medico);
        if(tienesCita) throw new Exception("YA TIENES CITA PENDIENTE PARA ESE MEDICO");
        if(!tienesCita){
            listaPosiblesFechas = medico.obtenerPosiblesCitas();
        }
        return listaPosiblesFechas;
    }
    
    public List pedirCita(String dniP, String dniM, Calendar fecha) throws Exception{
        Medico medico = this.buscarMedico(dniM);
        Paciente paciente = this.buscarPaciente(dniP);
        List infoCita = medico.asignarCita(paciente,fecha);
        return infoCita;
    }
    
    public void terminarConsulta(String dniM, String dniP) throws Exception{
        Medico medico = this.buscarMedico(dniM);
        Paciente paciente = this.buscarPaciente(dniP);
        medico.terminarConsulta(paciente);
    }
    
}
