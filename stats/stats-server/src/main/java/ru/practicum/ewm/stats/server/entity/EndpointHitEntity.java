package ru.practicum.ewm.stats.server.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "endpoint_hits")
public class EndpointHitEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;

   @Column(name = "app")
   private String app;

   @Column(name = "uri")
   private String uri;

   @Column(name = "ip")
   private String ip;

   @Column(name = "timestamp")
   private LocalDateTime timestamp;
}
