package org.molgenis.database.config;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatabaseConfiguration {

	@JsonProperty
	@NotNull
	private String driver;

	@JsonProperty
	@NotNull
	private String url;

	@JsonProperty
	private String username;

	@JsonProperty
	private String password;

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}