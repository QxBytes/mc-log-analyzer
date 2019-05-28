package com.qxbytes.filetreatment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StandardFileTreatment implements FileTreatment{

	@Override
	public boolean isFileCompatible(Path file) {
		return true;
	}

	@Override
	public List<String> getDataFromFile(Path file) {
		try {
			return Files.readAllLines(file,com.qxbytes.searcher.AwesomeFileVisitor.CHARSET);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
