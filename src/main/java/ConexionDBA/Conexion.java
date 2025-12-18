package ConexionDBA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class Conexion {

    private static final String IP = "localhost";
    private static final int PUERTO = 3306;
    private static final String SCHEMA = "Proyecto_Videojuegos";
    private static final String USER = "root";
    private static final String PASSWORD = "010418";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;

    private static Conexion instance;
    private DataSource dataSource;

    private Conexion() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            PoolProperties p = new PoolProperties();
            p.setUrl(URL);
            p.setDriverClassName("com.mysql.cj.jdbc.Driver");
            p.setUsername(USER);
            p.setPassword(PASSWORD);
            p.setJmxEnabled(true);
            p.setTestWhileIdle(false);
            p.setTestOnBorrow(true);
            p.setValidationQuery("SELECT 1");
            p.setTestOnReturn(false);
            p.setValidationInterval(30000);
            p.setTimeBetweenEvictionRunsMillis(30000);
            p.setMaxActive(100);
            p.setInitialSize(10);
            p.setMaxWait(10000);
            p.setRemoveAbandonedTimeout(60);
            p.setMinEvictableIdleTimeMillis(30000);
            p.setMinIdle(10);
            p.setLogAbandoned(true);
            p.setRemoveAbandoned(true);
            p.setJdbcInterceptors(
                    "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"
                            + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
            dataSource = new DataSource(p);
            dataSource.setPoolProperties(p);

        } catch (ClassNotFoundException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public Connection getConnect() {

        try {
            return dataSource.getConnection();
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null;
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public void cerrarPool() {
        if (dataSource != null) {
            try {
                dataSource.close();
                dataSource = null;
                instance = null;
                System.out.println("Pool de conexiones cerrado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
