import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Bus {
    private String placa;
    private int totalAsientos;
    private boolean[] asientos;

    public Bus(String placa, int totalAsientos) {
        this.placa = placa;
        this.totalAsientos = totalAsientos;
        this.asientos = new boolean[totalAsientos];
    }

    public String obtenerPlaca() { return placa; }
    public int obtenerTotalAsientos() { return totalAsientos; }

    public boolean estaOcupado(int numeroAsiento) {
        return asientos[numeroAsiento - 1];
    }

    public void ocuparAsiento(int numeroAsiento) {
        asientos[numeroAsiento - 1] = true;
    }

    public void mostrarMapaAsientos() {
        System.out.println("\n=== MAPA DE ASIENTOS (Bus: " + placa + ") ===");
        for (int i = 1; i <= totalAsientos; i++) {
            if (asientos[i - 1]) {
                System.out.printf("[%02d:X] ", i);
            } else {
                System.out.printf("[%02d:L] ", i);
            }

            if (i % 4 == 0) System.out.println();
        }
        System.out.println("\n(X = Ocupado, L = Disponible)");
    }
}

class Ruta {
    private String origen;
    private String destino;
    private String horaSalida;
    private Bus busAsignado;
    private double precioPasaje;

    public Ruta(String origen, String destino, String horaSalida, Bus busAsignado, double precioPasaje) {
        this.origen = origen;
        this.destino = destino;
        this.horaSalida = horaSalida;
        this.busAsignado = busAsignado;
        this.precioPasaje = precioPasaje;
    }

    public String obtenerOrigen() { return origen; }
    public String obtenerDestino() { return destino; }
    public String obtenerHoraSalida() { return horaSalida; }
    public Bus obtenerBusAsignado() { return busAsignado; }
    public double obtenerPrecioPasaje() { return precioPasaje; }
}

class Boleto {
    private int numeroFactura;
    private String codigoControl;
    private String nombrePasajero;
    private String documentoPasajero;
    private Ruta ruta;
    private int asiento;

    public Boleto(int numeroFactura, String codigoControl, String nombrePasajero, String documentoPasajero, Ruta ruta, int asiento) {
        this.numeroFactura = numeroFactura;
        this.codigoControl = codigoControl;
        this.nombrePasajero = nombrePasajero;
        this.documentoPasajero = documentoPasajero;
        this.ruta = ruta;
        this.asiento = asiento;
    }

    public int obtenerNumeroFactura() { return numeroFactura; }
    public String obtenerNombrePasajero() { return nombrePasajero; }

    public void mostrarEnConsola() {
        System.out.println(generarTextoBoleto());
    }

    public void registrarComprobante() {
        String nombreArchivo = "boleto_" + numeroFactura + ".txt";

        try (FileWriter escritor = new FileWriter(nombreArchivo)) {
            escritor.write(generarTextoBoleto());
            System.out.println("-> Comprobante '" + nombreArchivo + "' procesado.");
        } catch (IOException e) {
            System.out.println("Error al procesar el documento.");
        }
    }

    private String generarTextoBoleto() {
        StringBuilder boleto = new StringBuilder();

        boleto.append("\n==================================================\n");
        boleto.append("               BOLETO DE TRANSPORTE              \n");
        boleto.append("==================================================\n");
        boleto.append("Factura N°: ").append(numeroFactura).append("      | Código Control: ").append(codigoControl).append("\n");
        boleto.append("Origen: ").append(ruta.obtenerOrigen()).append(" ---> Destino: ").append(ruta.obtenerDestino()).append("\n");
        boleto.append("Salida: ").append(ruta.obtenerHoraSalida()).append(" | Bus Placa: ").append(ruta.obtenerBusAsignado().obtenerPlaca()).append("\n");
        boleto.append("--------------------------------------------------\n");
        boleto.append("Pasajero: ").append(nombrePasajero).append("\n");
        boleto.append("Doc. Identidad: ").append(documentoPasajero).append("\n");
        boleto.append("Asiento Asignado: N° ").append(asiento).append("\n");
        boleto.append("--------------------------------------------------\n");
        boleto.append("TOTAL PAGADO: $").append(ruta.obtenerPrecioPasaje()).append("\n");
        boleto.append("==================================================\n");
        boleto.append("       * Gracias por su preferencia * \n");
        boleto.append("==================================================\n");

        return boleto.toString();
    }
}

public class transporte {
    private static ArrayList<Ruta> rutas = new ArrayList<>();
    private static ArrayList<Boleto> boletosEmitidos = new ArrayList<>();
    private static Scanner teclado = new Scanner(System.in);
    private static int contadorFactura = 1001;

    public static void main(String[] args) {
        precargarDatos();
        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("  SISTEMA DE VENTAS Y FACTURACIÓN - VÍAS");
            System.out.println("========================================");
            System.out.println("1. Ver Rutas y Horarios Disponibles");
            System.out.println("2. Vender Pasaje / Ver Asientos");
            System.out.println("3. Generar/Ver Boletos Guardados");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    mostrarRutas();
                    break;
                case 2:
                    procesarVentaPasaje();
                    break;
                case 3:
                    mostrarEImprimirBoletos();
                    break;
                case 4:
                    System.out.println("Saliendo del sistema... ¡Buen viaje!");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private static void precargarDatos() {
        Bus bus1 = new Bus("2344-ABC", 20);
        Bus bus2 = new Bus("5588-XYZ", 40);

        rutas.add(new Ruta("UPeU", "centro comercial", "12:40 p.m", bus1, 1));
        rutas.add(new Ruta("UPeU", "centro comercial", "07:00 p.m", bus2, 1));
    }

    private static void mostrarRutas() {
        System.out.println("\n--- RUTAS DISPONIBLES ---");
        for (int i = 0; i < rutas.size(); i++) {
            Ruta r = rutas.get(i);
            System.out.println((i + 1) + ". " + r.obtenerOrigen() + " a " + r.obtenerDestino() +
                    " | Salida: " + r.obtenerHoraSalida() +
                    " | Bus: " + r.obtenerBusAsignado().obtenerPlaca() +
                    " | Precio: $" + r.obtenerPrecioPasaje());
        }
    }

    private static void procesarVentaPasaje() {
        mostrarRutas();
        System.out.print("\nSeleccione el número de ruta: ");
        int indiceRuta = teclado.nextInt() - 1;

        if (indiceRuta < 0 || indiceRuta >= rutas.size()) {
            System.out.println("Ruta inválida.");
            return;
        }

        Ruta rutaSeleccionada = rutas.get(indiceRuta);
        Bus bus = rutaSeleccionada.obtenerBusAsignado();

        bus.mostrarMapaAsientos();

        System.out.print("\nIngrese el número de asiento que desea comprar: ");
        int numAsiento = teclado.nextInt();
        teclado.nextLine();

        if (numAsiento < 1 || numAsiento > bus.obtenerTotalAsientos()) {
            System.out.println("El número de asiento no existe en este bus.");
            return;
        }

        if (bus.estaOcupado(numAsiento)) {
            System.out.println("Lo sentimos, el asiento " + numAsiento + " ya está ocupado.");
            return;
        }

        System.out.print("Ingrese Nombre del Pasajero: ");
        String nombre = teclado.nextLine();
        System.out.print("Ingrese C.I. / DNI del Pasajero: ");
        String documento = teclado.nextLine();

        bus.ocuparAsiento(numAsiento);

        String codigoControl = "CONTR-" + (int)(Math.random() * 9000 + 1000);

        Boleto nuevoBoleto = new Boleto(contadorFactura++, codigoControl, nombre, documento, rutaSeleccionada, numAsiento);
        boletosEmitidos.add(nuevoBoleto);

        System.out.println("\n¡Venta registrada!");
        nuevoBoleto.mostrarEnConsola();
        nuevoBoleto.registrarComprobante();
    }

    private static void mostrarEImprimirBoletos() {
        if (boletosEmitidos.isEmpty()) {
            System.out.println("\nNo hay boletos registrados en el sistema todavía.");
            return;
        }

        System.out.println("\n--- HISTORIAL DE BOLETOS EMITIDOS ---");
        for (int i = 0; i < boletosEmitidos.size(); i++) {
            Boleto b = boletosEmitidos.get(i);
            System.out.println((i + 1) + ". Factura N° " + b.obtenerNumeroFactura() + " | Pasajero: " + b.obtenerNombrePasajero());
        }

        System.out.print("\nSeleccione el número del boleto para volver a generar su archivo (0 para cancelar): ");
        int seleccion = teclado.nextInt();
        teclado.nextLine();

        if (seleccion == 0) {
            return;
        }

        if (seleccion < 1 || seleccion > boletosEmitidos.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Boleto boletoSeleccionado = boletosEmitidos.get(seleccion - 1);
        System.out.println("\nReescribiendo archivo de texto...");
        boletoSeleccionado.mostrarEnConsola();
        boletoSeleccionado.registrarComprobante();
    }
}