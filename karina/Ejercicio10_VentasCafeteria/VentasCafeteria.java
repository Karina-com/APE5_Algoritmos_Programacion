import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Ejercicio 10 - Sistema de ventas de una cafetería
 * Archivo: ventas.txt (cada línea: producto,precio)
 * Registra ventas con BufferedWriter, lee los registros, convierte precios a double,
 * calcula el total, el producto más caro, filtra por un valor y valida los precios.
 */
public class VentasCafeteria {

    static final String ARCHIVO = "ventas.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n===== CAFETERIA - VENTAS =====");
            System.out.println("1. Registrar venta");
            System.out.println("2. Ver ventas y total");
            System.out.println("3. Filtrar productos mayores a un valor");
            System.out.println("4. Salir");
            System.out.print("Opcion: ");
            opcion = leerOpcion();
            switch (opcion) {
                case 1: registrarVenta(); break;
                case 2: reporteVentas(); break;
                case 3: filtrarPorPrecio(); break;
                case 4: System.out.println("Caja cerrada"); break;
                default: System.out.println("Opcion no valida");
            }
        } while (opcion != 4);
    }

    static int leerOpcion() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // convierte el texto a double; devuelve -1 si no es un precio valido
    static double convertirPrecio(String texto) {
        try {
            double precio = Double.parseDouble(texto.trim().replace(",", "."));
            if (precio <= 0) return -1;   // no hay precios negativos ni en cero
            return precio;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void registrarVenta() {
        System.out.print("Producto: ");
        String producto = sc.nextLine().trim();
        System.out.print("Precio: $");
        double precio = convertirPrecio(sc.nextLine());

        if (producto.isEmpty() || precio < 0) {
            System.out.println("Datos invalidos, la venta no se registro");
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true))) {
            bw.write(producto + "," + precio);
            bw.newLine();
            System.out.println("Venta registrada");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void reporteVentas() {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("Todavia no hay ventas");
            return;
        }
        double total = 0;
        double mayor = 0;
        String productoMayor = "";

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length != 2) {
                    System.out.println("  Registro mal formado: " + linea);
                    continue;
                }
                double precio = convertirPrecio(p[1]);
                if (precio < 0) {
                    System.out.println("  Precio incorrecto en: " + linea);
                    continue;
                }
                System.out.printf("  %-20s $%6.2f%n", p[0].trim(), precio);
                total += precio;
                if (precio > mayor) {
                    mayor = precio;
                    productoMayor = p[0].trim();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
            return;
        }
        System.out.println("--------------------------------------------");
        System.out.printf("Total de ventas: $%.2f%n", total);
        System.out.printf("Producto de mayor precio: %s ($%.2f)%n", productoMayor, mayor);
    }

    static void filtrarPorPrecio() {
        if (!new File(ARCHIVO).exists()) {
            System.out.println("Todavia no hay ventas");
            return;
        }
        System.out.print("Mostrar productos con precio mayor a: $");
        double limite = convertirPrecio(sc.nextLine());
        if (limite < 0) {
            System.out.println("Valor no valido");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            int encontrados = 0;
            while ((linea = br.readLine()) != null) {
                String[] p = linea.split(",");
                if (p.length != 2) continue;
                double precio = convertirPrecio(p[1]);
                if (precio > limite) {
                    System.out.printf("  %s - $%.2f%n", p[0].trim(), precio);
                    encontrados++;
                }
            }
            System.out.println("Productos encontrados: " + encontrados);
        } catch (IOException e) {
            System.out.println("Error al leer: " + e.getMessage());
        }
    }
}
