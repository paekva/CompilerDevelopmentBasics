package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class CompoundOperator(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Составной оператор>::= Begin < Список операторов > End
    fun analyze(): ASTNode?{
        val beginNode = begin()
        moveToTheNextLexeme()

        val operatorsListNode = operatorsList()
        moveToTheNextLexeme()

        val endNode = end()
        moveToTheNextLexeme()

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, arrayListOf(beginNode, operatorsListNode, endNode))
        if(parent == null)
            printErrMsg("compound operator")

        return parent
    }

    // Begin
    private fun begin(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.BEGIN) {
            return ASTNode(GrammarSymbols.BEGIN, lexeme)
        }
        return null
    }

    // < Список операторов >
    private fun operatorsList(): ASTNode?{
        return OperatorsList(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }

    // End
    private fun end(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.END) {
            return ASTNode(GrammarSymbols.END, lexeme)
        }
        return null
    }
}