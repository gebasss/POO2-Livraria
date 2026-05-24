package br.edu.poo2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "ano_publicacao", nullable = false)
    private Integer anoPublicacao;

    // unique = true → ISBN é único no mundo
    @Column(name = "isbn", nullable = false, unique = true, length = 20)
    private String isbn;

    // BigDecimal → tipo correto para valores monetários
    // Double pode gerar erros de arredondamento
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    // EnumType.STRING → salva "IMPRESSO", "DIGITAL" ou "AUDIOBOOK"
    // Nunca use ORDINAL — se mudar a ordem do enum, os dados ficam errados
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoPublicacao tipo;

    // ─────────────────────────────────────────────────────────
    // @ManyToOne — DONO do relacionamento com Editora.
    // A FK "editora_id" fica na tabela "livros".
    // Relacionamento BIDIRECIONAL: Editora.livros aponta
    // de volta via mappedBy = "editora".
    // ─────────────────────────────────────────────────────────
    @ManyToOne
    @JoinColumn(name = "editora_id", nullable = false)
    private Editora editora;

    // ─────────────────────────────────────────────────────────
    // @ManyToMany — DONO do relacionamento com Autor.
    // @JoinTable → Hibernate cria a tabela de junção
    // "livros_autores" com as colunas "livro_id" e "autor_id".
    // Relacionamento BIDIRECIONAL: Autor.livros aponta
    // de volta via mappedBy = "autores".
    // ─────────────────────────────────────────────────────────
    @ManyToMany
    @JoinTable(
        name               = "livros_autores",
        joinColumns        = @JoinColumn(name = "livro_id"),
        inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new ArrayList<>();

    protected Livro() {}

    public Livro(String titulo, Integer anoPublicacao, String isbn,
                 BigDecimal preco, TipoPublicacao tipo, Editora editora) {
        this.titulo        = titulo;
        this.anoPublicacao = anoPublicacao;
        this.isbn          = isbn;
        this.preco         = preco;
        this.tipo          = tipo;
        this.editora       = editora;
    }

    // Método auxiliar para manter consistência do relacionamento bidirecional
    public void adicionarAutor(Autor autor) {
        this.autores.add(autor);           // lado dono (persiste)
        autor.getLivros().add(this);       // lado inverso (navegação)
    }

    public Integer        getId()              { return id; }

    public String         getTitulo()          { return titulo; }
    public void           setTitulo(String t)  { this.titulo = t; }

    public Integer        getAnoPublicacao()   { return anoPublicacao; }
    public void           setAnoPublicacao(Integer a) { this.anoPublicacao = a; }

    public String         getIsbn()            { return isbn; }
    public void           setIsbn(String isbn) { this.isbn = isbn; }

    public BigDecimal     getPreco()           { return preco; }
    public void           setPreco(BigDecimal p) { this.preco = p; }

    public TipoPublicacao getTipo()            { return tipo; }
    public void           setTipo(TipoPublicacao t) { this.tipo = t; }

    public Editora        getEditora()         { return editora; }
    public void           setEditora(Editora e){ this.editora = e; }

    public List<Autor>    getAutores()         { return autores; }

    @Override
    public String toString() {
        return "Livro{" +
               "id="         + id            +
               ", titulo='"  + titulo        + '\'' +
               ", ano="      + anoPublicacao +
               ", isbn='"    + isbn          + '\'' +
               ", preco="    + preco         +
               ", tipo="     + tipo          +
               ", editora='" + (editora != null ? editora.getNome() : "?") + '\'' +
               '}';
    }
}
