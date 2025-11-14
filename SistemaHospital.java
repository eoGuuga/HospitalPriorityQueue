import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gustavo Henrick Rodrigues da Silva e Matheus Fraga Baptistucci
 */
public class SistemaHospital {
    private MinHeap<Paciente> fila;
    private int contadorChamada;
    private int contadorPrioridade2;
    private JFrame frame;
    private JTextArea areaFila;
    private JTextField campoNome;
    private JComboBox<Integer> comboPrioridade;
    private JLabel labelProximo;
    private JButton btnCadastrar;
    private JButton btnChamar;
    
    public SistemaHospital() {
        this.fila = new MinHeap<>(100);
        this.contadorChamada = 0;
        this.contadorPrioridade2 = 0;
        criarInterface();
    }
    
    private void criarInterface() {
        frame = new JFrame("Sistema de Fila Hospitalar - Gustavo e Matheus");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());
        
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro de Paciente"));
        painelCadastro.setBackground(new Color(240, 248, 255));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        painelCadastro.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        campoNome = new JTextField(20);
        painelCadastro.add(campoNome, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        painelCadastro.add(new JLabel("Prioridade:"), gbc);
        gbc.gridx = 1;
        comboPrioridade = new JComboBox<>(new Integer[]{1, 2, 3});
        comboPrioridade.setSelectedIndex(2);
        painelCadastro.add(comboPrioridade, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnCadastrar = new JButton("Cadastrar Paciente");
        btnCadastrar.setBackground(new Color(76, 175, 80));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPaciente();
            }
        });
        painelCadastro.add(btnCadastrar, gbc);
        
        JPanel painelProximo = new JPanel(new BorderLayout());
        painelProximo.setBorder(BorderFactory.createTitledBorder("Próximo a Ser Atendido"));
        painelProximo.setBackground(new Color(255, 245, 238));
        
        labelProximo = new JLabel("Nenhum paciente na fila", JLabel.CENTER);
        labelProximo.setFont(new Font("Arial", Font.BOLD, 18));
        labelProximo.setForeground(new Color(139, 69, 19));
        painelProximo.add(labelProximo, BorderLayout.CENTER);
        
        btnChamar = new JButton("Chamar Próximo Paciente");
        btnChamar.setBackground(new Color(244, 67, 54));
        btnChamar.setForeground(Color.WHITE);
        btnChamar.setFont(new Font("Arial", Font.BOLD, 16));
        btnChamar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chamarProximo();
            }
        });
        painelProximo.add(btnChamar, BorderLayout.SOUTH);
        
        JPanel painelFila = new JPanel(new BorderLayout());
        painelFila.setBorder(BorderFactory.createTitledBorder("Fila de Atendimento"));
        
        areaFila = new JTextArea(15, 40);
        areaFila.setEditable(false);
        areaFila.setFont(new Font("Courier New", Font.PLAIN, 12));
        areaFila.setBackground(new Color(250, 250, 250));
        JScrollPane scrollFila = new JScrollPane(areaFila);
        painelFila.add(scrollFila, BorderLayout.CENTER);
        
        frame.add(painelCadastro, BorderLayout.NORTH);
        frame.add(painelProximo, BorderLayout.CENTER);
        frame.add(painelFila, BorderLayout.SOUTH);
        
        atualizarInterface();
        frame.setVisible(true);
    }
    
    private void cadastrarPaciente() {
        String nome = campoNome.getText().trim();
        
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "Por favor, informe o nome do paciente!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int prioridade = (Integer) comboPrioridade.getSelectedItem();
        contadorChamada++;
        
        Paciente paciente = new Paciente(nome, prioridade, contadorChamada);
        fila.inserir(paciente);
        
        campoNome.setText("");
        campoNome.requestFocus();
        
        atualizarInterface();
        
        JOptionPane.showMessageDialog(frame, 
            "Paciente cadastrado com sucesso!\n" + paciente.toString(), 
            "Sucesso", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void chamarProximo() {
        if (fila.estaVazio()) {
            JOptionPane.showMessageDialog(frame, 
                "Não há pacientes na fila!", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            atualizarInterface();
            return;
        }
        
        Paciente atendido = null;
        Paciente proximo = fila.min();
        
        boolean aplicarRegraEspecial = contadorPrioridade2 >= 2 && 
                                       existePrioridade3() && 
                                       proximo.getPrioridade() != 1;
        
        if (aplicarRegraEspecial) {
            atendido = removerPrioridade3();
            contadorPrioridade2 = 0;
        } else {
            atendido = fila.removerMin();
            
            if (atendido.getPrioridade() == 2) {
                contadorPrioridade2++;
            } else if (atendido.getPrioridade() == 1) {
                contadorPrioridade2 = 0;
            }
        }
        
        if (atendido != null) {
            String mensagem = String.format(
                "ATENDENDO AGORA:\n\n" +
                "Nome: %s\n" +
                "Numero de Chamada: %d\n" +
                "Prioridade: %d\n\n" +
                "Por favor, dirija-se ao atendimento!",
                atendido.getNome(),
                atendido.getNumeroChamada(),
                atendido.getPrioridade()
            );
            
            JOptionPane.showMessageDialog(frame, 
                mensagem, 
                "Chamada de Paciente", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        atualizarInterface();
    }
    
    @SuppressWarnings("unchecked")
    private boolean existePrioridade3() {
        Comparable<?>[] array = fila.obterArray();
        for (Comparable<?> paciente : array) {
            if (((Paciente) paciente).getPrioridade() == 3) {
                return true;
            }
        }
        return false;
    }
    
    @SuppressWarnings("unchecked")
    private Paciente removerPrioridade3() {
        Comparable<?>[] array = fila.obterArray();
        Paciente primeiroPrioridade3 = null;
        
        for (Comparable<?> paciente : array) {
            Paciente p = (Paciente) paciente;
            if (p.getPrioridade() == 3) {
                primeiroPrioridade3 = p;
                break;
            }
        }
        
        if (primeiroPrioridade3 == null) {
            return fila.removerMin();
        }
        
        List<Paciente> pacientesTemporarios = new ArrayList<>();
        Paciente pacienteEncontrado = null;
        
        while (!fila.estaVazio()) {
            Paciente p = fila.removerMin();
            if (pacienteEncontrado == null && 
                p.getPrioridade() == 3 && 
                p.getNumeroChamada() == primeiroPrioridade3.getNumeroChamada()) {
                pacienteEncontrado = p;
            } else {
                pacientesTemporarios.add(p);
            }
        }
        
        for (Paciente p : pacientesTemporarios) {
            fila.inserir(p);
        }
        
        return pacienteEncontrado != null ? pacienteEncontrado : fila.removerMin();
    }
    
    private void atualizarInterface() {
        if (fila.estaVazio()) {
            labelProximo.setText("Nenhum paciente na fila");
            btnChamar.setEnabled(false);
        } else {
            Paciente proximo = fila.min();
            labelProximo.setText(String.format(
                "Próximo: %s (Chamada: %d, Prioridade: %d)",
                proximo.getNome(),
                proximo.getNumeroChamada(),
                proximo.getPrioridade()
            ));
            btnChamar.setEnabled(true);
        }
        
        atualizarAreaFila();
    }
    
    private void atualizarAreaFila() {
        if (fila.estaVazio()) {
            areaFila.setText("Fila vazia - Nenhum paciente aguardando atendimento.");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Total de pacientes na fila: %d\n\n", fila.tamanho));
        sb.append("Ordem de atendimento (por prioridade e chegada):\n");
        sb.append(repeatString("=", 60) + "\n\n");
        
        @SuppressWarnings("unchecked")
        Comparable<?>[] array = fila.obterArray();
        java.util.Arrays.sort(array);
        
        int posicao = 1;
        for (Comparable<?> paciente : array) {
            Paciente p = (Paciente) paciente;
            String prioridadeStr = p.getPrioridade() == 1 ? "ALTA" : 
                                  p.getPrioridade() == 2 ? "MEDIA" : "BAIXA";
            
            sb.append(String.format("%d. %s | %s | Chamada: %d\n",
                posicao++, p.getNome(), prioridadeStr, p.getNumeroChamada()));
        }
        
        sb.append("\n" + repeatString("=", 60) + "\n");
        sb.append(String.format("Contador Prioridade 2: %d/2 (regra especial)\n", contadorPrioridade2));
        
        areaFila.setText(sb.toString());
    }
    
    private String repeatString(String str, int vezes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vezes; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaHospital();
            }
        });
    }
}
