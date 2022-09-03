import java.io.*;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String args[]) {

        ArrayList<String> list = new ArrayList<>();
        list.add("D://Games//savegames//save1.dat");
        list.add("D://Games//savegames//save2.dat");
        list.add("D://Games//savegames//save3.dat");

        String savePathZip = "D://Games//savegames//zip.zip";

        GameProgress gameProgress1 = new GameProgress(1, 1, 1, 1);
        GameProgress gameProgress2 = new GameProgress(2, 2, 2, 2);
        GameProgress gameProgress3 = new GameProgress(3, 3, 3, 3);

        saveGame(list.get(0), gameProgress1);
        saveGame(list.get(1), gameProgress2);
        saveGame(list.get(2), gameProgress3);

        zipFiles(savePathZip, list);

        deleteFiles(list);
    }

    public static void saveGame(String savePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(savePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String savePathZip, ArrayList<String> list) {
        int i = 0;
        for (String path : list) {
            System.out.println(path);
        }
        try {

            ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(savePathZip));
            for (String path : list) {

                FileInputStream fis = new FileInputStream(path);
                ZipEntry entry = new ZipEntry("save" + Integer.toString(i + 1) + ".dat");
                i++;
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);

                zout.closeEntry();
                fis.close();
            }
            zout.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFiles(ArrayList<String> list) {
        for (String path : list) {
            File file = new File(path);
            if (file.delete()) {
                System.out.println(file.getName() + " deleted");
            } else {
                System.out.println(file.getName() + " not deleted");
            }


        }
    }
}







