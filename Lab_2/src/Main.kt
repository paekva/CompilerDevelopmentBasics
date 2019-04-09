import java.io.File

fun main(){
    val file = readFile("E:/in.txt")
    val lexicalAnaliser = LexicalAnaliser(file)
    lexicalAnaliser.analise()
    writeToFile(lexicalAnaliser.lexemTable, "E:/lexemTable.txt" )
    writeToFile(lexicalAnaliser.indentTable, "E:/indentTable.txt" )
}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines(Charsets.UTF_8)

fun writeToFile(table: ArrayList<Lexem>, fileName: String){
    File(fileName).printWriter().use { out ->
        table.forEachIndexed{ index, it ->
            out.println("<${it.type.code},${it.value}>")
        }
    }
}