package com.example.demo.config;

import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/** 해당 클래스를 Configuration으로 등록 */
@Configuration
/** Spring Security를 활성화 */
@EnableWebSecurity
/** Controller에서 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션에 대한 설정을 활성화시키는 어노테이션 (필수는 아님) */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Autowired
    private LoginService loginService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler("/login");
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenicationSuccessHandler customAuthenicationSuccessHandler() {
        return new CustomAuthenicationSuccessHandler();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        System.out.println("SecurityConfig.customAuthenticationFilter");
//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(customAuthenticationManager());

//        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();

        // 지우면 되냐 왜 ?????????
//        customAuthenticationFilter.setFilterProcessesUrl("/loginProc");

//        customAuthenticationFilter.setAuthenticationManager(customAuthenticationManager());
        customAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenicationSuccessHandler());
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());

        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public CustomAuthenticationManager customAuthenticationManager() {
        System.out.println("SecurityConfig.customAuthenticationManager");
        return new CustomAuthenticationManager();
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        /** Spring Security에서 해당 요청은 인증 대상에서 제외 */
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * http 요청에 대해서 모든 사용자가 /** 경로로 요청할 수 있지만, /member/** , /admin/** 경로는 인증된 사용자만 요청이 가능
         */
        http.authorizeRequests() //HttpServletRequest 요청 URL에 따라 접근 권한을 설정
//                .antMatchers("/login**").permitAll()
//                .antMatchers("/login/member/**").authenticated() // antMatchers("pathPattern") - 요청 URL 경로 패턴을 지정
//                .antMatchers("/login/admin/**").authenticated() // authenticated() - 인증된 유저만 접근을 허용
                .antMatchers("/admin/**").hasAnyRole("SUPER","MAIN","SUB") // authenticated() - 인증된 유저만 접근을 허용
                .antMatchers("/mypage/**").hasAnyRole("GENERAL") // authenticated() - 인증된 유저만 접근을 허용
                .antMatchers("/test/**").hasAnyRole("MAIN") //
//                .antMatchers("/login/member/**").hasRole("MANAGER") // authenticated() - 인증된 유저만 접근을 허용
                .antMatchers("/**").permitAll() // permitAll() - 모든 유저에게 접근을 허용
                // anonymous() - 인증되지 않은 유저만 허용합니다.
                .anyRequest().authenticated() // 그 외의 모든 요청은 인증된 사용자만 접근가능
                .and()
//                .csrf().ignoringAntMatchers("/adm/deviceMonitor/deviceMng/serialNoCompare/{serialNo}") //csrf 예외 처리
//                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()); // @EnableWebSecurity 어노테이션을 활성화하면 추가적인 필요 없음
//                .and()                                       // 크로스 도메인 사용시
//                .cors()
//                .configurationSource(configurationSource());



        /**
         * 로그인 설정을 진행
         */
        http.formLogin()
                .loginPage("/login") // loginPage("path") - 커스텀 로그인 페이지 경로와 로그인 인증 경로를 등록
                .loginProcessingUrl("/loginProc") // 인증처리를 수행하는 필터가 호출 - Form action 경로와 일치
                .defaultSuccessUrl("/")  //defaultSuccessUrl("path") - 로그인 인증을 성공하면 이동하는 페이지를 등록
                .permitAll()
                .usernameParameter("userId") //login form에서 username에 parameter value 값 수정
                .passwordParameter("userPwd")
                .and()
                .addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                // UsernamePasswordAuthenticationFilter - 기본 인증 처리 담당 필터
                // UsernamePasswordAuthenticationFilter 이전에 custom Filter 추가
                // -> override 개념이 아니라 custom filter로 인증 처리 되면 UsernamePasswordAuthenticationFilter 인증 자연스레 통과하는 구조

        /**
         * 로그아웃 설정을 진행
         */
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 경로를 지정
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 경로를 지정
                .invalidateHttpSession(true); // 로그아웃 성공 시 세션을 제거

        /**
         * 권한이 없는 사용자가 접근했을 경우 이동할 경로
         */
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler());
    }


    /**
     * 크로스 도메인 사용시
     */
    /*
    private CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.addAllowedOrigin("https://payple.kr");
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers"
                , "Access-Control-Allow-Origin"
                , "Access-Control-Request-Method"
                , "Access-Control-Request-Headers"
                , "Origin"
                , "Cache-Control"
                , "Content-Type"
                , "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    */

    /**
     * AuthenticationManagerBuilder
     * AuthenticationManager를 생성합니다. AuthenticationManager는 사용자 인증을 담당
     */
/*

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 커스텀한 AuthenticationProvider 를 AuthenticationManager 에 등록
//        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider())
//        auth.authenticationProvider()
        System.out.println("SecurityConfig.configure");
        auth.userDetailsService(loginService).passwordEncoder(passwordEncoder());
    }
*/


}