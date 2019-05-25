package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

//<Список операторов> ::= <Оператор> | <Оператор> <Список операторов>
class OperatorsList(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Список операторов> ::= <Оператор><Продолжение списка >
    fun analyze(): ASTNode?{
        val operatorNode = operator()
        val continueOfOperatorListNode = continueOfOperatorList()

        val children = arrayListOf(operatorNode)
        children.addAll(continueOfOperatorListNode)

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("operatorsList")
        return parent
    }

    // <Продолжение списка операторов> ::= Ɛ | ,<Список операторов>
    private fun continueOfOperatorList(): List<ASTNode?>{
        if(lineBreak())
            return emptyList()

        return commaWithOperators()
    }

    // <Оператор>
    private fun operator(): ASTNode? {
        return Operator(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }

    // , <Список операторов>
    private fun commaWithOperators(): List<ASTNode?> {
        if(!comma())
            return emptyList()

        val operatorsListNode = analyze()
        if(operatorsListNode == null)
            return emptyList()

        return operatorsListNode.getChildren()
    }

    private fun lineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK){
            moveToTheNextLexeme()
            return true
        }
        return false
    }

    private fun comma(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.COMMA){
            moveToTheNextLexeme()
            return true
        }
        return false
    }
}