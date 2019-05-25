package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg
import Lexem

class SubExpression(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Подвыражение> :: = ( <Выражение> ) | <Операнд> | <Подвыражение > <Бин.оп.> <Подвыражение>
    fun analyze(): ASTNode? {
        var children: ArrayList<ASTNode?> = bracedExpression()

        if (children.isEmpty())
            children = operand()

        if (children.isEmpty())
            children = subExpressionsWithBinaryOperator()


        val parent = constructTree(GrammarSymbols.ASSIGNMENT, children)
        if (parent == null)
            printErrMsg("subExpression")
        return parent
    }


    // ( <Выражение> )
    private fun bracedExpression(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()
        val lexeme = getCurrentLexeme.invoke()

        if(lexeme.type == LexemType.LBRACE){
            val leftBraceNode = ASTNode(GrammarSymbols.LEFT_BRACE, lexeme)
            children.add(leftBraceNode)

            moveToTheNextLexeme.invoke()
            val expressionNode = Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
            children.add(expressionNode)

            moveToTheNextLexeme.invoke()
            if(lexeme.type == LexemType.RBRACE){
                val rightBraceNode = ASTNode(GrammarSymbols.RIGHT_BRACE, lexeme)
                children.add(rightBraceNode)
            }
        }

        return children
    }

    // <Операнд>
    private fun operand(): ArrayList<ASTNode?>{
        return arrayListOf( Operand(getCurrentLexeme).analyze() )
    }

    // <Подвыражение > <Бин.оп.> <Подвыражение>
    private fun subExpressionsWithBinaryOperator(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()
        val subExpressionNodeOne = analyze()
        children.add(subExpressionNodeOne)

        moveToTheNextLexeme.invoke()
        val lexeme = getCurrentLexeme.invoke()
        val binaryOperator = binaryOperator(lexeme)
        children.add(binaryOperator)

        moveToTheNextLexeme.invoke()
        val subExpressionNodeTwo = analyze()
        children.add(subExpressionNodeTwo)

        return children
    }

    // <Бин.оп.> ::= "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
    private fun binaryOperator(lexeme: Lexem): ASTNode? {
        if(lexeme.type == LexemType.BIN_MATH_OPERATOR) {
            return ASTNode(GrammarSymbols.BINARY_OPERATOR, lexeme)
        }
        return null
    }
}