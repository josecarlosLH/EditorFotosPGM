import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        Imagen img = new Imagen();
        String entrada;

        System.out.println("\nBienvenido al editor de fotos PGM.");
        img.seleccionarArchivoLeer();

        do {
            System.out.println("Elige la opción que quieres ejecutar sobre el PGM: ");
            System.out.println("[1] Imagen en negativo");
            System.out.println("[2] Imagen en espejo");
            System.out.println("[3] Imagen rotada");
            System.out.println("[4] Zoom");
            System.out.println("[5] Filtro de eliminación de ruido");
            System.out.println("[S] Guardar y salir");

            do {
                entrada = sc.next();
                if (!Pattern.matches("[Ss1-5]", entrada)) {
                    System.out.println("Entrada no válida. Por favor, introduce un valor indicado en el menú.");
                }
            } while(!Pattern.matches("[Ss1-5]", entrada));

            switch(entrada.charAt(0)) {
                case '1':
                    System.out.println("-----------------------------------------------------------------------------");
                    img.imagenEnNegativo();
                    System.out.println("¡Realizado con éxito!");
                    System.out.println("-----------------------------------------------------------------------------\n");
                    break;
                case '2':
                    System.out.println("-----------------------------------------------------------------------------");
                    img.imagenEnEspejo();
                    System.out.println("¡Realizado con éxito!");
                    System.out.println("-----------------------------------------------------------------------------\n");
                    break;
                case '3':
                    System.out.println("-----------------------------------------------------------------------------");
                    img.rotarImagen();
                    System.out.println("¡Realizado con éxito!");
                    System.out.println("-----------------------------------------------------------------------------\n");
                    break;
                case '4':
                    System.out.println("-----------------------------------------------------------------------------");
                    img.zoom();
                    System.out.println("\n-----------------------------------------------------------------------------");
                    System.out.println("¡Realizado con éxito!");
                    System.out.println("-----------------------------------------------------------------------------\n");
                    break;
                case '5':
                    System.out.println("-----------------------------------------------------------------------------");
                    img.eliminarRuido();
                    System.out.println("¡Realizado con éxito!");
                    System.out.println("-----------------------------------------------------------------------------\n");
                    break;
            }
        } while(entrada.toUpperCase().charAt(0) != 'S');
        System.out.println("\n-----------------------------------------------------------------------------");
        img.seleccionarArchivoEscribir();
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("¡Archivo creado con éxito!");
        System.out.println("-----------------------------------------------------------------------------\n");
    }
}