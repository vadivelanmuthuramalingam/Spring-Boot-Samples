package com.pst.httpclient.builder.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptorOkHttpClient implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();

        Request request = original.newBuilder()
            .header("Authorization", "Barear " + " token details")
            .method(original.method(), original.body())
            .build();

        Response response =  chain.proceed(request);
        
        return response;
	}

}
