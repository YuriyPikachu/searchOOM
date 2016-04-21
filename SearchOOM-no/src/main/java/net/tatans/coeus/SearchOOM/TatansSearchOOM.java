package net.tatans.coeus.SearchOOM;

import android.app.Application;

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
