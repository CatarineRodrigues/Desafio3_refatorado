package menu

import constantes.MSG_ERRO
import constantes.MSG_OBRIGADO_FINAL
import constantes.MSG_OPCAO_INVALIDA
import constantes.MSG_QUAL_PRODUTO
import constantes.REFRIGERANTES
import constantes.SUCOS
import constantes.X_BURGER
import constantes.X_SALADA
import carrinho.Carrinho
import kotlin.system.exitProcess

class Menu {
    private var opcaoMenuPrincipal: Int = 0
    private var subMenuGeral: Int = 0
    private val carrinhoCompras = Carrinho()

    private fun menuPrincipal() {
        println("-----------------------------------------")
        println("----------- MENU PRINCIPAL --------------")
        println("---- 1 Comprar Lanche -------------------")
        println("---- 2 Comprar Bebida -------------------")
        println("---- 3 Verificar carrinho.Carrinho de Compras ----")
        println("---- 4 Remover produtos do carrinho.Carrinho -----")
        println("---- 5 Finalizar Compra -----------------")
        println("---- 6 Sair -----------------------------")
        println("-----------------------------------------")
        opcaoMenuPrincipal = carrinhoCompras.validarNumeroDigitado()
    }

    private fun menu2(tipo1: String, tipo2: String) {
        println(MSG_QUAL_PRODUTO)
        println("1. $tipo1")
        println("2. $tipo2")
        subMenuGeral = validarOpcoesSubMenu()
    }

    fun redirecionamentoDoMenu() {
        while (true) {
            menuPrincipal()
            when (opcaoMenuPrincipal) {
                1 -> {
                    menu2(X_BURGER, X_SALADA)
                    when (subMenuGeral) {
                        1 , 2 -> {
                            carrinhoCompras.redirecionarLanche(subMenuGeral)
                            carrinhoCompras.mostrarProdutos()
                        }
                    }
                }
                2 -> {
                    menu2(REFRIGERANTES, SUCOS)
                    when (subMenuGeral) {
                        1 , 2 -> {
                            carrinhoCompras.redirecionarBebida(subMenuGeral)
                            carrinhoCompras.mostrarProdutos()
                        }
                    }
                }
                3 -> carrinhoCompras.mostrarProdutos()
                4 -> carrinhoCompras.removerProdutos()
                5 -> carrinhoCompras.finalizarPedido()
                6 -> {
                    println(MSG_OBRIGADO_FINAL)
                    exitProcess(0)
                }
                else -> println(MSG_OPCAO_INVALIDA)
            }
        }
    }

    private fun validarOpcoesSubMenu(): Int {
        return try {
            var opcaoSelecionada = readln().toInt()
            while (opcaoSelecionada !in 1..2) {
                println(MSG_OPCAO_INVALIDA)
                opcaoSelecionada = readln().toInt()
            }
            opcaoSelecionada
        } catch (exception: NumberFormatException) {
            println(MSG_ERRO)
            validarOpcoesSubMenu()
        }
    }
}

//    fun sistema() {
//        boasVindas()
//        menu(constantes.LANCHE, constantes.BEBIDA)
//        opcaoMenuPrincipal = validarOpcoesSubMenu()
//        subMenu(opcaoSelecionada)
//    }