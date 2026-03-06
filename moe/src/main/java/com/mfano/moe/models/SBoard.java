package com.mfano.moe.models;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.mfano.moe.security.model.Profile;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
public class SBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String remarks;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate from = LocalDate.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate upTo;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profileid", insertable = false, updatable = false)
	private Profile profile;
	private Long profileid;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sstatusid", insertable = false, updatable = false)
	private SStatus sStatus;
	private Long sstatusid;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "institutionid", insertable = false, updatable = false)
	private Institution institution;
	private Long institutionid;

}
