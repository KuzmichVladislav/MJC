package com.epam.esm.configuration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

@EnableWebMvc
public class SpringApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext =
                new AnnotationConfigWebApplicationContext();
        annotationConfigWebApplicationContext.register(WebConfiguration.class);
        annotationConfigWebApplicationContext.setServletContext(servletContext);
        DispatcherServlet servlet = new DispatcherServlet(annotationConfigWebApplicationContext);
        servlet.setThrowExceptionIfNoHandlerFound(true);// FIXME: 12/12/2021
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher",
                servlet);
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
