package net.sinsengumi;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProcessExecutor {

	private List<String> script;
	private File baseDir;

	private InputStreamThread it;
	private InputStreamThread et;

	public ProcessExecutor(List<String> script, File baseDir) {
		this.script = script;
		this.baseDir = baseDir;
	}

	public int execute() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(script);

		if (baseDir != null) {
			pb.directory(baseDir);
		}

		Process p = pb.start();

		it = new InputStreamThread(p.getInputStream());
		et = new InputStreamThread(p.getErrorStream());
		it.start();
		et.start();

		p.waitFor();

		it.join();
		et.join();

		return p.exitValue();
	}

	public List<String> getResultString() {
		return it.getStringList();
	}

	public List<String> getErrorResultString() {
		return et.getStringList();
	}
}
