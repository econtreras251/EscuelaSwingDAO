package uy.com.morelio.escuela.dao;

public interface DAOManager {
    
    AlumnoDAO getAlumnoDAO();
    
    AsignaturaDAO getAsignaturaDAO();
    
    ProfesorDAO getProfesorDAO();
    
    MatriculaDAO getMatriculaDAO();
    
}
