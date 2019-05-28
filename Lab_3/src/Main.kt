fun main(){
    val lexemeList = LexemStreamReader()
        .parseLexemParserOutput()

    val ast = SyntaxAnalyzer(lexemeList).beginAnalise()
    printAST(ast, 0)
}

fun printAST(astNode: ASTNode?, level: Int){
    if(astNode == null)
        println("Cannot produce the tree")
    else {
        var tabulate = level
        while(tabulate>0){
            print("\t")
            tabulate--
        }
        if(astNode.lexeme != null) print("|-- ${astNode.lexeme.sign}")
        else print("|-- ${astNode.type.sign}")

        if(astNode.getChildren().isNotEmpty())
            println(" -->")
        else println()

        astNode.getChildren().forEach{
            printAST(it, level+1)
        }
    }

}