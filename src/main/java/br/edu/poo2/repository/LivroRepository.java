package br.edu.poo2.repository;

import br.edu.poo2.model.Livro;
import br.edu.poo2.model.TipoPublicacao;
import br.edu.poo2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class LivroRepository {

    // CREATE
    public void salvar(Livro livro) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(livro);
            em.getTransaction().commit();
            System.out.println(">> Livro salvo: " + livro);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar livro.", e);
        } finally {
            em.close();
        }
    }

    // READ por ID
    public Livro buscarPorId(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Livro.class, id);
        } finally {
            em.close();
        }
    }

    // READ todos
    public List<Livro> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Livro l ORDER BY l.titulo", Livro.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // UPDATE
    public Livro atualizar(Livro livro) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Livro atualizado = em.merge(livro);
            em.getTransaction().commit();
            System.out.println(">> Livro atualizado: " + atualizado);
            return atualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar livro.", e);
        } finally {
            em.close();
        }
    }

    // DELETE
    public void remover(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Livro livro = em.find(Livro.class, id);
            if (livro == null) {
                System.out.println(">> Livro com id " + id + " não encontrado.");
                em.getTransaction().rollback();
                return;
            }
            em.remove(livro);
            em.getTransaction().commit();
            System.out.println(">> Livro removido: " + livro);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao remover livro.", e);
        } finally {
            em.close();
        }
    }

    // ── CONSULTAS JPQL ──────────────────────────────────────────

    // Busca por trecho do título (case-insensitive)
    // :titulo → parâmetro nomeado (evita SQL Injection)
    public List<Livro> buscarPorTitulo(String titulo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                "FROM Livro l WHERE LOWER(l.titulo) LIKE LOWER(:titulo)", Livro.class)
                .setParameter("titulo", "%" + titulo + "%")
                .getResultList();
        } finally {
            em.close();
        }
    }

    // Filtra por tipo de publicação
    // Enum passado diretamente — Hibernate converte para String no banco
    public List<Livro> buscarPorTipo(TipoPublicacao tipo) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                "FROM Livro l WHERE l.tipo = :tipo ORDER BY l.titulo", Livro.class)
                .setParameter("tipo", tipo)
                .getResultList();
        } finally {
            em.close();
        }
    }

    // Navega pelo @ManyToOne em JPQL sem JOIN explícito
    // "l.editora.nome" → Hibernate resolve o JOIN automaticamente
    public List<Livro> buscarPorEditora(String nomeEditora) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery(
                "FROM Livro l WHERE LOWER(l.editora.nome) = LOWER(:nome)", Livro.class)
                .setParameter("nome", nomeEditora)
                .getResultList();
        } finally {
            em.close();
        }
    }
}
