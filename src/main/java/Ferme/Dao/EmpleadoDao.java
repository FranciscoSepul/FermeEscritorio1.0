package Ferme.Dao;

import Ferme.Dto.*;
import FermeEscritoriodb.Conexion;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class EmpleadoDao implements Crud {

    Empleado emp = new Empleado();
    Sucursal suc = new Sucursal();
    Direccion direc = new Direccion();
    ResultSet rs = null;
    Connection con = null;
    CallableStatement call = null;
    String query = "";

    @Override
    public List Listar() {

        ArrayList list = new ArrayList();
        query = "{call LISTARTODOSEMPLEADOS(?)}";
        try {
            con = Conexion.getConexion();
            call = con.prepareCall(query);
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.execute();
            rs = (ResultSet) call.getObject(1);

            while (rs.next()) {
                emp.setApellido(rs.getString("apellido"));
                emp.setContrasena("contrasena");
                emp.setDigitoVerificador(rs.getString("digitoverif"));
                emp.setEstado(rs.getInt("estado"));
                emp.setNombre(rs.getString("nombree"));
                emp.setRunEmpleado(rs.getString("runempleado"));
                emp.setSexo(rs.getInt("sexoe"));
                emp.setCorreo(rs.getString("correoemple"));
                emp.setIDCARGO(rs.getInt("IDCARGO"));
                direc.setComuna(rs.getString("comuna"));
                direc.setNumero(rs.getString("numero"));
                direc.setNumeroDepto(rs.getString("numerodepto"));
                direc.setPasaje(rs.getString("pasaje"));
                direc.setRegion(rs.getInt("region"));
                suc.setNombre(rs.getString("NOMBRES"));
                suc.setNumFono(rs.getString("telefono"));
                emp.setSucursal(suc);
                emp.setDireccion(direc);
                list.add(emp);

            }
        } catch (SQLException e) {
            System.out.println("error al listar" + e.getMessage());
        }
        return list;
    }

    public Empleado BuscarEmpleado(String rut) {

        try {
            query = "{call LISTAREMPLEADO(?,?)}";
            con = Conexion.getConexion();

            call = con.prepareCall(query);
            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.setString(2, rut);
            call.execute();

            rs = (ResultSet) call.getObject(1);

            while (rs.next()) {
                emp.setApellido(rs.getString("apellido"));
                emp.setContrasena(rs.getString("contrasena"));
                emp.setDigitoVerificador(rs.getString("digitoverif"));
                emp.setEstado(rs.getInt("estado"));
                emp.setNombre(rs.getString("nombree"));
                emp.setRunEmpleado(rs.getString("runempleado"));
                emp.setSexo(rs.getInt("sexoe"));
                emp.setCorreo(rs.getString("correoemple"));
                emp.setIDCARGO(rs.getInt("IDCARGO"));
                direc.setComuna(rs.getString("comuna"));
                direc.setNumero(rs.getString("numero"));
                direc.setNumeroDepto(rs.getString("numerodepto"));
                direc.setPasaje(rs.getString("pasaje"));
                direc.setRegion(rs.getInt("region"));
                suc.setNombre(rs.getString("NOMBRES"));
                suc.setNumFono(rs.getString("telefono"));
                emp.setSucursal(suc);
                emp.setDireccion(direc);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
        }
        return emp;
    }

    public Empleado BuscarEmpleadoPorMail(String mail) {

        try {
            query = "{select * from empleado where correoemple =? }";
            con = Conexion.getConexion();

            call = con.prepareCall(query);
//            call.registerOutParameter(1, OracleTypes.CURSOR);
            call.setString(2, mail);
            call.execute();

            rs = (ResultSet) call.getObject(1);

            while (rs.next()) {
                emp.setApellido(rs.getString("apellido"));
                emp.setContrasena("contrasena");
                emp.setDigitoVerificador(rs.getString("digitoverif"));
                emp.setEstado(rs.getInt("estado"));
                emp.setNombre(rs.getString("nombree"));
                emp.setRunEmpleado(rs.getString("runempleado"));
                emp.setSexo(rs.getInt("sexoe"));
                emp.setCorreo(rs.getString("correoemple"));
                emp.setIDCARGO(rs.getInt("IDCARGO"));
                direc.setComuna(rs.getString("comuna"));
                direc.setNumero(rs.getString("numero"));
                direc.setNumeroDepto(rs.getString("numerodepto"));
                direc.setPasaje(rs.getString("pasaje"));
                direc.setRegion(rs.getInt("region"));
                suc.setNombre(rs.getString("NOMBRES"));
                suc.setNumFono(rs.getString("telefono"));
                emp.setSucursal(suc);
                emp.setDireccion(direc);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
        }
        return emp;
    }

    public boolean habilitar(String rut) {
        try {
            query = "{ call HABILITAR_EMPLEADO(?)}";
            con = Conexion.getConexion();
            call = con.prepareCall(query);
            call.setString(1, rut);
            call.execute();
        } catch (SQLException e) {
            System.out.println("error al Habilitar" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean desabilitar(String rut, String dv) {
        try {
            query = "{ call DESHABILITAR_EMPLEADO(?)}";
            con = Conexion.getConexion();
            call = con.prepareCall(query);
            call.setString(1, rut);
            call.execute();
        } catch (SQLException e) {
            System.out.println("error al eliminar" + e.getMessage());
        }
        return true;
    }

    public Empleado logIn(String user, String pass) {

        try {
            Codificador cod = new Codificador();
            String has = cod.sha256(pass);
            query = "select * from empleado where correoemple=? and contrasena=?";
            con = Conexion.getConexion();
            call = con.prepareCall(query);
            call.setString(1, user);
            call.setString(2, has);
            rs = call.executeQuery();

            while (rs.next()) {
                emp.setApellido(rs.getString("apellido"));
                emp.setContrasena("contrasena");
                emp.setDigitoVerificador(rs.getString("digitoverif"));
                emp.setEstado(rs.getInt("estado"));
                emp.setNombre(rs.getString("nombree"));
                emp.setRunEmpleado(rs.getString("runempleado"));
                emp.setSexo(rs.getInt("sexoe"));
                emp.setCorreo(rs.getString("correoemple"));
                emp.setIDCARGO(rs.getInt("IDCARGO"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
        }
        return emp;
    }

     public boolean  cambiarPass(String rut,String pass){
        
        try {
            Codificador cod =new Codificador();
            String has=cod.sha256(pass);
            query = "update empleado set contrasena = ? where runempleado = ?";
            con = Conexion.getConexion();            
            call = con.prepareCall(query);
            call.setString(1, has);
            call.setString(2, rut);          
            rs = call.executeQuery();

        } catch (SQLException e) {
            System.out.println("Error al buscar" + e.getMessage());
            return false;
        }
        return true;
    }


    public boolean agregarEmpleado() {
        try {
            query = "{call AGREGAR_EMPLEADO(?,?,?,?,?,?,?,?,?,?,?)}";
            con = Conexion.getConexion();
            
            call = con.prepareCall(query);
            call.setInt(1, emp.getIDCARGO());
            call.setObject(2, emp.getDireccion());
            call.setObject(3, emp.getSucursal());
            call.setInt(4, emp.getEstado());
            call.setString(5, emp.getRunEmpleado());
            call.setString(6, emp.getDigitoVerificador());
            call.setInt(7, emp.getSexo());
            call.setString(8, emp.getNombre());
            call.setString(9, emp.getApellido());
            call.setString(10, emp.getCorreo());
            call.setString(11, emp.getContrasena());
            call.executeUpdate();



        } catch (SQLException e) {
            System.out.println("Error al agregar " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public boolean ModificarE(String nombre,String apellido,String correo,String contra ,String run) throws SQLException{          
        try{
            query = "update empleado set nombree = ? ,apellido=?,correoemple=?,contrasena=?  where runempleado = ?";
            Codificador cod =new Codificador();
            String has=cod.sha256(contra);
            con = Conexion.getConexion();            
            call = con.prepareCall(query);
            call.setString(1, nombre);
            call.setString(2, apellido);
            call.setString(3, correo);
            call.setString(4, has);
            call.setString(5, run);          
            rs = call.executeQuery();
        } catch (SQLException e) {
            System.out.println("error al modificar un empleado"+e.getMessage());
            return false;
        }
        return true;
    }

}
