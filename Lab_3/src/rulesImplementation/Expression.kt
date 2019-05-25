package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class Expression (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    fun analyze(): ASTNode?{
        val children = subExpressionWithUnaryOperator()
        val subExpressionNode = SubExpression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        children.add(subExpressionNode)

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, children)
        if(parent == null)
            printErrMsg("expression")
        return parent
    }

    // <Ун.оп.> <Подвыражение>
    private fun subExpressionWithUnaryOperator(): ArrayList<ASTNode?>{
        val lexeme = getCurrentLexeme.invoke()
        val children: ArrayList<ASTNode?> = arrayListOf()

        if(lexeme.type == LexemType.UNI_MATH_OPERATOR){
            val unaryOperatorNode = ASTNode(GrammarSymbols.UNARY_OPERATOR, lexeme)
            children.add(unaryOperatorNode)
            moveToTheNextLexeme.invoke()
        }

        return children
    }
}