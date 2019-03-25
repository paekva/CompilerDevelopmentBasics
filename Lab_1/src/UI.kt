class UI(){
    fun getData(){
        // set rules

        val alphabet = ArrayList<String>()
        alphabet.addAll(getTerminators())
        alphabet.addAll(getNonTerminators())

        /*val listOfRules: List<TransitionRules> = listOf(
            TransitionRules(alphabet.S, listOf(c,A)),
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

        val recogniser = Recogniser(listOfRules, alphabet)
        fillRelationsTable(recogniser.relations)
        recogniser.recognise(...)
        */
    }

    private fun getTerminators(): List<String>{
        val c = "c"
        val d = "d"
        val a = "a"
        val b = "b"
        return listOf(a,b,c,d)
    }

    private fun getNonTerminators(): List<String>{
        val S = "S"
        val A = "A"
        val B = "B"
        val W = "W"
        val T = "T"
        val M = "M"
        val K = "K"
        return listOf(S,A,B,W,T,M,K)
    }

    private fun fillRelationsTable(relations: Array<Array<Int>>){

    }
}