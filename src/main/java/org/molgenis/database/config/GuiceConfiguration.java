package org.molgenis.database.config;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.setup.Environment;

import java.util.Properties;

import org.molgenis.database.DataService;
import org.molgenis.database.ProjectService;
import org.molgenis.database.repository.EntityInstanceRepository;
import org.molgenis.database.repository.EntityRepository;
import org.molgenis.database.repository.ProjectRepository;
import org.molgenis.database.rest.ProjectResource;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;

public class GuiceConfiguration {
	private MolgenisConfiguration molgenisConfiguration;
	private Injector injector;

	public GuiceConfiguration(MolgenisConfiguration molgenisConfiguration) {
		this.molgenisConfiguration = molgenisConfiguration;
		injector = Guice.createInjector(createJpaModule(), new MolgenisGuiceModule());
	}

	public void configure(Environment environment) {
		if (molgenisConfiguration.isPrettyPrintJson()) {
			environment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		}

		configJersey(environment.jersey());
		configServletEnvironment(environment.servlets());
	}

	private void configJersey(JerseyEnvironment environment) {
		environment.register(injector.getInstance(ProjectResource.class));
	}

	private void configServletEnvironment(ServletEnvironment environment) {
		environment.addFilter("persistFilter", injector.getInstance(PersistFilter.class));
	}

	private static class MolgenisGuiceModule extends AbstractModule {

		@Override
		protected void configure() {
			binder().bind(ProjectRepository.class);
			binder().bind(ProjectService.class);
			binder().bind(EntityInstanceRepository.class);
			binder().bind(EntityRepository.class);
			binder().bind(DataService.class);
		}
	}

	private JpaPersistModule createJpaModule() {
		DatabaseConfiguration databaseConfiguration = molgenisConfiguration.getDatabaseConfiguration();

		Properties properties = new Properties();
		properties.put("javax.persistence.jdbc.driver", databaseConfiguration.getDriver());
		properties.put("javax.persistence.jdbc.url", databaseConfiguration.getUrl());
		properties.put("javax.persistence.jdbc.user", databaseConfiguration.getUsername());
		properties.put("javax.persistence.jdbc.password", databaseConfiguration.getPassword());

		JpaPersistModule jpaModule = new JpaPersistModule("molgenis");
		jpaModule.properties(properties);

		return jpaModule;
	}
}
