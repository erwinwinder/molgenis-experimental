package org.molgenis.database.repository;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.sql.SQLException;
import java.util.Map;

import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;
import org.postgresql.util.PGobject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PayloadConverter implements Converter {
	private static final long serialVersionUID = -995736330259741804L;

	@Override
	public Object convertObjectValueToDataValue(Object objectValue, Session session) {
		PGobject po = new PGobject();
		po.setType("json");
		try {
			po.setValue((new ObjectMapper()).writeValueAsString(objectValue));
		} catch (JsonProcessingException | SQLException e) {
			throw new RuntimeException(e);
		}
		return po;
	}

	@Override
	public Object convertDataValueToObjectValue(Object dataValue, Session session) {
		try {
			return (new ObjectMapper()).readValue(((PGobject) dataValue).getValue(), Map.class);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public void initialize(DatabaseMapping mapping, Session session) {
	}
}
