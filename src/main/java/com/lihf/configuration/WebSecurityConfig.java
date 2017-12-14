package com.lihf.configuration;

import com.lihf.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * web安全适配器配置
 * @author lihf
 * @date 2017-12-14
 */
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserService();
    }
    /**
     * 配置用户的权限角色信息
     * @param builder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder
                //基于数据库
                .userDetailsService(customUserService())

                //基于内存
//                .inMemoryAuthentication()
//                .withUser("lihf").password("123").roles("ADMIN")
//              .and()
//                .withUser("admin").password("123").roles("RESOURCE").roles("ADMIN")
        ;
    }

    /**
     * 配置url的访问规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity security) throws Exception{
        security.authorizeRequests()
                .antMatchers("/static/**").hasRole("ADMIN")
                .antMatchers("/resource/**").hasRole("RESOURCE")
                .and()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
            .and()
                .formLogin()
                //指定登录路劲
//                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
            .and()
                .logout()
                .deleteCookies("remove")
                //所有访问者能够访问登录路劲
                .permitAll()
            .and()
                .authorizeRequests()
                .antMatchers("/oauth/**")
                .permitAll();
//            .and()
//                .logout()
//                .logoutUrl("/")
//                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .addLogoutHandler(logoutHandler)
//                .deleteCookies(cookieNamesToClear)
        ;
    }


    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Autowired
    public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore){
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(tokenStore);
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        handler.setClientDetailsService(clientDetailsService);
        return handler;
    }

    @Bean
    @Autowired
    public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
        TokenApprovalStore store = new TokenApprovalStore();
        store.setTokenStore(tokenStore);
        return store;
    }

}
