package com.Quan.TryJWT.payload.request;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class UpdateProfileRequest {
	
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@Size(max = 120)
	private String firstName;

	@Size(max = 120)
	private String lastName;

	public UpdateProfileRequest(@NotBlank @Size(max = 20) String username, 
			@NotBlank @Size(max = 50) @Email String email, @Size(max = 15) String phone) {
		super();
		this.username = username;
		this.email = email;
		this.phone = phone;
	}

	@Column(length = 10, unique = true)
	private String phone;

	@Column(name = "image", length = 300)
	private String image;
}