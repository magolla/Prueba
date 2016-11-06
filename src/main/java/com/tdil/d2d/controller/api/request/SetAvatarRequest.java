package com.tdil.d2d.controller.api.request;

public class SetAvatarRequest extends ApiRequest {
	
	// TODO: longitud?
	private String avatarBase64;

	public String getAvatarBase64() {
		return avatarBase64;
	}

	public void setAvatarBase64(String avatarBase64) {
		this.avatarBase64 = avatarBase64;
	}
	
}
