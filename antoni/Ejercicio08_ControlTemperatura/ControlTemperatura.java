import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 8 - Control de temperatura
 * Archivo: temperaturas.txt (una temperatura por línea)
 * Guarda nuevas temperaturas, lee los registros, los convierte a double,
 * calcula promedio, máxima y cuántos superan 30 grados. Controla datos no numéricos.
 */
public class ControlTemperatura {

    static final String ARCHIVO = "temperaturas.txt";
    static final double LIMITE = 30.0;
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== ESTACION METEOROLOGICA =====");
            System.out.println("1. Registrar temperatura");
            System.out.println("2. Ver registros y estadisticas");
            System.out.println("3. Salir");
            System.out.print("Opcion: ");
            opcion = leerOpcion();
            switch (opcion) {
                case 1: guardarTemperatura(); break;
                case 2: analizarTemperaturas(); break;
                case 3: System.out.println("Fin del programa"); break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 3);
    }

    static int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // antes de guardar se comprueba que lo ingresado sea un numero
    static void guardarTemperatura() {
        System.out.print("Temperatura en grados C: ");
        String texto = sc.nextLine().trim().replace(",", ".");   // acepta 25,5 o 25.5
        try {
            double t = Double.parseDouble(texto);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
                bw.write(String.valueOf(t));
                bw.newLine();
                System.out.println("Temperatura guardada");
            }
        } catch (NumberFormatException e) {
            System.out.println("Eso no es una temperatura valida: " + texto);
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void analizarTemperaturas() {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("No hay registros todavia");
            return;
        }

        double suma = 0;
        double maxima = Double.NEGATIVE_INFINITY;   // valor mas pequeno posible
        int validas = 0;
        int mayores30 = 0;
        int numLinea = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            System.out.println("Registros:");
            while ((linea = br.readLine()) != null) {
                numLinea++;
                try {
                    double t = Double.parseDouble(linea.trim());
                    validas++;
                    suma += t;
                    if (t > maxima) maxima = t;
                    if (t > LIMITE) mayores30++;
                    System.out.println("  Dia " + numLinea + ": " + t + " C");
                } catch (NumberFormatException e) {
                    System.out.println("  Dia " + numLinea + ": dato invalido \"" + linea + "\" (se omite)");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }

        if (validas == 0) {
            System.out.println("No hay temperaturas validas");
            return;
        }
        System.out.println("--------------------------------------------");
        System.out.printf("Promedio: %.2f C%n", suma / validas);
        System.out.println("Temperatura mas alta: " + maxima + " C");
        System.out.println("Registros mayores a " + LIMITE + " C: " + mayores30);
    }
}
