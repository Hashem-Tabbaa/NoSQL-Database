package com.database.atypon.DBMS;

import com.database.atypon.DBMS.database_system.connection.DBConnection;
import com.database.atypon.DBMS.database_system.connection.DatabaseOperations;
import com.database.atypon.DBMS.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@SpringBootApplication
public class DbmsApplication{

	public static void main(String[] args) {
		SpringApplication.run(DbmsApplication.class, args);
	}

	@Component
	private class server implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {
		@Override
		public void customize(ConfigurableWebServerFactory factory) {
			// Setting the port number
			factory.setPort(8078);
		}
	}
}
