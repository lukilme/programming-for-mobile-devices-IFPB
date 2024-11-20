val RESET = "\u001B[0m"
val RED = "\u001B[31m"
val GREEN = "\u001B[32m"
val YELLOW = "\u001B[33m"
val BLUE = "\u001B[34m"
val MAGENTA = "\u001B[35m"
val CYAN = "\u001B[36m"
val WHITE = "\u001B[37m"


class Livro(val id: Int, var titulo: String, var preco: Float) {
    override fun toString(): String {
        return "${BLUE}Livro [${GREEN}ID: $id${RESET}, ${YELLOW}Titulo: $titulo${RESET}, ${MAGENTA}Preco: R$ %.2f${RESET}]".format(preco)
    }
}

fun print_msg(msg: String, type: String ) {
    when (type) {
        "normal" -> {
            print("$WHITE"+msg+"$RESET")
        }
        "sucess" -> {
            print("$GREEN"+msg+"$RESET")
        }
        "alert" -> {
            print("$YELLOW"+msg+"$RESET")
        }
        "error" -> {
            print("$RED"+msg+"$RESET")
        }
        "info" -> {
            print("$BLUE"+msg+"$RESET")
        }
        
        else -> print("Opção inválida")
    }
}

fun menu() {
    println(
        """
        ${CYAN}1 - Cadastrar livro
        2 - Excluir livro
        3 - Buscar livro
        4 - Editar livro
        5 - Listar livros
        6 - Listar livros que começam com letra escolhida
        7 - Listar livros com preço abaixo do informado
        8 - Sair${RESET}
        """.trimIndent()
    )
}

fun inputTitulo(): String {
    print_msg("Digite o título do livro: ","info")
    return readlnOrNull()?.takeIf { it.isNotBlank() } ?: "not valid"
}

fun inputPreco(): Float {
    print_msg("Digite o preco do livro: ","info")
    //val preco = readlnOrNull()!!.toFloat() o uso de '!!' não é recomendado
    val input = readlnOrNull()
    
    if (input.isNullOrBlank()) {
        return 0f  
    } else {
        return input.toFloatOrNull() ?: 0f 
    } 
}

fun inputId(): Int {
    print_msg("Digite o ID do livro: ","info")
    //val preco = readlnOrNull()!!.toFloat() o uso de '!!' não é recomendado
    val input = readlnOrNull()
    
    if (input.isNullOrBlank()) {
        return 0
    } else {
        return input.toIntOrNull() ?: 0
    } 
}

fun cadastrarLivro(repositorio: MutableList<Livro>, nextId: Int): Int {
    val titulo = inputTitulo()
    val preco = inputPreco()
    repositorio.add(Livro(nextId, titulo, preco))
    print_msg("Livro cadastrado com sucesso!\n","sucess")
    return nextId + 1
}

fun excluirLivro(repositorio: MutableList<Livro>) {
    val livro = buscarLivroPorTitulo(repositorio)
    if (livro != null) {
        repositorio.remove(livro)
        print_msg("Livro removido com sucesso!\n","sucess")
    } else {
        print_msg("Livro não encontrado!\n","alert")
    }
}

fun buscarLivroPorTitulo(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    val livro = repositorio.find { it.titulo.equals(titulo, ignoreCase = true) }
    if (livro == null) print_msg("Nenhum livro encontrado com o título \"$titulo\". \n", "alert")
    return livro
}

fun buscarLivroPorId(repositorio: MutableList<Livro>): Livro? {
    val id = inputId()
    val livro = repositorio.find { it.id.equals(id) }
    if (livro == null) print_msg("Nenhum livro encontrado com o ID \"$id\". \n", "error")
    return livro
}

fun buscarNome(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    return repositorio.find { it.titulo == titulo }
}


fun editarLivro(repositorio: MutableList<Livro>) {
    val livro = buscarLivroPorId(repositorio)
    if (livro != null) {
        print_msg("Editando livro: $livro","info")
        livro.titulo = inputTitulo()
        livro.preco = inputPreco()
        print_msg("Livro editado com sucesso!\n","sucess")
    }
}

fun listar(repositorio: MutableList<Livro>) {
    if (repositorio.isEmpty()) {
        print_msg("Nenhum livro cadastrado.\n","alert")
    } else {
        print_msg("Livros cadastrados:\n","info")
        repositorio.forEach { println(it) }
    }
}

fun listarComLetraInicial(repositorio: MutableList<Livro>) {
    print("Informe a letra: ")
    val letra = readlnOrNull()?.takeIf { it.length == 1 } ?: return println("Letra inválida.")
    val livros = repositorio.filter { it.titulo.startsWith(letra, ignoreCase = true) }
    if (livros.isEmpty()) {
        print_msg("Nenhum livro encontrado começando com a letra \"$letra\". \n","alert")
    } else {
        livros.forEach { println(it) }
    }
}

fun listarComPrecoAbaixo(repositorio: MutableList<Livro>) {
    val preco = inputPreco()
    val livros = repositorio.filter { it.preco < preco }
    if (livros.isEmpty()) {
        print_msg("Nenhum livro encontrado com R$ %.2f. \n".format(preco),"alert")
    } else {
        livros.forEach { println(it) }
    }
}

fun main() {
    val repositorioLivros = mutableListOf<Livro>()
    var nextId = 1

    repositorioLivros.add(Livro(nextId++, "Livro dos Livros", 999.99f))
    repositorioLivros.add(Livro(nextId++, "Turma da Mônica", 4.99f))
    repositorioLivros.add(Livro(nextId++, "Kotlin for Dummies", 29.99f))
    repositorioLivros.add(Livro(nextId++, "Aprendendo a Programar", 59.99f))

    var opcao: Int
    do {
        menu()
        // println(repositorioLivros[0]) ??? pra que?
        print_msg("Digite a opção: ","info")
        opcao = readlnOrNull()?.toInt() ?:8

        when (opcao) {
            1 -> nextId = cadastrarLivro(repositorioLivros, nextId)
            2 -> excluirLivro(repositorioLivros)
            3 -> buscarNome(repositorioLivros)
            
            4 -> editarLivro(repositorioLivros)
            5 -> listar(repositorioLivros)
            6 -> listarComLetraInicial(repositorioLivros)
            7 -> listarComPrecoAbaixo(repositorioLivros)
            8 -> print_msg("Até a próxima, vejo vocês no inferno😉\n","alert")
            else -> print_msg("Opção inválida. Tente novamente.\n","error")
        }
        Thread.sleep(500)// de 3000 para 500, tenho mais o que fazer.
    } while (opcao != 8)
}