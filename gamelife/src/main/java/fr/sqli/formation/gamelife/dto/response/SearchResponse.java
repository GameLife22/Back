package fr.sqli.formation.gamelife.dto.response;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record SearchResponse(
        @NotBlank String keyword

) {}
