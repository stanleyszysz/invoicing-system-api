package pl.fc.invoicing.configuration;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .addOpenApiCustomiser(openApi -> {
                Contact contact = new Contact();
                contact.setName("Stanisław Szczurek");
                contact.setEmail("smth@gmail.com");
                Info info = new Info();
                info.setTitle("Invoicing System");
                info.setDescription("Invoicing system API");
                info.setContact(contact);
                info.setVersion("0.0.1");
                openApi.setInfo(info);
            })
            .group("invoicing-system")
            .pathsToMatch("/**")
            .build();
    }
}
