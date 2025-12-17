package br.com.teleconsulta.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    private static final String PERSISTENCE_UNIT = "teleconsultaPU";
    private static EntityManagerFactory factory;
    
    private static void initFactory() {
        if (factory == null || !factory.isOpen()) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
            } catch (Exception e) {
                System.err.println("Erro ao criar EntityManagerFactory: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Falha na inicialização do JPA", e);
            }
        }
    }
    
    public static EntityManager getEntityManager() {
        initFactory();
        return factory.createEntityManager();
    }
    
    public static void closeFactory() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
    
    public static boolean isFactoryOpen() {
        return factory != null && factory.isOpen();
    }
}
