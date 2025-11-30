package org;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.model.Paciente;
import org.w3c.dom.Entity;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Paciente p1 = new Paciente();
        LocalDate data = LocalDate.of(1990, 12, 25);

        p1.setCpf("111.222.333-44");
        p1.setNome("João Paulo");
        p1.setDataNascimento(data);
        p1.setTelefone("999138752");
        em.persist(p1);
        em.getTransaction().commit();
        em.close();
    }
}
