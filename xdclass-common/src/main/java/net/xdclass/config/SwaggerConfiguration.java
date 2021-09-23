package net.xdclass.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Component
@EnableOpenApi
@Data
public class SwaggerConfiguration {


    @Bean
    public Docket webApiDoc() {


        return new Docket(DocumentationType.OAS_30)
                .groupName("用户端接口文档")
                .pathMapping("/")
                // 定义是否开启swagger，false为关闭，可以通过变量控制，线上关闭
                .enable(true)
                //配置api文档元信息
                .apiInfo(apiInfo())
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.xdclass"))
                //正则匹配请求路径，并分配至当前分组
                .paths(PathSelectors.ant("/api/**"))
                .build()

                // 3.0  公参
                .globalRequestParameters(globalRequestParameters())
                .globalResponses(HttpMethod.GET, globalResponseMessage())
                .globalResponses(HttpMethod.POST, globalResponseMessage());

    }

    /**
     * 管理端接口文档
     *
     * @return
     */
    @Bean
    public Docket adminApiDoc() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("管理端接口文档")
                .pathMapping("/")
                .enable(true)
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("net.xdclass"))
                // 正则表达式，表示已/admin开头的路径，要放到这个分组里面来。
                .paths(PathSelectors.ant("/admin/**")).build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("1024-2048")
                .description("微服务接口文档")
                .contact(new Contact("小滴课堂-二当家小D", "https://xdclass.net", "794666918@qq.com"))
                .version("12")
                .build();
    }

    private ApiInfo adminApiInfo(){
        return new ApiInfoBuilder()
                .title("1024-2048")
                .description("微服务接口文档--admin")
                .contact(new Contact("帅帅的佐恩-佐恩", "https://www.baidu.com", "zzohn@foxmail.com"))
                .version("24")
                .build();
    }

    private List<RequestParameter> globalRequestParameters(){
        List<RequestParameter> requestParameters = new ArrayList<>();
        requestParameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录令牌")
                .in(ParameterType.HEADER)
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .required(false)
                .build());
        return requestParameters;
    }

    private List<Response> globalResponseMessage(){
        List<Response> responseList = new ArrayList<>();
        responseList.add(new ResponseBuilder()
                .code("401")
                .description("登录已过期，请重新登录")
                .build()
        );
        return responseList;
    }
}