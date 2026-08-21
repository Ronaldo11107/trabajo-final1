package pe.edu.upeu;

public class Coche {
    String marca;
    int velocidad;

    Coche(String marca, int velocidadInicial) {
        this.marca = marca;
        this.velocidad = velocidadInicial;
    }

    Coche(String marca) {
        this(marca, 0);
    }
}
