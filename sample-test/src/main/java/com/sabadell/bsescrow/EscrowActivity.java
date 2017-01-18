package com.sabadell.bsescrow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.TextView;

public final class EscrowActivity extends AppCompatActivity {
  private TestSampleApp app;
  private TextView tv_output, et_address_buyer, et_address_seller, et_password, et_amount,
      et_asset_id;
  private CheckBox cb_validate;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.escrow_activity);

    app = (TestSampleApp) getApplication();
    tv_output = (TextView) findViewById(R.id.tv_output);
    et_address_buyer = (TextView) findViewById(R.id.et_address_buyer);
    et_address_seller = (TextView) findViewById(R.id.et_address_seller);
    et_password = (TextView) findViewById(R.id.et_password);
    et_amount = (TextView) findViewById(R.id.et_amount);
    et_asset_id = (TextView) findViewById(R.id.et_asset_id);
    cb_validate = (CheckBox) findViewById(R.id.cb_validate);

    findViewById(R.id.bt_cash_in).setOnClickListener(view -> {
      int amount = Integer.parseInt(et_amount.getText().toString());
      String address = et_address_buyer.getText().toString();

      app.bsToken().cashIn(this, amount, address).subscribe(result -> {
        int resultCode = result.resultCode();

        if (resultCode == RESULT_OK) {
          tv_output.setText(R.string.success);
        } else {
          tv_output.setText(R.string.failure);
        }
      }, error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_create_escrow).setOnClickListener(view -> {
      String addressBuyer = et_address_buyer.getText().toString();
      String password = et_password.getText().toString();
      String addressSeller = et_address_seller.getText().toString();
      int amount = Integer.parseInt(et_amount.getText().toString());
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .createEscrow(addressBuyer, password, addressSeller, amount, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_cancel_escrow_arbitrating).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .cancelEscrowArbitrating(password, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_cancel_escrow).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .cancelEscrow(password, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_cancel_escrow_proposal).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .cancelEscrowProposal(password, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_validate_cancel_escrow_proposal).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .validateCancelEscrowProposal(password, assetId, cb_validate.isChecked())
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_fulfill_escrow_arbitrating).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .fulfillEscrowArbitrating(password, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_fulfill_escrow).setOnClickListener(view -> {
      String password = et_password.getText().toString();
      String assetId = et_asset_id.getText().toString();

      app.bsEscrow()
          .fulfillEscrow(password, assetId)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_balance_of_buyer).setOnClickListener(view -> {
      String address = et_address_buyer.getText().toString();

      app.bsToken().balanceOf(address)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(balance -> tv_output.setText(String.valueOf(balance)),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_balance_of_seller).setOnClickListener(view -> {
      String address = et_address_seller.getText().toString();

      app.bsToken().balanceOf(address)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_state_escrow).setOnClickListener(view -> {
      String idAsset = et_asset_id.getText().toString();

      app.bsEscrow().getStateEscrow(idAsset)
          .subscribeOn(app.backgroundThread())
          .observeOn(app.mainThread())
          .subscribe(escrow -> tv_output.setText(String.valueOf(escrow.getState().ordinal())),
              error -> tv_output.setText(error.getMessage()));
    });

    findViewById(R.id.bt_clear_cache).setOnClickListener(view -> {
      app.bsToken().clearCache()
          .subscribe(tx -> tv_output.setText(R.string.success),
              error -> tv_output.setText(error.getMessage()));
    });
  }
}
