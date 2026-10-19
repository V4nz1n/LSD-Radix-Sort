package br.puc.radix.lista;

import java.util.Objects;

/**
 * Lista encadeada simples e genérica, feita à mão (sem estruturas prontas).
 * A lista não guarda os valores: guarda só o primeiro nó, o último nó e o tamanho.
 * Inserir nas pontas é rápido (O(1)) porque ela sempre sabe onde está o last.
 */
public class LinkedList<T> {
    private Node<T> first; // primeiro nó (null quando a lista está vazia)
    private Node<T> last;  // último nó: permite inserir no fim sem percorrer a lista
    private int size;      // quantidade de nós

    public LinkedList(){
        this.first = null; // lista vazia: first e last nulos, size 0
    }

    public LinkedList(T value) {
        this.first = new Node<T>(value);
        this.last = this.first; // com um só nó, first e last são o mesmo
        this.size = 1;
    }

    // Insere no início: o nó novo passa a apontar para o antigo primeiro.
    public void addFirst(T value){
        Node<T> newNode = new Node<>(value);
        if(this.isEmpty()){ // lista vazia: o nó novo é o primeiro e o último
            this.first = newNode;
            this.last = this.first;
            size++;
            return;
        }
        newNode.setNext(this.first); // 1º liga o novo ao antigo primeiro (senão a lista se perde)
        this.first = newNode;        // 2º o novo vira o primeiro
        this.size++;
    }

    // Insere no fim: o antigo último passa a apontar para o nó novo.
    public void addLast(T value){
        Node<T> newNode = new Node<>(value);
        if(!this.isEmpty()){
            this.last.setNext(newNode); // 1º liga o antigo último ao novo
            this.last = newNode;        // 2º o novo vira o último (a ordem importa)
            this.size++;
            return;
        }
        this.first = newNode; // lista vazia: o nó novo é o primeiro e o último
        this.last = this.first;
        this.size++;
    }

    // Recebe um nó que JÁ EXISTE (não cria nó novo) e o coloca no fim da lista.
    // É o que o Radix usa para mover nós entre os baldes.
    // Atenção: zera o next do nó recebido, então quem chama deve guardar o próximo nó antes.
    public void addNode(Node<T> value) {
        value.setNext(null); // o nó vira o último, então não pode apontar para o vizinho antigo
        if(!this.isEmpty()){
            this.last.setNext(value);
            this.last = value;
            value.setNext(null); // repetido: o next já foi zerado na primeira linha do método
            this.size++;
            return;
        }
        this.first = value; // lista vazia: o nó é o primeiro e o último
        this.last = this.first;
        this.size++;
    }

    // Esvazia a lista sem percorrer nem desmontar os nós:
    // eles continuam ligados entre si e podem estar em uso em outra lista (os baldes).
    public void clear(){
        this.first = null;
        this.last = null;
        this.size = this.size - getSize(); // size - getSize() é sempre 0, ou seja, zera o tamanho
    }

    // Junta outra lista ao fim desta em O(1): só religa ponteiros, sem percorrer nada.
    public void concat(LinkedList<T> otherList){
        if(otherList.isEmpty()){ // nada para juntar: esta lista fica como está
            return;
        }
        if(this.isEmpty()){ // esta lista está vazia: ela passa a ser a outra
            this.first = otherList.getFirst();
            this.last = otherList.last; // private vale por classe, então dá para ler o last da outra lista
            this.size = otherList.getSize();
            return;
        }
        this.last.setNext(otherList.getFirst()); // 1º liga o fim desta ao começo da outra
        this.last = otherList.last;              // 2º o last passa a ser o da outra lista
        this.size = getSize() + otherList.getSize();
    }

    // Devolve a posição (começando em 0) da primeira ocorrência, ou -1 se não achar.
    public int search(T value) {
        Node<T> current = this.first;
        int index = 0;
        while (current != null) {
            if (Objects.equals(current.getData(), value)) { // equals, e não ==, por causa do Integer
                return index;
            }
            current = current.getNext();
            index++;
        }
        return -1;
    }

    // Remove a primeira ocorrência do valor. Devolve true se removeu e false se não achou.
    public boolean remove(T value) {
        Node<T> previous = null; // guarda o nó anterior: para tirar um nó, o anterior precisa "pular" ele
        Node<T> current = this.first;
        while (current != null) {
            if (Objects.equals(current.getData(), value)) {
                if (previous == null) {
                    this.first = current.getNext(); // removendo o primeiro nó: o first avança
                } else {
                    previous.setNext(current.getNext()); // o anterior passa a apontar para o seguinte
                }
                if (current == this.last) {
                    this.last = previous; // removeu o último nó: o last recua
                }
                current.setNext(null); // solta o nó removido
                this.size--;
                return true;
            }
            previous = current;
            current = current.getNext();
        }
        return false;
    }

    public boolean isEmpty(){
        return this.first == null; // sem primeiro nó, a lista está vazia
    }

    // Devolve o nó (e não o valor) para quem está fora da classe poder percorrer a lista.
    public Node<T> getFirst(){
        return this.first;
    }

    public int getSize(){
        return this.size;
    }

    // Usa uma variável local para percorrer, assim o first não se perde.
    public void print() {
        Node<T> current = this.first;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
        System.out.println();
    }
}