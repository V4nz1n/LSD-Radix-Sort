package br.puc.radix.ordenacao;

import br.puc.radix.lista.LinkedList;
import br.puc.radix.lista.Node;

/**
 * Radix Sort LSD (dígito menos significativo primeiro) sobre lista encadeada.
 * A cada passada, os nós são distribuídos em 10 baldes pelo dígito da vez e os baldes
 * são juntados de volta, de 0 a 9. Os baldes fazem o papel do Counting Sort dos vetores.
 * Nenhum nó é criado: só ponteiros são religados.
 * Custo O(d * n) no melhor, médio e pior caso (d = dígitos do maior valor, n = elementos).
 * Limitação: aceita apenas inteiros não negativos.
 */
public class RadixSortLSD {
    // 10 baldes, um para cada dígito de 0 a 9. Cada balde é uma lista encadeada.
    private LinkedList<Integer>[] buckets;

    // Devolve o maior valor da lista: ele define quantas passadas (dígitos) serão feitas.
    public int findMax(LinkedList<Integer> list) {
        Node<Integer> current = list.getFirst();
        int max = Integer.MIN_VALUE; // qualquer valor da lista é maior ou igual a este
        for (int i = 0; i < list.getSize(); i++) {
            if (current.getData() > max) {
                max = current.getData();
            }
            current = current.getNext();
        }
        return max;
    }

    // Cria 10 baldes vazios. Chamado a cada passada para os nós da passada anterior não se misturarem.
    public void createBuckets() {
        // new LinkedList<Integer>[10] não compila em Java, por isso o aviso "unchecked" é esperado
        this.buckets = new LinkedList[10]; // criar o vetor não cria as listas: as 10 posições começam null
        for (int i = 0; i < this.buckets.length; i++) {
            this.buckets[i] = new LinkedList<>(); // cada posição recebe uma lista vazia
        }
    }

    // Move cada nó da lista para o balde do seu dígito, sem criar nós novos.
    // Percorrer do início ao fim e entrar no FIM do balde mantém a estabilidade.
    public void distributeNodes(LinkedList<Integer> list, int exp) {
        Node<Integer> current = list.getFirst();
        while (current != null) {
            Node<Integer> varTemp = current.getNext(); // guarda o próximo ANTES: o addNode zera o next
            int digit = ((current.getData() / exp) % 10); // exp 1 = unidades, 10 = dezenas, 100 = centenas...
            this.buckets[digit].addNode(current); // o dígito é o índice do balde
            current = varTemp; // avança pelo nó guardado, e não pelo getNext() do atual
        }
    }

    // Esvazia a lista e emenda os baldes nela, na ordem de 0 a 9.
    public void gatherBuckets(LinkedList<Integer> list) {
        list.clear(); // vem antes: os nós já estão nos baldes e a lista precisa estar vazia
        for (int i = 0; i < this.buckets.length; i++) {
            list.concat(this.buckets[i]); // baldes vazios são ignorados pelo concat
        }
    }

    // Coordena o algoritmo: repete criar baldes, distribuir e juntar, uma vez por dígito.
    public void bucketCoordination(LinkedList<Integer> list){
        int exp = 1; // posição do dígito da passada atual
        if(list.getSize() <= 1){ // vazia ou com um elemento: já está ordenada
            return;
        }
        int max = this.findMax(list); // calculado uma vez, pois não muda entre as passadas
        while(max / exp > 0){ // para quando passa do último dígito do maior valor
            this.createBuckets();
            this.distributeNodes(list, exp);
            this.gatherBuckets(list);
            exp *= 10; // passa para o próximo dígito (acima de ~1 bilhão estoura o int)
        }
    }

    // Só para depuração: mostra o conteúdo de cada balde.
    public void printBuckets() {
        for (int i = 0; i < this.buckets.length; i++) {
            System.out.print("balde " + i + ": ");
            this.buckets[i].print();
        }
    }
}