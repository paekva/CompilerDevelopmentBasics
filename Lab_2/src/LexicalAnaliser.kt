class LexicalAnaliser(private val file: List<String>){
    private val lexemTable: ArrayList<Lexem> = arrayListOf()
    val lineLexemCount: ArrayList<Int> = arrayListOf()

    fun analise(): ArrayList<Lexem>{
        file.forEachIndexed{index, line ->
            lineAnaliser(line, index)

            var tmp = 0
            lineLexemCount.forEach{ tmp += it}
            lineLexemCount.add(tmp + lexemTable.size - 1)
        }
        return lexemTable
    }

    private fun lineAnaliser(line: String, lineIndex: Int){
        var lexem = ""
        var index = 0

        while(index < line.length){
            val c = line[index]
            val isEndOfLine = index == line.length - 1
            val isSpaceSymbol = c == ' '
            val isBeginOfAComment = !isEndOfLine && c == '/' && line[index+1]=='/'

            if(isBeginOfAComment)
                break


            lexem+=c
            val type = recognise(lexem)
            val typeOfBiggerLexem = if(!isEndOfLine) recognise(lexem + line[index + 1]) else LexemType.UNRECOGNISED

            try {
                if(typeOfBiggerLexem != LexemType.UNRECOGNISED) {
                    index++
                    continue
                }
                else if(type != LexemType.UNRECOGNISED ){
                    addToTable(lexem, type)
                    lexem = ""
                }
                else if (isSpaceSymbol)
                    lexem=""
                else throw Exception()
            }
            catch(e: Exception){
                println("Error in parsing expression '$lexem' at position ${lineIndex+1} : ${index+1}")
            }

            index++
        }
    }

    private fun recognise(lexem: String): LexemType{
        if(Words.values().map { it.lexem }.contains(lexem))
            return LexemType.WORD

        if(MathOperators.values().map { it.lexem }.contains(lexem))
            return LexemType.MATH_OPERATOR

        if(Relations.values().map { it.lexem }.contains(lexem))
            return LexemType.RELATION_OPERATOR

        if(lexem.matches("^[0-9]+".toRegex()))
            return LexemType.CONST

        if(lexem.matches("^[a-zA-Z]+".toRegex()))
            return LexemType.IDENTIFIER

        return LexemType.UNRECOGNISED
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
    CONST(4),
    UNRECOGNISED(5)
}

enum class Words(val lexem: String) {
    Is(":="),
    LBRACE("("),
    RBRACE(")"),
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