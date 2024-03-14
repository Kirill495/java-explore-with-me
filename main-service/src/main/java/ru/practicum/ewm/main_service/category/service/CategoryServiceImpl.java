package ru.practicum.ewm.main_service.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main_service.category.dto.CategoryDto;
import ru.practicum.ewm.main_service.category.dto.NewCategoryDto;
import ru.practicum.ewm.main_service.category.exception.CategoryNotFoundException;
import ru.practicum.ewm.main_service.category.exception.CategoryRemoveException;
import ru.practicum.ewm.main_service.category.mapper.CategoryMapper;
import ru.practicum.ewm.main_service.category.model.Category;
import ru.practicum.ewm.main_service.category.storage.entity.CategoryEntity;
import ru.practicum.ewm.main_service.category.storage.repository.CategoryRepository;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.event.storage.repository.EventRepository;
import ru.practicum.ewm.main_service.event.storage.specification.EventSpecification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository repository;
   private final EventRepository eventRepository;
   private final CategoryMapper mapper;

   @Override
   public CategoryDto addCategory(NewCategoryDto cat) {
      return mapper.toDto(repository.save(mapper.toEntity(cat)));
   }

   @Override
   public CategoryDto updateCategory(Long id, NewCategoryDto cat) {
      CategoryEntity catEntity = repository.findById(id).orElseThrow(() -> new CategoryNotFoundException(id));
      Category category = mapper.toModel(catEntity);
      category.setName(cat.getName());
      return mapper.toDto(repository.save(mapper.toEntity(category)));
   }

   @Override
   public List<CategoryDto> getCategories(Pageable page) {
      return mapper.toDto(repository.findAll(page).toList());
   }

   @Override
   public CategoryDto getCategory(Long catId) {
      return mapper.toDto(repository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId)));
   }

   @Override
   public Category getCategoryUtil(Long catId) {
      return mapper.toModel(repository.findById(catId).orElseThrow(() -> new CategoryNotFoundException(catId)));
   }

   @Override
   public void removeCategory(Long id) {
      if (!repository.existsById(id)) {
         throw new CategoryNotFoundException(id);
      }

      Specification<EventEntity> spec = Specification.where(EventSpecification.ofCategory(id));
      if (eventRepository.exists(spec)) {
         throw new CategoryRemoveException(String.format("Category with id=%d exists in events", id));
      } else {
         repository.deleteById(id);
      }
   }
}
