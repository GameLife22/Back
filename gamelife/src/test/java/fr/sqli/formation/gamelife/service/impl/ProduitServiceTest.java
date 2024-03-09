package fr.sqli.formation.gamelife.service.impl;

import fr.sqli.formation.gamelife.dto.in.ProduitDtoIn;
import fr.sqli.formation.gamelife.entity.ImageEntity;
import fr.sqli.formation.gamelife.ex.EntityExistException;
import fr.sqli.formation.gamelife.ex.EntityNotFoundException;
import fr.sqli.formation.gamelife.ex.ParameterException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProduitServiceTest {
    @Autowired
    private ProduitService service;
}