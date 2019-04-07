class LexicalAnaliser(private val file: List<String>){
    private val lexemTable: ArrayList<Lexem> = arrayListOf()
    val lineLexemCount: ArrayList<Int> = arrayListOf()

    fun analise(): ArrayList<Lexem>{
        file.forEachIndexed{index, line ->
            lineParser(line, index)

            var tmp = 0
            lineLexemCount.forEach{ tmp += it}
            lineLexemCount.add(tmp + lexemTable.size - 1)
        }
        return lexemTable
    }

    private fun lineParser(lexemStr: String, lineIndex: Int){
        var lexem = ""
        for(index: Int in 0..(lexemStr.length-1) ){
            val c = lexemStr[index]
            val isEndOfLine = index == lexemStr.length - 1
            val isSpaceSymbol = c == ' '
            val isBeginOfAComment = c == '/' && !isEndOfLine && lexemStr[index+1]=='/'
            val isRelationOperator = Relations.values().map { it.lexem }.contains(c.toString())
            val isMathOperator = MathOperators.values().map { it.lexem }.contains(c.toString())

            if(isSpaceSymbol || isRelationOperator || isMathOperator || isBeginOfAComment || isEndOfLine) {
                try{
                    if(lexem != "" ) lexemRecognition(lexem)
                }
                catch(e: Exception){
                    println("Error in parsing expression '$lexem' at position ${lineIndex+1} : ${index+1}")
                }

                if(isBeginOfAComment) break
                if(isRelationOperator) addToTable(c.toString(), LexemType.RELATION_OPERATOR)
                if(isMathOperator) addToTable(c.toString(), LexemType.MATH_OPERATOR)
                lexem = ""
            }

            if(!isSpaceSymbol && !isRelationOperator && !isMathOperator)
                lexem+=c
        }
    }

    private fun lexemRecognition(id: String){
        if(Words.values().map { it.lexem }.contains(id))
            addToTable(id, LexemType.WORD)
        else if(id.matches("^[0-9]+".toRegex())){
            val value = Integer.toHexString(id.toInt())
            addToTable(value.toString().toUpperCase(), LexemType.CONST)
        }
        else if(id.matches("^[a-zA-Z]+".toRegex()))
            addToTable(id, LexemType.IDENTIFIER)
        else throw Exception()
    }

    private fun addToTable(lexem: String, type: LexemType){
        lexemTable.add(Lexem(type,lexem))
    }
}


class Lexem(val type: LexemType, val value: String)

enum class LexemType(val code: Int){
    RELATION_OPERATOR(0),
    MATH_OPERATOR(1),
    WORD(2),
    IDENTIFIER(3),
    CONST(4)
}

enum class Words(val lexem: String) {
    Begin("Begin"),
    End("End"),
    Var("Var"),
    If("IF"),
    Then("THEN"),
    Else("ELSE"),
}

enum class Relations(val lexem: String) {
    MMore(">>"),
    MLess("<<"),
    More(">"),
    Less("<"),
    Equal("=")
}

enum class MathOperators(val lexem: String){
    Minus("-"),
    Plus("+"),
    Mul("*"),
    Div("/"),
}