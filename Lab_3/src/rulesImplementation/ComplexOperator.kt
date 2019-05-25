package rulesImplementation

import currentLexeme
import ASTNode

class ComplexOperator (private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // <Сложный оператор> ::= IF < Выражение> THEN Оператор | IF <Выражение> THEN <Оператор> ELSE <Оператор>| <Составной оператор>
    fun analyze(): ASTNode?{
        return null
    }

    // IF < Выражение> THEN Оператор
    private fun ifThen(){

    }

    // IF <Выражение> THEN <Оператор> ELSE <Оператор>
    private fun ifThenElse(){

    }

    // <Составной оператор>
    private fun compoundOperator(){
        return CompoundOperator(getCurrentLexeme, moveToTheNextLexeme).analyze()
    }
}