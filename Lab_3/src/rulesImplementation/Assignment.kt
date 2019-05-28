package rulesImplementation
import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class Assignment(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Присваивание> ::= <Идент> := <Выражение>
    fun analyze(): ASTNode? {
        val identifierNode = identifier()
        identifierNode ?: return null

        val assignmentSignNode = assignment()
        assignmentSignNode ?: return null

        val expressionNode = expression()
        expressionNode ?: return null

        removeLineBreak()
        return constructTree(GrammarSymbols.ASSIGNMENT, arrayListOf(identifierNode, assignmentSignNode, expressionNode ))
    }

    private fun removeLineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK) {
            moveToTheNextLexeme()
            return true
        }
        return false
    }

    // <Идент>
    private fun identifier(): ASTNode?{
        return Operand(getCurrentLexeme, moveToTheNextLexeme).identifier()
    }

    // :=
    private fun assignment(): ASTNode?{
        return OperatorSign(getCurrentLexeme, moveToTheNextLexeme).assigmentSign()
    }

    // <Выражение>
    private fun expression(): ASTNode?{
        return Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }
}
