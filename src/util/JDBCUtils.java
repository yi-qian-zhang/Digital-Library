package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * JDBC Tool class using druid connection pool.
 */
public class JDBCUtils
{
    private static DataSource ds;

    static
    {
        try
        {
            Properties pro = new Properties();
            InputStream is = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
            pro.load(is);
            ds = DruidDataSourceFactory.createDataSource(pro);
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    /**
     * Obtain data source.
     *
     * @return Datasource.
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * Get connection to database.
     *
     * @return Connection to database.
     * @throws SQLException Get Connection failed.
     */
    public static Connection getConnection() throws SQLException { return ds.getConnection(); }
}
