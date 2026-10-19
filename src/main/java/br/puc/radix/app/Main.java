package br.puc.radix.app;

import br.puc.radix.lista.LinkedList;
import br.puc.radix.lista.Node;
import br.puc.radix.ordenacao.RadixSortLSD;

/**
 * Demonstração do Radix Sort LSD sobre lista encadeada.
 *
 * Roda os 3 casos exigidos no enunciado (aleatório, quase ordenado e inverso),
 * todos com 8 elementos para dar para acompanhar à mão. Em cada caso confere
 * se a lista saiu ordenada e se nenhum nó foi perdido.
 */
public class Main {

    public static void main(String[] args) {
        // Caso 1: ordem sem padrão (os valores do exemplo feito à mão)
        demonstrate("Caso 1: aleatório",
                new int[]{170, 45, 75, 90, 802, 24, 2, 66});

        // Caso 2: crescente, com 66 e 75 fora do lugar
        demonstrate("Caso 2: quase ordenado",
                new int[]{2, 24, 45, 75, 66, 90, 170, 802});

        // Caso 3: totalmente decrescente
        demonstrate("Caso 3: inverso",
                new int[]{802, 170, 90, 75, 66, 45, 24, 2});
    }

    // Monta a lista, ordena com o Radix e confere o resultado.
    private static void demonstrate(String title, int[] values) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int v : values) {
            list.addLast(v);
        }

        System.out.println("=== " + title + " ===");
        System.out.print("Antes:  ");
        list.print();

        new RadixSortLSD().bucketCoordination(list);

        System.out.print("Depois: ");
        list.print();
        System.out.println("Ordenada: " + isSorted(list));
        System.out.println("Tamanho preservado: " + (list.getSize() == values.length));
        System.out.println();
    }

    // true se cada nó é menor ou igual ao próximo. Só percorre a lista.
    private static boolean isSorted(LinkedList<Integer> list) {
        Node<Integer> current = list.getFirst();
        while (current != null && current.getNext() != null) {
            if (current.getData() > current.getNext().getData()) {
                return false;
            }
            current = current.getNext();
        }
        return true;
    }
}