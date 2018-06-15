/*
Universidad de la sierra sur Lic. Informática
Luis Fernando Santiago Martínez
 */
package base_de_datos_postgresql;

import Conexion.conexion_Bd;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Fercho
 */
public class Base_de_datos_postgresql {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Este array se ocupa para visualizar como se encuentran las elecciones en ese momento
        ArrayList<String> estadisticas = new ArrayList<String>();
        //Este array es para almacenar el nombre de los candidatos que se vayan a usar
        ArrayList<String> p = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            p.add("Fer "+(i+1));
        }
        //Este array almacena la lista de alumnos con su respectiva matrícula
        ArrayList<String> A = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            A.add("Luis"+(i+1));
            A.add("201406016"+i);
        }
        //Este es un array de prueba que solo contiene matriculas
        ArrayList<String> V = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            V.add("201406016"+i);
        }
        //Creando la conexión a la BD        
        conexion_Bd con = new conexion_Bd();
        //Eliminaos todos los datos de la BD en caso de ser necesario
        //Si no se borrarán los datos omitit esta función
        con.limpiar_bd();
        //Almacenamos a los participantes en la BD
        con.llenado_de_participantes(p);
        //Almacenamos a los alumnos en la BD
        con.llenado_de_alumnos(A);
        
//////////////////////////////////////////////////////////////////////////////////  
//Asignación de votos a diferentes individuos
        

        for (int i = 0; i < 9; i++) {
            //Verificamos si el alumno ya votó
            if(con.Votar(V.get(i))){
                //Si no ha votado aun entonces
                System.out.println("Votamos");
                //Asignamos nuestro voto a la persona que querramos
                con.Contabilizar_voto(p.get(2));
            }else{
                //Si ya votamos
                System.out.println("No podemos votar");
                System.out.println("Tu voto ya había sido registrado hacia otro candidato");
            }
        }
        
        
        if(con.Votar(V.get(9))){
            System.out.println("Votamos");
            con.Contabilizar_voto(p.get(1));
        }else{
            System.out.println("No podemos votar");
            System.out.println("Éxito el error ha sido emitido");
        }
/////////////////////////////////////////////////////////////////////////
                
        estadisticas=con.consultar_participantes_votos();
        //Para visualizar las estadísticas utilizamos el siguiente método
        for (int i = 0; i < estadisticas.size(); i+=2) {
            System.out.println(estadisticas.get(i));//Almacenamos el nombre de la persona postulada
            System.out.println(estadisticas.get(i+1));//Almacenamos la cantidad de votos que poseé esa persona
        }
        //Cerramos la conexión de la BD
        con.cerrarConexion();
    }
    
}
