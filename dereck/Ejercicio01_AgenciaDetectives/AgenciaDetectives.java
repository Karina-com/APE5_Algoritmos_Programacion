import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 1 - Agencia de detectives: análisis de pistas
 * Lee el archivo pistas.txt línea por línea, muestra cada pista
 * y cuenta cuántas pistas tiene el archivo.
 */
public class AgenciaDetectives {

    // nombre del archivo donde están guardadas las pistas
    static final String ARCHIVO = "pistas.txt";

    public static void main(String[] args) {
        System.out.println("=== AGENCIA DE DETECTIVES: ANALISIS DE PISTAS ===");

        // primero revisamos que el archivo exista
        if (!existeArchivo(ARCHIVO)) {
            System.out.println("No se encontro el archivo " + ARCHIVO);
            return;
        }

        int total = leerPistas(ARCHIVO);
        System.out.println("--------------------------------------------");
        System.out.println("Total de pistas encontradas: " + total);
    }

    // devuelve true si el archivo existe en la carpeta del programa
    static boolean existeArchivo(String nombre) {
        File f = new File(nombre);
        return f.exists();
    }

    // lee el archivo con readLine(), muestra cada pista y devuelve el contador
    static int leerPistas(String nombre) {
        int contador = 0;
        // try-with-resources: el archivo se cierra solo al terminar
        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // las lineas vacias no se cuentan como pista
                if (linea.trim().isEmpty()) {
                    continue;
                }
                contador++;
                System.out.println("Pista " + contador + ": " + linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return contador;
    }
}
