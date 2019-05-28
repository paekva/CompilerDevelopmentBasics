package rulesImplementation

import currentLexeme
import ASTNode
import constructTree
import printErrMsg

class ComplexOperator (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // <Сложный оператор> ::= IF < Выражение> THEN Оператор | IF <Выражение> THEN <Оператор> ELSE <Оператор>| <Составной оператор>
    fun analyze(): ASTNode?{
        val children: ArrayList<ASTNode?> = arrayListOf()

        val ifThenNode = ifThen()
        if(ifThenNode!=null)
            children.add(ifThenNode)
        else{
            val compoundOperatorNode = compoundOperator()
            if(compoundOperatorNode != null)
                children.add(compoundOperatorNode)
        }

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("complexOperator")
        return parent
    }

    // IF < Выражение> THEN <Оператор> <Продолжение IF THEN>
    private fun ifThen(): ASTNode?{
        val operatorSignService = OperatorSign(getCurrentLexeme, moveToTheNextLexeme)

        val ifSignNode = operatorSignService.ifSign()
        val expressionNode = Expression(getCurrentLexeme, moveToTheNextLexeme).analyze()
        val elseSignNode = operatorSignService.elseSign()
        val operatorNode = Operator(getCurrentLexeme, moveToTheNextLexeme).analyze()
        val children = arrayListOf(ifSignNode, expressionNode, elseSignNode, operatorNode)

        val continueNode = ifThenElse()
        children.addAll(continueNode)

        val parent = constructTree(GrammarSymbols.OPERATORS_LIST, children)
        if(parent == null)
            printErrMsg("ifThen")
        return parent
    }

    //  <Продолжение IF THEN> ::= Ɛ | ELSE <Оператор>
    private fun ifThenElse(): List<ASTNode?>{
        val children: ArrayList<ASTNode?> = arrayListOf()

        if(!lineBreak()){
            val elseSignNode = OperatorSign(getCurrentLexeme, moveToTheNextLexeme).elseSign()
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

    private fun lineBreak(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LINEBREAK){
            moveToTheNextLexeme()
            return true
        }
        return false
    }
}