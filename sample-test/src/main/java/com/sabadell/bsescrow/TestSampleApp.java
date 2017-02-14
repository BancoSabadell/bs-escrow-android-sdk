package com.sabadell.bsescrow;

import android.app.Application;
import android.os.AsyncTask;
import com.sabadell.bs_banking.BsBanking;
import com.sabadell.bs_escrow.BsEscrow;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import rx_activity_result2.RxActivityResult;

public final class TestSampleApp extends Application {
  private BsEscrow bsEscrow;
  private BsBanking bsBanking;

  @Override public void onCreate() {
    super.onCreate();
    RxActivityResult.register(this);

    bsBanking = new BsBanking.Builder()
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
   * Provide a single instance BsBanking for the entire app lifecycle.
   */
  public BsBanking bsBanking() {
    return bsBanking;
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
