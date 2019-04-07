import java.io.File

fun main(){
    val file = readFile("E:/in.txt")
    val lexicalAnaliser = LexicalAnaliser(file)
    val table = lexicalAnaliser.analise()
    writeToFile(table, lexicalAnaliser.lineLexemCount, "E:/out.txt" )
}

fun readFile(fileName: String): List<String>
        = File(fileName).readLines(Charsets.UTF_8)

fun writeToFile(table: ArrayList<Lexem>, lineNumbers: ArrayList<Int>, fileName: String){
    var line = 0
    val myfile = File(fileName)

    myfile.printWriter().use { out ->
        table.forEachIndexed{ index, it ->
            if (lineNumbers[line] < index) line ++
            out.println("line:$line <${it.type},${it.value}>")
        }
    }
}