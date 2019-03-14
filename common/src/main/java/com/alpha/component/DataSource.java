package com.alpha.component;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection();
}
