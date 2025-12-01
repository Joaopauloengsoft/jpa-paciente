package org.dao;
import org.hibernate.query.Query; // Importante importar

import jakarta.transaction.SystemException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.model.Paciente;
import org.utils.HibernateUtil;

import java.util.List;

public class PacienteDAO {


    public void salarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            //Inicia Transação
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



    public Paciente buscarPacientePorNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // Criamos uma consulta HQL: "Traga o Paciente p onde p.nome é igual ao parâmetro"
            String hql = "FROM Paciente p WHERE p.nome = :nomeBusca";

            Query<Paciente> query = session.createQuery(hql, Paciente.class);
            query.setParameter("nomeBusca", nome);

            // uniqueResult() retorna o objeto se achar, ou null se não achar.
            // Se houver mais de um "Daniel", ele lança erro (o que é bom para integridade)
            return query.uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Paciente> buscarPaciente(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            return session.createQuery("from Paciente",Paciente.class).list();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarPaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(paciente);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void excluirPaciente(String nome) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Paciente paciente =session.find(Paciente.class, nome);

            if(paciente!=null){
                session.remove(paciente);
            }
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}