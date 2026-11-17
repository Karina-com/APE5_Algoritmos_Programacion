import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 6 - Sistema de mensajes secretos
 * Archivo: secreto.txt
 * Escribe mensajes con FileWriter + BufferedWriter (sobrescribiendo o agregando),
 * los lee con FileReader + BufferedReader, cuenta las líneas y controla errores de E/S.
 */
public class MensajesSecretos {

    static final String ARCHIVO = "secreto.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== MENSAJES SECRETOS =====");
            System.out.println("1. Escribir mensaje nuevo (borra los anteriores)");
            System.out.println("2. Agregar mensaje (conserva los anteriores)");
            System.out.println("3. Leer mensajes");
            System.out.println("4. Contar lineas");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");
            opcion = leerOpcion();

            switch (opcion) {
                case 1: escribirMensaje(false); break;
                case 2: escribirMensaje(true); break;
                case 3: leerMensajes(); break;
                case 4: System.out.println("Lineas registradas: " + contarLineas()); break;
                case 5: System.out.println("Archivo cerrado. Fin."); break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 5);
    }

    static int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // agregar = false -> sobrescribe el archivo ; agregar = true -> modo append
    static void escribirMensaje(boolean agregar) {
        System.out.print("Escriba el mensaje: ");
        String mensaje = sc.nextLine();
        if (mensaje.trim().isEmpty()) {
            System.out.println("No se guardan mensajes vacios");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, agregar))) {
            bw.write(mensaje);
            bw.newLine();
            if (agregar) {
                System.out.println("Mensaje agregado al final del archivo");
            } else {
                System.out.println("Archivo sobrescrito con el nuevo mensaje");
            }
        } catch (IOException e) {
            System.out.println("Error de escritura: " + e.getMessage());
        }
    }

    static void leerMensajes() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("Aun no hay mensajes guardados");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linea;
            int n = 1;
            System.out.println("--- Contenido de " + ARCHIVO + " ---");
            while ((linea = br.readLine()) != null) {
                System.out.println(n + ") " + linea);
                n++;
            }
        } catch (IOException e) {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }

    static int contarLineas() {
        int lineas = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            while (br.readLine() != null) {
                lineas++;
            }
        } catch (IOException e) {
            // si el archivo no existe tambien cae aqui (FileNotFoundException es una IOException)
            System.out.println("No se pudo abrir el archivo: " + e.getMessage());
        }
        return lineas;
    }
}
