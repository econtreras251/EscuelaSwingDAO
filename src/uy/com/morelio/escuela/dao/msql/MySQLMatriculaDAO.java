package uy.com.morelio.escuela.dao.msql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import uy.com.morelio.escuela.dao.DAOException;
import uy.com.morelio.escuela.dao.MatriculaDAO;
import uy.com.morelio.escuela.modelo.Matricula;

public class MySQLMatriculaDAO implements MatriculaDAO{
    
    // alumno, asignatura, fecha, nota
    
    final String INSERT = "INSERT INTO matriculas(alumno, asignatura, fecha, nota) VALUES( ?, ?, ?)";
    final String UPDATE = "UPDATE matriculas SET nota = ? WHERE alumno = ? AND asignatura = ? AND fecha = ?";
    final String DELETE = "DELETE FROM matriculas WHERE alumno = ? AND asignatura = ? AND fecha = ?";
    final String GETALL = "SELECT alumno, asignatura, fecha, nota FROM matriculas";
    final String GETONE = GETALL + " WHERE alumno = ? AND asignatura = ? AND fecha = ?";
    final String GETALU = GETALL + " WHERE alumno = ?";
    final String GETCUR = GETALL + " WHERE fecha = ?";
    final String GETASI = GETALL + " WHERE asignatura = ?";
    
    private Connection conn;
    
    public MySQLMatriculaDAO(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public void insertar(Matricula m) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(INSERT);
            stat.setLong(1, m.getId().getAlumno());
            stat.setLong(2, m.getId().getAsignatura());
            stat.setInt(3, m.getId().getYear());
            if(m.getNota() != null){
                stat.setInt(4, m.getNota());
            } else {
                stat.setNull(4, Types.INTEGER);
            }
            
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
    public void eliminar(Matricula m) throws DAOException {
        PreparedStatement stat = null;
        try{
            stat = conn.prepareStatement(DELETE);
            stat.setLong(1, m.getId().getAlumno());
            stat.setLong(2, m.getId().getAsignatura());
            stat.setInt(3, m.getId().getYear());
            
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
    public void modificar(Matricula m) throws DAOException {
        PreparedStatement stat = null;
        
        try{
            stat = conn.prepareStatement(UPDATE);
            stat.setInt(1, m.getNota());
            stat.setLong(2, m.getId().getAlumno());
            stat.setLong(3, m.getId().getAsignatura());
            stat.setInt(4, m.getId().getYear());
            
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
    public List<Matricula> obtenerTodos() throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Matricula> matriculas = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALL);
            rs = stat.executeQuery();
            
            while(rs.next()){
                matriculas.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { //Buscar reutilizar esta pieza de codigo.
            MySQLUtils.cerrar(stat, rs);
        }
        
        return matriculas;
    }
    
    @Override
    public Matricula obtener(Matricula.IdMatricula id) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        Matricula m = null;
        try {
            stat =  conn.prepareStatement(GETONE);
            stat.setLong(1, id.getAlumno());
            stat.setLong(2, id.getAsignatura());
            stat.setInt(3, id.getYear());
            rs = stat.executeQuery();
            
            if(rs.next()){
                m = convertir(rs);
            }else{
                throw new DAOException("No se ha encontrado ese registro");
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally {
            MySQLUtils.cerrar(stat, rs);
        }
        
        return m;
    }

    @Override
    public Matricula convertir(ResultSet rs) throws SQLException {
        //Extraccion
        long alumno = rs.getLong("alumno");
        long asignatura = rs.getLong("asignatura");
        int year = rs.getInt("fecha");
        
        //Verificar distincion de Null
        Integer nota = rs.getInt("nota");
        if(rs.wasNull()) nota = null;
        
        //Matricula
        Matricula mat = new Matricula(alumno, asignatura, year);
        mat.setNota(nota);
        return mat;
    }

    @Override
    public List<Matricula> obtenerPorAlumno(long alumno) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Matricula> matriculas = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALU);
            stat.setLong(1, alumno);
            rs = stat.executeQuery();
            
            while(rs.next()){
                matriculas.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { //Buscar reutilizar esta pieza de codigo.
            MySQLUtils.cerrar(stat, rs);
        }
        
        return matriculas;
    }

    @Override
    public List<Matricula> obtenerPorAsignatura(long asignatura) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Matricula> matriculas = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALU);
            stat.setLong(1, asignatura);
            rs = stat.executeQuery();
            
            while(rs.next()){
                matriculas.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { //Buscar reutilizar esta pieza de codigo.
            MySQLUtils.cerrar(stat, rs);
        }
        
        return matriculas;
    }

    @Override
    public List<Matricula> obtenerPorCurso(int curso) throws DAOException {
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<Matricula> matriculas = new ArrayList<>();
        try {
            stat =  conn.prepareStatement(GETALU);
            stat.setInt(1, curso);
            rs = stat.executeQuery();
            
            while(rs.next()){
                matriculas.add(convertir(rs));
            }
            
        } catch(SQLException ex){
            throw new DAOException("Error en SQL",ex);
        } finally { //Buscar reutilizar esta pieza de codigo.
            MySQLUtils.cerrar(stat, rs);
        }
        
        return matriculas;
    }

    
}
