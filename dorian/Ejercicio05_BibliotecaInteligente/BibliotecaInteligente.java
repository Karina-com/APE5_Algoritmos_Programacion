import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 5 - Biblioteca inteligente
 * Archivo: libros.txt  (cada línea: titulo,autor)
 * Permite registrar libros sin borrar los anteriores, leer los títulos,
 * buscar un libro y contar cuántos hay. Antes de leer se valida que el archivo exista.
 */
public class BibliotecaInteligente {

    static final String ARCHIVO = "libros.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion();
            switch (opcion) {
                case 1: registrarLibro(); break;
                case 2: listarLibros(); break;
                case 3: buscarLibro(); break;
                case 4: System.out.println("Libros registrados: " + contarLibros()); break;
                case 5: System.out.println("Saliendo de la biblioteca..."); break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 5);
    }

    static void mostrarMenu() {
        System.out.println("\n===== BIBLIOTECA INTELIGENTE =====");
        System.out.println("1. Registrar libro");
        System.out.println("2. Ver titulos");
        System.out.println("3. Buscar libro");
        System.out.println("4. Contar libros");
        System.out.println("5. Salir");
        System.out.print("Elija una opcion: ");
    }

    // lee la opcion como texto y la convierte, asi no se cae si escriben letras
    static int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // FileWriter con true = modo append, agrega al final sin borrar lo anterior
    static void registrarLibro() {
        System.out.print("Titulo: ");
        String titulo = sc.nextLine().trim();
        System.out.print("Autor: ");
        String autor = sc.nextLine().trim();

        if (titulo.isEmpty() || autor.isEmpty()) {
            System.out.println("El titulo y el autor no pueden estar vacios");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(titulo + "," + autor);
            bw.newLine();
            System.out.println("Libro guardado correctamente");
        } catch (IOException e) {
            System.out.println("No se pudo guardar: " + e.getMessage());
        }
    }

    static boolean archivoExiste() {
        File f = new File(ARCHIVO);
        if (!f.exists()) {
            System.out.println("Todavia no hay libros: el archivo " + ARCHIVO + " no existe");
            return false;
        }
        return true;
    }

    static void listarLibros() {
        if (!archivoExiste()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int n = 0;
            System.out.println("Titulos almacenados:");
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                n++;
                System.out.println("  " + n + ". " + datos[0] + (datos.length > 1 ? " (" + datos[1] + ")" : ""));
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }

    // busca por titulo; no importa si escriben con mayusculas o minusculas
    static void buscarLibro() {
        if (!archivoExiste()) return;
        System.out.print("Titulo a buscar: ");
        String buscado = sc.nextLine().trim().toLowerCase();
        boolean encontrado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String titulo = linea.split(",")[0];
                if (titulo.toLowerCase().contains(buscado)) {
                    System.out.println("Encontrado: " + linea);
                    encontrado = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        if (!encontrado) {
            System.out.println("No existe ningun libro con ese titulo");
        }
    }

    static int contarLibros() {
        if (!archivoExiste()) return 0;
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            while (br.readLine() != null) {
                contador++;
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return contador;
    }
}
