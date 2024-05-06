package ar.edu.itba.paw.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@EnableAsync
@EnableScheduling
@EnableTransactionManagement
@PropertySource("classpath:/application.properties")
@ComponentScan({"ar.edu.itba.paw.webapp.controller", "ar.edu.itba.paw.services", "ar.edu.itba.paw.webapp.form", "ar.edu.itba.paw.persistence", "ar.edu.itba.paw.webapp.miscellaneous"})
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:i18n/messages");
        ms.setUseCodeAsDefaultMessage(true);
        ms.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return ms;
    }

    @Autowired
    private Environment environment;

    @Bean(name = "baseUrl")
    public String basePath() {
        return environment.getProperty("base_url");
    }

    @Bean(name = "dbUrl")
    public String dbUrl() {
        return environment.getProperty("db_url");
    }

    @Bean(name = "dbUsername")
    public String dbUsername() {
        return environment.getProperty("db_username");
    }

    @Bean(name = "dbPassword")
    public String dbPassword() {
        return environment.getProperty("db_password");
    }

    @Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler(); //single threaded by default
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setPackagesToScan("ar.edu.itba.paw.models");
        factoryBean.setDataSource(dataSource());

        final HibernateJpaVendorAdapter jpaAdapter = new HibernateJpaVendorAdapter();
        factoryBean.setJpaVendorAdapter(jpaAdapter);

        final Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL92Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "none");
//         properties.setProperty("hibernate.show_sql", "true");
//         properties.setProperty("format_sql", "true");

        factoryBean.setJpaProperties(properties);

        return factoryBean;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory em) {
        return new JpaTransactionManager(em);
    }

    @Value("classpath:schema.sql")
    private Resource schemaSql;

    @Value("classpath:languages.sql")
    private Resource languagesSql;

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }


    @Bean
    public DataSource dataSource() {
        final SimpleDriverDataSource ds = new SimpleDriverDataSource();

        ds.setDriverClass(org.postgresql.Driver.class);
        // Que base de datos me conecto
        ds.setUrl(dbUrl());
//      ds.setUrl("jdbc:postgresql://localhost/paw");

        ds.setUsername(dbUsername());
        ds.setPassword(dbPassword());
//       ds.setUsername("postgres");
//        ds.setPassword("root");

        LOGGER.debug("DATA SOURCE CREATED");

        return ds;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(final DataSource ds) {
        final DataSourceInitializer dsi = new DataSourceInitializer();
        dsi.setDataSource(ds);
        dsi.setDatabasePopulator(databasePopulator());
        return dsi;
    }

    public DatabasePopulator databasePopulator() {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();

        populator.addScript(languagesSql);
        populator.addScript(schemaSql);

        return populator;
    }
}
