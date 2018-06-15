package Conexion;

//Este archivo contiene la configuracion de la conexion por ODBC de la BD, donde se 
//Cargan todos los drives necesarios y archivos que permiten realizar las operaciones
//con las tablas de la BD

//Paquete con las funcion que pemite configurar la conexion de la BD y tiene definido 
//los controladores necesarios para las conexiones de la BD //Connection - ResultSet(consulta selet) - Statement(ejecutar consultas insert, update, delete) - SQException (Excepciones en SQL)
import java.sql.*; 

//paquete que permite configurar las funciones de red
import java.net.URL;   
import java.util.ArrayList;
import java.util.List;

public class conexion_Bd {
    //Objeto Tipo conexion que contiene todos los parametros para interactuar 
    //con la BD se crea un objeto tipo con
    public Connection con;
    protected String Database="EleccionesCorba";//nombre de la Base de datos
    protected String pass_bd="q";//Nombre de la contraseña a la BD
    
    //Modificar en caso de ser necesario
    protected String cc = "jdbc:postgresql://localhost:5432/" +Database+
                "?user=postgres&password="+pass_bd;//conexión completa
    
    //Constructor de la clase Conexion que contiene el parametro nombre que determina
    //el nombre de la base de datos que se va ha conectar con el ODBC
 
     public conexion_Bd()    {
          try
            {
              //Clase que especifica el nombre de los controladores que se van
              //ha utilizar en la carga de la BD en este caso son los de Access
              Class.forName("org.postgresql.Driver");  //loads the driver
            }
            catch(ClassNotFoundException e)
            {
                    System.out.println("No encontro driver");
            }
            try
            {
                    //url es un texto que contiene la ruta del nombre o la direccion
                    //de coneccion de la base da Datos conectada al JDBC
                    String url = "jdbc:postgresql://localhost:5432/"+Database;
                    //Con es el objeto creado para la coneccion donde se especifican los
                    //parametros de la ubicacion de la BD, login si la base de datos
                    //posee seguridad y por ultimo la clave
                    //DriverManager.getConnection es el servicio que permite establecer
                    //la conexion ABRIR CONEXION!!!
                    con = DriverManager.getConnection(url, "postgres", pass_bd);
                    //checkForWarning es una funcion que recibe como parametro
                    //el listado de los errores generados en la conexion
                    checkForWarning (con.getWarnings ());
                    //Es un drvie que permite cargar las configuraciones del proveedor
                    //de la BD en este caso las reglas de configuraciones de pOSTgRESS
                    DatabaseMetaData dma = con.getMetaData ();
                    System.out.println("\nConectado a: " + dma.getURL());
                    System.out.println("Driver       " + dma.getDriverName());
                    System.out.println("Version      " + dma.getDriverVersion());
            }
            catch (SQLException ex) 
            {
                System.out.println ("\n*** SQLException caught ***\n");
                while (ex != null) 
                {
                    System.out.println ("SQLState: " + ex.getSQLState ());
                    System.out.println ("Message:  " + ex.getMessage ());
                    System.out.println ("Vendor:   " + ex.getErrorCode ());
                    ex = ex.getNextException ();
                    System.out.println ("Error Línea 71");
                }
        }
        catch (java.lang.Exception ex) 
        {
            ex.printStackTrace ();
        }		
    }
    
    private static boolean checkForWarning (SQLWarning warn) throws SQLException  {
        boolean rc = false;
        if (warn != null) 
        {
            System.out.println ("\n *** Warning ***\n");
            rc = true;
            while (warn != null) 
            {
                System.out.println ("SQLState: " +warn.getSQLState ());
                System.out.println ("Message:  " +warn.getMessage ());
                System.out.println ("Vendor:   " +warn.getErrorCode ());
                warn = warn.getNextWarning ();
            }
        }
        return rc;
    }

    public void cerrarConexion()    {
        try
        {
            //Cierra la conexion de la Base de Datos
            con.close();
            System.out.println("Cerrada la conexion con la Base de Datos");
        }
        catch(SQLException e)
        {
            System.out.println("Error al cerrar la conexion con la Base de datos. \n"+e);
        }
    }
    
    public ArrayList<String> consultar_participantes_votos() {
        String tabla_a_consultar="Participantes";
        ArrayList<String> Participantes = new ArrayList<String>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            String ordenar="Votos";//Nombre de la tabla de ordenado
            //Verificar dependiendo del tipo de tabla lleva o no comillas
            String sql="select * from \""+tabla_a_consultar+"\" ORDER BY \""+ordenar+"\" DESC";
            System.out.println(sql);
            ResultSet resultado = comando.executeQuery(sql);
            while(resultado.next()) {
            Participantes.add(resultado.getString("Nombre"));
            Participantes.add(""+resultado.getInt("Votos"));
        }
        resultado.close();
        comando.close();
        conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
        return Participantes;
    }

    public void limpiar_bd(){
        eliminar_datos_de_tabla("Participantes");
        eliminar_datos_de_tabla("Usuarios");
        eliminar_datos_de_tabla("Votos_realizados");
    }
    
    public void eliminar_datos_de_tabla(String tabla_a_eliminar){
        
        //ArrayList<String> Participantes = new ArrayList<String>();
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            //Verificar dependiendo del tipo de tabla lleva o no comillas
                    
            String sql="delete from \""+tabla_a_eliminar+"\"";
                
            comando.executeUpdate(sql);
            
        comando.close();
        conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
    }

    public void llenado_de_participantes(ArrayList<String> participantes) throws SQLException, ClassNotFoundException{
        String tabla_a_insertar="Participantes";
        eliminar_datos_de_tabla(tabla_a_insertar);//primero eliminamos lo que contenga depues inserrtamos nuevos
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            //Verificar dependiendo del tipo de tabla lleva o no comillas
            
            System.out.println("Verificando cantidad de participantes = "+participantes.size()/2);
            
            for (int i = 0; i < participantes.size();i++) {
                String sql="insert into \""+tabla_a_insertar+"\"(\"Nombre\",\"Votos\") values ('"+participantes.get(i)+"',0)";
                System.out.println(sql);
                comando.executeUpdate(sql);
            }
        comando.close();
        conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
    }

    public void llenado_de_alumnos(ArrayList<String> alumnos) throws SQLException, ClassNotFoundException{
        String tabla_a_insertar="Usuarios";
        eliminar_datos_de_tabla(tabla_a_insertar);//primero eliminamos lo que contenga depues inserrtamos nuevos
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            //Verificar dependiendo del tipo de tabla lleva o no comillas
            
            System.out.println("Verificando cantidad de alumnos = "+alumnos.size()/2);
            
            for (int i = 0; i < alumnos.size();i+=2) {
                String sql="insert into \""+tabla_a_insertar+"\"(\"Matricula\",\"Nombre\") values ('"+alumnos.get(i)+"','"+alumnos.get(i+1)+"')";
                System.out.println(sql);
                comando.executeUpdate(sql);
            }
        comando.close();
        conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
    }
    
    public Boolean Usuario_existe(String Matricula){
        Boolean existe=false;
        String tabla_usuario="Votos_realizados";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
              
            Statement comando = conexion.createStatement();
                 
            //Consulta a la BD
            String sql="select * from \""+tabla_usuario+"\" where \"Matricula\"="+Matricula;
            ResultSet resultado = comando.executeQuery(sql);
            while(resultado.next()) {
                existe=true;
                }
            resultado.close();
            comando.close();
            conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
        }
        return existe;
    }
    
    public Boolean Votar(String matricula){
        Boolean voto=null;
        if(Usuario_existe(matricula)){
            voto=false;
            System.out.println("Error al votar");
        }else{
            voto=true;
            Registrar_voto(matricula);
            System.out.println("Voto exitoso");
        }
        return voto;
    }
    
    public void Registrar_voto(String Matricula){
        String tabla_a_registrar="Votos_realizados";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            //Verificar dependiendo del tipo de tabla lleva o no comillas
            
        String sql="insert into \""+tabla_a_registrar+"\" values ('"+Matricula+"')";
        System.out.println(sql);
        comando.executeUpdate(sql);
            
        comando.close();
        conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
    }

    public int Contar_voto_personal(String Nombre){
        int num_votos=0;
        String tabla_participantes="Participantes";
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
                 
            //Consulta a la BD
            String sql="select \"Votos\" from \""+tabla_participantes+"\" where \"Nombre\"='"+Nombre+"'";
            System.out.println(sql);
            ResultSet resultado = comando.executeQuery(sql);
            while(resultado.next()) {
                num_votos=resultado.getInt(1);
                }
            resultado.close();
            comando.close();
            conexion.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return num_votos;
    }
    
    public void Contabilizar_voto(String Persona){
        String tabla_a_modificar="Participantes";
        int voto_cantidad=0;
        voto_cantidad=Contar_voto_personal(Persona)+1;
        try {
            Class.forName("org.postgresql.Driver");
            Connection conexion = DriverManager.getConnection(cc);
            Statement comando = conexion.createStatement();
            //Verificar dependiendo del tipo de tabla lleva o no comillas
            String sql="update \""+tabla_a_modificar+"\" set \"Votos\" = "+voto_cantidad;
            sql=sql+" where \"Nombre\"='"+Persona+"'";
            System.out.println(sql);
            comando.executeUpdate(sql);
            comando.close();
            conexion.close();
        } catch(ClassNotFoundException | SQLException e) {
                  System.out.println(e.getMessage());
        }
    }
    
    
    
}
