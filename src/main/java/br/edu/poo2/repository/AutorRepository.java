package br.edu.poo2.repository;

import br.edu.poo2.model.Autor;
import br.edu.poo2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AutorRepository {

    // CREATE
    public void salvar(Autor autor) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
            System.out.println(">> Autor salvo: " + autor);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar autor.", e);
        } finally {
            em.close();
        }
    }

    // READ por ID
    public Autor buscarPorId(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Autor.class, id);
        } finally {
            em.close();
        }
    }

    // READ todos
    public List<Autor> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Autor a ORDER BY a.nome", Autor.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // UPDATE
    public Autor atualizar(Autor autor) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Autor atualizado = em.merge(autor);
            em.getTransaction().commit();
            System.out.println(">> Autor atualizado: " + atualizado);
            return atualizado;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar autor.", e);
        } finally {
            em.close();
        }
    }

    // DELETE
    public void remover(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Autor autor = em.find(Autor.class, id);
            if (autor == null) {
                System.out.println(">> Autor com id " + id + " não encontrado.");
                em.getTransaction().rollback();
                return;
            }
            em.remove(autor);
            em.getTransaction().commit();
            System.out.println(">> Autor removido: " + autor);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao remover autor.", e);
        } finally {
            em.close();
        }
    }
}
