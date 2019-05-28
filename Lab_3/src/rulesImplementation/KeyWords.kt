package rulesImplementation

import ASTNode
class KeyWords{
    companion object {
        // Begin
        fun begin(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemeType.BEGIN) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                SyntaxAnalyzer.removeLineBreak()
                return ASTNode(GrammarSymbols.BEGIN, lexeme)
            }
            return null
        }

        // Var
        fun variable(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemeType.VAR) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                return ASTNode(GrammarSymbols.VAR, lexeme)
            }
            return null
        }

        // End
        fun end(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemeType.END) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                SyntaxAnalyzer.removeLineBreak()
                return ASTNode(GrammarSymbols.END, lexeme)
            }
            return null
        }

        fun isEnd(): Boolean {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemeType.END) {
                return true
            }
            return false
        }
    }
}