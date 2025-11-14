/**
 * @author Gustavo Henrick Rodrigues da Silva e Matheus Fraga Baptistucci
 */
public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    public int tamanho;
    private int capacidade;
    
    @SuppressWarnings("unchecked")
    public MinHeap(int capacidade) {
        this.capacidade = capacidade;
        this.heap = (T[]) new Comparable[capacidade + 1];
        this.tamanho = 0;
    }
    
    public void inserir(T elemento) {
        if (tamanho >= capacidade) {
            System.out.println("⚠️  Heap cheio! Não é possível inserir mais elementos.");
            return;
        }
        
        tamanho++;
        heap[tamanho] = elemento;
        subir(tamanho);
        
        System.out.println("✅ " + elemento.toString() + " inserido na fila.");
    }
    
    public T removerMin() {
        if (tamanho == 0) {
            System.out.println("⚠️  Heap vazio! Não há elementos para remover.");
            return null;
        }
        
        T min = heap[1];
        heap[1] = heap[tamanho];
        heap[tamanho] = null;
        tamanho--;
        
        if (tamanho > 0) {
            descer(1);
        }
        
        System.out.println("🏥 Atendendo: " + min.toString());
        return min;
    }
    
    public T min() {
        if (tamanho == 0) {
            return null;
        }
        return heap[1];
    }
    
    public boolean estaVazio() {
        return tamanho == 0;
    }
    
    private void subir(int indice) {
        while (indice > 1 && heap[indice].compareTo(heap[pai(indice)]) < 0) {
            trocar(indice, pai(indice));
            indice = pai(indice);
        }
    }
    
    private void descer(int indice) {
        while (true) {
            int menor = indice;
            int esquerdo = filhoEsquerdo(indice);
            int direito = filhoDireito(indice);
            
            if (esquerdo <= tamanho && heap[esquerdo].compareTo(heap[menor]) < 0) {
                menor = esquerdo;
            }
            
            if (direito <= tamanho && heap[direito].compareTo(heap[menor]) < 0) {
                menor = direito;
            }
            
            if (menor == indice) {
                break;
            }
            
            trocar(indice, menor);
            indice = menor;
        }
    }
    
    private int pai(int indice) {
        return indice / 2;
    }
    
    private int filhoEsquerdo(int indice) {
        return 2 * indice;
    }
    
    private int filhoDireito(int indice) {
        return 2 * indice + 1;
    }
    
    private void trocar(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    
    public T[] obterArray() {
        @SuppressWarnings("unchecked")
        T[] copia = (T[]) new Comparable[tamanho];
        System.arraycopy(heap, 1, copia, 0, tamanho);
        return copia;
    }
}
