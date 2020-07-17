package be.vdab.dao;

public class JdbcPass {
    public static final String JDBCURL = "jdbc:mysql://localhost:3306/bitcoindb?serverTimezone=UTC";
    public static final String JDBCUSER = "root";
    public static final String PASSW = "Kolokolsedesed1";

    public static String getJDBCURL() {
        return JDBCURL;
    }

    public static String getJDBCUSER() {
        return JDBCUSER;
    }

    public static String getPASSW() {
        return PASSW;
    }
}
