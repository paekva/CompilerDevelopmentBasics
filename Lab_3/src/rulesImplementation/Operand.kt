package rulesImplementation

import ASTNode
class Operand {

    // <Операнд> ::= <Идент> | <Const>
    fun analyze(): ASTNode?{
        val node = identifier()
        return node ?: const()
    }

    // <Идент>
    fun identifier(): ASTNode?{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.IDENTIFIER) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.IDENTIFIER, lexeme)
        }
        return null
    }

    // <Const>
    private fun const(): ASTNode?{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemeType.CONST) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.CONST, lexeme)
        }
        return null
    }
}