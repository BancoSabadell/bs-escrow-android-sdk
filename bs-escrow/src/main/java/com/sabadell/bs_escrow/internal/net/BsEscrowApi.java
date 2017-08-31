package com.sabadell.bs_escrow.internal.net;

import com.sabadell.bs_escrow.internal.data.Balance;
import com.sabadell.bs_escrow.internal.data.Escrow;
import com.sabadell.bs_escrow.internal.data.Tx;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BsEscrowApi {
  String URL_BASE = "http://innochain.innocells.io:8082/escrow/api/v1/";
  String URL_BASE_DEV = "http://admin-hackathon.westeurope.cloudapp.azure.com:8081/escrow/api/v1/";

  @GET("balance/{account}") Observable<Response<Balance>> balanceOf(
      @Path("account") String account);

  @GET("escrows/{assetId}") Observable<Response<Escrow>> stateEscrow(
      @Path("assetId") String assetId);

  @FormUrlEncoded
  @POST("createEscrow") Observable<Response<Tx>> createEscrow(
      @Field("buyer") String addressBuyer,
      @Field("password_buyer") String passwordBuyer,
      @Field("seller") String addressSeller,
      @Field("asset_price") int assetPrice,
      @Field("asset_id") String assetId);

  @FormUrlEncoded
  @POST("cancelEscrowArbitrating") Observable<Response<Tx>> cancelEscrowArbitrating(
      @Field("password_owner") String passwordOwner,
      @Field("asset_id") String assetId);

  @FormUrlEncoded
  @POST("cancelEscrow") Observable<Response<Tx>> cancelEscrow(
      @Field("password_seller") String passwordSeller,
      @Field("asset_id") String assetId);

  @FormUrlEncoded
  @POST("cancelEscrowProposal") Observable<Response<Tx>> cancelEscrowProposal(
      @Field("password_buyer") String passwordBuyer,
      @Field("asset_id") String assetId);

  @FormUrlEncoded
  @POST("validateCancelEscrowProposal") Observable<Response<Tx>> validateCancelEscrowProposal(
      @Field("password_seller") String passwordSeller,
      @Field("asset_id") String assetId,
      @Field("validate") Boolean validate);

  @FormUrlEncoded
  @POST("fulfillEscrowArbitrating") Observable<Response<Tx>> fulfillEscrowArbitrating(
      @Field("password_owner") String passwordOwner,
      @Field("asset_id") String assetId);

  @FormUrlEncoded
  @POST("fulfillEscrow") Observable<Response<Tx>> fulfillEscrow(
      @Field("password_buyer") String passwordBuyer,
      @Field("asset_id") String assetId);
}
