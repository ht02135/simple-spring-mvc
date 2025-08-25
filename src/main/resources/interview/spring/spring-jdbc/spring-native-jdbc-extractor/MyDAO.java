/*
/////////////////////////////
use case for NativeJdbcExtractor and Jdbc4NativeJdbcExtractor
------------------------------------
1>NativeJdbcExtractor
is a Spring Framework interface that allows you to unwrap proxied JDBC 
objects (like Connections or Statements) provided by a connection pool 
to get access to the underlying, vendor-specific objects. This is essential 
for accessing database-specific features not available through the 
standard JDBC API. 

2>Jdbc4NativeJdbcExtractor is a specific implementation of NativeJdbcExtractor 
that uses the java.sql.Wrapper.unwrap() method, introduced in JDBC 4.0, to 
perform the unwrapping
/////////////////////////////
This section includes a DAO class, a main class to run the example, and an 
init.sql file for setting up the database. The MyDAO class demonstrates 
how to use the NativeJdbcExtractor to get the underlying connection and 
perform a vendor-specific action. The main class creates the database 
schema and calls the DAO method.
/////////////////////////////
src/main/java/com/example/dao/MyDAO.java
This class uses Jdbc4NativeJdbcExtractor to unwrap the Connection to its 
native H2Connection type, which isn't possible through the standard 
java.sql.Connection interface.
/////////////////////////////////
hung : they are essentially adaptor that wrap native jdbc object.
----------------------------------
The NativeJdbcExtractor and Jdbc4NativeJdbcExtractor classes in the Spring 
Framework employ the adapter pattern. Their purpose is to adapt the wrapped 
JDBC objects from connection pools into their native, vendor-specific forms
//////////////////////////////////
Expected Output
Table 'users' created.
Performing vendor-specific action...
Got native H2Connection: org.h2.jdbc.JdbcConnection
Database Product Name: H2
*/
package com.example.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.Jdbc4NativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.h2.jdbc.JdbcConnection; // H2-specific class
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import java.sql.Connection;
import java.sql.SQLException;

public class MyDAO {

  private JdbcTemplate jdbcTemplate;
  private NativeJdbcExtractor nativeJdbcExtractor;

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setNativeJdbcExtractor(NativeJdbcExtractor nativeJdbcExtractor) {
    this.nativeJdbcExtractor = nativeJdbcExtractor;
  }

  public void performVendorSpecificAction() {
    System.out.println("Performing vendor-specific action...");
    jdbcTemplate.execute(new ConnectionCallback<Void>() {
      @Override
      public Void doInConnection(Connection con) throws SQLException, DataAccessException {
        // Use the nativeJdbcExtractor to get the native H2Connection
        Connection nativeConnection = nativeJdbcExtractor.getNativeConnection(con);
        
        // Now you can cast it to the vendor-specific class
        JdbcConnection h2Connection = (JdbcConnection) nativeConnection;

        // Perform a vendor-specific action
        System.out.println("Got native H2Connection: " + h2Connection.getClass().getName());
        System.out.println("Database Product Name: " + h2Connection.getMetaData().getDatabaseProductName());
        
        return null;
      }
    });
  }

  public void createTable() {
    jdbcTemplate.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(255))");
    System.out.println("Table 'users' created.");
  }
}