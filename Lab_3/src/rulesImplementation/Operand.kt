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
        if(lexeme.type == LexemType.IDENTIFIER) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.IDENTIFIER, lexeme)
        }
        return null
    }

    // <Const>
    private fun const(): ASTNode?{
        val lexeme = SyntaxAnalyzer.getCurrentLexeme()
        if(lexeme.type == LexemType.CONST) {
            SyntaxAnalyzer.moveToTheNextLexeme()
            return ASTNode(GrammarSymbols.CONST, lexeme)
        }
        return null
    }
}