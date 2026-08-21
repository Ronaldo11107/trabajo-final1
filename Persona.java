package pe.edu.upeu;

public class Persona {
    private System nombre;

    Persona(String nombre) {
        this.nombre = nombre;
    }

    void conducir(Coche coche) {
        System.out.println(nombre + " conduce el coche");
        coche.acelerar();
        coche.frenar();
    }
}