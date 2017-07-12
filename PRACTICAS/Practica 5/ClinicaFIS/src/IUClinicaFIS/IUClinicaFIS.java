/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IUClinicaFIS;

import clinicafis.ClinicaFIS;
import clinicafis.Medico;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ana anaya
 */
public class IUClinicaFIS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
         
        ClinicaFIS unaClinica = ClinicaFIS.getInstance(); 
        
        // Definir la variable que nos permite leer String desde teclado
        final Scanner in = new Scanner(System.in);
        int opcion = 0; 
        do{
            try{ // tratamiento de las excepciones. Bloque try en el que se puede producir una excepción y la capturamos
		 
                 //Terminar de diseñar el menú (usando System.out.println(...)) con las opciones que faltan
		 // Podéis hacer vuestros propios diseños de interfaz, esta es la interfaz mínima que tenéis que entregar
                 System.out.println("\n\n********************** OPCIONES ********************\n");
                 
                 System.out.println("===== GESTIÓN DE MEDICO ===== \n" +
                                    "\t10. Nuevo Médico \n" +
                                    "\t11. Definir agenda \n" +
                                    "\t12. Consultar agenda \n" +
                                    "\t13. Ver todos los médicos \n");                 
                 
                  System.out.println("===== GESTIÓN DE PACIENTES =====\n" +
                                     "\t20. Nuevo paciente \n" +
                                     "\t21. Consultar datos personales del paciente \n" +
                                     "\t22. Eliminar Paciente\n" +
                                     "\t23. Ver todos los pacientes\n");	
                                 
                System.out.println("===== GESTIÓN DE CITAS =====  \n" +                             
                                    "\t30. Ver todas las posibles citas \n" +
                                    "\t31. Pedir una cita \n" +
                                    "\t32. Anular una cita \n" +
                                    "\t33. Consultar todas las citas pendientes\n");
                
               System.out.println("===== GESTIÓN DE CONSULTAS MEDICAS =====  \n" +                             
                                    "\t40. Terminar Consulta \n" +
                                    "\t41. Diagnosticar paciente \n" +
                                    "\t42. Consultar datos clinicos paciente\n");
                                                                 
               
                
                System.out.println("\n*******************************************************");
                		         
                System.out.println("\t0. TERMINAR");
		System.out.println("\n*******************************************************");
                 
                // Lectura de un int, para darle valor a opcion.
                opcion =Integer.parseInt(in.nextLine()); 
                
                // Estructura switch con todas las opciones de menú. Algunos de ellos ya lo tenéis hecho
                // Tenéis que terminar las opciones que están incompletas y las que no están hechas
                switch(opcion){
                    case 10: //incluir un nuevo usuario en el sistema 
                                            
                        System.out.print("Nombre del médico: ");
                        String nombre=in.nextLine();
                                       
                        System.out.print("dni del médico: ");
                        String dni= in.nextLine();
                        
                        System.out.print("Especialidad: ");
                        String especialidad= in.nextLine();
                        
                        unaClinica.nuevoMedico(dni, nombre, especialidad);                                             
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;  
                    
                    case 11:/*Definir agenda */
                        System.out.print("dni del médico: ");
                        dni= in.nextLine();
                        System.out.print("A partir de cuántos días? : ");
                        int numeroDias = Integer.parseInt(in.nextLine());                     
                        unaClinica.definirAgenda(dni,numeroDias);
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;
                    
                    case 12:/*Consultar agenda*/
                        System.out.print("dni del médico: ");
                        dni= in.nextLine(); 
                        System.out.print("para qué día \n \t1 = hoy \n \t2 = mañana \n \t .... \n \t-1 = todos los días \n ");
                        numeroDias = Integer.parseInt(in.nextLine()); 
                        
                        System.out.println(unaClinica.consultarAgenda(dni,numeroDias));
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                                                                
                    break;
                    case 13:/*Ver todos los médicos */
                        System.out.println("-------------------------------------------------------------");
                        System.out.println("\t\tdni\t\tnombre\t\tespecialidad");
                        System.out.println("-------------------------------------------------------------");
                        System.out.println(unaClinica.todosLosMedico());
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();                                   
                    break;
                
                    case 20: /*Nuevo paciente  */   
                        System.out.print("DNI del paciente: ");
                        String dnip=in.nextLine();
                        
                        System.out.print("Nombre del paciente: ");
                        String nombrep=in.nextLine();
                                       
                        System.out.print("numero de tarjeta del paciente: ");
                        String tarjetap= in.nextLine();
                        
                        System.out.print("telefono paciente: ");
                        String telefonop= in.nextLine();
                        
                        unaClinica.crearPaciente(dnip, nombrep, tarjetap, telefonop);
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();                          
                    break;
                  
                    case 21: /* Consultar datos clinicos del paciente */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();
                        
                        List infoPaciente = unaClinica.consultarPaciente(dnip);
                        System.out.println("--------------------------------------------------");
                        System.out.println("\tdni\tnombre\ttarjeta\ttelefono");
                        System.out.println("--------------------------------------------------");
                        System.out.println("\t" + infoPaciente);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                              
                    break;             
                  
                    case 22: /* Eliminar paciente  */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();
                        
                        List<String> infoBajaEnClinica = unaClinica.eliminarPaciente(dnip);
                        System.out.println("\t" + infoBajaEnClinica);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;
  
    
                    case 23: /* Ver todos los pacientes */
                        System.out.println("---------------------------------------------------------------------------------");
                        System.out.println("\t\tdni\t\tnombre\t\ttarjeta\t\ttelefono");
                        System.out.println("---------------------------------------------------------------------------------");
                        System.out.println(unaClinica.todosLosPacientes());
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine(); 
                                        
                    break;
                    case 30: /* Ver posibles citas */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();

                        System.out.print("dni del medico: ");
                        String dnim= in.nextLine();
                        
                        List listaPosiblesFechas = unaClinica.obtenerPosiblesCitas(dnim, dnip);
                        System.out.println("\t" + listaPosiblesFechas);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 31: /* Pedir una cita */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();

                        System.out.print("dni del medico: ");
                        dnim= in.nextLine(); 
                        
                        System.out.print("fecha: (SOLO dia) ");
                        int fechad = in.nextInt();
                        
                        System.out.print("fecha: (SOLO mes) ");
                        int fecham = in.nextInt();
                        
                        System.out.print("fecha: (SOLO año) ");
                        int fechaa = in.nextInt();
                        
                        System.out.print("fecha: (SOLO hora) ");
                        int fechah = in.nextInt();
                        
                        System.out.print("fecha: (SOLO minuto) ");
                        int fechamin = in.nextInt();
                        
                        Calendar fecha = Calendar.getInstance();
                        fecha.set(fechaa, fecham-1, fechad, fechah, fechamin);
                        
                        List infoCita = unaClinica.pedirCita(dnip, dnim, fecha);
                        
                        System.out.println("\t" + infoCita);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                        in.nextLine();
                    break;

                    case 32: /* Anular cita */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();
                        
                        System.out.print("fecha: (SOLO dia) ");
                        fechad = in.nextInt();
                        
                        System.out.print("fecha: (SOLO mes) ");
                        fecham = in.nextInt();
                        
                        System.out.print("fecha: (SOLO año) ");
                        fechaa = in.nextInt();
                        
                        System.out.print("fecha: (SOLO hora) ");
                        fechah = in.nextInt();
                        
                        System.out.print("fecha: (SOLO minuto) ");
                        fechamin = in.nextInt();
                        
                        fecha = Calendar.getInstance();
                        fecha.set(fechaa, fecham-1, fechad, fechah, fechamin);
                        
                        List datosConfirmacion = unaClinica.anularCita(dnip, fecha);
                        
                        System.out.println("\t" + datosConfirmacion);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                        in.nextLine();
                        
                    break;

                    case 33: /*  Consultar todas las citas pendientes  */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();
                        
                        List listadoCitas = unaClinica.consultarCitas(dnip);
                        
                        System.out.println("\t" + listadoCitas);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;  
                    case 40: /* Terminar consulta */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();

                        System.out.print("dni del medico: ");
                        dnim= in.nextLine(); 

                        unaClinica.terminarConsulta(dnim, dnip);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 41: /* Hacer un diagnóstico */
                         System.out.print("dni del paciente: ");
                        dnip= in.nextLine();

                        System.out.print("dni del medico: ");
                        dnim= in.nextLine();
                        
                        System.out.print("codigo diagnostico: ");
                        String codd= in.nextLine();

                        System.out.print("texto explicativo: ");
                        String texto= in.nextLine();
                        
                        List infoDiagnostico = unaClinica.diagnosticar(dnip, codd, texto, dnim);
                        
                        System.out.println("\t" + infoDiagnostico);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break;

                    case 42: /*  Consultar datos clinicos pendientes  */
                        System.out.print("dni del paciente: ");
                        dnip= in.nextLine();
                        
                        infoPaciente = unaClinica.consultarDatosClinicos(dnip);
                        
                        System.out.println("\t" + infoPaciente);
                        
                        System.out.print("++++++  Operación realizada con éxito ++++++\n");
                        in.nextLine();
                    break; 

                    case 0: /* terminar */
                    break;                        
                                    
                    default:
                        System.out.println("OPCION NO VÁLIDA");
                    break;
                }
//               
            }catch(Exception ex){ // captura de la excepción
                System.err.println("se ha producido la siguiente excepcion: "+ ex.getMessage());
            } 
        }while(opcion !=0); 
        System.exit(0);
    }  
    }
    
    

