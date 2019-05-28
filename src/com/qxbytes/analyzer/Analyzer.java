package com.qxbytes.analyzer;

import java.nio.file.Path;

public interface Analyzer {
	public abstract void start ();
	public abstract void fileStart(Path file, int count);
	public abstract void analyzeLine (String data, int lineNumber);
	public abstract void fileEnd(Path file, int count);
	public abstract void end();
}
