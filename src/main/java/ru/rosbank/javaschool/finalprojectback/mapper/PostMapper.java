package ru.rosbank.javaschool.finalprojectback.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.rosbank.javaschool.finalprojectback.constants.PostConstant;
import ru.rosbank.javaschool.finalprojectback.dto.PostResponseDto;
import ru.rosbank.javaschool.finalprojectback.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.finalprojectback.entity.PostEntity;


@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(target = "authorId", expression = "java(entity.getAuthor().getId())"),
            @Mapping(target = "authorName", expression = "java(entity.getAuthor().getName())")
    })
    PostResponseDto entityToPostResponseDto(PostEntity entity);

    @Mappings({
            @Mapping(target = PostConstant.LIKES, constant = "0"),
            @Mapping(target = PostConstant.REMOVED, constant = "false"),
            @Mapping(target = PostConstant.CREATED, expression = "java(java.time.LocalDate.now())")
    })
    PostEntity dtoToPostEntity(PostSaveRequestDto dto);
}
