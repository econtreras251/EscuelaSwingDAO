package uy.com.morelio.escuela.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DAO<T, K> {
    
    T convertir(ResultSet rs) throws SQLException;
    
    void insertar(T a) throws DAOException;
    
    void eliminar(T a) throws DAOException;
    
    void modificar(T a) throws DAOException;
    
    List<T> obtenerTodos() throws DAOException;
    
    T obtener(K id) throws DAOException;
    
}
