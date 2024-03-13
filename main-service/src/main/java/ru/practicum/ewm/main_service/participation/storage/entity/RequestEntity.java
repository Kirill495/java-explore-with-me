package ru.practicum.ewm.main_service.participation.storage.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.participation.model.RequestStatus;
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
@Table(name = "participation_requests")
public class RequestEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "event_id")
   private EventEntity event;

   @ManyToOne
   @JoinColumn(name = "requester_id")
   private UserEntity requester;

   @Column(name = "status")
   @Enumerated(EnumType.STRING)
   private RequestStatus status;

   @Column(name = "created")
   @CreationTimestamp
   private LocalDateTime created;

}
