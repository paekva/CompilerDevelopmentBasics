import java.util.*

class Recogniser(val listOfRules: List<TransitionRules>, val alphabet: List<String>){

    val relations: Array<Array<Int>>
    val store: Stack<String>

    init{
        relations =  Array(alphabet.size+1, ({ Array(alphabet.size+1, ({0})) }))
        store = Stack()
        store.push("#")

    }

    fun recognise(input: List<String>): Boolean{
        return false
    }
}