package com.matheustorres.eadhub.course.mappers;

import org.springframework.stereotype.Component;

import com.matheustorres.eadhub.course.domain.models.Module;
import com.matheustorres.eadhub.course.dtos.ModuleDTO;

@Component
public class ModuleMapper {

    public Module toEntityBuilder(ModuleDTO moduleDto) {
        return Module.builder()
                .title(moduleDto.title())
                .description(moduleDto.description())
                .build();
    }
}
