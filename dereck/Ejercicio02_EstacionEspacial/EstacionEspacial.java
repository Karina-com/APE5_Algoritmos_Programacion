import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 2 - Estación espacial: descifrado de mensajes
 * Lee mensaje.txt carácter por carácter con FileReader y read(),
 * muestra el contenido y cuenta el total de caracteres.
 */
public class EstacionEspacial {

    static final String ARCHIVO = "mensaje.txt";

    public static void main(String[] args) {
        System.out.println("=== ESTACION ESPACIAL: DESCIFRADO DE MENSAJES ===");

        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("El mensaje no llego: no existe " + ARCHIVO);
            return;
        }

        System.out.println("Mensaje recibido:");
        System.out.println("--------------------------------------------");
        int total = leerCaracterPorCaracter(ARCHIVO);
        System.out.println();
        System.out.println("--------------------------------------------");
        System.out.println("Total de caracteres leidos: " + total);
    }

    // read() devuelve un int con el codigo del caracter, y -1 cuando se acaba el archivo
    static int leerCaracterPorCaracter(String nombre) {
        int contador = 0;
        try (FileReader fr = new FileReader(nombre)) {
            int c;
            while ((c = fr.read()) != -1) {
                char letra = (char) c;   // convertimos el numero a caracter
                // el salto de linea de Windows (\r) no lo mostramos ni lo contamos
                if (letra == '\r') {
                    continue;
                }
                System.out.print(letra);
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
        return contador;
    }
}
