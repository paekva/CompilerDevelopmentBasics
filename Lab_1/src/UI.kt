import java.io.File

class UI{

    val alphabet: Array<Symbol>
    val arrayListOfRules: List<TransitionRules>
    val relations: Array<Array<Relation>>

    init{
        alphabet = getSymbols()
        arrayListOfRules = getArrayListOfRules(alphabet)
        relations =  Array(alphabet.size, ({ Array(alphabet.size, ({Relation.LESS})) }))

        fillInRelationTable()
    }

    fun getInput(): ArrayList<Symbol>{
        val inputText = "bababdc"
        val list = inputText.toList().map { Symbol(it.toString()) }
        return ArrayList(list)
    }

    private fun getSymbols(): Array<Symbol>{
        val spec = Symbol("#")
        val cT = Symbol("c")
        val dT = Symbol("d")
        val aT = Symbol("a")
        val bT = Symbol("b")
        val s = Symbol("S")
        val a = Symbol("A")
        val b = Symbol("B")
        val m = Symbol("M")
        val w = Symbol("W")
        val t = Symbol("T")
        val k = Symbol("K")
        return arrayOf(s,a,b,w,t,m,k,cT,dT,aT,bT,spec)
    }

    private fun getArrayListOfRules(alphabet: Array<Symbol>):List<TransitionRules>{
        val S = alphabet.find{it.value =="S"}
        val A = alphabet.find{it.value =="A"}
        val B = alphabet.find{it.value =="B"}
        val W = alphabet.find{it.value =="W"}
        val T = alphabet.find{it.value =="T"}
        val M = alphabet.find{it.value =="M"}
        val K = alphabet.find{it.value =="K"}
        val c = alphabet.find{it.value =="c"}
        val d = alphabet.find{it.value =="d"}
        val a = alphabet.find{it.value =="a"}
        val b = alphabet.find{it.value =="b"}

        if(S == null || A == null || B==null || W==null || T==null
            || M == null || K==null || c == null || d == null || a == null || b == null) throw Exception()

        return listOf(
            TransitionRules(S, arrayListOf(c,A)),
            TransitionRules(S, arrayListOf(B)),
            TransitionRules(S, arrayListOf(d)),
            TransitionRules(S, arrayListOf(c,d)),
            TransitionRules(A, arrayListOf(a,b,A)),
            TransitionRules(A, arrayListOf(a,b,d)),
            TransitionRules(B, arrayListOf(W,c)),
            TransitionRules(W, arrayListOf(b,A)),
            TransitionRules(B, arrayListOf(T,c)),
            TransitionRules(T, arrayListOf(b,d)),
            TransitionRules(B, arrayListOf(M,b)),
            TransitionRules(M, arrayListOf(a,A)),
            TransitionRules(B, arrayListOf(K,b)),
            TransitionRules(K, arrayListOf(a,d))
        )
    }

    private fun fillInRelationTable(){
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