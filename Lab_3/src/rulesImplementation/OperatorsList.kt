package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

//<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
class OperatorsList(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Список операторов> ::= <Оператор><Продолжение списка операторов>
    fun analyze(): ASTNode?{
        val children = operatorsList()

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("operatorsList")
        return parent
    }

    // <Список операторов> ::= <Оператор><Продолжение списка операторов>
    private fun operatorsList(): ArrayList<ASTNode?> {
        val children = arrayListOf<ASTNode?>()

        val operatorNode = operator()
        children.add(operatorNode)

        val continueOfOperatorListNode = continueOfOperatorList()
        children.addAll(continueOfOperatorListNode)

        return children
    }

    // <Продолжение списка операторов> ::= Ɛ | <Список операторов>
    private fun continueOfOperatorList(): List<ASTNode?>{
        if(KeyWords(getCurrentLexeme, moveToTheNextLexeme).isEnd())
            return emptyList()

        return operatorsList()
    }

    // <Оператор>
    private fun operator(): ASTNode? {
        return Operator(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }
}