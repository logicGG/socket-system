package club.ovelya.socketsystem.config;

import org.hibernate.dialect.MySQLDialect;

/**
 * 解决自动创建表字符集问题
 */
public class MyDialect extends MySQLDialect {

    @Override
    public String getTableTypeString() {
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
