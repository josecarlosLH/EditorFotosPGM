import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Imagen {
    Scanner sc;
    int ancho, alto, rango;
    int [][] m;

    public void seleccionarArchivoLeer() {
        sc = new Scanner(System.in);

        System.out.println("Introduce el nombre de la imagen PGM que quieres modificar sin la extensión .pgm: ");
        String nombreArchivo = sc.nextLine();
        File archivo = new File(nombreArchivo + ".pgm");

        boolean existe = archivo.exists();
        if (existe) {
            leerPgm(archivo);
        } else {
            System.out.println("El archivo no existe.");
            System.exit(0);
        }
    }

    public void seleccionarArchivoEscribir() throws IOException {
        sc = new Scanner(System.in);
        int i, j;

        System.out.println("Introduce el nombre del archivo en el que quieres guardar la\nimagen PGM modificada. No incluyas la extensión .pgm en el nombre: ");
        String nombreArchivo = sc.nextLine();
        File archivo = new File(nombreArchivo + ".pgm");
        BufferedWriter bw;

        if(archivo.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo));
            System.out.println("El fichero ya estaba creado, por lo que se van a sobreescribir los datos.");
        } else {
            bw = new BufferedWriter(new FileWriter(archivo));
        }

        bw.write("P2");
        bw.newLine();
        bw.write(ancho + " " + alto);
        bw.newLine();

        for (i = 0; i < m.length; i++) {
            for (j = 0; j < m[0].length; j++) {
                bw.write(m[i][j] + " ");
            }
        }

        bw.flush();
        bw.close();
    }

    public void leerPgm(File archivo) {
        try {
            FileReader fileReader = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fileReader);
            sc = new Scanner(br);

            String linea;
            linea = br.readLine();

                if (linea.contains("P2")) {
                    linea = br.readLine();
                    if (linea.contains("#")) {
                        linea = br.readLine();
                    }


                    String[] parts = linea.split(" ");
                    ancho = Integer.parseInt(parts[0]);
                    alto = Integer.parseInt(parts[1]);

                    m = new int[alto][ancho];

                    rango = sc.nextInt();

                    int i, j;
                    for(i = 0; i < alto; i++) {
                        for (j = 0; j < ancho; j++) {
                            m [i][j] = sc.nextInt();
                        }
                    }
                }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////// IMAGEN EN NEGATIVO //////////////////////////////////////

    public void imagenEnNegativo() {
        int i, j;

        for(i = 0; i < alto; i++) {
            for (j = 0; j < ancho; j++) {
                m[i][j] = Math.abs(rango - m[i][j]);
            }
        }
    }

    ////////////////////////////////////// IMAGEN EN ESPEJO //////////////////////////////////////

    public void imagenEnEspejo() {
        sc = new Scanner(System.in);

        System.out.println("Escribe 'horizontal' o 'vertical' según la forma en la que desees aplicar el efecto: ");
        String opcion;

        do {
            opcion = sc.nextLine();
            if (!opcion.equalsIgnoreCase("horizontal") && !opcion.equalsIgnoreCase("vertical")) {
                System.out.println("Entrada no válida. Por favor, inténtalo de nuevo");
            }
        } while (!opcion.equalsIgnoreCase("horizontal") && !opcion.equalsIgnoreCase("vertical"));

        if (opcion.equalsIgnoreCase("horizontal")) {
            espejoHorizontal();
        } else {
            espejoVertical();
        }
    }

    public void espejoHorizontal() {
        int i, j;
        int[][]imagenEnEspejoHorizontal = new int[alto][ancho];

        for (i = 0; i < alto / 2; i++) {
            for(j = 0; j < ancho; j++) {
                imagenEnEspejoHorizontal[alto - (i + 1)][j] = m[i][j];
                imagenEnEspejoHorizontal[i][j] = m[alto - (i + 1)][j];
            }
        }
        m = imagenEnEspejoHorizontal;
    }

    public void espejoVertical() {
        int i, j;
        int[][] imagenEnEspejoVertical = new int[alto][ancho];

        for (i = 0; i < alto; i++) {
            for (j = 0; j < ancho; j++) {
                imagenEnEspejoVertical[i][ancho - j - 1] = m[i][j];
            }
        }
        m = imagenEnEspejoVertical;
    }

    ////////////////////////////////////// MÉTODO ROTAR //////////////////////////////////////

    public void rotarImagen() {
        sc = new Scanner(System.in);

        System.out.println("Escribe 'positivo' si quieres girar la imagen en el sentido de las agujas\ndel reloj o 'negativo' si quieres girar la imagen en sentido antihorario: ");
        String opcion;

        do {
            opcion = sc.nextLine();
            if (!opcion.equalsIgnoreCase("positivo") && !opcion.equalsIgnoreCase("negativo")) {
                System.out.println("Entrada no válida. Por favor, inténtalo de nuevo");
            }
        } while (!opcion.equalsIgnoreCase("positivo") && !opcion.equalsIgnoreCase("negativo"));

        if (opcion.equalsIgnoreCase("positivo")) {
            rotarPositivo();
        } else {
            rotarNegativo();
        }
    }

    public void rotarPositivo() {
        int i, j;
        final int M = m.length;
        final int N = m[0].length;
        int[][] imagenRotadaPositivo = new int[N][M];

        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                imagenRotadaPositivo[j][M - 1 - i] = m[i][j];
            }
        }
        m = imagenRotadaPositivo;
    }

    public void rotarNegativo() {
        int i, j;
        final int M = m.length;
        final int N = m[0].length;
        int[][] imagenRotadaNegativo = new int[N][M];

        for (i = 0; i < M; i++) {
            for (j = 0; j < N; j++) {
                //imagenRotadaNegativo[j][M - 1 - i] = m[i][j];
                imagenRotadaNegativo[N - 1 - j][i] = m[i][j];
            }
        }
        m = imagenRotadaNegativo;
    }

    ////////////////////////////////////// ZOOM //////////////////////////////////////

    public void zoom() {
        int i, j, k, valor1, valor2, aux, media;
        sc = new Scanner(System.in);
        System.out.println("Para que pueda realizar el zoom, tendrás que introducir dos píxeles de la imagen.");

        int filaEsSuIz;
        int columEsSuIz;

        do {
            System.out.println("Introduce la fila de la esquina superior izquierda: ");
            filaEsSuIz = sc.nextInt();
            System.out.println("Introduce la columna de la esquina superior izquierda: ");
            columEsSuIz = sc.nextInt();

            if ((filaEsSuIz >= alto || columEsSuIz >= ancho) || (filaEsSuIz < 0 || columEsSuIz < 0)) {
                System.out.println("Los valores no son válidos. Te estás saliendo de la matriz.");
            }
        } while ((filaEsSuIz >= alto || columEsSuIz >= ancho) || (filaEsSuIz < 0 || columEsSuIz < 0));

        int filaEsInDer;
        int columEsInDer;

        do {
            System.out.println("Introduce la fila y la columna de la esquina inferior derecha: ");
            filaEsInDer = sc.nextInt();
            System.out.println("Introduce la columna de la esquina inferior derecha: ");
            columEsInDer = sc.nextInt();

            if ((filaEsInDer < filaEsSuIz || columEsInDer < columEsSuIz) || (filaEsInDer == filaEsSuIz && columEsInDer == columEsSuIz)) {
                System.out.println("Los valores de la fila y la columna de la esquina inferior derecha han de ser mayores a los de la esquina superior izquierda. Introduce 0 en el valor de la fila y la columna si quieres ");
            }
        } while ((filaEsInDer < filaEsSuIz || columEsInDer < columEsSuIz) || (filaEsInDer == filaEsSuIz && columEsInDer == columEsSuIz));

        int filasTemp = filaEsInDer - filaEsSuIz;
        int columTemp = columEsInDer - columEsSuIz;

        int filasImagenZoom = 2 * filasTemp - 1;
        int columImagenZoom = 2 * columTemp - 1;
        alto = filasImagenZoom;
        ancho = columImagenZoom;
        int [][] imgZoom = new int [filasImagenZoom][columImagenZoom];

        for (i = 0; i < filasImagenZoom; i++) {
            if (i % 2 == 0) {
                for (j = 0; j <= columImagenZoom - 2; j += 2) {
                    valor1 = m[i][j];
                    aux = j + 2;
                    valor2 = m[i][aux];
                    media = valor1 + valor2 / 2;
                    imgZoom[i][j + 1] = media;
                }

                for (j = 1; j < columImagenZoom; j += 2) {
                    valor1 = m[i][j];
                    imgZoom[i][j] = valor1;
                }
            } else {
                for (k = 0; k < columImagenZoom; k++) {
                    valor1 = m[i - 1][k];
                    valor2 = m[i + 1][k];
                    media = valor1 + valor2 / 2;
                    imgZoom[i][k] = media;
                }
            }
        }
        m = imgZoom;
    }

    ////////////////////////////////////// FILTRO RUIDO //////////////////////////////////////

    public void eliminarRuido() {
        int i, j, k, l;
        int [][] mFila = new int [2][2];
        int [][] mFiltro = new int [alto][ancho];
        int [] lista = new int [ancho];
        int posicionLista = 0, mediana;

        for (i = 0; i < alto; i++) {
            for (j = 0; j < ancho; j++) {
                if (i - 1 < 0 || j - 1 < 0 || i + 1 == alto || j + 1 == ancho) {

                    // CASILLA DE LA ESQUINA SUPERIOR IZQUIERDA
                    if (i - 1 < 0 && j - 1 < 0) {
                        for (l = i; l <= i + 1; l++) {
                            for (k = j; k <= j + 1; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLAS DEL BORDE SUPERIOR
                    if (i - 1 < 0 && j != 0 && j + 1 != ancho) {
                        for (l = i; l <= i + 1; l++) {
                            for (k = j - 1; k <= j + 1; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLA DE LA ESQUINA SUPERIOR DERECHA
                    if (i - 1 < 0 && j + 1 == ancho) {
                        for (l = i; l <= i + 1; l++) {
                            for (k = j - 1; k <= j; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLAS DEL BORDE IZQUIERDO
                    if (j - 1 < 0 && i > 0 && i + 1 != alto) {
                        for (l = i - 1; l <= i + 1; l++) {
                            for (k = j; k <= j + 1; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLA DE LA ESQUINA INFERIOR IZQUIERDA
                    if (i + 1 == alto && j - 1 < 0) {
                        for (l = i - 1; l <= i; l++) {
                            for (k = j; k <= j + 1; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLAS DEL BORDE DERECHO
                    if (j + 1 >= ancho && i > 0 && i + 1 != alto) {
                        for (l = i - 1; l <= i + 1; l++) {
                            for (k = j - 1; k <= j; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLA DE LA ESQUINA INFERIOR DERECHA
                    if (i + 1 == alto && j + 1 == ancho) {
                        for (l = i - 1; l <= i; l++) {
                            for (k = j - 1; k <= j; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }

                    //CASILLAS DEL BORDE INFERIOR
                    if (i + 1 == alto && j != 0 && j + 1 != ancho) {
                        for (l = i - 1; l <= i; l++) {
                            for (k = j - 1; k <= j + 1; k++) {
                                mFila[l][k] = m[l][k];
                                lista[posicionLista++] = mFila[l][k];
                            }
                        }
                        Arrays.sort(lista);
                        mediana = lista[((posicionLista - 1) / 2) + 1];
                        lista = new int[ancho];
                        mFila = new int[alto][ancho];
                        mFiltro[i][j] = mediana;
                    }
                } else {
                    for (l = i - 1; l <= i + 1; l++) {
                        for (k = j - 1; k <= j + 1; k++) {
                            mFila[l][k] = m[l][k];
                            lista[posicionLista++] = mFila[l][k];
                        }
                    }
                    Arrays.sort(lista);
                    mediana = lista[(posicionLista - 1) / 2];
                    lista = new int[ancho];
                    mFila = new int[alto][ancho];
                    mFiltro[i][j] = mediana;
                }
                posicionLista = 0;
            }
        }
        m = mFiltro;
    }
}