package desafio.votacao.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DesafioVotacaoContext implements ApplicationContextAware {

    private static ApplicationContext context;

    private static final String ERR_MSG = "Classe utilitária do Spring não foi inicializada.";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T bean(Class<T> clazz) {
        if (context == null) {
            throw new IllegalStateException(ERR_MSG);
        }
        return context.getBean(clazz);
    }

    public static <T> T bean(String name) {
        if (context == null) {
            throw new IllegalStateException(ERR_MSG);
        }
        return (T) context.getBean(name);
    }

}
