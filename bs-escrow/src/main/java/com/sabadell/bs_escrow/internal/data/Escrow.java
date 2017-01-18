package com.sabadell.bs_escrow.internal.data;

import com.google.gson.annotations.SerializedName;

public final class Escrow {
  private final String buyer, seller;
  private final int assetPrice;
  private final State state;

  public Escrow(String buyer, String sellerAddress, int assetPrice,
      State state) {
    this.buyer = buyer;
    this.seller = sellerAddress;
    this.assetPrice = assetPrice;
    this.state = state;
  }

  public String getBuyer() {
    return buyer;
  }

  public String getSeller() {
    return seller;
  }

  public int getAssetPrice() {
    return assetPrice;
  }

  public State getState() {
    return state;
  }

  public enum State {
    @SerializedName("0")
    Held(),
    @SerializedName("1")
    Cancelled(),
    @SerializedName("2")
    Fulfilled(),
    @SerializedName("3")
    BuyerProposeCancellation(),
    @SerializedName("4")
    SellerDisagreeProposalCancellation();
  }
}
