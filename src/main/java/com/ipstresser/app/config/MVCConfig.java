package com.ipstresser.app.config;

import com.ipstresser.app.web.interceptors.FaviconInterceptor;
import com.ipstresser.app.web.interceptors.PageTitleInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class MVCConfig implements WebMvcConfigurer {

    private final FaviconInterceptor faviconInterceptor;
    private final PageTitleInterceptor pageTitleInterceptor;

    @Autowired
    public MVCConfig(FaviconInterceptor faviconInterceptor, PageTitleInterceptor pageTitleInterceptor) {
        this.faviconInterceptor = faviconInterceptor;
        this.pageTitleInterceptor = pageTitleInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.faviconInterceptor);
        registry.addInterceptor(this.pageTitleInterceptor);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/faq").setViewName("faq");
        registry.addViewController("contact").setViewName("contact-us");
    }
}
