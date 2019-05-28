package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class SubExpression(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Подвыражение> :: = ( <Выражение> ) <Вспомогательное выражение>  | <Операнд> <Вспомогательное выражение>
    fun analyze(): ASTNode? {
        val lex = getCurrentLexeme()
        val children: ArrayList<ASTNode?> = arrayListOf()

        val bracedExpressionNode = startsWithBracedExpression()
        if(bracedExpressionNode.isEmpty()){
            val subExpressionWithOperandNode = subExpressionWithOperand()
            children.addAll(subExpressionWithOperandNode)
        }
        else children.addAll(bracedExpressionNode)

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
        val operatorSignService = OperatorSign(getCurrentLexeme, moveToTheNextLexeme)

        if(!operatorSignService.leftBrace())
            return null

        val expressionNode = Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
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
        if(helpfulExpressionNode.isEmpty())
            children.addAll(helpfulExpressionNode)

        return children
    }

    // <Вспомогательное выражение> :: = <Бин.оп.><Подвыражение><Вспомогательное выражение> | Ɛ
    private fun helpfulExpression(): ArrayList<ASTNode?>{
        if(lineBreak())
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
        return Operand(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }


    // <Бин.оп.>
    private fun binaryOperator(): ASTNode? {
        return OperatorSign(getCurrentLexeme, moveToTheNextLexeme).binaryOperator()
    }

    private fun lineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK)
            return true
        return false
    }
}