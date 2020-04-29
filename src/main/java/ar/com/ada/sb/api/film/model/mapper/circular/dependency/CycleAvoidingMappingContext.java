package ar.com.ada.sb.api.film.model.mapper.circular.dependency;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

import java.util.IdentityHashMap;
import java.util.Map;

@Component("cycleAvoidingMappingContext")
public class CycleAvoidingMappingContext {

    //Se guardan todas las conversiones de los objetos que va a ser el mapper
    private Map<Object, Object> knowInstances = new IdentityHashMap<>();

    private static CycleAvoidingMappingContext INSTANCE = null;

    //private CycleAvoidingMappingContext() {}

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        return (T) knowInstances.get(source);
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        knowInstances.put(source, target);
    }

    /*public static CycleAvoidingMappingContext getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CycleAvoidingMappingContext();

        return INSTANCE;
    }*/

}
