package com.epam.esm.configuration;

import com.epam.esm.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilter jwtTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN") // TODO: 1/20/2022
                .antMatchers("/user/*").hasRole("USER") // TODO: 1/20/2022
                .antMatchers("/register", "/authorize").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtFilter jwtFilter;
//
//    @Bean
//    public PasswordEncoder getPasswordEncoder() {
//        return new BCryptPasswordEncoder(8);
//    }
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
////        auth.userDetailsService(userService)
////                .passwordEncoder(NoOpPasswordEncoder.getInstance());
////                .usersByUsernameQuery("SELECT login, password, active from user WHERE login=?")
////                .authoritiesByUsernameQuery("SELECT u.login, ur.roles from user u inner join user_role ur ON u.id=ur.user_id WHERE u.login=?");
////                .withUser("user")
////                .password(encoder.encode("user"))
////                .roles("USER")
////                .and()
////                .withUser("admin")
////                .password(encoder.encode("admin"))
////                .roles("ADMIN");
////    }
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .antMatchers("/v1/registration").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin()
////                .permitAll()
////                .and()
////                .logout()
////                .permitAll();
////    }
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/*").hasRole("ADMIN")
//                .antMatchers("/user/*").hasRole("USER")
//                .antMatchers("/v1/registration", "/auth").permitAll()
//                .and()
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//
//
////    @Override
////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userService)
////                .passwordEncoder(passwordEncoder);
////    }
}
