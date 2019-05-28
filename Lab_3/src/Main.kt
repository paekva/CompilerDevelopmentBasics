fun main(){
    val lexemList = LexemStreamReader()
        .parseLexemParserOutput()

    // lexemList.forEach { l -> println("${l.type} ${l.sign}") }

    val ast = SyntaxAnalyzer(lexemList).beginAnalise()
    printAST(ast)
}

fun printAST(astNode: ASTNode?){
    if(astNode == null)
        println("Cannot produce the tree")
    else {
        if(astNode.lexem != null) println("${astNode.type.sign} ${astNode.lexem.sign}")
        astNode.getChildren().forEach{
            println("-->\t")
            printAST(it)
        }
    }

}