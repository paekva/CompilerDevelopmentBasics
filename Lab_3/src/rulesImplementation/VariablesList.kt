package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

// <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
class VariablesList (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Список переменных> ::= <Идент> <Продолжение списка>
    fun analyze(): ASTNode? {
        val identifierNode = identifier()
        val continueOfVariablesListNode = continueOfVariablesList()

        val children = arrayListOf(identifierNode)
        children.addAll(continueOfVariablesListNode)

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("variablesList")
        return parent
    }

    //<Продолжение списка переменных> ::= Ɛ | , <Список переменных>
    private fun continueOfVariablesList(): List<ASTNode?>{
        if(lineBreak())
            return emptyList()

        return commaWithVariables()
    }

    // <Оператор>
    private fun identifier(): ASTNode? {
        return Operand(getCurrentLexeme, moveToTheNextLexeme).identifier()
    }

    // , <Список переменных>
    private fun commaWithVariables(): List<ASTNode?> {
        if(!comma())
            return emptyList()

        val variablesListNode = analyze()
        if(variablesListNode == null)
            return emptyList()

        return variablesListNode.getChildren()
    }

    private fun lineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK)
            return true
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