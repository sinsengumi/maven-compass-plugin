package net.sinsengumi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * Compass Compile Goal.
 *
 * @goal compile
 * @phase compile
 */
public class CompassCompileMojo extends AbstractCompassMojo {

	/**
	 * Turns on sass's debuging information.
	 *
	 * @parameter
	 */
	private Boolean debugInfo;

	/**
	 * Specify the location of the configuration file explicitly.
	 *
	 * @parameter
	 */
	private File config;

	/**
	 * The source directory where you keep your sass stylesheets.
	 *
	 * @parameter
	 */
	private File sassDir;

	/**
	 * The target directory where you keep your css stylesheets.
	 *
	 * @parameter
	 */
	private File cssDir;

	/**
	 * The directory where you keep your images.
	 *
	 * @parameter
	 */
	private File imagesDir;

	/**
	 * The directory where you keep your javascripts.
	 *
	 * @parameter
	 */
	private File javascriptsDir;

	/**
	 * The directory where you keep your fonts.
	 *
	 * @parameter
	 */
	private File fontsDir;

	/**
	 * The directory that the compass command should be run from.
	 *
	 * @parameter
	 */
	private File baseDir;

	/**
	 * Use sensible defaults for your current environment.<br>
	 * One of: development (default), production
	 *
	 * @parameter
	 */
	private String environment;

	/**
	 * Select a CSS output mode.<br>
	 * One of: nested, expanded, compact, compressed
	 *
	 * @parameter
	 */
	private String outputStyle;

	/**
	 * Disable line comments.
	 *
	 * @parameter
	 */
	private Boolean noLineComments;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			rubyVersionCommandExec();
			sassVersionCommandExec();
			compassVersionCommandExec();

			compassCommandExec();
		} catch (IOException e) {
			throw new MojoExecutionException("Command failure.", e);
		} catch (InterruptedException e) {
			throw new MojoExecutionException("Command failure.", e);
		}
	}

	private void compassCommandExec() throws IOException, InterruptedException,
			MojoExecutionException {

		List<String> compassScript = buildCompassScript();
		getLog().info("[Execute] " + scriptToString(compassScript));

		ProcessExecutor pe = new ProcessExecutor(compassScript, baseDir);
		int ret = pe.execute();

		for (String s : pe.getResultString()) {
			getLog().info(s);
		}

		for (String s : pe.getErrorResultString()) {
			getLog().error(s);
		}

		if (ret != 0) {
			throw new MojoExecutionException("Compass command failure.");
		}
	}

	private List<String> buildCompassScript() {
		List<String> args = new ArrayList<String>();

		if (isWindows()) {
			args.add("cmd");
			args.add("/c");
		}

		args.add("compass");
		args.add("compile");
		args.add("--force");
		args.add("--time");
		args.add("--trace");
		args.add("--boring");

		if (debugInfo != null && debugInfo == true) {
			args.add("--debug-info");
		}

		if (config != null) {
			args.add("--config");
			args.add(config.getAbsolutePath());
		}

		if (sassDir != null) {
			args.add("--sass-dir");
			args.add(sassDir.getAbsolutePath());
		}

		if (cssDir != null) {
			args.add("--css-dir");
			args.add(cssDir.getAbsolutePath());
		}

		if (imagesDir != null) {
			args.add("--images-dir");
			args.add(imagesDir.getAbsolutePath());
		}

		if (javascriptsDir != null) {
			args.add("--javascripts-dir");
			args.add(javascriptsDir.getAbsolutePath());
		}

		if (fontsDir != null) {
			args.add("--fonts-dir");
			args.add(fontsDir.getAbsolutePath());
		}

		if (outputStyle != null) {
			args.add("--output-style");
			args.add(outputStyle);
		}

		if (environment != null) {
			args.add("--environment");
			args.add(environment);
		}

		if (noLineComments != null && noLineComments == true) {
			args.add("--no-line-comments");
		}

		return args;
	}
}
