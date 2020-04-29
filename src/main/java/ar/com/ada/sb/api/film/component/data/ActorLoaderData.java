package ar.com.ada.sb.api.film.component.data;

import ar.com.ada.sb.api.film.model.entity.Actor;
import ar.com.ada.sb.api.film.model.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class ActorLoaderData implements ApplicationRunner {

    //Saber el valor de la variable
    private static final Logger LOGGER = LoggerFactory.getLogger(ActorLoaderData.class);

    @Autowired @Qualifier("actorRepository")
    private ActorRepository actorRepository;

    //Ambiente de aplicacion - obtener el valor de la variable
    @Value("${spring.application.env}")
    private String appEnv;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //Saber en que ambiente esta
        LOGGER.info("Enviroment: " + appEnv);
        //Al levantar la app, sabremos en que ambiente esta config la aplicacion y
        //Carga los datos ya ingresados,
        //Si esta en otro ambiente (prod || predev) los datos no se cargan

        if (appEnv.equals("dev") || appEnv.equals("qa")) {

            LOGGER.info("Loading actor initial data... ");

            List<Actor> actorList = Arrays.asList(
                    new Actor("Scarlett Johansson", "F", LocalDate.parse("1984-11-21")),
                    new Actor("Jennifer Aniston", "F", LocalDate.parse("1969-02-10")),
                    new Actor("Brad Pitt", "M", LocalDate.parse("1963-12-17")),
                    new Actor("Leonardo DiCaprio", "M", LocalDate.parse("1974-11-10"))
            );
            actorList.forEach(actor -> actorRepository.save(actor));

//        OPCION 2
//        for (Actor actor: actorList) {
//            actorRepository.save(actor);
//        }
//        OPCION 3
//        for (int i = 0; i <= actorList.size(); i++) {
//            Actor actor = actorList.get(i);
//            actorRepository.save(actor);
//        }


        }
    }
}
