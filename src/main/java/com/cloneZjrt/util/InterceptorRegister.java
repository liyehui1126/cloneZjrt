package com.cloneZjrt.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@EnableWebMvc
@Configuration
public class InterceptorRegister extends WebMvcConfigurationSupport {

    @Bean
    public MyInterceptor myInterceptor() {
        return new MyInterceptor();
    }

    /*
     * 添加spring中的拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(myInterceptor()).addPathPatterns("/cloneZjrt");
        super.addInterceptors(registry);
    }

    /**
     * 静态资源跨域
     * @param registry
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/file/**")
//                .addResourceLocations("file:/opt/files/");
//    }
}
