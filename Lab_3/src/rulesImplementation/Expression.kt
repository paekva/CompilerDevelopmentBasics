package rulesImplementation

import ASTNode
import constructTree
import printErrMsg

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

        val parent = constructTree(GrammarSymbols.EXPRESSION, children)
        if(parent == null)
            printErrMsg("expression")
        return parent
    }

    //  <Ун.оп.>
    private fun unaryOperator(): ASTNode? {
        return OperatorSign().unaryOperator()
    }
}