package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import fr.sqli.formation.gamelife.utils.ValidationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
abstract class AbstractService<E> {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

    public E findEntity(Integer pEntityPrimaryKey) throws EntityNotFoundException, ParameterException {
        ValidationUtils.isNotNull(pEntityPrimaryKey, "findEntity - l'id est null");

        var optionalResult = this.getTargetedDao().findById(pEntityPrimaryKey);
        if (optionalResult.isEmpty()) {
            throw new EntityNotFoundException("findEntity - l'entité introuvable");
        }

        return optionalResult.get();
    }

    public List<E> findAllEntities() {
        var iterable = this.getTargetedDao().findAll();
        List<E> result = new ArrayList<>(iterable);
        return result;
    }

    protected abstract JpaRepository<E, Integer> getTargetedDao();
}
