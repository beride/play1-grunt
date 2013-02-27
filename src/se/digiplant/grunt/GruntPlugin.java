package se.digiplant.grunt;

import play.Play;
import play.Logger;
import play.PlayPlugin;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GruntPlugin extends PlayPlugin {

	private Process gruntProcess;
	private Thread loggerThread;
	private final String command = "grunt";

	@Override
	public void onLoad() {
		if (Play.mode.isProd())
			return;

		runGrunt("default");

		Logger.info("Grunt:onLoad");
	}

    @Override
    public void onApplicationStart() {
	    if (Play.mode.isProd())
		    return;

	    gruntProcess = runGrunt("watch");

	    Logger.info("Grunt:onApplicationStart");
    }

    @Override
    public void onApplicationStop() {
	    if (gruntProcess != null)
			gruntProcess.destroy();

	    Logger.info("Grunt:onApplicationStop");
    }

	private Process runGrunt(String args) {
		try {
			Process p = new ProcessBuilder(command, args).directory(Play.applicationPath).start();
			inputStreamToOutputStream(p.getErrorStream(), System.out);
			inputStreamToOutputStream(p.getInputStream(), System.out);
			p.waitFor();
		} catch (IOException e) {
			Logger.fatal("Couldn't find grunt");
			e.printStackTrace();
		} catch (InterruptedException e) {
			Logger.fatal("Interrupted" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	private void inputStreamToOutputStream(final InputStream inputStream, final OutputStream out) {
		loggerThread = new Thread(new Runnable() {
			public void run() {
				try {
					int d;
					while ((d = inputStream.read()) != -1) {
						out.write(d);
					}
				} catch (IOException ex) {
					//TODO make a callback on exception.
				}
			}
		});
		loggerThread.setDaemon(true);
		loggerThread.start();
	}
}
