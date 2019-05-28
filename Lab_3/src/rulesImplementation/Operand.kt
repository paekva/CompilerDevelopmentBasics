package rulesImplementation

import currentLexeme
import ASTNode

class Operand(private val getCurrentLexeme: currentLexeme, private val moveToTheNextLexeme: currentLexeme) {

    // <Операнд> ::= <Идент> | <Const>
    fun analyze(): ASTNode?{
        val node = identifier()
        return node ?: const()
    }

    // <Идент>
    fun identifier(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.IDENTIFIER) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.IDENTIFIER, lexeme)
        }
        return null
    }

    // <Const>
    private fun const(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.CONST) {
            moveToTheNextLexeme.invoke()
            return ASTNode(GrammarSymbols.CONST, lexeme)
        }
        return null
    }
}