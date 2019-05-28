package rulesImplementation

import ASTNode
import constructTree
import SyntaxAnalyzer

// <Список переменных> ::= <Идент> | <Идент> , <Список переменных>
class VariablesList{

    // <Список переменных> ::= <Идент> <Продолжение списка>
    fun analyze(): ASTNode? {
        val identifierNode = identifier()
        identifierNode ?: run {
            ErrorLog.logError("Bad variables declaration")
            SyntaxAnalyzer.skipCurrentLine()
            return null
        }
        val continueOfVariablesListNode = continueOfVariablesList()

        val children = arrayListOf<ASTNode?>()
        children.add(identifierNode)
        children.addAll(continueOfVariablesListNode)
        return constructTree(GrammarSymbols.OPERATORS_LIST, children)
    }

    //<Продолжение списка переменных> ::= Ɛ | , <Список переменных>
    private fun continueOfVariablesList(): List<ASTNode?>{
        if(lineBreak())
            return emptyList()

        return commaWithVariables()
    }

    // <Оператор>
    private fun identifier(): ASTNode? {
        return Operand().identifier()
    }

    // , <Список переменных>
    private fun commaWithVariables(): List<ASTNode?> {
        if(!OperatorSign().comma()){
            ErrorLog.logError("Comma not found")
            SyntaxAnalyzer.skipCurrentLine()
            return emptyList()
        }

        val variablesListNode = analyze()
        variablesListNode ?: run {
            ErrorLog.logError("No variables after comma")
            SyntaxAnalyzer.skipCurrentLine()
            return emptyList()
        }

        return variablesListNode.getChildren()
    }

    private fun lineBreak(): Boolean{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme() // gcl()
        if(lexeme.type == LexemType.LINEBREAK){
            ErrorLog.nextLine()
            return true
        }
        return false
    }

}