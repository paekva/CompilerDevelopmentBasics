package rulesImplementation
import ASTNode
import constructTree

class Assignment{

    // <Присваивание> ::= <Идент> := <Выражение>
    fun analyze(): ASTNode? {
        val identifierNode = identifier()
        identifierNode ?: return null

        val assignmentSignNode = assignment()
        assignmentSignNode ?: run {
            ErrorLog.logError("Нет знака присваивания")
            SyntaxAnalyzer.skipCurrentLine()
            return null
        }

        val expressionNode = expression()
        expressionNode ?: run {
            ErrorLog.logError("Нет правой части операции присваивания")
            SyntaxAnalyzer.skipCurrentLine()
            return null
        }

        SyntaxAnalyzer.removeLineBreak()
        return constructTree(GrammarSymbols.ASSIGNMENT, arrayListOf(identifierNode, assignmentSignNode, expressionNode ))
    }

    // <Идент>
    private fun identifier(): ASTNode?{
        return Operand().identifier()
    }

    // :=
    private fun assignment(): ASTNode?{
        return OperatorSign().assignmentSign()
    }

    // <Выражение>
    private fun expression(): ASTNode?{
        return Expression().analyze()
    }
}
