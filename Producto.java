package pe.edu.upeu;


public class Producto {

    private String codigo;
    String nombre;
    double precio;
    int stock;
    private double cantidad;
    double igv=getIgv();


    public Producto(String codigo, String nombre, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }


    public Producto(String codigo, String nombre, double precio) {
        this(codigo, nombre, precio, 0);
    }

    //Metodos
    public double getIgv() {

        if (this.precio<0)
            throw new IllegalArgumentException();

        return precio*0.18;


    }
}
