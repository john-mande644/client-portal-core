package com.owd.core.managers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johnwholtman
 * Date: 9/3/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResultManager {
private final static Logger log =  LogManager.getLogger();

    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;
    private Connection connection = null;

    /**
     *
     * @param sql
     * @param parameters
     */
    public ResultManager(String sql, List<?> parameters) {
        this.prepareStatement(sql);
        this.setParameters(parameters);
    }

//    public static ResultSet getResultSet(String sql, List<?> parameters) {
//        try {
//            ResultManager resultManager = new ResultManager(sql, parameters);
//            ResultSet resultSet = resultManager.getResultSet();
//            resultManager.closeConnection();
//            return resultSet;
//        } catch (Exception e) {
//            System.err.println("Error static ResultManager.getResultSet: " + e.getLocalizedMessage());
//            return null;
//        }
//    }

    /**
     * Create prepared statement from SQL String.
     *
     * @param sql
     * @throws RuntimeException
     */
    public void prepareStatement(String sql) throws RuntimeException {

        try {
            connection = ConnectionManager.getConnection();
            this.preparedStatement = connection.prepareStatement(sql);

        } catch (SQLException se) {
            System.err.println(se.getLocalizedMessage());
            throw new RuntimeException(se);
        }
        catch (Exception e) {
             System.err.println("ResultManager.prepareStatement" + e.getLocalizedMessage());
             throw new RuntimeException(e);
        }
    }

    /**
     * Set query parameters from List of values.
     * @param values
     */
    public void setParameters(List<?> values) {

        try {
            for (int i = 0; i < values.size(); i++) {

                if (values.get(i) instanceof String) {
                    this.preparedStatement.setString(i + 1, (String)values.get(i));

                } else if (values.get(i) instanceof Integer) {
                    this.preparedStatement.setInt(i + 1, (Integer)values.get(i));
                } else if (values.get(i) instanceof Float) {
                    this.preparedStatement.setFloat(i + 1, (Float)values.get(i));
                }  else if (values.get(i) instanceof Double) {
                    this.preparedStatement.setDouble(i + 1, (Double)values.get(i));
                } else if (values.get(i) instanceof Date) {
                    this.preparedStatement.setDate(i + 1, (Date)values.get(i));
                }
            }
        } catch (Exception e) {
            System.err.println("Error ResultManager.setParameters: " + e.getLocalizedMessage());
            e.printStackTrace();

            throw new RuntimeException(e);
        }
    }


    /**
     * Get result set from prepared statement.
     *
     * @return
     */
    public ResultSet getResultSet() {

         this.resultSet = null;

         try {
             if (this.preparedStatement == null) {
                 System.err.println("Error ResultManager.getResultSet: Prepared statement not set.");
                 throw new RuntimeException("Error ResultManager.getResultSet: Prepared statement not set.");
             }
             this.resultSet = preparedStatement.executeQuery();

         } catch (Exception e) {
             System.err.println("Error ResultManager.getResultSet: " + e.getLocalizedMessage());
             e.printStackTrace();
             this.closeConnectionComponents();
         }
         return this.resultSet;
    }

    /**
     * Close all connection components.
     */
    public void closeConnectionComponents() {
        try {
            if (this.resultSet != null) {
                this.resultSet.close();
                this.resultSet = null;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }

        try {
            if (this.preparedStatement != null) {
                this.preparedStatement.close();
                this.preparedStatement = null;
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
        try {
            ConnectionManager.freeConnection(this.connection);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
