package com.zcj.ls.ls_web.utils;

import org.hibernate.dialect.MySQL5Dialect;

public class CustomMysqlDialect extends MySQL5Dialect {
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}