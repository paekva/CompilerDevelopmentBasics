package rulesImplementation

import ASTNode
import constructTree

//<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
class OperatorsList{

    // <Список операторов> ::= <Оператор><Продолжение списка операторов>
    fun analyze(): ASTNode?{
        val children = operatorsList()
        return constructTree(GrammarSymbols.OPERATORS_LIST, children)
    }

    // <Оператор><Продолжение списка операторов>
    private fun operatorsList(): ArrayList<ASTNode?> {
        val children = arrayListOf<ASTNode?>()

        val operatorNode = operator()

        if(operatorNode!=null)
            children.add(operatorNode)

        val continueOfOperatorListNode = continueOfOperatorList()
        children.addAll(continueOfOperatorListNode)
        return children
    }

    // <Продолжение списка операторов> ::= Ɛ | <Список операторов>
    private fun continueOfOperatorList(): List<ASTNode?>{
        if(KeyWords.isEnd())
            return emptyList()

        return operatorsList()
    }

    // <Оператор>
    private fun operator(): ASTNode? {
        return Operator().analyze()
    }
}