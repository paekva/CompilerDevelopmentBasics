fun main(){
    val lexemeList = LexemStreamReader()
        .parseLexemParserOutput()

    val ast = SyntaxAnalyzer(lexemeList).beginAnalise()
    printAST(ast)
}

fun printAST(astNode: ASTNode?){
    if(astNode == null)
        println("Cannot produce the tree")
    else {
        if(astNode.lexeme != null) println(astNode.lexeme.sign)
        else println("${astNode.type.sign} -->")
        astNode.getChildren().forEach{
            printAST(it)
        }
    }

}