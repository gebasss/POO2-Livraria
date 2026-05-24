package br.edu.poo2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "nacionalidade", length = 60)
    private String nacionalidade;

    // ─────────────────────────────────────────────────────────
    // Lado INVERSO do @ManyToMany.
    // mappedBy = "autores" aponta para o campo "autores"
    // dentro de Livro — que é o lado DONO do relacionamento.
    // Este lado existe apenas para navegação: permite consultar
    // todos os livros de um autor sem query extra.
    // ─────────────────────────────────────────────────────────
    @ManyToMany(mappedBy = "autores", fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();

    protected Autor() {}

    public Autor(String nome, String nacionalidade) {
        this.nome          = nome;
        this.nacionalidade = nacionalidade;
    }

    public Integer getId()              { return id; }

    public String getNome()             { return nome; }
    public void   setNome(String nome)  { this.nome = nome; }

    public String getNacionalidade()    { return nacionalidade; }
    public void   setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public List<Livro> getLivros()      { return livros; }

    @Override
    public String toString() {
        return "Autor{id=" + id + ", nome='" + nome + "', nacionalidade='" + nacionalidade + "'}";
    }
}
