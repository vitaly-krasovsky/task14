package com.epam.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author vkrasovsky
 */
public final class RepositoryHelper {
    private static final Logger LOGGER = LogManager.getLogger(AccountRepositoryImpl.class);

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("task14");

    private static final String SQL_CREATE_CLIENT_TABLE = "CREATE TABLE CLIENT (CLIENT_ID INTEGER NOT NULL, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255), AGE INTEGER, CONSTRAINT PK_CLIENT PRIMARY KEY (CLIENT_ID))";
    private static final String SQL_CREATE_ACCOUNT_TABLE = "CREATE TABLE ACCOUNT (ACCOUNT_ID INTEGER NOT NULL, DESCRIPTION VARCHAR(255), CLIENT_ID INTEGER NOT NULL, CONSTRAINT PK_ACCOUNT PRIMARY KEY (ACCOUNT_ID), CONSTRAINT FK_ACCOUNT_CLIENT FOREIGN KEY (CLIENT_ID) REFERENCES CLIENT(CLIENT_ID))";

    private RepositoryHelper() {
    }

    public static EntityManagerFactory getEntityManager() {
        //TODO: for postrges use/rename persistence.xml.postgres
        return ENTITY_MANAGER_FACTORY;
    }

    /**
     * Check created schema for in memory derby
     *
     * @param args the args
     */
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            LOGGER.debug("Connecting to a selected database...");
            Connection connection = DriverManager.getConnection("jdbc:derby:memory:demo");
//            Connection connection = DriverManager.getConnection("jdbc:derby:memory:demo;create=true");
            LOGGER.debug("Connected database successfully...");
            LOGGER.debug("Creating table in given database...");
            Statement statement = connection.createStatement();

//            statement.executeUpdate(SQL_CREATE_CLIENT_TABLE);
//            statement.executeUpdate(SQL_CREATE_ACCOUNT_TABLE);

//            https://db.apache.org/derby/docs/10.0/manuals/reference/sqlj155.html#HDRSII-SISTABS-22441
//            https://db.apache.org/derby/docs/10.0/manuals/reference/sqlj165.html
//            https://db.apache.org/derby/docs/10.0/manuals/reference/sqlj157.html#HDRSII-SISTABS-23241

            String sqlAccount = "select * FROM sys.systables t, sys.syscolumns WHERE TABLEID = REFERENCEID and tablename = 'ACCOUNT' ";
            ResultSet resultSet1 = statement.executeQuery(sqlAccount);
            while (resultSet1.next()) {
                LOGGER.debug("COLUMNNAME: " + resultSet1.getString("COLUMNNAME"));
            }
            resultSet1.close();

            sqlAccount = "select * FROM sys.systables t, sys.sysconstraints c WHERE t.TABLEID = c.TABLEID and tablename = 'ACCOUNT' ";
            ResultSet resultSet2 = statement.executeQuery(sqlAccount);
            while (resultSet2.next()) {
                LOGGER.debug("CONSTRAINTNAME: " + resultSet2.getString("CONSTRAINTNAME"));
            }
            resultSet2.close();

        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
        } finally {

        }
    }
}
