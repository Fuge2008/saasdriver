package com.small.saasdriver.utils;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

import com.small.saasdriver.entity.MessageCommit;

public interface MessageCommitInterface {

	 
	   
	@FormUrlEncoded
	@POST("Driver/FeedbackIfomation")
	Call<MessageCommit> getUser( @Field("FeedbackMsg") String content,@Field("DriverID") String DriverID,@Field("Identify") String Identify,
			
			@Field("EnterpriseOrLeasing") String index, @Field("CompanyInfoName") String received);
	 
	 
	
	
	
}
