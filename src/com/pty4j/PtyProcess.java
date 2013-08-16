package com.pty4j;

import com.pty4j.unix.Pty;
import com.pty4j.unix.UnixPtyProcess;
import com.pty4j.util.PtyUtil;
import com.pty4j.windows.WinPtyProcess;
import com.sun.jna.Platform;

import java.io.IOException;
import java.util.Map;

/**
 * @author traff
 */
public abstract class PtyProcess extends Process {
  public abstract boolean isRunning();

  public abstract void setWinSize(WinSize winSize);

  public abstract WinSize getWinSize() throws IOException;

  public static PtyProcess exec(String[] command) throws IOException {
    return exec(command, (String[])null, null);
  }

  public static PtyProcess exec(String[] command, String[] environment) throws IOException {
    return exec(command, environment, null);
  }

  public static PtyProcess exec(String[] command, Map<String, String> environment, String workingDirectory) throws IOException {
    return exec(command, PtyUtil.toStringArray(environment), workingDirectory);
  }

  public static PtyProcess exec(String[] command, String[] environment, String workingDirectory) throws IOException {
    if (Platform.isWindows()) {
      return new WinPtyProcess(command, environment, workingDirectory);
    }
    return new UnixPtyProcess(command, environment, workingDirectory, new Pty());
  }
}
