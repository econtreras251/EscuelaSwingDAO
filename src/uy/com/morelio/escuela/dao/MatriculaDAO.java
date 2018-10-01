package uy.com.morelio.escuela.dao;

import java.util.List;
import uy.com.morelio.escuela.modelo.Matricula;

public interface MatriculaDAO extends DAO<Matricula, Matricula.IdMatricula>{
    
    List<Matricula> obtenerPorAlumno(long alumno) throws DAOException;
    
    List<Matricula> obtenerPorAsignatura(long asignatura) throws DAOException;
    
    List<Matricula> obtenerPorCurso(int curso) throws DAOException;
}
