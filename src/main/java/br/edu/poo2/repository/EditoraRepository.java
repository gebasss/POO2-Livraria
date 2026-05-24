package br.edu.poo2.repository;

import br.edu.poo2.model.Editora;
import br.edu.poo2.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class EditoraRepository {

    public void salvar(Editora editora) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(editora);
            em.getTransaction().commit();
            System.out.println(">> Editora salva: " + editora);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar editora.", e);
        } finally {
            em.close();
        }
    }

    public Editora buscarPorId(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Editora.class, id);
        } finally {
            em.close();
        }
    }

    public List<Editora> listarTodos() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("FROM Editora e ORDER BY e.nome", Editora.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    public Editora atualizar(Editora editora) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Editora atualizada = em.merge(editora);
            em.getTransaction().commit();
            System.out.println(">> Editora atualizada: " + atualizada);
            return atualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao atualizar editora.", e);
        } finally {
            em.close();
        }
    }

    public void remover(Integer id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Editora editora = em.find(Editora.class, id);
            if (editora == null) {
                System.out.println(">> Editora com id " + id + " não encontrada.");
                em.getTransaction().rollback();
                return;
            }
            em.remove(editora);
            em.getTransaction().commit();
            System.out.println(">> Editora removida: " + editora);
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new RuntimeException("Erro ao remover editora.", e);
        } finally {
            em.close();
        }
    }
}
