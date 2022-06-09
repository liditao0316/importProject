package com.bookshop.system.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;

    //注入数据源
    @Autowired
    private DataSource dataSource;

    //配置对象，用于rememberMe功能
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //jdbcTokenRepository.setCreateTableOnStartup(true);//系统自己创建数据库重的table
        return jdbcTokenRepository;
    }

    /**
     * 将UserDetailsService注入进调用链中（自定义类继承UserDetailsService）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(password());
    }

    @Bean
    PasswordEncoder password(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //退出登陆
        //http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();

        //配置没有权限访问跳转自定义页面
        http.exceptionHandling().accessDeniedPage("/unauth.html");

        http.formLogin() //自定义自己登录的界面
                .loginPage("/login.html") //登录页面设置
                .loginProcessingUrl("/user/login") //登录访问路径
                .defaultSuccessUrl("/success.html").permitAll()//登录成功之后，跳转路径
                .and().authorizeHttpRequests()
                .antMatchers("/","/user/login").permitAll() //设置哪些路径可以直接访问，不需要认证
                //当前登录用户，只有具有admins权限才可以访问这个路径
                //.antMatchers("/test/index").hasAuthority("admins")
                //当前登录用户，只要有其中一个权限就可以访问
                //.antMatchers("/test/index").hasAnyAuthority("admins,manager")
                //当前登录用户，只有具有sale角色才可以访问这个路径
                //.antMatchers("/test/index").hasRole("sale")\
                //当前登录用户，只要有其中一个角色才可以访问这个路径
                .antMatchers("/test/index").hasAnyRole("sale,manager")
                .anyRequest().authenticated()
                .and().rememberMe().tokenRepository(persistentTokenRepository())//设置rememberMe功能
                .tokenValiditySeconds(60)//设置token有效时间
                .userDetailsService(userDetailsService)//设置自定义查询数据库返回user对象类
                .and().csrf().disable(); //关闭csrf防护
    }
}
