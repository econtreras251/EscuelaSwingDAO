package uy.com.morelio.escuela.dao.msql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uy.com.morelio.escuela.dao.AsignaturaDAO;
import uy.com.morelio.escuela.dao.DAOException;
import uy.com.morelio.escuela.modelo.Asignatura;

public class MySQLAsignaturaDAO implements AsignaturaDAO {
    
    final String INSERT = "INSERT INTO asignaturas(nombre, profesor) VALUES( ?, ?)";
    final String UPDATE = "UPDATE asignaturas SET nombre = ?, profesor = ? WHERE id_asignatura = ?";
    final String DELETE = "DELETE FROM asignaturas WHERE id_asignatura = ?";
    final String GETALL = "SELECT id_asignatura, nombre, profesor FROM asignaturas";
    final String GETONE = GETALL + " WHERE id_asignatura = ?";
    
    private Connection conn;
    
    public MySQLAsignaturaDAO(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insertar(Asignatura a) throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setString(1, a.getNombre());
            stat.setLong(2, a.getIdProfesor());
            
            if(stat.executeUpdate() == 0){
                throw new DAOException("Puede que no se haya guardado");
            }
            
            rs = stat.getGeneratedKeys();
            if(rs.next()){
                a.setId(rs.getLong(1));
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
    public void eliminar(Asignatura a) throws DAOException{
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
    public void modificar(Asignatura a) throws DAOException{
        PreparedStatement stat = null;
        
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setString(1, a.getNombre());
            stat.setLong(2, a.getIdProfesor());
            stat.setLong(3, a.getId());
            
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
    public List<Asignatura> obtenerTodos() throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Asignatura> asignaturas = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            
            while(rs.next()){
                asignaturas.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { //Buscar reutilizar esta pieza de codigo.
            MySQLUtils.cerrar(stat, rs);
        }
        
        return asignaturas;
    }

    @Override
    public Asignatura obtener(Long id) throws DAOException{
        PreparedStatement stat = null;
        ResultSet rs = null;
        Asignatura a = null;
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

    @Override
    public Asignatura convertir(ResultSet rs) throws SQLException {
        String nombre = rs.getString("nombre");
        long profesor = rs.getLong("profesor");
        long id = rs.getLong("id_asignatura");
        Asignatura asignatura = new Asignatura(nombre, profesor);
        asignatura.setId(id);
        return asignatura;
    }

    
    
}
