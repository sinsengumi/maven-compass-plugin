package net.sinsengumi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

public abstract class AbstractCompassMojo extends AbstractMojo {

	protected void rubyVersionCommandExec() throws IOException, InterruptedException, MojoExecutionException {

		List<String> script = new ArrayList<String>();
		script.add("ruby");
		script.add("-v");

		versionCommandExec("Ruby", script);
	}

	protected void sassVersionCommandExec() throws IOException, InterruptedException, MojoExecutionException {

		List<String> script = new ArrayList<String>();

		if (isWindows()) {
			script.add("cmd");
			script.add("/c");
		}
		script.add("sass");
		script.add("-v");

		versionCommandExec("Sass", script);
	}

	protected void compassVersionCommandExec() throws IOException, InterruptedException, MojoExecutionException {

		List<String> script = new ArrayList<String>();

		if (isWindows()) {
			script.add("cmd");
			script.add("/c");
		}
		script.add("compass");
		script.add("version");

		versionCommandExec("Compass", script);
	}

	protected void versionCommandExec(String commandName, List<String> script) throws IOException,
			InterruptedException, MojoExecutionException {

		ProcessExecutor pe = new ProcessExecutor(script);
		int ret = pe.execute();

		if (!pe.getResultString().isEmpty()) {
			String version = pe.getResultString().get(0);
			getLog().info("[" + commandName + "] " + version);
		}

		for (String s : pe.getErrorResultString()) {
			getLog().error(s);
		}

		if (ret != 0) {
			throw new MojoExecutionException(commandName + " version command failure. " + scriptToString(script));
		}
	}

	protected String scriptToString(List<String> script) {
		StringBuilder builder = new StringBuilder();
		for (String arg : script) {
			builder.append(arg);
			builder.append(" ");
		}

		return builder.toString();
	}

	protected boolean isWindows() {
		String os = System.getProperty("os.name");
		if (os != null && os.startsWith("Windows")) {
			return true;
		} else {
			return false;
		}
	}
}
