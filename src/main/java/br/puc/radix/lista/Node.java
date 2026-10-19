package br.puc.radix.lista;

/**
 * Nó da lista encadeada: guarda um dado e a referência para o próximo nó.
 * A lista é formada por vários nós ligados pelo campo next.
 */
public class Node<T> {
    T data;       // valor guardado no nó
    Node<T> next; // próximo nó da cadeia (null se este for o último)

    public Node(T data){
        this.data = data;
        this.next = null; // nó novo nasce solto, sem vizinho
    }

    public Node(T data, Node<T> next){
        this.data = data;
        this.next = next; // nó novo já nasce ligado ao próximo
    }

    public T getData(){
        return this.data;
    }

    // É por aqui que os nós são religados: trocar o next muda quem vem depois.
    public void setNext(Node<T> next){
        this.next = next;
    }

    public Node<T> getNext(){
        return this.next;
    }

    public boolean isLastData(){
        return this.next == null; // só o último nó não aponta para ninguém
    }

}