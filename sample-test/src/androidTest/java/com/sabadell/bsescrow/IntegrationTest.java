package com.sabadell.bsescrow;

import android.support.test.runner.AndroidJUnit4;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static com.sabadell.bsescrow.Raw.balanceAddressBuyer;
import static com.sabadell.bsescrow.Raw.cancelEscrow;
import static com.sabadell.bsescrow.Raw.cancelEscrowArbitrating;
import static com.sabadell.bsescrow.Raw.cancelEscrowProposal;
import static com.sabadell.bsescrow.Raw.cashIn;
import static com.sabadell.bsescrow.Raw.createEscrow;
import static com.sabadell.bsescrow.Raw.fulfillEscrow;
import static com.sabadell.bsescrow.Raw.fulfillEscrowArbitrating;
import static com.sabadell.bsescrow.Raw.randomString;
import static com.sabadell.bsescrow.Raw.stateEscrow;
import static com.sabadell.bsescrow.Raw.validateCancelEscrowProposal;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class IntegrationTest {
  private final static String amount = "5032", assetPrice = "100";
  private String
      idAsset1 = randomString(),
      idAsset2 = randomString(),
      idAsset3 = randomString(),
      idAsset4 = randomString();

  @Test public void _a01_Verify_cashIn_Buyer() {
    cashIn(amount);
  }

  @Test public void _a02_Verify_balanceAddressBuyer() {
    balanceAddressBuyer(amount);
  }

  @Test public void _a03_Verify_createEscrow() {
    createEscrow(assetPrice, idAsset1);
  }

  @Test public void _a04_Verify_stateEscrow() {
    stateEscrow(idAsset1, "0");
  }

  @Test public void _a05_Verify_cancelEscrowProposal() {
    cancelEscrowProposal(idAsset1);
    stateEscrow(idAsset1, "3");
  }

  @Test public void _a06_Verify_validateCancelEscrowProposal() {
    validateCancelEscrowProposal(idAsset1, false);
    stateEscrow(idAsset1, "4");
  }

  @Test public void _a07_Verify_cancelEscrowArbitrating() {
    cancelEscrowArbitrating(idAsset1);
    stateEscrow(idAsset1, "1");
  }

  @Test public void _a08_Verify_cancelEscrow() {
    createEscrow(assetPrice, idAsset2);
    cancelEscrow(idAsset2);
    stateEscrow(idAsset2, "1");
  }

  @Test public void _a09_Verify_fulfillEscrowArbitrating() {
    createEscrow(assetPrice, idAsset3);
    cancelEscrowProposal(idAsset3);
    validateCancelEscrowProposal(idAsset3, false);
    fulfillEscrowArbitrating(idAsset3);
    stateEscrow(idAsset3, "2");
  }

  @Test public void _b01_Verify_fulfillEscrow() {
    createEscrow(assetPrice, idAsset4);
    fulfillEscrow(idAsset4);
    stateEscrow(idAsset4, "2");
  }
}
