package org.molgenis.database.config;

import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MolgenisConfiguration extends Configuration {

	@NotNull
	@JsonProperty
	private DatabaseConfiguration databaseConfiguration;

	@JsonProperty
	private boolean prettyPrintJson;

	public DatabaseConfiguration getDatabaseConfiguration() {
		return databaseConfiguration;
	}

	public boolean isPrettyPrintJson() {
		return prettyPrintJson;
	}

}
