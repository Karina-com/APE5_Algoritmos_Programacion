import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 7 - Competencia de videojuegos
 * Archivo: jugadores.txt  (cada línea: nombre,puntuacion)
 * Lee los registros, convierte las puntuaciones a número, calcula el promedio,
 * encuentra al mejor jugador, filtra a los que superan 70 y valida el formato.
 */
public class CompetenciaVideojuegos {

    static final String ARCHIVO = "jugadores.txt";
    static final int PUNTAJE_FILTRO = 70;

    // guardamos los datos validos en arreglos para poder filtrarlos despues
    static String[] nombres = new String[100];
    static int[] puntajes = new int[100];
    static int cantidad = 0;

    public static void main(String[] args) {
        System.out.println("=== COMPETENCIA DE VIDEOJUEGOS ===");
        if (!new File(ARCHIVO).exists()) {
            System.out.println("No existe " + ARCHIVO);
            return;
        }

        cargarJugadores();
        if (cantidad == 0) {
            System.out.println("No hay registros validos para procesar");
            return;
        }
        mostrarEstadisticas();
        filtrarMayoresA(PUNTAJE_FILTRO);
    }

    static void cargarJugadores() {
        int numLinea = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                numLinea++;
                if (registroValido(linea, numLinea)) {
                    String[] p = linea.split(",");
                    nombres[cantidad] = p[0].trim();
                    puntajes[cantidad] = Integer.parseInt(p[1].trim());
                    System.out.println("  " + nombres[cantidad] + " -> " + puntajes[cantidad]);
                    cantidad++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    // valida que haya nombre y puntaje, y que el puntaje sea un numero entre 0 y 100
    static boolean registroValido(String linea, int numLinea) {
        String[] p = linea.split(",");
        if (p.length != 2 || p[0].trim().isEmpty()) {
            System.out.println("  [Linea " + numLinea + "] formato incorrecto: \"" + linea + "\"");
            return false;
        }
        try {
            int valor = Integer.parseInt(p[1].trim());
            if (valor < 0 || valor > 100) {
                System.out.println("  [Linea " + numLinea + "] puntaje fuera de rango: " + valor);
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("  [Linea " + numLinea + "] la puntuacion no es numero: \"" + p[1].trim() + "\"");
            return false;
        }
        return true;
    }

    static void mostrarEstadisticas() {
        int suma = 0;
        int posMayor = 0;
        for (int i = 0; i < cantidad; i++) {
            suma += puntajes[i];
            if (puntajes[i] > puntajes[posMayor]) {
                posMayor = i;
            }
        }
        double promedio = (double) suma / cantidad;
        System.out.println("--------------------------------------------");
        System.out.println("Jugadores validos: " + cantidad);
        System.out.printf("Promedio de puntuacion: %.2f%n", promedio);
        System.out.println("Mejor jugador: " + nombres[posMayor] + " con " + puntajes[posMayor] + " puntos");
    }

    static void filtrarMayoresA(int limite) {
        System.out.println("Jugadores con mas de " + limite + " puntos:");
        for (int i = 0; i < cantidad; i++) {
            if (puntajes[i] > limite) {
                System.out.println("  * " + nombres[i] + " (" + puntajes[i] + ")");
            }
        }
    }
}
