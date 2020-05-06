/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package removeabandonedraws;

import java.io.IOException;

/**
 *
 * @author TiMan
 */
public class RemoveAbandonedRaws {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        /**
         * Application must be in the main directory, where are directories with photos
         * Application found all raw files, which are located in directory (FileManager.rawFolder) - add files to list
         * Next found all files in main directory and subdirectories with extenstion (FileManager.photoExtension)
         * and remove corresponding raw files from list
         * Remain files in list (remain only raw files, which doesnt have correspondig photo files) are
         * moved to directory (FileManager.(FileManager.rawFolder))
         */
        FileManager manager = new FileManager();
    }

}
