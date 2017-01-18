package com.sabadell.bs_escrow.internal.data;

import com.sabadell.bs_escrow.BsEscrow;
import com.sabadell.bs_escrow.internal.net.BsEscrowApi;
import com.sabadell.bs_escrow.internal.net.NetworkResponse;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public final class BsEscrowRepository implements BsEscrow {
  private final BsEscrowApi api;
  private final NetworkResponse networkResponse;
  static final String PASSWORD_VALIDATION = "Invalid password",
      ASSET_PRICE_VALIDATION = "Invalid asset price",
      ADDRESS_VALIDATION = "Invalid account",
      ID_ASSET_VALIDATION = "Invalid idAsset";

  public BsEscrowRepository(BsEscrowApi api) {
    this.api = api;
    this.networkResponse = new NetworkResponse();
  }

  @Override public Observable<Escrow> getStateEscrow(String idAsset) {
    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.stateEscrow(idAsset)
        .compose(networkResponse.<Escrow>process());
  }

  @Override public Observable<String> createEscrow(String addressBuyer, String passwordBuyer,
      String addressSeller, int assetPrice, String idAsset) {
    if (addressBuyer == null
        || addressBuyer.isEmpty()
        || addressSeller == null
        || addressSeller.isEmpty()) {
      return Observable.error(new RuntimeException(ADDRESS_VALIDATION));
    }

    if (passwordBuyer == null || passwordBuyer.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (assetPrice == 0) return Observable.error(new RuntimeException(ASSET_PRICE_VALIDATION));

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.createEscrow(addressBuyer, passwordBuyer, addressSeller, assetPrice, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override public Observable<String> cancelEscrowArbitrating(String passwordOwner,
      String idAsset) {
    if (passwordOwner == null || passwordOwner.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.cancelEscrowArbitrating(passwordOwner, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override public Observable<String> cancelEscrow(String passwordSeller, String idAsset) {
    if (passwordSeller == null || passwordSeller.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.cancelEscrow(passwordSeller, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override public Observable<String> cancelEscrowProposal(String passwordBuyer, String idAsset) {
    if (passwordBuyer == null || passwordBuyer.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.cancelEscrowProposal(passwordBuyer, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override
  public Observable<String> validateCancelEscrowProposal(String passwordSeller, String idAsset,
      boolean validate) {
    if (passwordSeller == null || passwordSeller.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.validateCancelEscrowProposal(passwordSeller, idAsset, validate)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override public Observable<String> fulfillEscrowArbitrating(String passwordOwner,
      String idAsset) {
    if (passwordOwner == null || passwordOwner.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.fulfillEscrowArbitrating(passwordOwner, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }

  @Override public Observable<String> fulfillEscrow(String passwordBuyer, String idAsset) {
    if (passwordBuyer == null || passwordBuyer.isEmpty()) {
      return Observable.error(new RuntimeException(PASSWORD_VALIDATION));
    }

    if (idAsset == null || idAsset.isEmpty()) {
      return Observable.error(new RuntimeException(ID_ASSET_VALIDATION));
    }

    return api.fulfillEscrow(passwordBuyer, idAsset)
        .compose(networkResponse.<Tx>process())
        .map(new Function<Tx, String>() {
          @Override public String apply(Tx tx) throws Exception {
            return tx.getValue();
          }
        });
  }
}
