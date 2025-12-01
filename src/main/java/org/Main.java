package org;

import org.dao.PacienteDAO;
import org.model.Paciente;
import org.utils.HibernateUtil;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        PacienteDAO dao = new PacienteDAO();

        Paciente paciente = new Paciente();
        /*
        System.out.println("------[ Create Paciente ] -------");
        paciente.setNome("DANIEL C. SILVA");
        paciente.setCpf("999.888.777-66");
        LocalDate data = LocalDate.of(1990, 12, 25);
        paciente.setDataNascimento(data);
        paciente.setTelefone("(62) 99999-8888");
        dao.salarPaciente(paciente);

         */

        System.out.println("");
        System.out.println("------[ Buscar por nome do Paciente ] -------");
        Paciente pacienteAux = dao.buscarPacientePorNome("DANIEL C. SILVA");
        System.out.println(pacienteAux);

        System.out.println("");
        System.out.println("------[ Update Paciente ] -------");
        if (pacienteAux != null) {
            paciente.setCpf("17317");
            LocalDate data1 = LocalDate.of(1990, 12, 25);
            paciente.setDataNascimento(data1);
            paciente.setNome("Miguel C. SILVA");
            System.out.printf("Professor atualizado: " + dao.buscarPacientePorNome(""));
        }

        System.out.println("");
        System.out.println("------[ Listar todos Pacientes ] -------");
        dao.buscarPaciente().forEach(System.out::println);

        System.out.println("");
        System.out.println("------[ Deletar Professor ] -------");
        if (dao.buscarPacientePorNome("DANIEL C. SILVA") != null) {
            dao.excluirPaciente("DANIEL C. SILVA");
        }
        HibernateUtil.shutdown();
    }
}