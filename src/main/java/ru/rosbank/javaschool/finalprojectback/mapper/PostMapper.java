package ru.rosbank.javaschool.finalprojectback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;


@Mapper(componentModel = "spring")
public interface PostMapper {

    PostResponseDto entityToPostResponseDto(PostEntity entity);

    @Mappings({
            @Mapping(target = "likes", constant = "0"),
            @Mapping(target = "removed", constant = "false"),
            @Mapping(target = "created", expression = "java(java.time.LocalDate.now())")
    })
    PostEntity dtoToPostEntity(PostSaveRequestDto dto);
}
