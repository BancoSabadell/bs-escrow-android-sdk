package com.sabadell.bs_escrow;

import android.support.annotation.CheckResult;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sabadell.bs_escrow.internal.data.BsEscrowRepository;
import com.sabadell.bs_escrow.internal.data.Escrow;
import com.sabadell.bs_escrow.internal.net.BsEscrowApi;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Interface to operate with BsEscrow service.
 */
public interface BsEscrow {
  /**
   * Check the escrow state for the supplied idAsset.
   *
   * @param idAsset the identifier of the asset being checked
   * @return The current state for the escrow: hold, disburse or cancel.
   */
  @CheckResult Observable<Escrow> getStateEscrow(final String idAsset);

  /**
   * The user pay for the asset using escrow.
   *
   * @param addressBuyer the buyer address
   * @param passwordBuyer the buyer password address.
   * @param assetPrice the price of the asset
   * @param idAsset the identifier of the asset being marketed
   * @param addressSeller the seller address
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> createEscrow(final String addressBuyer,
      final String passwordBuyer, final String addressSeller, final int assetPrice,
      final String idAsset);

  /**
   * Call this method when the state of the escrow is SellerDisagreeProposalCancellation and it is
   * required to manually cancel the escrow.
   *
   * @param passwordOwner the password of the contract owner's.
   * @param idAsset the identifier of the asset being arbitrated.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> cancelEscrowArbitrating(final String passwordOwner,
      final String idAsset);

  /**
   * The seller cancel the escrow and the buyer gets his money back.
   *
   * @param passwordSeller the password associated with the seller account.
   * @param idAsset the identifier of the asset being escrow.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> cancelEscrow(final String passwordSeller, final String idAsset);

  /**
   * The buyer proposes to the seller to cancel the escrow.
   *
   * @param passwordBuyer the password associated with the buyer account.
   * @param idAsset the identifier of the asset being escrow.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> cancelEscrowProposal(final String passwordBuyer,
      final String idAsset);

  /**
   * The seller validates the proposition of the seller about to cancel the escrow.
   *
   * @param passwordSeller the password associated with the seller account.
   * @param idAsset the identifier of the asset being escrow.
   * @param validate true if the proposition should be accepted.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> validateCancelEscrowProposal(String passwordSeller,
      String idAsset, boolean validate);

  /**
   * Call this method when the state of the escrow is SellerDisagreeProposalCancellation and it is
   * required to manually fulfill the escrow.
   *
   * @param passwordOwner the password of the contract owner's.
   * @param idAsset the identifier of the asset being arbitrated.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> fulfillEscrowArbitrating(final String passwordOwner,
      final String idAsset);

  /**
   * The buyer fulfill the escrow and the seller gets the money.
   *
   * @param passwordBuyer the password associated with the buyer account.
   * @param idAsset the identifier of the asset being escrow.
   * @return The tx of the Ethereum transaction.
   */
  @CheckResult Observable<String> fulfillEscrow(String passwordBuyer, String idAsset);

  class Builder {
    private boolean dev;

    /**
     * Call this to let the api resolve against an instance of ethereumjs-testrpc.
     */
    public Builder development() {
      this.dev = true;
      return this;
    }

    public BsEscrow build() {
      String url = dev ? BsEscrowApi.URL_BASE_DEV : BsEscrowApi.URL_BASE;

      OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
          .readTimeout(60, TimeUnit.SECONDS)
          .writeTimeout(60, TimeUnit.SECONDS)
          .connectTimeout(60, TimeUnit.SECONDS)
          .build();

      return new BsEscrowRepository(new Retrofit.Builder()
          .baseUrl(url)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .client(okHttpClient)
          .build().create(BsEscrowApi.class));
    }
  }
}
