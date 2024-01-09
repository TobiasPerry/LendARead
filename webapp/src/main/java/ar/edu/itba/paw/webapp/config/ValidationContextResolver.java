package ar.edu.itba.paw.webapp.config;

import org.glassfish.jersey.server.validation.ValidationConfig;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import java.util.Locale;
import java.util.ResourceBundle;

@Provider
public class ValidationContextResolver implements ContextResolver<ValidationConfig> {

    @Override
    public ValidationConfig getContext(Class<?> type) {
        final ValidationConfig config = new ValidationConfig();
        config.messageInterpolator(new CustomMessageInterpolator());
        return config;
    }

    private static class CustomMessageInterpolator implements MessageInterpolator {
        private final MessageInterpolator messageInterpolator;

        public CustomMessageInterpolator(){
            PlatformResourceBundleLocator aux = new PlatformResourceBundleLocator("validation");
            ResourceBundle ans = aux.getResourceBundle(Locale.ENGLISH);
            messageInterpolator = Validation.byDefaultProvider()
                    .configure()
                   .messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("classpath:i18n/ValidationMessages")))
                    .getDefaultMessageInterpolator();
        }


        @Override
        public String interpolate(String messageTemplate, Context context) {
            return messageInterpolator.interpolate(messageTemplate,context, LocaleContextHolder.getLocale());
        }

        @Override
        public String interpolate(String messageTemplate, Context context, Locale locale) {
            return messageInterpolator.interpolate(messageTemplate,context, LocaleContextHolder.getLocale());
        }
    }
}