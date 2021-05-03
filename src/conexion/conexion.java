package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vistas.ventanaVendedor;
import vistas.ventanaGerente;



public class conexion {
public static Connection con;

public void conectar(){ 
        try {
            
            con=DriverManager.getConnection("jdbc:mysql://localhost:3306/testcelaju","root","root1234");
            if (con!=null){
            System.out.println("Conectado a la base de datos");
            int prueba  = con.getNetworkTimeout();
            System.out.println("tiempo de conexion: " +prueba);
            }
        } catch (SQLException e) {
            System.out.println("Sin Conexion a base de datos");
        }
    }
    
    public void desconectar() throws SQLException{
        con.close();
        System.out.println("Desconectado de la base de Datos");
    }
    
    
    public void iniciosesion(String usuario, String contraseña) throws SQLException{
        PreparedStatement ps = con.prepareStatement("select * from usuario where Nombre =? and Contraseña=?");
        ps.setString(1,usuario);
        ps.setString(2,contraseña);
        ResultSet rs = ps.executeQuery();
        if(rs.next()==true){
           JOptionPane.showMessageDialog(null,"Bienvenido") ;
           System.out.println("el usuario es "+rs.getString(2));
           if("1".equals(rs.getString(4))){
                System.out.println("usuario es gerente");
                ventanaGerente gerente = new ventanaGerente();
                gerente.setVisible(true);
            }
            if("2".equals(rs.getString(4))){
                System.out.println("usuario es vendedor");
                ventanaVendedor vendedor = new ventanaVendedor();
                vendedor.setVisible(true);
               
            }
         }else{
            JOptionPane.showMessageDialog(null,"Datos Incorrectos") ;
        }
    }
    
    public void agregarcliente(String Nombre, String Nit, String Telefono, String Direccion, String Tipo) throws SQLException{
        
        PreparedStatement ps = con.prepareStatement("INSERT INTO cliente(Nombre, Nit, Telefono, Direccion, Tipo) VALUES(?,?,?,?,?)");
        ps.setString(1, Nombre);
        ps.setString(2, Nit);
        ps.setString(3, Telefono);
        ps.setString(4, Direccion);
        ps.setString(5, Tipo);
        ps.executeUpdate();
        System.out.println("cliente agregado");        
    }
    
     public void ingresarproducto(String Clave, String Descripcion, String Linea, int Existencia, double Precio, int Bodega_idBodega ) throws SQLException{
        PreparedStatement ps1 = con.prepareStatement("INSERT INTO producto(Clave, Descripcion, Linea, Existencia, Precio, Bodega_idBodega) VALUES(?,?,?,?,?,?)");
        ps1.setString(1, Clave);
        ps1.setString(2, Descripcion);
        ps1.setString(3, Linea);
        ps1.setInt(4, Existencia);
        ps1.setDouble(5, Precio);
        ps1.setInt(6, Bodega_idBodega);
        ps1.executeUpdate();
        System.out.println("Producto Agregado");        
    }
     
     public void eliminarproducto(String Clave) throws SQLException{
         PreparedStatement ps = con.prepareStatement("DELETE  FROM producto WHERE Clave=?");
         ps.setString(1, Clave);
         ps.executeUpdate();
         System.out.println("Producto Eliminado");
         
     }
     
     public String buscarcliente(String id) throws SQLException{
         String nombre = null;
         String sql = "select * from cliente where idCliente =?"; 
         try {
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setString(1, id);
             ResultSet rs = ps.executeQuery(); 
             nombre = rs.getString(1);
             System.out.println("en teoria ya encontre algo"+nombre);
             
         } catch (Exception e) {
         }
         return nombre;
         
     }
     
     public void eliminarcliente(String prueba) throws SQLException{
         //DELETE FROM `cliente` WHERE idCliente = 10
         PreparedStatement ps2 = con.prepareStatement("DELETE FROM cliente WHERE idCliente=?");
         ps2.setString(1, prueba );
         ps2.executeUpdate();
         System.out.println("Cliente Eliminado");
     }
    
    public DefaultTableModel cargartabla(){
        DefaultTableModel modelo = new DefaultTableModel();
        String [] titulo = {"Clave","Nombre","Nit","Dirección","Tipo de Cliente"};
        modelo.addColumn("prueba");
        return modelo;
         
    }
    
    
    
    
    
    
}
