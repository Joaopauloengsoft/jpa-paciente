package org.utils;


import lombok.Getter;
import org.hibernate.SessionFactory;
import org.model.Paciente;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Cria a SessionFactory a partir do hibernate.properties e das classes mapeadas
            Configuration configuration = new Configuration();

            // Adiciona a classe de Entidade
            configuration.addAnnotatedClass(Paciente.class);

            // Configura a partir do hibernate.properties (lido automaticamente)
            return configuration.buildSessionFactory();
            // O .configure() carrega o arquivo hibernate.cfg.xml


        } catch (Throwable ex) {
            System.err.println("Falha na criação inicial da SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void shutdown() {
        // Fecha caches e pools de conexão
        getSessionFactory().close();
    }
}
