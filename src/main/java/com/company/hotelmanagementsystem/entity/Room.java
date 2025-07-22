package com.company.hotelmanagementsystem.entity;

import com.company.hotelmanagementsystem.enums.RoomStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Column(nullable = false, length = 50)
    private String roomNumber;

    @Column(nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RoomStatus roomStatus;

    @OneToOne(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Booking booking;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
