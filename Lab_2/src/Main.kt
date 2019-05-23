import java.io.File

fun main(){
    val charBuffer = readFile("E:/in.txt")
    val lexicalAnaliser = LexicalAnalyzer(charBuffer)
    lexicalAnaliser.analyze()
    writeToFile(lexicalAnaliser.lexemTable, "E:/lexemTable.txt" )
    writeToFile(lexicalAnaliser.indentTable, "E:/indentTable.txt" )
}


fun readFile(fileName: String): CharArray {
    var bytes:ByteArray = File(fileName).readBytes()
    val tmp = CharArray(bytes.size)
    bytes.forEachIndexed { index, byte -> tmp[index] = byte.toChar() }
    return tmp
}


fun writeToFile(table: ArrayList<Lexem>, fileName: String){
    File(fileName).printWriter().use { out ->
        table.forEachIndexed{ index, it ->
            out.println("<${it.type.code},${it.value}>")
        }
    }
}