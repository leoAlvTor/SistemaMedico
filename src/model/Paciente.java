package model;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Paciente {

    private int numeroFicha;
    private String cedula;
    private String nombres;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String celular;
    private String estadoCivil;
    private String procedencia;
    private String residencia;
    private String fechaNacimiento;
    private String genero;
    private String antecedentes;
    private double peso;
    private double talla;
    private String grupoSanguineo;

    private List<Cita> citas;

    public Paciente(){
        fechaNacimiento = new String();
        citas = new ArrayList<>();
    }

    public int getNumeroFicha() {
        return numeroFicha;
    }

    public void setNumeroFicha(int numeroFicha) {
        this.numeroFicha = numeroFicha;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombresApellidos(){
        return this.nombres + " " + this.apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public void setProcedencia(String procedencia) {
        this.procedencia = procedencia;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getFechaNacimiento() {

        return fechaNacimiento;
    }

    public Date getFechaNacimientoAsDate(){
        if(this.fechaNacimiento.contains(" "))
            this.fechaNacimiento = this.fechaNacimiento.split(" ")[0];
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(this.fechaNacimiento);
            return simpleDateFormat.parse(this.fechaNacimiento);
        }catch (Exception e){
            System.out.println("ERROR: Error while parsing date: " + this.fechaNacimiento + ", ERROR: " + e.getMessage());
            return new Date();
        }
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setFechaFromLocal(LocalDate local){
        this.fechaNacimiento = local.getYear() + "-" + local.getMonthValue() + "-" + local.getDayOfMonth();
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public boolean addCita(Cita cita){
        return citas.add(cita);
    }

    public Object[] toList(){
        List<Object> objectList = new ArrayList<>();

        objectList.add(cedula);
        objectList.add(nombres);
        objectList.add(apellidos);
        objectList.add(direccion);
        objectList.add(telefono);
        objectList.add(celular);
        objectList.add(estadoCivil);
        objectList.add(procedencia);
        objectList.add(residencia);
        objectList.add(fechaNacimiento);
        objectList.add(genero);
        objectList.add(antecedentes);
        objectList.add(peso);
        objectList.add(talla);
        objectList.add(grupoSanguineo);

        return objectList.toArray();
    }

    @Deprecated(since = "1.0 Replaced with Apache Commons DBUtils")
    public Paciente resultSetToPaciente(ResultSet resultSet) throws Exception{
        this.numeroFicha = resultSet.getInt(1);
        this.cedula = resultSet.getString(2);
        this.nombres = resultSet.getString(3);
        this.apellidos = resultSet.getString(4);
        this.direccion = resultSet.getString(5);
        this.telefono = resultSet.getString(6);
        this.celular = resultSet.getString(7);
        this.estadoCivil = resultSet.getString(8);
        this.procedencia = resultSet.getString(9);
        this.residencia = resultSet.getString(10);
        this.setFechaNacimiento(resultSet.getString(11));
        this.genero = resultSet.getString(12);
        this.antecedentes = resultSet.getString(13);
        this.peso = Double.parseDouble(resultSet.getString(14));
        this.talla = Double.parseDouble(resultSet.getString(15));
        this.grupoSanguineo = resultSet.getString(16);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paciente paciente = (Paciente) o;
        return numeroFicha == paciente.numeroFicha && Double.compare(paciente.peso, peso) == 0 && Double.compare(paciente.talla, talla) == 0 && cedula.equals(paciente.cedula) && nombres.equals(paciente.nombres) && apellidos.equals(paciente.apellidos) && Objects.equals(direccion, paciente.direccion) && Objects.equals(telefono, paciente.telefono) && Objects.equals(celular, paciente.celular) && Objects.equals(estadoCivil, paciente.estadoCivil) && Objects.equals(procedencia, paciente.procedencia) && Objects.equals(residencia, paciente.residencia) && Objects.equals(fechaNacimiento, paciente.fechaNacimiento) && Objects.equals(genero, paciente.genero) && antecedentes.equals(paciente.antecedentes) && Objects.equals(grupoSanguineo, paciente.grupoSanguineo) && Objects.equals(citas, paciente.citas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroFicha, cedula, nombres, apellidos, direccion, telefono, celular, estadoCivil, procedencia, residencia, fechaNacimiento, genero, antecedentes, peso, talla, grupoSanguineo, citas);
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "numeroFicha=" + numeroFicha +
                ", cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", celular='" + celular + '\'' +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", procedencia='" + procedencia + '\'' +
                ", residencia='" + residencia + '\'' +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", genero='" + genero + '\'' +
                ", antecedentes='" + antecedentes + '\'' +
                ", peso=" + peso +
                ", talla=" + talla +
                ", grupoSanguineo='" + grupoSanguineo + '\'' +
                '}';
    }
}
