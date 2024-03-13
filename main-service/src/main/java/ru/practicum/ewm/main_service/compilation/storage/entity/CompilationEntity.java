package ru.practicum.ewm.main_service.compilation.storage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "compilations")
public class CompilationEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ToString.Exclude
   @ManyToMany
   @JoinTable(name = "compilation_content",
           joinColumns = @JoinColumn(
                   name = "compilation_id",
                   referencedColumnName = "id"
           ),
           inverseJoinColumns = @JoinColumn(
                   name = "event_id",
                   referencedColumnName = "id"
           )
   )
   private List<EventEntity> events;

   @Column(name = "pinned", nullable = false)
   private Boolean pinned;

   @Column(name = "title", unique = true, nullable = false)
   private String title;
}
