import java.io.File

class LexemeStreamReader(private val filePath: String = "E:/lexemTable.txt") {

    fun parseLexemeParserOutput(): ArrayList<Lexeme>{
        val lexemeStringList = readFile()
        val lexemeList = arrayListOf<Lexeme>()
        lexemeStringList.forEach { lexeme -> lexemeList.add(getLexemeByString(lexeme)) }
        return lexemeList
    }

    private fun readFile(): List<String>
            = File(filePath).readLines(Charsets.UTF_8)

    private fun getLexemeByString(lexeme: String): Lexeme{
        val regex = "<(\\d+),(\\S+)>".toRegex()
        val matchResult = regex.find(lexeme)
        val (type, sign) = matchResult!!.destructured

        var lt: LexemeType? = LexemeType.values().find{ el -> el.code == type.toInt() }
        if(lt==null) lt = LexemeType.UNRECOGNISED

        return Lexeme( lt, sign)
    }
}