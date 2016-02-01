package org.molgenis.database;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import org.molgenis.database.config.GuiceConfiguration;
import org.molgenis.database.config.MolgenisConfiguration;

public class MolgenisApplication extends Application<MolgenisConfiguration> {

	public static void main(String[] args) throws Exception {
		new MolgenisApplication().run(args);
	}

	@Override
	public String getName() {
		return "molgenis-database";
	}

	@Override
	public void initialize(Bootstrap<MolgenisConfiguration> bootstrap) {
	}

	@Override
	public void run(MolgenisConfiguration configuration, Environment environment) throws Exception {
		GuiceConfiguration guice = new GuiceConfiguration(configuration);
		guice.configure(environment);
	}

}
