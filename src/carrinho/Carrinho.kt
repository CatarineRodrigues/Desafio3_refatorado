package carrinho

import constantes.MSG_COMPRA_FINALIZADA
import constantes.MSG_ERRO
import constantes.MSG_OPCAO_INVALIDA
import constantes.MSG_PERGUNTA_FORMA_PGTO
import constantes.OPCOES_PGTO_CREDITO
import constantes.OPCOES_PGTO_DEBITO
import constantes.OPCOES_PGTO_DINHEIRO
import constantes.OPCOES_PGTO_VALE
import produtos.Produtos
import constantes.REFRIGERANTES
import constantes.SUCOS
import constantes.VALOR_REFRIGERANTE
import constantes.VALOR_SUCO
import constantes.VALOR_X_BURGER
import constantes.VALOR_X_SALADA
import constantes.X_BURGER
import constantes.X_SALADA
import kotlin.system.exitProcess

class Carrinho {
    private val dicionario = mutableMapOf<Int, ArrayList<Produtos>>()
    private var contador: Int = 0

    fun validarNumeroDigitado(): Int {
        return try {
            var opcaoSelecionada = readln().toInt()
            while (opcaoSelecionada <= 0) {
                println(MSG_OPCAO_INVALIDA)
                opcaoSelecionada = readln().toInt()
            }
            opcaoSelecionada
        } catch (exception: NumberFormatException) {
            println(MSG_ERRO)
            validarNumeroDigitado()
        }
    }

    private fun guardarProdutoCarrinho(listaProdutos: ArrayList<Produtos>): Int {
        contador += 1
        dicionario[contador] = listaProdutos
        return contador
    }

    fun mostrarProdutos() {
        if (dicionario.isEmpty()) {
            println("Não tem nenhum item cadastrado ainda")
        } else {
            dicionario.forEach { (chave, valor) ->
                println("Código do produto: $chave")
                valor.forEach {
                    println("${it.qntd} ${it.nome} - Valor total: R$ ${it.qntd * it.valor}")
                }
            }
        }
    }

    fun removerProdutos() {
        println("Digite o código do item que deseja remover")
        val numero = validarNumeroDigitado()
        if (dicionario.containsKey(numero)) {
            println("----------------------")
            println("Foi removida o item foi removido com sucesso")
            println("----------------------")
            dicionario.remove(numero)
        } else {
            println("Código não encontrado")
        }
    }

    private fun pedirQuantidade(item: String): Int {
        println("Qual quantidade de $item que deseja comprar?")
        return validarNumeroDigitado()
    }

    fun redirecionarLanche(subMenu: Int) {
        when (subMenu) {
            1 -> selecionarProduto(X_BURGER, VALOR_X_BURGER)
            2 -> selecionarProduto(X_SALADA, VALOR_X_SALADA)
        }
    }

    fun redirecionarBebida(subMenu: Int) {
        when (subMenu) {
            1 -> selecionarProduto(REFRIGERANTES, VALOR_REFRIGERANTE)
            2 -> selecionarProduto(SUCOS, VALOR_SUCO)
        }
    }

    private fun selecionarProduto(tipoProduto: String, valorProduto: Double) {
        val qnt = pedirQuantidade(tipoProduto)
        val produto = Produtos(tipoProduto, qnt, valorProduto)
        val listaProdutos: ArrayList<Produtos> = ArrayList()
        listaProdutos.add(produto)
        guardarProdutoCarrinho(listaProdutos)
    }

    fun finalizarPedido() {
        mostrarProdutos()
        println("O valor total da compra é de R$ " + valorFinalPedido())
        pagarPedido(formaPagamento())
    }

    private fun formaPagamento(): Int {
        println(MSG_PERGUNTA_FORMA_PGTO)
        println("1- $OPCOES_PGTO_CREDITO")
        println("2- $OPCOES_PGTO_DEBITO")
        println("3- $OPCOES_PGTO_VALE")
        println("4- $OPCOES_PGTO_DINHEIRO")
        return validarNumeroDigitado()
    }

    private fun pgtoDinheiro(valorTotal: Double) {
        println("Total da compra R$ $valorTotal")
        println("Insira o valor em dinheiro a ser pago")
        try {
            var valorDinheiro = readln().toDouble()
            while (valorDinheiro < valorTotal) {
                println("Dinheiro insuficiente, tente novamente.")
                valorDinheiro = readln().toDouble()
            }
            if (valorDinheiro > valorTotal) {
                val troco = valorTotal - valorDinheiro
                println("Seu troco é de R$ $troco")
                println(MSG_COMPRA_FINALIZADA)
                exitProcess(0)
            } else {
                println(MSG_COMPRA_FINALIZADA)
                exitProcess(0)
            }
        } catch (exception: NumberFormatException) {
            println(MSG_ERRO)
            pgtoDinheiro(valorTotal)
        }
    }

    private fun pagarPedido(formaPgto: Int) {
        when (formaPgto) {
            1, 2, 3 -> {
                println(MSG_COMPRA_FINALIZADA)
                exitProcess(0)
            }
            4 -> pgtoDinheiro(valorFinalPedido())
            else -> {
                println(MSG_OPCAO_INVALIDA)
                finalizarPedido()
            }
        }
    }

    private fun valorFinalPedido(): Double {
        var valorTotalPedido = 0.0
        var i = 0
        while (i < dicionario.size) {
            dicionario.forEach { (_, valor) ->
                valor.forEach {
                    valorTotalPedido += it.qntd * it.valor
                }
                i++
            }
        }
        return valorTotalPedido
    }
}