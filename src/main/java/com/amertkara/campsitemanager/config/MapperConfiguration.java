package com.amertkara.campsitemanager.config;

import static com.amertkara.campsitemanager.config.ServiceConstants.DATE_FORMAT;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.builtin.DateToStringConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class MapperConfiguration {
	@Bean
	public MapperFactory mapperFactory() {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
		mapperFactory.getConverterFactory().registerConverter(new DateToStringConverter(DATE_FORMAT, Locale.US));
		return mapperFactory;
	}
}
