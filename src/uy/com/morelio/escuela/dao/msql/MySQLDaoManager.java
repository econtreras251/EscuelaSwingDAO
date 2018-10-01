package uy.com.morelio.escuela.dao.msql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import uy.com.morelio.escuela.dao.AlumnoDAO;
import uy.com.morelio.escuela.dao.AsignaturaDAO;
import uy.com.morelio.escuela.dao.DAOManager;
import uy.com.morelio.escuela.dao.MatriculaDAO;
import uy.com.morelio.escuela.dao.ProfesorDAO;

public class MySQLDaoManager implements DAOManager{
    
    private Connection conn;
    
    private AlumnoDAO alumnos = null;
    private AsignaturaDAO asignaturas = null;
    private ProfesorDAO profesores = null;
    private MatriculaDAO matriculas = null;
    
    public MySQLDaoManager(String host, String username, String password, String database) throws SQLException{
        conn = DriverManager.getConnection("jdbc:mysql://"+ host +":3306/"+ database , username ,password);
    }

    @Override
    public AlumnoDAO getAlumnoDAO() {
        if(alumnos == null){
            alumnos = new MySQLAlumnoDAO(conn);
        }
        return alumnos;
    }

    @Override
    public AsignaturaDAO getAsignaturaDAO() {
        if(asignaturas == null){
            asignaturas = new MySQLAsignaturaDAO(conn);
        }
        return asignaturas;
    }

    @Override
    public ProfesorDAO getProfesorDAO() {
        if(profesores == null){
            profesores = new MySQLProfesorDAO(conn);
        }
        return profesores;
    }

    @Override
    public MatriculaDAO getMatriculaDAO() {
        if(matriculas == null){
            matriculas = new MySQLMatriculaDAO(conn);
        }
        return matriculas;
    }
    
    
    
}
