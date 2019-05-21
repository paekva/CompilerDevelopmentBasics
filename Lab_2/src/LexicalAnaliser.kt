class LexicalAnaliser(private val file: List<String>) {
    val lexemTable: ArrayList<Lexem> = arrayListOf()
    val indentTable: ArrayList<Lexem> = arrayListOf()
    val lineLexemCount: ArrayList<Int> = arrayListOf()

    fun analise() {
        file.forEachIndexed { index, line ->
            lineAnaliser(line, index)

            var tmp = 0
            lineLexemCount.forEach { tmp += it }
            lineLexemCount.add(tmp + lexemTable.size - 1)
        }
    }

    private fun lineAnaliser(line: String, lineIndex: Int) {
        var lexem = ""
        var index = 0

        while (index < line.length) {
            val c = line[index]
            val isEndOfLine = index == line.length - 1
            val isSpaceSymbol = c == ' '
            val isBeginOfAComment = !isEndOfLine && c == '/' && line[index + 1] == '/'

            if (isBeginOfAComment)
                break


            lexem += c
            val type = recognise(lexem)
            val typeOfBiggerLexem = if (!isEndOfLine) recognise(lexem + line[index + 1]) else LexemType.UNRECOGNISED

            try {

                if (typeOfBiggerLexem != LexemType.UNRECOGNISED) {
                    index++
                    continue
                } else if (type != LexemType.UNRECOGNISED) {
                    addToLexemTable(lexem, type)
                    lexem = ""
                } else if (isSpaceSymbol)
                    lexem = ""
                else throw Exception()

            } catch (e: Exception) {
                println("Error in parsing expression '$lexem' at position ${lineIndex + 1} : ${index + 1}")
            }

            index++
        }
    }

    private fun recognise(lexem: String): LexemType {
        if (Words.values().map { it.lexem }.contains(lexem))
            return LexemType.WORD

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