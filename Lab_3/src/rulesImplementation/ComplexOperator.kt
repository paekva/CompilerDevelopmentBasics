package rulesImplementation

import ASTNode
import constructTree
import printErrMsg

class ComplexOperator{

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
        val operatorSignService = OperatorSign()

        val ifSignNode = operatorSignService.ifSign()
        ifSignNode ?: return arrayListOf()

        val expressionNode = Expression().analyze()
        expressionNode ?: run {
            ErrorLog.logError("Нет выражения после if")
            SyntaxAnalyzer.skipCurrentLine()
            return arrayListOf()
        }

        val thenSignNode = operatorSignService.then()
        thenSignNode ?: run {
            ErrorLog.logError("Нет then после if")
            SyntaxAnalyzer.skipCurrentLine()
            return arrayListOf()
        }

        val operatorNode = operator()
        operatorNode ?: run {
            ErrorLog.logError("Нет выражения после then")
            SyntaxAnalyzer.skipCurrentLine()
            return arrayListOf()
        }

        val continueNode = ifThenElse()

        removeLineBreak()

        val children = arrayListOf<ASTNode?>(ifSignNode, expressionNode, thenSignNode, operatorNode)
        children.addAll(continueNode)
        return children
    }

    private fun removeLineBreak(): Boolean{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemType.LINEBREAK) {
            ErrorLog.nextLine()
            SyntaxAnalyzer.moveToTheNextLexeme()
            return true
        }
        return false
    }

    // <Оператор>
    private fun operator(): ASTNode? {
        return Operator().analyze()
    }

    //  <Продолжение IF THEN> ::= Ɛ | ELSE <Оператор>
    private fun ifThenElse(): ArrayList<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()
        val operatorSignService = OperatorSign()

        if(operatorSignService.isElse()){
            val elseSignNode = operatorSignService.elseSign()
            children.add(elseSignNode)

            val operatorNode = Operator().analyze()
            operatorNode ?:run {
                ErrorLog.logError("Нет выражения после else")
                SyntaxAnalyzer.skipCurrentLine()
                return arrayListOf()
            }

            children.add(operatorNode)
        }

        return children
    }

    // <Составной оператор>
    private fun compoundOperator(): ASTNode? {
        return CompoundOperator().analyze()
    }
}