package fr.sqli.formation.gamelife.dto.request;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

public record SearchRequest(
        @NotBlank String keyword
) {}
