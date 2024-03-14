package ru.practicum.ewm.main_service.category.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.dto.NewCategoryDto;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.category.storage.entity.CategoryEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
   Category toModel(NewCategoryDto cat);

   Category toModel(CategoryDto cat);

   Category toModel(CategoryEntity cat);

   CategoryEntity toEntity(Category cat);

   CategoryEntity toEntity(NewCategoryDto cat);

   CategoryDto toDto(Category cat);

   CategoryDto toDto(CategoryEntity cat);

   List<CategoryDto> toDto(List<CategoryEntity> cats);
}
