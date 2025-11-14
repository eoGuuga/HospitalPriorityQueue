/**
 * @author Gustavo Henrick Rodrigues da Silva e Matheus Fraga Baptistucci
 */
public class Paciente implements Comparable<Paciente> {
    private String nome;
    private int numeroChamada;
    private int prioridade;
    private long timestamp;
    
    public Paciente(String nome, int numeroChamada, int prioridade) {
        this.nome = nome;
        this.numeroChamada = numeroChamada;
        this.prioridade = prioridade;
        this.timestamp = System.currentTimeMillis();
    }
    
    public Paciente(String nome, int prioridade, int contador) {
        this.nome = nome;
        this.numeroChamada = 100 + contador;
        this.prioridade = prioridade;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getNumeroChamada() {
        return numeroChamada;
    }
    
    public int getPrioridade() {
        return prioridade;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    @Override
    public int compareTo(Paciente outro) {
        if (this.prioridade != outro.prioridade) {
            return Integer.compare(this.prioridade, outro.prioridade);
        }
        return Long.compare(this.timestamp, outro.timestamp);
    }
    
    @Override
    public String toString() {
        return String.format("Paciente: %s | Chamada: %d | Prioridade: %d", 
                           nome, numeroChamada, prioridade);
    }
}
