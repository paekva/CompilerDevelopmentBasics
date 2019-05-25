package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class CompoundOperator(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Составной оператор>::= Begin < Список операторов > End
    fun analyze(): ASTNode?{
        val keyWordsService = KeyWords(getCurrentLexeme, moveToTheNextLexeme)

        val beginNode = keyWordsService.begin()
        moveToTheNextLexeme()

        val operatorsListNode = operatorsList()
        moveToTheNextLexeme()

        val endNode = keyWordsService.end()
        moveToTheNextLexeme()

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, arrayListOf(beginNode, operatorsListNode, endNode))
        if(parent == null)
            printErrMsg("compound operator")

        return parent
    }

    // < Список операторов >
    private fun operatorsList(): ASTNode?{
        return OperatorsList(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }

}