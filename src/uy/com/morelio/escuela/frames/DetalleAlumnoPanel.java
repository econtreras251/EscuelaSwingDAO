package uy.com.morelio.escuela.frames;

import java.text.ParseException;
import java.util.Date;
import uy.com.morelio.escuela.modelo.Alumno;

public class DetalleAlumnoPanel extends javax.swing.JPanel {
    
    private Alumno alumno;
    
    private boolean editable;

    public DetalleAlumnoPanel() {
        initComponents();
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        nombre.setEditable(editable);
        apellidos.setEditable(editable);
        fechanac.setEditable(editable);
    }
    
    public void loadData(){
        if(alumno != null){
            apellidos.setText(alumno.getApellidos());
            nombre.setText(alumno.getNombre());
            fechanac.setValue(alumno.getFechaNacimiento());
        } else {
            apellidos.setText("");
            nombre.setText("");
            fechanac.setValue(null);
        }
        apellidos.requestFocus();
    }
    
    public void saveData() throws ParseException{
        if(alumno == null){
            alumno = new Alumno();
        }
        alumno.setApellidos(apellidos.getText());
        alumno.setNombre(nombre.getText());
        fechanac.commitEdit();
        alumno.setFechaNacimiento((Date) fechanac.getValue());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        apellidos = new javax.swing.JTextField();
        nombre = new javax.swing.JTextField();
        fechanac = new javax.swing.JFormattedTextField();

        jLabel1.setText("Apellidos:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Fecha de Nacimiento:");

        fechanac.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/yyyy"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(nombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addComponent(fechanac, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(apellidos))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fechanac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField apellidos;
    private javax.swing.JFormattedTextField fechanac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField nombre;
    // End of variables declaration//GEN-END:variables
}
