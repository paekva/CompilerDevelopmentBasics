class LexicalAnalyzer(private val charBuffer: CharArray) {

    private var currentSymbolPosition: Int = 0
    private fun getCurrentSymbol(): Char = charBuffer[currentSymbolPosition]
    private fun getNextSymbol(): Char = if(currentSymbolPosition + 1 < charBuffer.size) charBuffer[currentSymbolPosition + 1] else '@'
    private fun isStartOfTheComment(): Boolean = getCurrentSymbol() == '/' && getNextSymbol() == '/'
    private fun isEndOfLine() = getCurrentSymbol() == '\r' && getNextSymbol() == '\n'

    fun analyze(){
        var lexem = ""
        while(currentSymbolPosition < charBuffer.size){
            if(isStartOfTheComment()) getComment()

            val isSpaceSymbol = getCurrentSymbol() == ' '
            val isCommaSymbol = getCurrentSymbol() == ','
            val isEndOfLine = isEndOfLine()

            if (isCommaSymbol) addToLexemTable(",", LexemType.COMMA)
            else if(isEndOfLine) {
                addToLexemTable("/n", LexemType.LINEBREAK)
                currentSymbolPosition++
            }
            else if(!isSpaceSymbol){
                lexem+=getCurrentSymbol()

                val type = recognise(lexem)
                val typeOfBiggerLexem = recognise(lexem+getNextSymbol())

                if(typeOfBiggerLexem != LexemType.UNRECOGNISED) {
                    currentSymbolPosition++
                    continue
                }
                else addToLexemTable(lexem, type)
            }

            currentSymbolPosition++
            lexem = ""
        }
    }

    private fun getComment(){
        while(!isEndOfLine()){
            currentSymbolPosition++
        }
    }

    val lexemTable: ArrayList<Lexem> = arrayListOf()
    val indentTable: ArrayList<Lexem> = arrayListOf()

    private fun recognise(lexem: String): LexemType {
        when(lexem){
            "Begin" -> return LexemType.BEGIN
            "Var" -> return LexemType.VAR
            "End" -> return LexemType.END
            "IF" -> return LexemType.IF
            "THEN" -> return LexemType.THEN
            "ELSE" -> return LexemType.ELSE
            ":=" -> return LexemType.DECLARE
            "(" -> return LexemType.LBRACE
            ")" -> return LexemType.RBRACE
        }

        if (UniMathOperators.values().map { it.lexem }.contains(lexem)
            && lexemTable[lexemTable.size - 1].type != LexemType.IDENTIFIER && lexemTable[lexemTable.size - 1].type != LexemType.CONST
        )
            return LexemType.UNI_MATH_OPERATOR

        if (BinMathOperators.values().map { it.lexem }.contains(lexem))
            return LexemType.BIN_MATH_OPERATOR

        if (Relations.values().map { it.lexem }.contains(lexem))
            return LexemType.RELATION_OPERATOR

        if (lexem.matches("^[0-9]+".toRegex()))
            return LexemType.CONST

        if (lexem.matches("^[a-zA-Z]+".toRegex()))
            return LexemType.IDENTIFIER

        return LexemType.UNRECOGNISED
    }

    private fun addToLexemTable(lexem: String, type: LexemType) {
        lexemTable.add(Lexem(type, lexem))

        if (type == LexemType.IDENTIFIER || type == LexemType.CONST)
            addToIdentTable(lexem, type)
    }

    private fun addToIdentTable(lexem: String, type: LexemType) {
        val newLexem = Lexem(type, lexem)
        if(!indentTable.map { it.value }.contains(newLexem.value))
            indentTable.add(newLexem)
    }
}