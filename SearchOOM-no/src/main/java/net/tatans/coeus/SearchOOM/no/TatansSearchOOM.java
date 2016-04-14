package net.tatans.coeus.SearchOOM.no;

import android.app.Application;

import net.tatans.coeus.SearchOOM.RefWatcher;

/**
 * A no-op version of {@link TatansSearchOOM} that can be used in release builds.
 */
public final class TatansSearchOOM {

  public static RefWatcher install(Application application) {
    return RefWatcher.DISABLED;
  }

  private TatansSearchOOM() {
    throw new AssertionError();
  }
}
