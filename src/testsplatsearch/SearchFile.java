/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsplatsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author tosha
 */
public class SearchFile implements Runnable{

    private File file;
    private String strSearch;
    private String strExtension;
    private ArrayList<File[]> listFile;         //лист с файлами с нужным раширением
    private ArrayList<File> listFileText;       //лист с файлами с найденным текстом
        
    
    SearchFile(File f, String strSearch, String strExtension){
        this.strSearch = strSearch;
        this.strExtension = strExtension;
        file = f;
        listFile = new ArrayList<>();
        listFileText = new ArrayList<>();
        
    }
    @Override
    public void run() {
        allFiles(file, file.getPath());
        searchText();
        for(int i = 0; i < listFile.size(); i++){
            for (int j = 0; j < listFile.get(i).length; j++) {
                System.out.println(listFile.get(i)[j].getPath());
            }
        }
        SearchFrame.listFileText = listFileText;
         
    }
    
    public void allFiles(File f1, String dirname){
        if (f1.isDirectory()) {
            File[] flst = f1.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(strExtension);
                }
            });
            if(flst.length != 0){
                listFile.add(flst);
            }
      
            String s[] = f1.list();
            for (int i = 0; i < s.length; i++) {
                File f = new File(dirname + "/" + s[i]);
                if (f.isDirectory()) {
                    allFiles(f, f.getPath());
                } 
            }
        } 
        return;
    }
    
    public void searchText(){
        for(int i = 0; i < listFile.size(); i++){
             for (int j = 0; j < listFile.get(i).length; j++) {
                try(BufferedReader br = new BufferedReader(new FileReader(listFile.get(i)[j]))){
                    String str;
                    while((str = br.readLine()) != null){
                        if(str.indexOf(strSearch) != -1){
                            listFileText.add(listFile.get(i)[j]);
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SearchFile.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
    }
}
