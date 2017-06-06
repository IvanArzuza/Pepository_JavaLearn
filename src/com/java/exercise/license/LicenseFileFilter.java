/**
 * 
 */
package com.java.exercise.license;

import Utility.FileExtension;
import java.io.File;
import javax.swing.filechooser.FileFilter;

public class LicenseFileFilter extends FileFilter {
    public boolean accept(File f) {
        return f.isDirectory() || f.getName().toLowerCase().endsWith(FileExtension.ZIP);
    }
    
    public String getDescription() {
        return "License files";
    }
}