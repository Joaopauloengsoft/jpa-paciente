package org.jpapaciente;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dao.PacienteDAO;
import org.model.Paciente;

import java.time.LocalDate;
import java.util.List;

public class PacienteController {

    // --- Mapeamento EXATO dos campos do FXML ---
    @FXML private TextField txtId;
    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private DatePicker dpDataNascimento;
    @FXML private TextField txtTelefone;

    // --- Mapeamento EXATO da Tabela e Colunas do FXML ---
    @FXML private TableView<Paciente> tabelaPacientes;
    @FXML private TableColumn<Paciente, Integer> colId;
    @FXML private TableColumn<Paciente, String> colNome;
    @FXML private TableColumn<Paciente, String> colCpf;

    // ATENÇÃO: O nome aqui deve ser igual ao fx:id do FXML (colDataNascimento)
    @FXML private TableColumn<Paciente, LocalDate> colDataNascimento;

    @FXML private TableColumn<Paciente, String> colTelefone;

    // --- Dependências ---
    private PacienteDAO dao = new PacienteDAO();
    private ObservableList<Paciente> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Vincula as colunas da tabela com os atributos da Classe Paciente
        // "id", "nome", etc devem ser iguais aos nomes das variáveis na classe Paciente
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colDataNascimento.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        // Carrega os dados iniciais
        atualizarTabela();

        // Listener: Quando clicar na tabela, preenche os campos lá em cima
        tabelaPacientes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                preencherCampos(newVal);
            }
        });
    }


    @FXML
    public void onCadastrar(ActionEvent event) {
        try {
            Paciente p = new Paciente();
            p.setNome(txtNome.getText());
            p.setCpf(txtCpf.getText());
            p.setDataNascimento(dpDataNascimento.getValue());
            p.setTelefone(txtTelefone.getText());

            dao.salvarPaciente(p);

            atualizarTabela();
            limparCampos();
            alerta("Sucesso", "Paciente cadastrado!");
        } catch (Exception e) {
            alerta("Erro", "Erro ao cadastrar: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onBuscar(ActionEvent event) {
        String termo = txtNome.getText();
        List<Paciente> resultados = dao.buscarPacientePorNome(termo);

        observableList.setAll(resultados);
        tabelaPacientes.setItems(observableList);
    }

    @FXML
    public void onAtualizar(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            alerta("Aviso", "Selecione um paciente para atualizar.");
            return;
        }

        try {
            Paciente p = new Paciente();
            p.setId(Integer.parseInt(txtId.getText())); // O ID é fundamental para o update
            p.setNome(txtNome.getText());
            p.setCpf(txtCpf.getText());
            p.setDataNascimento(dpDataNascimento.getValue());
            p.setTelefone(txtTelefone.getText());

            dao.atualizarPaciente(p);

            atualizarTabela();
            limparCampos();
            alerta("Sucesso", "Paciente atualizado!");
        } catch (Exception e) {
            alerta("Erro", "Erro ao atualizar: " + e.getMessage());
        }
    }

    @FXML
    public void onExcluir(ActionEvent event) {
        if (txtId.getText().isEmpty()) {
            alerta("Aviso", "Selecione um paciente para excluir.");
            return;
        }

        try {
            int id = Integer.parseInt(txtId.getText());
            dao.excluirPaciente(id);

            atualizarTabela();
            limparCampos();
            alerta("Sucesso", "Paciente excluído!");
        } catch (Exception e) {
            alerta("Erro", "Erro ao excluir: " + e.getMessage());
        }
    }

    // --- Métodos Auxiliares ---

    private void atualizarTabela() {
        // Busca todos do banco e joga na ObservableList
        List<Paciente> listaDoBanco = dao.listarPacientes();
        if (listaDoBanco != null) {
            observableList.setAll(listaDoBanco);
            tabelaPacientes.setItems(observableList);
        }
    }

    private void preencherCampos(Paciente p) {
        txtId.setText(String.valueOf(p.getId()));
        txtNome.setText(p.getNome());
        txtCpf.setText(p.getCpf());
        dpDataNascimento.setValue(p.getDataNascimento());
        txtTelefone.setText(p.getTelefone());
    }

    private void limparCampos() {
        txtId.clear();
        txtNome.clear();
        txtCpf.clear();
        dpDataNascimento.setValue(null);
        txtTelefone.clear();
    }

    private void alerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}