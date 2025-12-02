package org.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.model.Paciente;
import org.utils.HibernateUtil;

import java.util.List;

public class PacienteDAO {

    // 1. Adiciona um paciente (No Banco de Dados via Hibernate)
    public void salvarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(paciente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 2. Retorna uma lista de todos os pacientes
    public List<Paciente> listarPacientes() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL para trazer todos os registros
            return session.createQuery("FROM Paciente", Paciente.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 3. Retorna uma lista de pacientes cujo nome contenha o termo (Busca parcial)
    public List<Paciente> buscarPacientePorNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // HQL usando LIKE para verificar se "contém" o termo
            // O lower() ajuda a ignorar maiúsculas e minúsculas
            String hql = "FROM Paciente p WHERE lower(p.nome) LIKE lower(:nomeBusca)";

            Query<Paciente> query = session.createQuery(hql, Paciente.class);
            // Os % são curingas do SQL para dizer "qualquer coisa antes ou depois"
            query.setParameter("nomeBusca", "%" + nome + "%");

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 4. Atualiza as informações de um paciente existente
    public void atualizarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // O merge verifica se o ID existe e atualiza os dados
            session.merge(paciente);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    // 5. Remove um paciente com base no ID
    public void excluirPaciente(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Primeiro buscamos o objeto pelo ID (Primary Key)
            Paciente paciente = session.find(Paciente.class, id);

            if (paciente != null) {
                session.remove(paciente);
                System.out.println("Paciente excluído com sucesso.");
            } else {
                System.out.println("Paciente com ID " + id + " não encontrado.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}