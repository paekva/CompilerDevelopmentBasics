package rulesImplementation

import currentLexeme
import ASTNode
import Lexem

class Operand(private val getCurrentLexeme: currentLexeme) {

    // <Операнд> ::= <Идент> | <Const>
    fun analyze(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        var node = identifier()

        if(node==null)
            node = const()

        return node
    }

    // <Идент>
    fun identifier(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.IDENTIFIER) {
            return ASTNode(GrammarSymbols.IDENTIFIER, lexeme)
        }
        return null
    }

    // <Const>
    fun const(): ASTNode?{
        val lexeme = getCurrentLexeme.invoke()
        if(lexeme.type == LexemType.CONST) {
            return ASTNode(GrammarSymbols.CONST, lexeme)
        }
        return null
    }
}