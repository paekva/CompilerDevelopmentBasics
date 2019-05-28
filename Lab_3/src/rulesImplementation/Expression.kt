package rulesImplementation

import ASTNode
import constructTree

class Expression{

    // <Выражение> ::= <Ун.оп.> <Подвыражение> | <Подвыражение>
    fun analyze(): ASTNode?{
        val children = arrayListOf<ASTNode?>()

        val unaryNode = unaryOperator()
        if(unaryNode!=null)
            children.add(unaryNode)

        val subExpressionNode = SubExpression().analyze()
        subExpressionNode ?: return null

        children.add(subExpressionNode)

        return constructTree(GrammarSymbols.EXPRESSION, children)
    }

    //  <Ун.оп.>
    private fun unaryOperator(): ASTNode? {
        return OperatorSign().unaryOperator()
    }
}