package com.qxbytes.filetreatment;

import java.nio.file.Path;
import java.util.List;

public interface FileTreatment {
	public abstract boolean isFileCompatible(Path file);
	public abstract List<String> getDataFromFile(Path file);
}
