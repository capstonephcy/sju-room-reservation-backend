package com.sju.roomreservationbackend.common.base;

import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySQLCustomDialect extends MySQL8Dialect {
    public MySQLCustomDialect() {
        super();
        this.registerFunction("ST_Distance_Sphere", new StandardSQLFunction("ST_Distance_Sphere", StandardBasicTypes.DOUBLE));
    }
}
