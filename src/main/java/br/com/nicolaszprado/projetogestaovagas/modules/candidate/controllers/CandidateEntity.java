package br.com.nicolaszprado.projetogestaovagas.modules.candidate.controllers;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;




import java.util.UUID;
@Data
@Entity(name= "candidate")
public class CandidateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp = "\\s+", message = "O campo [name] nao deve possuir espacos")
    private String name;
    @Email(message = "O campo [Email] deve possuir um e-mail valido")
    private String email;
    private String description;

    private String password;
    private String curriculum;

}
