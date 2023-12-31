package com.shop.config;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity // 1
public class SecurityConfig extends WebSecurityConfigurerAdapter { //

    @Autowired
    MemberService memberService;

    // 1,2) WebSecurityConfigurerAdapter를 상속받는 클래스에 @EnableWebSecurity 어노테이션을 선언하면 SpringSecurityFilterChain이 자동으로 포함됩니다.
    // WebSecurityConfigurerAdapter를 상속받아서 메소드 오버라이딩을 통해 보안 설정을 커스터마이징할 수 있습니다.

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 3
        // 3) http 요청에 대한 보안을 설정합니다. 페이지 권한 설정, 로그인 페이지 설정, 로그아웃 메소드 등에 대한 설정을 작성합니다.

        http.formLogin()
                .loginPage("/members/login") // 로그인 페이지 URL을 설정합니다.
                .defaultSuccessUrl("/") // 로그인 성공 시 이동할 URL을 설정합니다.
                .usernameParameter("email") // 로그인 시 사용할 파라미터 이름으로 email을 지정합니다.
                .failureUrl("/members/login/error") // 로그인 실패 시 이동할 URL을 설정합니다.
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL을 설정합니다.
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL을 설정합니다.
        ;

        http.authorizeRequests() // 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미합니다.
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**").permitAll() // permitAll()을 통해 모든 사용자가 인증(로그인)없이 해당 경로에 접근할 수 있도록 설정합니다.
                .mvcMatchers("/admin/**").hasRole("ADMIN") // /admin으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정합니다.
                .anyRequest().authenticated() // 위에 설정해준 경로를 제외한 나머지 경로들은 모두 인증을 요구하도록 설정합니다.
        ;

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러를 등록합니다.
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**"); // static 디렉터리의 하위 파일은 인증을 무시하도록 설정합니다.
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Spring Security에서 인증은 AuthenticationManager를 통해 이루어지며 AuthenticationManagerBuilder가 AuthenticationManager를 생성합니다.
        // userDetailsService를 구현하고 있는 객체로 memberService를 지정해주며, 비밀번호 암호화를 위해 passwordEncoder를 지정해줍니다.
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 4
        // 4) 비밀번호를 데이터베이스에 그대로 저장했을 경우, 데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출됩니다.
        // 이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여 비밀번호를 암호화하여 저장합니다.
        // BCryptPasswordEncoder를 빈으로 등록하여 사용하겠습니다.
        return new BCryptPasswordEncoder();
    }


}
