
//package equisDE;

// recuerden si van a usar MariaDB tienen que agregar el driver.jar correspondiente al igual que para sqlite

import java.sql.*;
import java.util.logging.*;
/**
 *
 * @author SirH
 */
 
public class Conector implements AutoCloseable{
    private Connection conexion = null;
    private Statement statment = null;
    private ResultSet set = null;
    private boolean mysql = true; // false para usar sqlite (base de datos en archivo sin conexion)

    private String url;
    private String 
            db = "bdnombre", 
            ip = "ip", 
            puerto="3306", 
            user = "root", 
            pw="";
	
    
    public Conector () {
        if(mysql)	
            url = "jdbc:mysql://" + ip + ":" + puerto + "/" + db + "?user=" + user + "&password=" + pw;
        else
            url = "jdbc:sqlite:" + db + ".db";
        try{
           conexion = DriverManager.getConnection(url);
        }
        catch(SQLException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public ResultSet Query(String query){

        try {
            statment = conexion.createStatement();
            set = statment.executeQuery(query);
        }
        /*catch(com.mysql.cj.jdbc.exceptions.CommunicationsException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos [Query]");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
        }*/
        catch (Exception e) {
            System.out.println("Excepcion en la consulta [Query]:" + query + "\n\n" + e.getMessage());
        }
        return set;
    }

    public boolean Update (String update) {
        int result;
        try
        {   
            statment = conexion.createStatement();
            result = statment.executeUpdate(update);
        }
        /*catch(com.mysql.cj.jdbc.exceptions.CommunicationsException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos [Update]");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }*/
        catch (SQLException e) {
            System.out.println("Excepcion en la actualizacion [Update]:\n" + update + "\n\n" + e.getMessage());
            return false;
        }
        return (result!=0); // si es 0 ningun registro cambió.
    }
    public void Desconectar(){
        try{
            if(conexion != null)
                conexion.close();
            if (statment != null)
                statment.close();
            if(set != null)
                set.close();
            conexion = null;
            statment = null;
            set = null;
        }catch(SQLException e){
             System.out.println("Error al cerrar la conexion");
        }
    }
    @Override
    protected void finalize() throws Throwable 
    {
        super.finalize();
        Desconectar();
    }

    @Override
    public void close() throws Exception {
        this.Desconectar();
    }
}
/* METODO DE USO

    import java.sql.ResultSet;
    import java.sql.SQLException;
    ....
    
    try(Conector bd = new Conector()){
        ResultSet rs;
        rs = bd.Query("SELECT * FROM BLABLABLA");
        
        while(rs.next()){
            System.out.prinln(rs.getString("nombre"));
        }
        //EJEMPLO DE INSERT O CUALQUIER OPERACION QUE NO DEVUELVA DATOS
        boolean exito = bd.Update("INSERT INTO TABLA VALUES BLABLABAL");
        if (exito)
            profit;
        else
            shit happens;
    }
    catch( SQLException e ){ e.printStackTrace();}
*/