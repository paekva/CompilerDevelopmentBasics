class UI{

    fun getData(){
        val alphabet = getSymbols()

        val listOfRules: List<TransitionRules> = getListOfRules(alphabet)

        val recogniser = Recogniser(listOfRules, alphabet)
        recogniser.fillInRelationTable()
        val input = getInput()
        recogniser.recognise(input)
    }

    private fun getInput(): ArrayList<Symbol>{
        val inputText = "aabbcd"
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
        return arrayOf(s,a,b,w,t,m,k,aT,bT,cT,dT,spec)
    }

    private fun getListOfRules(alphabet: Array<Symbol>):List<TransitionRules>{
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
            TransitionRules(S, listOf(c,A)),
            TransitionRules(S, listOf(B)),
            TransitionRules(S, listOf(d)),
            TransitionRules(S, listOf(c,d)),
            TransitionRules(A, listOf(a,b,A)),
            TransitionRules(A, listOf(a,b,d)),
            TransitionRules(B, listOf(W,c)),
            TransitionRules(W, listOf(b,A)),
            TransitionRules(B, listOf(T,c)),
            TransitionRules(T, listOf(b,d)),
            TransitionRules(B, listOf(M,b)),
            TransitionRules(M, listOf(a,A)),
            TransitionRules(B, listOf(K,b)),
            TransitionRules(K, listOf(a,d))
        )
    }
}

class Symbol(val value: String)