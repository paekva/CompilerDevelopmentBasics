import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class Recogniser(val listOfRules: List<TransitionRules>, val alphabet: Array<Symbol>){

    val relations: Array<Array<Relation>>
    val store: Stack<Symbol>
    val msg: String = "Input does not belong to a grammar"

    init{
        relations =  Array(alphabet.size, ({ Array(alphabet.size, ({Relation.LESS})) }))
        store = Stack()
        store.push(Symbol("#") )

    }

    fun recognise(input: ArrayList<Symbol>){
        input.forEach{
            val storeEl = store.peek()
            val relation = compare(storeEl, it)
            val current = it
            input.remove(it)

            when(relation){
                Relation.NONE -> throw Exception(msg)
                Relation.LESS -> convert(current)
                else -> store.push(current)
            }
        }

        val sym = store.pop()
        if( (sym != alphabet[0]) || (store.peek() != alphabet[alphabet.size-1]) )
            throw Exception(msg)

    }

    fun convert(element: Symbol){
        var condition = true
        val base = arrayListOf(element)

        while(condition){
            val newVal = store.pop()
            base.add(newVal)
            if(compare(store.peek(),newVal) == Relation.LESS)
                condition = false
        }

        val newSymbol = findRule(base)

        store.push(newSymbol)
    }

    fun findRule(base: ArrayList<Symbol>): Symbol{
        listOfRules.forEach{
            if(base == it.to) return it.from
        }
        throw Exception(msg)
    }

    fun compare(stack: Symbol, input: Symbol): Relation{
        val stackInd = alphabet.indexOf(stack)
        val inputInd = alphabet.indexOf(input)
        return relations[stackInd][inputInd]
    }

    fun fillInRelationTable(){
        val fileLines = readFileAsLinesUsingBufferedReader("E:/relations.txt")

        fileLines.forEachIndexed{ lineNum, value ->
            val line = value.toCharArray()
            var index = 0
            line.forEach {
                when(it){
                    ';' -> index++
                    '>' -> placeInRelationTable(lineNum, index, Relation.MORE)
                    '<' -> placeInRelationTable(lineNum, index, Relation.LESS)
                    '=' -> placeInRelationTable(lineNum, index, Relation.EQUAL)
                    else -> placeInRelationTable(lineNum, index, Relation.NONE)
                }
            }
            placeInRelationTable(lineNum, alphabet.size-1, Relation.MORE)
        }
    }

    private fun placeInRelationTable(lineNum: Int, columnNum: Int, relation: Relation){
        relations[lineNum][columnNum] = relation
    }

    private fun readFileAsLinesUsingBufferedReader(fileName: String): List<String>
            = File(fileName).bufferedReader().readLines()

}

enum class Relation(val sign: String) {
    EQUAL("="), MORE(">"), LESS("<"), NONE("")
}

class TransitionRules(val from: Symbol, val to: List<Symbol>)