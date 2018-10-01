package uy.com.morelio.escuela.modelo;

public class Matricula {
    
    public class IdMatricula {
        
        private long alumno;
    
        private long asignatura;
        
        private int year;
        
        public IdMatricula(long alumno, long asignatura, int year) {
            this.alumno = alumno;
            this.asignatura = asignatura;
            this.year = year;
        }

        public long getAlumno() {
            return alumno;
        }

        public void setAlumno(long alumno) {
            this.alumno = alumno;
        }

        public long getAsignatura() {
            return asignatura;
        }

        public void setAsignatura(long asignatura) {
            this.asignatura = asignatura;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

    }
    
    private Integer nota = null;
    
    private IdMatricula id;

    public Matricula(long alumno, long asignatura, int year) {
        this.id = new IdMatricula(alumno, asignatura, year);
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public IdMatricula getId() {
        return id;
    }

    public void setId(IdMatricula id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Matricula{" + "nota=" + nota + ", id=" + id + '}';
    }

   
    
}
