# LSD-Radix-Sort

Trabalho NT1: **Lista Encadeada + Ordenação**.
Implementação do **Radix Sort LSD** (dígito menos significativo primeiro) sobre uma **lista encadeada simples** feita do zero em Java, sem estruturas prontas (`java.util.LinkedList`, `Collections.sort` etc.).

A ordenação é feita **religando ponteiros** dos nós, e não copiando valores. Nenhum nó novo é criado durante a ordenação.

---

## Como compilar e rodar

### Requisitos

- JDK 23 ou superior
- Maven 3.8 ou superior (ou a IDE IntelliJ IDEA)

### Pelo terminal

```bash
# clonar
git clone https://github.com/V4nz1n/LSD-Radix-Sort.git
cd LSD-Radix-Sort

# compilar
mvn compile

# rodar a demonstração
java -cp target/classes br.puc.radix.app.Main

# rodar os testes
mvn test
```

### Pelo IntelliJ IDEA

1. **File → Open** e selecione a pasta do projeto (abra como projeto Maven).
2. Aguarde o Maven baixar as dependências.
3. Abra `src/main/java/br/puc/radix/app/Main.java` e clique no ícone verde ao lado do `main`.

---

## Estrutura do projeto

```
LSD-Radix-Sort/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/br/puc/radix/
    │   ├── lista/
    │   │   ├── Node.java            → nó com valor (data) e referência para o próximo (next)
    │   │   └── LinkedList.java      → lista encadeada simples (first, last, size)
    │   ├── ordenacao/
    │   │   └── RadixSortLSD.java    → algoritmo de ordenação com baldes
    │   └── app/
    │       └── Main.java            → demonstração e casos de teste
    └── test/java/br/puc/radix/      → testes automatizados (JUnit 4)
```

### Responsabilidade de cada classe

| Classe | Responsabilidade |
|---|---|
| `Node<T>` | Guarda um valor e a referência para o próximo nó |
| `LinkedList<T>` | Guarda e liga nós. Mantém `first`, `last` e `size` sempre consistentes |
| `RadixSortLSD` | Coordena a ordenação. Não guarda dados: cria os baldes, distribui os nós e junta de volta |
| `Main` | Monta as listas de teste e mostra o resultado |

### Operações da lista

| Operação | Método | Custo |
|---|---|---|
| Inserir no início | `addFirst` | O(1) |
| Inserir no fim | `addLast` | O(1) |
| Remover por valor | `removeValue` | O(n) |
| Buscar | `searchValue` | O(n) |
| Imprimir | `print` | O(n) |
| Tamanho | `getSize` | O(1) |

Operações auxiliares usadas pelo Radix:

| Método | O que faz | Custo |
|---|---|---|
| `getFirst` | Devolve o primeiro nó | O(1) |
| `addNode` | Anexa ao fim um nó que já existe (zera o `next` dele) | O(1) |
| `concat` | Junta outra lista ao fim da atual, sem percorrer nada | O(1) |
| `clear` | Esvazia a lista sem desmontar os nós | O(1) |

> A lista guarda um ponteiro `last`, e é ele que torna as inserções no fim O(1). Sem ele, cada inserção percorreria a lista inteira.

---

## Como o Radix Sort LSD funciona

O algoritmo **não compara** os números entre si. Ele ordena dígito por dígito, do menos significativo (unidades) ao mais significativo, e a cada passada distribui os nós em 10 baldes (0 a 9) conforme o dígito da vez.

**Uma passada tem três etapas:**

1. **Criar** 10 baldes vazios (cada balde é uma `LinkedList`).
2. **Distribuir:** percorrer a lista do início ao fim e mover cada nó para o fim do balde do seu dígito, calculado por `(valor / exp) % 10`.
3. **Juntar:** esvaziar a lista original e emendar os baldes de 0 a 9, em ordem.

O `exp` vale 1 (unidades), 10 (dezenas), 100 (centenas)... e as passadas continuam enquanto `maior / exp > 0`, ou seja, uma passada por dígito do maior valor.

### Estabilidade

Cada nó entra no **fim** do balde, e a lista é percorrida do início ao fim. Assim, nós com o mesmo dígito mantêm a ordem em que chegaram. Isso é o que faz o algoritmo funcionar: a passada de um dígito mais significativo não desfaz a ordem conquistada pelas anteriores.

### Exemplo

Lista inicial: `170 → 45 → 75 → 90 → 802 → 24 → 2 → 66` (maior valor 802, 3 dígitos, 3 passadas).

| Passada | Dígito | Resultado |
|---|---|---|
| 1 | unidades | `170 90 802 2 24 45 75 66` |
| 2 | dezenas | `802 2 24 45 66 170 75 90` |
| 3 | centenas | `2 24 45 66 75 90 170 802` |

### Por que não há Counting Sort

No Radix com vetor, o Counting Sort é a sub-rotina estável de cada passada. Em lista encadeada, os **baldes** cumprem esse papel: distribuir os nós por dígito e concatenar os baldes já é uma ordenação estável, sem vetor auxiliar e sem calcular posições.

### Restrições

- Aceita apenas **inteiros não negativos**. Números negativos produziriam um dígito negativo e um índice inválido nos baldes.
- O vetor de 10 posições é usado apenas para guardar as referências dos baldes. Os dados continuam nos nós, e a lista nunca é convertida em vetor.

---

## Complexidade

Seja **n** o número de elementos, **d** a quantidade de dígitos do maior valor e **b** a base (10).

| Caso | Tempo | Observação |
|---|---|---|
| **Melhor** | O(d · (n + b)) | Igual aos demais |
| **Médio** | O(d · (n + b)) | Igual aos demais |
| **Pior** | O(d · (n + b)) | Igual aos demais |

Como **b = 10** é constante, o custo se reduz a **O(d · n)**.

### Por que os três casos são iguais

O algoritmo não depende da ordem inicial dos dados, apenas de **n** e de **d**. Uma lista já ordenada, aleatória ou invertida percorre os mesmos nós o mesmo número de vezes. Isso difere de algoritmos baseados em comparação, como o Bubble Sort, em que o melhor e o pior caso são muito diferentes.

### De onde vem o custo

- Cada passada percorre a lista uma vez para distribuir (**n**) e emenda os 10 baldes em O(1) cada (**b**).
- São **d** passadas, uma por dígito do maior valor.
- Achar o maior valor percorre a lista uma única vez, O(n), e não muda o custo total.

### Espaço extra

**O(b)**, apenas as referências dos 10 baldes. Nenhum nó novo é criado, porque os nós existentes são religados. Na versão com vetor, o Counting Sort exigiria um vetor auxiliar de tamanho n, ou seja, O(n).

### Limitações

- Só funciona para chaves que tenham dígitos (inteiros, strings de tamanho fixo).
- Quando os números são muito grandes em comparação com **n**, **d** cresce e o algoritmo perde a vantagem.

---

## Integrantes e divisão de tarefas

| Integrante | Responsabilidade |
|---|---|
| `Lucas Vanzin` | Estrutura do projeto, `RadixSortLSD`, `LinkedList` |
| `Miguel Rodrigues` | `LinkedList`, `Node` |
| `Arthur` | `Apresentação` |
| `João Henrique Luz` | `casos de teste e README` |
| `Gustavo Freire` | `apresentação` |
| `Guilherme Carrijo` | `apresentação` |

---
