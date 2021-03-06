package rulesImplementation

import ASTNode
import constructTree

class SubExpression{

    // <Подвыражение> :: = ( <Выражение> ) <Вспомогательное выражение>  | <Операнд> <Вспомогательное выражение>
    fun analyze(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()

        val bracedExpressionNode = startsWithBracedExpression()
        if(bracedExpressionNode.isEmpty()){
            val subExpressionWithOperandNode = subExpressionWithOperand()
            children.addAll(subExpressionWithOperandNode)
        }
        else children.addAll(bracedExpressionNode)

        if(children.size == 1)
            return children[0]

        return constructTree(GrammarSymbols.SUB_EXPRESSION, children)
    }

    // ( <Выражение> ) <Вспомогательное выражение>
    private fun startsWithBracedExpression(): List<ASTNode?> {
        val children: ArrayList<ASTNode?> = arrayListOf()

        val bracedExpressionNode = bracedExpression()
        bracedExpressionNode ?: return emptyList()
        children.add(bracedExpressionNode)

        val helpfulExpressionNode = helpfulExpression()
        children.addAll(helpfulExpressionNode)

        return children
    }

    // ( <Выражение> )
    private fun bracedExpression(): ASTNode? {
        val operatorSignService = OperatorSign()

        if(!operatorSignService.leftBrace())
            return null

        val expressionNode = Expression().analyze()
        expressionNode?: return null

        if(!operatorSignService.rightBrace())
            return null

        return expressionNode
    }

    // <Операнд> <Вспомогательное выражение>
    private fun subExpressionWithOperand(): ArrayList<ASTNode?> {
        val children: ArrayList<ASTNode?> = arrayListOf()

        val operandNode = operand()
        operandNode ?: return arrayListOf()
        children.add(operandNode)

        val helpfulExpressionNode = helpfulExpression()
        children.addAll(helpfulExpressionNode)

        return children
    }

    // <Вспомогательное выражение> :: = <Бин.оп.><Подвыражение><Вспомогательное выражение> | Ɛ
    private fun helpfulExpression(): ArrayList<ASTNode?>{
        if(SyntaxAnalyzer.removeLineBreak())
            return arrayListOf()

        return subExpressionsWithBinaryOperator()
    }

    // <Бин.оп.><Подвыражение><Вспомогательное выражение>
    private fun subExpressionsWithBinaryOperator(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()

        val binaryOperator = binaryOperator()
        binaryOperator ?: return arrayListOf()
        children.add(binaryOperator)

        val subExpressionNode = analyze()
        subExpressionNode ?: return arrayListOf()
        children.add(subExpressionNode)

        val helpfulExpressionNode = helpfulExpression()
        children.addAll(helpfulExpressionNode)

        return children
    }

    // <Операнд>
    private fun operand(): ASTNode?{
        return Operand().analyze()
    }


    // <Бин.оп.>
    private fun binaryOperator(): ASTNode? {
        return OperatorSign().binaryOperator()
    }
}