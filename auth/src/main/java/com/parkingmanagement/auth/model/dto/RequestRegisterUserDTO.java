package com.parkingmanagement.auth.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class RequestRegisterUserDTO {

    @NotBlank(message = "'name' é obrigatório")
    @Size(min = 3, message = "'name' deve ter no mínimo 3 caracteres")
    private String name;

    @NotBlank(message = "'cpf' é obrigatório")
    @Size(min = 11, max = 11, message = "'cpf' deve ter 11 caracteres")
    @CPF(message = "'cpf' é inválido")
    private String cpf;

    @NotBlank(message = "'email' é obrigatório")
    @Email(message = "'email' é inválido")
    private String email;

    @Size(min = 11, max = 11, message = "'telephone' deve ter 11 caracteres")
    private String telephone;

    @Size(min = 6, message = "'password' deve ter no mínimo 6 caracteres")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
