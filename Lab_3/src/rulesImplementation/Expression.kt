package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class Expression (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    fun analyze(): ASTNode?{
        val children = arrayListOf<ASTNode?>()

        val unaryNode = unaryOperator()
        if(unaryNode!=null)
            children.add(unaryNode)

        val subExpressionNode = SubExpression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        subExpressionNode ?: return null

        children.add(subExpressionNode)

        val parent = constructTree(GrammarSymbols.EXPRESSION, children)
        if(parent == null)
            printErrMsg("expression")
        return parent
    }

    //  <Ун.оп.>
    private fun unaryOperator(): ASTNode? {
        return OperatorSign(getCurrentLexeme, moveToTheNextLexeme).unaryOperator()
    }
}