package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class ComplexOperator (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // <Сложный оператор> ::= IF < Выражение> THEN <Оператор> <Продолжение IF THEN> | <Составной оператор>
    fun analyze(): ASTNode?{
        val children: ArrayList<ASTNode?> = arrayListOf()

        val ifThenNode = ifThen()
        if(ifThenNode.isNotEmpty())
            children.addAll(ifThenNode)
        else
        {
            val compoundOperatorNode = compoundOperator()
            compoundOperatorNode ?: return null

            children.add(compoundOperatorNode)
        }

        val parent = constructTree(GrammarSymbols.COMPLEX_OPERATOR, children)
        if(parent == null)
            printErrMsg("complexOperator")
        return parent
    }

    // IF < Выражение> THEN <Оператор> <Продолжение IF THEN>
    private fun ifThen(): ArrayList<ASTNode?>{
        val operatorSignService = OperatorSign(getCurrentLexeme, moveToTheNextLexeme)

        val ifSignNode = operatorSignService.ifSign()
        ifSignNode ?: return arrayListOf()

        val expressionNode = Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        expressionNode ?: return arrayListOf()

        val thenSignNode = operatorSignService.then()
        thenSignNode ?: return arrayListOf()

        val operatorNode = operator()
        operatorNode ?: return arrayListOf()

        val continueNode = ifThenElse()

        removeLineBreak()

        val children = arrayListOf<ASTNode?>(ifSignNode, expressionNode, thenSignNode, operatorNode)
        children.addAll(continueNode)
        return children
    }

    private fun removeLineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK) {
            moveToTheNextLexeme()
            return true
        }
        return false
    }

    // <Оператор>
    private fun operator(): ASTNode? {
        return Operator(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }

    //  <Продолжение IF THEN> ::= Ɛ | ELSE <Оператор>
    private fun ifThenElse(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()
        val operatorSignService = OperatorSign(getCurrentLexeme, moveToTheNextLexeme)

        if(operatorSignService.isElse()){
            val elseSignNode = operatorSignService.elseSign()
            children.add(elseSignNode)
            val operatorNode = Operator(getCurrentLexeme, moveToTheNextLexeme).analyze()
            children.add(operatorNode)
        }

        return children
    }

    // <Составной оператор>
    private fun compoundOperator(): ASTNode? {
        return CompoundOperator(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }
}