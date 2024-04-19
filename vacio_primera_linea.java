import java.io.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "archivo.zip";
        String targetFileName = "archivo_de_texto.txt";

        try (ZipFile zipFile = new ZipFile(filePath)) {
            ZipEntry textFileEntry = zipFile.getEntry(targetFileName);

            if (textFileEntry != null) {
                boolean isEmpty = isTextFileEmpty(zipFile.getInputStream(textFileEntry));

                if (isEmpty) {
                    throw new ArchivoVacioException("El archivo está completamente vacío.");
                } else {
                    System.out.println("El archivo de texto no está completamente vacío.");
                }
            } else {
                System.out.println("El archivo de texto no existe en el archivo ZIP.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArchivoVacioException e) {
            System.err.println(e.getMessage());
        }
    }

    private static boolean isTextFileEmpty(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    return false; // Si se encuentra una línea no vacía, el archivo no está vacío
                }
            }
            return true; // Si todas las líneas están vacías, el archivo está vacío
        }
    }
}


class ArchivoVacioException extends Exception {
    public ArchivoVacioException() {
        super("El archivo está vacío.");
    }

    public ArchivoVacioException(String mensaje) {
        super(mensaje);
    }
}




