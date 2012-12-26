package net.sinsengumi;

import java.io.IOException;
import java.util.List;

public class ProcessExecutor {

	private List<String> script;

	private InputStreamThread it;
	private InputStreamThread et;

	public ProcessExecutor(List<String> script) {
		this.script = script;
	}

	public int execute() throws IOException, InterruptedException {
		ProcessBuilder pb = new ProcessBuilder(script);
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
