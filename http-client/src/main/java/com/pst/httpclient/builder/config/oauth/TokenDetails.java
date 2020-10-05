package com.pst.httpclient.builder.config.oauth;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class TokenDetails {
	    public String access_token;
	    public String token_type;
	    public int expires_in;
	    public String refresh_token;
	    public String scope;
}
