package com.example.project.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
@ToString(exclude = {"companies"})
@Table(name="authuser")
public class Authuser implements Serializable {
	
	private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "First name can not be blank")
    @Column(name="first_name")
    private String firstName;

    @NotBlank(message = "Last name can not be blank")
    @Column(name="last_name")
    private String lastName;

    @NotBlank(message = "Username can not be blank")
    @Column(unique = true)
    private String username;  

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    
    @Pattern(regexp = "^\\(?([0-9]{3})\\)?([ .-]?)([0-9]{3})\\2([0-9]{4})", message = "Phone number format is invalid!")
	private String contact;
    
    @Column
    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotNull(message = "Password can not be empty")
    private String password;
   

	@OneToMany(mappedBy = "authuser", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Company> companies;

    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;
    
    @Column(name = "enabled")
    private boolean enabled;
    
    @Transient
    private int codeNumber;

}
