/* Turma 94				*/
/* 8516048 - Kaic Nunes Bastidas	*/
/* 8598610 - Djean Axt  		*/

import java.io.*;
import java.util.*;
import java.lang.*;

class glc{

	public static Map<String, String[]> terminais; /* HashMap de subistituicoes terminais */

	public static Map<String, String[]> gramatica; /* HashMap de subistituicoes de gramatica */

	public static String variavelInicial; /* Variavel inicial da gramatica */

	public static String diretorioAtual; /* Diretorio de arquivos atual */

	@SuppressWarnings("unchecked")
	public static void main(String []args){
		try{

			int q;
			int s;
			terminais = new HashMap<String, String[]>(); /* Instanciando o HashMap das subistituicoes Terminais */
			gramatica = new HashMap<String, String[]>(); /* Instanciando o HashMap das subistituicoes de Gramatica */
			diretorioAtual = System.getProperty("user.dir"); /* Diretorio Atual */

  			Scanner sc1 = new Scanner(new File("inp-glc.txt")); /* Comeco da leitura de inp-glc.txt */

			q = sc1.nextInt(); /* numero de variaveis */
			sc1.nextInt(); /* numero de simbolos terminais (nao utlizado) */
			s = sc1.nextInt(); /* quantidade de regras de substituicao */

			sc1.nextLine();

			/* -------		 Leitura da Gramatia 		------- */

			String[] leGramatica = sc1.nextLine().split(" ");
			ArrayList<String> gramaticaTemp = new ArrayList<String>();
			
			for(int i = 0; i <= leGramatica.length-1; i++){
				gramaticaTemp.add(leGramatica[i]);
			}

			/* -------		 Leitura da Gramatia 		------- */

			variavelInicial = leGramatica[0]; /* Variavel inicial */
			
			/* -------		 Leitura das Terminais 		------- */

			String[] leTerminais = sc1.nextLine().split(" ");
			ArrayList<String> terminaisTemp = new ArrayList<String>();

			for(int i = 0; i <= leTerminais.length-1; i++){
				terminaisTemp.add(leTerminais[i]);
			}

			/* -------		 Leitura das Terminais 		------- */


			/* ------- Gramatica e Terminais temporarias ------- */
			@SuppressWarnings("unchecked")
			ArrayList<String>[] trancicoesG = new ArrayList[q];
			ArrayList<String>[] trancicoesT = new ArrayList[q];
			
			for(int i = 0; i < q; i++){
				trancicoesG[i] = new ArrayList<String>();
				trancicoesT[i] = new ArrayList<String>(); 
			}

			/* ------- Gramatica e Terminais temporarias ------- */

			/* -------		 Leitura e Insercao final das trancicoes		------- */
			
			for(int i = 1; i <= s; i++){
         		String[] linhaAtual = sc1.nextLine().split(" > ");  		
                     if(terminaisTemp.contains(linhaAtual[1])){
					trancicoesT[gramaticaTemp.indexOf(linhaAtual[0])].add(linhaAtual[1]); /* Se for final, adiciona valor nas terminais temporarias */
				}else{
					trancicoesG[gramaticaTemp.indexOf(linhaAtual[0])].add(linhaAtual[1]); /* Se nao, adiciona valor nas gramaticas temporarias */
				}
			}

			for(int i = 0; i < q; i++){

				String[] aux = (String[]) trancicoesG[i].toArray(new String[trancicoesG[i].size()]);
				gramatica.put(leGramatica[i], aux);

				String[] aux2 = (String[]) trancicoesT[i].toArray(new String[trancicoesT[i].size()]);
				terminais.put(leGramatica[i], aux2);
	
			}

			/* -------		 Leitura e Insercao final das trancicoes		------- */
			
			sc1.close(); /* Fim da leitura de inp-glc.txt */

			Scanner sc2 = new Scanner(new File("inp-cadeias.txt")); /* Comeco da leitura de inp-cadeias.txt */

			int qntCadeias = sc2.nextInt(); /* Quantidade de cadeias */
			sc2.nextLine();
			int[] respostasFinais = new int[qntCadeias]; /* Tabela de respostas finais (cadeia aceita ou nao) */


			/* ESCREVENDO "out-tabela.txt" */

			File outtabela = new File(diretorioAtual + "/out-tabela.txt"); /* Instancia novo documento de texto para o arquivos out-status.txt */
			if(!outtabela.exists()) outtabela.createNewFile();
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outtabela.getPath(), true)));
			pw.println(qntCadeias); /* escreve quantidade de cadeias analizadas */
			pw.close();

			/* ESCREVENDO "out-tabela.txt" */

			/* ------				CHAMADA AO METODO CYK			------ */

			for(int cadeias = 1; cadeias <= qntCadeias; cadeias++){
				String[] cadeiaAtual = sc2.nextLine().split(" "); /* Cada cadeia */
				if (cyk(cadeiaAtual)) respostasFinais[cadeias-1] = 1;
				else respostasFinais[cadeias-1] = 0;
			}

			/* ------				CHAMADA AO METODO CYK			------ */

			sc2.close(); /* Fim da leitura de inp-cadeias.txt */

			/* ESCREVENDO "out-status.txt" */

			File outstatus = new File(diretorioAtual + "/out-status.txt"); /* Instancia novo documento de texto para o arquivos out-status.txt */
			if(!outstatus.exists()) outstatus.createNewFile();
			PrintWriter pw2 = new PrintWriter(new BufferedWriter(new FileWriter(outstatus.getPath(), true)));
			for (int i = 0; i <= respostasFinais.length-1; i++) pw2.print(respostasFinais[i]+" "); /* Escreve cada posicao da tabela respostasFinais[] */
			pw2.close();

			/* ESCREVENDO "out-status.txt" */
		}
      	catch (IOException ex){
         	ex.printStackTrace();
      	}	
	}

	@SuppressWarnings("unchecked")
	public static boolean cyk(String[] entrada){
      	ArrayList<String>[][] tabelaRespostas; /* Instanciando matriz final de resposta */

      	/* Tamanho da cadeia sendo avaliada */

      	int tamanhoCadeia = 0;
		for (int i = 0; i <= entrada.length-1; i++) {
		 	tamanhoCadeia++;
		}

		/* Tamanho da cadeia sendo avaliada */

	   	tabelaRespostas = new ArrayList[tamanhoCadeia][]; /* Dando tamanho a tabela de respostas a partir do tamanho da cadeia */

	   	/* 	CASO DE CADEIA VAZIA "&" */
		if(entrada[0].charAt(0) == "&".charAt(0)){
			escreveTabela(tabelaRespostas, entrada, tamanhoCadeia);
			String [] aux = gramatica.get(variavelInicial);
			for (int i =0;i <= aux.length-1;i++){
				if(aux[i].contains("&")){
					return true;
				}
			}
		
		/* 	CASO DE CADEIA VAZIA "&" */

		/* 	EXECUTANDO O RESTO DO CODIGO CASO A CADEIA NAO SEJA VAZIA */

   		}else{

   			/* 	Criando os espacos na tabela */

   			for (int i = 0; i < tamanhoCadeia; i++){
       			tabelaRespostas[i] = new ArrayList[tamanhoCadeia];
       			for (int j = 0; j < tamanhoCadeia; j++) tabelaRespostas[i][j] = new ArrayList<String>();
   			}

   			/* 	Criando os espacos na tabela */

   			/* -----	Preenchendo a tabela 	----- */

   			for (int i = 0; i < tamanhoCadeia; i++){
       			Set<String> chaves = terminais.keySet();
       			for (String chaveAtual : chaves){
					String[] auxiliar = terminais.get(chaveAtual);
					for(int corredor = 0; corredor <= auxiliar.length-1; corredor++){
        				if (auxiliar[corredor].charAt(0) == entrada[i].charAt(0)){
        					if(tabelaRespostas[i][i].contains(chaveAtual)) break; /* Caso ja exista nao insere novamente */
        					tabelaRespostas[i][i].add(chaveAtual);
        				}
					}
       			}
   			}

	   		for (int l = 1; l <= tamanhoCadeia; l++){
    	   		for (int i = 0; i <= tamanhoCadeia - l; i++){
        			int j = i + l - 1;
        			for (int k = i; k <= j - 1; k++){
        	   			Set<String> chaves = gramatica.keySet();
           				for (String chaveAtual : chaves){
           					String[] palavra = gramatica.get(chaveAtual);
							for(int corredor = 0; corredor <= palavra.length-1; corredor++){
   		        				String[] partes = palavra[corredor].split(" ");
           		   				if (tabelaRespostas[i][k].contains(partes[0]) && tabelaRespostas[k + 1][j].contains(partes[1])){
	              					if(tabelaRespostas[i][j].contains(chaveAtual)) break; /* Caso ja exista nao insere novamente */
	              					tabelaRespostas[i][j].add(chaveAtual);
    	          				}
            	  			}
              			}
        			}
       			}
   			}
			
			/* -----	Preenchendo a tabela 	----- */

			escreveTabela(tabelaRespostas, entrada, tamanhoCadeia); /* Metodo para escrever a tabela no arquivo "out-tabela.txt" */

   			if (tabelaRespostas[0][tamanhoCadeia-1].contains(variavelInicial)){
   				return true; /* Caso no canto superior direito da tabela exista a "variavelInicial" previamente declarada, a cadeia foi aceita */
   			}
			return false; /* Caso nao, a cadeia foi rejeitada */
    	}

    	/* 	FIM DO CODIGO CASO A CADEIA NAO SEJA VAZIA */

    	return false;
	}

	public static void escreveTabela(ArrayList<String>[][] tabelaRespostas, String[] entrada, int tamanhoCadeia){
		try{
			File outtabela = new File (diretorioAtual + "/out-tabela.txt"); /* instancia out-status.txt */
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outtabela.getPath(), true)));

			/* Cadeia Vazia */

			if(entrada[0].charAt(0) == "&".charAt(0)){
				pw.println("&");
				pw.close();
				return;
			}

			/* Cadeia Vazia */

			/* Cadeia Valida */

			for (int i = 0; i< entrada.length; i++) pw.print(entrada[i]+" "); /* Printa a cadeia */
			pw.println();

			for (int i = 0; i < tamanhoCadeia; i++) {
				for (int j = 0; j < tamanhoCadeia; j++) {
					if( j >= i){
						pw.print((i+1)+" "+(j+1)+" "); /* Printa posicao */
						String[] aux = (String[]) tabelaRespostas[i][j].toArray(new String[tabelaRespostas[i][j].size()]);
						for (int k = aux.length - 1; k >= 0; k--) pw.print(aux[k]+" ");	/* Printa valores */
						pw.println();
					}					
				}
			}

			/* Cadeia Valida */

			pw.close();

		}catch (IOException ex){
         	ex.printStackTrace();
      	}
	}
}
