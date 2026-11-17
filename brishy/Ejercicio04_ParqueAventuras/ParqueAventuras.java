import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 4 - Parque de aventuras: resultados de participantes
 * resultados.txt tiene en cada línea: nombre,puntaje
 * Con BufferedReader se muestran los participantes, el puntaje total
 * y el puntaje más alto.
 */
public class ParqueAventuras {

    static final String ARCHIVO = "resultados.txt";

    public static void main(String[] args) {
        System.out.println("=== PARQUE DE AVENTURAS: RESULTADOS ===");

        if (!new File(ARCHIVO).exists()) {
            System.out.println("No se encontro " + ARCHIVO);
            return;
        }
        procesarResultados(ARCHIVO);
    }

    static void procesarResultados(String nombre) {
        int total = 0;
        int maximo = -1;          // empieza en -1 para que cualquier puntaje sea mayor
        String ganador = "";
        int participantes = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length != 2) {
                    continue;   // si la linea esta mal escrita la saltamos
                }
                String participante = datos[0].trim();
                int puntaje = convertirPuntaje(datos[1]);
                if (puntaje < 0) {
                    System.out.println("  Puntaje invalido para " + participante);
                    continue;
                }

                participantes++;
                total += puntaje;
                System.out.println(participantes + ". " + participante + " - " + puntaje + " pts");

                // buscamos el mayor
                if (puntaje > maximo) {
                    maximo = puntaje;
                    ganador = participante;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }

        System.out.println("--------------------------------------------");
        System.out.println("Participantes: " + participantes);
        System.out.println("Puntaje total: " + total);
        System.out.println("Puntaje mas alto: " + maximo + " (" + ganador + ")");
    }

    // convierte el texto a numero; si no es un numero devuelve -1
    static int convertirPuntaje(String texto) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
