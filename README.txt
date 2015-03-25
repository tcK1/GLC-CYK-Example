# EP-ITC-2014

Cógido de Leitura e Interpretação de Cadeias em função de uma Linguagem.

O programa recebe como entrada dois arquivos de texto (.txt). Em um deles existe a linguagem em si, que está formatado da seguinte forma:

1.  A primeira linha contem os campos:
    q t s
    onde "q" é o n´umero de variáveis, "t" é o número de símbolos terminais e "s" é o número de regras de substituição; 
2.  A segunda linha contem a lista de variaveis. (A variavel inicial vem em primeiro);
3.  A terceira linha contem a lista de terminais;
4.  A quarta linha em diante contem as regras de substituição. (Variável > Variável).

EX.:
Para a especificação abaixo;                          Temos o seguinte arquivo:
  S0 → & | A T | B U | S S | A B | B A                  6 2 15
  S → A T | B U | S S | A B | B A                       S0 S T U A B
  T → S B                                               a b
  U → S A                                               S0 > &
  A → a                                                 S0 > A T
  B → b                                                 S0 > B U
                                                        S0 > S S
                                                        S0 > A B
                                                        S0 > B A
                                                        S > A T
                                                        S > B U
                                                        S > S S
                                                        S > A B
                                                        S > B A
                                                        T > S B
                                                        U > S A
                                                        A > a
                                                        B > b

No outro arquivo, estão as cadeias a serem avaliadas, descritos da seguinte forma:

1.	Na primeira linha a quantidade de cadeias a serem avaliadas;
2. 	Na segunda linha em diante as cadeias em sí. (Uma cadeia por linha).

EX.:

(Seguindo a especificação do exemplo acima).

3
&
a
a b a a b b

Estes arquivos são nomeados como: "inp-glc.txt" e "inp-cadeias.txt" respectivamente.

Como saídas, terão dois arquivos: "out-status.txt" e "out-tabela.txt".

O conteúdo do primeiro é simples: 1 se a cadeia é aceita ou 0 se ela é rejeitada.

EX.:

(Seguindo a especificação e as cadeias dos exemplos acima)

1 0 1

Já o segundo arquivo contém uma série de tabelas criadas pelo algoritimo CYK, um para cada cadeia apresentada, e contem a seguinte extrutura:
1.	Na primeira linha a quantidade de cadeias analisadas.
2.	Na segunda linha em diante, aparecera a cadeia analisada e em baixo a tabela gerada pelo algoritmo.

EX.:

(Seguindo a especificação e as cadeias dos exemplos acima)

3
& 						<-- Para cadeia vazia, nã se imprime a matriz de variaveis
a 						<-- Segunda cadeia
1 1 A
a b a a b b 	        <-- Terceira cadeia
1 1 					<-- Cada linha corresponde a uma posição (i,j) da matriz
1 2 S0 S 			    <-- Separação por espacos
1 3 U
1 4
1 5
1 6 S0 S
2 2 B
2 3 S0 S
2 4 U
2 5 S0 S
2 6 T
3 3 A
3 4
3 5
3 6 S0 S
4 4 A
4 5 S0 S
4 6 T
5 5 B
5 6
6 6 B
