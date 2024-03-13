package ru.practicum.ewm.main_service.event.storage.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.main_service.category.storage.entity.CategoryEntity;
import ru.practicum.ewm.main_service.event.model.EventState;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
public class EventEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "annotation")
   private String annotation;

   @ManyToOne
   @JoinColumn(name = "category_id")
   private CategoryEntity category;

   @Column(name = "created_on")
   @CreationTimestamp
   private LocalDateTime createdOn;

   @Column(name = "description")
   private String description;

   @Column(name = "event_date")
   private LocalDateTime eventDate;

   @ManyToOne
   @JoinColumn(name = "initiator_id")
   private UserEntity initiator;

   @Column(name = "location_lat")
   private Float locationLat;

   @Column(name = "location_lon")
   private Float locationLon;

   @Column(name = "paid")
   private Boolean paid;

   @Column(name = "participant_limit")
   private Integer participantLimit;

   @Column(name = "published_on")
   private LocalDateTime publishedOn;

   @Column(name = "request_moderation")
   private Boolean requestModeration;

   @Column(name = "state")
   @Enumerated(EnumType.STRING)
   private EventState state;

   @Column(name = "title")
   private String title;

}
