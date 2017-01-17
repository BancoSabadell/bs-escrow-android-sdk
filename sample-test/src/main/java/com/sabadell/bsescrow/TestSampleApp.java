package com.sabadell.bsescrow;

import android.app.Application;
import android.os.AsyncTask;
import com.sabadell.bs_escrow.BsEscrow;
import com.sabadell.bs_token.BsToken;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx_activity_result2.RxActivityResult;

public final class TestSampleApp extends Application {
  private BsEscrow bsEscrow;
  private BsToken bsToken;

  @Override public void onCreate() {
    super.onCreate();
    RxActivityResult.register(this);

    bsToken = new BsToken.Builder()
        .development()
        .build(this);

    bsEscrow = new BsEscrow.Builder()
        .development()
        .build();
  }

  /**
   * Provide a single instance BsEscrow for the entire app lifecycle.
   */
  public BsEscrow bsEscrow() {
    return bsEscrow;
  }

  /**
   * Provide a single instance BsToken for the entire app lifecycle.
   */
  public BsToken bsToken() {
    return bsToken;
  }

  /**
   * Sync with main thread after subscribing to observables emitting from data layer.
   */
  public Scheduler mainThread() {
    return AndroidSchedulers.mainThread();
  }

  /**
   * Using this executor as the scheduler for all async operations allow us to tell espresso when
   * the app is in an idle state in order to wait for the response.
   */
  public Scheduler backgroundThread() {
    return Schedulers.from(AsyncTask.THREAD_POOL_EXECUTOR);
  }
}
