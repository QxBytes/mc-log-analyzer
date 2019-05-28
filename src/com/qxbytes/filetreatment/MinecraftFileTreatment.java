package com.qxbytes.filetreatment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.qxbytes.searcher.AwesomeFileVisitor;
import com.qxbytes.searcher.ScanningGUI;

public class MinecraftFileTreatment implements FileTreatment{

	@Override
	public boolean isFileCompatible(Path file) {
		
		try {
			
		
			if (!file.getFileName().toString().endsWith(".log.gz")) {
				throw new Exception("Wrong file type");
			}
			
			if (Files.size(file) > 500000) {
				throw new Exception("File initially seems too big! > 500KB");
			}
		
		} catch (Exception e) {
			ScanningGUI.addError(file.getFileName() + " wasn't compatible: " + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<String> getDataFromFile(Path filename) {
		List<String> decompressed = new ArrayList<String>();
		try {
			InputStream fileStream = new FileInputStream(filename.toString());
			InputStream gzipStream = new GZIPInputStream(fileStream);
			Reader decoder = new InputStreamReader(gzipStream,AwesomeFileVisitor.CHARSET);
			BufferedReader buffered = new BufferedReader(decoder);
			//reliable?
			int linesTest = 0;
			while (buffered.ready() == true) {
				decompressed.add(buffered.readLine());
				linesTest++;
			}
			ScanningGUI.addLog("There seem to be Lines: " + linesTest);
			buffered.close();
			
			return decompressed;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
