/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package removeabandonedraws;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author TiMan
 */
public class FileManager {

    private ArrayList<Path> rawFiles = new ArrayList<>();
    private final static String rawFolder = "raw";
    private final static String photoExtension = ".jpg";
    private final static String rawExtension = ".DNG";
    private final static String deleteFolderName = "abandonedRaw";

    public FileManager() throws IOException {
        addRawFilesToList(Paths.get(rawFolder), rawExtension);
//        System.out.println("after add);
//         for (Path path : rawFiles) {
//            System.out.println(path.toString());
//        }
         
        removePhotoFiles(Paths.get(""), photoExtension);
//        System.out.println("after remove");
//         for (Path path : rawFiles) {
//            System.out.println(path.toString());
//        }
         
        moveRemainRawFiles();
    }

    // Add all raw files to the raw files list
    public void addRawFilesToList(Path rawDirPath, String extension) {
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(rawDirPath)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    addRawFilesToList(file, extension);
                } else {
                    if (file.toString().contains(extension)) {
                        rawFiles.add(file);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Search for a photo in photoExtenstion - remove from list them
    public void removePhotoFiles(Path mainPath, String extension) {
        try ( DirectoryStream<Path> stream = Files.newDirectoryStream(mainPath)) {
            for (Path file : stream) {
                if (Files.isDirectory(file)) {
                    removePhotoFiles(file, extension);
                } else {
                    if (file.toString().contains(extension)) {
                        removeRawFromList(file);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Remove corresponding photo path in raw path list
    public boolean removeRawFromList(Path path) {
        for (Path raw : rawFiles) {
            String rawWithoutExtension = raw.getFileName().toString().replace(rawExtension, "");
            String photoWithoutExtension = path.getFileName().toString().replace(photoExtension, "");
            
            if (rawWithoutExtension.equals(photoWithoutExtension)) {
                rawFiles.remove(raw);
                return true;
            }
        }
        return false;
    }

    // Move abandoned raw files to deleteFolderName
    public void moveRemainRawFiles() throws IOException {
        Path folderPath = Paths.get(deleteFolderName);
        if (!Files.exists(folderPath)) {
            Files.createDirectory(folderPath);
        }
        for (Path path : rawFiles) {
            Files.move(path, Paths.get(folderPath.toString(), "/", path.getFileName().toString()), REPLACE_EXISTING);
        }

    }

}
