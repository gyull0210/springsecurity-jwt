package com.gyull.jwt.jwt.domain.login;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

  @NotNull
  @Size(min=4, max=20)
  private String memId;

  @NotNull
  @Size(min=4, max=100)
  private String memPw;
}
