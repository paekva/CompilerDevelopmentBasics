package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg
import Lexem

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

        val unaryOperatorNode = unaryOperator(lexeme)
        if(unaryOperatorNode!=null){
            children.add(unaryOperatorNode)
            moveToTheNextLexeme.invoke()
        }

        val subExpressionNode = SubExpression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        children.add(subExpressionNode)

        return children
    }

    //  <Ун.оп.>
    private fun unaryOperator(lexeme: Lexem): ASTNode? {
        return OperatorSign(getCurrentLexeme).unaryOperator()
    }
}