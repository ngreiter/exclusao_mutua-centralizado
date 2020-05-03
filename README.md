# SincronizacaoRelogio

Algoritmo usado para a sincronização interna de um grupo de computadores.
- "Servidor de tempo" é ativo (master) e coleta os valores de relógios de outros (slaves).
- Master usa estimativas para estimar o valor dos relógios dos computadores dentro dos grupos.
- Hora atual é resultante de uma média.
- Master envia ao slaves o total de tempo em que os relógios devem adiantar/atrasar.
- Caso o master falhe, um novo computador master é eleito.

1. Servidor solicita a hora dos clientes
2. Cada cliente responde ao servidor informando qual é a diferença de tempo em relação a ele
3. O servidor efetua a média dos tempos (incluindo a leitura dele).
4. O  servidor encaminha o ajuste necessário a ser feito pelo cliente (média  + inversão  da diferença de tempo enviada no passo 2. 
5.Cliente realiza o ajuste.
