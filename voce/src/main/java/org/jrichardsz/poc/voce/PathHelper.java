package org.jrichardsz.poc.voce;

import java.io.File;
import java.io.FileFilter;

public class PathHelper
{
	public static String getGrammarFileName(String grammarPath)throws Exception{
		File dir = new File(grammarPath);
		final String ext = ".gram"; // needs to be final so the anonymous class can use it
		File[] matchingFiles = dir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(ext);
			}
		});

		if(matchingFiles == null || matchingFiles.length == 0){
			throw new Exception("grammar file was not found in :"+grammarPath);
		}

		if(matchingFiles.length > 1){
			throw new Exception("several grammar files were found in :"+grammarPath+". Only one *.gram file is required");
		}		
		
		return getFileNameFile(matchingFiles[0]);
	}



	public static String getFileExtension(File file) {
		String name = file.getName();
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getFileNameFile(File file) throws Exception{
		String name = file.getName();
		try {
			return name.substring(0,name.lastIndexOf("."));
		} catch (Exception e) {
			throw new Exception("Is not possible to determine file name of : "+name+". Expected name must end with .gram. Example : digits.gram");
		}
	}	

	
}