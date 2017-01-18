package com.sabadell.bsescrow;

import android.support.test.espresso.web.webdriver.DriverAtoms;
import android.support.test.espresso.web.webdriver.Locator;
import java.security.SecureRandom;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.web.sugar.Web.onWebView;
import static android.support.test.espresso.web.webdriver.DriverAtoms.findElement;
import static android.support.test.espresso.web.webdriver.DriverAtoms.webClick;
import static com.sabadell.bsescrow.R.id.et_address_buyer;
import static com.sabadell.bsescrow.R.id.et_amount;
import static com.sabadell.bsescrow.R.id.et_password;

final class Raw {
  private static final String PASSWORD = "dummy_password",
      ADDRESS_BUYER = "0x4f9b5b1909773719e4e96daebe7425596d4f5f04",
      ADDRESS_SELLER = "0x8000ad8e4bd80411ca94d42fab880271a101d2b8";

  static void cashIn(String amount) {
    onView(withId(et_address_buyer)).perform(replaceText(ADDRESS_BUYER), closeSoftKeyboard());
    onView(withId(et_amount)).perform(replaceText(amount), closeSoftKeyboard());
    onView(withId(R.id.bt_cash_in)).perform(scrollTo(), click());

    onWebView()
        .withElement(findElement(Locator.ID, "inputCard"))
        .perform(DriverAtoms.webKeys("4548812049400004"))
        .withElement(findElement(Locator.ID, "cad1"))
        .perform(DriverAtoms.webKeys("12"))
        .withElement(findElement(Locator.ID, "cad2"))
        .perform(DriverAtoms.webKeys("20"))
        .withElement(findElement(Locator.ID, "codseg"))
        .perform(DriverAtoms.webKeys("123"))
        .withElement(findElement(Locator.ID, "divImgAceptar"))
        .perform(webClick())
        .withElement(findElement(Locator.NAME, "pin"))
        .perform(DriverAtoms.webKeys("123456"))
        .withElement(findElement(Locator.XPATH, "//img[@alt='Aceptar']"))
        .perform(webClick())
        .withElement(findElement(Locator.XPATH, "//input[@alt='Continuar']"))
        .perform(webClick());

    waitTime();
    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void createEscrow(String assetPrice, String assetId) {
    onView(withId(et_address_buyer)).perform(replaceText(ADDRESS_BUYER), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());
    onView(withId(R.id.et_address_seller)).perform(replaceText(ADDRESS_SELLER),
        closeSoftKeyboard());
    onView(withId(R.id.et_amount)).perform(replaceText(assetPrice), closeSoftKeyboard());
    onView(withId(R.id.et_asset_id)).perform(replaceText(assetId), closeSoftKeyboard());

    onView(withId(R.id.bt_create_escrow)).perform(scrollTo(), click());

    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void stateEscrow(String idAsset, String state) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(R.id.bt_state_escrow)).perform(scrollTo(), click());
    onView(withId(R.id.tv_output)).check(matches(withText(state)));
  }

  static void cancelEscrowProposal(String idAsset) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    onView(withId(R.id.bt_cancel_escrow_proposal)).perform(scrollTo(), click());

    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void validateCancelEscrowProposal(String idAsset, boolean valid) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    if (valid) onView(withId(R.id.cb_validate)).perform(scrollTo(), click());

    onView(withId(R.id.bt_validate_cancel_escrow_proposal)).perform(scrollTo(), click());

    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void cancelEscrowArbitrating(String idAsset) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    onView(withId(R.id.bt_cancel_escrow_arbitrating)).perform(scrollTo(), click());
    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void cancelEscrow(String idAsset) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    onView(withId(R.id.bt_cancel_escrow)).perform(scrollTo(), click());
    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void fulfillEscrowArbitrating(String idAsset) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    onView(withId(R.id.bt_fulfill_escrow_arbitrating)).perform(scrollTo(), click());
    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void fulfillEscrow(String idAsset) {
    onView(withId(R.id.et_asset_id)).perform(replaceText(idAsset), closeSoftKeyboard());
    onView(withId(et_password)).perform(replaceText(PASSWORD), closeSoftKeyboard());

    onView(withId(R.id.bt_fulfill_escrow)).perform(scrollTo(), click());
    onView(withId(R.id.tv_output)).check(matches(withText(R.string.success)));
    waitTime();
  }

  static void clearCache() {
    onView(withId(R.id.bt_clear_cache)).perform(scrollTo(), click());
  }

  private static void waitTime() {
    waitN(2500);
  }

  static void waitN(long time) {
    try {
      Thread.sleep(time);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
  private static SecureRandom rnd = new SecureRandom();

  static String randomString() {
    int len = 8;
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++)
      sb.append(AB.charAt(rnd.nextInt(AB.length())));
    return sb.toString();
  }
}
