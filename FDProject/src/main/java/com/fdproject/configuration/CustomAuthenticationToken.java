package com.fdproject.configuration;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken implements Authentication {

	private String userId;
	private String credentials;
	
	public CustomAuthenticationToken(String userId, String credentials, Collection<? extends GrantedAuthority> authorities) {
		super();
        this.userId = userId;
        this.credentials = credentials;
	}
	
	public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
		super();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getCredentials() {
		return this.credentials;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return this.userId;
	}

	@Override
	public boolean isAuthenticated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		// TODO Auto-generated method stub

	}

}
