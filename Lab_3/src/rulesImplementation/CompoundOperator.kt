package rulesImplementation

import currentLexeme
import ASTNode
import constructTree

class CompoundOperator(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Составной оператор>::= Begin <Список операторов> End
    fun analyze(): ASTNode?{
        val keyWordsService = KeyWords(getCurrentLexeme, moveToTheNextLexeme)

        val beginNode = keyWordsService.begin()
        beginNode ?: return null

        val operatorsListNode = operatorsList()
        operatorsListNode ?: return null

        val endNode = keyWordsService.end()
        endNode ?: return null

        return constructTree(GrammarSymbols.COMPOUND_OPERATOR, arrayListOf(beginNode, operatorsListNode, endNode))
    }

    // < Список операторов >
    private fun operatorsList(): ASTNode?{
        return OperatorsList(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }

}