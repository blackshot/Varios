
// package xddddddd;

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
    private boolean mysql = false; // false para usar sqlite (base de datos sin conexion a internet)

    private String url, db, ip, puerto, user, pw;
	
    
    public Conector () {
        db = "basededatos";
        if(mysql){
            ip = "ingresa la ip aca";
            user = "nombredeusuario";
            pw = "contraseña";
            puerto = "3306"; // puerto sql por defecto		
            url = "jdbc:mysql://" + ip + ":" + puerto + "/" + db;
        }
        else{
            url = "jdbc:sqlite:" + db + ".db";
        }
        try{
           conexion = getConectar();
        }
        catch(com.mysql.cj.jdbc.exceptions.CommunicationsException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos 1");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
        }
        catch(SQLException e){
           Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private Connection getConectar() throws SQLException {
        if(mysql){
            return DriverManager.getConnection(url, user, pw);
        }
        else
        {
            return DriverManager.getConnection(url);
        }
        
    }
    
    /*private void setDriver() { // esto no es necesario en las ultimas versiones de java
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver ()); // este es el driver de mariadb o mysql
        } catch (SQLException ex) {
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/

    public ResultSet Query(String query){

        try {
            statment = conexion.createStatement();
            set = statment.executeQuery(query);
        }
        catch(com.mysql.cj.jdbc.exceptions.CommunicationsException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos [Query]");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (Exception e) {
            System.out.println("Exception in query method:" + query + "\n\n" + e.getMessage());
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
        catch(com.mysql.cj.jdbc.exceptions.CommunicationsException e)
        {
            javax.swing.JOptionPane.showMessageDialog(null, "No se puede conectar a la base de datos [Update]");
            Logger.getLogger(Conector.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        catch (SQLException e) {
            System.out.println("Exception in update method:\n" + update + "\n\n" + e.getMessage());
            return false;
        }
        return (result!=0); // si es 0 ningun registro cambió.
    }
    public void Desconectar(){
        try{
            conexion.close();
            conexion = null;
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