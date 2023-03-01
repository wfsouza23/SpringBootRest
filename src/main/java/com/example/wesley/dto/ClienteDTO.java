package com.example.wesley.dto;

import com.example.wesley.domain.Cliente;
import com.example.wesley.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClienteUpdate
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1l;

    private Integer id;

    @NotEmpty(message = "Este campo nao pode ser vazio")
    @Length(min = 5, max = 80, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String name;

    @NotEmpty(message = "Este campo nao pode ser vazio")
    @Email(message = "Email Invalido")
    private String email;

    public ClienteDTO() {

    }

    public ClienteDTO(Cliente obj) {
        id = obj.getId();
        name = obj.getNome();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
