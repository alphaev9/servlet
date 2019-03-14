package com.alpha.listeners;

import com.alpha.component.DataSource;
import com.alpha.annotation.Dao;
import com.alpha.annotation.Inject;
import com.alpha.util.PackageScanner;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;

public class DataSourceProcessListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

         /*********************************
         * 1、根据web.xml确定是product，还是dev，注入不同的数据源
         * 2、如果是dev，注入h2数据库，执行sql文件，创建测试数据库，停止时删除
         * */
        ServletContext context = servletContextEvent.getServletContext();
        String config = context.getInitParameter("config");
        DataSource dataSource = createDataSource(config);
        if ("dev".equals(config)) {
            /*如果是开发环境，生成相应数据源，执行预处理操作*/
            initDB(dataSource);
        }
        inject(dataSource, context);
    }

    private DataSource createDataSource(String config) {
        InputStream resource = getClass().getClassLoader().getResourceAsStream(config);
        Properties properties = new Properties();
        DataSource dataSource = null;
        try {
            properties.load(resource);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            dataSource = () -> {
                try {
                    Class.forName(driver);
                    return DriverManager.getConnection(url, user, password);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            };

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        String config = context.getInitParameter("config");
        DataSource dataSource = (DataSource) context.getAttribute("dataSource");
        if ("dev".equals(config)) {
            dropDB(dataSource);
        }
    }

    private void inject(DataSource dataSource, ServletContext context) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = null;
        try {
            URL base = context.getResource("/WEB-INF/classes");
            file = new File(base.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Set<String> classSet = PackageScanner.getClassNameFromDir(file);


        classSet.stream()
                .map(className -> {
                    try {
                        Class<?> aClass = classLoader.loadClass(className);
                        if (aClass.isAnnotationPresent(Dao.class)) {
                            return aClass;
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .filter(clazz -> clazz != null)
                .peek(clazz -> System.out.println(clazz.getName()))
                .forEach(clazz -> {
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Inject.class)) {
                            try {
                                System.out.println(field.getName());
                                field.setAccessible(true);
                                field.set(clazz, dataSource);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void initDB(DataSource dataSource) {
        Connection connection = dataSource.getConnection();
    }

    private void dropDB(DataSource dataSource) {

    }
}
