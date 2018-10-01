package uy.com.morelio.escuela.dao.msql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uy.com.morelio.escuela.dao.DAOException;

class MySQLUtils {
    
    static void cerrar(PreparedStatement stat) throws DAOException{
        if(stat != null){
            try{
                stat.close();
            } catch (SQLException ex){
                throw new DAOException("Error al cerrar el recurso SQL",ex);
            }
        }
    }
    
    static void cerrar(PreparedStatement stat, ResultSet rs) throws DAOException{
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException ex) {
                new DAOException("Error al cerrar el recurso SQL", ex);
             }
        }
        if(stat != null){
            try{
                stat.close();
            } catch (SQLException ex){
                throw new DAOException("Error al cerrar el recurso SQL",ex);
            }
        }
    
    }
    
    
}
