package rulesImplementation

import currentLexeme
import ASTNode
import Lexem

class Operand(private val getCurrentLexeme: currentLexeme) {

    // <Операнд> ::= <Идент> | <Const>
    fun analyze(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        var node = identifier(lexeme)

        if(node==null)
            node = const(lexeme)

        return node
    }

    // <Идент>
    private fun identifier(lexeme: Lexem): ASTNode?{
        if(lexeme.type == LexemType.IDENTIFIER) {
            return ASTNode(GrammarSymbols.IDENTIFIER, lexeme)
        }
        return null
    }

    // <Const>
    private fun const(lexeme: Lexem): ASTNode?{
        if(lexeme.type == LexemType.CONST) {
            return ASTNode(GrammarSymbols.CONST, lexeme)
        }
        return null
    }
}