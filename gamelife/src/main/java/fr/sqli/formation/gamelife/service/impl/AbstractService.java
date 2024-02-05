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
abstract class AbstractService<T> {
    public T findEntity(Integer pEntityPrimaryKey) throws EntityNotFoundException, ParameterException {
        ValidationUtils.isNotNull(pEntityPrimaryKey, "findEntity - l'id est null");

        var optionalResult = this.getTargetedDao().findById(pEntityPrimaryKey);
        if (optionalResult.isEmpty()) {
            throw new EntityNotFoundException("findEntity - l'entité introuvable");
        }

        return optionalResult.get();
    }

    public List<T> findAllEntities() {
        final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

        var iterable = this.getTargetedDao().findAll();
        List<T> result = new ArrayList<>(iterable);
        if(result.size() == 0) {
            LOG.error("findAllEntities - la base de données est vide");
        }
        return result;
    }

    protected abstract JpaRepository<T, Integer> getTargetedDao();
}
