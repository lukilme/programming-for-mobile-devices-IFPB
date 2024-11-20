class Livro(val id: Int, var titulo: String, var preco: Float) {
    override fun toString(): String {
        return "Livro [ID: $id, Titulo: $titulo, Preco: R$ %.2f]".format(preco)
    }
}

fun menu() {
    println(
        """
        1 - Cadastrar livro
        2 - Excluir livro
        3 - Buscar livro
        4 - Editar livro
        5 - Listar livros
        6 - Listar livros que começam com letra escolhida
        7 - Listar livros com preço abaixo do informado
        8 - Sair
        """.trimIndent()
    )
}

fun inputTitulo(): String {
    print("Digite o título do livro: ")
    return readlnOrNull()?.takeIf { it.isNotBlank() } ?: "not valid"
}

fun inputPreco(): Float {
    print("Digite o preco do livro: ")
    //val preco = readlnOrNull()!!.toFloat() o uso de '!!' não é recomendado
    val input = readlnOrNull()
    
    if (input.isNullOrBlank()) {
        return 0f  
    } else {
        return input.toFloatOrNull() ?: 0f 
    }
       
}

fun cadastrarLivro(repositorio: MutableList<Livro>, nextId: Int): Int {
    val titulo = inputTitulo()
    val preco = inputPreco()
    repositorio.add(Livro(nextId, titulo, preco))
    println("\nLivro cadastrado com sucesso!\n")
    return nextId + 1
}

fun excluirLivro(repositorio: MutableList<Livro>) {
    val livro = buscarLivroPorTitulo(repositorio)
    if (livro != null) {
        repositorio.remove(livro)
        println("Livro removido com sucesso!")
    } else {
        println("Livro não encontrado!")
    }
}

fun buscarLivroPorTitulo(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    val livro = repositorio.find { it.titulo.equals(titulo, ignoreCase = true) }
    if (livro == null) println("Nenhum livro encontrado com o título \"$titulo\".")
    return livro
}

fun buscarNome(repositorio: MutableList<Livro>): Livro? {
    val titulo = inputTitulo()
    return repositorio.find { it.titulo == titulo }
}


fun editarLivro(repositorio: MutableList<Livro>) {
    val livro = buscarLivroPorTitulo(repositorio)
    if (livro != null) {
        println("Editando livro: $livro")
        livro.titulo = inputTitulo()
        livro.preco = inputPreco()
        println("Livro editado com sucesso!")
    }
}

fun listar(repositorio: MutableList<Livro>) {
    if (repositorio.isEmpty()) {
        println("Nenhum livro cadastrado.")
    } else {
        println("Livros cadastrados:")
        repositorio.forEach { println(it) }
    }
}

fun listarComLetraInicial(repositorio: MutableList<Livro>) {
    print("Informe a letra: ")
    val letra = readlnOrNull()?.takeIf { it.length == 1 } ?: return println("Letra inválida.")
    val livros = repositorio.filter { it.titulo.startsWith(letra, ignoreCase = true) }
    if (livros.isEmpty()) {
        println("Nenhum livro encontrado começando com a letra \"$letra\".")
    } else {
        livros.forEach { println(it) }
    }
}

fun listarComPrecoAbaixo(repositorio: MutableList<Livro>) {
    val preco = inputPreco()
    val livros = repositorio.filter { it.preco < preco }
    if (livros.isEmpty()) {
        println("Nenhum livro encontrado com R$ %.2f.".format(preco))
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
        print("Digite a opção: ")
        opcao = readlnOrNull()?.toInt() ?:8

        when (opcao) {
            1 -> nextId = cadastrarLivro(repositorioLivros, nextId)
            2 -> excluirLivro(repositorioLivros)
            3 -> buscarNome(repositorioLivros)
            
            4 -> editarLivro(repositorioLivros)
            5 -> listar(repositorioLivros)
            6 -> listarComLetraInicial(repositorioLivros)
            7 -> listarComPrecoAbaixo(repositorioLivros)
            8 -> println("Até a próxima :)")
            else -> println("Opção inválida. Tente novamente.")
        }
        Thread.sleep(500)// de 3000 para 500, tenho mais o que fazer.
    } while (opcao != 8)
}