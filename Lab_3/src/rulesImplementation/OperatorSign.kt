package rulesImplementation

import currentLexeme
import ASTNode

class OperatorSign(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme){

    // "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
    fun binaryOperator(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.BIN_MATH_OPERATOR) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.BINARY_OPERATOR, lexeme)
        }
        return null
    }

    // "-"
    fun unaryOperator(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.UNI_MATH_OPERATOR) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.UNARY_OPERATOR, lexeme)
        }
        return null
    }


    // "("
    fun leftBrace(): Boolean {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.LBRACE) {
            moveToTheNextLexeme.invoke()
            return true
        }
        return false
    }

    // ")"
    fun rightBrace(): Boolean {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.RBRACE) {
            moveToTheNextLexeme.invoke()
            return true
        }
        return false
    }

    // "IF"
    fun ifSign(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.IF) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.IF, lexeme)
        }
        return null
    }

    // "THEN"
    fun then(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.THEN) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.THEN, lexeme)
        }
        return null
    }

    // "ELSE"
    fun elseSign(): ASTNode? {
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.ELSE) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.ELSE, lexeme)
        }
        return null
    }

    fun comma(): Boolean{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.COMMA){
            moveToTheNextLexeme()
            return true
        }
        return false
    }
}