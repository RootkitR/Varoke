package ch.rootkit.varoke.database;

import java.sql.Connection;
import java.util.Date;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.utils.Logger;

public class Database {

	private HikariDataSource hikari;
	
	public Database() throws Exception {
		final long started = new Date().getTime();
		Logger.printVaroke("Connecting to Database ");
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://" + Configuration.get("mysql.db.host") + ":" + Configuration.get("mysql.db.port") + "/" + Configuration.get("mysql.db"));
		config.setUsername(Configuration.get("mysql.db.username"));
		config.setPassword(Configuration.get("mysql.db.password"));
		hikari = new HikariDataSource(config);
		Logger.printLine("(" + (new Date().getTime() - started) +  " ms)");
	}
	public Connection getConnection() throws Exception{
		return hikari.getConnection();
	}
}
