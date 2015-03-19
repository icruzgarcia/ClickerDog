package icruzgarcia.com.clickerdog;


public class Mejora {


    private int id;
    private String nombre;
    private double coste;
    private String descripcion;
    private int comprada;
    private int pasivo;


    public Mejora() {
    }

    public Mejora(int id, String nombre, double coste, String descripcion, int comprada, int pasivo) {
        this.id = id;
        this.pasivo = pasivo;
        this.comprada = comprada;
        this.descripcion = descripcion;
        this.coste = coste;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCoste() {
        return coste;
    }

    public void setCoste(double coste) {
        this.coste = coste;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getComprada() {
        return comprada;
    }

    public void setComprada(int comprada) {
        this.comprada = comprada;
    }

    public int getPasivo() {
        return pasivo;
    }

    public void setPasivo(int pasivo) {
        this.pasivo = pasivo;
    }
}
