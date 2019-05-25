fun main(){
    val lexemList = LexemStreamReader()
        .parseLexemParserOutput()

    // lexemList.forEach { l -> println("${l.type} ${l.sign}") }

    val ast = SyntaxAnalyzer(lexemList).beginAnalise()
}