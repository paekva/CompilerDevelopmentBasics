package rulesImplementation
import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class Assignment(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Присваивание> ::= <Идент> := <Выражение>
    fun analyze(): ASTNode? {
        val identifierNode = identifier()
        if(identifierNode == null)
            return null

        val assignmentSignNode = assignment()
        if(assignmentSignNode == null)
            return null

        val expressionNode = expression()

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, arrayListOf(identifierNode, assignmentSignNode, expressionNode ))
        if(parent == null)
            printErrMsg("assignment")

        return parent
    }


    // <Идент>
    private fun identifier(): ASTNode?{
        return Operand(getCurrentLexeme, moveToTheNextLexeme).identifier()
    }

    // :=
    private fun assignment(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.DECLARE) {
            moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.ASSIGNMENT_SIGN, lexeme)
        }
        return null
    }

    // <Выражение>
    private fun expression(): ASTNode?{
        return Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }
}
