package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

// <Подвыражение> :: = ( <Выражение> ) | <Операнд> | <Подвыражение > <Бин.оп.> <Подвыражение>
class SubExpression(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Подвыражение> :: = ( <Выражение> ) <Вспомогательное выражение>  | <Операнд> <Вспомогательное выражение>
    fun analyze(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()
        val lexeme = getCurrentLexeme()

        val bracedExpressionNode = bracedExpression()
        if(bracedExpressionNode==null){
            val subExpressionWithOperandNode = subExpressionWithOperand()
            children.add(subExpressionWithOperandNode)
        }
        else children.add(bracedExpressionNode)

        val parent = constructTree(GrammarSymbols.SUB_EXPRESSION, children)
        if (parent == null)
            printErrMsg("subExpression")
        return parent
    }

    // <Подвыражение> :: = ( <Выражение> ) <Вспомогательное выражение>
    private fun bracedExpression(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()
        val lexeme = getCurrentLexeme()
        val operatorSignService = OperatorSign(getCurrentLexeme, moveToTheNextLexeme)

        if(!operatorSignService.leftBrace())
            return null

        val lexem = getCurrentLexeme()

        val expressionNode = Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        children.add(expressionNode)

        if(!operatorSignService.rightBrace())
            return null

        val helpfulExpressionNode = helpfulExpression()
        if(helpfulExpressionNode!= null)
            children.add(helpfulExpressionNode)

        val parent = constructTree(GrammarSymbols.ASSIGNMENT, children)
        if (parent == null)
            printErrMsg("bracedExpression")
        return parent
    }

    // <Подвыражение> :: = <Операнд> <Вспомогательное выражение>
    private fun subExpressionWithOperand(): ASTNode? {
        val children: ArrayList<ASTNode?> = arrayListOf()
        val operandNode = operand()
        children.add(operandNode)


        val lexem = getCurrentLexeme()
        val helpfulExpressionNode = helpfulExpression()
        if(helpfulExpressionNode!= null)
            children.add(helpfulExpressionNode)

        val parent = constructTree(GrammarSymbols.SUB_EXPRESSION, children)
        if (parent == null)
            printErrMsg("subExpression")
        return parent
    }

    // <Вспомогательное выражение> :: = <Бин.оп.><Подвыражение><Вспомогательное выражение> | Ɛ
    private fun helpfulExpression(): ASTNode?{
        val lexem = getCurrentLexeme()
        if(lineBreak())
            return null

        val subExpressionsWithBinaryOperatorNodeList = subExpressionsWithBinaryOperator()

        val parent = constructTree(GrammarSymbols.SUB_EXPRESSION, subExpressionsWithBinaryOperatorNodeList)
        if (parent == null)
            printErrMsg("subExpression")
        return parent

    }

    // <Бин.оп.><Подвыражение><Вспомогательное выражение>
    private fun subExpressionsWithBinaryOperator(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()
        val LEXEM = getCurrentLexeme.invoke()

        val binaryOperator = binaryOperator()
        children.add(binaryOperator)

        val subExpressionNode = analyze()
        children.add(subExpressionNode)

        val helpfulExpressionNode = helpfulExpression()
        if(helpfulExpressionNode!= null)
            children.add(helpfulExpressionNode)

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