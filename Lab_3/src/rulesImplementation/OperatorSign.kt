package rulesImplementation

import currentLexeme
import ASTNode

class OperatorSign(private val getCurrentLexeme: currentLexeme){

    // <Бин.оп.> ::= "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
    fun binaryOperator(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.BIN_MATH_OPERATOR) {
            return ASTNode(GrammarSymbols.BINARY_OPERATOR, lexeme)
        }
        return null
    }

    // <Ун.оп.> ::= "-"
    fun unaryOperator(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.UNI_MATH_OPERATOR) {
            return ASTNode(GrammarSymbols.UNARY_OPERATOR, lexeme)
        }
        return null
    }
}