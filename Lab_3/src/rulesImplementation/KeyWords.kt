package rulesImplementation

import ASTNode
class KeyWords{
    companion object {
        // Begin
        fun begin(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemType.BEGIN) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                removeLineBreak()
                return ASTNode(GrammarSymbols.BEGIN, lexeme)
            }
            return null
        }

        // Var
        fun variable(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemType.VAR) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                return ASTNode(GrammarSymbols.VAR, lexeme)
            }
            return null
        }

        // End
        fun end(): ASTNode? {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemType.END) {
                SyntaxAnalyzer.moveToTheNextLexeme()
                removeLineBreak()
                return ASTNode(GrammarSymbols.END, lexeme)
            }
            return null
        }

        fun isEnd(): Boolean {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemType.END) {
                return true
            }
            return false
        }

        private fun removeLineBreak(): Boolean {
            val lexeme = SyntaxAnalyzer.getCurrentLexeme()
            if (lexeme.type == LexemType.LINEBREAK) {
                ErrorLog.nextLine()
                SyntaxAnalyzer.moveToTheNextLexeme()
                return true
            }
            return false
        }
    }
}