package uy.com.morelio.escuela.dao.msql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uy.com.morelio.escuela.dao.DAOException;
import uy.com.morelio.escuela.dao.ProfesorDAO;
import uy.com.morelio.escuela.modelo.Profesor;

public class MySQLProfesorDAO implements ProfesorDAO {
    
    final String INSERT = "INSERT INTO profesores(nombre, apellidos) VALUES( ?, ?)";
    final String UPDATE = "UPDATE profesores SET nombre = ?, apellidos = ?, WHERE id_profesor = ?";
    final String DELETE = "DELETE FROM profesores WHERE id_profesor = ?";
    final String GETALL = "SELECT id_profesor, nombre, apellidos FROM profesores";
    final String GETONE = GETALL + " WHERE id_profesor = ?";
    
    private Connection conn;
    
    public MySQLProfesorDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insertar(Profesor p) throws DAOException {
    
        PreparedStatement stat = null;
        ResultSet rs = null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, p.getNombre());
            stat.setString(2, p.getApellidos());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado");
            }
            
            // Insertamos las claves
            rs = stat.getGeneratedKeys();
            if(rs.next()){
                p.setId(rs.getLong(1));
            } else {
                throw new DAOException("Error al generar clave");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat, rs);
        }
    
    }

    @Override
    public void eliminar(Profesor p) throws DAOException{
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setLong(1, p.getId());
            
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
    public void modificar(Profesor p) throws DAOException{
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, p.getNombre());
            stat.setString(2, p.getApellidos());
            stat.setLong(3, p.getId());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya modificado");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat);
        }
    }

    @Override
    public List<Profesor> obtenerTodos() throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Profesor> profesores = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            
            while(rs.next()){
                profesores.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { 
            MySQLUtils.cerrar(stat, rs);
        }
        
        return profesores;
    }

    @Override
    public Profesor obtener(Long id) throws DAOException{
        
        PreparedStatement stat = null;
        ResultSet rs = null;
        Profesor p = null;
        try {
            stat =  conn.prepareStatement(GETONE);
            stat.setLong(1, id);
            rs = stat.executeQuery();
            
            if(rs.next()){
                p = convertir(rs);
            }else{
                throw new DAOException("No se ha encontrado ese registro");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat, rs);
        }
        
        return p;
    }

    @Override
    public Profesor convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        Profesor profesor = new Profesor(nombre, apellidos);
        profesor.setId(rs.getLong("id_profesor"));
        return profesor;
    }
    
}
