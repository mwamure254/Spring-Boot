package com.mfano.mpos.models.security;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.mfano.mpos.models.BaseObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken extends BaseObject {
  @Column(nullable = false, unique = true)
  private String token;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "user_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private User user;

  private LocalDateTime expiryDate;
}
