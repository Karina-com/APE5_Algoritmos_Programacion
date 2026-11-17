import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 9 - Diario de exploradores
 * Archivo: descubrimientos.txt (cada línea: nombre del descubrimiento,descripcion)
 * Escribe y agrega descubrimientos al final, los lee línea por línea, los cuenta,
 * busca uno por nombre y valida que el archivo exista y tenga información.
 */
public class DiarioExploradores {

    static final String ARCHIVO = "descubrimientos.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== DIARIO DE EXPLORADORES =====");
            System.out.println("1. Registrar descubrimiento");
            System.out.println("2. Leer diario");
            System.out.println("3. Buscar descubrimiento");
            System.out.println("4. Contar descubrimientos");
            System.out.println("5. Salir");
            System.out.print("Opcion: ");
            opcion = leerOpcion();
            switch (opcion) {
                case 1: registrar(); break;
                case 2: leerDiario(); break;
                case 3: buscar(); break;
                case 4: System.out.println("Descubrimientos: " + contar()); break;
                case 5: System.out.println("Diario guardado. Hasta pronto, explorador."); break;
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

    static void registrar() {
        System.out.print("Nombre del descubrimiento: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Descripcion corta: ");
        String descripcion = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("El nombre es obligatorio");
            return;
        }
        // true = se agrega al final del archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(nombre + "," + descripcion);
            bw.newLine();
            System.out.println("Descubrimiento anotado en el diario");
        } catch (IOException e) {
            System.out.println("Error al escribir: " + e.getMessage());
        }
    }

    // valida dos cosas: que el archivo exista y que no este vacio
    static boolean diarioDisponible() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("El diario no existe todavia");
            return false;
        }
        if (f.length() == 0) {
            System.out.println("El diario existe pero esta vacio");
            return false;
        }
        return true;
    }

    static void leerDiario() {
        if (!diarioDisponible()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int n = 0;
            while ((linea = br.readLine()) != null) {
                n++;
                String[] p = linea.split(",", 2);   // el 2 evita cortar la descripcion si tiene comas
                String desc = (p.length > 1) ? p[1] : "(sin descripcion)";
                System.out.println(n + ". " + p[0] + " -> " + desc);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    static void buscar() {
        if (!diarioDisponible()) return;
        System.out.print("Nombre a buscar: ");
        String buscado = sc.nextLine().trim();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String nombre = linea.split(",", 2)[0];
                if (nombre.equalsIgnoreCase(buscado)) {
                    System.out.println("Encontrado: " + linea);
                    return;
                }
            }
            System.out.println("No hay ningun descubrimiento llamado \"" + buscado + "\"");
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    static int contar() {
        if (!diarioDisponible()) return 0;
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) total++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return total;
    }
}
