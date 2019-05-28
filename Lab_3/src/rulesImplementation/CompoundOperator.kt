package rulesImplementation

import ASTNode
import constructTree

class CompoundOperator{

    // <Составной оператор>::= Begin <Список операторов> End
    fun analyze(): ASTNode?{
        val beginNode = KeyWords.begin()
        beginNode ?: run {
            ErrorLog.logError("Нет begin в начале вложенного блока")

            SyntaxAnalyzer.skipCurrentLine()
            return null
        }

        val operatorsListNode = operatorsList()
        operatorsListNode ?: return null

        val endNode = KeyWords.end()
        endNode ?: run {
            ErrorLog.logError("Нет end в конце вложенного блока")
            SyntaxAnalyzer.skipCurrentLine()
            return null
        }

        return constructTree(GrammarSymbols.COMPOUND_OPERATOR, arrayListOf(beginNode, operatorsListNode, endNode))
    }

    // < Список операторов >
    private fun operatorsList(): ASTNode?{
        return OperatorsList().analyze()
    }

}