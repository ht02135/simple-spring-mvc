@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
            requests -> requests
                    .requestMatchers("/admin")
                    .hasAnyRole("Admin", "Editor")
                    .requestMatchers("/login")
                    .permitAll()
                    .requestMatchers("/register")
                    .permitAll()
                    .requestMatchers("/logout")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
        );

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }