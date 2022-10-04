package com.Quan.TryJWT.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdatePasswordRequest {
	
    private Long id;

    @NotBlank
    @Size(min = 6,max = 40)
    private String oldPassword;
    
    @NotBlank
    @Size(min = 6,max = 40)
    private String newPassword;
}
