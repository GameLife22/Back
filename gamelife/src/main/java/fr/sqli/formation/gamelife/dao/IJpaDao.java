package fr.sqli.formation.gamelife.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface IJpaDao<T> extends JpaRepository<T, UUID> {
}
