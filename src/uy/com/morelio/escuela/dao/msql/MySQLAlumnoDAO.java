package uy.com.morelio.escuela.dao.msql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uy.com.morelio.escuela.dao.AlumnoDAO;
import uy.com.morelio.escuela.dao.DAOException;
import uy.com.morelio.escuela.modelo.Alumno;

public class MySQLAlumnoDAO implements AlumnoDAO {
    
    //Declaracion de las Consultas preparadas (PreparedStatement)
    
    final String INSERT = "INSERT INTO alumnos(nombre, apellidos, fecha_nac) VALUES( ?, ?, ?)";
    final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE id_alumno = ?";
    final String DELETE = "DELETE FROM alumnos WHERE id_alumno = ?";
    final String GETALL = "SELECT id_alumno, nombre, apellidos, fecha_nac FROM alumnos";
    final String GETONE = "SELECT id_alumno, nombre, apellidos, fecha_nac FROM alumnos WHERE id_alumno = ?";
    
    private Connection conn;
    
    public MySQLAlumnoDAO(Connection conn){
        this.conn = conn;
    }
    
    /*
        Este Metodo recibe un objeto de tipo Alumno, crea una consulta
        con las variables declaradas, asigna los datos a la consulta
        y trata con los errores generales
    */
    @Override
    public void insertar(Alumno a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getApellidos());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
           MySQLUtils.cerrar(stat);
        }
    }

    @Override
    public void eliminar(Alumno a) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setLong(1, a.getId());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya borrado");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat);
        }
    }

    @Override
    public void modificar(Alumno a)throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getNombre());
            stat.setString(2, a.getApellidos());
            stat.setDate(3, new Date(a.getFechaNacimiento().getTime()));
            stat.setLong(4, a.getId());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya modificado");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat);
        }
    }
    
    
    /*
        Este metodo devuelve la lista de los elementos encontrados
        como un List de Alumnos, recorriendo el resultado con 
        e; objeto ResultSet
    */
    @Override
    public List<Alumno> obtenerTodos() throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Alumno> alumnos = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            
            while(rs.next()){
                alumnos.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { 
            MySQLUtils.cerrar(stat, rs);
        }
        
        return alumnos;
    }
    
    
    //Este metodo obtiene un objeto Alumno mediante su ID, de tipo Long.
    @Override
    public Alumno obtener(Long id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Alumno a = null;
        try {
            stat =  conn.prepareStatement(GETONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            
            if(rs.next()){
                a = convertir(rs);
            }else{
                throw new DAOException("No se ha encontrado ese registro");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat, rs);
        }
        
        return a;
    }
    
    //Este metodo transforma el resultado traido de la base de datos 
    // en el tipo de objeto que estamos trabajando
    @Override
    public Alumno convertir(ResultSet rs) throws SQLException{
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        Date fechaNac = rs.getDate("fecha_nac");
        Alumno alumno = new Alumno(nombre, apellidos, fechaNac);
        alumno.setId(rs.getLong("id_alumno"));
        return alumno;
    }
    
}
