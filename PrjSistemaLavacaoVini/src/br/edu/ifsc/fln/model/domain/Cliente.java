/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.ifsc.fln.model.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author vinic
 */
public abstract class Cliente implements ICliente {
    protected int id;
    protected String nome;
    protected String celular;
    protected String email;
    protected String endereco;
    protected LocalDate dataCadastro;
    
    protected List<Veiculo> veiculos;
    
    protected Pontuacao pontuacao;

    public Cliente() {
        this.createPontuacao();
    }

    public Cliente(String nome, String celular, LocalDate dataCadastro) {
        this.nome = nome;
        this.celular = celular;
        this.dataCadastro = dataCadastro;
    }

    public Cliente(int id, String nome, String celular, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.dataCadastro = dataCadastro;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCelular() {
        return celular;
    }    
    
    public void setCelular(String celular) {
        this.celular = celular;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public List<Veiculo> getVeiculos() {
        return this.veiculos;
    }
    
    public void add(Veiculo veiculo) {
        if (veiculos == null) {
            veiculos = new ArrayList<>();
        }
        veiculos.add(veiculo);
        veiculo.setCliente(this);
    }
    
    public void remove(Veiculo veiculo) {
        if (veiculos != null) {
            veiculos.remove(veiculo);
            veiculo.setCliente(null);
        }
    }
    
    @Override
    public String toString() {
        return nome;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return this.id == other.id;
    }

    private void createPontuacao() {
        this.pontuacao = new Pontuacao();
        this.pontuacao.setCliente(this);
    }
    
    
    
}
