package com.example.algamoney.api.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name="contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @NotEmpty
    private String nome;

    @NotEmpty
    @NotNull
    @Email
    private String email;

    @NotEmpty
    private String telefone;

    @NotNull
    @ManyToOne
    @JoinColumn(name="codigo_pessoa")
    private Pessoa pessoa;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contato contato = (Contato) o;
        return Objects.equals(codigo, contato.codigo) &&
                Objects.equals(nome, contato.nome) &&
                Objects.equals(email, contato.email) &&
                Objects.equals(telefone, contato.telefone) &&
                Objects.equals(pessoa, contato.pessoa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo, nome, email, telefone, pessoa);
    }
}
