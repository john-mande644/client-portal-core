package com.owd.hibernate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by stewartbuskirk1 on 3/25/15.
 */
public class BulkImporter {
private final static Logger log =  LogManager.getLogger();

    public static void main(String[] args) throws Exception {


        Session sess = HibernateSession.currentSession();
        //   sess.getTransaction().begin();
        final List<String[]> list = new ArrayList<String[]>();

        File f = new File("audit.csv");

        int i=0;
        Scanner scanner = new Scanner(f,"UTF-8");//.useDelimiter("\\Z");
        while (scanner.hasNextLine()) {
            String  line = scanner.nextLine();
            System.out.println((++i)+":"+line);
            list.add(line.split(","));
        }
        scanner.close();


        log.debug(list.size() + " items");
        sess.doWork(new Work() {
            @Override
            public void execute(Connection conn) throws SQLException {
                PreparedStatement pstmt = null;
                try {
                    String sqlInsert ="INSERT\n" +
                            "INTO\n" +
                            "    dbo.audit_uspsfeb2016\n" +
                            "    (\n" +
                            "        tracking,\n" +
                            "        actual" +
                            "    )\n" +
                            "    VALUES\n" +
                            "    (\n" +
                            "        ?,\n" +
                            "        ?\n" +
                            "    )";
                    pstmt = conn.prepareStatement(sqlInsert);
                    int i = 1;
                    for (String[] name : list) {
                        pstmt.setString(1, name[0]);
                        pstmt.setString(2, name[1]);
                        pstmt.addBatch();
                        log.debug(name[0]+":"+name[1]);
                        //20 : JDBC batch size
                        log.debug("Batch Level:" + (i % 200));
                        if ((i % 200) == 0) {
                            log.debug("batch save");
                            pstmt.executeBatch();
                            log.debug("batch saved");
                        }
                        i++;
                    }
                    pstmt.executeBatch();
                    log.debug("final batch saved");
                } finally {
                    pstmt.close();
                }
            }
        });


        sess.getTransaction().commit();
        sess.close();
    }
}
