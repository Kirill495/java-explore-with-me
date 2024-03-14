package ru.practicum.ewm.main_service.comment.storage.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.main_service.event.storage.entity.EventEntity;
import ru.practicum.ewm.main_service.user.storage.entity.UserEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comments")
public class CommentEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ToString.Exclude
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "author_id", nullable = false)
   private UserEntity author;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "event_id", nullable = false)
   private EventEntity event;

   @Column(name = "text", nullable = false)
   private String text;

   @CreationTimestamp
   @Column(name = "created")
   private LocalDateTime createdOn;
}
