import java.io.File

class LexemStreamReader(private val filePath: String = "E:/lexemTable.txt") {

    fun parseLexemParserOutput(): ArrayList<Lexem>{
        val lexemStringList = readFile()
        val lexemList = arrayListOf<Lexem>()
        lexemStringList.forEach { lexem -> lexemList.add(getLexemByString(lexem)) }
        return lexemList
    }

    private fun readFile(): List<String>
            = File(filePath).readLines(Charsets.UTF_8)

    private fun getLexemByString(lexem: String): Lexem{
        val regex = "<(\\d),(\\S+)>".toRegex()
        val matchResult = regex.find(lexem)
        val (type, sign) = matchResult!!.destructured

        var lt: LexemType? = LexemType.values().find{ el -> el.code == type.toInt() }
        if(lt==null) lt = LexemType.UNRECOGNISED

        return Lexem( lt, sign)
    }
}