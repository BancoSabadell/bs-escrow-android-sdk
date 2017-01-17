package com.sabadell.bs_escrow.internal.data;

import com.sabadell.bs_escrow.internal.net.BsEscrowApi;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import retrofit2.Response;

import static org.mockito.Mockito.when;

public final class BsEscrowRepositoryTest {
  @Mock BsEscrowApi api;
  @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
  private BsEscrowRepository repositoryUT;
  private static final String ASSET_ID = "asset_id", ADDRESS = "address", PASSWORD = "password";
  private static final int ASSET_PRICE = 1;

  @Before public void before() {
    repositoryUT = new BsEscrowRepository(api);
  }

  @Test public void Verify_stateEscrow_With_Invalid_Id_Asset() {
    repositoryUT.getStateEscrow("")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.getStateEscrow(null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_stateEscrow_Success() {
    Escrow escrow = new Escrow("buyerAddress", "sellerAddress", 1,
        Escrow.State.Held);

    when(api.stateEscrow(ASSET_ID))
        .thenReturn(Observable.just(Response.success(escrow)));

    repositoryUT.getStateEscrow(ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue(escrow)
        .assertComplete();
  }

  @Test public void Verify_stateEscrow_Failure() {
    String errorMessage = "Can't get id asset";
    when(api.stateEscrow(ASSET_ID))
        .thenReturn(this.<Escrow>mockErrorResponse(errorMessage));

    repositoryUT.getStateEscrow(ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_With_Invalid_Address_Buyer() {
    repositoryUT.createEscrow("", PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ADDRESS_VALIDATION)
        .assertNotComplete();

    repositoryUT.createEscrow(null, PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ADDRESS_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_With_Invalid_Password() {
    repositoryUT.createEscrow(ADDRESS, "", ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.createEscrow(ADDRESS, null, ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_With_Invalid_Address_Seller() {
    repositoryUT.createEscrow(ADDRESS, PASSWORD, "", ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ADDRESS_VALIDATION)
        .assertNotComplete();

    repositoryUT.createEscrow(ADDRESS, PASSWORD, null, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ADDRESS_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_With_Invalid_Asset_Price() {
    repositoryUT.createEscrow(ADDRESS, PASSWORD, ADDRESS, 0, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ASSET_PRICE_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_With_Invalid_Asset_Id() {
    repositoryUT.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_createEscrow_Success() {
    when(api.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_createEscrow_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.createEscrow(ADDRESS, PASSWORD, ADDRESS, ASSET_PRICE, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowArbitrating_With_Invalid_Password() {
    repositoryUT.cancelEscrowArbitrating("", ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrowArbitrating(null, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowArbitrating_With_Invalid_Asset_Id() {
    repositoryUT.cancelEscrowArbitrating(PASSWORD, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrowArbitrating(PASSWORD, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowArbitrating_Success() {
    when(api.cancelEscrowArbitrating(PASSWORD, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.cancelEscrowArbitrating(PASSWORD, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_cancelEscrowArbitrating_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.cancelEscrowArbitrating(PASSWORD, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.cancelEscrowArbitrating(PASSWORD, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrow_With_Invalid_Password() {
    repositoryUT.cancelEscrow("", ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrow(null, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrow_With_Invalid_Asset_Id() {
    repositoryUT.cancelEscrow(PASSWORD, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrow(PASSWORD, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrow_Success() {
    when(api.cancelEscrow(PASSWORD, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.cancelEscrow(PASSWORD, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_cancelEscrow_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.cancelEscrow(PASSWORD, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.cancelEscrow(PASSWORD, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowProposal_With_Invalid_Password() {
    repositoryUT.cancelEscrowProposal("", ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrowProposal(null, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowProposal_With_Invalid_Asset_Id() {
    repositoryUT.cancelEscrowProposal(PASSWORD, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.cancelEscrowProposal(PASSWORD, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_cancelEscrowProposal_Success() {
    when(api.cancelEscrowProposal(PASSWORD, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.cancelEscrowProposal(PASSWORD, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_cancelEscrowProposal_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.cancelEscrowProposal(PASSWORD, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.cancelEscrowProposal(PASSWORD, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_validateCancelEscrowProposal_With_Invalid_Password() {
    repositoryUT.validateCancelEscrowProposal("", ASSET_ID, true)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.validateCancelEscrowProposal(null, ASSET_ID, true)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_validateCancelEscrowProposal_With_Invalid_Asset_Id() {
    repositoryUT.validateCancelEscrowProposal(PASSWORD, "", true)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.validateCancelEscrowProposal(PASSWORD, null, true)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_validateCancelEscrowProposal_Success() {
    when(api.validateCancelEscrowProposal(PASSWORD, ASSET_ID, true))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.validateCancelEscrowProposal(PASSWORD, ASSET_ID, true)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_validateCancelEscrowProposal_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.validateCancelEscrowProposal(PASSWORD, ASSET_ID, true))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.validateCancelEscrowProposal(PASSWORD, ASSET_ID, true)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrowArbitrating_With_Invalid_Password() {
    repositoryUT.fulfillEscrowArbitrating("", ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.fulfillEscrowArbitrating(null, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrowArbitrating_With_Invalid_Asset_Id() {
    repositoryUT.fulfillEscrowArbitrating(PASSWORD, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.fulfillEscrowArbitrating(PASSWORD, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrowArbitrating_Success() {
    when(api.fulfillEscrowArbitrating(PASSWORD, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.fulfillEscrowArbitrating(PASSWORD, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_fulfillEscrowArbitrating_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.fulfillEscrowArbitrating(PASSWORD, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.fulfillEscrowArbitrating(PASSWORD, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrow_With_Invalid_Password() {
    repositoryUT.fulfillEscrow("", ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();

    repositoryUT.fulfillEscrow(null, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.PASSWORD_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrow_With_Invalid_Asset_Id() {
    repositoryUT.fulfillEscrow(PASSWORD, "")
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();

    repositoryUT.fulfillEscrow(PASSWORD, null)
        .test()
        .assertNoValues()
        .assertErrorMessage(BsEscrowRepository.ID_ASSET_VALIDATION)
        .assertNotComplete();
  }

  @Test public void Verify_fulfillEscrow_Success() {
    when(api.fulfillEscrow(PASSWORD, ASSET_ID))
        .thenReturn(Observable.just(Response.success(new Tx("tx"))));

    repositoryUT.fulfillEscrow(PASSWORD, ASSET_ID)
        .test()
        .assertNoErrors()
        .assertValueCount(1)
        .assertValue("tx")
        .assertComplete();
  }

  @Test public void Verify_fulfillEscrow_Failure() {
    String errorMessage = "Can't fulfill";

    when(api.fulfillEscrow(PASSWORD, ASSET_ID))
        .thenReturn(this.<Tx>mockErrorResponse(errorMessage));

    repositoryUT.fulfillEscrow(PASSWORD, ASSET_ID)
        .test()
        .assertNoValues()
        .assertErrorMessage(errorMessage)
        .assertNotComplete();
  }

  private <T> Observable<Response<T>> mockErrorResponse(String message) {
    Response<T> response = Response.error(404, ResponseBody.create(null, message));
    return Observable.just(response);
  }
}
