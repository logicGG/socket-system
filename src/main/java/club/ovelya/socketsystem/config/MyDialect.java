package club.ovelya.socketsystem.config;

import org.hibernate.dialect.MySQLDialect;

public class MyDialect extends MySQLDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
