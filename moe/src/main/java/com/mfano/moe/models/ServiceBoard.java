package com.mfano.moe.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.mfano.moe.security.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String remarks;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from = LocalDate.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate upTo;

    @ManyToOne
	@JoinColumn(name = "userid", insertable = false, updatable = false)
	private User user;
	private Integer userid;

    @ManyToOne
	@JoinColumn(name = "sstatusid", insertable = false, updatable = false)
	private SStatus sStatus;
	private Integer sstatusid;

    @ManyToOne
	@JoinColumn(name = "institutionid", insertable = false, updatable = false)
	private Institution institution;
	private Integer institutionid;

    //TODO Length of stay Formula

    //Individual Port Folio
}
