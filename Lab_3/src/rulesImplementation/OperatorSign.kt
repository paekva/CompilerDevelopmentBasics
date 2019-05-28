package rulesImplementation

import ASTNode
import GrammarSymbols

class OperatorSign(){

    // "-" | "+" | "*" | "/" | ">>" | "<<" | ">" | "<" | "="
    fun binaryOperator(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.BIN_MATH_OPERATOR || lexeme.type == LexemeType.RELATION_OPERATOR) {
            SyntaxAnalyzer.moveToTheNextLexeme()

            val operatorType: GrammarSymbols = findOperatorType(lexeme.sign)
            return ASTNode(operatorType, lexeme)
        }
        return null
    }

    private fun findOperatorType(value: String): GrammarSymbols {
        return GrammarSymbols.values().filter { it.sign == value }[0]
    }

    // "-"
    fun unaryOperator(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.UNI_MATH_OPERATOR) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.UNARY_OPERATOR, lexeme)
        }
        return null
    }


    // "("
    fun leftBrace(): Boolean {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.LBRACE) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return true
        }
        return false
    }

    // ")"
    fun rightBrace(): Boolean {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.RBRACE) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return true
        }
        return false
    }

    // "IF"
    fun ifSign(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.IF) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.IF, lexeme)
        }
        return null
    }

    // "THEN"
    fun then(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.THEN) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.THEN, lexeme)
        }
        return null
    }

    // "ELSE"
    fun elseSign(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.ELSE) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.ELSE, lexeme)
        }
        return null
    }

    fun isElse(): Boolean {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.ELSE)
            return true
        return false
    }

    fun comma(): Boolean{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.COMMA){
            SyntaxAnalyzer.moveToTheNextLexeme()
            return true
        }
        return false
    }

    fun assignmentSign(): ASTNode? {
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.DECLARE) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.ASSIGNMENT_SIGN, lexeme)
        }
        return null
    }
}