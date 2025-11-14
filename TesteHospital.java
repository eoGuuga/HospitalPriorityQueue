/**
 * @author Gustavo Henrick Rodrigues da Silva e Matheus Fraga Baptistucci
 */
public class TesteHospital {
    
    public static void main(String[] args) {
        MinHeap<Paciente> fila = new MinHeap<>(20);
        
        Paciente[] pacientes = {
            new Paciente("Carlos", 103, 1),
            new Paciente("Ana", 101, 2),
            new Paciente("Bruno", 102, 3),
            new Paciente("Daniela", 104, 2),
            new Paciente("Eduardo", 105, 3),
            new Paciente("Fernanda", 106, 1),
            new Paciente("Gabriel", 107, 2),
            new Paciente("Helena", 108, 3),
            new Paciente("Igor", 109, 1),
            new Paciente("Julia", 110, 2)
        };
        
        System.out.println("Simulacao Heap Hospital (Java 8 compativel)\n");
        
        System.out.println(" Inserindo pacientes:\n");
        for (Paciente p : pacientes) {
            fila.inserir(p);
            try { 
                Thread.sleep(800); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\nRemovendo pacientes:\n");
        while (fila.tamanho > 0) {
            fila.removerMin();
            try { 
                Thread.sleep(1200); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("\n✅ Todos os pacientes foram atendidos!");
    }
}
