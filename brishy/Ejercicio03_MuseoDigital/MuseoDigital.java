import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Ejercicio 3 - Museo digital: registro de obras
 * Cada línea de obras.txt tiene: nombre de la obra,categoria
 * El programa muestra las obras y cuenta cuántas hay de cada categoría.
 */
public class MuseoDigital {

    static final String ARCHIVO = "obras.txt";

    // arreglos para guardar las categorias encontradas y cuantas obras tiene cada una
    static String[] categorias = new String[50];
    static int[] cantidades = new int[50];
    static int totalCategorias = 0;

    public static void main(String[] args) {
        System.out.println("=== MUSEO DIGITAL: REGISTRO DE OBRAS ===");

        if (!new File(ARCHIVO).exists()) {
            System.out.println("No existe el archivo " + ARCHIVO);
            return;
        }

        int totalObras = leerObras(ARCHIVO);
        mostrarClasificacion();
        System.out.println("Total de obras registradas: " + totalObras);
    }

    // lee cada linea, la separa con split(",") y va contando por categoria
    static int leerObras(String nombre) {
        int total = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(nombre))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                // validamos que la linea tenga nombre y categoria
                if (partes.length != 2) {
                    System.out.println("  (linea ignorada, formato incorrecto: " + linea + ")");
                    continue;
                }
                String obra = partes[0].trim();
                String categoria = partes[1].trim();
                total++;
                System.out.println(total + ". " + obra + "  ->  " + categoria);
                sumarCategoria(categoria);
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
        return total;
    }

    // si la categoria ya existe le suma 1, si no la agrega al arreglo
    static void sumarCategoria(String categoria) {
        for (int i = 0; i < totalCategorias; i++) {
            if (categorias[i].equalsIgnoreCase(categoria)) {
                cantidades[i]++;
                return;
            }
        }
        categorias[totalCategorias] = categoria;
        cantidades[totalCategorias] = 1;
        totalCategorias++;
    }

    static void mostrarClasificacion() {
        System.out.println("--------------------------------------------");
        System.out.println("Obras por categoria:");
        for (int i = 0; i < totalCategorias; i++) {
            System.out.println("  " + categorias[i] + ": " + cantidades[i]);
        }
        System.out.println("--------------------------------------------");
    }
}
