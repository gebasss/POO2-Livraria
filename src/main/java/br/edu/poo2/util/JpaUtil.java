package br.edu.poo2.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT = "poo2-pu";

    // Singleton — uma única instância do EMF em toda a aplicação
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            System.out.println(">> EntityManagerFactory criado com sucesso.");
        } catch (Exception e) {
            System.err.println(">> ERRO ao criar EntityManagerFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    // Construtor privado — impede new JpaUtil()
    private JpaUtil() {}

    // Retorna um EntityManager novo — quem chama deve fechar com em.close()
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Chamar uma vez ao encerrar a aplicação
    public static void fechar() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println(">> EntityManagerFactory encerrado.");
        }
    }
}
