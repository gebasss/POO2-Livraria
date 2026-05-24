package br.edu.poo2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "editoras")
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "cidade", length = 60)
    private String cidade;

    // ─────────────────────────────────────────────────────────
    // Lado INVERSO do @OneToMany.
    // mappedBy = "editora" aponta para o campo "editora"
    // dentro de Livro — que tem a FK (editora_id) no banco.
    // LAZY: os livros só são carregados quando getLivros()
    // for chamado explicitamente.
    // ─────────────────────────────────────────────────────────
    @OneToMany(mappedBy = "editora", fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();

    protected Editora() {}

    public Editora(String nome, String cidade) {
        this.nome   = nome;
        this.cidade = cidade;
    }

    public Integer getId()              { return id; }

    public String getNome()             { return nome; }
    public void   setNome(String nome)  { this.nome = nome; }

    public String getCidade()           { return cidade; }
    public void   setCidade(String cidade) { this.cidade = cidade; }

    public List<Livro> getLivros()      { return livros; }

    @Override
    public String toString() {
        return "Editora{id=" + id + ", nome='" + nome + "', cidade='" + cidade + "'}";
    }
}
