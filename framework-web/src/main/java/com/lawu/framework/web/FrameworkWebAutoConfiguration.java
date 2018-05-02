package com.lawu.framework.web;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.lawu.authorization.AuthorizationAutoConfiguration;
import com.lawu.framework.web.FrameworkWebAutoConfiguration.CorsAutoConfiguration;
import com.lawu.framework.web.FrameworkWebAutoConfiguration.DocumentAutoConfiguration;
import com.lawu.framework.web.FrameworkWebAutoConfiguration.ParamSignInterceptorAutoConfiguration;
import com.lawu.framework.web.FrameworkWebAutoConfiguration.SwaggerAutoConfiguration;
import com.lawu.framework.web.FrameworkWebAutoConfiguration.UserVisitInterceptorAutoConfiguration;
import com.lawu.framework.web.doc.annotation.Audit;
import com.lawu.framework.web.doc.controller.DocumentController;
import com.lawu.framework.web.doc.controller.ResultCodeController;
import com.lawu.framework.web.filter.XssFilter;
import com.lawu.framework.web.interceptor.ParamSignInterceptor;
import com.lawu.framework.web.interceptor.UserVisitInterceptor;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * FrameworkWeb自动配置类
 * @author jiangxinjun
 * @createDate 2018年3月5日
 * @updateDate 2018年3月5日
 */
@AutoConfigureAfter(AuthorizationAutoConfiguration.class)
@Configuration
@Import({ UserVisitInterceptorAutoConfiguration.class, 
    ParamSignInterceptorAutoConfiguration.class, 
    CorsAutoConfiguration.class, 
    DocumentAutoConfiguration.class, 
    SwaggerAutoConfiguration.class,
    GlobalExceptionHandler.class})
public class FrameworkWebAutoConfiguration {
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {  
        return new MethodValidationPostProcessor();
    }
    
    /**
     * xss过滤器
     * @return
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @ConditionalOnProperty(name = {"lawu.framework-web.xss-filter.enabled"}, havingValue="true", matchIfMissing = false)
    @Bean
    public FilterRegistrationBean  filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/**");
        return registrationBean;
    }
    
    /*@Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        // 为mapper注册一个带有SerializerModifier的Factory
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new JsonBeanSerializerModifier()));
        return converter;
    }*/
    
    /**
     * 用户访问拦截器自动配置
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @ConditionalOnProperty(name = {"lawu.framework-web.interceptor.user-visit.enabled"}, havingValue="true", matchIfMissing = false)
    @Configuration
    public static class UserVisitInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {
        
        @ConfigurationProperties(prefix = "lawu.framework-web.interceptor.user-visit")
        @Bean
        public UserVisitInterceptor userVisitInterceptor() {
            return new UserVisitInterceptor();
        }
        
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(userVisitInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        }
    }
    
    /**
     * 拦截器自动配置
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @ConditionalOnProperty(name = {"lawu.framework-web.cors.enabled"}, havingValue="true", matchIfMissing = false)
    @Configuration
    public static class CorsAutoConfiguration extends WebMvcConfigurerAdapter {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedHeaders("*")
            .allowedMethods("*");
        }

    }
    
    /**
     * 文档接口自动配置
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @ConditionalOnProperty(name = {"lawu.framework-web.document.enabled"}, havingValue="true", matchIfMissing = false)
    @ComponentScan(basePackageClasses = {DocumentController.class, ResultCodeController.class})
    @Configuration
    public static class DocumentAutoConfiguration {
    }
    
    @ConditionalOnClass(Docket.class)
    @ConditionalOnProperty(name = "lawu.framework-web.swagger-api.enabled", havingValue = "true", matchIfMissing = false)
    @ConfigurationProperties(prefix = "lawu.framework-web.swagger-api")
    @Configuration
    @EnableSwagger2
    public static class SwaggerAutoConfiguration {

        /**
         * 是否审核
         */
        private Boolean audit = true;

        /**
         * 访问地址
         */
        private String host;

        public Boolean getAudit() {
            return audit;
        }

        public void setAudit(Boolean audit) {
            this.audit = audit;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .groupName("API")
                    .forCodeGeneration(true)
                    .pathMapping("/")
                    .host(host)
                    .select()
                    .apis(RequestHandlerSelectors.withMethodAnnotation(getAudit() ? Audit.class : ApiOperation.class))
                    .paths(paths())
                    .build()
                    .apiInfo(apiInfo())
                    .useDefaultResponseMessages(false);
        }

        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("API")
                    .description("API调试工具")
                    .version("1.0")
                    .build();
        }

        private Predicate<String> paths() {
            return Predicates.and(PathSelectors.regex("/.*"), Predicates.not(PathSelectors.regex("/error")));
        }

    }
    
    /**
     * 参数签名拦截器自动配置
     * @author jiangxinjun
     * @createDate 2018年3月5日
     * @updateDate 2018年3月5日
     */
    @ConditionalOnProperty(name = {"lawu.framework-web.interceptor.param-sign.enabled"}, havingValue="true", matchIfMissing = false)
    @Configuration
    public class ParamSignInterceptorAutoConfiguration extends WebMvcConfigurerAdapter {

        @ConfigurationProperties(prefix = "lawu.framework-web.interceptor.param-sign")
        @Bean
        public ParamSignInterceptor paramSignInterceptor() {
            return new ParamSignInterceptor();
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(paramSignInterceptor()).addPathPatterns("/**");
            super.addInterceptors(registry);
        }
    }
}
