import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {

        String dir = "C:/Games/savegames";

        GameProgress[] gameProgresses = {
                new GameProgress(4, 8, 1, 7.7),
                new GameProgress(10, 4, 12, 22.1),
                new GameProgress(7, 10, 4, 18),
        };

        for (int i = 0; i < gameProgresses.length; i++) {
            saveGame(dir + "/save" + i + ".dat", gameProgresses[i]);
        }

        List<String> listOfFiles = new ArrayList<>();
        for (int i = 0; i < gameProgresses.length; i++) {
            listOfFiles.add(dir + "/save" + i + ".dat");
        }

        zipFiles(dir, listOfFiles);

        File[] files = {
                new File(dir + "/save0.dat"),
                new File(dir + "/save1.dat"),
                new File(dir + "/save2.dat"),
        };

        deleteFiles(files);
    }

    public static void deleteFiles(File[] files) {
        for (File file : files) {
            if (file.delete()) {
                System.out.println("Файл " + file.getName() + " удален\n");
            } else {
                System.out.println("Не удалось удалить файл " + file.getName() + "\n");
            }
        }
    }

    private static void zipFiles(String dir, List<String> listOfFiles) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dir + "/zip_output.zip"))) {
            for (String file : listOfFiles) {
                ZipEntry entry = new ZipEntry(file.substring(19));
                zout.putNextEntry(entry);
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                fis.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void saveGame(String dir, GameProgress gameProgresses) {
        try (FileOutputStream fos = new FileOutputStream(dir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgresses);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
