import java.util.*
import kotlin.collections.ArrayList

class Recogniser(private val listOfRules: List<TransitionRules>,private val alphabet: Array<Symbol>,private val relations: Array<Array<Relation>>){

    private val store: Stack<Symbol> = Stack()
    private val msg: String = "Input does not belong to a grammar"

    init{
        store.push(alphabet[alphabet.size-1])
    }

    fun recognise(input: ArrayList<Symbol>){
        input.add(alphabet[alphabet.size-1])

        var i = 0
        while(i<input.size){
            val storeEl = store.peek()
            if( input[i] == alphabet[alphabet.size-1] && store.size == 2 && storeEl == alphabet[0] )
                break
            val relation = compare(storeEl, input[i])

            when(relation){
                Relation.NONE -> throw Exception(msg)
                Relation.MORE -> {
                    convert()
                    i--
                }
                else -> store.push(input[i])
            }

            i++
        }

        println("Recognised")
    }

    private fun convert(){
        var condition = true
        val base = arrayListOf(Symbol(" "))

        while(condition){
            val newVal = store.pop()
            base.add(newVal)
            if(compare(store.peek(),newVal) == Relation.LESS)
                condition = false
        }

        base.removeAt(0)
        base.reverse()
        val newSymbol = findRule(base)

        store.push(newSymbol)
    }

    private fun findRule(base: ArrayList<Symbol>): Symbol{
        for(it in listOfRules){
            if(compareLists(base,it.to))
            {
                print("${it.from.value} ->")
                it.to.forEach{ sym ->
                    print(sym.value)
                }

                return it.from
            }
        }
        throw Exception(msg)
    }

    private fun compareLists(first: ArrayList<Symbol>, second: ArrayList<Symbol>): Boolean{
        if(first.size != second.size) return false
        first.forEachIndexed { index, symbol ->
            if(symbol.value != second[index].value)
                return false
        }
        return true
    }

    private fun compare(stack: Symbol, input: Symbol): Relation{
        val stackSymbol = alphabet.find{it.value == stack.value}
        val inputSymbol = alphabet.find{it.value == input.value}
        val stackInd = alphabet.indexOf(stackSymbol)
        val inputInd = alphabet.indexOf(inputSymbol)
        return relations[stackInd][inputInd]
    }

}

enum class Relation(val sign: String) {
    EQUAL("="), MORE(">"), LESS("<"), NONE("")
}

class TransitionRules(val from: Symbol, val to: ArrayList<Symbol>)

class Symbol(val value: String)