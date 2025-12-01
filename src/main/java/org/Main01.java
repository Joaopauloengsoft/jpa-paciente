package org;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.model.Paciente;

import java.time.LocalDate;

public class Main01 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Paciente paciente = new Paciente();
        LocalDate data = LocalDate.of(1990, 12, 25);

        paciente.setCpf("111.222.333-44");
        paciente.setNome("João Paulo");
        paciente.setDataNascimento(data);
        paciente.setTelefone("999138752");
        em.persist(paciente);
        em.getTransaction().commit();
        em.close();
    }
}
